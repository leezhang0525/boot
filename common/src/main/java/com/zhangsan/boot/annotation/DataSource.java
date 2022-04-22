package com.zhangsan.boot.annotation;

import com.zhangsan.boot.enums.core.DataSourceType;

import java.lang.annotation.*;

/**
 * 主从库注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataSource {
    DataSourceType value() default DataSourceType.MASTER;
}
