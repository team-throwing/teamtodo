package org.example.dao;

import org.example.model.Status;
import org.example.model.TodoLogging;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.List;
import java.util.Optional;

/**
 * todo_logging 테이블에 대한 CRUD 작업을 수행하는 DAO 입니다.
 * @implNote JDBC 이용하실 때 예외 처리는 별도로 안 하셔도 됩니다. 서비스 레이어에서 받아서 처리할 계획입니다.
 */
public interface TodoLoggingDao {

    /**
     * 할일 상태 변경 == 새로운 최근 상태 추가(생성) 하는 방식으로 할일 상태를 변경합니다.
     *
     * <br><br><br>
     *
     * <h2>중요!</h2>
     * <ul>
     *     <li>이 메서드는 할일 변경이 가능한지에 대한 비즈니스 규칙을 고려하지 않습니다. 이 규칙은 외부에서 검사되어야 합니다.</li>
     *     <li> ** Todo객체에서 TodoLogging 객체를 가지고 있지 않을 때 생기는 문제점 회고해보면 좋을 듯합니다!</li>
     * </ul>
     * @param todoId 할일 id
     * @param status 변경할 상태
     * @return 저장 성공: 자동 생성 키를 포함하는 새 {@link TodoLogging} 객체를 가지는 Optinoal <br/>
     *          저장 실패: Optional.empty()
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    Optional<TodoLogging> updateStatus(long todoId, Status status) throws SQLException, SQLTimeoutException;

    /**
     * 특정 할일의 가장 최근 상태 조회
     * 반드시 가장 최근 상태가 존재해야 한다는 등의 비즈니스 규칙은 여기서 고려치 않습니다.
     * @param todo
     * @return 가장 최근 상태를 나타내는 TodoLogging 객체를 가지는 Optinoal 또는 Optional.empty()
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    Optional<TodoLogging> findLatestByTodoId(long todo) throws SQLException, SQLTimeoutException;

    /**
     * 특정 할일의 상태 변경 내역 조회
     * @param todo
     * @return 상태 변경 내역, 비어있을 수도 있습니다.
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    List<TodoLogging> findAllByTodoId(long todo) throws SQLException, SQLTimeoutException;
}
