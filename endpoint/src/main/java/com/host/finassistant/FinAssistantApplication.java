package com.host.finassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.host.finassistant.*")
public class FinAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinAssistantApplication.class, args);
	}

}
