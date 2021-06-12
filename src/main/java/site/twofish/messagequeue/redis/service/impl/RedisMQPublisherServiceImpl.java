package site.twofish.messagequeue.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import site.twofish.messagequeue.redis.service.RedisMQPublisherService;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@Service
public class RedisMQPublisherServiceImpl implements RedisMQPublisherService {

    @Autowired
    RedisProperties redisProperties;

    @Override
    public void publishMessage(String channel, String message) {
        Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());
        System.out.println("向" + channel + "频道发送消息：" + message);
        jedis.publish(channel, message);
        jedis.close();
    }
}
