package com.generics.controller.Output;

import com.generics.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookOutput {

    private Long code;

    private String title;

    private String author;

    private int pages;
    public static BookOutput getBook(Book book){
        return new BookOutput(book.getCode(), book.getTitle(), book.getAuthor(), book.getPages());
    }

    @Override
    public String toString() {
        return "title = " + title +", author = " + author;
    }
}
