package org.example.서비스_레이어_설계_테스트_패키지입니다.config;

import org.example.서비스_레이어_설계_테스트_패키지입니다.util.BcryptPasswordCodec;
import org.example.서비스_레이어_설계_테스트_패키지입니다.util.PasswordCodec;

public class UtilConfig {

    private static PasswordCodec passwordCodec;

    public static PasswordCodec passwordCodec() {
        if (passwordCodec == null) {
            passwordCodec = new BcryptPasswordCodec();
        }
        return passwordCodec;
    }
}
