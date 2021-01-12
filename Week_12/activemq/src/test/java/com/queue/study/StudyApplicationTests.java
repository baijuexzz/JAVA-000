package com.queue.study;

import com.queue.study.activeMq.SpringDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyApplicationTests {

	@Test
	void contextLoads() {
	}



	@Autowired
	private SpringDemo studyActiveMq;

	@Test
	void TestQueue(){
		for (int i = 0; i <200 ; i++) {
			studyActiveMq.sendMessage();
		}
	}

}
