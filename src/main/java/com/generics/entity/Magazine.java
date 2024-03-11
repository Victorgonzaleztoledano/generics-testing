package com.generics.entity;

import com.generics.controller.Input.MagazineInput;
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
public class Magazine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long code;

    private String title;

    private String author;

    private int pages;

    public Magazine(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public static Magazine getBook(MagazineInput magazine){
        return new Magazine(magazine.getTitle(), magazine.getAuthor(), magazine.getPages());
    }
}
