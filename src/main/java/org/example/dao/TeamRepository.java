package org.example.dao;

import org.example.model.Team;
import org.example.model.User;

public interface TeamRepository {

    // c
    int create(Team team);

    // r
    Team findById(long id);

    // u
    int update(Team team);

    // d
    int deleteById(long id);

    // 팀원?
    boolean isMember(User user);
}
