package com.cardealer.service;

import com.cardealer.dto.CustomerDto;
import com.cardealer.repository.CustomerRepository;

import java.sql.SQLException;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto getCustomerById(int id) throws SQLException {
        return customerRepository.getCustomerById(id);
    }

    public void addCustomer(CustomerDto customerDTO) throws SQLException {
        customerRepository.addCustomer(customerDTO);
    }

    public void updateCustomer(CustomerDto customerDTO) throws SQLException {
        customerRepository.updateCustomer(customerDTO);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerRepository.deleteCustomer(id);
    }
}
