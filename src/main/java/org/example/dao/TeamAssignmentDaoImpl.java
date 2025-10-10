package org.example.dao;

import org.example.QueryUtil;
import org.example.model.Team;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamAssignmentDaoImpl implements TeamAssignmentDao {
    private final Connection connection;

    public TeamAssignmentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean assign(long userId, long teamId) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("teamAssignment.assign");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, userId);
            pst.setLong(2, teamId);
            int result = pst.executeUpdate();
            return result > 0;
        }
    }

    @Override
    public boolean isTeamHasMember(long userId, long teamId) {
        String sql = QueryUtil.getQuery("teamAssignment.isTeamHasMember");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, userId);
            pst.setLong(2, teamId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();  // 결과가 있으면 true, 없으면 false 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> findUsersByTeamId(long teamId) throws SQLException, SQLTimeoutException {
        List<User> users = new ArrayList<>();
        String sql = QueryUtil.getQuery("teamAssignment.findUsersByTeamId");

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, teamId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String email = rs.getString("email");
                    String encryptedPassword = rs.getString("encrypted_password");
                    String nickname = rs.getString("nickname");

                    User user = new User(id, email, encryptedPassword, nickname);
                    users.add(user);  // 사용자 목록에 추가
                }
            }
        }
        return users;  // 사용자 목록 반환 // 사용자 목록 반환
    }

    @Override
    public List<Team> findTeamsByUserId(long userId) throws SQLException, SQLTimeoutException {
        List<Team> teams = new ArrayList<>();
        String sql = QueryUtil.getQuery("teamAssignment.findTeamsByUserId");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Team team = new Team(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("goal")
                    );
                    teams.add(team);  // 팀 목록에 추가
                }
            }
        }
        return teams;
    }

    @Override
    public boolean resign(long userId, long teamId) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("teamAssignment.resign");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, userId);
            pst.setLong(2, teamId);
            int result = pst.executeUpdate();
            return result > 0;  // 1행 이상 삭제되면 true 반환
        }
    }
}
