package com.example.demo;

import com.example.demo.config.ExceptionResolver.MyExceptionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShiroWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShiroWebApplication.class, args);
	}

	/**
	 * 统一异常处理
	 * @return
	 */
	@Bean
	public MyExceptionResolver myExceptionResolver(){
		return new MyExceptionResolver();
	}
}
