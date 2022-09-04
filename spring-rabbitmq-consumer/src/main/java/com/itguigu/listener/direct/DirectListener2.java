package com.itguigu.listener.direct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class DirectListener2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("C2接收的消息内容："+new String(message.getBody()));
        System.out.println("交换机名称："+message.getMessageProperties().getReceivedExchange());
        System.out.println("路由key：" + message.getMessageProperties().getReceivedRoutingKey());
        System.out.println("队列名：" + message.getMessageProperties().getConsumerQueue());
        System.out.println("消息序号：" + message.getMessageProperties().getDeliveryTag());
    }
}