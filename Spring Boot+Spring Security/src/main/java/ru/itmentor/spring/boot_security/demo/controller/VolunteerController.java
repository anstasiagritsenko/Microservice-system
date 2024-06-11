// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.controller
package ru.itmentor.spring.boot_security.demo.controller;

// Импортируем необходимые классы из Spring Security и Spring MVC
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itmentor.spring.boot_security.demo.model.Volunteer;
import ru.itmentor.spring.boot_security.demo.security.VolunteerDetails;
import ru.itmentor.spring.boot_security.demo.service.VolunteerService;

import javax.validation.Valid;
import java.util.List;

// Аннотация @Controller указывает, что этот класс является контроллером Spring MVC
@Controller
// Указываем, что все методы внутри этого контроллера будут обрабатывать запросы, начинающиеся с "/volunteers"
@RequestMapping("/volunteers")
public class VolunteerController {

    // Поле для хранения ссылки на сервис волонтеров
    private final VolunteerService volunteerService;

    // Конструктор для внедрения зависимости сервиса волонтеров
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    // Метод для обработки GET-запросов на URL "/volunteers"
    @GetMapping
    public String listVolunteers(Model model) {
        // получение списка волонтеров из базы
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();

        // получение текущего пользователя из контекста безопасности
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        VolunteerDetails currentVolunteerDetails = null;
        if (principal instanceof UserDetails) {
            currentVolunteerDetails = (VolunteerDetails) principal;
        }

        // добавляем список волонтеров и данные текущего пользователя в модель
        model.addAttribute("volunteers", volunteers);
        model.addAttribute("currentVolunteer", currentVolunteerDetails != null ? currentVolunteerDetails.getVolunteer() : null);
        model.addAttribute("currentVolunteerDetails", currentVolunteerDetails);
        // возвращаем название шаблона для отображения страницы волонтеров
        return "volunteers_edit"; // ваш шаблон с волонтерами
    }

    // Метод для обработки GET-запросов на URL "/volunteers/add" и отображения формы для добавления нового волонтера
    @GetMapping("/add")
    public String addVolunteerForm(Model model) {
        // добавляем в модель новый объект Volunteer
        model.addAttribute("volunteer", new Volunteer());
        // возвращаем название шаблона для отображения формы
        return "form_v";
    }

    // Метод для обработки GET-запросов на URL "/volunteers/{id}/edit" и отображения формы для редактирования волонтера
    @GetMapping("/{id}/edit")
    public String editVolunteerForm(@PathVariable("id") long id, Model model) {
        // получаем волонтера по id
        Volunteer volunteer = volunteerService.readVolunteer(id);
        if (volunteer == null) {
            // перенаправление на главную страницу, если волонтер не найден
            return "redirect:/";
        }
        // добавляем в модель найденного волонтера
        model.addAttribute("volunteer", volunteer);
        // возвращаем название шаблона для отображения формы
        return "form_v";
    }

    // Метод для обработки POST-запросов на URL "/volunteers" и сохранения нового или отредактированного волонтера
    @PostMapping
    public String saveVolunteer(@ModelAttribute("volunteer") @Valid Volunteer volunteer, BindingResult bindingResult,
                                RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            // возвращаем форму с ошибками, если они есть
            return "form_v";
        }
        // сохраняем волонтера
        volunteerService.createOrUpdateVolunteer(volunteer);
        // добавляем сообщение об успешном добавлении волонтера
        attributes.addFlashAttribute("flashMessage",
                "Волонтер " + volunteer.getFirstName() + " " + volunteer.getLastName() + " успешно добавлен!");
        // перенаправление на страницу списка волонтеров
        return "redirect:/volunteers";
    }

    // Метод для обработки GET-запросов на URL "/volunteers/delete/{id}" и удаления волонтера
    @GetMapping("/delete/{id}")
    public String deleteVolunteer(@PathVariable("id") long id, RedirectAttributes attributes) {
        // удаляем волонтера по id
        Volunteer volunteer = volunteerService.deleteVolunteer(id);
        if (volunteer == null) {
            // добавляем сообщение, если волонтер не найден
            attributes.addFlashAttribute("flashMessage", "Волонтер не найден!");
        } else {
            // добавляем сообщение об успешном удалении волонтера
            attributes.addFlashAttribute("flashMessage",
                    "Волонтер " + volunteer.getFirstName() + " " + volunteer.getLastName() + " успешно удален!");
        }
        // перенаправление на страницу списка волонтеров
        return "redirect:/volunteers";
    }

    // Метод для обработки GET-запросов на URL "/volunteers/volunteers_edit" и отображения страницы редактирования волонтеров
    @GetMapping("/volunteers_edit")
    @PreAuthorize("hasRole('ADMIN')") // метод доступен только пользователям с ролью ADMIN
    public String showVolunteersEditPage(Model model) {
        // добавляем в модель список всех волонтеров
        model.addAttribute("volunteers", volunteerService.getAllVolunteers());
        // возвращаем название шаблона для отображения страницы редактирования волонтеров
        return "volunteers_edit";
    }
}
