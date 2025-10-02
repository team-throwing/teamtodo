package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long id;
    private String email;
    private String encryptedPassword;
    private String name;

    // 가입한 팀 목록이 필요할 때만 소속 Team 객체가 채워짐
    private final List<Team> teams = new ArrayList<>();

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
}
