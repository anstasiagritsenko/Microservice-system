package com.itm.space.backendresources.controller;

import com.itm.space.backendresources.exception.BackendResourcesException;  // Импорт пользовательского исключения BackendResourcesException
import org.springframework.http.HttpStatus;  // Импорт класса HttpStatus из Spring Framework
import org.springframework.http.ResponseEntity;  // Импорт класса ResponseEntity из Spring Framework
import org.springframework.web.bind.MethodArgumentNotValidException;  // Импорт класса MethodArgumentNotValidException из Spring Framework
import org.springframework.web.bind.annotation.ExceptionHandler;  // Импорт аннотации ExceptionHandler из Spring Framework
import org.springframework.web.bind.annotation.ResponseStatus;  // Импорт аннотации ResponseStatus из Spring Framework
import org.springframework.web.bind.annotation.RestControllerAdvice;  // Импорт аннотации RestControllerAdvice из Spring Framework

import javax.ws.rs.WebApplicationException;  // Импорт класса WebApplicationException из JAX-RS API
import java.util.HashMap;  // Импорт класса HashMap из Java Collections Framework
import java.util.Map;  // Импорт интерфейса Map из Java Collections Framework

@RestControllerAdvice  // Объявление класса как глобального обработчика исключений для контроллеров REST
public class RestExceptionHandler {

    @ExceptionHandler(BackendResourcesException.class)  // Обработка исключения BackendResourcesException
    public ResponseEntity<String> handleException(BackendResourcesException backendResourcesException) {
        return new ResponseEntity<>(backendResourcesException.getMessage(), backendResourcesException.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Установка HTTP статуса BAD_REQUEST (400) для исключения MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)  // Обработка исключения MethodArgumentNotValidException
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();  // Создание карты для хранения ошибок
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));  // Добавление ошибок в карту
        return errorMap;  // Возврат карты ошибок
    }

    @ExceptionHandler(WebApplicationException.class)  // Обработка исключения WebApplicationException
    public ResponseEntity<String> handleWebApplicationException(WebApplicationException ex) {
        return ResponseEntity.status(ex.getResponse().getStatus())  // Установка HTTP статуса из исключения
                .body(ex.getMessage());  // Установка тела ответа из сообщения исключения
    }
}
