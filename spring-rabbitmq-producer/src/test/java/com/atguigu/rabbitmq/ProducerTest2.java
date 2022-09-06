package com.atguigu.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq2.xml")

public class ProducerTest2 {
    @Autowired
    private RabbitTemplate rabbitTemplate;



    /**
     * 路由模式
     * 路由模式必须指定路由key
     *
     * 未确认confirm确认机制前
     * 未配置return确认机制前
     */
    @Test
    public void direct(){
        String msg = "spring的整合发送路由模式的消息...";
        //发布订阅模式：不需要路由key，指定为 ""
        //当交换机不存在时也不会自动创建，也不显示错误
        //所以有可能会存在消息在发往交换机阶段出现错误
        //rabbitTemplate.convertAndSend("spring_direct_exchange2","info",msg);

        //消息丢失，没有匹配到的队列
        rabbitTemplate.convertAndSend("spring_direct_exchange","haha",msg);
    }


    /**
     * 路由模式
     * 路由模式必须指定路由key
     *
     * 配置confirm确认机制后
     */
    @Test
    public void direct2(){
        String msg = "spring的整合发送路由模式的消息...";

        //记得在<rabbit:connection-factory>配置publisher-confirms="true"
        //发送消息前，设置confirmCallback，用于判断消息是否发送到交换机
        //消息发送到交换机不论成功与否，ConfirmCallBack都会执行
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    System.out.println("消息成功发送到交换机"+cause);
                }else {
                    System.out.println("消息发送到交换机失败"+cause);

                    //根据具体业务需求处理
                    //如：数据比较重要时，重试三次，还是失败则记录到数据库中，等待u开发人员介入，人为处理
                }
            }
        });
        //消息丢失 交换机不存在
        rabbitTemplate.convertAndSend("spring_direct_exchange2","info",msg);
    }



    /**
     * 路由模式
     * 路由模式必须指定路由key
     *
     * 配置confirm确认机制后
     */
    @Test
    public void direct3(){
        String msg = "spring的整合发送路由模式的消息...";

        //发送消息前，设置return
        //交换机转发消息给队列失败时，默认丢弃
        //rabbitTemplate.setMandatory(true);则不丢弃，将消息退回给生产者的ReturnCallback
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("错误码："+replyCode);
                System.out.println("错误描述："+replyText);
                System.out.println("交换机名称："+exchange);
                System.out.println("路由key："+routingKey);
            }
        });

        /*设置强制处理，交换机转发消息给队列失败后，将消息退回生产者的ReturnCallback*/
        rabbitTemplate.setMandatory(true);

        //假设没有匹配的队列
        rabbitTemplate.convertAndSend("spring_direct_exchange","haha",msg);
    }




}
