package com.zhanghui.api.exception;


import com.zhanghui.api.emums.EApiErrorCode;

import java.text.MessageFormat;

/**
 * @author zhui
 */
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 16789476595630713L;

    private String code = EApiErrorCode.SYSTEM_ERROR.getCode();
    private String msg=EApiErrorCode.SYSTEM_ERROR.getMsg();

    private Object data;

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(Exception e) {
        super(e);
    }

    public ApiException(EApiErrorCode error) {
        this(error.getCode(),error.getMsg());
    }


    public ApiException(String code,String msg) {
        super(msg);
        this.code = code;
        this.msg=msg;
    }

    public ApiException(String code,String msgFormat, Object... args) {
        super(MessageFormat.format(msgFormat, args));
        this.code=code;
        this.msg = MessageFormat.format(msgFormat, args);
    }


    public ApiException(String msg, String code, Object data) {
        super(msg);
        this.code = code;
        this.msg=msg;
        this.data = data;
    }

    public ApiException(EApiErrorCode error, Object data) {
        this(error);
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
