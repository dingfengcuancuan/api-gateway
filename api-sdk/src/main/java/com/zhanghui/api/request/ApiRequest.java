package com.zhanghui.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

//@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRequest {
    private String app_id;
    private String method;
    private String format="JSON";
    private String charset="UTF-8";
    private String sign_type="RSA2";
    private String sign;
    private String timstamp;
    private String version="1.0";
    private String notify_url;
    private String app_auth_token;
    private String biz_content;


    public String getAppId() {
        return app_id;
    }

    public void setAppId(String app_id) {
        this.app_id = app_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return sign_type;
    }

    public void setSignType(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimstamp() {
        return timstamp;
    }

    public void setTimstamp(String timstamp) {
        this.timstamp = timstamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotifyUrl() {
        return notify_url;
    }

    public void setNotifyUrl(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getAppAuthToken() {
        return app_auth_token;
    }

    public void setAppAuthToken(String app_auth_token) {
        this.app_auth_token = app_auth_token;
    }

    public String getBizContent() {
        return biz_content;
    }

    public void setBizContent(String biz_content) {
        this.biz_content = biz_content;
    }
}
