package site.twofish.messagequeue.activemq.service.impl;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.stereotype.Service;
import site.twofish.messagequeue.activemq.service.ActiveConsumerService;

import javax.jms.*;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@Service
public class ActiveConsumerServiceImpl implements ActiveConsumerService {

    @Autowired
    ActiveMQProperties activeMQProperties;

    @Override
    public void beginListen() {
        try {
            //1.创建ConnectiongFactory,绑定地址
            ConnectionFactory factory = new ActiveMQConnectionFactory(
                    activeMQProperties.getUser(),
                    activeMQProperties.getPassword(),
                    activeMQProperties.getBrokerUrl()
            );
            //2.创建Connection
            Connection connection = factory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建会话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个目标 （队列类型）
            Destination destination = session.createQueue("test123");
            //6.创建一个消费者
            MessageConsumer consumer1 = session.createConsumer(destination);
            //7.创建一个消费者1
            consumer1.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message arg0) {
                    TextMessage textMessage = (TextMessage) arg0;
                    try {
                        System.out.println("消费者1" + " 接收消息：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //8.创建一个消费者2
            MessageConsumer consumer2 = session.createConsumer(destination);
            consumer2.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message arg0) {
                    TextMessage textMessage = (TextMessage) arg0;
                    try {
                        System.out.println("消费者2" + " 接收消息：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //9. 因为不知道什么时候有，所以没法主动关闭，就不关闭了，一直处于监听状态
            //connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginTopicListen() {
        try {
            //1.创建ConnectiongFactory,绑定地址
            ConnectionFactory factory = new ActiveMQConnectionFactory(
                    activeMQProperties.getUser(),
                    activeMQProperties.getPassword(),
                    activeMQProperties.getBrokerUrl()
            );
            //2.创建Connection
            Connection connection = factory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建会话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个目标 （主题类型）
            Destination destination = session.createTopic("testTopic123");
            //6.创建一个消费者
            MessageConsumer consumer1 = session.createConsumer(destination);
            //7.创建一个消费者1
            consumer1.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message arg0) {
                    TextMessage textMessage = (TextMessage) arg0;
                    try {
                        System.out.println("消费者1" + " 接收消息：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //8.创建一个消费者2
            MessageConsumer consumer2 = session.createConsumer(destination);
            consumer2.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message arg0) {
                    TextMessage textMessage = (TextMessage) arg0;
                    try {
                        System.out.println("消费者2" + " 接收消息：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //9. 因为不知道什么时候有，所以没法主动关闭，就不关闭了，一直处于监听状态
            //connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
