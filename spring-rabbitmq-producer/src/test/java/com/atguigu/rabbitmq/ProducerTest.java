package com.atguigu.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")

public class ProducerTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 简单模式
     * 简单模式绑定的是默认的交换机，类型为direct，名称为"",路由key为队列名
     */
    @Test
    public void simple(){
        rabbitTemplate.convertAndSend("spring_simple_queue","spring的整合发送的消息...");
    }

    /**
     * 工作队列模式模式
     * 工作队列模式模式绑定的是默认的交换机，类型为direct，名称为"",路由key为队列名
     */
    @Test
    public void work(){
        rabbitTemplate.convertAndSend("spring_simple_queue","spring的整合发送工作队列的消息...");
    }

    /**
     * 发布订阅模式
     */
    @Test
    public void fanout(){
        String msg = "spring的整合发送发布订阅模式的消息...";
        //发布订阅模式：不需要路由key，指定为 ""
        rabbitTemplate.convertAndSend("spring_fanout_exchange","",msg);
    }


    /**
     * 路由模式
     * 路由模式必须指定路由key
     */
    @Test
    public void direct(){
        String msg = "spring的整合发送路由模式的消息...";
        //发布订阅模式：不需要路由key，指定为 ""
        rabbitTemplate.convertAndSend("spring_direct_exchange","info",msg);
    }


    /**
     * 通配符模式
     * 通配符模式必须指定具体路由key
     */
    @Test
    public void topic(){
        String msg = "spring的整合发送通配符模式的消息...";
        //发布订阅模式：不需要路由key，指定为 ""
        rabbitTemplate.convertAndSend("spring_topic_exchange","lazy.com",msg);
    }



}
