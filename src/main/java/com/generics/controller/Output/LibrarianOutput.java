package com.generics.controller.Output;

import com.generics.entity.Librarian;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibrarianOutput {
    private Long code;

    private String name;

    private String address;

    @Override
    public String toString() {
        return "name = " + name;
    }

    public static LibrarianOutput getLibrarian(Librarian librarian){
        return new LibrarianOutput(librarian.getCode(), librarian.getName(), librarian.getAddress());
    }
}
