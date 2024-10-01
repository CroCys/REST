package com.cardealer.mapper;

import com.cardealer.dto.CustomerDto;
import com.cardealer.entity.Car;
import com.cardealer.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerMapperTest {
    private Customer customer;
    private CustomerDto customerDto;
    private List<Car> cars;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customerDto = new CustomerDto();
        cars = new ArrayList<>();
    }

    @Test
    void testToDto() {
        assertEquals(CustomerMapper.toDTO(customer).getClass(), CustomerDto.class);
    }

    @Test
    void testToEntity() {
        assertEquals(CustomerMapper.toEntity(customerDto, cars).getClass(), Customer.class);
    }
}
