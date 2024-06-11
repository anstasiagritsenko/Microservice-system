package org.example.config; // Определение пакета

import org.springframework.context.annotation.ComponentScan; // Импорт аннотации ComponentScan
import org.springframework.context.annotation.Configuration; // Импорт аннотации Configuration

@Configuration // Объявление класса как конфигурационного
@ComponentScan // Автоматическое сканирование компонентов в пакетах и их регистрация в контексте Spring

public class RestTemplateConfig { // Объявление класса RestTemplateConfig

}
