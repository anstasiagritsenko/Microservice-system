package com.itm.space.backendresources.exception;  // Объявление пакета, в котором расположен класс

import lombok.Getter;  // Импорт аннотации Getter из Lombok для генерации геттеров
import org.springframework.http.HttpStatus;  // Импорт класса HttpStatus из Spring Framework

@Getter  // Аннотация Lombok для генерации геттеров для всех полей класса
public class BackendResourcesException extends RuntimeException {  // Объявление класса BackendResourcesException, наследующего RuntimeException

    private final HttpStatus httpStatus;  // Приватное поле для хранения HTTP статуса исключения

    public BackendResourcesException(String message, HttpStatus httpStatus) {  // Конструктор класса с параметрами
        super(message);  // Вызов конструктора родительского класса с передачей сообщения
        this.httpStatus = httpStatus;  // Инициализация поля httpStatus значением, переданным в конструкторе
    }
}
