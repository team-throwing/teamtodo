package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private long id;
    private String name;
    private String goal;

    private final List<User> users = new ArrayList<>();

    public Team(long id, String name, String goal) {
        this.id = id;
        this.name = name;
        this.goal = goal;
    }

    public Team(String name, String goal) {
        this.name = name;
        this.goal = goal;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGoal() {
        return goal;
    }
}
