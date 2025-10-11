package org.example.서비스_레이어_설계_테스트_패키지입니다.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.example.서비스_레이어_설계_테스트_패키지입니다.dto.UserAssignmentForm;
import org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction.TransactionManager;
import org.example.서비스_레이어_설계_테스트_패키지입니다.util.PasswordCodec;

import java.sql.SQLException;
import java.util.NoSuchElementException;
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

    public User assignUser(UserAssignmentForm form) throws Exception {

        return transactionManager.execute(() -> {

            User newUser;
            try {
                newUser = new User(form.getEmail(), form.getEncryptedPassword(), form.getNickname());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("사용자 생성 실패");
            }

            Optional<User> optionalUser;
            try {
                optionalUser = userDao.create(newUser);
                return optionalUser.get();
            } catch (SQLException | NoSuchElementException e) {
                throw new RuntimeException("사용자 영속화 실패");
            }
        });
    }
}
