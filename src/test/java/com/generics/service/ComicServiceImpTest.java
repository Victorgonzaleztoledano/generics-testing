package com.generics.service;

import com.generics.entity.Book;
import com.generics.entity.Comic;
import com.generics.repository.ComicRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ComicServiceImpTest {
    @Autowired
    private ComicRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Comic("Quijote", "Pepe", 90));
        repository.save(new Comic("Celestina", "Juan", 12));
        repository.save(new Comic("Moby dick", "Maria", 32));
        repository.save(new Comic("Sherlock Holmes", "Jose", 45));
    }

    @Test
    void whenGetAComic_thenSaveComic() {
        Comic comic = new Comic("El principito", "Sergio", 76);

        Long countBeforeSave = repository.count();
        List<Comic> comicsBeforeSave = repository.findAll();

        repository.save(comic);

        Long countAfterSave = countBeforeSave + 1;
        List<Comic> comicsAfterSave = repository.findAll();

        MatcherAssert.assertThat("Number of comics before save is not as expected", comicsBeforeSave, hasSize(countBeforeSave.intValue()));
        MatcherAssert.assertThat("Number of comics after save is not as expected", comicsAfterSave, hasSize(countAfterSave.intValue()));
    }

    @Test
    void whenChooseAComic_thenDelete() {
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
    void whenGetAll_thenReturnAllComics() {
        Long size = repository.count();
        List<Comic> comics = repository.findAll();
        MatcherAssert.assertThat("Number of comics retrieved is not as expected", comics, hasSize(size.intValue()));
    }

    @Test
    void whenFindById_thenReturnComic() {
        Long id = 3L;
        boolean comicExists = repository.findById(id).isPresent();
        Assertions.assertTrue(comicExists, "Comic with id " + id + " not found");
    }

    @Test
    void whenChooseAComic_thenUpdate() {
        Long id = 3L;
        Optional<Comic> comic = repository.findById(id);


        Comic comicCopy = comic.get();
        comicCopy.setPages(23);
        repository.save(comicCopy);
        Assertions.assertAll(
                () -> Assertions.assertTrue(comic.isPresent(), "Comic with id " + id + " not found"),
                () -> Assertions.assertNotEquals(32, comicCopy.getPages(), "Pages should be updated")
        );
    }
}
