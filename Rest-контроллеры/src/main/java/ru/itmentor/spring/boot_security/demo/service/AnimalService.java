package ru.itmentor.spring.boot_security.demo.service; // Пакет, в котором находится интерфейс

import ru.itmentor.spring.boot_security.demo.model.Animal; // Импорт класса Animal из пакета ru.itmentor.spring.boot_security.demo.model

import java.util.List; // Импорт интерфейса List из пакета java.util

public interface AnimalService { // Объявление интерфейса AnimalService

    List<Animal> getAllAnimals(); // Объявление метода для получения списка всех животных

    Animal readAnimal(long id); // Объявление метода для чтения информации о животном по его идентификатору

    Animal deleteAnimal(long id); // Объявление метода для удаления животного по его идентификатору

    Animal createOrUpdateAnimal(Animal animal); // Объявление метода для создания или обновления информации о животном
}
