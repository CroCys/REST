package com.cardealer.mapper;

import com.cardealer.dto.ManufacturerDTO;
import com.cardealer.entity.Manufacturer;

public class ManufacturerMapper {
    public static ManufacturerDTO toDTO(Manufacturer manufacturer) {
        ManufacturerDTO dto = new ManufacturerDTO();
        dto.setId(manufacturer.getId());
        dto.setName(manufacturer.getName());
        return dto;
    }

    public static Manufacturer toEntity(ManufacturerDTO dto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(dto.getId());
        manufacturer.setName(dto.getName());
        return manufacturer;
    }
}
