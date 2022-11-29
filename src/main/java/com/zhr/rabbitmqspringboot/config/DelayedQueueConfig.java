package com.zhr.rabbitmqspringboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedQueueConfig {
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";
    // 备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "back_exchange";
    // 备份队列
    public static final String BACKUP_QUEUE_NAME = "back_queue";
    // 报警队列
    public static final String WARNING_QUEUE_NAME = "warning_queue";

    @Bean
    public Queue delayQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    // 自定义交换机
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        args.put("alternate-exchange",BACKUP_EXCHANGE_NAME);
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingDelayedQueue(@Qualifier("delayQueue") Queue queue,
                                       @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(queue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean("backupQueue")
    public Queue backupQueue() {
        return new Queue(BACKUP_QUEUE_NAME);
    }

    @Bean("warningQueue")
    public Queue warningQueue() {
        return new Queue(WARNING_QUEUE_NAME);
    }

    @Bean
    public Binding backupQueueBingingBackupExchange(@Qualifier("backupExchange") FanoutExchange exchange,
                                                    @Qualifier("backupQueue") Queue backupQueue) {
        return BindingBuilder.bind(backupQueue).to(exchange);
    }

    @Bean
    public Binding warningQueueBindingBackupExchange(@Qualifier("backupExchange") FanoutExchange exchange,
                                                     @Qualifier("warningQueue") Queue warningQueue) {
        return BindingBuilder.bind(warningQueue).to(exchange);
    }
}
