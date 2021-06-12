package site.twofish.messagequeue.activemq.service;

/**
 * @author:ganggang
 * @create:2021-06-12
 * @description:
 **/
public interface ActiveProducerService {

    void sendMessage(String message);


    void sendTopicMessage(String message);

}
