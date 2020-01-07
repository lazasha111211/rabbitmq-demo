package com.sleb.springcloud.rabbitmqproducerdemo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.amqp.rabbit.connection.CorrelationData;



/**
 * @description: 扩展消息的类
 * @author: lazasha
 * @date: 2019/12/23  16:43
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
public class PolicyDataEx  extends CorrelationData {
    private String message;
    private Long sendtime;
}
