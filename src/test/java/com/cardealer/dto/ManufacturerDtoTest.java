package com.cardealer.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManufacturerDtoTest {
    private ManufacturerDto manufacturerDto;
    private final int id = 3;
    private final String name = "John Doe";

    @BeforeEach
    void setUp() {
        manufacturerDto = new ManufacturerDto();
    }

    @Test
    void testSetAndGetId() {
        manufacturerDto.setId(id);
        assertEquals(id, manufacturerDto.getId());
    }

    @Test
    void testSetAndGetName() {
        manufacturerDto.setName(name);
        assertEquals(name, manufacturerDto.getName());
    }
}
