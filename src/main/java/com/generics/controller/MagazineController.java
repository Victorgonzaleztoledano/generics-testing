package com.generics.controller;

import com.generics.controller.Input.MagazineInput;
import com.generics.controller.Output.MagazineOutput;
import com.generics.service.MagazineServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/magazines")
@RequiredArgsConstructor
@Slf4j
public class MagazineController {
    private final MagazineServiceImp service;

    @PostMapping
    public MagazineOutput addBook(@RequestBody MagazineInput book){
        final MagazineOutput output = service.add(book);
        log.info("Magazine to be saved: {}", output.toString());
        return output;
    }

    @GetMapping
    public List<MagazineOutput> getAll(){
        final List<MagazineOutput> list = service.getAll();
        log.info("Magazines to be listed:");
        return list;
    }

    @GetMapping("/{code}")
    public MagazineOutput getBy(@PathVariable Long code) throws Exception {
        final MagazineOutput output = service.getBy(code);
        log.info("Magazine to be listed: {}", output.toString());
        return output;
    }

    @DeleteMapping("/{code}")
    public MagazineOutput deleteBook(@PathVariable Long code) throws Exception {
        final MagazineOutput output = service.delete(code);
        log.warn("Magazine to be deleted: {}", output.toString());
        return output;
    }

    @PutMapping("/{code}")
    public MagazineOutput updateBook(@PathVariable Long code, @RequestBody MagazineInput book) throws Exception {
        final MagazineOutput output = service.update(code, book);
        log.info("Magazine to be updated: {}", output.toString());
        return output;
    }
}
