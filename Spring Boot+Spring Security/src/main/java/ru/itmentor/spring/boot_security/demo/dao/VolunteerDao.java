// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.dao
package ru.itmentor.spring.boot_security.demo.dao;

// Импортируем ваш класс Volunteer и необходимые классы из java.util
import ru.itmentor.spring.boot_security.demo.model.Volunteer;
import java.util.List;
import java.util.Optional;

// Объявляем интерфейс VolunteerDao
public interface VolunteerDao {
    // Метод для получения списка всех волонтеров
    List<Volunteer> getAllVolunteers();

    // Метод для создания нового волонтера
    void createVolunteer(Volunteer volunteer);

    // Метод для обновления существующего волонтера
    void updateVolunteer(Volunteer volunteer);

    // Метод для чтения информации о волонтере по его идентификатору
    Volunteer readVolunteer(long id);

    // Метод для удаления волонтера по его идентификатору
    Volunteer deleteVolunteer(long id);

    // Метод для поиска волонтера по его имени пользователя
    Optional<Volunteer> findByUsername(String username);
}
