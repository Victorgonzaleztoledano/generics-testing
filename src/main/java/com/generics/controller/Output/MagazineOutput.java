package com.generics.controller.Output;

import com.generics.entity.Book;
import com.generics.entity.Magazine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MagazineOutput {
    private Long code;
    private String title;

    private String author;

    private int pages;

    public static MagazineOutput getMagazine(Magazine magazine){
        return new MagazineOutput(magazine.getCode(), magazine.getTitle(), magazine.getAuthor(), magazine.getPages());
    }

    @Override
    public String toString() {
        return "title = " + title + '\'' +
                ", author = " + author;
    }
}
