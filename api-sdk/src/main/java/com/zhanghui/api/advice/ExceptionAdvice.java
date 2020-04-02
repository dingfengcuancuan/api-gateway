package com.zhanghui.api.advice;

import com.zhanghui.api.exception.ApiException;
import com.zhanghui.api.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

/**
 * 统一异常处理
 */
@ControllerAdvice
@EnableAspectJAutoProxy
public class ExceptionAdvice {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @CrossOrigin
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public ApiResponse defaultExceptionHandler(ApiException exception, HttpServletResponse response) {
        ApiResponse result;

        /*try {
            logger.warn("全局异常 >> error = {}", exception.getMessage(), exception);
            throw exception;
        } catch (ApiException e) {*/
            logger.error("API异常 >> error = {}", exception.getMessage(), exception);

            result = ApiResponse.error(exception.getCode(), exception.getMsg());
        //}

        return result;

    }

}
