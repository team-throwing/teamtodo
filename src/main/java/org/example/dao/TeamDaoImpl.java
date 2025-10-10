package org.example.dao;

import org.example.QueryUtil;
import org.example.model.Team;

import java.sql.*;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao {
    private final Connection connection;

    public TeamDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Team> create(Team team) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("team.create");
        try(PreparedStatement pst = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, team.getName());
            pst.setString(2, team.getGoal());

            int result = pst.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        team.setId(id);
                    }
                }
                return Optional.of(team);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<Team> findById(long id) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("team.findById");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Team team = new Team(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3)
                    );
                    return Optional.of(team);
                }
            }
        } catch (SQLTimeoutException e) {
            throw e;
        }
        return Optional.empty();    }

    @Override
    public boolean update(Team team) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("team.update");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, team.getName());
            pst.setString(2, team.getGoal());
            pst.setLong(3, team.getId());

            int result = pst.executeUpdate();
            return result > 0;
        }
    }

    @Override
    public boolean deleteById(long id) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("team.deleteById");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            int result = pst.executeUpdate();
            return result > 0;  // 삭제된 행이 있다면 true 반환, 없다면 false 반환
        }    }
}
