package com.zhanghui.api.annotation;

import java.lang.annotation.*;

/**
 * 作用在service类的方法上，service类被@ApiService标记
 * @author zhui
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Api {
    /**
     * @return 接口名，建议命名规则: <strong>业务.模块.名字.动词</strong>。如：<br/>
     *          支付业务订单状态修改：pay.order.status.update<br/>
     *          充值业务用户余额充值：charge.user.money.recharge<br/>
     *          充值业务用户余额查询：charge.user.money.search<br/>
     */
    String name();

    /**
     * @return 接口版本号，默认""，建议命名规则：x.y，如1.0，1.1
     */
    String version() default "";

    /**
     * @return 忽略验证签名，默认false。为true接口不执行验签操作，但其它验证会执行。
     */
    boolean ignoreSign() default false;

    String desc() default "";
}
