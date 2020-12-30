package m2_work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Administrator
 * 2020/12/29 - 22:40
 */
public class Producer {
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
                // 是否是持久队列
                false,
                // 排他队列、独占队列
                false,
                // 自动删除
                false,
                // 其他属性
                null
        );
        while (true){
            System.out.println("scanning message.......");
            String msg = new Scanner(System.in).nextLine();
            channel.basicPublish(
                    "",
                    "helloworld",
                    null,
                    msg.getBytes()
            );
        }
    }
}
