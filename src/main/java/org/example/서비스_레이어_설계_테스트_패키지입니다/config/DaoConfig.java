package org.example.서비스_레이어_설계_테스트_패키지입니다.config;

import org.example.dao.*;
import org.example.서비스_레이어_설계_테스트_패키지입니다.service.TestUserService;
import org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction.TransactionManager;
import org.example.서비스_레이어_설계_테스트_패키지입니다.util.BcryptPasswordCodec;
import org.example.서비스_레이어_설계_테스트_패키지입니다.util.PasswordCodec;

public class DaoConfig {

    private static UserDao userDao;


    public static UserDao userDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }

        return userDao;
    }
}
