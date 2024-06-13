package com.itm.space.backendresources.controller;

import com.itm.space.backendresources.api.request.UserRequest;  // Импорт класса UserRequest из пакета api.request
import com.itm.space.backendresources.api.response.UserResponse;  // Импорт класса UserResponse из пакета api.response
import com.itm.space.backendresources.service.UserService;  // Импорт сервиса UserService из пакета service
import io.swagger.v3.oas.annotations.security.SecurityRequirement;  // Импорт аннотации SecurityRequirement из Swagger для OpenAPI 3
import jakarta.validation.Valid;  // Импорт аннотации Valid из пакета jakarta.validation
import lombok.RequiredArgsConstructor;  // Импорт аннотации RequiredArgsConstructor из библиотеки Lombok
import org.springframework.http.HttpStatus;  // Импорт класса HttpStatus из Spring Framework
import org.springframework.security.access.annotation.Secured;  // Импорт аннотации Secured из Spring Security для доступа на основе ролей
import org.springframework.security.core.annotation.AuthenticationPrincipal;  // Импорт аннотации AuthenticationPrincipal из Spring Security для получения текущего пользователя
import org.springframework.security.core.context.SecurityContextHolder;  // Импорт класса SecurityContextHolder из Spring Security для работы с контекстом аутентификации
import org.springframework.web.bind.annotation.*;  // Импорт аннотаций для создания контроллера REST API

import java.security.Principal;  // Импорт интерфейса Principal из пакета java.security
import java.util.UUID;  // Импорт класса UUID из пакета java.util

@RestController  // Объявление класса как контроллера REST
@RequestMapping("/api/users")  // Указание базового пути для всех методов контроллера
@RequiredArgsConstructor  // Аннотация Lombok для создания конструктора с final полями
public class UserController {
    private final UserService userService;  // Инъекция зависимости UserService

    @PostMapping  // Обработка HTTP POST запросов на указанный путь
    @Secured("ROLE_MODERATOR")  // Требуемая роль для доступа к методу
    @ResponseStatus(HttpStatus.CREATED)  // Установка HTTP статуса CREATED (201) для успешного создания ресурса
    @SecurityRequirement(name = "oauth2_auth_code")  // Требование к авторизации с использованием OAuth2
    public void create(@RequestBody @Valid UserRequest userRequest) {  // Метод для создания пользователя
        userService.createUser(userRequest);  // Вызов метода сервиса для создания пользователя
    }

    @GetMapping("/{id}")  // Обработка HTTP GET запросов с динамическим параметром {id}
    @Secured("ROLE_MODERATOR")  // Требуемая роль для доступа к методу
    @SecurityRequirement(name = "oauth2_auth_code")  // Требование к авторизации с использованием OAuth2
    public UserResponse getUserById(@PathVariable UUID id) {  // Метод для получения пользователя по ID
        return userService.getUserById(id);  // Вызов метода сервиса для получения пользователя
    }

    @GetMapping("/hello")  // Обработка HTTP GET запросов на указанный путь
    @Secured("ROLE_MODERATOR")  // Требуемая роль для доступа к методу
    @SecurityRequirement(name = "oauth2_auth_code")  // Требование к авторизации с использованием OAuth2
    public String hello() {  // Простой метод для возвращения имени текущего пользователя
        return SecurityContextHolder.getContext().getAuthentication().getName();  // Получение имени текущего аутентифицированного пользователя
    }
}
