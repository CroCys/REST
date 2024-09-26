package com.cardealer.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarDtoTest {
    private CarDto carDto;
    private final int id = 3;
    private final String model = "Model S";
    private final int manufacturerId = 3;
    private final List<Integer> customerIds = Arrays.asList(1, 2, 3);

    @BeforeEach
    void setUp() {
        carDto = new CarDto();
    }

    @Test
    void testSetAndGetId() {
        carDto.setId(id);
        assertEquals(id, carDto.getId());
    }

    @Test
    void testSetAndGetModel() {
        carDto.setModel(model);
        assertEquals(model, carDto.getModel());
    }

    @Test
    void testSetAndGetManufacturerId() {
        carDto.setManufacturerId(manufacturerId);
        assertEquals(manufacturerId, carDto.getManufacturerId());
    }

    @Test
    void testSetAndGetCustomerIds() {
        carDto.setCustomerIds(customerIds);
        assertEquals(customerIds, carDto.getCustomerIds());
    }
}
