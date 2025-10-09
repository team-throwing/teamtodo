package org.example.서비스_레이어_설계_테스트_패키지입니다.dto;

public class UserAssignmentForm {
    private String email;
    private String rawPassword;
    private String nickname;

    public UserAssignmentForm(String email, String rawPassword, String nickname) {
        this.email = email;
        this.rawPassword = rawPassword;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
