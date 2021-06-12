package site.twofish.messagequeue.redis.service;

/**
 * @author:ganggang
 * @create:2021-06-12
 * @description:
 **/
public interface RedisMQPublisherService {
    void publishMessage(String channel, String message);
}
