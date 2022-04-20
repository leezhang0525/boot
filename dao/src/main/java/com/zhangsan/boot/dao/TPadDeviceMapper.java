package com.zhangsan.boot.dao;


import com.zhangsan.boot.entity.TPadDevice;

public interface TPadDeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TPadDevice record);

    int insertSelective(TPadDevice record);

    TPadDevice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TPadDevice record);

    int updateByPrimaryKey(TPadDevice record);
}