package com.generics.service;

import com.generics.entity.Librarian;
import com.generics.entity.Magazine;
import com.generics.repository.LibrarianRepository;
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
public class LibrarianServiceImpTest {
    @Autowired
    private LibrarianRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Librarian( "Pepe", "Oxford Street"));
        repository.save(new Librarian( "Juan", "Victoria Avenue"));
        repository.save(new Librarian("Maria", "Churchill Lane"));
        repository.save(new Librarian("Jose", "Kensington Crescent"));
    }

    @Test
    void whenGetALibrarian_thenSaveLibrarian() {
        Librarian librarian = new Librarian( "Sergio", "Windsor Drive");

        Long countBeforeSave = repository.count();
        List<Librarian> librariansBeforeSave = repository.findAll();

        repository.save(librarian);

        Long countAfterSave = countBeforeSave + 1;
        List<Librarian> librariansAfterSave = repository.findAll();

        MatcherAssert.assertThat("Number of librarians before save is not as expected", librariansBeforeSave, hasSize(countBeforeSave.intValue()));
        MatcherAssert.assertThat("Number of librarians after save is not as expected", librariansAfterSave, hasSize(countAfterSave.intValue()));
    }

    @Test
    void whenChooseALibrarian_thenDelete() {
        Long id = 3L;

        boolean existsBeforeDelete = repository.findById(id).isPresent();
        repository.deleteById(id);
        boolean existsAfterDelete = repository.findById(id).isPresent();

        Assertions.assertTrue(existsBeforeDelete);
        Assertions.assertFalse(existsAfterDelete);
    }

    @Test
    void whenGetAll_thenReturnAllLibrarians() {
        Long size = repository.count();
        List<Librarian> librarian = repository.findAll();
        MatcherAssert.assertThat("Number of librarians retrieved is not as expected", librarian, hasSize(size.intValue()));
    }

    @Test
    void whenFindById_thenReturnLibrarian() {
        Long id = 3L;
        boolean comicExists = repository.findById(id).isPresent();
        Assertions.assertTrue(comicExists, "Librarian with id " + id + " not found");
    }

    @Test
    void whenChooseALibrarian_thenUpdate() {
        Long id = 3L;
        Optional<Librarian> librarian = repository.findById(id);


        Librarian librarianCopy = librarian.get();
        librarianCopy.setName("Juana");
        repository.save(librarianCopy);
        Assertions.assertAll(
                () -> Assertions.assertTrue(librarian.isPresent(), "Librarian with id " + id + " not found"),
                () -> Assertions.assertNotEquals("Maria", librarianCopy.getName(), "Name should be updated")
                        );
    }
}
