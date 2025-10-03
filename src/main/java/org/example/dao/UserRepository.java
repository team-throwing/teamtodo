package org.example.dao;

import org.example.model.User;

public interface UserRepository {

    // c
    int create(User user);

    // r
    User findById(long id);

    // u
    int update(User user);

    // d
    int deleteById(long id);
}
