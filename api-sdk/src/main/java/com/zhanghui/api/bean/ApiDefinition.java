package com.zhanghui.api.bean;


import lombok.Data;

import java.lang.reflect.Method;

/**
 * 接口定义，负责存放定义的接口信息
 * @author zhui
 */
@Data
public class ApiDefinition {
    /** 接口名 */
    private String name;
    /** 版本号 */
    private String version;
    /** 接口描述，如果定义了@ApiDocMethod注解会同步其description属性到这 */
    private String description;

    /**
     * 类 spring bean
     */
    private String beanName;

    /** 接口对应的Service类 */
    private Object clazz;

    /** 接口对应的方法 */
    private Method method;

    /** 方法参数的class */
    private Class<?> methodArguClass;

    //private boolean ignoreSign;
}
