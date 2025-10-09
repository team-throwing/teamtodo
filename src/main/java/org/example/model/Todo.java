package org.example.model;

import java.time.LocalDateTime;

public class Todo {

    private long id;
    private String title;
    // 시간대 고려 x
    private LocalDateTime due;
    private String description;
    private long team_id;

    public Todo(long id, String title, LocalDateTime due, String description, long team_id) {
        this.id = id;
        this.title = title;
        this.due = due;
        this.description = description;
        this.team_id = team_id;
    }

    public Todo(String title, LocalDateTime due, String description, long team_id) {
        this.title = title;
        this.due = due;
        this.description = description;
        this.team_id = team_id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDue() {
        return due;
    }

    public String getDescription() {
        return description;
    }

    public long getTeam_id() {
        return team_id;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", due=" + due +
                ", description='" + description + '\'' +
                ", team_id=" + team_id +
                '}';
    }
}
