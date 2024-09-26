package com.cardealer.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DataBaseUtilTest {
    private static PostgreSQLContainer<?> postgreSQLContainer;

    @BeforeAll
    static void setUp() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3")
                .withDatabaseName("cardealer")
                .withUsername("postgres")
                .withPassword("7894");
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testGetConnection() {
        try (Connection connection = DataBaseUtil.getConnection()) {
            assertNotNull(connection);
            assertTrue(connection.isValid(1));
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}
