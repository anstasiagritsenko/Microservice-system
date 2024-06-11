package ru.itmentor.spring.boot_security.demo.controller; // Объявление пакета

import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации @Autowired из пакета org.springframework.beans.factory.annotation
import org.springframework.http.ResponseEntity; // Импорт класса ResponseEntity из пакета org.springframework.http
import org.springframework.web.bind.annotation.*; // Импорт аннотаций для работы с веб-запросами из пакета org.springframework.web.bind.annotation
import ru.itmentor.spring.boot_security.demo.model.Animal; // Импорт класса Animal из пакета ru.itmentor.spring.boot_security.demo.model
import ru.itmentor.spring.boot_security.demo.service.AnimalService; // Импорт класса AnimalService из пакета ru.itmentor.spring.boot_security.demo.service

import javax.validation.Valid; // Импорт аннотации @Valid из пакета javax.validation
import java.util.List; // Импорт класса List из пакета java.util

@RestController // Аннотация, указывающая, что класс является REST контроллером
@RequestMapping("/api/animals") // Аннотация, указывающая базовый URL-адрес для всех методов в контроллере
public class AnimalRestController { // Объявление класса AnimalRestController

    private final AnimalService animalService; // Объявление поля animalService типа AnimalService

    @Autowired // Аннотация, указывающая на автоматическое внедрение зависимости
    public AnimalRestController(AnimalService animalService) { // Конструктор класса с параметром animalService
        this.animalService = animalService; // Инициализация поля animalService
    }

    @GetMapping // Аннотация, указывающая, что метод обрабатывает HTTP GET запросы
    public ResponseEntity<List<Animal>> getAllAnimals() { // Объявление метода для получения списка всех животных
        List<Animal> animals = animalService.getAllAnimals(); // Получение списка всех животных с помощью animalService
        return ResponseEntity.ok().body(animals); // Возврат успешного ответа с телом списка животных
    }

    @PostMapping // Аннотация, указывающая, что метод обрабатывает HTTP POST запросы
    public ResponseEntity<Animal> saveAnimal(@Valid @RequestBody Animal animal) { // Объявление метода для сохранения животного
        Animal savedAnimal = animalService.createOrUpdateAnimal(animal); // Сохранение или обновление животного с помощью animalService
        return ResponseEntity.ok().body(savedAnimal); // Возврат успешного ответа с телом сохраненного животного
    }

    @GetMapping("/{id}") // Аннотация, указывающая, что метод обрабатывает HTTP GET запросы с указанным путем
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id) { // Объявление метода для получения животного по его идентификатору
        Animal animal = animalService.readAnimal(id); // Получение животного по его идентификатору с помощью animalService
        return ResponseEntity.ok().body(animal); // Возврат успешного ответа с телом найденного животного
    }

    @PutMapping("/{id}") // Аннотация, указывающая, что метод обрабатывает HTTP PUT запросы с указанным путем
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @Valid @RequestBody Animal animal) { // Объявление метода для обновления животного
        animal.setId(id); // Установка идентификатора животного
        Animal updatedAnimal = animalService.createOrUpdateAnimal(animal); // Обновление животного с помощью animalService
        return ResponseEntity.ok().body(updatedAnimal); // Возврат успешного ответа с телом обновленного животного
    }

    @DeleteMapping("/{id}") // Аннотация, указывающая, что метод обрабатывает HTTP DELETE запросы с указанным путем
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) { // Объявление метода для удаления животного по его идентификатору
        animalService.deleteAnimal(id); // Удаление животного по его идентификатору с помощью animalService
        return ResponseEntity.ok().build(); // Возврат успешного ответа без тела
    }
}
