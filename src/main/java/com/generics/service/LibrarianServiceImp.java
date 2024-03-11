package com.generics.service;

import com.generics.controller.Input.LibrarianInput;
import com.generics.controller.Output.LibrarianOutput;
import com.generics.entity.Librarian;
import com.generics.repository.LibrarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibrarianServiceImp implements EmployeeService<LibrarianOutput, LibrarianInput> {

    private final LibrarianRepository repository;
    @Override
    public LibrarianOutput add(LibrarianInput employee) {
        Librarian librarian = Librarian.getLibrarian(employee);
        repository.save(librarian);
        LibrarianOutput output = LibrarianOutput.getLibrarian(librarian);
        return output;
    }

    @Override
    public LibrarianOutput update(Long code, LibrarianInput employee) throws Exception {
        final Librarian librarian = repository.findById(code).orElse(null);
        if(librarian == null) throw new Exception("Not found");
        librarian.setName(employee.getName());
        librarian.setAddress(employee.getAddress());
        repository.save(librarian);
        LibrarianOutput output = LibrarianOutput.getLibrarian(librarian);
        return output;
    }

    @Override
    public LibrarianOutput delete(Long code) throws Exception {
        final Librarian librarian = repository.findById(code).orElse(null);
        if(librarian == null) throw new Exception("Not found");
        repository.delete(librarian);
        LibrarianOutput output = LibrarianOutput.getLibrarian(librarian);
        return output;
    }

    @Override
    public List<LibrarianOutput> getAll() {
        final List<Librarian> list = repository.findAll();
        List<LibrarianOutput> newList = new ArrayList<>();
        for (Librarian librarian : list) {
            newList.add(LibrarianOutput.getLibrarian(librarian));
        }
        return newList;
    }

    @Override
    public LibrarianOutput getBy(Long code) throws Exception {
        final Librarian librarian = repository.findById(code).orElse(null);
        if(librarian == null) throw new Exception("Not found");
        LibrarianOutput output = LibrarianOutput.getLibrarian(librarian);
        return output;
    }
}
