package com.cardealer.service;

import com.cardealer.dto.CustomerDTO;
import com.cardealer.repository.CustomerRepository;

import java.sql.SQLException;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO getCustomerById(int id) throws SQLException {
        return customerRepository.getCustomerById(id);
    }

    public void addCustomer(CustomerDTO customerDTO) throws SQLException {
        customerRepository.addCustomer(customerDTO);
    }

    public void updateCustomer(CustomerDTO customerDTO) throws SQLException {
        customerRepository.updateCustomer(customerDTO);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerRepository.deleteCustomer(id);
    }
}
