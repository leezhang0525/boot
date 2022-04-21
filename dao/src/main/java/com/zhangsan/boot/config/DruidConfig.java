package com.zhangsan.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class DruidConfig {

    public static final String MAPPER_BASE_PACKAGE = "com.zhangsan.boot.dao";
    public static final String ENTITY_BASE_PACKAGE = "com.zhangsan.boot.entity";

    /**
     * 数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    /**
     * SqlSessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory (@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(ENTITY_BASE_PACKAGE);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mappers/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * mapperScannerConfigurer
     * @return
     */
    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(MAPPER_BASE_PACKAGE);
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }
}
