package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            props.load(DBConnection.class.getClassLoader().getResourceAsStream("config.properties"));

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("hikari.maximumPoolSize")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("hikari.minimumIdle")));
            config.setConnectionTimeout(Integer.parseInt(props.getProperty("hikari.connectionTimeout")));
            config.setIdleTimeout(Integer.parseInt(props.getProperty("hikari.idleTimeout")));
            config.setMaxLifetime(Integer.parseInt(props.getProperty("hikari.maxLifetime")));

            dataSource = new HikariDataSource(config);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
