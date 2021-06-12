package site.twofish.messagequeue.activemq.service.impl;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.stereotype.Service;
import site.twofish.messagequeue.activemq.service.ActiveProducerService;

import javax.jms.*;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@Service
public class ActiveProducerServiceImpl implements ActiveProducerService {

    @Autowired
    ActiveMQProperties activeMQProperties;

    @Override
    public void sendMessage(String message) {
        //1.创建ConnectionFactory,绑定地址
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                activeMQProperties.getUser(),
                activeMQProperties.getPassword(),
                activeMQProperties.getBrokerUrl());
        try {
            //2.创建Connection
            Connection connection = factory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建会话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个目标 (队列类型)
            Destination destination = session.createQueue("test123");
            //6.创建一个生产者
            MessageProducer producer = session.createProducer(destination);
            //7.创建消息
            TextMessage textMessage = session.createTextMessage(message);
            //8.发送消息
            producer.send(textMessage);
            System.out.println("发送：" + textMessage.getText());
            //7. 关闭连接
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTopicMessage(String message) {
        //1.创建ConnectionFactory,绑定地址
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                activeMQProperties.getUser(),
                activeMQProperties.getPassword(),
                activeMQProperties.getBrokerUrl());
        try {
            //2.创建Connection
            Connection connection = factory.createConnection();
            //3.启动连接
            connection.start();
            //4.创建会话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个目标 (主题类型)
            Destination destination = session.createTopic("testTopic123");
            //6.创建一个生产者
            MessageProducer producer = session.createProducer(destination);
            //7.创建消息
            TextMessage textMessage = session.createTextMessage(message);
            //8.发送消息
            producer.send(textMessage);
            System.out.println("发送：" + textMessage.getText());
            //7. 关闭连接
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
