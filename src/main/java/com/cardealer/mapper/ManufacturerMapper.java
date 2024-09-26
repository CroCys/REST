package com.cardealer.mapper;

import com.cardealer.dto.ManufacturerDto;
import com.cardealer.entity.Manufacturer;

public class ManufacturerMapper {
    public static ManufacturerDto toDTO(Manufacturer manufacturer) {
        ManufacturerDto dto = new ManufacturerDto();
        dto.setId(manufacturer.getId());
        dto.setName(manufacturer.getName());
        return dto;
    }

    public static Manufacturer toEntity(ManufacturerDto dto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(dto.getId());
        manufacturer.setName(dto.getName());
        return manufacturer;
    }
}
