package com.zhanghui.api.bean;


/**
 * Api 参数配置
 */

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置类,所有配置相关都在这里.
 * 
 * @author tanghc
 *
 */
@Data
public class ApiConfig {

    private String appName = "app";

    /**
     * 默认版本号
     */
    private String defaultVersion = "1.0";

    /**
     * 忽略验证签名
     * true: 不验证  false：验证
     */
    private Boolean ignoreSign = false;


    /**
     * 超时时间 单位：秒
     */
    private int timeoutSeconds = 10;

    /**
     * 忽略token签名
     * true: 不验证  false：验证
     */
    private Boolean ignoreToken =false;


}
