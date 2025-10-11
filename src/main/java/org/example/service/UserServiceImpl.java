package main.java.org.example.service;

import main.java.org.example.dao.UserDao;
import main.java.org.example.dao.UserDaoImpl;
import main.java.org.example.model.User;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override // 생성 규칙?
    public Optional<User> create(User user) throws SQLException, SQLTimeoutException {
        // 유효성 검사
        if(user == null){
            throw new IllegalArgumentException("유저 정보가 없습니다.");
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()){
            throw new IllegalArgumentException("이메일은 필수 입력 항목입니다.");
        }
        if(user.getEncryptedPassword() == null || user.getEncryptedPassword().isEmpty()){
            throw new IllegalArgumentException("비밀번호는 필수 입력 항목입니다.");
        }
        // 중복 검사
        if(userDao.findById(user.getId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 ID입니다");
        }
        if(userDao.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
            return userDao.create(user);
    }


    @Override
    public Optional<User> findById(long id) throws SQLException, SQLTimeoutException {
        if(id <= 0){
            throw new IllegalArgumentException("잘못된 ID입니다.");
        }
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException, SQLTimeoutException {
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }
        return userDao.findByEmail(email);
    }

    @Override
    public boolean update(User user) throws SQLException, SQLTimeoutException {
        if(user == null||user.getId() <= 0){
            throw new IllegalArgumentException("수정할 유저 정보가 올바르지 않습니다.");
        }
        // 이메일 중복 검사를 넣어야하는데 모르겠음

        return userDao.update(user);
    }

    @Override
    public boolean deleteById(long id) throws SQLException, SQLTimeoutException {
        if(id <= 0){
            throw new IllegalArgumentException("삭제할 ID가 올바르지 않습니다.");
        }

        Optional<User> existing = userDao.findById(id);
        if(existing.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        return userDao.deleteById(id);
    }
}
