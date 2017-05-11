package com.jerryl.auth.service.aop.privilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuruijie on 2017/4/7.
 * 用于标识方法所属模块，所属操作，从而验证用户是否能够访问此方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Privilege {
    String moduleType();
    ActionType actionType();
}
