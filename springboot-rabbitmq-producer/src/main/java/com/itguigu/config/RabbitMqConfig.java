package com.itguigu.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RabbitMqConfig {

    @Bean
    public Exchange bootExchange(){
        //创建direct类型的交换机--路由模式
        return ExchangeBuilder.directExchange("boot_direct_exchange")      //创建交换机的名称与类型
                .durable(true)  //持久化
                .build();       //构建
    }

    @Bean
    public Queue bootQueue(){
        return QueueBuilder.durable("boot_direct_queue")  //持久化，队列名称
                .build();  //构建
    }

    @Bean
    public Binding bindQueueAndExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){
        return BindingBuilder.bind(queue)  //绑定的队列对象
                .to(exchange)   //绑定的交换机对象
                .with("boot_key")   //路由key
                .noargs();  //不需要其他参数
    }
}
