package site.twofish.messagequeue.activemq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.twofish.messagequeue.activemq.service.ActiveConsumerService;
import site.twofish.messagequeue.activemq.service.ActiveProducerService;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@RestController
@RequestMapping(value = "/activemq")
public class ActiveController {

    @Autowired
    ActiveProducerService activeProducerService;

    @Autowired
    ActiveConsumerService activeConsumerService;

    @PostMapping(value = "/send")
    public String sendMessage(@RequestBody String message) {
        for (int i = 0; i < 10; i++) {
            activeProducerService.sendMessage(message);
        }
        return "发送成功";
    }

    @GetMapping(value = "/beginListen")
    public String beginListen() {
        activeConsumerService.beginListen();
        return "开始监听成功";
    }

    @PostMapping(value = "/topic-send")
    public String sendTopicMessage(@RequestBody String message) {
        for (int i = 0; i < 10; i++) {
            activeProducerService.sendTopicMessage(message);
        }
        return "发送成功";
    }

    @GetMapping(value = "/beginTopicListen")
    public String beginTopicListen() {
        activeConsumerService.beginTopicListen();
        return "开始监听主题成功";
    }
}
