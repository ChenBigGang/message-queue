package site.twofish.messagequeue.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import site.twofish.messagequeue.redis.listener.RedisSubscriber;
import site.twofish.messagequeue.redis.service.RedisMQPublisherService;
import site.twofish.messagequeue.redis.service.RedisMQSubscriberService;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@Service
public class RedisMQSubscriberServiceImpl implements RedisMQSubscriberService {
    @Autowired
    RedisProperties redisProperties;

    @Override
    public void beginListen(String channel) {
        Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());
        new Thread(new Runnable() {
            @Override
            public void run() {
                jedis.subscribe(new RedisSubscriber(), channel);
            }
        }).start();

    }
}
