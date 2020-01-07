package com.sleb.springcloud.rabbitmqproducerdemo.domain;

import lombok.*;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/24  10:29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
//PolicyModal类的全限名称（包名+类名）会带入到mq, 所以消费者服务一边必须有同样全限名称的类，否则接收会失败。
public class PolicyModal implements Serializable {
    private String policyNo;
    private String name;
    private int age;
    private int sex;
    private Long sendtime;


}