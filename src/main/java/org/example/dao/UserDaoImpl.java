package org.example.dao;

import org.example.model.User;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    /**
     * DB에 User 저장
     * @param user User
     * @return 저장 성공: 자동 생성 키를 포함하는 새 User 객체를 가지는 Optional 객체<br/>
     *          저장 실패: Optional.empty()
     * @throws SQLException
     * @throws SQLTimeoutException
     * @implNote <br>
     * <ul>
     *     <li>파라마터로 전달받은 user의 id 필드는 사용하지 마세요.</li>
     *     <li>executeUpdate 의 반환 값이 0 인 경우 저장이 되지 않은 것이므로 Optional.empty() 을 반환하시면 됩니다.</li>
     * </ul>
     */


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
                    user.setId(newId);
                    return Optional.of(user);
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
        User user = new User();
        user.id(rs.getLong("id"));
        user.name(rs.getString("username"));
        user.email(rs.getString("email"));
        user.encryptedPassword(rs.getString("password"));
        return user;
    }
}
