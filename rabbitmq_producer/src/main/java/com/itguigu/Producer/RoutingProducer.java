package com.itguigu.Producer;

import com.itguigu.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由模式
 * 在编码上与 Publish/Subscribe发布与订阅模式 的区别是交换机的类型为：Direct，
 * 还有队列绑定交换机的时候需要指定routing key。
 */
public class RoutingProducer {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
         /*
       exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
       参数：
        1. exchange：交换机名称
        2. type：交换机类型
            DIRECT("direct"),：定向
            FANOUT("fanout"),：扇形（广播）,发送消息到每一个与之绑定队列。
            TOPIC("topic"),通配符的方式
            HEADERS("headers");参数匹配
        3. durable：是否持久化
        4. autoDelete：自动删除
        5. internal：内部使用。 一般false
        6. arguments：参数
        */

        //交换机名称
        String exchangeName = "test_routing";
        //创建交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,false,null);
        //创建队列
        String queue_name1 = "queue1_Routing";
        String queue_name2 = "queue2_Routing";

        channel.queueDeclare(queue_name1,true,false,false,null);
        channel.queueDeclare(queue_name2,true,false,false,null);

        //7. 绑定队列和交换机
        /*
        queueBind(String queue, String exchange, String routingKey)
        参数：
            1. queue：队列名称
            2. exchange：交换机名称
            3. routingKey：路由键,绑定规则
                如果交换机的类型为fanout ,routingKey设置为""
         */
        channel.queueBind(queue_name1, exchangeName, "error");
        channel.queueBind(queue_name2, exchangeName, "info");
        channel.queueBind(queue_name2, exchangeName, "error");
        channel.queueBind(queue_name2, exchangeName, "warning");

        String body = "日志信息：张三调用了findAll方法...日志级别：info...";
        channel.basicPublish(exchangeName, "warning", null, body.getBytes());
        ConnectionUtil.ConnectionClose(channel,connection);

    }
}
