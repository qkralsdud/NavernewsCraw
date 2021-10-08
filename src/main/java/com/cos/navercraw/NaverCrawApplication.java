package com.cos.navercraw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NaverCrawApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverCrawApplication.class, args);
	}

}
