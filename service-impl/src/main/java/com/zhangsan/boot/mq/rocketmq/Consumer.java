package com.zhangsan.boot.mq.rocketmq;

import com.zhangsan.boot.listener.ConcurrentlyListener;
import com.zhangsan.boot.listener.OrderlyListener;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Slf4j
@Configuration
public class Consumer {

    @Autowired
    private BaseConsumerConfig baseConsumerConfig;

    @Autowired
    private OrderlyConsumerConfig orderlyConsumerConfig;

    @Autowired
    private ConcurrentlyConsumerConfig concurrentlyConsumerConfig;

    @Autowired
    private OrderlyListener orderlyListener;
    @Autowired
    private ConcurrentlyListener concurrentlyListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer defaultConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(orderlyConsumerConfig.getConsumerGroupName());
        consumer.setNamesrvAddr(baseConsumerConfig.getNamesrvAddr());
        consumer.setConsumeThreadMin(baseConsumerConfig.getConsumeThreadMin());
        consumer.setConsumeThreadMax(baseConsumerConfig.getConsumeThreadMax());
        consumer.setConsumeMessageBatchMaxSize(baseConsumerConfig.getConsumeMessageBatchMaxSize());
        // 设置消息监听
        consumer.registerMessageListener(orderlyListener);
        // 设置消费起始位置位置
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //设置消费模型，集群还是广播
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(orderlyConsumerConfig.getTopicName(), orderlyConsumerConfig.getTag());
        return consumer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer concurrentConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(concurrentlyConsumerConfig.getConsumerGroupName());
        consumer.setNamesrvAddr(baseConsumerConfig.getNamesrvAddr());
        consumer.setConsumeThreadMin(baseConsumerConfig.getConsumeThreadMin());
        consumer.setConsumeThreadMax(baseConsumerConfig.getConsumeThreadMax());
        consumer.setConsumeMessageBatchMaxSize(baseConsumerConfig.getConsumeMessageBatchMaxSize());
        // 设置消息监听
        consumer.registerMessageListener(concurrentlyListener);
        // 设置消费起始位置位置
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //设置消费模型，集群还是广播
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(concurrentlyConsumerConfig.getTopicName(), concurrentlyConsumerConfig.getTag());
        return consumer;
    }
}
