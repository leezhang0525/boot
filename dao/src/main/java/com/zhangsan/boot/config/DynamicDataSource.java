package com.zhangsan.boot.config;

import com.zhangsan.boot.enums.core.DataSourceType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义动态数据源路由
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     *
     * @return Map<Object, DataSource> resolvedDataSources key值
     */
    @Override
    protected Object determineCurrentLookupKey() {
        if(StringUtils.isNotBlank(DataSourceContextHolder.getDataSource())){
            return DataSourceContextHolder.getDataSource();
        }
        // 默认主库
        return DataSourceType.MASTER.getValue();
    }
}
