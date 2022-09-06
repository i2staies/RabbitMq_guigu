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
public class AckListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("消息内容："+new String(message.getBody()));
        System.out.println("交换机名称："+message.getMessageProperties().getReceivedExchange());
        System.out.println("路由key：" + message.getMessageProperties().getReceivedRoutingKey());
        System.out.println("队列名：" + message.getMessageProperties().getConsumerQueue());
        System.out.println("消息序号：" + message.getMessageProperties().getDeliveryTag());

        //业务处理过程
        try{
            //出现异常
            int i = 1/0;
            //手动确认
            /**
             *  @param deliveryTag  消息序号
             *  @param multiple 是否批量处理
             *
             *  basicAck(5.true)  批量确认消息序号 <= 5 的所有消息
             *  basicAck(5.false)  只确认消息序号 = 5 的所有消息   单独处理
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        }catch (Exception e){
            e.printStackTrace();
            //手动拒绝
            /**
             *  @param deliveryTag 消息序号
             *  @param multiple  是否批量处理（批量拒绝）
             *  @param requeue  是否重回队列，true-消息返回队列 false-丢弃消息
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,true);

            //根据具体业务需求处理
            //如丢弃了三次，还是失败，则记录到数据库中，等待开发者人员人为处理
            //丢弃消息，避免继续循环

        }


    }
}
