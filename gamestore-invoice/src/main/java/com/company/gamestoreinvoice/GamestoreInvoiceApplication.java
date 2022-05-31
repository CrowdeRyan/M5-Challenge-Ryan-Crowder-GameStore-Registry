package com.company.gamestoreinvoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GamestoreInvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamestoreInvoiceApplication.class, args);
	}

}
