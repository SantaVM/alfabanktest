package ru.example.alfabanktest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AlfabanktestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlfabanktestApplication.class, args);
	}

}
