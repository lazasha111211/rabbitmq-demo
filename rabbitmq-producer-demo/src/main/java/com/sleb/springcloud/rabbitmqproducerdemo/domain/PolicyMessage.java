package com.sleb.springcloud.rabbitmqproducerdemo.domain;

import lombok.*;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2020/1/7  10:12
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class PolicyMessage implements Serializable {
    //保单号
    private String policyno;
    //发送时间，取System.currentTimeMillis()
    private Long sendTime;

}
