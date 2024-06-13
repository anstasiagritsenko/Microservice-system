package com.itm.space.backendresources.api.request;

import jakarta.validation.constraints.Email;  // Импорт аннотации для валидации email
import jakarta.validation.constraints.NotBlank;  // Импорт аннотации для валидации на непустое значение
import jakarta.validation.constraints.Size;  // Импорт аннотации для валидации на размер строки
import lombok.Data;  // Импорт аннотации Lombok для генерации геттеров, сеттеров и других методов

@Data  // Аннотация Lombok для генерации геттеров, сеттеров, equals, hashCode и toString
public class UserRequest {

    @NotBlank(message = "Username should not be blank")  // Аннотация для проверки, что поле не пустое
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters long")  // Аннотация для проверки длины строки
    private final String username;  // Поле для имени пользователя

    @NotBlank(message = "Email should not be blank")  // Аннотация для проверки, что поле не пустое
    @Email(message = "Email should be valid", regexp = ".+@.+\\..+")  // Аннотация для проверки валидности email
    private final String email;  // Поле для email пользователя

    @NotBlank(message = "Password should not be blank")  // Аннотация для проверки, что поле не пустое
    @Size(min = 4, message = "Password should be greater than 4 characters long")  // Аннотация для проверки минимальной длины строки
    private final String password;  // Поле для пароля пользователя

    @NotBlank  // Аннотация для проверки, что поле не пустое
    private final String firstName;  // Поле для имени пользователя

    @NotBlank  // Аннотация для проверки, что поле не пустое
    private final String lastName;  // Поле для фамилии пользователя
}
