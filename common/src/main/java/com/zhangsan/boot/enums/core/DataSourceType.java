package com.zhangsan.boot.enums.core;

/**
 * 数据源枚举
 */
public enum DataSourceType {

    MASTER("master"),

    SLAVE("slave");

    private String value;

    DataSourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
