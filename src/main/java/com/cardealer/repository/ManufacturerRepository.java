package com.cardealer.repository;

import com.cardealer.dto.ManufacturerDTO;
import com.cardealer.entity.Manufacturer;
import com.cardealer.mapper.ManufacturerMapper;
import com.cardealer.util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManufacturerRepository {
    private final Connection connection;

    public ManufacturerRepository() throws SQLException {
        this.connection = DataBaseUtil.getConnection();
    }

    public ManufacturerDTO getManufacturerById(int id) throws SQLException {
        String query = "SELECT * FROM manufacturer WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = mapToManufacturer(resultSet);
                return ManufacturerMapper.toDTO(manufacturer);
            }
        }
        return null;
    }

    public void addManufacturer(ManufacturerDTO manufacturerDTO) throws SQLException {
        Manufacturer manufacturer = ManufacturerMapper.toEntity(manufacturerDTO);

        String query = "INSERT INTO manufacturer (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.executeUpdate();
        }
    }

    public void updateManufacturer(ManufacturerDTO manufacturerDTO) throws SQLException {
        Manufacturer manufacturer = ManufacturerMapper.toEntity(manufacturerDTO);

        String query = "UPDATE manufacturer SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.setInt(2, manufacturer.getId());
            statement.executeUpdate();
        }
    }

    public void deleteManufacturer(int id) throws SQLException {
        String query = "DELETE FROM manufacturer WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Manufacturer mapToManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getInt("id"));
        manufacturer.setName(resultSet.getString("name"));
        return manufacturer;
    }
}
