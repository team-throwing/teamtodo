package org.example.model;

public class User {

    private long id;
    private String email;
    private String encryptedPassword;
    private String name;

    public User(long id, String email, String encryptedPassword, String name) {
        this.id = id;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.name = name;
    }

    public User(String email, String encryptedPassword, String nickname) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getNickname() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
