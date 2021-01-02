package com.baijue.study;

import com.baijue.study.distributed.Counter;
import com.baijue.study.distributed.Deduplication;
import com.baijue.study.distributed.PubSub;
import com.baijue.study.distributed.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RedisStudyApplicationTests {

	@Autowired
	private Deduplication deduplication;


	/**
	 * 测试ID去重
	 */
	@Test
	public void testDeduplication(){
		for (int i = 0; i <2 ; i++) {
			for (int j = 0; j <30 ; j++) {
				boolean b = deduplication.checkId(new Long(j));
				log.info("本次校验的ID为 {}  ID是否可用{}",j,b);
			}
		}
	}



	@Autowired
	private Counter counter;


	/**
	 * 测试商品库存计数器
	 */
	@Test
	public void testCounter(){
		counter.initSkuNumber(2999);
		counter.testCounter(10,300);
	}



	@Autowired
	private RedisDistributedLock redisDistributedLock;


	/**
	 * 测试分布式锁
	 */
	@Test
	public void testLock(){
		redisDistributedLock.doSomething(10,5);
	}



	@Autowired
	private PubSub pubSub;


	/**
	 * PubSub 测试用例
	 */
	@Test
	public void testQueue(){
		pubSub.testQueue(10000);
	}

}
