package com.generics.service;

import com.generics.controller.Input.ComicInput;
import com.generics.controller.Output.ComicOutput;
import com.generics.entity.Comic;
import com.generics.repository.ComicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComicServiceImp implements BookService<ComicOutput, ComicInput>{
    private final ComicRepository repository;

    @Override
    public ComicOutput add(ComicInput book) {
        Comic newComic = Comic.getComic(book);
        repository.save(newComic);
        ComicOutput output = ComicOutput.getOutput(newComic);
        return output;
    }

    @Override
    public ComicOutput update(Long code, ComicInput comic) throws Exception {
        final Comic oldComic = repository.findById(code).orElse(null);
        if(oldComic == null) throw new Exception("Not found");
        oldComic.setAuthor(comic.getAuthor());
        oldComic.setPages(comic.getPages());
        oldComic.setTitle(comic.getTitle());
        repository.save(oldComic);
        ComicOutput output = ComicOutput.getOutput(oldComic);
        return output;
    }

    @Override
    public ComicOutput delete(Long code) throws Exception {
        final Comic comic = repository.findById(code).orElse(null);
        if(comic == null) throw new Exception("Not found");
        repository.delete(comic);
        ComicOutput output = ComicOutput.getOutput(comic);
        return output;
    }

    @Override
    public List<ComicOutput> getAll() {
        final List<Comic> list = repository.findAll();
        List<ComicOutput> newList = new ArrayList<>();
        for (Comic comic : list) {
            newList.add(ComicOutput.getOutput(comic));
        }
        return newList;
    }

    @Override
    public ComicOutput getBy(Long code) throws Exception {
        final Comic comic = repository.findById(code).orElse(null);
        if(comic == null) throw new Exception("Not found");
        ComicOutput output = ComicOutput.getOutput(comic);
        return output;
    }
}
