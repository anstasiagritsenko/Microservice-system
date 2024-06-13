package com.itm.space.backendresources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest  // Аннотация для настройки тестового контекста Spring Boot приложения
@AutoConfigureMockMvc  // Аннотация для автоматической настройки MockMvc
public abstract class BaseIntegrationTest {

    // Объект ObjectMapper для преобразования объектов в JSON
    private final ObjectWriter contentWriter = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE, false)  // Отключение обертки корневого значения
            .writer()
            .withDefaultPrettyPrinter();  // Поддержка красивого форматирования вывода JSON

    @Autowired
    protected MockMvc mvc;  // MockMvc для выполнения запросов в тестах

    // Метод для добавления заголовка Content-Type с типом APPLICATION_JSON в запрос
    protected MockHttpServletRequestBuilder requestToJson(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder
                .contentType(APPLICATION_JSON);
    }

    // Метод для добавления содержимого в запрос в формате JSON
    protected MockHttpServletRequestBuilder requestWithContent(MockHttpServletRequestBuilder requestBuilder,
                                                               Object content) throws JsonProcessingException {
        return requestToJson(requestBuilder).content(contentWriter.writeValueAsString(content));
    }
}
