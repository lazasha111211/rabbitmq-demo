package com.sleb.springcloud.rabbitmqproducerdemo.service;

import com.sleb.springcloud.rabbitmqproducerdemo.config.RabbitConfig;
import com.sleb.springcloud.rabbitmqproducerdemo.domain.PolicyDataEx;
import com.sleb.springcloud.rabbitmqproducerdemo.domain.PolicyModal;
import com.sleb.springcloud.rabbitmqproducerdemo.util.DateUtils;
import com.sleb.springcloud.rabbitmqproducerdemo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description: 消息发送服务
 * @author: lazasha
 * @date: 2019/12/23  16:50
 **/
@Service
public class AcceptProducerServiceImpl implements AcceptProducerService {
    private final Logger logger = LoggerFactory.getLogger(AcceptProducerServiceImpl.class);


    private final RabbitTemplate rabbitTemplate;

    public AcceptProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(PolicyModal policyModal) {
        logger.info("开始发送时间: " + DateUtils.localDateTimeToString(LocalDateTime.now())
                + ",保单号: " + policyModal.getPolicyNo()
                + ",发送内容: " + policyModal.toString());
        /*
         * policyDataEx继承CorrelationData, 把需要发送消息的关键字段加入
         * 这样confirmcallback可以返回带有关键字段的correlationData,我们可以通过这个来确定发送的是那条业务记录
         * policyno为唯一的值
         */
        policyModal.setSendtime(System.currentTimeMillis());
        PolicyDataEx policyDataEx = new PolicyDataEx();
        policyDataEx.setId(policyModal.getPolicyNo());
        policyDataEx.setMessage(policyModal.toString());
        policyDataEx.setSendtime(System.currentTimeMillis());

        /*
         * 加上这个,可以从returncallback参数中读取发送的json消息，否则是二进制bytes
         * 比如：如果returncallback触发，则表明消息没有投递到队列，则继续业务操作，比如将消息记录标志位未投递成功，记录投递次数
         */
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //PolicyModal类的全限名称（包名+类名）会带入到mq, 所以消费者服务一边必须有同样全限名称的类，否则接收会失败。

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_SLEB_ACCEPT, RabbitConfig.ROUTING_KEY_ACCEPT, policyModal, policyDataEx);

    }
}
