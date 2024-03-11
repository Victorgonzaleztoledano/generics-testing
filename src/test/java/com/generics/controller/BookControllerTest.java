package com.generics.controller;

import com.generics.controller.Input.BookInput;
import com.generics.controller.Output.BookOutput;
import com.generics.service.BookServiceImp;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImp service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldAddBook() throws Exception {
        BookOutput expectedOutput = new BookOutput(1L, "Quijote", "Cervantes", 400);
        Mockito.when(service.add(any(BookInput.class))).thenReturn(expectedOutput);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Quijote\",\"author\":\"Cervantes\",\"pages\":400}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Quijote"))
                .andExpect(jsonPath("$.author").value("Cervantes"))
                .andExpect(jsonPath("$.pages").value(400));
    }
    @Test
    void shouldFailAtAddingBook() throws Exception {
        Mockito.when(service.add(any(BookInput.class))).thenThrow(new Exception("Error saving book"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Quijote\",\"author\":\"Cervantes\",\"pages\":400}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Error saving book"));
    }

    @Test
    void shouldReturnListOfBooks() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(
                new BookOutput(1L, "Quijote", "Cervantes", 400),
                new BookOutput(2L, "Celestina", "Fernando de Rojas", 200)));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].title").value("Quijote"))
                .andExpect(jsonPath("$.[0].author").value("Cervantes"))
                .andExpect(jsonPath("$.[0].pages").value(400))
                .andExpect(jsonPath("$.[1].title").value("Celestina"))
                .andExpect(jsonPath("$.[1].author").value("Fernando de Rojas"))
                .andExpect(jsonPath("$.[1].pages").value(200));
    }
    @Test
    void shouldFailAtReturningListOfBooks() throws Exception {
        Mockito.when(service.getAll()).thenThrow(new Exception("Error returning books"));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Error returning books"));
    }

    @Test
    void shouldReturnOneBook() throws Exception {
        Long code = 1L;
        BookOutput book = new BookOutput(code, "Quijote", "Cervantes", 400);
        Mockito.when(service.getBy(code)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books/{code}", code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Quijote"))
                .andExpect(jsonPath("$.author").value("Cervantes"))
                .andExpect(jsonPath("$.pages").value(400));
    }
    @Test
    void shouldFailAtReturningOneBook() throws Exception {
        Long code = 1L;
        Mockito.when(service.getBy(code)).thenThrow(new Exception("Error returning book"));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books/{code}", code))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Error returning book"));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        Long code = 1L;
        BookOutput book = new BookOutput(code, "Quijote", "Cervantes", 400);
        Mockito.when(service.delete(code)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/books/{code}", code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Quijote"))
                .andExpect(jsonPath("$.author").value("Cervantes"))
                .andExpect(jsonPath("$.pages").value(400));
    }
    @Test
    void shouldFailAtDeletingBook() throws Exception {
        Long code = 1L;
        Mockito.when(service.delete(code)).thenThrow(new Exception("Error deleting book"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/books/{code}", code))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Error deleting book"));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        Long code = 1L;
        BookOutput bookUpdated = new BookOutput(code, "Quijote", "Cervantes", 400);

        Mockito.when(service.update(eq(code), any(BookInput.class))).thenReturn(bookUpdated);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/v1/books/{code}", code)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Celestina\",\"author\":\"Fernando de Rojas\",\"pages\":200}"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Quijote"))
                .andExpect(jsonPath("$.author").value("Cervantes"))
                .andExpect(jsonPath("$.pages").value(400));
    }

    @Test
    void shouldFailAtUpdatingBook() throws Exception {
        Long code = 1L;

        Mockito.when(service.update(eq(code), any(BookInput.class))).thenThrow(new Exception("Error updating book"));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/v1/books/{code}", code)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Celestina\",\"author\":\"Fernando de Rojas\",\"pages\":200}"));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Error updating book"));
    }
}