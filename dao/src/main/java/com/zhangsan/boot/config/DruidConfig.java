package com.zhangsan.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zhangsan.boot.enums.core.DataSourceType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    public static final String MAPPER_BASE_PACKAGE = "com.zhangsan.boot.dao";
    public static final String ENTITY_BASE_PACKAGE = "com.zhangsan.boot.entity";

    /**
     * 主数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource(){
        return new DruidDataSource();
    }

    /**
     * 从数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource(){
        return new DruidDataSource();
    }

    /**
     * 动态数据源
     * @return
     */
    @Bean
    @DependsOn({ "masterDataSource", "slaveDataSource"})
    @Primary
    public DataSource dynamicDataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.getValue(), masterDataSource());
        targetDataSources.put(DataSourceType.SLAVE.getValue(), slaveDataSource());
        dataSource.setTargetDataSources(targetDataSources);
        // 这里不需要重复添加默认数据源，DynamicDataSource类中已经制定默认主数据源key
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory (@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
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

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
