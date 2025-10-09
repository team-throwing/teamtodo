package org.example.서비스_레이어_설계_테스트_패키지입니다.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BcryptPasswordCodec implements PasswordCodec {

    @Override
    public String encrypt(String rawPassword) {
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }

    @Override
    public boolean match(String rawPassword, String encryptedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encryptedPassword);
        return result.verified;
    }
}
