package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public WebClient webClient(){
		return WebClient.builder()
				.filter((request, next) -> next.exchange(request))
				.build();
	}
}
