package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication; // Импорт класса Authentication из пакета org.springframework.security.core
import org.springframework.security.core.authority.AuthorityUtils; // Импорт класса AuthorityUtils из пакета org.springframework.security.core.authority
import org.springframework.security.web.authentication.AuthenticationSuccessHandler; // Импорт интерфейса AuthenticationSuccessHandler из пакета org.springframework.security.web.authentication
import org.springframework.stereotype.Component; // Импорт аннотации Component из пакета org.springframework.stereotype

import javax.servlet.http.HttpServletRequest; // Импорт класса HttpServletRequest из пакета javax.servlet.http
import javax.servlet.http.HttpServletResponse; // Импорт класса HttpServletResponse из пакета javax.servlet.http
import java.io.IOException; // Импорт класса IOException из пакета java.io
import java.util.Set; // Импорт класса Set из пакета java.util

@Component // Аннотация, обозначающая, что класс является Spring компонентом
public class SuccessUserHandler implements AuthenticationSuccessHandler { // Объявление класса SuccessUserHandler, реализующего интерфейс AuthenticationSuccessHandler

    // Метод вызывается, когда пользователь успешно аутентифицирован
    @Override // Аннотация, обозначающая переопределение метода из интерфейса
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // Получение ролей пользователя из аутентификации
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        // Проверка, есть ли у пользователя роль "ROLE_USER"
        if (roles.contains("ROLE_USER")) {
            // Если у пользователя есть роль "ROLE_USER", перенаправляем его на страницу "/profile"
            httpServletResponse.sendRedirect("/volunteers/profile");
        }
        if (roles.contains("ROLE_ADMIN")) {
            // Если у пользователя есть роль "ROLE_USER", перенаправляем его на страницу "/profile"
            httpServletResponse.sendRedirect("/volunteers/volunteers_edit");
        }
    }
}
