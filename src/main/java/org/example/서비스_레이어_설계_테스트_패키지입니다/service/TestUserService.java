package org.example.서비스_레이어_설계_테스트_패키지입니다.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.example.서비스_레이어_설계_테스트_패키지입니다.dto.UserAssignmentForm;
import org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction.TransactionManager;
import org.example.서비스_레이어_설계_테스트_패키지입니다.util.PasswordCodec;

import java.util.Optional;

/**
 * 서비스 레이어에서는 비즈니스 로직에만 집중하고
 * Connection 객체를 통한 트랜잭션 관리는 TransactionManager 에 위임한다.
 */
public class TestUserService {

    private final UserDao userDao;
    private final TransactionManager transactionManager;
    private final PasswordCodec passwordCodec;

    // DI
    public TestUserService(UserDao userDao, TransactionManager transactionManager, PasswordCodec passwordCodec) {
        this.userDao = userDao;
        this.transactionManager = transactionManager;
        this.passwordCodec = passwordCodec;
    }

    public User assignUser(UserAssignmentForm form) {
        try {

            return transactionManager.execute(() -> {

                String encryptedPassword = passwordCodec.encrypt(form.getRawPassword());
                User newUser = new User(form.getEmail(), encryptedPassword, form.getNickname());

                Optional<User> optionalUser = userDao.create(newUser);
                if (optionalUser.isEmpty()) {
                    throw new RuntimeException("사용자가 생성되지 않았습니다.");
                }
                newUser = optionalUser.get();

                return newUser;

            });

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        return null;
    }
}
