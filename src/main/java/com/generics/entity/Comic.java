package com.generics.entity;

import com.generics.controller.Input.ComicInput;
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
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long code;

    private String title;

    private String author;

    private int pages;

    public Comic(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public static Comic getComic(ComicInput comic){
        return new Comic(comic.getTitle(), comic.getAuthor(), comic.getPages());
    }
}
