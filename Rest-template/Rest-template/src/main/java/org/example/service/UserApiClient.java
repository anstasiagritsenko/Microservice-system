package org.example.service; // Определение пакета

import org.example.model.User; // Импорт класса User из пакета org.example.model
import org.slf4j.Logger; // Импорт интерфейса Logger из пакета org.slf4j
import org.slf4j.LoggerFactory; // Импорт класса LoggerFactory из пакета org.slf4j

import org.springframework.http.*; // Импорт классов из пакета org.springframework.http
import org.springframework.stereotype.Service; // Импорт аннотации @Service из пакета org.springframework.stereotype
import org.springframework.web.client.RestTemplate; // Импорт класса RestTemplate из пакета org.springframework.web.client

import java.util.Objects; // Импорт класса Objects из пакета java.util

@Service // Аннотация, указывающая, что класс является сервисом
public class UserApiClient { // Объявление класса UserApiClient

    private static final Logger logger = LoggerFactory.getLogger(UserApiClient.class); // Инициализация логгера

    private final String url = "http://94.198.50.185:7081/api/users"; // Инициализация константы url с адресом API
    private final RestTemplate restTemplate = new RestTemplate(); // Инициализация RestTemplate
    private final HttpHeaders headers = new HttpHeaders(); // Инициализация HttpHeaders
    private static StringBuilder result = new StringBuilder(); // Инициализация StringBuilder для результата

    public static void main(String[] args) { // Метод main для запуска приложения
        UserApiClient userClient = new UserApiClient(); // Создание экземпляра класса UserApiClient
        try { // Обработка исключений
            userClient.performOperations(); // Выполнение операций
            if (result.length() != 18) { // Проверка длины результата
                logger.error("Ошибка: итоговый код имеет неправильную длину"); // Логгирование ошибки
            } else {
                logger.info("Итоговый код - {}", result); // Логгирование успешного завершения с результатом
            }
        } catch (Exception e) { // Обработка исключений
            logger.error("Ошибка выполнения операций: ", e); // Логгирование ошибки выполнения операций
        }
    }

    public void performOperations() { // Метод для выполнения операций
        String sessionId = getAllUsers(); // Получение сессионного идентификатора
        headers.set("cookie", sessionId); // Установка заголовка Cookie
        createUser(); // Создание пользователя
        updateUser(); // Обновление пользователя
        deleteUser(3L); // Удаление пользователя
    }

    private String getAllUsers() { // Метод для получения всех пользователей
        try { // Обработка исключений
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); // Выполнение GET запроса
            return String.join(";", Objects.requireNonNull(response.getHeaders().get("set-cookie"))); // Возвращение значения заголовка Set-Cookie
        } catch (Exception e) { // Обработка исключений
            logger.error("Ошибка получения всех пользователей: ", e); // Логгирование ошибки
            throw e; // Пробрасывание исключения
        }
    }

    private void createUser() { // Метод для создания пользователя
        try { // Обработка исключений
            User user = new User(3L, "James", "Brown", (byte) 30, "james.brown@example.com"); // Создание объекта пользователя
            HttpEntity<User> entity = new HttpEntity<>(user, headers); // Создание HTTP сущности
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class); // Выполнение POST запроса
            result.append(response.getBody()); // Добавление результата в StringBuilder
        } catch (Exception e) { // Обработка исключений
            logger.error("Ошибка создания пользователя: ", e); // Логгирование ошибки
            throw e; // Пробрасывание исключения
        }
    }

    private void updateUser() { // Метод для обновления пользователя
        try { // Обработка исключений
            User user = new User(3L, "Thomas", "Shelby", (byte) 30, "thomas.shelby@example.com"); // Создание объекта пользователя
            HttpEntity<User> entity = new HttpEntity<>(user, headers); // Создание HTTP сущности
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class); // Выполнение PUT запроса
            result.append(response.getBody()); // Добавление результата в StringBuilder
        } catch (Exception e) { // Обработка исключений
            logger.error("Ошибка обновления пользователя: ", e); // Логгирование ошибки
            throw e; // Пробрасывание исключения
        }
    }

    private void deleteUser(Long id) { // Метод для удаления пользователя
        try { // Обработка исключений
            HttpEntity<User> entity = new HttpEntity<>(headers); // Создание HTTP сущности
            ResponseEntity<String> response = restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, String.class); // Выполнение DELETE запроса
            result.append(response.getBody()); // Добавление результата в StringBuilder
        } catch (Exception e) { // Обработка исключений
            logger.error("Ошибка удаления пользователя: ", e); // Логгирование ошибки
            throw e; // Пробрасывание исключения
        }
    }
}
