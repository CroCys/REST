package com.cardealer.service;

import com.cardealer.dto.ManufacturerDTO;
import com.cardealer.repository.ManufacturerRepository;

import java.sql.SQLException;

public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public ManufacturerDTO getManufacturerById(int id) throws SQLException {
        return manufacturerRepository.getManufacturerById(id);
    }

    public void addManufacturer(ManufacturerDTO manufacturerDTO) throws SQLException {
        manufacturerRepository.addManufacturer(manufacturerDTO);
    }

    public void updateManufacturer(ManufacturerDTO manufacturerDTO) throws SQLException {
        manufacturerRepository.updateManufacturer(manufacturerDTO);
    }

    public void deleteManufacturer(int id) throws SQLException {
        manufacturerRepository.deleteManufacturer(id);
    }
}
