package com.cardealer.service;

import com.cardealer.dto.CustomerDto;
import com.cardealer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;
    private CustomerDto customerDTO;

    @BeforeEach
    void setUp() {
        customerDTO = new CustomerDto();
        customerDTO.setId(3);
        customerDTO.setName("John Doe");
    }

    @Test
    void testGetCustomerById() throws SQLException {
        when(customerRepository.getCustomerById(3)).thenReturn(customerDTO);

        CustomerDto result = customerService.getCustomerById(3);

        assertEquals(customerDTO, result);
        verify(customerRepository, times(1)).getCustomerById(3);
    }

    @Test
    void testAddCustomer() throws SQLException {
        customerService.addCustomer(customerDTO);
        verify(customerRepository, times(1)).addCustomer(customerDTO);
    }

    @Test
    void testUpdateCustomer() throws SQLException {
        customerService.updateCustomer(customerDTO);
        verify(customerRepository, times(1)).updateCustomer(customerDTO);
    }

    @Test
    void testDeleteCustomer() throws SQLException {
        customerService.deleteCustomer(3);
        verify(customerRepository, times(1)).deleteCustomer(3);
    }
}
