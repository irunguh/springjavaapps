package com.amkatek.rabbitmq;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    //constructor
    public Runner(Receiver receiver,RabbitTemplate rabbitTemplate){
        this.receiver = receiver ;
        this.rabbitTemplate = rabbitTemplate ;
    }
    @Override
    public void run(String ... args) throws Exception{
        System.out.println("Sending message ....");
        rabbitTemplate.convertAndSend(MessagingRabbitApplication.topicExchangeName,"amka.tek.rabbitmq","Send this message!");
        receiver.getLatch().await(10000,TimeUnit.MILLISECONDS);
    }
}