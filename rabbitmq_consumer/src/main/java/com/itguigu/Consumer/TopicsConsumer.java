package com.itguigu.Consumer;

import com.itguigu.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicsConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        String QUEUE_NAME = "queue2_Topic";

        channel.queueDeclare(QUEUE_NAME,true,false, false, null);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body："+new String(body));
            }
        };


        /*
        basicConsume(String queue, boolean autoAck, Consumer callback)
        参数：
            1. queue：队列名称
            2. autoAck：是否自动确认 ,类似咱们发短信,发送成功会收到一个确认消息
            3. callback：回调对象
         */

        // 消费者类似一个监听程序,主要是用来监听消息
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
