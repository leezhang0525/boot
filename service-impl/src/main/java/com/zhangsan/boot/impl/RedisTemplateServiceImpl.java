package com.zhangsan.boot.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("redisTemplateService")
@Slf4j
public class RedisTemplateServiceImpl {

    private static final String TEST_REDIS_KEY_PREFIX = "test:redis:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String setKey (String param){
        String key = TEST_REDIS_KEY_PREFIX+param;
        String value = UUID.randomUUID().toString().replace("-","");
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key,value,200, TimeUnit.SECONDS);
        if(res){
            return "success";
        }else {
            return "fail";
        }
    }
}
