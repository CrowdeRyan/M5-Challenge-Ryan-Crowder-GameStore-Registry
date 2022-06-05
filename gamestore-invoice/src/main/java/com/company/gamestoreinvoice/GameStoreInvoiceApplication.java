package com.company.gamestoreinvoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GameStoreInvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameStoreInvoiceApplication.class, args);
	}

}
