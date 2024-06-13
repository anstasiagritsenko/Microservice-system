package com.itm.space.backendresources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.api.response.UserResponse;
import com.itm.space.backendresources.controller.UserController;
import com.itm.space.backendresources.exception.BackendResourcesException;
import com.itm.space.backendresources.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class) // Используем расширение для интеграции с Spring и JUnit 5
@WebMvcTest(UserController.class) // Тестируем только UserController, автоматическая конфигурация Spring MVC
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc для выполнения HTTP запросов в тестах

    @MockBean
    private UserService userService; // MockBean для сервиса UserService

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper для сериализации объектов в JSON

    private UserRequest userRequest; // Объект UserRequest для создания пользователей
    private UserResponse userResponse; // Объект UserResponse для получения пользователей

    @BeforeEach
    public void setup() {
        // Инициализация данных для тестов
        userRequest = new UserRequest("svetlanatsvetkova", "svetlana.tsvetkova@example.com", "password", "Светлана", "Цветкова");
        userResponse = new UserResponse("Светлана", "Цветкова", "svetlana.tsvetkova@example.com", List.of("ROLE_USER"), List.of("Группа1"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR") // Моделируем авторизованного пользователя с ролью MODERATOR
    public void createUser_shouldReturn201() throws Exception {
        // Настройка mock объекта userService для метода createUser, который void (ничего не возвращает)
        Mockito.doNothing().when(userService).createUser(any(UserRequest.class));

        // Выполняем POST запрос на создание пользователя
        mockMvc.perform(post("/api/users")
                        .with(csrf())  // Добавляем CSRF токен
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());  // Ожидаем HTTP статус 201 Created
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createUser_shouldHandleValidationErrors() throws Exception {
        // Проверяем обработку ошибок валидации при создании пользователя

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\",\"email\":\"svetlana.tsvetkova@example.com\",\"password\":\"password\",\"firstName\":\"Светлана\",\"lastName\":\"Цветкова\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"svetlanatsvetkova\",\"email\":\"invalid-email\",\"password\":\"password\",\"firstName\":\"Светлана\",\"lastName\":\"Цветкова\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER") // Моделируем авторизованного пользователя с ролью USER
    public void createUser_shouldReturn403_Forbidden() throws Exception {
        // Проверяем доступ для пользователя с ролью USER при создании пользователя
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createUser_shouldReturn401_Unauthorized() throws Exception {
        // Проверяем неавторизованный доступ при создании пользователя
        mockMvc.perform(post("/api/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getUserById_shouldReturnUser() throws Exception {
        // Проверяем успешное получение пользователя по идентификатору

        UUID userId = UUID.randomUUID();
        Mockito.when(userService.getUserById(userId)).thenReturn(userResponse);

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Светлана"))
                .andExpect(jsonPath("$.lastName").value("Цветкова"))
                .andExpect(jsonPath("$.email").value("svetlana.tsvetkova@example.com"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("$.groups[0]").value("Группа1"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getUserById_shouldHandleUserNotFound() throws Exception {
        // Проверяем обработку ситуации, когда пользователь не найден

        UUID userId = UUID.randomUUID();
        Mockito.doThrow(new BackendResourcesException("Пользователь не найден", HttpStatus.NOT_FOUND))
                .when(userService).getUserById(userId);

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createUser_shouldHandleServerError() throws Exception {
        // Проверяем обработку внутренней ошибки сервера при создании пользователя

        Mockito.doThrow(new WebApplicationException("Ошибка сервера", 500))
                .when(userService).createUser(any(UserRequest.class));

        mockMvc.perform(post("/api/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isInternalServerError());
    }
}
