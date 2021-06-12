package site.twofish.messagequeue.redis.listener;

import redis.clients.jedis.JedisPubSub;

/**
 * @author ganggang
 * @since 2021/06/12
 */
public class RedisSubscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("监听到 " + channel + " 频道的消息：" + message);
    }
}
