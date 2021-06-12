package site.twofish.messagequeue.rabbitmq.service;

/**
 * @author ganggang
 * @since 2021/06/12
 */
public interface RabbitMQConsumerService {

    void beginListenFanout();

    void beginListenTopic();
}
