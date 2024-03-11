package com.generics.controller.Output;

import com.generics.entity.Book;
import com.generics.entity.Comic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComicOutput {
    private Long code;
    private String title;

    private String author;

    private int pages;

    public static ComicOutput getOutput(Comic comic){
        return new ComicOutput(comic.getCode(), comic.getTitle(), comic.getAuthor(), comic.getPages());
    }

    @Override
    public String toString() {
        return "title = " + title + '\'' +
                ", author = " + author;
    }
}
