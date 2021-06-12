package site.twofish.messagequeue.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.twofish.messagequeue.rabbitmq.service.RabbitMQConsumerService;
import site.twofish.messagequeue.rabbitmq.service.RabbitMQProducerService;

/**
 * @author ganggang
 * @since 2021/06/12
 */
@RestController
@RequestMapping(value = "/rabbitmq")
public class RabbitMQController {
    @Autowired
    RabbitMQProducerService rabbitMQProducerService;

    @Autowired
    RabbitMQConsumerService rabbitMQConsumerService;

    @PostMapping(value = "/send-fanout-message")
    public String sendFanoutMessage(@RequestBody String message) {
        for (int i = 0; i < 10; i++) {
            rabbitMQProducerService.sendFanoutMessage(message);
        }
        return "发送成功";
    }

    @GetMapping(value = "/beginFanoutListen")
    public String beginFanoutListen() {
        rabbitMQConsumerService.beginListenFanout();
        return "开始监听成功";
    }

    @PostMapping(value = "/send-topic-message")
    public String sendTopicMessage() {
        rabbitMQProducerService.sendTopicMessage();
        return "发送成功";
    }

    @GetMapping(value = "/beginTopicListen")
    public String beginTopicListen() {
        rabbitMQConsumerService.beginListenTopic();
        return "开始监听成功";
    }
}
