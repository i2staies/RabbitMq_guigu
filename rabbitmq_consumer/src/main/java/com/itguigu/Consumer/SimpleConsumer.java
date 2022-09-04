package com.itguigu.Consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //2. 设置参数
        factory.setHost("192.168.88.131");//ip
        factory.setPort(5672); //端口  默认值 5672
        factory.setVirtualHost("/");//虚拟机 默认值/
        factory.setUsername("admin");//用户名
        factory.setPassword("admin");//密码
        //3. 创建连接 Connection
        Connection connection = factory.newConnection();
        //4. 创建Channel
        Channel channel = connection.createChannel();
        //5. 创建队列(要和生产者一样)
        channel.queueDeclare("simple_queue",true,false, false, null);
        //回调函数
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag："+consumerTag);
                System.out.println("Exchange："+envelope.getExchange());
                System.out.println("RoutingKey："+envelope.getRoutingKey());
                System.out.println("properties："+properties);
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
        channel.basicConsume("simple_queue",true,consumer);
    }
}
