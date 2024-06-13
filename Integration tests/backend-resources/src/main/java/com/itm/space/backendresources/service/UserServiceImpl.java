package com.itm.space.backendresources.service;  // Объявление пакета, в котором расположен класс

import com.itm.space.backendresources.api.request.UserRequest;  // Импорт класса UserRequest из пакета api.request
import com.itm.space.backendresources.api.response.UserResponse;  // Импорт класса UserResponse из пакета api.response
import com.itm.space.backendresources.exception.BackendResourcesException;  // Импорт класса BackendResourcesException из пакета exception
import com.itm.space.backendresources.mapper.UserMapper;  // Импорт интерфейса UserMapper из пакета mapper
import lombok.RequiredArgsConstructor;  // Импорт аннотации RequiredArgsConstructor из Lombok
import lombok.extern.slf4j.Slf4j;  // Импорт аннотации slf4j из Lombok для логирования
import org.keycloak.admin.client.CreatedResponseUtil;  // Импорт класса CreatedResponseUtil из Keycloak API
import org.keycloak.admin.client.Keycloak;  // Импорт класса Keycloak из Keycloak API
import org.keycloak.admin.client.resource.UserResource;  // Импорт класса UserResource из Keycloak API
import org.keycloak.representations.idm.CredentialRepresentation;  // Импорт класса CredentialRepresentation из Keycloak API
import org.keycloak.representations.idm.GroupRepresentation;  // Импорт класса GroupRepresentation из Keycloak API
import org.keycloak.representations.idm.RoleRepresentation;  // Импорт класса RoleRepresentation из Keycloak API
import org.keycloak.representations.idm.UserRepresentation;  // Импорт класса UserRepresentation из Keycloak API
import org.springframework.beans.factory.annotation.Value;  // Импорт аннотации Value из Spring Framework
import org.springframework.http.HttpStatus;  // Импорт класса HttpStatus из Spring Framework
import org.springframework.stereotype.Service;  // Импорт аннотации Service из Spring Framework

import javax.ws.rs.WebApplicationException;  // Импорт класса WebApplicationException из JAX-RS
import javax.ws.rs.core.Response;  // Импорт класса Response из JAX-RS
import java.util.Collections;  // Импорт класса Collections из Java.util для работы с коллекциями
import java.util.List;  // Импорт класса List из Java.util для работы с коллекциями
import java.util.UUID;  // Импорт класса UUID из Java.util для работы с уникальными идентификаторами

@Slf4j  // Аннотация для генерации логгера во время компиляции
@Service  // Аннотация, указывающая, что класс является сервисом Spring
@RequiredArgsConstructor  // Аннотация Lombok для генерации конструктора с обязательными аргументами
public class UserServiceImpl implements UserService {

    private final Keycloak keycloakClient;  // Инстанс Keycloak для взаимодействия с Keycloak API
    private final UserMapper userMapper;  // Инстанс маппера, преобразующего объекты пользователей

    @Value("${keycloak.realm}")  // Внедрение значения из application.properties в realm
    private String realm;

    // Реализация метода для создания пользователя
    public void createUser(UserRequest userRequest) {
        // Подготовка представления пароля
        CredentialRepresentation password = preparePasswordRepresentation(userRequest.getPassword());
        // Подготовка представления пользователя
        UserRepresentation user = prepareUserRepresentation(userRequest, password);
        try {
            // Вызов Keycloak API для создания пользователя
            Response response = keycloakClient.realm(realm).users().create(user);
            String userId = null;
            if (response != null) {  // Добавляем проверку на null, чтобы избежать NullPointerException
                userId = CreatedResponseUtil.getCreatedId(response);  // Извлечение идентификатора пользователя из ответа
            }
            log.info("Created UserId: {}", userId);  // Логирование успешного создания пользователя
        } catch (WebApplicationException ex) {
            log.error("Exception on \"createUser\": ", ex);  // Логирование исключения при создании пользователя
            throw new BackendResourcesException(ex.getMessage(), HttpStatus.resolve(ex.getResponse().getStatus()));
        }
    }

    // Реализация метода для получения пользователя по идентификатору
    @Override
    public UserResponse getUserById(UUID id) {
        UserRepresentation userRepresentation;
        List<RoleRepresentation> userRoles;
        List<GroupRepresentation> userGroups;
        try {
            // Вызов Keycloak API для получения представления пользователя по идентификатору
            userRepresentation = keycloakClient.realm(realm).users().get(String.valueOf(id)).toRepresentation();
            if (userRepresentation != null) {
                UserResource userResource = keycloakClient.realm(realm).users().get(String.valueOf(id));
                if (userResource.roles() != null) {
                    userRoles = userResource.roles().getAll().getRealmMappings();  // Получение ролей пользователя
                } else {
                    userRoles = Collections.emptyList();
                }
                userGroups = userResource.groups();  // Получение групп, в которых состоит пользователь
            } else {
                throw new BackendResourcesException("User not found", HttpStatus.NOT_FOUND);  // Генерация исключения, если пользователь не найден
            }
        } catch (RuntimeException ex) {
            log.error("Exception on \"getUserById\": ", ex);  // Логирование исключения при получении пользователя
            throw new BackendResourcesException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Преобразование представления пользователя в ответ пользователю с помощью маппера
        return userMapper.userRepresentationToUserResponse(userRepresentation, userRoles, userGroups);
    }

    // Приватный метод для подготовки представления пароля пользователя
    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);  // Установка временного пароля в false
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);  // Установка типа пароля
        credentialRepresentation.setValue(password);  // Установка значения пароля
        return credentialRepresentation;
    }

    // Приватный метод для подготовки представления пользователя
    private UserRepresentation prepareUserRepresentation(UserRequest userRequest,
                                                         CredentialRepresentation credentialRepresentation) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(userRequest.getUsername());  // Установка имени пользователя
        newUser.setEmail(userRequest.getEmail());  // Установка email пользователя
        newUser.setCredentials(List.of(credentialRepresentation));  // Установка представления пароля
        newUser.setEnabled(true);  // Установка активности пользователя в true
        newUser.setFirstName(userRequest.getFirstName());  // Установка имени пользователя
        newUser.setLastName(userRequest.getLastName());  // Установка фамилии пользователя
        return newUser;
    }
}
