package com.generics.service;

import com.generics.controller.Input.MagazineInput;
import com.generics.controller.Output.MagazineOutput;
import com.generics.entity.Magazine;
import com.generics.repository.MagazineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MagazineServiceImp implements BookService<MagazineOutput, MagazineInput> {

    private final MagazineRepository repository;
    @Override
    public MagazineOutput add(MagazineInput book) {
        Magazine magazine = Magazine.getBook(book);
        repository.save(magazine);
        MagazineOutput output = MagazineOutput.getMagazine(magazine);
        return output;
    }

    @Override
    public MagazineOutput update(Long code, MagazineInput magazine) throws Exception {
        final Magazine oldMagazine = repository.findById(code).orElse(null);
        if(oldMagazine == null) throw new Exception("Not found");
        oldMagazine.setAuthor(magazine.getAuthor());
        oldMagazine.setPages(magazine.getPages());
        oldMagazine.setTitle(magazine.getTitle());
        repository.save(oldMagazine);
        MagazineOutput output = MagazineOutput.getMagazine(oldMagazine);
        return output;
    }

    @Override
    public MagazineOutput delete(Long code) throws Exception {
        final Magazine magazine = repository.findById(code).orElse(null);
        if(magazine == null) throw new Exception("Not found");
        repository.delete(magazine);
        MagazineOutput output = MagazineOutput.getMagazine(magazine);
        return output;
    }

    @Override
    public List<MagazineOutput> getAll() {
        final List<Magazine> list = repository.findAll();
        List<MagazineOutput> newList = new ArrayList<>();
        for (Magazine magazine : list) {
            newList.add(MagazineOutput.getMagazine(magazine));
        }
        return newList;
    }

    @Override
    public MagazineOutput getBy(Long code) throws Exception {
        final Magazine magazine = repository.findById(code).orElse(null);
        if(magazine == null) throw new Exception("Not found");
        MagazineOutput output = MagazineOutput.getMagazine(magazine);
        return output;
    }
}
