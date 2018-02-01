package com.ttp.TradeCoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"TradeCoin"})
public class TradeCoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeCoinApplication.class, args);
	}
}
