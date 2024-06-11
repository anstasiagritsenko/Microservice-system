package ru.itmentor.spring.boot_security.demo.dao; // Объявление пакета

import org.springframework.stereotype.Repository; // Импорт аннотации Repository из пакета org.springframework.stereotype
import ru.itmentor.spring.boot_security.demo.model.Animal; // Импорт класса Animal из пакета ru.itmentor.spring.boot_security.demo.model

import javax.persistence.EntityManager; // Импорт класса EntityManager из пакета javax.persistence
import javax.persistence.PersistenceContext; // Импорт аннотации PersistenceContext из пакета javax.persistence
import java.util.List; // Импорт класса List из пакета java.util

@Repository // Аннотация, обозначающая класс как репозиторий Spring
public class AnimalDaoImpl implements AnimalDao { // Объявление класса AnimalDaoImpl, реализующего интерфейс AnimalDao

    @PersistenceContext // Аннотация для внедрения EntityManager
    private EntityManager entityManager; // Объявление переменной entityManager типа EntityManager

    @Override // Аннотация, обозначающая переопределение метода из интерфейса
    public List<Animal> getAllAnimals() { // Реализация метода для получения списка всех животных
        return entityManager.createQuery("from Animal", Animal.class).getResultList(); // Создание запроса для получения всех записей из таблицы Animal
    }

    @Override // Аннотация, обозначающая переопределение метода из интерфейса
    public void createAnimal(Animal animal) { // Реализация метода для создания нового животного
        entityManager.persist(animal); // Сохранение нового животного в базе данных
        entityManager.flush(); // Принудительное выполнение всех несохраненных изменений в базе данных
    }

    @Override // Аннотация, обозначающая переопределение метода из интерфейса
    public void updateAnimal(Animal animal) { // Реализация метода для обновления информации о животном
        entityManager.merge(animal); // Обновление информации о животном в базе данных
        entityManager.flush(); // Принудительное выполнение всех несохраненных изменений в базе данных
    }

    @Override // Аннотация, обозначающая переопределение метода из интерфейса
    public Animal readAnimal(long id) { // Реализация метода для чтения информации о животном по его идентификатору
        return entityManager.find(Animal.class, id); // Поиск животного в базе данных по его идентификатору
    }

    @Override // Аннотация, обозначающая переопределение метода из интерфейса
    public Animal deleteAnimal(long id) throws NullPointerException { // Реализация метода для удаления животного по его идентификатору
        Animal animal = readAnimal(id); // Получение информации о животном по его идентификатору
        if (null == animal) { // Проверка, существует ли животное с указанным идентификатором
            throw new NullPointerException("Animal not found"); // Генерация исключения, если животное не найдено
        }
        entityManager.remove(animal); // Удаление животного из базы данных
        entityManager.flush(); // Принудительное выполнение всех несохраненных изменений в базе данных
        return animal; // Возвращение удаленного животного
    }
}
