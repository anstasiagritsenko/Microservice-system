package ru.itmentor.spring.boot_security.demo.controller; // Объявление пакета

import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации @Autowired из пакета org.springframework.beans.factory.annotation
import org.springframework.http.HttpStatus; // Импорт класса HttpStatus из пакета org.springframework.http
import org.springframework.http.ResponseEntity; // Импорт класса ResponseEntity из пакета org.springframework.http
import org.springframework.web.bind.annotation.*; // Импорт аннотаций для работы с веб-запросами из пакета org.springframework.web.bind.annotation
import ru.itmentor.spring.boot_security.demo.model.Volunteer; // Импорт класса Volunteer из пакета ru.itmentor.spring.boot_security.demo.model
import ru.itmentor.spring.boot_security.demo.service.VolunteerService; // Импорт класса VolunteerService из пакета ru.itmentor.spring.boot_security.demo.service

import javax.validation.Valid; // Импорт аннотации @Valid из пакета javax.validation
import java.util.List; // Импорт класса List из пакета java.util

@RestController // Аннотация, указывающая, что класс является REST контроллером
@RequestMapping("/api/volunteers") // Аннотация, указывающая базовый URL-адрес для всех методов в контроллере
public class VolunteerRestController { // Объявление класса VolunteerRestController

    private final VolunteerService volunteerService; // Объявление поля volunteerService типа VolunteerService

    @Autowired // Аннотация, указывающая на автоматическое внедрение зависимости
    public VolunteerRestController(VolunteerService volunteerService) { // Конструктор класса с параметром volunteerService
        this.volunteerService = volunteerService; // Инициализация поля volunteerService
    }

    @GetMapping // Аннотация, указывающая, что метод обрабатывает HTTP GET запросы
    public ResponseEntity<List<Volunteer>> getAllVolunteers() { // Объявление метода для получения списка всех волонтеров
        List<Volunteer> volunteers = volunteerService.getAllVolunteers(); // Получение списка всех волонтеров с помощью volunteerService
        return new ResponseEntity<>(volunteers, HttpStatus.OK); // Возврат ответа с телом списка волонтеров и статусом HTTP OK
    }

    @GetMapping("/{id}") // Аннотация, указывающая, что метод обрабатывает HTTP GET запросы с указанным путем
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable("id") long id) { // Объявление метода для получения волонтера по его идентификатору
        Volunteer volunteer = volunteerService.readVolunteer(id); // Получение волонтера по его идентификатору с помощью volunteerService
        return volunteer != null ? // Если волонтер найден
                new ResponseEntity<>(volunteer, HttpStatus.OK) : // Возврат ответа с телом волонтера и статусом HTTP OK
                new ResponseEntity<>(HttpStatus.NOT_FOUND); // Возврат ответа с статусом HTTP NOT_FOUND
    }

    @PostMapping // Аннотация, указывающая, что метод обрабатывает HTTP POST запросы
    public ResponseEntity<Volunteer> createVolunteer(@Valid @RequestBody Volunteer volunteer) { // Объявление метода для создания волонтера
        volunteerService.createVolunteer(volunteer); // Создание волонтера с помощью volunteerService
        return new ResponseEntity<>(volunteer, HttpStatus.CREATED); // Возврат ответа с телом созданного волонтера и статусом HTTP CREATED
    }

    @PutMapping("/{id}") // Аннотация, указывающая, что метод обрабатывает HTTP PUT запросы с указанным путем
    public ResponseEntity<Volunteer> updateVolunteer(@PathVariable("id") long id, @Valid @RequestBody Volunteer volunteer) { // Объявление метода для обновления информации о волонтере
        Volunteer existingVolunteer = volunteerService.readVolunteer(id); // Получение существующего волонтера по его идентификатору с помощью volunteerService
        if (existingVolunteer != null) { // Если волонтер найден
            volunteer.setId(id); // Установка идентификатора волонтера
            volunteerService.updateVolunteer(volunteer); // Обновление информации о волонтере с помощью volunteerService
            return new ResponseEntity<>(volunteer, HttpStatus.OK); // Возврат ответа с телом обновленного волонтера и статусом HTTP OK
        } else { // Если волонтер не найден
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Возврат ответа с статусом HTTP NOT_FOUND
        }
    }

    @DeleteMapping("/{id}") // Аннотация, указывающая, что метод обрабатывает HTTP DELETE запросы с указанным путем
    public ResponseEntity<Void> deleteVolunteer(@PathVariable("id") long id) { // Объявление метода для удаления волонтера
        Volunteer existingVolunteer = volunteerService.readVolunteer(id); // Получение существующего волонтера по его идентификатору с помощью volunteerService
        if (existingVolunteer != null) { // Если волонтер найден
            volunteerService.deleteVolunteer(id); // Удаление волонтера с помощью volunteerService
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Возврат ответа с пустым телом и статусом HTTP NO_CONTENT
        } else { // Если волонтер не найден
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Возврат ответа с статусом HTTP NOT_FOUND
        }
    }
}
