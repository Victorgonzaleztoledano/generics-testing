package com.generics.service;

import com.generics.controller.Input.BookInput;
import com.generics.controller.Output.BookOutput;
import com.generics.entity.Book;
import com.generics.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService<BookOutput, BookInput> {

    private final BookRepository repository;

    @Override
    public BookOutput add(BookInput book) throws Exception {
        if(book == null) throw new Exception("Book can not be null");
        Book newBook = Book.getBook(book);
        repository.save(newBook);
        BookOutput output = BookOutput.getBook(newBook);
        return output;
    }

    @Override
    public BookOutput update(Long code, BookInput book) throws Exception {
        final Book oldBook = repository.findById(code).orElse(null);
        if(oldBook == null) throw new Exception("Not found");
        oldBook.setAuthor(book.getAuthor());
        oldBook.setPages(book.getPages());
        oldBook.setTitle(book.getTitle());
        repository.save(oldBook);
        BookOutput output = BookOutput.getBook(oldBook);
        return output;
    }

    @Override
    public BookOutput delete(Long code) throws Exception {
        final Book newBook = repository.findById(code).orElse(null);
        if(newBook == null) throw new Exception("Not found");
        repository.delete(newBook);
        BookOutput output = BookOutput.getBook(newBook);
        return output;
    }

    @Override
    public List<BookOutput> getAll() throws Exception {
        final List<Book> list = repository.findAll();
        if(list.isEmpty()) throw new Exception("List is null");
        List<BookOutput> newList = new ArrayList<>();
        for (Book book : list) {
            newList.add(BookOutput.getBook(book));
        }
        return newList;
    }

    @Override
    public BookOutput getBy(Long code) throws Exception {
        final Book newBook = repository.findById(code).orElse(null);
        if(newBook == null) throw new Exception("Not found");
        BookOutput output = BookOutput.getBook(newBook);
        return output;
    }
}
