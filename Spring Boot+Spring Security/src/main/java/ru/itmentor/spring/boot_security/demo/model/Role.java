package ru.itmentor.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// Указываем, что класс является сущностью JPA
@Entity
public class Role implements GrantedAuthority {

    // Уникальный идентификатор роли, генерируемый автоматически
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название роли
    private String name;

    // Конструктор по умолчанию
    public Role() {
        // Конструктор без параметров
    }

    // Конструктор с параметрами
    public Role(String name) {
        this.name = name;
    }

    // Геттер для id
    public Long getId() {
        return id;
    }

    // Сеттер для id
    public void setId(Long id) {
        this.id = id;
    }

    // Геттер для name
    public String getName() {
        return name;
    }

    // Сеттер для name
    public void setName(String name) {
        this.name = name;
    }

    // Реализация метода интерфейса GrantedAuthority, возвращающего название роли
    @Override
    public String getAuthority() {
        return name;
    }
}
