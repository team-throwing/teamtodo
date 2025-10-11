package org.example.서비스_레이어_설계_테스트_패키지입니다.dto;

public class UserAssignmentForm {
    private String email;
    private String encryptedPassword;
    private String nickname;

    public UserAssignmentForm(String email, String encryptedPassword, String nickname) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
