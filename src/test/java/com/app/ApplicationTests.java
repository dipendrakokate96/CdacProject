package com.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.service.impl.EmailSenderService;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
		
			EmailSenderService mess = new  EmailSenderService();
			mess.sendSimpleEmail("nawaleajinkyass@gmail.com", "hi dk", "Mail testing 123");
		   System.out.print("mail sent to Ajinkya ;-)");

	}

}
