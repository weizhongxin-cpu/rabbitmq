package m2_work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

/**
 * Administrator
 * 2020/12/30 - 15:32
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.64.140");
        factory.setPort(5672);
        factory.setUsername("weizhongxin");
        factory.setPassword("123456");

        Channel channel = factory.newConnection().createChannel();
        // 定义队列
        channel.queueDeclare(
                "helloworld",
                false,
                false,
                false,
                null
        );
        DeliverCallback deliverCallback = (String consumerTag, Delivery message) -> {
            String msg = new String(message.getBody());
            System.out.println("receive:" + msg);
            for (int i = 0; i < msg.length(); i++) {
                if (msg.charAt(i) == '.') {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            System.out.println("消息处理结束....");
        };
        CancelCallback cancelCallback = (String consumerTag) -> {

        };
        // 消费数据，即接收数据
        channel.basicConsume(
                "helloworld",
                // true自动确认回执，false手动确认回执
                false,
                deliverCallback,
                cancelCallback
        );
    }
}
