package com.ilnur.BenderWeatherAssistBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BenderBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenderBotApplication.class, args);
                
	}
}
