package com.atguigu.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq2.xml")

public class ProducerTest4 {
    @Autowired
    private RabbitTemplate rabbitTemplate;



    /**
        死信队列
     */
    @Test
    public void ttl(){
        String msg = "spring的整合发送路由模式的消息...";

        //消息丢失，没有匹配到的队列
        rabbitTemplate.convertAndSend("spring_normal_exchange","normal_key",msg);
    }

}
