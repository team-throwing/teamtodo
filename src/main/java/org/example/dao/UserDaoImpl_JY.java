package org.example.dao;

import org.example.QueryUtil;
import org.example.model.User;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> create(User user) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("user.create");
        try(PreparedStatement pst = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getEncryptedPassword());
            pst.setString(3, user.getNickname());

            int result = pst.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);  // 자동 생성된 ID 받아오기
                        user.setId(id);           // User 객체에 ID 설정
                    }
                }
                return Optional.of(user);  // 성공적으로 생성된 User 반환
            } else {
                return Optional.empty();   // 실패 시 빈 Optional 반환
            }
        }
    }

    @Override
    public Optional<User> findById(long id) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("user.findById");
        try(PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            } else return Optional.empty();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("user.findByEmail");
        try(PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            } else return Optional.empty();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User user) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("user.update");
        try (PreparedStatement pst = connection.prepareStatement(sql)){
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getEncryptedPassword());
            pst.setString(3, user.getNickname());
            pst.setLong(4, user.getId());

            int rowsAffected = pst.executeUpdate();

            return rowsAffected > 0;
        }
    }

    @Override
    public boolean deleteById(long id) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("user.delete");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);

            int rowsAffected = pst.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
