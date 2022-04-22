package com.zhangsan.boot.impl;

import com.zhangsan.boot.annotation.DataSource;
import com.zhangsan.boot.dao.TPadDeviceMapper;
import com.zhangsan.boot.entity.TPadDevice;
import com.zhangsan.boot.enums.core.DataSourceType;
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

    /**
     * 默认主库
     * @param id
     * @return
     */
    @Override
    public TPadDevice masterGetById(Long id) {
        return tPadDeviceMapper.selectByPrimaryKey(id);
    }

    /**
     * 从库
     * @param id
     * @return
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public TPadDevice salverGetById(Long id) {
        return tPadDeviceMapper.selectByPrimaryKey(id);
    }
}
