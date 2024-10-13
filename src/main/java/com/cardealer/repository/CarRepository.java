package com.cardealer.repository;

import com.cardealer.dto.CarDto;
import com.cardealer.entity.Car;
import com.cardealer.entity.Customer;
import com.cardealer.entity.Manufacturer;
import com.cardealer.mapper.CarMapper;
import com.cardealer.mapper.CustomerMapper;
import com.cardealer.mapper.ManufacturerMapper;
import com.cardealer.util.DataBaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    public CarDto getCarById(int carId) throws SQLException {
        String sql = "SELECT c.id, c.model, c.manufacturer_id, m.id as manufacturerId, m.name as manufacturerName " +
                "FROM cars c JOIN manufacturers m ON c.manufacturer_id = m.id WHERE c.id = ?";
        try (PreparedStatement preparedStatement = DataBaseUtil.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CarDto carDto = new CarDto();
                carDto.setId(resultSet.getInt("id"));
                carDto.setModel(resultSet.getString("model"));
                carDto.setManufacturerId(resultSet.getInt("manufacturerId"));
                return carDto;
            } else {
                return null;
            }
        }
    }

    public void addCar(CarDto carDTO) throws SQLException {
        Manufacturer manufacturer = getManufacturerById(carDTO.getManufacturerId());
        List<Customer> customers = getCustomersByIds(carDTO.getCustomerIds());

        Car car = CarMapper.toEntity(carDTO, manufacturer, customers);

        String query = "INSERT INTO car (model, manufacturer_id) VALUES (?, ?)";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getModel());
            statement.setInt(2, car.getManufacturer().getId());
            statement.executeUpdate();
        }

        updateCarCustomers(car);
    }

    public void updateCar(CarDto carDTO) throws SQLException {
        Manufacturer manufacturer = getManufacturerById(carDTO.getManufacturerId());
        List<Customer> customers = getCustomersByIds(carDTO.getCustomerIds());

        Car car = CarMapper.toEntity(carDTO, manufacturer, customers);

        String query = "UPDATE car SET model = ?, manufacturer_id = ? WHERE id = ?";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getModel());
            statement.setInt(2, car.getManufacturer().getId());
            statement.setInt(3, car.getId());
            statement.executeUpdate();
        }

        updateCarCustomers(car);
    }

    public void deleteCar(int id) throws SQLException {
        deleteCarCustomers(id);
        deleteCarById(id);
    }

    private void deleteCarCustomers(int carId) throws SQLException {
        String deleteCarCustomerSql = "DELETE FROM car_customer WHERE car_id = ?";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement deleteCarCustomerStmt = connection.prepareStatement(deleteCarCustomerSql)) {
            deleteCarCustomerStmt.setInt(1, carId);
            deleteCarCustomerStmt.executeUpdate();
        }
    }

    private void deleteCarById(int id) throws SQLException {
        String query = "DELETE FROM car WHERE id = ?";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    Manufacturer getManufacturerById(int manufacturerId) throws SQLException {
        String query = "SELECT * FROM manufacturer WHERE id = ?";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, manufacturerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return ManufacturerMapper.toEntity(ManufacturerMapper.toDTO(mapToManufacturer(resultSet)));
            }
        }
        return null;
    }

    List<Customer> getCustomersByIds(List<Integer> customerIds) throws SQLException {
        if (customerIds.isEmpty()) return new ArrayList<>();

        String query = "SELECT * FROM customer WHERE id = ANY (?)";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setArray(1, connection.createArrayOf("INTEGER", customerIds.toArray()));
            ResultSet resultSet = statement.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(CustomerMapper.toEntity(CustomerMapper.toDTO(mapToCustomer(resultSet)), new ArrayList<>()));
            }
            return customers;
        }
    }

    void updateCarCustomers(Car car) throws SQLException {
        String deleteQuery = "DELETE FROM car_customer WHERE car_id = ?";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, car.getId());
            deleteStatement.executeUpdate();
        }

        String insertQuery = "INSERT INTO car_customer (car_id, customer_id) VALUES (?, ?)";
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            for (Customer customer : car.getCustomers()) {
                insertStatement.setInt(1, car.getId());
                insertStatement.setInt(2, customer.getId());
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    }

    private Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setName(resultSet.getString("name"));
        return customer;
    }

    private Manufacturer mapToManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getInt("id"));
        manufacturer.setName(resultSet.getString("name"));
        return manufacturer;
    }
}
