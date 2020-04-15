package com.rabbitmq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qyc
 * @time 2020/4/15 - 14:12
 */
public class TestDemo {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm:ss")));
        System.currentTimeMillis();
        System.out.println(localDateTime);
        System.out.println(localDateTime.getNano());
    }
}
