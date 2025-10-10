package main.java.org.example.dao;

import main.java.org.example.model.Team;
import main.java.org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamAssignmentDaoImpl implements TeamAssignmentDao {
    private Connection conn;

    public TeamAssignmentDaoImpl(Connection conn) {
        this.conn = conn;
    }


    @Override // 팀가입
    public boolean assign(long userId, long teamId) throws SQLException, SQLTimeoutException {
        if (userId <= 0 || teamId <= 0) {
            throw new IllegalArgumentException("잘못된 유저 ID입니다.");
        }
        String sql = "INSERT INTO team_assignment(user_id,team_id) VALUES(?,?)";

        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            pstmt.setLong(2, teamId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }


    @Override // 탈퇴
    public boolean resign(long userId, long teamId) throws SQLException, SQLTimeoutException {
        if (userId <= 0 || teamId <= 0) {
            throw new IllegalArgumentException("잘못된 사용자 ID 또는 팀 ID입니다.");
        }

        String sql = "DELETE FROM team_members WHERE user_id = ? AND team_id = ?";

        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, teamId);

            int rows = pstmt.executeUpdate();
            return rows > 0; // 탈퇴 성공 여부 반환
        }
    }


    @Override //유저명으로 팀찾기
    public List<Team> findTeamsByUserId(long userId) throws SQLException, SQLTimeoutException {
        if (userId <= 0) {
            throw new IllegalArgumentException("잘못된 사용자 ID입니다.");
        }

        String sql = "SELECT t.id, t.name, t.description " +
                "FROM teams t " +
                "JOIN team_members tm ON t.id = tm.team_id " +
                "WHERE tm.user_id = ?";

        List<Team> teams = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teams.add(new Team(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    ));
                }
            }
        }

        return teams;
    }

    @Override
    public List<User> findUsersByTeamId(long teamId) throws SQLException, SQLTimeoutException {
        if (teamId <= 0) {
            throw new IllegalArgumentException("잘못된 팀 ID입니다.");
        }

        String sql = "SELECT u.id, u.username, u.email " +
                "FROM users u " +
                "JOIN team_members tm ON u.id = tm.user_id " +
                "WHERE tm.team_id = ?";

        List<User> users = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, teamId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            null // password 안씀
                    ));
                }
            }
        }

        return users;



}

    @Override // 팀 내 회원 존재 유무 판별
    public boolean isTeamHasMember(long userId, long teamId) {
        if (userId <= 0 || teamId <= 0) {
            throw new IllegalArgumentException("잘못된 ID입니다.");
        }
        String sql = "SELECT 1 FROM team_members WHERE user_id = ? AND team_id = ?"; // 존재만 확인

        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, teamId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}

