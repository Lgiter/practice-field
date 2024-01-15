package com.lgiter.practice.spring.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: lixiaolong
 * Date: 2024/1/15
 * Desc:
 *
 * @author lixiaolong5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TimezoneConvert {

    String value() default "";
}
