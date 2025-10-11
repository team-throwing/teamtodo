package main.java.org.example.dao;

import main.java.org.example.model.Team;

import java.sql.*;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao{
    private Connection conn;
    public TeamDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Team> create(Team team) throws SQLException, SQLTimeoutException {
        String sql = "insert into teams (name) values (?)";

        try(Connection conn = DButil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, team.getName());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Team> findById(long id) throws SQLException, SQLTimeoutException {


        if (id < 0){
            throw new IllegalArgumentException("유효하지 않은 팀 ID 입니다");
        }

        String sql = "select * from teams where id = ?";

        try(Connection conn = DButil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {

                pstmt.setLong(1, id);

                try(ResultSet rs = pstmt.executeQuery()){
                    if(rs.next()) {
                        Team team = new Team(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("description")
                        );
                        return Optional.of(team);
                    }else {
                        return Optional.empty();
        }
                }
            }
        }


    @Override
    public boolean update(Team team) throws SQLException, SQLTimeoutException {
        if (team == null || team.getId() < 0) {
            throw new IllegalArgumentException("수정할 팀 ID가 없습니다");
        }
            String sql = "update teams set name = ?, description = ? where id = ?";

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, team.getName());
                pstmt.setString(2, team.getGoal());
                pstmt.setLong(3, team.getId());

                        int rows = pstmt.executeUpdate();

                        return rows > 0;
                    }
                    }
    @Override
    public boolean deleteById(long id) throws SQLException, SQLTimeoutException {
        if(id <= 0){
            throw new IllegalArgumentException("유효하지 않는 ID 입니다");
                 }
        String sql = "delete from teams where id = ?";
            try(Connection conn = DButil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                     pstmt.setLong(1, id);
                     int rows = pstmt.executeUpdate();
                     return rows > 0; // 삭제된게 있으면 true
       }
       }
    }