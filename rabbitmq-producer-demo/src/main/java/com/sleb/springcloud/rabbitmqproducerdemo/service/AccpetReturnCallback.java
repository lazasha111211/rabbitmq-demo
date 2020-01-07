package com.sleb.springcloud.rabbitmqproducerdemo.service;

import com.sleb.springcloud.rabbitmqproducerdemo.domain.PolicyModal;
import com.sleb.springcloud.rabbitmqproducerdemo.service.AcceptConfirmCallback;
import com.sleb.springcloud.rabbitmqproducerdemo.util.DateUtils;
import com.sleb.springcloud.rabbitmqproducerdemo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/24  13:28
 **/
@Service
public class AccpetReturnCallback implements RabbitTemplate.ReturnCallback {
    private final Logger logger = LoggerFactory.getLogger(AcceptConfirmCallback.class);
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        // exchange到queue成功,则不回调return
        // exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
        String jsonStr = new String(message.getBody());
        PolicyModal policyModal = JsonUtils.JSON2Object(jsonStr, PolicyModal.class);
        logger.error("消息发送失败,未发送到指定队列:\n"
                + "返回时间: " + DateUtils.localDateTimeToString(LocalDateTime.now())
                + ",Message: " + jsonStr
                + ",replyCode: " + i
                + ",replyText: " + s  //错误原因
                + ",exchange: " + s1
                + ",routingKey: " + s2);//queue名称
        //从message获得policyno,需要将message转换为PolicyModal,
        //String policyno = policyModal.getPolicyno();

        //可以更新数据库标志位推送mq不成功，让批处理来处理为发送成功的消息
        //发送邮件通知消息发送不成功，已经转为批处理
    }
}
