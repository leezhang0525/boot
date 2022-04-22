package com.zhangsan.boot.service;

import com.zhangsan.boot.entity.TPadDevice;

public interface PadDeviceService {

    TPadDevice getById (Long id);

    TPadDevice masterGetById (Long id);

    TPadDevice salverGetById (Long id);
}
