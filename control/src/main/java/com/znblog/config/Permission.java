package com.znblog.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hzqiuxm on 2016/8/29 0029.
 * 如果需要检查权限 就是用这个注解 规定访问权限
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {
    int[] value();
}
