// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.configs
package ru.itmentor.spring.boot_security.demo.configs;

// Импортируем необходимые классы из Spring Security для работы с аутентификацией
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

// Импортируем классы для работы с сервлетами и HTTP-запросами
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Аннотация @Component указывает Spring, что этот класс является компонентом и должен быть зарегистрирован как bean
@Component
// Объявляем класс CustomAuthenticationSuccessHandler, который реализует интерфейс AuthenticationSuccessHandler
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // Переопределяем метод onAuthenticationSuccess, который вызывается при успешной аутентификации
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Перенаправляем пользователя на главную страницу после успешного входа
        response.sendRedirect("/");
    }
}
