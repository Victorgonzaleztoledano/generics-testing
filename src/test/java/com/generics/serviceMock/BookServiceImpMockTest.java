package com.generics.serviceMock;

import com.generics.controller.Input.BookInput;
import com.generics.controller.Output.BookOutput;
import com.generics.entity.Book;
import com.generics.repository.BookRepository;
import com.generics.service.BookServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImpMockTest {
    @Mock
    private BookRepository repository;
    @InjectMocks
    private BookServiceImp service;

    @Test
    void add() throws Exception {
        Book bookSave = new Book(2L, "A", "A", 90);
        when(repository.save(Mockito.any(Book.class))).thenReturn(bookSave);

        Long beforeSave = repository.count();

        BookOutput book = service.add(new BookInput("A", "A", 90));
        Long afterSave = beforeSave + 1;

        Assertions.assertAll(
                () -> Assertions.assertNotNull(book),
                () -> Assertions.assertEquals(bookSave.getTitle(), book.getTitle()),
                () -> Assertions.assertEquals(0, beforeSave),
                () -> Assertions.assertEquals(1, afterSave)
        );
    }
    @Test
    void delete() {
        repository.delete(new Book(1L, "A", "A", 90));
        Book book = repository.findById(1L).orElse(null);
        Mockito.doNothing().when(repository).delete(book);

        verify(repository, times(1)).delete(book);
    }
}
