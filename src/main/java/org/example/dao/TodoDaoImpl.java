package main.java.org.example.dao;

import main.java.org.example.model.Todo;

import java.sql.*;
import java.util.Optional;

public class TodoDaoImpl implements main.java.org.example.dao.TodoDao {


    private final Connection conn;

    public TodoDaoImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Optional<Todo> create(Todo todo) throws SQLException, SQLTimeoutException {
        String sql = "INSERT INTO Todo (title, description, due, team_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            pstmt.setString(1, todo.getTitle());
            pstmt.setString(2, todo.getDescription());
            // due 처리

            if (todo.getDue() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(todo.getDue()));
            } else {
                pstmt.setNull(3, Types.TIMESTAMP);
            }
            pstmt.setLong(4, todo.getTeam_id());

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                return Optional.empty();
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    // 생성자 순서에 맞게
                    Todo newTodo = new Todo(
                            generatedId,   //todo id 객체생성
                            todo.getTitle(),
                            todo.getDue(),
                            todo.getDescription(),
                            todo.getTeam_id()
                    );
                    return Optional.of(newTodo);
                }
            }
        }

        return Optional.empty();
    }


    @Override
    public Optional<Todo> findById(long id) throws SQLException, SQLTimeoutException {
        String sql = """
                SELECT *
                FROM Todo
                where id = ?
                """;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // DB의 한 행 → Todo 객체로 변환
                    Todo todo = new Todo(
                            rs.getString("title"),
                            rs.getTimestamp("due") != null
                                    ? rs.getTimestamp("due").toLocalDateTime()
                                    : null,
                            rs.getString("description"),
                            rs.getLong("team_id"));

                    return Optional.of(todo);
                }
            }
        }

        // 조회 실패 시 Optional.empty() 반환
        return Optional.empty();
        }


    @Override
    public boolean update(Todo todo) throws SQLException, SQLTimeoutException {
        if(todo == null || todo.getId() <= 0 ) {
            throw new IllegalArgumentException("Todo id가 유효하지 않음");
        }// id있는지 없는지부터 (boolean)


        String spl = """
                UPDATE todo SET title = ?, description = ?, team_id = ?
                where id = ?
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(spl)) {
            pstmt.setString(1,todo.getTitle());     // 수정할 제목 컬럼
        if(todo.getDue() != null) {
            pstmt.setTimestamp(2,Timestamp.valueOf(todo.getDue())); // 자동으로 등록된 마감일 수정
        } else {
            pstmt.setTimestamp(2, null);
        }
        pstmt.setString(3,todo.getDescription());
        pstmt.setLong(4,todo.getTeam_id());
        pstmt.setLong(5,todo.getId());

        int rows = pstmt.executeUpdate();
        return rows > 0;

        }
    }

    @Override
    public boolean deleteById(long id) throws SQLException, SQLTimeoutException {

        if(id <= 0 ) {
            throw new IllegalArgumentException("유효하지 않는 ID입니다."); // id있는지 없는지부터 (boolean)
        }

        String sql = """
                    DELETE
                    FROM todo
                    WHERE id = ?
                  
                    """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0; //삭제 성공 여부 반환
        }



    }
}
