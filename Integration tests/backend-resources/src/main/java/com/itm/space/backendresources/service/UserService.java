package com.itm.space.backendresources.service;  // Объявление пакета, в котором расположен интерфейс

import com.itm.space.backendresources.api.request.UserRequest;  // Импорт класса UserRequest из пакета api.request
import com.itm.space.backendresources.api.response.UserResponse;  // Импорт класса UserResponse из пакета api.response

import java.util.UUID;  // Импорт класса UUID из Java.util для работы с уникальными идентификаторами

public interface UserService {

    void createUser(UserRequest userRequest);  // Метод для создания пользователя, принимает объект UserRequest

    UserResponse getUserById(UUID id);  // Метод для получения пользователя по его идентификатору, возвращает объект UserResponse

}
