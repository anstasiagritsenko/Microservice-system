package com.itm.space.backendresources;

import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.api.response.UserResponse;
import com.itm.space.backendresources.exception.BackendResourcesException;
import com.itm.space.backendresources.mapper.UserMapper;
import com.itm.space.backendresources.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Интеграция с Mockito для JUnit 5
public class UserServiceImplIntegrationTest {

    @Mock
    private Keycloak keycloakClient; // Mock объект Keycloak клиента

    @Mock
    private UserMapper userMapper; // Mock объект маппера пользователей

    @InjectMocks
    private UserServiceImpl userService; // Тестируемый сервис с инъекцией моков

    @Mock
    private RealmResource realmResource; // Mock ресурса Realm

    @Mock
    private UsersResource usersResource; // Mock ресурса пользователей

    @Mock
    private UserResource userResource; // Mock ресурса конкретного пользователя

    @Mock
    private UserRepresentation userRepresentation; // Mock представления пользователя

    private UserRequest userRequest; // Объект запроса пользователя для тестирования
    private UserResponse userResponse; // Ожидаемый объект ответа пользователя для тестирования

    @Test
    public void createUser_shouldHandleNetworkError() {
        // Тестирование обработки сетевой ошибки при создании пользователя

        userRequest = new UserRequest("svetlanacvetkova", "svetlana.cvetkova@example.com", "password", "Светлана", "Цветкова");
        when(keycloakClient.realm(any())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        Mockito.doThrow(new BackendResourcesException("Network error", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(usersResource).create(any(UserRepresentation.class));

        BackendResourcesException exception = assertThrows(BackendResourcesException.class, () -> userService.createUser(userRequest));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    public void getUserById_shouldHandleUserNotFound() {
        // Тестирование обработки случая, когда пользователь не найден

        UUID userId = UUID.randomUUID();
        when(keycloakClient.realm(any())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.get(anyString())).thenReturn(userResource);
        when(userResource.toRepresentation()).thenReturn(null);

        assertThrows(BackendResourcesException.class, () -> userService.getUserById(userId));
    }

    @Test
    public void createUser_shouldCreateUser() {
        // Тестирование успешного создания пользователя

        userRequest = new UserRequest("svetlanacvetkova", "svetlana.cvetkova@example.com", "password", "Светлана", "Цветкова");
        when(keycloakClient.realm(any())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(null);

        userService.createUser(userRequest);

        Mockito.verify(usersResource).create(any(UserRepresentation.class));
    }

    @Test
    public void getUserById_shouldReturnUserResponse() {
        // Тестирование успешного получения пользователя по идентификатору

        UUID userId = UUID.randomUUID();
        userResponse = new UserResponse("Светлана", "Цветкова", "svetlana.cvetkova@example.com", Collections.emptyList(), Collections.emptyList());
        when(keycloakClient.realm(any())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.get(anyString())).thenReturn(userResource);
        when(userResource.toRepresentation()).thenReturn(userRepresentation);
        when(userMapper.userRepresentationToUserResponse(any(UserRepresentation.class), any(), any())).thenReturn(userResponse);

        UserResponse response = userService.getUserById(userId);

        assertEquals(userResponse, response);
    }
}
