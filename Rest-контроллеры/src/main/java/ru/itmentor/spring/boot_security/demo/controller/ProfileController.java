// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.controller
package ru.itmentor.spring.boot_security.demo.controller;

// Импортируем необходимые классы и аннотации из Spring
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmentor.spring.boot_security.demo.security.VolunteerDetails;

// Аннотация @Controller указывает, что этот класс является контроллером Spring MVC
@Controller
public class ProfileController {

    // Метод для обработки GET-запросов на URL "/profile"
    @GetMapping("/profile")
    public String showProfile(Model model) {
        // Получаем данные текущего пользователя из контекста безопасности
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Проверяем, является ли текущий пользователь объектом UserDetails
        if (principal instanceof UserDetails) {
            // Приводим объект principal к VolunteerDetails
            VolunteerDetails currentUser = (VolunteerDetails) principal;

            // Добавляем данные текущего пользователя в модель
            model.addAttribute("currentUser", currentUser.getVolunteer());
        }

        // Возвращаем название шаблона для отображения профиля
        return "profile";
    }
}
