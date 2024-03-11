package com.generics.service;

import java.util.List;

public interface EmployeeService<T, G> {
    T add(G employee);

    T update(Long code, G employee) throws Exception;
    T delete(Long code) throws Exception;

    List<T> getAll();
    T getBy(Long code) throws Exception;
}
