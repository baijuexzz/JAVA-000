package com.baijue.study.distributed;

import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * 分布式锁
 */
@Component
@Slf4j
public class RedisDistributedLock {


    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String REDISKEY = "redis:lock";

    private static final Long EXPIRETIME = 6000L;


    public boolean getLock(String uuid) {
        return redisTemplate.opsForValue().setIfAbsent(REDISKEY, uuid, EXPIRETIME, TimeUnit.MILLISECONDS);
    }


    public boolean deletekey(String uuid) {
        /**
         * 此处涉及两个操作  无法保证两个的统一原子性  应该使用LUA脚本进行解锁
         */
        String value = redisTemplate.opsForValue().get(REDISKEY);
        if (value.equals(uuid)) {
            log.info("删除锁成功");
            return redisTemplate.delete(REDISKEY);
        }
        log.info("解锁失败 解锁的UUID为{}  实际持有的UUID为{}", uuid, redisTemplate.opsForValue().get(REDISKEY));
        return false;
    }

    public void doSomething(Integer threadNumber, Integer forNumer) {
        redisTemplate.delete(REDISKEY);
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        //主线程等待
        CountDownLatch mainCountDownLatch = new CountDownLatch(threadNumber);
        //循环计数 模拟多进程并发争抢
        CyclicBarrier forcyclicBarrier = new CyclicBarrier(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            executorService.submit(() -> {
                try {
                    String uuid = getUUID();
                    for (int j = 0; j < forNumer; j++) {
                        if (forNumer != 0) {
                            try {
                                forcyclicBarrier.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }
                        if (getLock(uuid)) {
                            log.info("获取锁成功 写入值为{}", uuid);
                            log.info("执行业务逻辑");
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            log.info("获取锁失败");
                            continue;
                        }
                        if (deletekey(uuid)) {
                            log.info("解锁成功 解锁的为{}", uuid);
                        }

                    }
                } finally {
                    mainCountDownLatch.countDown();
                }
            });
        }
        try {
            mainCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取UUID 标识持有锁的线程 避免 a线程持有的锁被B线程释放
     *
     * @return
     */
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
