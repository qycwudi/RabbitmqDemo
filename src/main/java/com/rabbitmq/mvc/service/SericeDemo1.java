package com.rabbitmq.mvc.service;

import com.rabbitmq.bean.Student;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author qyc
 * @time 2020/4/15 - 15:07
 */
@Service
public class SericeDemo1 {
    @RabbitListener(queues = "py.news")
    public void getMessage(Student student){
        System.out.println("py.news  "+student);
    }
    @RabbitListener(queues = "py.emps")
    public void getMessage1(Student student){
        System.out.println("py.emps  "+student);
    }
    @RabbitListener(queues = "py.emps.qyc")
    public void getMessage4(Student student){
        System.out.println("py.emps.qyc  "+student);
    }

    //获得消息
//    @RabbitListener(queues = "py.emps")
    public void getMessage2(Message message){
        System.out.println("py.emp Body:"+message.getBody());
        System.out.println("py.emp Properties:"+message.getMessageProperties());
    }



}
