package com.zhangsan.boot.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分区顺序消费
 */
@Component
@Slf4j
public class OrderlyListener implements MessageListenerOrderly {
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        if (CollectionUtils.isEmpty(list)) {
            log.info("MQ接收消息为空，直接返回成功");
            return ConsumeOrderlyStatus.SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        try {
            String topic = messageExt.getTopic();
            String tags = messageExt.getTags();
            int queueId = messageExt.getQueueId();
            String body = new String(messageExt.getBody(), "utf-8");
            log.info("MQ消息topic={},queueId={}, tags={}, 消息内容={}", topic,queueId,tags,body);
        } catch (Exception e) {
            log.error("获取MQ消息内容异常{}",e);
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        // 处理业务逻辑
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
