package com.pulse.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DatabaseConfig.getUrl());
        config.setUsername(DatabaseConfig.getUser());
        config.setPassword(DatabaseConfig.getPassword());
        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);

    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();

    }
    
}
