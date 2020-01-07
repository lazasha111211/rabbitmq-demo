package com.sleb.springcloud.rabbitmqproducerdemo.util;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/27  13:09
 **/

public class JavaObjectMemory {
    public static void main(String[] args) {
        //Goods goods = new Goods();
        //goods.setName("laza");
        //System.out.println(ClassLayout.parseClass(Goods.class).toPrintable());
        //System.out.println(ClassLayout.parseClass(Goods.class).toPrintable(goods));
        //System.out.println(ClassLayout.parseClass(String.class));
        String str = new String("lazasha");
        List<String> list = new ArrayList<>();
        list.add("lazasha");
        System.out.println(ClassLayout.parseClass(ArrayList.class));
        System.out.println(ClassLayout.parseInstance(list.get(0)).toPrintable());
        System.out.println(ClassLayout.parseInstance(list).toPrintable());
    }
}
