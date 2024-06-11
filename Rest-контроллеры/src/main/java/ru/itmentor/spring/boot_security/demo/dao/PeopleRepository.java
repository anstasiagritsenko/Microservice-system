// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.dao
package ru.itmentor.spring.boot_security.demo.dao;

// Импортируем необходимые классы из Spring Data JPA и ваш класс Volunteer
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmentor.spring.boot_security.demo.model.Volunteer;

import java.util.Optional;

// Объявляем интерфейс PeopleRepository, который расширяет JpaRepository
// Указываем, что JpaRepository работает с сущностью Volunteer и ключевым типом Integer
public interface PeopleRepository extends JpaRepository<Volunteer, Integer> {
    // Метод для поиска волонтера по имени пользователя
    Optional<Volunteer> findByUsername(String username);
}
