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

    static {
        initDataSource();
    }

    private static void initDataSource() {
        try (InputStream input = DataBaseUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("jdbc.url"));
            config.setUsername(properties.getProperty("jdbc.username"));
            config.setPassword(properties.getProperty("jdbc.password"));
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("jdbc.pool.size")));
            config.setDriverClassName("org.postgresql.Driver");

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void setDataSource(HikariDataSource ds) {
        dataSource = ds;
    }
}
