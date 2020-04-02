package com.zhanghui.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhanghui.api.emums.EApiErrorCode;
import lombok.Data;
import sun.security.krb5.internal.EncAPRepPart;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    String code;
    String msg;
    String sign;
    Object result;

    /**
     * 默认构造器
     *
     */
    public ApiResponse() {
    }

    /**
     * 处理成功构造器
     *
     * @param result
     */
    public ApiResponse(Object result) {
        this.code= EApiErrorCode.SUCCESS.getCode();
        this.msg= EApiErrorCode.SUCCESS.getMsg();
        this.setResult(result);
    }


    /**
     * 返回成功方法
     *
     * @param data
     * @return
     */
    public static ApiResponse success(Object data) {
        return new ApiResponse(data);
    }
}
