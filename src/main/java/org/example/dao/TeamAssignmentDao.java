package org.example.dao;

import org.example.model.Team;
import org.example.model.User;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.List;

/**
 * team_assignment 테이블에 대한 CRUD 작업을 수행하는 DAO 입니다.
 * @implNote JDBC 이용하실 때 예외 처리는 별도로 안 하셔도 됩니다. 서비스 레이어에서 받아서 처리할 계획입니다.
 */
public interface TeamAssignmentDao {

    /**
     * 사용자를 팀에 가입시킵니다.
     * @param userId 사용자 id
     * @param teamId 팀 id
     * @return 가입 성공 여부
     * @throws SQLException
     * @throws SQLTimeoutException
     * @implNote 팀과 사용자가 존재한다고 가정하고 구현 바랍니다.(존재 검증은 외부에서 별도로 진행할 예정)
     */
    boolean assign(long userId, long teamId) throws SQLException, SQLTimeoutException;

    /**
     * 특정 팀에 특정 사용자가 가입되어 있는 지 확인합니다.
     * @param userId 사용자 id
     * @param teamId 팀 id
     * @return 가입 여부
     */
    boolean isTeamHasMember(long userId, long teamId);

    /**
     * 특정 팀에 가입되어 있는 사용자 목록을 조회합니다.
     * @param teamId 팀 id
     * @return 가입된 사용자 목록, 사용자가 없는 경우 비어있습니다.
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    List<User> findUsersByTeamId(long teamId) throws SQLException, SQLTimeoutException;

    /**
     * 특정 사용자가 가입 중인 팀 목록을 조회합니다.
     * @param userId 사용자 id
     * @return 가입한 팀 목록, 가입한 팀이 없는 경우 비어있습니다.
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    List<Team> findTeamsByUserId(long userId) throws SQLException, SQLTimeoutException;

    /**
     * 특정 사용자를 특정 팀에서 탈퇴시킵니다.
     * @param userId 사용자 id
     * @param teamId 팀 id
     * @return 탈퇴 성공 여부
     * @throws SQLException
     * @throws SQLTimeoutException
     */
    boolean resign(long userId, long teamId) throws SQLException, SQLTimeoutException;
}
