package com.zhanghui.api.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.zhanghui.api.bean.ApiContext;
import com.zhanghui.api.bean.sign.ApiKey;
import com.zhanghui.api.bean.sign.ApiKeyContainer;
import com.zhanghui.api.controller.ApiController;
import com.zhanghui.api.emums.EApiErrorCode;
import com.zhanghui.api.exception.ApiException;
import com.zhanghui.api.request.ApiRequest;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.security.provider.MD5;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Api 工具类
 */
public class ApiUtil {
    private static final Logger logger = LoggerFactory.getLogger(ApiUtil.class);

    /**
     * 验签
     * @param apiRequest
     */
    public static void checkSign(ApiRequest apiRequest) {
        try {
            //校验签名开关
            if (ApiContext.getApiConfig().getIgnoreSign()) {
                logger.warn("验签开关关闭");
                return;
            }

            String apiRequestJson= JSON.toJSONString(apiRequest);

            Map map=JSON.parseObject(apiRequestJson);

            logger.warn("验签参数 {}",map);

            ApiKey apiKey=ApiKeyContainer.getApiKey(apiRequest.getAppId());
            if(null!=apiKey && !StringUtils.isEmpty(apiKey.getPublicKey())) {
                boolean checkSign = AlipaySignature.rsaCheckV1(map, apiKey.getPublicKey(), apiRequest.getCharset(), apiRequest.getSignType());

                //boolean checkSign = AlipaySignature.rsaCheck(apiRequestJson, applicationProperty.getPublicKey(), apiRequest.getCharset(), apiRequest.getSignType());
                if (!checkSign) {
                    logger.info("验签失败 >> params = {}", JSON.toJSONString(apiRequest));
                    throw new ApiException(EApiErrorCode.INVALID_SIGN);
                }
                logger.warn("验签成功 >> params = {}", JSON.toJSONString(apiRequest));
            }else{
                logger.error("验签异常 >> params = {}, error = {}", JSON.toJSONString(apiRequest), EApiErrorCode.PUBLIC_KEY_NOT_EXIST.getMsg());
                throw new ApiException(EApiErrorCode.PUBLIC_KEY_NOT_EXIST);
            }
        }catch (ApiException e){
           throw e;
        } catch (Exception e) {
            logger.error("验签异常 >> params = {}, error = {}", JSON.toJSONString(apiRequest), e.getStackTrace());
            throw new ApiException(EApiErrorCode.SIGN_ERROR);
        }

    }

    //使用了默认的格式创建了一个日期格式化对象,目前用于token验证
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ;

     /**
     * 获取token
     * @param appid
     */
    public static String createToken(String appid) {
       return TokenUtil.getToken(appid,TokenUtil.TOKEN_SECRET);
    }

    public static void checkToken(ApiRequest apiRequest){
        try {
            //校验签名开关
            if (ApiContext.getApiConfig().getIgnoreToken()) {
                logger.warn("token校验关闭");
                return;
            }

            String reqToken=apiRequest.getAppAuthToken().trim();
            if(!StringUtils.isEmpty(reqToken)){
                TokenUtil.checkToken(reqToken,TokenUtil.TOKEN_SECRET);
            }else{
                logger.error("token异常 >> params = {}, error = {}", JSON.toJSONString(apiRequest), EApiErrorCode.TOKEN_NOT_EXIST.getMsg());
                throw new ApiException(EApiErrorCode.TOKEN_NOT_EXIST);
            }
        }catch (Exception e){
            logger.error("token异常 >> params = {}, error = {}", JSON.toJSONString(apiRequest), e.getStackTrace());
            throw new ApiException(EApiErrorCode.TOKEN_ERROR);
        }
    }
}
