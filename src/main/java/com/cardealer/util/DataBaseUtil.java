package com.cardealer.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseUtil {
    private static HikariDataSource dataSource;

    private static void initDataSource() {
        try (InputStream input = DataBaseUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String jdbcUrl = System.getProperty("jdbc.url", properties.getProperty("jdbc.url"));
            String jdbcUsername = System.getProperty("jdbc.username", properties.getProperty("jdbc.username"));
            String jdbcPassword = System.getProperty("jdbc.password", properties.getProperty("jdbc.password"));
            String jdbcDriver = System.getProperty("jdbc.driver", properties.getProperty("jdbc.driver", "org.postgresql.Driver"));

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);  // Используем URL из системных свойств
            config.setUsername(jdbcUsername);  // Используем Username из системных свойств
            config.setPassword(jdbcPassword);  // Используем Password из системных свойств
            config.setDriverClassName(jdbcDriver);  // Используем Driver
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("jdbc.pool.size", "10")));

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initDataSource();
        }
        return dataSource.getConnection();
    }

    public static void resetDataSource() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }

    public static void setDataSource(HikariDataSource ds) {
        dataSource = ds;
    }
}
