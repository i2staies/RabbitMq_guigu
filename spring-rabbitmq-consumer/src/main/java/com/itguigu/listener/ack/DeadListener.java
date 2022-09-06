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
public class DeadListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("消息内容：" + new String(message.getBody()));

        /*确认消息*/
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}