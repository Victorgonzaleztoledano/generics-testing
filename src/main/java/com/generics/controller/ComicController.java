package com.generics.controller;

import com.generics.controller.Input.ComicInput;
import com.generics.controller.Output.ComicOutput;
import com.generics.service.ComicServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/comics")
@Slf4j
@RequiredArgsConstructor
public class ComicController {
    private final ComicServiceImp service;

    @PostMapping
    public ComicOutput addBook(@RequestBody ComicInput book){
        final ComicOutput output = service.add(book);
        log.info("Comic to be saved: {}", output.toString());
        return output;
    }

    @GetMapping
    public List<ComicOutput> getAll(){
        final List<ComicOutput> list = service.getAll();
        log.info("Comics to be listed:");
        return list;
    }

    @GetMapping("/{code}")
    public ComicOutput getBy(@PathVariable Long code) throws Exception {
        final ComicOutput output = service.getBy(code);
        log.info("Comic to be listed: {}", output.toString());
        return output;
    }

    @DeleteMapping("/{code}")
    public ComicOutput deleteBook(@PathVariable Long code) throws Exception {
        final ComicOutput output = service.delete(code);
        log.warn("Comic to be deleted: {}", output.toString());
        return output;
    }

    @PutMapping("/{code}")
    public ComicOutput updateBook(@PathVariable Long code, @RequestBody ComicInput book) throws Exception {
        final ComicOutput output = service.update(code, book);
        log.info("Comic to be updated: {}", output.toString());
        return output;
    }
}
