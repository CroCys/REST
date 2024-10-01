package com.cardealer.mapper;

import com.cardealer.dto.ManufacturerDto;
import com.cardealer.entity.Manufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManufacturerMapperTest {
    private Manufacturer manufacturer;
    private ManufacturerDto manufacturerDto;

    @BeforeEach
    void setUp() {
        manufacturer = new Manufacturer();
        manufacturerDto = new ManufacturerDto();
    }

    @Test
    void testToDto() {
        assertEquals(ManufacturerMapper.toDTO(manufacturer).getClass(), ManufacturerDto.class);
    }

    @Test
    void testToEntity() {
        assertEquals(ManufacturerMapper.toEntity(manufacturerDto).getClass(), Manufacturer.class);
    }
}
