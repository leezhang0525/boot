package com.zhangsan.boot.impl;

import com.zhangsan.boot.dao.TPadDeviceMapper;
import com.zhangsan.boot.entity.TPadDevice;
import com.zhangsan.boot.service.PadDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PadDeviceServiceImpl implements PadDeviceService {

    @Autowired
    private TPadDeviceMapper tPadDeviceMapper;

    @Override
    public TPadDevice getById(Long id) {
        return tPadDeviceMapper.selectByPrimaryKey(id);
    }
}
