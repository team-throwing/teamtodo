package org.example.model;

import java.sql.Timestamp;

public class TodoLogging {
    private long id;
    private Status status;
    private Timestamp timestamp;
    private long todoId;

    public TodoLogging(long id, Status status, Timestamp timestamp, long todoId) {
        this.id = id;
        this.status = status;
        this.timestamp = timestamp;
        this.todoId = todoId;
    }

    public TodoLogging(Status status, Timestamp timestamp, long todoId) {
        this.status = status;
        this.timestamp = timestamp;
        this.todoId = todoId;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public long getTodoId() {
        return todoId;
    }

    @Override
    public String toString() {
        return "TodoLogging{" +
                "id=" + id +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", todoId=" + todoId +
                '}';
    }
}
