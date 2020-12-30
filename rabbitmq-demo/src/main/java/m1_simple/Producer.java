package m1_simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Administrator
 * 2020/11/8 - 17:09
 * 生产者
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.连接---连接，通道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.64.140");
        factory.setPort(5672);
        factory.setUsername("weizhongxin");
        factory.setPassword("123456");
//        factory.setVirtualHost("/weizhongxin");
        /*
         * 与rabbitmq服务器建立连接,
         * rabbitmq服务器端使用的是nio,会复用tcp连接,
         * 并开辟多个信道与客户端通信
         * 以减轻服务器端建立连接的开销
         */
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 2.定义队列
        // 告诉服务器我们要使用的队列是哪个队列
        // 如果存在，直接使用；如果不存在mq服务器会新建队列
        /*
         * 声明队列,会在rabbitmq中创建一个队列
         * 如果已经创建过该队列，就不能再使用其他参数来创建
         *
         * 参数含义:
         *   -queue: 队列名称
         *   -durable: 队列持久化,true表示RabbitMQ重启后队列仍存在
         *   -exclusive: 排他,true表示限制仅当前连接可用
         *   -autoDelete: 当最后一个消费者断开后,是否删除队列
         *   -arguments: 其他参数
         */
        channel.queueDeclare(
                "Hello World",
                false,
                false,
                false,
                null
        );

        // 3.发送消息
        /*
         * 发布消息
         * 这里把消息向默认交换机发送.
         * 默认交换机隐含与所有队列绑定,routing key即为队列名称
         *
         * 参数含义:
         * 	-exchange: 交换机名称,空串表示默认交换机"(AMQP default)",不能用 null
         * 	-routingKey: 对于默认交换机,路由键就是目标队列名称
         * 	-props: 其他参数,例如头信息
         * 	-body: 消息内容byte[]数组
         */
        channel.basicPublish(
                "",
                "Hello World",
                null,
                "Hello World".getBytes()
        );
        System.out.println("消息已发送！");

        // 4.断开连接
        channel.close();
        connection.close();
    }
}
