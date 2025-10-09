package org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    
    private final DataSource dataSource;

    // DataSource 주입 받아야 함
    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T execute(TransactionCallback<T> service) throws Exception {

        Connection conn = null;
        try {

            // 1. 커넥션 획득 및 등록
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            DataSourceConnectionHolder.set(conn);

            // 2. 비즈니스 로직 실행
            T result = service.transact();

            // 3. 커밋
            conn.commit();

            // 4. Connection 해제 및 DataSourceConnectionHolder 정리
            conn.close();
            DataSourceConnectionHolder.clear();

            // 5. 결과 반환
            return result;

        } catch (Exception causeTransactionFail) {
            // 롤백
            if (conn != null) { // 만약 Connection 을 획득하는 과정에서 발생한 예외가 아니라면,
                try {
                    conn.rollback();
                } catch (SQLException causeRollbackFail) {
                    // To Do: 롤백 실패 로깅 AOP?
                    System.err.println("[트랜잭션 롤백 실패!] 사유: " + causeRollbackFail);
                }
                conn.close();
            }
            DataSourceConnectionHolder.clear();
            throw causeTransactionFail;
        }
    }
}
