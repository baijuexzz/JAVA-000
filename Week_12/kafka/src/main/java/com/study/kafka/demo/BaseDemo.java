package com.study.kafka.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 原生操作
 */
@Slf4j
public class BaseDemo {

    /**
     * 主题名
     */
    private static final String KAFKA_TOPIC = "test01";

    /**
     * broker地址
     */
    private static final String BROKER_URL = "127.0.0.1:9092,127.0.0.1:19092,127.0.0.1:29092";

    /**
     * 默认消费组
     */
    private static final String DEFAULT_GROUP = "test01";


    /**
     * 发送次数
     */
    private static final Integer SEND_SIZE = 10000;


    /**
     * 是否开启事务
     */
    private static final boolean OPEN_TRANSACTION = false;


    public static void main(String[] args) {
        try {
            //启动消费者
            ConsumerDemo consumerDemo = new ConsumerDemo();
            new Thread(() -> {
                consumerDemo.consumerMsg();
            }).start();
            //启动生产者
            ProducerDemo producerDemo = new ProducerDemo();
            producerDemo.syncSendMsg();
            //producerDemo.asyncSendMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class ConsumerDemo {

        private Consumer consumer;

        public ConsumerDemo() {
            this.consumer = createConsumer();
        }


        private static Consumer<String, String> createConsumer() {
            // 设置 Producer 的属性
            Properties properties = new Properties();
            properties.put("bootstrap.servers", BROKER_URL); // 设置 Broker 的地址
            properties.put("group.id", DEFAULT_GROUP); // 消费者分组
            properties.put("auto.offset.reset", "earliest"); // 设置消费者分组最初的消费进度为 earliest 。可参考博客 https://blog.csdn.net/lishuangzhe7047/article/details/74530417 理解
            properties.put("enable.auto.commit", true); // 是否自动提交消费进度
            properties.put("auto.commit.interval.ms", "1000"); // 自动提交消费进度频率
            properties.put("key.deserializer", StringDeserializer.class.getName()); // 消息的 key 的反序列化方式
            properties.put("value.deserializer", StringDeserializer.class.getName()); // 消息的 value 的反序列化方式
            return new KafkaConsumer<>(properties);
        }

        private void consumerMsg() {
            //订阅主题
            consumer.subscribe(Arrays.asList(KAFKA_TOPIC));
            // 拉取消息
            while (true) {
                ConsumerRecords records = consumer.poll(Duration.ofSeconds(10));
                records.forEach(new java.util.function.Consumer<ConsumerRecord>() {
                    @Override
                    public void accept(ConsumerRecord record) {
                        log.info("消费的topic为{} 消费的offset为{} 消费的内容为{}", record.topic(), record.offset(), record.value());
                    }
                });
            }
        }
    }


    public static class ProducerDemo {

        private Producer producer;

        public ProducerDemo() {
            this.producer = createProducer();
            if (OPEN_TRANSACTION) {
                producer.initTransactions();
                producer.beginTransaction();
            }
        }

        private static Producer<String, String> createProducer() {
            Properties properties = new Properties();
            //设置Broker 的地址
            properties.put("bootstrap.servers", BROKER_URL);
            properties.put("acks", "all");
            //消息的序列化方式
            properties.put("key.serializer", StringSerializer.class.getName());
            properties.put("value.serializer", StringSerializer.class.getName());
            if (OPEN_TRANSACTION) {
                //设置的事务ID为
                properties.put("transactional.id", "tx00");
            }
            return new KafkaProducer<>(properties);
        }

        /**
         * 同步发送消息
         */
        public void syncSendMsg() throws ExecutionException, InterruptedException {
            log.info("同步发送消息");
            for (int i = 0; i < SEND_SIZE; i++) {
                ProducerRecord<String, String> message = new ProducerRecord<>(KAFKA_TOPIC, UUID.randomUUID().toString());
                Future<RecordMetadata> sendResultFuture = producer.send(message);
                RecordMetadata result = sendResultFuture.get();
                log.info("发送的topic为{} 发送的分区为{} 最终的偏移量为{}", result.topic(), result.partition(), result.offset());
            }
        }


        /**
         * 异步发送消息
         */
        public void asyncSendMsg() throws InterruptedException {
            log.info("异步发送消息");
            try {
                for (int i = 0; i < SEND_SIZE; i++) {
                    ProducerRecord<String, String> message = new ProducerRecord<>(KAFKA_TOPIC, UUID.randomUUID().toString());
                    producer.send(message, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            log.info("接收到回调信息");
                            if (null != e) {
                                producer.abortTransaction();
                                log.error("回调的Kafka事务异常为{}", e);
                                log.error("失败的数据为{}", message.value());
                            }
                        }
                    });
                }
                producer.commitTransaction();
            } catch (Exception exception) {
                producer.abortTransaction();
                log.error("写入Kafka数据失败 {}",exception);
            }
            // producer.commitTransaction();
            Thread.sleep(30000);
        }
    }


}
