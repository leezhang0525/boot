package com.zhangsan.boot.mq.rocketmq;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.rocket.consumer.concurrently")
public class ConcurrentlyConsumerConfig {

    private String consumerGroupName;

    private String topicName;

    private String tag;

}
