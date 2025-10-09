package org.example.서비스_레이어_설계_테스트_패키지입니다.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class DataSourceConfig {

    private static DataSource dataSource;

    static {
        initHikari();
    }

    private static void initHikari() {
        try {
            Properties props = new Properties();
            props.load(DataSourceConfig.class.getClassLoader().getResourceAsStream("application.properties"));

            HikariConfig config = new HikariConfig();

            // DB 연결정보 명시
            // Essentials(https://github.com/brettwooldridge/HikariCP?tab=readme-ov-file#gear-configuration-knobs-baby)
            config.setDriverClassName(props.getProperty("db.driver-class-name"));
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            // hikari 커넥션 풀 설정
            config.setMaximumPoolSize(
                    Integer.parseInt(props.getProperty("hikari.maximumPoolSize"))
            );
            config.setMinimumIdle(
                    Integer.parseInt(props.getProperty("hikari.minimumIdle"))
            );
            config.setIdleTimeout(
                    Integer.parseInt(props.getProperty("hikari.connectionTimeout"))
            );
            config.setIdleTimeout(
                    Integer.parseInt(props.getProperty("hikari.idleTimeout"))
            );
            config.setMaxLifetime(
                    Integer.parseInt(props.getProperty("hikari.maxLifetime"))
            );
            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public static DataSource get() {
        return dataSource;
    }
}
