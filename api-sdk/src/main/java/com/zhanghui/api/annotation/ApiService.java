package com.zhanghui.api.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记类具有接口提供能力，该注解同样具备SpringBean管理功能，因为继承了@Service
 * @author zhui
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Service
public @interface ApiService {

}
