package com.zhr.rabbitmqspringboot.Component;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MsgTtlQueueConfig {
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    public static final String QUEUE_C = "C";

    // 声明死信队列
    @Bean("queueC")
    public Queue queueB() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", "YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(args).build();
    }

    // 申明队列绑定X交换机
    @Bean
    public Binding queueBindingX(@Qualifier("queueC") Queue queue,
                                 @Qualifier("xExchange") DirectExchange directExchange) {

        return BindingBuilder.bind(queue).to(directExchange).with("XC");
    }
}
