package com.zhanghui.api.bean;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Api上下文,方便获取信息
 * 
 * @author zhui
 *
 */
@Data
public class ApiContext {
    private static Logger logger = LoggerFactory.getLogger(ApiContext.class);

    private static ApplicationContext applicationContext;

    private static ApiConfig apiConfig;

    private ApiContext(){}

    public static ApiConfig getApiConfig() {
        return apiConfig;
    }

    public static void setApiConfig(ApiConfig apiConfig) {
        ApiContext.apiConfig = apiConfig;
    }

    /**
     * 获取spring应用上下文
     * @return 返回spring应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApiContext.applicationContext = applicationContext;
    }

}
