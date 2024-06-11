package ru.itmentor.spring.boot_security.demo.service; // Объявление пакета

import org.springframework.stereotype.Service; // Импорт аннотации @Service из пакета org.springframework.stereotype
import org.springframework.transaction.annotation.Transactional; // Импорт аннотации @Transactional из пакета org.springframework.transaction.annotation
import ru.itmentor.spring.boot_security.demo.dao.VolunteerDao; // Импорт класса VolunteerDao из пакета ru.itmentor.spring.boot_security.demo.dao
import ru.itmentor.spring.boot_security.demo.model.Volunteer; // Импорт класса Volunteer из пакета ru.itmentor.spring.boot_security.demo.model

import java.util.List; // Импорт класса List из пакета java.util

@Service // Объявление класса как сервисного компонента
@Transactional // Объявление транзакционного поведения
public class VolunteerServiceImpl implements VolunteerService { // Объявление класса VolunteerServiceImpl, реализующего интерфейс VolunteerService

    private final VolunteerDao volunteerDao; // Объявление поля volunteerDao типа VolunteerDao

    public VolunteerServiceImpl(VolunteerDao volunteerDao) { // Конструктор класса с параметром volunteerDao
        this.volunteerDao = volunteerDao; // Инициализация поля volunteerDao
    }

    @Override // Переопределение метода
    public List<Volunteer> getAllVolunteers() { // Объявление метода getAllVolunteers
        return volunteerDao.getAllVolunteers(); // Возврат списка всех волонтеров из volunteerDao
    }

    @Override // Переопределение метода
    public void createOrUpdateVolunteer(Volunteer volunteer) { // Объявление метода createOrUpdateVolunteer с параметром volunteer
        if (volunteer.getId() == 0) { // Если идентификатор волонтера равен 0
            createVolunteer(volunteer); // Вызов метода createVolunteer
        } else { // В противном случае
            updateVolunteer(volunteer); // Вызов метода updateVolunteer
        }
    }

    public void createVolunteer(Volunteer volunteer) { // Объявление приватного метода createVolunteer с параметром volunteer
        volunteerDao.createVolunteer(volunteer); // Вызов метода createVolunteer из volunteerDao
    }

    public void updateVolunteer(Volunteer volunteer) { // Объявление приватного метода updateVolunteer с параметром volunteer
        volunteerDao.updateVolunteer(volunteer); // Вызов метода updateVolunteer из volunteerDao
    }

    @Override // Переопределение метода
    public Volunteer readVolunteer(long id) { // Объявление метода readVolunteer с параметром id
        return volunteerDao.readVolunteer(id); // Возврат волонтера по его идентификатору из volunteerDao
    }

    @Override // Переопределение метода
    public Volunteer deleteVolunteer(long id) { // Объявление метода deleteVolunteer с параметром id
        Volunteer volunteer = volunteerDao.readVolunteer(id); // Получение волонтера по его идентификатору из volunteerDao
        if (volunteer != null) { // Если волонтер найден
            volunteerDao.deleteVolunteer(id); // Удаление волонтера из volunteerDao
        }
        return volunteer; // Возврат удаленного волонтера
    }
}
