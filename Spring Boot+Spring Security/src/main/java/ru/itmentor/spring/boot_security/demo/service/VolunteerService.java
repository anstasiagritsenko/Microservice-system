package ru.itmentor.spring.boot_security.demo.service; // Объявление пакета

import ru.itmentor.spring.boot_security.demo.model.Volunteer; // Импорт класса Volunteer из пакета ru.itmentor.spring.boot_security.demo.model

import java.util.List; // Импорт класса List из пакета java.util

public interface VolunteerService { // Объявление интерфейса VolunteerService

    List<Volunteer> getAllVolunteers(); // Метод для получения списка всех волонтеров

    Volunteer readVolunteer(long id); // Метод для чтения информации о волонтере по его идентификатору

    Volunteer deleteVolunteer(long id); // Метод для удаления волонтера по его идентификатору

    void createOrUpdateVolunteer(Volunteer volunteer); // Метод для создания или обновления информации о волонтере
}
