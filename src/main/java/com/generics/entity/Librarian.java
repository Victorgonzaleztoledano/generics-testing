package com.generics.entity;

import com.generics.controller.Input.LibrarianInput;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long code;

    private String name;

    private String address;

    public Librarian(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public static Librarian getLibrarian(LibrarianInput employee){
        return new Librarian(employee.getName(), employee.getAddress());
    }
}
