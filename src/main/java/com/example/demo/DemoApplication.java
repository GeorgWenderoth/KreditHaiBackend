package com.example.demo;

import com.example.demo.services.InterestScheduler;
import com.example.demo.services.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		//TransactionService transactionService = new TransactionService();
		//InterestScheduler interestScheduler = new InterestScheduler(transactionService);
		//interestScheduler.startScheduler();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				//interestScheduler.stopScheduler();
			}
		});

	}

}
