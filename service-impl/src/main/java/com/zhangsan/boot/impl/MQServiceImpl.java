package com.zhangsan.boot.impl;

import com.zhangsan.boot.mq.rocketmq.ConcurrentlyConsumerConfig;
import com.zhangsan.boot.mq.rocketmq.OrderlyConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("mQService")
@Slf4j
public class MQServiceImpl {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private OrderlyConsumerConfig orderlyConsumerConfig;

    @Autowired
    private ConcurrentlyConsumerConfig concurrentlyConsumerConfig;

    public String sendMQMsg (){
        for (int i = 0; i < 50; i++) {
            //生成50个订单,这里订单号就用uuid来代替了
            String orderId = UUID.randomUUID().toString();
            // 每个订单有4个步骤,比如说 下单 支付 确认收货 ,评价 ,每个步骤都会发送一个消息过去,并且这个消息不允许顺序乱,也就是 不能 支付在下单之前过来
            for (int j = 1; j <= 4; j++) {
                try {
                    Message msg =new Message(orderlyConsumerConfig.getTopicName(), orderlyConsumerConfig.getTag(), "KEY" + orderId,
                            ("订单Id:" + orderId + " 步骤:" + j).getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = defaultMQProducer.send(msg, new SelectMessageQueueByHash(), orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("消息发送失败,orderId:{}",orderId);
                }
            }
        }
        return "success";
    }

    public String sendRandomMQMsg (){
        for (int i = 0; i < 50; i++) {
            //生成50个订单,这里订单号就用uuid来代替了
            String orderId = UUID.randomUUID().toString();
            // 每个订单有4个步骤,比如说 下单 支付 确认收货 ,评价 ,每个步骤都会发送一个消息过去,并且这个消息不允许顺序乱,也就是 不能 支付在下单之前过来
            for (int j = 1; j <= 4; j++) {
                try {
                    Message msg =new Message(concurrentlyConsumerConfig.getTopicName(), concurrentlyConsumerConfig.getTag(), "KEY" + orderId,
                            ("订单Id:" + orderId + " 步骤:" + j).getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = defaultMQProducer.send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("消息发送失败,orderId:{}",orderId);
                }
            }
        }
        return "success";
    }

}
