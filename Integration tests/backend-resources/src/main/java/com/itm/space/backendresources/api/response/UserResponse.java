package com.itm.space.backendresources.api.response;

import lombok.Data;  // Импорт аннотации Lombok для генерации геттеров, сеттеров и других методов

import java.util.List;  // Импорт класса List из пакета java.util

@Data  // Аннотация Lombok для генерации геттеров, сеттеров, equals, hashCode и toString
public class UserResponse {

    private final String firstName;  // Поле для имени пользователя

    private final String lastName;  // Поле для фамилии пользователя

    private final String email;  // Поле для email пользователя

    private final List<String> roles;  // Поле для списка ролей пользователя

    private final List<String> groups;  // Поле для списка групп пользователя
}
