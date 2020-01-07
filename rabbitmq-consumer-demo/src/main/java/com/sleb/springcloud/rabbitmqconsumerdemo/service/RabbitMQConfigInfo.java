package com.sleb.springcloud.rabbitmqconsumerdemo.service;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/24  13:10
 **/

public class RabbitMQConfigInfo {
    /**
     * 投保消息队列
     */
    public static final String QUEUE_SLEB_ACCEPT = "queue_sleb_accept";
    /**
     * 投保消息交换机的名字
     */
    public static final String EXCHANGE_SLEB_ACCEPT = "exchange_sleb_accept";

    /**
     * 投保消息路由键
     */
    public static final String ROUTING_KEY_ACCEPT = "routing_key_accept";
}
