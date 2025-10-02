package org.example.dao;

import org.example.model.Todo;

public interface TodoRepository {

    // c
    int create(Todo todo);

    // r
    Todo findById(long id);

    // u
    int update(Todo todo);

    // d
    int deleteById(long id);
}
