package com.sleb.springcloud.rabbitmqconsumerdemo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/24  10:23
 **/

public class DateUtils {
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");
        return formatter.format(localDateTime);
    }
}
