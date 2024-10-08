package com.cardealer.service;

import com.cardealer.dto.ManufacturerDto;
import com.cardealer.repository.ManufacturerRepository;

import java.sql.SQLException;

public class ManufacturerService {
    private ManufacturerRepository manufacturerRepository;

    public ManufacturerService() throws SQLException {
        manufacturerRepository = new ManufacturerRepository();
    }

    public ManufacturerDto getManufacturerById(int id) throws SQLException {
        return manufacturerRepository.getManufacturerById(id);
    }

    public void addManufacturer(ManufacturerDto manufacturerDTO) throws SQLException {
        manufacturerRepository.addManufacturer(manufacturerDTO);
    }

    public void updateManufacturer(ManufacturerDto manufacturerDTO) throws SQLException {
        manufacturerRepository.updateManufacturer(manufacturerDTO);
    }

    public void deleteManufacturer(int id) throws SQLException {
        manufacturerRepository.deleteManufacturer(id);
    }
}
