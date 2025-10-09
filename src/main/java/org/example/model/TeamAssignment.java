package main.java.org.example.model;

public class TeamAssignment {

    private long id;
    private long userId;
    private long TeamId;

    public TeamAssignment(long id, long userId, long teamId) {
        this.id = id;
        this.userId = userId;
        TeamId = teamId;
    }

    public TeamAssignment(long userId, long teamId) {
        this.userId = userId;
        TeamId = teamId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getTeamId() {
        return TeamId;
    }

    @Override
    public String toString() {
        return "TeamAssignment{" +
                "id=" + id +
                ", userId=" + userId +
                ", TeamId=" + TeamId +
                '}';
    }
}
