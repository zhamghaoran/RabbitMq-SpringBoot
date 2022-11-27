package com.zhr.rabbitmqspringboot.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback {
    @Autowired
    RabbitTemplate rabbitTemplate;
    // 注入
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 交换机确认回调方法
     * 1. 发消息交换机接受到了 回调
     * 2. correlationData 存放了回调消息的ID以及相关信息
     * 3. true 为收到消息了
     * 4. cause在成功的情况下是null值 失败的情况下是失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack == true) {
            log.info("交换机已经收到了消息,id为：{}",correlationData.getId());
        } else {
            log.info("交换机没有收到id为{}消息,错误原因为：{}",correlationData.getId(),cause);
        }

    }
}
