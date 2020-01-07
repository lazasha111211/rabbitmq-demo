package com.sleb.springcloud.rabbitmqproducerdemo.service;

import com.sleb.springcloud.rabbitmqproducerdemo.domain.PolicyDataEx;
import com.sleb.springcloud.rabbitmqproducerdemo.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description: 如果消息没有到exchange,则confirm回调,ack=false
 *               如果消息到达exchange,则confirm回调,ack=true
 * @author: lazasha
 * @date: 2019/12/24  13:28
 **/
@Service
public class AcceptConfirmCallback implements RabbitTemplate.ConfirmCallback {
    private final Logger logger = LoggerFactory.getLogger(AcceptConfirmCallback.class);
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        PolicyDataEx dataEx = (PolicyDataEx) correlationData;
        assert dataEx != null;
        String policyno = dataEx.getId();
        if (b) {
            logger.info("消息发送成功:\n"
                    + "返回时间: " + DateUtils.localDateTimeToString(LocalDateTime.now())
                    + ",保单号: " + policyno
                    + ",发送消息: " + dataEx.getMessage());

            /*
             * 通过设置correlationData.id为业务主键，消息发送成功后去继续做候选业务。
             * String policyno = dateEx.getId();
             * 根据需要，进行其他业务处理;没有需要处理的就不处理了，
             */
        } else {

            logger.info("消息发送失败,未路由到交换机:\n"
                    + "返回时间: " + DateUtils.localDateTimeToString(LocalDateTime.now())
                    + ",发送消息: " + dataEx.getMessage()
                    + ",保单号: " + policyno
                    + ",失败原因: " + s);
            System.out.println("HelloSender消息发送失败" + s);
            //更新数据库，让批处理来处理为发送成功的消息
            //发送邮件，提升该保单发送mq不成功
        }
    }
}
