package main.java.org.example.dao;

import main.java.org.example.model.Status;
import main.java.org.example.model.TodoLogging;
import main.java.org.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoLoggingDaoImpl implements TodoLoggingDao {

    @Override
    public Optional<TodoLogging> updateStatus(long todoId, Status status) throws SQLException, SQLTimeoutException {
        String sql = "INSERT INTO todo_logging (todo_id, status, timestamp) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            Timestamp now = new Timestamp(System.currentTimeMillis());

            ps.setLong(1, todoId);
            ps.setString(2, status.name());
            ps.setTimestamp(3, now);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    TodoLogging logging = new TodoLogging(id, status, now, todoId);
                    return Optional.of(logging);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<TodoLogging> findLatestByTodoId(long todoId) throws SQLException, SQLTimeoutException {
        String sql = """
                SELECT id, todo_id, status, timestamp
                FROM todo_logging
                WHERE todo_id = ?
                ORDER BY timestamp DESC
                LIMIT 1""";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, todoId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TodoLogging logging = new TodoLogging(
                            rs.getLong("id"),
                            Status.valueOf(rs.getString("status")),
                            rs.getTimestamp("timestamp"),
                            rs.getLong("todo_id")
                    );
                    return Optional.of(logging);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<TodoLogging> findAllByTodoId(long todoId) throws SQLException, SQLTimeoutException {
        String sql = """
                SELECT id, todo_id, status,timestamp
                FROM todo_logging
                WHERE todo_id = ?
                ORDER BY timestamp ASC
                """;

        List<TodoLogging> result = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, todoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TodoLogging logging = new TodoLogging(
                            rs.getLong("id"),
                            Status.valueOf(rs.getString("status")),
                            rs.getTimestamp("timestamp"),
                            rs.getLong("todo_id")
                    );
                    result.add(logging);
                }
            }
        }
        return result;
    }
}
