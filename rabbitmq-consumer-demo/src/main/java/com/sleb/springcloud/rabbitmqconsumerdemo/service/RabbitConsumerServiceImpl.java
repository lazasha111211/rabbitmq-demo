package com.sleb.springcloud.rabbitmqconsumerdemo.service;

import com.rabbitmq.client.Channel;
import com.sleb.springcloud.rabbitmqproducerdemo.domain.PolicyModal;
import com.sleb.springcloud.rabbitmqconsumerdemo.util.DateUtils;
import com.sleb.springcloud.rabbitmqconsumerdemo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.sleb.springcloud.rabbitmqconsumerdemo.service.RabbitMQConfigInfo.*;

/**
 * @description: rabbit mq消费者
 * @author: lazasha
 * @date: 2019/12/24  10:20
 **/

@Service
public class RabbitConsumerServiceImpl implements RabbitConsumerService {

    private final Logger logger = LoggerFactory.getLogger(RabbitConsumerServiceImpl.class);

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = QUEUE_SLEB_ACCEPT, durable = "true"),
            exchange = @Exchange(name = EXCHANGE_SLEB_ACCEPT,
                    ignoreDeclarationExceptions = "true"),
            key = {ROUTING_KEY_ACCEPT}
    ))
    @Override
    public void process(Channel channel, Message message) throws IOException {
        String jsonStr = new String(message.getBody());
        logger.info("接收信息时间: " + DateUtils.localDateTimeToString(LocalDateTime.now())
                + "\n,消息：" + jsonStr);
        //PolicyModal类的全限名称（包名+类名）会带入到mq, 所以消费者服务一边必须有同样全限名称的类，否则接收会失败。
        PolicyModal policyModal = JsonUtils.JSON2Object(jsonStr, PolicyModal.class);
        Long time = System.currentTimeMillis();
        logger.info("接收时间：" + time + "; 发送时间:" + policyModal.getSendtime() + "; 消耗时间：" + (time - policyModal.getSendtime()));
        assert policyModal != null;
        try {
            //将message中的body获取出来, 转换为PolicyModal,再获取policyno
            //更根据policyno新数据库里面的标志，
            // todo

            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了
            // 否则消息服务器以为这条消息没处理掉 后续还会在发
            //throw new IOException("myself");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            /*logger.info("接收处理成功：\n"
                    + "接收信息时间: " + DateUtils.localDateTimeToString(LocalDateTime.now())
                    + ",保单号: " + policyModal.getPolicyNo()
                    + "\n,消息：" + new String(message.getBody()));
*/
        } catch (IOException e) {
            e.printStackTrace();
            //丢弃这条消息,则不会重新发送了
            //一般不丢弃，超时后mq自动会转到死信队列（如果设置了超时时间和死信交换机和队列后）
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            logger.info("接收处理失败：\n"
                    + "接收信息时间: " + DateUtils.localDateTimeToString(LocalDateTime.now())
                    + ",保单号: " + policyModal.getPolicyNo()
                    + "\n,消息：" + new String(message.getBody()));
        }
    }

}
