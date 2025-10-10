package org.example.dao;

import org.example.QueryUtil;
import org.example.model.Status;
import org.example.model.TodoLogging;

import java.sql.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoLoggingDaoImpl implements TodoLoggingDao {
    private final Connection connection;

    public TodoLoggingDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<TodoLogging> updateStatus(long todoId, Status status) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("todoLogging.updateStatus");

        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, status.name());  // 상태를 String으로 저장
            pst.setLong(2, todoId);           // 해당 Todo의 ID

            int result = pst.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        Timestamp timestamp = rs.getTimestamp(2);  // Timestamp 가져오기
                        TodoLogging todoLogging = new TodoLogging(id, status, timestamp, todoId);
                        return Optional.of(todoLogging);  // 상태 변경이 성공하면, 새 TodoLogging 객체 반환
                    }
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<TodoLogging> findLatestByTodoId(long todo) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("todoLogging.findLatestByTodoId");

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            long todoId = 0;    // 수정 필
            pst.setLong(1, todoId);  // Todo ID로 가장 최근 상태 조회
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong(1);  // 첫 번째 컬럼: id
                    long todoLoggingTodoId = rs.getLong(2);  // 두 번째 컬럼: todo_id
                    Status status = Status.valueOf(rs.getString(3));  // 세 번째 컬럼: status (String -> Status enum)
                    Timestamp timestamp = rs.getTimestamp(4);  // 네 번째 컬럼: timestamp

                    TodoLogging todoLogging = new TodoLogging(id, status, timestamp, todoLoggingTodoId);
                    return Optional.of(todoLogging);  // 가장 최근 상태 반환
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<TodoLogging> findAllByTodoId(long todo) throws SQLException, SQLTimeoutException {
        String sql = QueryUtil.getQuery("todoLogging.findAllByTodoId");
        List<TodoLogging> todoLoggingList = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            long todoId = 0;    // 수정 필
            pst.setLong(1, todoId);  // Todo ID로 상태 변경 내역 조회
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong(1);  // 첫 번째 컬럼: id
                    long todoLoggingTodoId = rs.getLong(2);  // 두 번째 컬럼: todo_id
                    Status status = Status.valueOf(rs.getString(3));  // 세 번째 컬럼: status (String -> Status enum)
                    Timestamp timestamp = rs.getTimestamp(4);  // 네 번째 컬럼: timestamp

                    TodoLogging todoLogging = new TodoLogging(id, status, timestamp, todoLoggingTodoId);
                    todoLoggingList.add(todoLogging);  // 변경 내역을 리스트에 추가
                }
            }
        }
        return todoLoggingList;
    }
}