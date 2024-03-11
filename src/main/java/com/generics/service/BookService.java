package com.generics.service;

import java.util.List;

public interface BookService<T, G> {
    T add(G book) throws Exception;

    T update(Long code, G book) throws Exception;
    T delete(Long code) throws Exception;

    List<T> getAll() throws Exception;
    T getBy(Long code) throws Exception;
}