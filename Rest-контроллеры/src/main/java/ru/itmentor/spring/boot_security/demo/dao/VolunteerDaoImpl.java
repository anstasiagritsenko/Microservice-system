// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.dao
package ru.itmentor.spring.boot_security.demo.dao;

// Импортируем необходимые классы и аннотации
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Volunteer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

// Обозначаем этот класс как компонент репозитория
@Repository
public class VolunteerDaoImpl implements VolunteerDao {

    // Внедряем EntityManager для управления сущностями JPA
    @PersistenceContext
    private EntityManager entityManager;

    // Метод для получения списка всех волонтеров
    @Override
    public List<Volunteer> getAllVolunteers() {
        // Выполняем запрос JPQL для получения всех волонтеров
        return entityManager.createQuery("from Volunteer", Volunteer.class).getResultList();
    }

    // Метод для создания нового волонтера
    @Override
    public void createVolunteer(Volunteer volunteer) {
        // Сохраняем нового волонтера в базе данных
        entityManager.persist(volunteer);
        // Принуждаем EntityManager записать изменения в базу данных
        entityManager.flush();
    }

    // Метод для обновления существующего волонтера
    @Override
    public void updateVolunteer(Volunteer volunteer) {
        // Обновляем данные волонтера в базе данных
        entityManager.merge(volunteer);
        // Принуждаем EntityManager записать изменения в базу данных
        entityManager.flush();
    }

    // Метод для чтения информации о волонтере по его идентификатору
    @Override
    public Volunteer readVolunteer(long id) {
        // Находим волонтера по его идентификатору
        return entityManager.find(Volunteer.class, id);
    }

    // Метод для удаления волонтера по его идентификатору
    @Override
    public Volunteer deleteVolunteer(long id) {
        // Находим волонтера по его идентификатору
        Volunteer volunteer = readVolunteer(id);
        // Если волонтер найден, удаляем его из базы данных
        if (volunteer != null) {
            entityManager.remove(volunteer);
            // Принуждаем EntityManager записать изменения в базу данных
            entityManager.flush();
        }
        // Возвращаем удаленного волонтера
        return volunteer;
    }

    // Метод для поиска волонтера по его имени пользователя
    @Override
    public Optional<Volunteer> findByUsername(String username) {
        // Выполняем запрос JPQL для поиска волонтера по имени пользователя
        List<Volunteer> volunteers = entityManager.createQuery("from Volunteer where username = :username", Volunteer.class)
                .setParameter("username", username)
                .getResultList();
        // Возвращаем первого найденного волонтера в виде Optional
        return volunteers.stream().findFirst();
    }
}
