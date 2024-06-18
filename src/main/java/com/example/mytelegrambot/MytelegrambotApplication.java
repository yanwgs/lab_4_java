package com.example.mytelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class MytelegrambotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MytelegrambotApplication.class, args);
	}

}
