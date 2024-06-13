package com.itm.space.backendresources.mapper;  // Объявление пакета, в котором расположен интерфейс

import com.itm.space.backendresources.api.response.UserResponse;  // Импорт класса UserResponse из пакета api.response
import org.keycloak.representations.idm.GroupRepresentation;  // Импорт класса GroupRepresentation из пакета keycloak.representations.idm
import org.keycloak.representations.idm.RoleRepresentation;  // Импорт класса RoleRepresentation из пакета keycloak.representations.idm
import org.keycloak.representations.idm.UserRepresentation;  // Импорт класса UserRepresentation из пакета keycloak.representations.idm
import org.mapstruct.Mapper;  // Импорт аннотации Mapper из MapStruct
import org.mapstruct.Mapping;  // Импорт аннотации Mapping из MapStruct
import org.mapstruct.MappingConstants;  // Импорт констант MappingConstants из MapStruct
import org.mapstruct.Named;  // Импорт аннотации Named из MapStruct

import java.util.Collections;  // Импорт класса Collections из Java.util
import java.util.List;  // Импорт интерфейса List из Java.util

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = Collections.class)  // Аннотация Mapper для генерации реализации интерфейса в Spring, импорт Collections для статического импорта
public interface UserMapper {

    @Mapping(target = "roles", source = "roleList", qualifiedByName = "mapRoleRepresentationToString")  // Отображение поля roles из roleList с использованием кастомного маппинга
    @Mapping(target = "groups", source = "groupList", qualifiedByName = "mapGroupRepresentationToString")  // Отображение поля groups из groupList с использованием кастомного маппинга
    UserResponse userRepresentationToUserResponse(UserRepresentation userRepresentation,
                                                  List<RoleRepresentation> roleList,
                                                  List<GroupRepresentation> groupList);  // Метод преобразования UserRepresentation в UserResponse

    @Named("mapRoleRepresentationToString")  // Кастомное имя для метода преобразования RoleRepresentation в строку
    default List<String> mapRoleRepresentationToString(List<RoleRepresentation> roleList) {  // Дефолтный метод для преобразования списка RoleRepresentation в список строк
        return roleList.stream().map(RoleRepresentation::getName).toList();  // Преобразование каждого элемента списка RoleRepresentation в его имя и сборка в новый список
    }

    @Named("mapGroupRepresentationToString")  // Кастомное имя для метода преобразования GroupRepresentation в строку
    default List<String> mapGroupRepresentationToString(List<GroupRepresentation> groupList) {  // Дефолтный метод для преобразования списка GroupRepresentation в список строк
        return groupList.stream().map(GroupRepresentation::getName).toList();  // Преобразование каждого элемента списка GroupRepresentation в его имя и сборка в новый список
    }

}
