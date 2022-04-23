package com.zhangsan.boot.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Slf4j
@Component
public class ConcurrentlyListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (CollectionUtils.isEmpty(list)) {
            log.info("并发消费MQ接收消息为空，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        try {
            String topic = messageExt.getTopic();
            String tags = messageExt.getTags();
            int queueId = messageExt.getQueueId();
            String body = new String(messageExt.getBody(), "utf-8");
            log.info("并发消费MQ消息topic={},queueId={}, tags={}, 消息内容={}", topic,queueId,tags,body);
        } catch (Exception e) {
            log.error("并发消费获取MQ消息内容异常{}",e);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
