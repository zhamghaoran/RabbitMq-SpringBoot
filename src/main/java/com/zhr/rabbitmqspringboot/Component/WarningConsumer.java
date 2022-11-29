package com.zhr.rabbitmqspringboot.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class WarningConsumer {
    public static final String WARNING_QUEUE_NAME = "warning_queue";

    @RabbitListener(queues = WARNING_QUEUE_NAME)
    public void receiveMessage(Message message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("报警发现不可路由的消息{}",msg);
    }
}
