package com.rabbitmq;

import com.rabbitmq.bean.Student;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;

@SpringBootTest
class RabbitmqDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    RabbitTemplate rabbitTemplate;


    //点对点direct  需要具体 routingKey
    //路由：转换器 -> 路由 -> queue
    @Test
    public void ptp(){
        rabbitTemplate.convertAndSend("directExchange","py.newss","987654");
    }
    @Test
    public void receive1(){
        String message = (String) rabbitTemplate.receiveAndConvert("py");
        System.out.println(message);
    }


    //广播fanout  不需要路由 发送转换器下全体queue
    @Test
    public void guangbo(){
        rabbitTemplate.convertAndSend("fanoutExchange","","123456");

    }
    @Test
    public void receive2(){
        String s = (String) rabbitTemplate.receiveAndConvert("py");
        String s1 = (String) rabbitTemplate.receiveAndConvert("py.news");
        String s2 = (String) rabbitTemplate.receiveAndConvert("py.emps");
        System.out.println(s+" "+s1+" "+s2);
    }
    //订阅 topic
    @Test
    public void dinyue(){
        Student student = new Student("强月城",22 );
        rabbitTemplate.convertAndSend("topicExchange","py.#",student);
    }
    @Test
    public void receive3(){
        Student student = (Student) rabbitTemplate.receiveAndConvert("py.emps");
        System.out.println(student);
    }

    //创建Exchange(转换器)  queue(队) Binding(绑定)
    @Autowired
    AmqpAdmin amqpAdmin;
    @Test
    public void createExchange(){
        amqpAdmin.declareExchange(new DirectExchange("directExchange",true,false));
        System.out.println("创建完成");
    }
    @Test
    public void createFanout(){
        amqpAdmin.declareExchange(new FanoutExchange("fanoutExchange",true,false));
        System.out.println("创建完成");
    }
    @Test
    public void createTopic(){
        amqpAdmin.declareExchange(new TopicExchange("topicExchange",true,false));
        System.out.println("创建完成");
    }

    //创建队列
    @Test void createQueue(){
        //drable 持久化 默认不自动删除
        amqpAdmin.declareQueue(new Queue("py.emps.qyc",true));
    }
    //创建绑定  topic
    @Test void createBinding(){
        amqpAdmin.declareBinding(new Binding("py.news", Binding.DestinationType.QUEUE,
                "topicExchange","py.*",null));
        amqpAdmin.declareBinding(new Binding("py.emps", Binding.DestinationType.QUEUE,
                "topicExchange","py.*",null));
        amqpAdmin.declareBinding(new Binding("py.emps.qyc", Binding.DestinationType.QUEUE,
                "topicExchange","py.emps.*",null));
        amqpAdmin.declareBinding(new Binding("py.emps.qyc", Binding.DestinationType.QUEUE,
                "topicExchange","py.#",null));
    }

}
