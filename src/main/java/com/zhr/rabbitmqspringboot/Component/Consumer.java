package com.zhr.rabbitmqspringboot.Component;

import com.zhr.rabbitmqspringboot.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Delayed;

/**
 *  接受消息
 */
@Component
@Slf4j
public class Consumer {
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveMessage(Message message) {
        String msg = new String (message.getBody(),StandardCharsets.UTF_8);
        log.info("接受到队列confirm.queue的消息：{}",msg);
    }

}