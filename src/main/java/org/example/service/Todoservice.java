package main.java.org.example.service;

import main.java.org.example.model.Todo;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Optional;

public interface Todoservice {

    Optional<Todo> create(Todo todo) throws SQLException, SQLTimeoutException;
    Optional<Todo> findById(long id) throws SQLException, SQLTimeoutException;
    boolean update(Todo todo) throws SQLException, SQLTimeoutException;
    boolean delete(long id) throws SQLException, SQLTimeoutException;
}

