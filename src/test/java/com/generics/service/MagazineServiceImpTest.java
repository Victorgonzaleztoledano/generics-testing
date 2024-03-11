package com.generics.service;

import com.generics.entity.Magazine;
import com.generics.repository.MagazineRepository;
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
public class MagazineServiceImpTest {
    @Autowired
    private MagazineRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Magazine("Quijote", "Pepe", 90));
        repository.save(new Magazine("Celestina", "Juan", 12));
        repository.save(new Magazine("Moby dick", "Maria", 32));
        repository.save(new Magazine("Sherlock Holmes", "Jose", 45));
    }

    @Test
    void whenGetAMagazine_thenSaveMagazine() {
        Magazine magazine = new Magazine("El principito", "Sergio", 76);

        Long countBeforeSave = repository.count();
        List<Magazine> magazinesBeforeSave = repository.findAll();

        repository.save(magazine);

        Long countAfterSave = countBeforeSave + 1;
        List<Magazine> magazinesAfterSave = repository.findAll();

        MatcherAssert.assertThat("Number of magazines before save is not as expected", magazinesBeforeSave, hasSize(countBeforeSave.intValue()));
        MatcherAssert.assertThat("Number of magazines after save is not as expected", magazinesAfterSave, hasSize(countAfterSave.intValue()));
    }

    @Test
    void whenChooseAMagazine_thenDelete() {
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
    void whenGetAll_thenReturnAllMagazines() {
        Long size = repository.count();
        List<Magazine> magazines = repository.findAll();
        MatcherAssert.assertThat("Number of magazines retrieved is not as expected", magazines, hasSize(size.intValue()));
    }

    @Test
    void whenFindById_thenReturnMagazine() {
        Long id = 3L;
        boolean comicExists = repository.findById(id).isPresent();
        Assertions.assertTrue(comicExists, "Magazine with id " + id + " not found");
    }

    @Test
    void whenChooseAMagazine_thenUpdate() {
        Long id = 3L;
        Optional<Magazine> comic = repository.findById(id);

        Magazine magazineCopy = comic.get();
        magazineCopy.setPages(23);
        repository.save(magazineCopy);
        Assertions.assertAll(
                () -> Assertions.assertNotEquals(32, magazineCopy.getPages(), "Pages should be updated"),
                () -> Assertions.assertTrue(comic.isPresent(), "Comic with id " + id + " not found")
        );
    }
}
