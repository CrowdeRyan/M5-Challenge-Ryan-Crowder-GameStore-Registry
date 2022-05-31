package com.company.gamestoreregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GamestoreRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamestoreRegistryApplication.class, args);
	}

}
