package com.study.kafka;

import com.study.kafka.demo.SpringDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaStudyApplicationTests {

	@Autowired
	private SpringDemo springDemo;

	@Test
	void testKafka() {
		springDemo.testSpringKafka(1000);
	}

}
