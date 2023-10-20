package com.gabriel.desafiopicpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioPicPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioPicPayApplication.class, args);
	}

}
