package com.itguigu.listener.ack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * 手动确认监听器
 * 手动确认 需要实现的接口ChannelAwareMessageListener
 */
@Component
public class NackListener2 implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("消息内容：" + new String(message.getBody()));

        //拒绝消息确认,requeue=false,不重回消息队列-->丢弃消息到死信队列
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, false);
    }
}
