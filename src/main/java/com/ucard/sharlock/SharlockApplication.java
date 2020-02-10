package com.ucard.sharlock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ucard.sharlock.tags.mapper")
public class SharlockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharlockApplication.class, args);
	}

}
