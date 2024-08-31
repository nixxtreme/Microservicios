package com.micro.micro_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroCrudApplication.class, args);
	}

}
