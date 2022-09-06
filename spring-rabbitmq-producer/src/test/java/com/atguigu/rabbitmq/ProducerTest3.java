package com.atguigu.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq2.xml")

public class ProducerTest3 {
    @Autowired
    private RabbitTemplate rabbitTemplate;



    /**
        队伍设置了过期时间
     */
    @Test
    public void ttl(){
        String msg = "spring的整合发送路由模式的消息...";

        //消息丢失，没有匹配到的队列
        rabbitTemplate.convertAndSend("spring_ttl_queue1",msg);
    }

    /**
     队伍设置了过期时间
     */
    @Test
    public void tt2(){
        //创建消息处理器
        MessagePostProcessor processor = new MessagePostProcessor(){
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置消息的过期时间
                message.getMessageProperties().setExpiration("6000");
                return message;
            }
        };

        String msg = "spring的整合发送路由模式的消息...";

        //消息丢失，没有匹配到的队列
        //(Object) msg 为了不匹配到另外的方法
        rabbitTemplate.convertAndSend("spring_ttl_queue1",(Object) msg,processor);
    }




}
