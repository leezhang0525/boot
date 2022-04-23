package com.zhangsan.boot.controller;

import com.zhangsan.boot.entity.TPadDevice;
import com.zhangsan.boot.impl.MQServiceImpl;
import com.zhangsan.boot.impl.RedisTemplateServiceImpl;
import com.zhangsan.boot.service.PadDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test(){
        log.info("这是测试");
        return "success";
    }

    @Autowired
    private PadDeviceService padDeviceService;

    @GetMapping("/sqlTest")
    public TPadDevice sqlTest(Long id){
        log.info("这是测试");
        TPadDevice padDevice = padDeviceService.getById(id);
        return padDevice;
    }

    @GetMapping("/masterDataSource")
    public TPadDevice dynamicDataSourceTest(Long id){
        log.info("这是测试");
        TPadDevice padDevice = padDeviceService.masterGetById(id);
        return padDevice;
    }

    @GetMapping("/slaveDataSource")
    public TPadDevice slaveDataSource(Long id){
        log.info("这是测试");
        TPadDevice padDevice = padDeviceService.salverGetById(id);
        return padDevice;
    }

    @Autowired
    private RedisTemplateServiceImpl redisTemplateService;

    @GetMapping("/redisTest")
    public String redisTest(String param){
        return redisTemplateService.setKey(param);
    }

    @Autowired
    private MQServiceImpl mqService;

    @GetMapping("/mqOrderLyTest")
    public String mqOrderLyTest(){
        return mqService.sendMQMsg();
    }

    @GetMapping("/mqConcurrentTest")
    public String mqConcurrentTest(){
        return mqService.sendRandomMQMsg();
    }
}
