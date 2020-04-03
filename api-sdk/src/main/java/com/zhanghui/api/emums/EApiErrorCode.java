package com.zhanghui.api.emums;

/**
 * 异常枚举
 */
public enum EApiErrorCode {

    /**
     * api异常枚举
     */
    SUCCESS("1000", "调用成功"),

    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    API_NOT_EXIST("API_NOT_EXIST", "API方法不存在"),
    INVALID_PUBLIC_PARAM("INVALID_PUBLIC_PARAM", "无效公共参数 >> {0}"),
    INVALID_REQUEST_ERROR("INVALID_REQUEST_ERROR", " 请求方式 {0} 错误 ! 请使用 {1} 方式"),
    INVALID_PARAM("INVALID_PARAM", "无效参数 >> 参数[{0}] >> 原因[{1}]"),
    INVALID_SIGN("INVALID_SIGN", "无效签名"),
    SIGN_ERROR("SIGN_ERROR", "签名异常"),
    PUBLIC_KEY_NOT_EXIST("PUBLIC_KEY_NOT_EXIST", "公钥不存在"),

    ;

    EApiErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
