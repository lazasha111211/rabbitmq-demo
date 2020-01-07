package com.sleb.springcloud.rabbitmqproducerdemo.service;

import com.sleb.springcloud.rabbitmqproducerdemo.domain.PolicyModal;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/23  16:46
 **/

public interface AcceptProducerService {
    void sendMessage(PolicyModal policyModal);
}
