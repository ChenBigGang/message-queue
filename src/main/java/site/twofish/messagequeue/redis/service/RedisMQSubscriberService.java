package site.twofish.messagequeue.redis.service;

/**
 * @author ganggang
 * @since 2021/06/12
 */
public interface RedisMQSubscriberService {

    void beginListen(String channel);
}
