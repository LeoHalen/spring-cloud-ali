package com.pocketcloud.response.common.annotation;


import java.lang.annotation.*;

/**
 * @Author Zg.Li · 2019/12/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
