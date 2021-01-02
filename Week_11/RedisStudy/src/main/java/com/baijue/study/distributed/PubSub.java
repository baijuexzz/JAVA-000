package com.baijue.study.distributed;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Redis 发布订阅实现
 * 1.List类型的实现
 */
@Component
@Slf4j
public class PubSub implements ApplicationRunner, StreamListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * redis队列的键名称
     */
    private static final String ORDER_QUEUE_REDISKEY = "order:Queue";

    /**
     * 设置消费组
     */
    private static final String DEFAULT_GROUP = "order";


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer;





    private static ExecutorService executorService = Executors.newFixedThreadPool(2);


    public void testQueue(Integer orderSize)   {
        //销毁 消费组
        redisTemplate.opsForStream().destroyGroup(ORDER_QUEUE_REDISKEY, DEFAULT_GROUP);
        //创建 消费组
        redisTemplate.opsForStream().createGroup(ORDER_QUEUE_REDISKEY, DEFAULT_GROUP);
        log.info("创建消费组为 {}", DEFAULT_GROUP);
        if (null == orderSize || orderSize <= 0) {
            throw new NullPointerException("参数为空");
        }
        produceOrder(orderSize);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    /**
     * 生产订单
     *
     * @param orderSize
     */
    private void produceOrder(Integer orderSize) {
        log.info("初始化订单的数量为{}", orderSize);
        executorService.submit(() -> {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            for (Integer i = 0; i < orderSize; i++) {
                String uuid = UUID.randomUUID().toString();
                stringObjectHashMap.put("orderId", uuid);
                RecordId add = redisTemplate.opsForStream().add(ORDER_QUEUE_REDISKEY, stringObjectHashMap);
                log.info("写入消息队列的订单号为 {} 消息队列存储的ID为 {}", uuid, add.getValue());
            }
        });
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 创建配置对象
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> streamMessageListenerContainerOptions = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                // 一次性最多拉取多少条消息
                .batchSize(10)
                // 消息消费异常的handler
                .errorHandler(new ErrorHandler() {
                    @Override
                    public void handleError(Throwable t) {
                        log.error("消费异常");
                    }
                })
                // 超时时间，设置为0，表示不超时（超时后会抛出异常）
                .pollTimeout(Duration.ZERO)
                // 序列化器
                .serializer(new StringRedisSerializer())
                .build();

        // 根据配置对象创建监听容器对象
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer = StreamMessageListenerContainer
                .create(redisConnectionFactory,streamMessageListenerContainerOptions);

        // 使用监听容器对象开始监听消费（使用的是手动确认方式）
        streamMessageListenerContainer.receive(Consumer.from(DEFAULT_GROUP, "consumer-1"),
                StreamOffset.create(ORDER_QUEUE_REDISKEY, ReadOffset.lastConsumed()), new PubSub());

        this.streamMessageListenerContainer = streamMessageListenerContainer;
        // 启动监听
        this.streamMessageListenerContainer.start();
    }



    @Override
    public void onMessage(Record record) {
        log.info("消费的消息为{}",record.getValue());
    }
}
