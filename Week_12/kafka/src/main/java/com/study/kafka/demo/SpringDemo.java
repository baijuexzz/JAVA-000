package com.study.kafka.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Spring 操作kafka Demo
 */
@Component
@Slf4j
public class SpringDemo {


    private static final String DEFAULT_TOPIC="test";

    private static final String DEFAULT_GROUP="spring-group";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 同步发送方法
     * 异步发送把future的get去掉就ok
     */
    private void syncSendMsg(){
        String value = UUID.randomUUID().toString();
        try {
            SendResult sendResult = (SendResult) kafkaTemplate.send(DEFAULT_TOPIC, UUID.randomUUID().toString()).get();
            log.info("本次写入的topic为{} 写入的分区为{} 对应的offset为{} 写入的消息为{}",sendResult.getProducerRecord().topic(),sendResult.getRecordMetadata().partition(),
                    sendResult.getRecordMetadata().offset(),sendResult.getProducerRecord().value());
        } catch (Exception e) {
            log.error("写入kafka数据失败 数据为{},异常原因为{}",value,e);
        }
    }


    /**
     * 消费kafka
     * @param consumerRecord
     */
    @KafkaListener(groupId =DEFAULT_GROUP,topicPartitions = {@TopicPartition(topic = DEFAULT_TOPIC,partitions = {"0"})})
    private void consumerMsg(ConsumerRecord consumerRecord){
        log.info("消费的topic为{} 消费的分区为{},消费的offset为{},消费的数据为{}",consumerRecord.topic(),consumerRecord.partition(),consumerRecord.offset(),consumerRecord.value());
    }


    public void testSpringKafka(Integer sendSize){
        for (int i = 0; i <sendSize ; i++) {
            syncSendMsg();
        }
    }
}
