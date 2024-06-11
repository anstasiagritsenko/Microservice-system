// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.controller
package ru.itmentor.spring.boot_security.demo.controller;

// Импортируем необходимые классы и аннотации из Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.service.VolunteerService;

// Аннотация @Controller указывает, что этот класс является контроллером Spring MVC
@Controller
// Аннотация @RequestMapping указывает базовый URL, с которого будут начинаться все запросы к этому контроллеру
@RequestMapping("/")
public class IndexController {

    // Объявляем поле для хранения ссылки на VolunteerService
    private final VolunteerService volunteerService;

    // Аннотация @Autowired указывает, что Spring автоматически внедрит зависимость VolunteerService
    @Autowired
    public IndexController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    // Метод для обработки GET-запросов на корневой URL "/"
    @GetMapping("")
    public String indexView(Model model) {
        // Возвращаем название шаблона для отображения главной страницы
        return "index";
    }
}
