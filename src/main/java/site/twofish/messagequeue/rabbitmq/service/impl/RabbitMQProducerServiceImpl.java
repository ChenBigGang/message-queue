package site.twofish.messagequeue.rabbitmq.service.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;
import site.twofish.messagequeue.rabbitmq.service.RabbitMQProducerService;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@Service
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {

    public final static String EXCHANGE_NAME = "test_fanout_exchange";

    @Autowired
    RabbitProperties rabbitProperties;

    @Override
    public void sendFanoutMessage(String message) {
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ相关信息
            factory.setHost(rabbitProperties.getHost());
            factory.setPort(rabbitProperties.getPort());
            factory.setUsername(rabbitProperties.getUsername());
            factory.setPassword(rabbitProperties.getPassword());
            //创建一个新的连接
            Connection connection = factory.newConnection();
            //创建一个通道
            Channel channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //发送消息到队列中
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println("发送消息： " + message);
            //关闭通道和连接
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTopicMessage() {
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ相关信息
            factory.setHost(rabbitProperties.getHost());
            factory.setPort(rabbitProperties.getPort());
            factory.setUsername(rabbitProperties.getUsername());
            factory.setPassword(rabbitProperties.getPassword());
            //创建一个新的连接
            Connection connection = factory.newConnection();
            //创建一个通道
            Channel channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare("test_topic_exchange", "topic");
            String[] routing_keys = new String[]{"usa.news", "usa.weather",
                    "europe.news", "europe.weather"};
            String[] messages = new String[]{"美国新闻", "美国天气",
                    "欧洲新闻", "欧洲天气"};
            for (int i = 0; i < routing_keys.length; i++) {
                String routingKey = routing_keys[i];
                String message = messages[i];
                channel.basicPublish("test_topic_exchange", routingKey, null, message
                        .getBytes());
                System.out.printf("发送消息到路由：%s, 内容是: %s%n ", routingKey, message);
            }
            //关闭通道和连接
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
