package com.cardealer.repository;

import com.cardealer.dto.CustomerDto;
import com.cardealer.util.DataBaseUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryTest {
    @InjectMocks
    private CustomerRepository customerRepository;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<DataBaseUtil> mockedDataBaseUtil;

    @BeforeEach
    void setUp() throws SQLException {
        if (mockedDataBaseUtil != null) {
            mockedDataBaseUtil.close();
        }

        mockedDataBaseUtil = Mockito.mockStatic(DataBaseUtil.class);
        when(DataBaseUtil.getConnection()).thenReturn(connection);
    }

    @AfterEach
    void tearDown() {
        mockedDataBaseUtil.close();
    }

    @Test
    void testGetCustomerById() throws SQLException {
        int customerId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(customerId);
        when(resultSet.getString("name")).thenReturn("John Doe");

        CustomerDto result = customerRepository.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("John Doe", result.getName());
        verify(preparedStatement).setInt(1, customerId);
    }

    @Test
    void testAddCustomer() throws SQLException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Jane Doe");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        customerRepository.addCustomer(customerDto);

        verify(preparedStatement, Mockito.times(2)).executeUpdate();
        verify(preparedStatement).setString(1, customerDto.getName());
    }

    @Test
    void testUpdateCustomer() throws SQLException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1);
        customerDto.setName("John Smith");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        customerRepository.updateCustomer(customerDto);

        verify(preparedStatement, Mockito.times(2)).executeUpdate();
        verify(preparedStatement).setString(1, customerDto.getName());
        verify(preparedStatement).setInt(2, customerDto.getId());
    }

    @Test
    void testDeleteCustomer() throws SQLException {
        int customerId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        customerRepository.deleteCustomer(customerId);

        verify(preparedStatement).setInt(1, customerId);
        verify(preparedStatement).executeUpdate();
    }
}
