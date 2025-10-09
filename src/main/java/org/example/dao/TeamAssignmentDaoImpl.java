package main.java.org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class TeamAssignmentDaoImpl implements TeamAssignmentDao {
    private Connection conn;
    public TeamAssignmentDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean resign(long userId, long teamId) throws SQLException, SQLTimeoutException {
        return false;
    }
}

