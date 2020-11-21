package m1_simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Administrator
 * 2020/11/8 - 17:32
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.64.140");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
//        factory.setVirtualHost("/weizhongxin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(
                "Hello World",
                false,
                false,
                false,
                null
        );
        // 创建 处理消息 的回调对象
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                byte[] a = message.getBody();
                String msg = new String(a);
                System.out.println("收到" + msg);
            }
        };
        // 创建 取消接收消息 的回调对象
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };
        // 消费、接收消息
        channel.basicConsume(
                "Hello World",
                true,
                deliverCallback,
                cancelCallback
        );
    }
}
