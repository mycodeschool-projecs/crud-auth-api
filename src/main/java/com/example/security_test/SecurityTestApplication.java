package com.example.security_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class SecurityTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityTestApplication.class, args);
	}

}
