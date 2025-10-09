package org.example.dao;

import org.example.model.User;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Optional;

/**
 * user 테이블에 대한 CRUD 작업을 수행하는 DAO 입니다.
 * @implNote JDBC 이용하실 때 예외 처리는 별도로 안 하셔도 됩니다. 서비스 레이어에서 받아서 처리할 계획입니다.
 */
public interface UserDao {

    /**
     * DB에 User 저장
     * @param user User
     * @return 저장 성공: 자동 생성 키를 포함하는 새 User 객체를 가지는 Optional 객체<br/>
     *          저장 실패: Optional.empty()
     * @throws SQLException
     * @throws SQLTimeoutException
     * @implNote <br>
     * <ul>
     *     <li>파라마터로 전달받은 user의 id 필드는 사용하지 마세요.</li>
     *     <li>executeUpdate 의 반환 값이 0 인 경우 저장이 되지 않은 것이므로 Optional.empty() 을 반환하시면 됩니다.</li>
     * </ul>
     */
    Optional<User> create(User user) throws SQLException, SQLTimeoutException;

    /**
     * id를 기준으로 DB 로부터 User 조회
     * @param id 조회할 사용자 id
     * @return User 객체를 가지는 Optional 또는 Optional.empty()
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    Optional<User> findById(long id) throws SQLException, SQLTimeoutException;

    /**
     * 고유한 email 을 기준으로 DB 로부터 User 조회
     * @param email 사용자 이메일
     * @return User 또는 Optional.empty()
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    Optional<User> findByEmail(String email) throws SQLException, SQLTimeoutException;

    /**
     * User 수정
     * @param user 반드시 id 를 포함하고 있어야 함
     * @return 수정 성공 여부(수정할 사용자를 찾지 못한 경우 false)
     * @throws SQLException
     * @throws SQLTimeoutException
     * @implNote 파라미터로 전달받은 user 의 id 초기화 여부를 반드시 검증해야 합니다.
     */
    boolean update(User user) throws SQLException, SQLTimeoutException;

    /**
     * DB 로부터 id 에 해당하는 User 제거
     * @param id 사용자 id
     * @return 삭제 성공 여부(삭제할 사용자를 찾지 못한 경우 false)
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    boolean deleteById(long id) throws SQLException, SQLTimeoutException;
}
