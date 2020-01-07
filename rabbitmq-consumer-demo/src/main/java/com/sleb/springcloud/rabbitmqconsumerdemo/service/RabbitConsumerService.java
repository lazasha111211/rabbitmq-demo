package com.sleb.springcloud.rabbitmqconsumerdemo.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/24  15:54
 **/

public interface RabbitConsumerService {
    public void process(Channel channel, Message message) throws IOException;
}
