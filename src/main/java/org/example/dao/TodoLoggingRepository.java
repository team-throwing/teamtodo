package org.example.dao;

import org.example.model.TodoLogging;

public interface TodoLoggingRepository {

    // c
    int create(TodoLogging todoLogging);

    // r
    TodoLogging findById(long id);

    // u
    int update(TodoLogging todoLogging);

    // d
    int deleteById(long id);
}
