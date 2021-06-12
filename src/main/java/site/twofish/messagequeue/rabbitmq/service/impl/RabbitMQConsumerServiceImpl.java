package site.twofish.messagequeue.rabbitmq.service.impl;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;
import site.twofish.messagequeue.rabbitmq.service.RabbitMQConsumerService;

import java.io.IOException;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@Service
public class RabbitMQConsumerServiceImpl implements RabbitMQConsumerService {

    public final static String EXCHANGE_NAME = "test_fanout_exchange";

    @Autowired
    RabbitProperties rabbitProperties;

    @Override
    public void beginListenFanout() {
        try {
            // 创建连接工厂
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
            //交换机声明（参数为：交换机名称；交换机类型）
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //获取一个临时队列
            String queueName = channel.queueDeclare().getQueue();
            //队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("消费者1接收到消息 '" + message + "'");
                }
            };
            //启动消费者，自动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginListenTopic() {
        try {
            // 创建连接工厂
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
            //交换机声明（参数为：交换机名称；交换机类型）
            channel.exchangeDeclare("test_topic_exchange", "topic");
            //获取一个临时队列
            String queueName = channel.queueDeclare().getQueue();
            //将交换机绑定到队列
            channel.queueBind(queueName, "test_topic_exchange", "usa.*");
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("消费者2收到消息 '" + message + "'");
                }
            };
            //自动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
