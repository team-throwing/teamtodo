package org.example.서비스_레이어_설계_테스트_패키지입니다.config;

import org.example.서비스_레이어_설계_테스트_패키지입니다.service.TestUserService;
import org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction.TransactionManager;

public class ServiceConfig {

    private static TransactionManager transactionManager;
    private static TestUserService testUserService;

    public static TransactionManager transactionManager() {
        if (transactionManager == null) {
            transactionManager = new TransactionManager(DataSourceConfig.get());
        }
        return transactionManager;
    }



    public static TestUserService testUserService() {
        if (testUserService == null) {
            testUserService = new TestUserService(DaoConfig.userDao(), transactionManager(), UtilConfig.passwordCodec());
        }
        return testUserService;
    }
}
