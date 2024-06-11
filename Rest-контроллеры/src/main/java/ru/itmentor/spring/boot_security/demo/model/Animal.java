package ru.itmentor.spring.boot_security.demo.model; // Объявление пакета


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity // Аннотация, указывающая, что данный класс является сущностью JPA
@Table(name = "animals") // Аннотация, указывающая на таблицу базы данных, с которой связана сущность

public class Animal { // Объявление класса Animal

    @Id // Аннотация, указывающая на то, что поле id является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации значений для id

    private long id; // Поле для хранения идентификатора животного

    @NotEmpty(message = "Name should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String name; // Поле для хранения имени животного

    private int age; // Поле для хранения возраста животного

    @NotEmpty(message = "Species should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String species; // Поле для хранения вида животного

    @NotEmpty(message = "Breed should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String breed; // Поле для хранения породы животного

    @NotEmpty(message = "Gender should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String gender; // Поле для хранения пола животного

    @NotEmpty(message = "Color should not be empty") // Аннотация, указывающая на то, что поле не должно быть пустым
    private String color; // Поле для хранения цвета животного

    public Animal() { // Конструктор без параметров

    }

    public Animal(String name, int age, String species, String breed, String gender, String color) {
        // Конструктор с параметрами
        this.name = name;
        this.age = age;
        this.species = species;
        this.breed = breed;
        this.gender = gender;
        this.color = color;
    }

    // Геттеры и сеттеры для доступа к полям класса

public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
