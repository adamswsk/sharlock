package com.ucard.sharlock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.ucard.sharlock.tags.mapper")
@EnableSwagger2
@EnableFeignClients
public class SharlockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharlockApplication.class, args);
	}

}
