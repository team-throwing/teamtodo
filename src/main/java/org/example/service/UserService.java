package main.java.org.example.service;

import main.java.org.example.model.User;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> create(User user) throws SQLException, SQLTimeoutException;
    Optional<User> findById(long id) throws SQLException, SQLTimeoutException;
    Optional<User> findByEmail(String email) throws SQLException, SQLTimeoutException;
    boolean update(User user) throws SQLException, SQLTimeoutException;
    boolean deleteById(long id) throws SQLException, SQLTimeoutException;

}
