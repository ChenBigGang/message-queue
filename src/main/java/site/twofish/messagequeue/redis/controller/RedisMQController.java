package site.twofish.messagequeue.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.twofish.messagequeue.redis.service.RedisMQPublisherService;
import site.twofish.messagequeue.redis.service.RedisMQSubscriberService;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@RestController
@RequestMapping(value = "/redismq")
public class RedisMQController {

    @Autowired
    RedisMQPublisherService redisMQPublisherService;
    @Autowired
    RedisMQSubscriberService redisMQSubscriberService;

    @PostMapping(value = "/send")
    public String sendMessage() {
        for (int i = 0; i < 10; i++) {
            redisMQPublisherService.publishMessage("redis_channel1", "hello，第一批消息");
        }
        return "发送成功";
    }

    @GetMapping(value = "/beginListen")
    public String beginListen() {
        redisMQSubscriberService.beginListen("redis_channel1");
        return "开始监听频道成功";
    }

}
