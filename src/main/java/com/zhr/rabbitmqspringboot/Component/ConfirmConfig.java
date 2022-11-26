package com.zhr.rabbitmqspringboot.Component;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    public static final String CONFIRM_ROUTINGKEY = "key1";

    // 声明交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    // 声明队列
    @Bean("ConfirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    // 绑定队列和交换机
    @Bean()
    public Binding confirmBinding(@Qualifier("confirmExchange") DirectExchange exchange,
                                  @Qualifier("ConfirmQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTINGKEY);
    }

}