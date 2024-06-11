// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.controller
package ru.itmentor.spring.boot_security.demo.controller;

// Импортируем необходимые классы из Spring Security и Spring MVC
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmentor.spring.boot_security.demo.service.VolunteerService;

// Аннотация @Controller указывает, что этот класс является контроллером Spring MVC
@Controller
public class UserController {

    // Поле для хранения ссылки на сервис волонтеров
    private final VolunteerService userService;

    // Конструктор для внедрения зависимости сервиса волонтеров
    public UserController(VolunteerService volunteerService) {
        this.userService = volunteerService;
    }

    // Метод для обработки GET-запросов на URL "/user"
    @GetMapping("/user")
    public String showUserPage(Model model) {
        // Получаем текущую аутентификацию из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Извлекаем имя текущего пользователя
        String currentUsername = authentication.getName();

        // Добавляем имя текущего пользователя в модель под именем "username"
        model.addAttribute("username", currentUsername);

        // Возвращаем название шаблона для отображения страницы пользователя
        return "user";
    }
}
