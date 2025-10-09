package main.java.org.example.dao;

import main.java.org.example.model.User;
import main.java.org.example.util.DBUtil;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final Connection conn;  // ✅ 실제 DB 연결

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override // 유저 아이디 생성
    public Optional<User> create(User user) throws SQLException, SQLTimeoutException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)"; // 아이디 이메일 비밀번호 입력

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getNickname());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getEncryptedPassword());

            int rows = pstmt.executeUpdate();
            if (rows == 0) return Optional.empty();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long newId = rs.getLong(1);
                    User newUser = new User(
                            newId,
                            user.getNickname(),
                            user.getEncryptedPassword(),   /* setid 빼고 새로운 user 만드는거로 수정*/
                            user.getEmail()
                    );
                    return Optional.of(newUser);
                }
            }

        }
        return Optional.empty();
    }

    @Override // 아이디 찾기
    public Optional<User> findById(long id) throws SQLException, SQLTimeoutException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override // 이메일 찾기
    public Optional<User> findByEmail(String email) throws SQLException, SQLTimeoutException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override //ID 정보 수정
    public boolean update(User user) throws SQLException, SQLTimeoutException {
        if (user.getId() == 0) {
            throw new IllegalArgumentException("수정할 유저 ID를 입력해 주세요.");
        }

        String sql = "UPDATE users SET username=?, email=?, password=? WHERE id=?"; // id위치의 개인정보들을 넣은 값으로 업데이트
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getNickname());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getEncryptedPassword());
            pstmt.setLong(4, user.getId());

            return pstmt.executeUpdate() > 0;
        }
    }

    @Override //계정 지우기
    public boolean deleteById(long id) throws SQLException, SQLTimeoutException {
        String sql = "DELETE FROM users WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    // ✅ 공통 ResultSet → User 변환
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password")
        );
    }

}
