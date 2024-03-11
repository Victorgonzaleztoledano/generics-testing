package com.generics.controller;

import com.generics.controller.Input.BookInput;
import com.generics.controller.Output.BookOutput;
import com.generics.service.BookServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/books")
public class BookController {

    private final BookServiceImp service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookOutput addBook(@RequestBody BookInput book) throws Exception {
        final BookOutput output = service.add(book);
        log.info("Book to be saved: {}", output.toString());
        return output;
    }

    @GetMapping
    public List<BookOutput> getAll() throws Exception {
        final List<BookOutput> list = service.getAll();
        log.info("Books to be listed:");
        return list;
    }

    @GetMapping("/{code}")
    public BookOutput getBy(@PathVariable Long code) throws Exception {
        final BookOutput output = service.getBy(code);
        log.info("Book to be listed: {}", output.toString());
        return output;
    }

    @DeleteMapping("/{code}")
    public BookOutput deleteBook(@PathVariable Long code) throws Exception {
        final BookOutput output = service.delete(code);
        log.warn("Book to be deleted: {}", output.toString());
        return output;
    }

    @PutMapping("/{code}")
    public BookOutput updateBook(@PathVariable Long code, @RequestBody BookInput book) throws Exception {
        final BookOutput output = service.update(code, book);
        log.info("Book to be updated: {}", output.toString());
        return output;
    }
}
