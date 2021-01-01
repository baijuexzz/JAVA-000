package com.baijue.study;

import com.baijue.study.distributed.Deduplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RedisStudyApplicationTests {

	@Autowired
	private Deduplication deduplication;


	@Test
	public void testDeduplication(){
		for (int i = 0; i <2 ; i++) {
			for (int j = 0; j <30 ; j++) {
				boolean b = deduplication.checkId(new Long(j));
				log.info("本次校验的ID为 {}  ID是否可用{}",j,b);
			}
		}
	}

	@Test
	void contextLoads() {
	}

}
