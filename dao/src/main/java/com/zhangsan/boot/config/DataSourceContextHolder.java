package com.zhangsan.boot.config;

/**
 * 数据源处理器
 */
public class DataSourceContextHolder {

    private DataSourceContextHolder(){}

    /**
     * 类加载时创建
     */
    private static final ThreadLocal<String>  holder = new ThreadLocal<>();

//    public static void putDataSource(DataSourceType dataSourceType) {
//        holder.set(dataSourceType.getValue());
//    }

    public static String getDataSource() {
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

}
