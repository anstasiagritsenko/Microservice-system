package ru.itmentor.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity // Аннотация, указывающая, что данный класс является сущностью JPA
@Table(name = "volunteers") // Аннотация, указывающая на таблицу базы данных, с которой связана сущность
public class Volunteer { // Объявление класса Volunteer

    @Id // Аннотация, указывающая на то, что поле id является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации значений для id
    private long id; // Поле для хранения идентификатора волонтера

    @NotEmpty(message = "First name should not be empty")
    // Аннотация, указывающая на то, что поле не должно быть пустым
    private String firstName; // Поле для хранения имени волонтера

    @NotEmpty(message = "Last name should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String lastName; // Поле для хранения фамилии волонтера

    @NotEmpty(message = "Position should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String position; // Поле для хранения должности волонтера

    @NotEmpty(message = "User role should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String userRole; // Поле для хранения роли пользователя волонтера

    @NotEmpty(message = "Username should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String username; // Поле для хранения логина волонтера

    @NotEmpty(message = "Password should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String password; // Поле для хранения пароля волонтера

    public Volunteer() { // Конструктор без параметров

    }

    // Метод для получения идентификатора волонтера
    public long getId() {
        return id;
    }

    // Метод для установки идентификатора волонтера
    public void setId(long id) {
        this.id = id;
    }

    // Метод для получения имени волонтера
    public @NotEmpty(message = "Имя не должно быть пустым") String getFirstName() {
        return firstName;
    }

    // Метод для установки имени волонтера
    public void setFirstName(@NotEmpty(message = "Имя не должно быть пустым") String firstName) {
        this.firstName = firstName;
    }

    // Метод для получения фамилии волонтера
    public @NotEmpty(message = "Фамилия не должна быть пустой") String getLastName() {
        return lastName;
    }

    // Метод для установки фамилии волонтера
    public void setLastName(@NotEmpty(message = "Фамилия не должна быть пустой") String lastName) {
        this.lastName = lastName;
    }

    // Метод для получения должности волонтера
    public @NotEmpty(message = "Должность не должна быть пустой") String getPosition() {
        return position;
    }

    // Метод для установки должности волонтера
    public void setPosition(@NotEmpty(message = "Должность не должна быть пустой") String position) {
        this.position = position;
    }

    // Метод для получения роли пользователя волонтера
    public @NotEmpty(message = "Роль пользователя не должна быть пустой") String getUserRole() {
        return userRole;
    }

    // Метод для установки роли пользователя волонтера
    public void setUserRole(@NotEmpty(message = "Роль пользователя не должна быть пустой") String userRole) {
        this.userRole = userRole;
    }

    // Метод для получения имени пользователя волонтера
    public @NotEmpty(message = "Имя пользователя не должно быть пустым") String getUsername() {
        return username;
    }

    // Метод для установки имени пользователя волонтера
    public void setUsername(@NotEmpty(message = "Имя пользователя не должно быть пустым") String username) {
        this.username = username;
    }

    // Метод для получения пароля волонтера
    public @NotEmpty(message = "Пароль не должен быть пустым") String getPassword() {
        return password;
    }

    // Метод для установки пароля волонтера
    public void setPassword(@NotEmpty(message = "Пароль не должен быть пустым") String password) {
        this.password = password;
    }


    public Volunteer(String firstName, String lastName, String position, String userRole, String username, String password) {
        // Конструктор с параметрами
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.userRole = userRole;
        this.username = username;
        this.password = password;
    }

}