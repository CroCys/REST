package com.cardealer.repository;

import com.cardealer.dto.CustomerDTO;
import com.cardealer.entity.Car;
import com.cardealer.entity.Customer;
import com.cardealer.mapper.CustomerMapper;
import com.cardealer.util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerRepository {
    private final Connection connection;

    public CustomerRepository() throws SQLException {
        this.connection = DataBaseUtil.getConnection();
    }

    public CustomerDTO getCustomerById(int id) throws SQLException {
        String query = "SELECT * FROM customer WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = mapToCustomer(resultSet);
                return CustomerMapper.toDTO(customer);
            }
        }
        return null;
    }

    public void addCustomer(CustomerDTO customerDTO) throws SQLException {
        Customer customer = CustomerMapper.toEntity(customerDTO, new ArrayList<>());

        String query = "INSERT INTO customer (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.executeUpdate();
        }

        // Update relationships (Customer-Car)
        updateCustomerCars(customer);
    }

    public void updateCustomer(CustomerDTO customerDTO) throws SQLException {
        Customer customer = CustomerMapper.toEntity(customerDTO, new ArrayList<>());

        String query = "UPDATE customer SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.setInt(2, customer.getId());
            statement.executeUpdate();
        }

        // Update relationships (Customer-Car)
        updateCustomerCars(customer);
    }

    public void deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM customer WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private void updateCustomerCars(Customer customer) throws SQLException {
        String deleteQuery = "DELETE FROM car_customer WHERE customer_id = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, customer.getId());
            deleteStatement.executeUpdate();
        }

        String insertQuery = "INSERT INTO car_customer (car_id, customer_id) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            for (Car car : customer.getCars()) {
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
}
