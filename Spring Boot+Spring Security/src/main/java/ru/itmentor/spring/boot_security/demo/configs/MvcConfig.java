package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration; // Импорт класса Configuration из пакета org.springframework.context.annotation
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry; // Импорт класса ViewControllerRegistry из пакета org.springframework.web.servlet.config.annotation
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Импорт интерфейса WebMvcConfigurer из пакета org.springframework.web.servlet.config.annotation

@Configuration // Аннотация, обозначающая, что класс является конфигурационным классом Spring
public class MvcConfig implements WebMvcConfigurer { // Объявление класса MvcConfig, реализующего интерфейс WebMvcConfigurer

    // Метод для добавления контроллеров представлений
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/volunteers/user").setViewName("user"); // Добавление контроллера представления для URL "/user" с именем представления "user"
    }
}
