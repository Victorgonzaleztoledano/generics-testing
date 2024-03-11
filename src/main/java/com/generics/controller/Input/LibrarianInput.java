package com.generics.controller.Input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibrarianInput {
    private Long code;

    private String name;

    private String address;

    @Override
    public String toString() {
        return "name = " + name;
    }
}