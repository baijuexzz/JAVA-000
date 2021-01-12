package com.queue.study.activeMq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.remote.JMXConnectorServer;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Spring Demo
 */
@Component
@Slf4j
public class SpringDemo {


    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    private static final String QUEUE_NAME="test1";



    public void sendMessage(){
        String s = UUID.randomUUID().toString();
        jmsMessagingTemplate.convertAndSend(QUEUE_NAME,s);
        log.info("发送的队列为{} 发送的消息为{}",QUEUE_NAME,s);
    }


    @JmsListener(destination = QUEUE_NAME)
    public void reciveMsg(Message msg)  {
        log.info("接收到的消息为 {}",msg);
    }
}
