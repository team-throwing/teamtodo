package org.example.model;

public class User {


    private long id;
    private String email;
    private String encryptedPassword;
    private String nickname;

    public User(long id, String email, String encryptedPassword, String nickname) {
        this.id = id;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.nickname = nickname;
    }

    public User(String email, String encryptedPassword, String nickname) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.nickname = nickname;
    }
    public void setId(long id) {      //
        this.id = id;}               // 지민수가 추가함

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
        return nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
