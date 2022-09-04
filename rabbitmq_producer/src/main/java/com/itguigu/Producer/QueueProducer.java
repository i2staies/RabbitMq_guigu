package com.itguigu.Producer;

import com.itguigu.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueProducer {
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();

        String QUEUE_NAME = "queue_name";

        // 声明（创建）队列
        /**
         * queue      参数1：队列名称
         * durable    参数2：是否定义持久化队列,当mq重启之后,还在
         * exclusive  参数3：是否独占本次连接
         *            ① 是否独占,只能有一个消费者监听这个队列
         *            ② 当connection关闭时,是否删除队列
         * autoDelete 参数4：是否在不使用的时候自动删除队列,当没有consumer时,自动删除
         * arguments  参数5：队列其它参数
         */
        channel.queueDeclare(QUEUE_NAME,true,false, false, null);

        /**
         * ToDo: 参数1：交换机名称,如果没有指定则使用默认Default Exchage
         * ToDo: 默认的交换机为direct模式的交换机  且routingKey为simple_queue
         *
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：配置信息
         * 参数4：消息内容
         */
        for (int i = 1; i <= 10; i++) {
            String body = i+"hello rabbitmq~~~";
            channel.basicPublish("",QUEUE_NAME,null,body.getBytes());
        }

        //关闭资源
        ConnectionUtil.ConnectionClose(channel, connection);
    }
}
