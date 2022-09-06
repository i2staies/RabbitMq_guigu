package com.itguigu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布消息，路由模式
     */
    @Test
    public void start(){
       while(true);
    }

}
