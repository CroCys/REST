package com.cardealer.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerDtoTest {
    private CustomerDto customerDto;
    private final int id = 3;
    private final String name = "John Doe";

    @BeforeEach
    void setUp() {
        customerDto = new CustomerDto();
    }

    @Test
    void testSetAndGetId() {
        customerDto.setId(id);
        assertEquals(id, customerDto.getId());
    }

    @Test
    void testSetAndGetName() {
        customerDto.setName(name);
        assertEquals(name, customerDto.getName());
    }
}
