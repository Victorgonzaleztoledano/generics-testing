package com.generics.controller;

import com.generics.controller.Input.LibrarianInput;
import com.generics.controller.Output.LibrarianOutput;
import com.generics.service.LibrarianServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/librarians")
@Slf4j
@RequiredArgsConstructor
public class LibrarianController {

    private final LibrarianServiceImp service;

    @PostMapping
    public LibrarianOutput add(@RequestBody LibrarianInput employee){
        final LibrarianOutput output = service.add(employee);
        log.info("Librarian to be saved: {}", output.toString());
        return output;
    }

    @GetMapping
    public List<LibrarianOutput> getAll(){
        final List<LibrarianOutput> list = service.getAll();
        log.info("Librarians to be listed:");
        return list;
    }

    @GetMapping("/{code}")
    public LibrarianOutput getBy(@PathVariable Long code) throws Exception {
        final LibrarianOutput output = service.getBy(code);
        log.info("Librarian to be listed: {}", output.toString());
        return output;
    }

    @DeleteMapping("/{code}")
    public LibrarianOutput delete(@PathVariable Long code) throws Exception {
        final LibrarianOutput output = service.delete(code);
        log.warn("Librarian to be deleted: {}", output.toString());
        return output;
    }

    @PutMapping("/{code}")
    public LibrarianOutput update(@PathVariable Long code, @RequestBody LibrarianInput employee) throws Exception {
        final LibrarianOutput output = service.update(code, employee);
        log.info("Librarian to be updated: {}", output.toString());
        return output;
    }
}
