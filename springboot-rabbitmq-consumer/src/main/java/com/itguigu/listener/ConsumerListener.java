package com.itguigu.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ConsumerListener {
    @RabbitListener(queues = "boot_direct_queue")
    public void getMessage(Message message){
        System.out.println("C1接收的消息内容："+new String(message.getBody()));
        System.out.println("交换机名称："+message.getMessageProperties().getReceivedExchange());
        System.out.println("路由key：" + message.getMessageProperties().getReceivedRoutingKey());
        System.out.println("队列名：" + message.getMessageProperties().getConsumerQueue());
        System.out.println("消息序号：" + message.getMessageProperties().getDeliveryTag());
    }
}
