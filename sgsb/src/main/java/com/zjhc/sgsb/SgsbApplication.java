package com.zjhc.sgsb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={ "com.zjhc.sgsb.controller","com.zjhc.sgsb.service"})
@MapperScan(value="com.zjhc.sgsb.mapper")
public class SgsbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgsbApplication.class, args);
	}

}
