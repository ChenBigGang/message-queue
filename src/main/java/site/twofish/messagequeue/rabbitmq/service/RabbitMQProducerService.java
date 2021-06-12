package site.twofish.messagequeue.rabbitmq.service;

/**
 * @author ganggang
 * @since 2021/06/12
 */
public interface RabbitMQProducerService {

    void sendFanoutMessage(String message);

    void sendTopicMessage();
}
