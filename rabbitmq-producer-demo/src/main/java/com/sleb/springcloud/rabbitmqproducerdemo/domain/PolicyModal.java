package com.sleb.springcloud.rabbitmqproducerdemo.domain;

import lombok.*;

import java.io.Serializable;

/**
 * @description: 要发送的消息体
 * @author: lazasha
 * @date: 2019/12/23  16:49
 **/

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class PolicyModal implements Serializable {
    private String policyNo;
    private String name;
    private int age;
    private int sex;
    private Long sendtime;
}
