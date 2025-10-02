package org.example.dao;

import org.example.model.Team;
import org.example.model.User;

public interface TeamAssignment {

    // c
    int create(TeamAssignment teamAssignment);

    // r
    TeamAssignment findById(long id);

    // u
    int update(TeamAssignment teamAssignment);

    // d
    int deleteById(long id);

    // 팀원?
    boolean isMember(User user);
}
