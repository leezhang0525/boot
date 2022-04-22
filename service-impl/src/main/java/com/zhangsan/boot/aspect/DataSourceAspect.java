package com.zhangsan.boot.aspect;

import com.zhangsan.boot.annotation.DataSource;
import com.zhangsan.boot.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
public class DataSourceAspect {

    @Around("execution(public * com.zhangsan.boot.service..*.*(..)) && @annotation(com.zhangsan.boot.annotation.DataSource) && @annotation(dataSource)")
    public Object setDataSource(ProceedingJoinPoint joinPoint, DataSource dataSource) throws Throwable {
        try {
            log.info("执行了数据路由切面，当前为:{}",dataSource.value());
            DataSourceContextHolder.putDataSource(dataSource.value());
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.clearDataSource();
            log.info("清理了数据路由切面，threadLocal中dataSource为:{}",DataSourceContextHolder.getDataSource());
        }
    }
}
