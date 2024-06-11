package ru.itmentor.spring.boot_security.demo; // Объявление пакета

import org.springframework.boot.SpringApplication; // Импорт класса SpringApplication из пакета org.springframework.boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Импорт аннотации @SpringBootApplication из пакета org.springframework.boot.autoconfigure

@SpringBootApplication // Объявление класса как приложения Spring Boot
public class SpringBootSecurityDemoApplication { // Объявление класса SpringBootSecurityDemoApplication

	public static void main(String[] args) { // Объявление метода main
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args); // Запуск приложения Spring Boot
	}

}
