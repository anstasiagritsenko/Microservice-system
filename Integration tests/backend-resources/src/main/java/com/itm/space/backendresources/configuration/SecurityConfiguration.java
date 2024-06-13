package com.itm.space.backendresources.configuration;

import org.springframework.context.annotation.Bean;  // Импорт аннотации Bean из Spring Framework
import org.springframework.context.annotation.Configuration;  // Импорт аннотации Configuration из Spring Framework
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;  // Импорт аннотации EnableMethodSecurity из Spring Security
import org.springframework.security.config.annotation.web.builders.HttpSecurity;  // Импорт класса HttpSecurity из Spring Security
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;  // Импорт аннотации EnableWebSecurity из Spring Security
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;  // Импорт класса AbstractHttpConfigurer из Spring Security
import org.springframework.security.core.GrantedAuthority;  // Импорт интерфейса GrantedAuthority из Spring Security
import org.springframework.security.core.authority.SimpleGrantedAuthority;  // Импорт класса SimpleGrantedAuthority из Spring Security
import org.springframework.security.oauth2.jwt.Jwt;  // Импорт класса Jwt из Spring Security OAuth2
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;  // Импорт класса JwtAuthenticationToken из Spring Security OAuth2
import org.springframework.security.web.SecurityFilterChain;  // Импорт класса SecurityFilterChain из Spring Security

import java.util.ArrayList;  // Импорт класса ArrayList из Java Collections Framework
import java.util.Collection;  // Импорт интерфейса Collection из Java Collections Framework
import java.util.List;  // Импорт интерфейса List из Java Collections Framework
import java.util.Map;  // Импорт интерфейса Map из Java Collections Framework

@Configuration  // Объявление класса как конфигурационного компонента Spring
@EnableWebSecurity  // Включение поддержки безопасности веб-приложения
@EnableMethodSecurity(securedEnabled = true)  // Включение поддержки аннотаций безопасности для методов

public class SecurityConfiguration {

    @Bean  // Аннотация для определения метода, возвращающего бин Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Отключение защиты CSRF
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().permitAll())  // Установка правил доступа: любые запросы разрешены
                .oauth2ResourceServer()  // Настройка ресурсного сервера OAuth2
                .jwt()  // Использование JWT для аутентификации
                .jwtAuthenticationConverter(SecurityConfiguration::convertJwtToken);  // Установка конвертера JWT-токена
        return http.build();  // Возврат настроенной цепочки фильтров безопасности
    }

    private static JwtAuthenticationToken convertJwtToken(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();  // Создание коллекции для хранения ролей
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwt, authorities);  // Создание объекта JwtAuthenticationToken
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");  // Получение множества ролей из JWT
        List<String> roles = (List<String>) realmAccess.get("roles");  // Получение списка ролей из множества
        for (String role : roles) {  // Перебор всех ролей
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));  // Добавление роли в коллекцию
        }
        return new JwtAuthenticationToken(jwt, authorities, authenticationToken.getName());  // Возврат объекта JwtAuthenticationToken с добавленными ролями
    }
}
