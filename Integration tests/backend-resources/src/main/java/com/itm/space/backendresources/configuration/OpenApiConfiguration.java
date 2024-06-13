package com.itm.space.backendresources.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;  // Импорт enum SecuritySchemeIn из библиотеки Swagger/OpenAPI
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;  // Импорт enum SecuritySchemeType из библиотеки Swagger/OpenAPI
import io.swagger.v3.oas.annotations.security.OAuthFlow;  // Импорт аннотации OAuthFlow из библиотеки Swagger/OpenAPI
import io.swagger.v3.oas.annotations.security.OAuthFlows;  // Импорт аннотации OAuthFlows из библиотеки Swagger/OpenAPI
import io.swagger.v3.oas.annotations.security.OAuthScope;  // Импорт аннотации OAuthScope из библиотеки Swagger/OpenAPI
import io.swagger.v3.oas.annotations.security.SecurityScheme;  // Импорт аннотации SecurityScheme из библиотеки Swagger/OpenAPI
import io.swagger.v3.oas.models.OpenAPI;  // Импорт модели OpenAPI из библиотеки Swagger/OpenAPI
import org.springframework.context.annotation.Bean;  // Импорт аннотации Bean из Spring Framework
import org.springframework.context.annotation.Configuration;  // Импорт аннотации Configuration из Spring Framework

@Configuration  // Объявление класса как конфигурационного компонента Spring
@SecurityScheme(  // Аннотация для определения схемы безопасности OpenAPI
        name = "oauth2_auth_code",  // Имя схемы безопасности
        type = SecuritySchemeType.OAUTH2,  // Тип схемы безопасности (OAuth2)
        flows = @OAuthFlows(  // Описание потоков авторизации OAuth2
                authorizationCode = @OAuthFlow(  // Поток авторизации по коду авторизации
                        authorizationUrl = "http://backend-keycloak-auth:8080/auth/realms/ITM/protocol/openid-connect/auth",  // URL для получения авторизационного кода
                        tokenUrl = "http://backend-keycloak-auth:8080/auth/realms/ITM/protocol/openid-connect/token",  // URL для обмена кода на токен
                        scopes = {  // Список областей видимости OAuth2
                                @OAuthScope(name = "openid", description = "Read access")  // Область видимости "openid" с описанием
                        }
                )
        ),
        in = SecuritySchemeIn.HEADER  // Место передачи токена (в заголовке HTTP-запроса)
)
public class OpenApiConfiguration {

    @Bean  // Аннотация для определения метода, возвращающего бин Spring
    public OpenAPI publicApi() {
        return new OpenAPI();  // Создание и возвращение экземпляра OpenAPI
    }
}
