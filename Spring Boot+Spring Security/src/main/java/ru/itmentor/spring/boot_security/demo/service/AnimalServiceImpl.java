package ru.itmentor.spring.boot_security.demo.service; // Пакет, в котором находится класс

import org.springframework.stereotype.Service; // Импорт аннотации Service из пакета org.springframework.stereotype
import org.springframework.transaction.annotation.Transactional; // Импорт аннотации Transactional из пакета org.springframework.transaction.annotation
import ru.itmentor.spring.boot_security.demo.dao.AnimalDao; // Импорт класса AnimalDao из пакета ru.itmentor.spring.boot_security.demo.dao
import ru.itmentor.spring.boot_security.demo.model.Animal; // Импорт класса Animal из пакета ru.itmentor.spring.boot_security.demo.model

import java.util.List; // Импорт интерфейса List из пакета java.util

@Service // Аннотация, указывающая, что класс является сервисом
@Transactional // Аннотация, указывающая, что методы класса должны выполняться в рамках транзакции
public class AnimalServiceImpl implements AnimalService { // Объявление класса AnimalServiceImpl, реализующего интерфейс AnimalService

    private final AnimalDao animalDao; // Приватное поле animalDao типа AnimalDao

    public AnimalServiceImpl(AnimalDao animalDao) { // Конструктор класса с параметром animalDao
        this.animalDao = animalDao; // Присвоение значения параметра animalDao полю класса
    }

    @Override // Аннотация, указывающая на переопределение метода интерфейса
    public List<Animal> getAllAnimals() { // Переопределенный метод для получения списка всех животных
        return animalDao.getAllAnimals(); // Возврат результата вызова метода getAllAnimals() объекта animalDao
    }

    @Override // Аннотация, указывающая на переопределение метода интерфейса
    public void createOrUpdateAnimal(Animal animal) { // Переопределенный метод для создания или обновления информации о животном
        if (animal.getId() == 0) { // Условие проверяет, является ли идентификатор животного равным нулю
            createAnimal(animal); // Вызов метода createAnimal() с передачей объекта animal
        } else { // В противном случае
            updateAnimal(animal); // Вызов метода updateAnimal() с передачей объекта animal
        }
    }

    private void createAnimal(Animal animal) { // Приватный метод для создания животного
        animalDao.createAnimal(animal); // Вызов метода createAnimal() объекта animalDao с передачей объекта animal
    }

    private void updateAnimal(Animal animal) { // Приватный метод для обновления информации о животном
        animalDao.updateAnimal(animal); // Вызов метода updateAnimal() объекта animalDao с передачей объекта animal
    }

    @Override // Аннотация, указывающая на переопределение метода интерфейса
    public Animal readAnimal(long id) { // Переопределенный метод для чтения информации о животном по его идентификатору
        return animalDao.readAnimal(id); // Возврат результата вызова метода readAnimal() объекта animalDao с передачей идентификатора id
    }

    @Override // Аннотация, указывающая на переопределение метода интерфейса
    public Animal deleteAnimal(long id) { // Переопределенный метод для удаления животного по его идентификатору
        Animal animal = animalDao.readAnimal(id); // Инициализация переменной animal результатом вызова метода readAnimal() объекта animalDao с передачей идентификатора id
        if (animal != null) { // Условие проверяет, не является ли переменная animal нулевой
            animalDao.deleteAnimal(id); // Вызов метода deleteAnimal() объекта animalDao с передачей идентификатора id
        }
        return animal; // Возврат переменной animal
    }
}
