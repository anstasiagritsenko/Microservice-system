package com.itm.space.backendresources;  // Объявление пакета, в котором расположен класс

import org.springframework.boot.SpringApplication;  // Импорт класса SpringApplication из Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;  // Импорт аннотации SpringBootApplication из Spring Boot

@SpringBootApplication  // Аннотация, объявляющая класс как основной Spring Boot приложения
public class BackendResourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendResourcesApplication.class, args);  // Запуск Spring Boot приложения
    }

}
