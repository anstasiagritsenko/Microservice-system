package ru.itmentor.spring.boot_security.demo.security; // Пакет, в котором находится класс

import org.springframework.security.core.GrantedAuthority; // Импорт интерфейса GrantedAuthority из Spring Security
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Импорт класса SimpleGrantedAuthority из Spring Security
import org.springframework.security.core.userdetails.UserDetails; // Импорт интерфейса UserDetails из Spring Security
import ru.itmentor.spring.boot_security.demo.model.Volunteer; // Импорт класса Volunteer из пакета ru.itmentor.spring.boot_security.demo.model

import java.util.Collection; // Импорт интерфейса Collection из пакета java.util
import java.util.Collections; // Импорт класса Collections из пакета java.util

public class VolunteerDetails implements UserDetails { // Объявление класса VolunteerDetails, реализующего интерфейс UserDetails из Spring Security

    private final Volunteer volunteer; // Объявление переменной volunteer типа Volunteer

    public VolunteerDetails(Volunteer volunteer) { // Объявление конструктора VolunteerDetails с параметром volunteer типа Volunteer
        this.volunteer = volunteer; // Присваивание переменной volunteer переданное значение
    }

    @Override // Аннотация, указывающая на переопределение метода
    public Collection<? extends GrantedAuthority> getAuthorities() { // Объявление метода getAuthorities, возвращающего коллекцию GrantedAuthority
        return Collections.singleton(new SimpleGrantedAuthority(volunteer.getUserRole())); // Возвращение коллекции с одним элементом - роль пользователя
    }

    @Override // Аннотация, указывающая на переопределение метода
    public String getPassword() { // Объявление метода getPassword, возвращающего пароль пользователя
        return volunteer.getPassword(); // Возвращение пароля пользователя
    }

    @Override // Аннотация, указывающая на переопределение метода
    public String getUsername() { // Объявление метода getUsername, возвращающего имя пользователя
        return volunteer.getUsername(); // Возвращение имени пользователя
    }

    @Override // Аннотация, указывающая на переопределение метода
    public boolean isAccountNonExpired() { // Объявление метода isAccountNonExpired, проверяющего неистекла ли учетная запись пользователя
        return true; // Возвращение значения true
    }

    @Override // Аннотация, указывающая на переопределение метода
    public boolean isAccountNonLocked() { // Объявление метода isAccountNonLocked, проверяющего не заблокирована ли учетная запись пользователя
        return true; // Возвращение значения true
    }

    @Override // Аннотация, указывающая на переопределение метода
    public boolean isCredentialsNonExpired() { // Объявление метода isCredentialsNonExpired, проверяющего не истекли ли учетные данные пользователя
        return true; // Возвращение значения true
    }

    @Override // Аннотация, указывающая на переопределение метода
    public boolean isEnabled() { // Объявление метода isEnabled, проверяющего активна ли учетная запись пользователя
        return true; // Возвращение значения true
    }

    public Volunteer getVolunteer() { // Объявление метода getVolunteer, возвращающего объект волонтера
        return volunteer; // Возвращение объекта волонтера
    }
}
