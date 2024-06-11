package ru.itmentor.spring.boot_security.demo.service; // Пакет, в котором находится класс

import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации Autowired из пакета org.springframework.beans.factory.annotation
import org.springframework.security.core.userdetails.UserDetailsService; // Импорт интерфейса UserDetailsService из пакета org.springframework.security.core.userdetails
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Импорт класса UsernameNotFoundException из пакета org.springframework.security.core.userdetails
import org.springframework.stereotype.Service; // Импорт аннотации Service из пакета org.springframework.stereotype
import ru.itmentor.spring.boot_security.demo.dao.VolunteerDao; // Импорт класса VolunteerDao из пакета ru.itmentor.spring.boot_security.demo.dao
import ru.itmentor.spring.boot_security.demo.model.Volunteer; // Импорт класса Volunteer из пакета ru.itmentor.spring.boot_security.demo.model
import ru.itmentor.spring.boot_security.demo.security.VolunteerDetails; // Импорт класса VolunteerDetails из пакета ru.itmentor.spring.boot_security.demo.security

import java.util.Optional; // Импорт класса Optional из пакета java.util

@Service // Аннотация, указывающая, что класс является сервисом
public class VolunteerDetailsService implements UserDetailsService { // Объявление класса VolunteerDetailsService, реализующего интерфейс UserDetailsService

    private final VolunteerDao volunteerDao; // Приватное поле volunteerDao типа VolunteerDao

    @Autowired // Аннотация, указывающая на необходимость внедрения зависимости через конструктор
    public VolunteerDetailsService(VolunteerDao volunteerDao) { // Конструктор класса с параметром volunteerDao
        this.volunteerDao = volunteerDao; // Присвоение значения параметра volunteerDao полю класса
    }

    @Override // Аннотация, указывающая на переопределение метода интерфейса
    public VolunteerDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Переопределенный метод для загрузки пользователя по его имени пользователя
        Optional<Volunteer> volunteer = volunteerDao.findByUsername(username); // Инициализация переменной volunteer результатом вызова метода findByUsername() объекта volunteerDao с передачей имени пользователя username

        if (!volunteer.isPresent()) { // Условие проверяет, присутствует ли объект volunteer
            throw new UsernameNotFoundException("Volunteer not found"); // Генерация исключения UsernameNotFoundException с сообщением "Volunteer not found"
        }

        return new VolunteerDetails(volunteer.get()); // Возврат нового объекта VolunteerDetails с передачей найденного волонтера
    }
}
