package com.generics.service;

import com.generics.entity.Book;
import com.generics.repository.BookRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;


@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookServiceImpTest {
    @Autowired
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Book("Quijote", "Pepe", 90));
        repository.save(new Book("Celestina", "Juan", 12));
        repository.save(new Book("Moby dick", "Maria", 32));
        repository.save(new Book("Sherlock Holmes", "Jose", 45));
    }

    @Test
    void whenGetABook_thenSaveBook() {
        Book book = new Book("El principito", "Sergio", 76);

        Long countBeforeSave = repository.count();
        List<Book> booksBeforeSave = repository.findAll();

        repository.save(book);

        Long countAfterSave = countBeforeSave + 1;
        List<Book> booksAfterSave = repository.findAll();

        MatcherAssert.assertThat("Number of books before save is not as expected", booksBeforeSave, hasSize(countBeforeSave.intValue()));
        MatcherAssert.assertThat("Number of books after save is not as expected", booksAfterSave, hasSize(countAfterSave.intValue()));
    }

    @Test
    void whenChooseABook_thenDelete() {
        Long id = 3L;

        boolean existsBeforeDelete = repository.findById(id).isPresent();
        repository.deleteById(id);
        boolean existsAfterDelete = repository.findById(id).isPresent();
        Assertions.assertAll(
                () -> Assertions.assertTrue(existsBeforeDelete),
                () -> Assertions.assertFalse(existsAfterDelete)
        );
    }

    @Test
    void whenGetAll_thenReturnAllBooks() {
        Long size = repository.count();
        List<Book> books = repository.findAll();
        MatcherAssert.assertThat("Number of books retrieved is not as expected", books, hasSize(size.intValue()));
    }

    @Test
    void whenFindById_thenReturnBook() {
        Long id = 3L;
        boolean bookExists = repository.findById(id).isPresent();
        Assertions.assertTrue(bookExists, "Book with id " + id + " not found");
    }

    @Test
    void whenChooseABook_thenUpdate() {
        Long id = 3L;
        Optional<Book> book = repository.findById(id);

        Book bookCopy = book.get();
        bookCopy.setPages(23);
        repository.save(bookCopy);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(32, bookCopy.getPages(), "Pages should be updated"),
                () -> Assertions.assertTrue(book.isPresent(), "Book with id " + id + " not found")
        );
    }
}