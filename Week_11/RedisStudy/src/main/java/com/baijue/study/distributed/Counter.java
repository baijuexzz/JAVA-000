package com.baijue.study.distributed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分布式计数器，模拟减库存
 * 基于String类型的decr
 */
@Slf4j
@Component
public class Counter {


    /**
     * Redis Key值
     */
    private static final String REDISKEY="counter:sku";


    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 初始化商品库存数量
     * @param number
     */
    public void initSkuNumber(Integer number){
        if (null ==number || number<=0){
            throw new NullPointerException("数量不可为0");
        }
        log.info("初始化商品数量为 {}",number);
        redisTemplate.delete(REDISKEY);
        redisTemplate.opsForValue().set(REDISKEY,number.toString());
    }


    private Long decrSkuNumber(){
        return  redisTemplate.opsForValue().decrement(REDISKEY);
    }


    /**
     * 测试计数器
     * @param threadSize 启动的线程数量
     * @param forNumber  循环的次数
     */
    public void testCounter(Integer threadSize,Integer forNumber){
         ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for (int i = 0; i <threadSize ; i++) {
            executorService.execute(()->{
                try {
                    for (int j = 0; j <forNumber ; j++) {
                        Long skuNumber = decrSkuNumber();
                        log.info("当前商品库存数量 {}",skuNumber);
                        if (skuNumber<=0){
                            log.info("库存不足");
                            return;
                        }
                    }
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
