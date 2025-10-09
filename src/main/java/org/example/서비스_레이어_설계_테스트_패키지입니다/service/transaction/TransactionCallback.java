package org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction;

@FunctionalInterface
public interface TransactionCallback<T> {

    /**
     * 트랜잭션을 실행합니다.
     * @return T result
     * @throws Exception 트랜잭션 실행 중 발생한 예외는 그대로 전파
     */
    T transact() throws Exception;
}
