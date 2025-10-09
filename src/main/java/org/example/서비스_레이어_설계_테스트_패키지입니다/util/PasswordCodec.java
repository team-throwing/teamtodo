package org.example.서비스_레이어_설계_테스트_패키지입니다.util;

public interface PasswordCodec {

    String encrypt(String rawPassword);

    boolean match(String rawPassword, String encryptedPassword);
}
