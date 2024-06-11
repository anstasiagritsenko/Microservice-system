/*
package ru.itmentor.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.service.VolunteerDetailsService;

import java.util.Collection;
import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider { // Реализация AuthenticationProvider, предоставляемая Spring Security

    private final VolunteerDetailsService volunteerDetailsService; // Сервис для получения деталей о волонтере

    @Autowired // Автоматическое внедрение зависимости
    public AuthProviderImpl(VolunteerDetailsService volunteerDetailsService) {
        this.volunteerDetailsService = volunteerDetailsService; // Инициализация сервиса
    }

    // Метод для аутентификации пользователя
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName(); // Получение имени пользователя из запроса аутентификации
        VolunteerDetails volunteerDetails = volunteerDetailsService.loadUserByUsername(username); // Загрузка деталей о волонтере по имени пользователя

        String password = authentication.getCredentials().toString(); // Получение пароля пользователя из запроса аутентификации

        // Проверка правильности пароля
        if (!password.equals(volunteerDetails.getPassword())) {
            throw new BadCredentialsException("Неправильный пароль"); // Выбрасывание исключения, если пароль неверный
        }

        // Возвращение объекта аутентификации с пользователями, ролями и т.д.
        return new UsernamePasswordAuthenticationToken(volunteerDetails, password, Collections.emptyList());
    }

    // Метод для определения, поддерживает ли этот провайдер заданный тип аутентификации
    @Override
    public boolean supports(Class<?> authentication) {
        return true; // Поддерживает все типы аутентификации
    }
}
*/