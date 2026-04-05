package com.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineBankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBankingBackendApplication.class, args);
	}

}
