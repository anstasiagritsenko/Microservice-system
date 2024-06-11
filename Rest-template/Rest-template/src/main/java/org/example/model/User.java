package org.example.model; // Определение пакета

public class User { // Объявление класса User

    // Поля класса
    private Long id; // Идентификатор пользователя
    private String name; // Имя пользователя
    private String lastName; // Фамилия пользователя
    private Byte age; // Возраст пользователя
    private String email; // Электронная почта пользователя

    // Методы доступа к полям класса (геттеры и сеттеры)
    public Long getId() { // Метод получения идентификатора
        return id;
    }

    public void setId(Long id) { // Метод установки идентификатора
        this.id = id;
    }

    public String getName() { // Метод получения имени
        return name;
    }

    public void setName(String name) { // Метод установки имени
        this.name = name;
    }

    public String getLastName() { // Метод получения фамилии
        return lastName;
    }

    public void setLastName(String lastName) { // Метод установки фамилии
        this.lastName = lastName;
    }

    public Byte getAge() { // Метод получения возраста
        return age;
    }

    public void setAge(Byte age) { // Метод установки возраста
        this.age = age;
    }

    public String getEmail() { // Метод получения электронной почты
        return email;
    }

    public void setEmail(String email) { // Метод установки электронной почты
        this.email = email;
    }

    // Конструкторы класса
    public User() { // Конструктор по умолчанию
    }

    public User(Long id, String name, String lastName, Byte age, String email) { // Конструктор с параметрами
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

}
