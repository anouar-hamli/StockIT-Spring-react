package com.sofac.StockIT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class StockItApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockItApplication.class, args);
	}

}
