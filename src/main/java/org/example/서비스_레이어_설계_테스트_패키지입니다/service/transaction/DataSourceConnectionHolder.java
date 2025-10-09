package org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction;

import java.sql.Connection;

/**
 * <h2>DataSourceConnectionHolder</h2>
 * 스레드 로컬한 방식으로 DataSource 의 Connection 을 저장 및 제공합니다.
 */
public class DataSourceConnectionHolder {

    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public static void set(Connection conn) {
        connectionThreadLocal.set(conn);
    }

    public static Connection get() {
        return connectionThreadLocal.get();
    }

    public static void clear() {
        connectionThreadLocal.remove();
    }
}
