package com.itm.space.backend.client;  // Объявление пакета, к которому принадлежит данный класс (com.itm.space.backend.client). Пакеты используются для организации классов и предотвращения конфликтов имен.

import org.springframework.boot.SpringApplication;  // Импорт класса SpringApplication из пакета org.springframework.boot. Класс SpringApplication предоставляет удобный способ инициализации Spring-приложения из метода main.
import org.springframework.boot.autoconfigure.SpringBootApplication;  // Импорт аннотации SpringBootApplication из пакета org.springframework.boot.autoconfigure. Эта аннотация является комбинацией @Configuration, @EnableAutoConfiguration и @ComponentScan, и используется для настройки и автоматической конфигурации Spring Boot-приложений.

@SpringBootApplication  // Аннотация, указывающая, что данный класс является Spring Boot-приложением и активирует автоматическую конфигурацию и сканирование компонентов.
public class BackendGatewayClientApplication {  // Объявление класса BackendGatewayClientApplication

	public static void main(String[] args) {  // Объявление метода main, который является точкой входа приложения
		SpringApplication.run(BackendGatewayClientApplication.class, args);  // Вызов метода run класса SpringApplication для запуска Spring Boot-приложения
	}

}
