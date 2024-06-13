package com.itm.space.backendresources.configuration;

import org.keycloak.admin.client.Keycloak;  // Импорт класса Keycloak из библиотеки Keycloak Admin Client
import org.keycloak.admin.client.KeycloakBuilder;  // Импорт класса KeycloakBuilder из библиотеки Keycloak Admin Client
import org.springframework.beans.factory.annotation.Value;  // Импорт аннотации Value из пакета org.springframework.beans.factory.annotation
import org.springframework.context.annotation.Bean;  // Импорт аннотации Bean из пакета org.springframework.context.annotation
import org.springframework.context.annotation.Configuration;  // Импорт аннотации Configuration из пакета org.springframework.context.annotation

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;  // Статический импорт константы CLIENT_CREDENTIALS из класса OAuth2Constants в библиотеке Keycloak
import static org.keycloak.OAuth2Constants.PASSWORD;  // Статический импорт константы PASSWORD из класса OAuth2Constants в библиотеке Keycloak

@Configuration  // Аннотация, указывающая на то, что данный класс является конфигурационным классом Spring
public class KeycloakClientConfiguration {

    @Value("${keycloak.credentials.secret}")  // Внедрение значения из application.properties в переменную secretKey
    private String secretKey;

    @Value("${keycloak.resource}")  // Внедрение значения из application.properties в переменную clientId
    private String clientId;

    @Value("${keycloak.auth-server-url}")  // Внедрение значения из application.properties в переменную authUrl
    private String authUrl;

    @Value("${keycloak.realm}")  // Внедрение значения из application.properties в переменную realm
    private String realm;

    @Bean  // Аннотация, указывающая на то, что данный метод возвращает экземпляр бина Spring
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()  // Создание нового экземпляра KeycloakBuilder
                .serverUrl(authUrl)  // Установка URL сервера Keycloak
                .realm(realm)  // Установка имени realm Keycloak
                .grantType(CLIENT_CREDENTIALS)  // Установка типа гранта (CLIENT_CREDENTIALS)
                .clientId(clientId)  // Установка клиентского идентификатора
                .clientSecret(secretKey)  // Установка секретного ключа клиента
                .build();  // Создание экземпляра Keycloak на основе текущей конфигурации
    }
}
