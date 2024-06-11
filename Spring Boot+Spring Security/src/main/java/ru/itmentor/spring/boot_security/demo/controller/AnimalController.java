// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.controller
package ru.itmentor.spring.boot_security.demo.controller;

// Импортируем необходимые классы и аннотации из Spring для создания контроллера
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itmentor.spring.boot_security.demo.model.Animal;
import ru.itmentor.spring.boot_security.demo.service.AnimalService;

import javax.validation.Valid;
import java.util.List;

// Аннотация @Controller указывает, что этот класс является контроллером Spring MVC
@Controller
// Аннотация @RequestMapping указывает базовый URL, с которого будут начинаться все запросы к этому контроллеру
@RequestMapping("/animals")
public class AnimalController {

    // Объявляем поле для хранения ссылки на AnimalService
    private final AnimalService animalService;

    // Конструктор, который принимает AnimalService и сохраняет его в поле класса
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // Метод для обработки GET-запросов на URL /animals, /animals/ и /animals/list
    @GetMapping({"", "/", "list"})
    public String showAllAnimals(Model model, @ModelAttribute("flashMessage") String flashAttribute) {
        // Получаем список всех животных из сервиса
        List<Animal> animals = animalService.getAllAnimals();
        // Добавляем список животных в модель
        model.addAttribute("animals", animals);
        // Добавляем flash-сообщение в модель
        model.addAttribute("flashMessage", flashAttribute);
        // Возвращаем название шаблона для отображения списка животных
        return "list";
    }

    // Метод для обработки GET-запросов на URL /animals/add
    @GetMapping("/add")
    public String addAnimalForm(Model model) {
        // Добавляем новый объект Animal в модель
        model.addAttribute("animal", new Animal());
        // Возвращаем название шаблона для формы добавления животного
        return "form";
    }

    // Метод для обработки GET-запросов на URL /animals/{id}/edit
    @GetMapping("/{id}/edit")
    public String editAnimalForm(@PathVariable(value = "id", required = true) long id, Model model,
                                 RedirectAttributes attributes) {
        // Получаем животное по его ID из сервиса
        Animal animal = animalService.readAnimal(id);
        // Если животное не найдено, добавляем flash-сообщение и перенаправляем на список животных
        if (null == animal) {
            attributes.addFlashAttribute("flashMessage", "Животное не найдено!");
            return "redirect:/animals";
        }
        // Добавляем животное в модель
        model.addAttribute("animal", animal);
        // Возвращаем название шаблона для формы редактирования животного
        return "form";
    }

    // Метод для обработки POST-запросов на URL /animals
    @PostMapping()
    public String saveAnimal(@ModelAttribute("animal") @Valid Animal animal, BindingResult bindingResult,
                             RedirectAttributes attributes, Model model) {
        // Если в форме есть ошибки валидации, возвращаем форму с ошибками
        if (bindingResult.hasErrors()) {
            List<Animal> animals = animalService.getAllAnimals();
            model.addAttribute("animals", animals);
            return "form";
        }
        // Сохраняем или обновляем животное в базе данных
        animalService.createOrUpdateAnimal(animal);
        // Добавляем flash-сообщение о успешном сохранении животного
        attributes.addFlashAttribute("flashMessage",
                "Животное " + animal.getName() + " успешно добавлено!");
        // Перенаправляем на список животных
        return "redirect:/animals";
    }

    // Метод для обработки GET-запросов на URL /animals/delete
    @GetMapping("/delete")
    public String deleteAnimal(@RequestParam(value = "id", required = true, defaultValue = "") long id,
                               RedirectAttributes attributes) {
        // Удаляем животное по его ID из базы данных
        Animal animal = animalService.deleteAnimal(id);
        // Добавляем flash-сообщение о результате удаления животного
        attributes.addFlashAttribute("flashMessage", (null == animal) ?
                "Животное не найдено!" :
                "Животное " + animal.getName() + " успешно удалено!");
        // Перенаправляем на список животных
        return "redirect:/animals";
    }
}
