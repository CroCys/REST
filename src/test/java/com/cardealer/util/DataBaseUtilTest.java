package com.cardealer.util;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DataBaseUtilTest {
    private static PostgreSQLContainer<?> postgreSQLContainer;

    @BeforeEach
    void setUp() {
        if (postgreSQLContainer == null) {
            postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("cardealer")
                    .withUsername("postgres")
                    .withPassword("7894");
            postgreSQLContainer.start();

            System.setProperty("jdbc.url", postgreSQLContainer.getJdbcUrl());
            System.setProperty("jdbc.username", postgreSQLContainer.getUsername());
            System.setProperty("jdbc.password", postgreSQLContainer.getPassword());
        }
    }

    @AfterEach
    void tearDown() {
        DataBaseUtil.resetDataSource();
        if (postgreSQLContainer != null && postgreSQLContainer.isRunning()) {
            postgreSQLContainer.stop();
        }
    }

    @Test
    void testGetConnection() {
        try (Connection connection = DataBaseUtil.getConnection()) {
            assertNotNull(connection);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}
