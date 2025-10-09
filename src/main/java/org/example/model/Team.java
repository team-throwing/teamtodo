package main.java.org.example.model;

public class Team {

    private long id;
    private String name;
    private String goal;

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

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", goal='" + goal + '\'' +
                '}';
    }
}
