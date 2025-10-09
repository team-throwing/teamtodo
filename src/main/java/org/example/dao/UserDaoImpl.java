package org.example.dao;

import org.example.model.User;
import org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction.DataSourceConnectionHolder;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {


    @Override // 유저 아이디 생성
    public Optional<User> create(User user) throws SQLException, SQLTimeoutException {

        String sql = """
                INSERT INTO user (email, encrypted_password, nickname)
                VALUES (?, ?, ?)
                """;

        Connection conn = DataSourceConnectionHolder.get();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, user.getEmail());
        pstmt.setString(2, user.getEncryptedPassword());
        pstmt.setString(3, user.getNickname());

        int rows = pstmt.executeUpdate();
        if (rows == 0) return Optional.empty();

        long generatedId = 0;
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            generatedId = rs.getLong(1);
        }

        User ret = new User(
                generatedId,
                user.getEmail(),
                user.getEncryptedPassword(),
                user.getNickname()
        );

        // conn 은 닫을 필요 없음
        pstmt.close();
        rs.close();

        return Optional.of(ret);
    }

    @Override // 아이디 찾기
    public Optional<User> findById(long id) throws SQLException, SQLTimeoutException {
        String sql = "SELECT * FROM user WHERE id = ?";

        Connection conn = DataSourceConnectionHolder.get();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, id);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return Optional.of(
                    new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("encrypted_password"),
                            rs.getString("nickname")
                    )
            );
        }
        return Optional.empty();
    }

    @Override // 이메일 찾기
    public Optional<User> findByEmail(String email) throws SQLException, SQLTimeoutException {
        String sql = "SELECT * FROM user WHERE email = ?";

        Connection conn = DataSourceConnectionHolder.get();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return Optional.of(
                    new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("encrypted_password"),
                            rs.getString("nickname")
                    )
            );
        }
        return Optional.empty();
    }

    @Override //ID 정보 수정
    public boolean update(User user) throws SQLException, SQLTimeoutException {

        if (user.getId() == 0) {
            throw new IllegalArgumentException("수정할 유저 ID를 입력해 주세요.");
        }

        String sql = """
                UPDATE user
                SET email=?, encrypted_password=?, nickname=?
                WHERE id=?
                """;

        Connection conn = DataSourceConnectionHolder.get();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, user.getEmail());
        pstmt.setString(2, user.getEncryptedPassword());
        pstmt.setString(3, user.getNickname());
        pstmt.setLong(4, user.getId());

        return pstmt.executeUpdate() > 0;
    }

    @Override //계정 지우기
    public boolean deleteById(long id) throws SQLException, SQLTimeoutException {
        String sql = "DELETE FROM user WHERE id=?";

        Connection conn = DataSourceConnectionHolder.get();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, id);
        return pstmt.executeUpdate() > 0;
    }

    /**
     * result 셋 형식은 select 쿼리에서 어떤 컬럼을 선택하냐에 따라 달라지므로
     * 이런 식으로 별도 함수로 추출하는 것은 별로 좋지 않은 것 같습니다
     */
//    // ✅ 공통 ResultSet → User 변환
//    private User mapResultSetToUser(ResultSet rs) throws SQLException {
//        return new User(
//                rs.
//        );
//        user.id(rs.getLong("id"));
//        user.name(rs.getString("username"));
//        user.email(rs.getString("email"));
//        user.encryptedPassword(rs.getString("password"));
//        return user;
//    }
}
