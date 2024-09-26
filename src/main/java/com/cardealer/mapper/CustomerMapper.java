package com.cardealer.mapper;

import com.cardealer.dto.CustomerDto;
import com.cardealer.entity.Car;
import com.cardealer.entity.Customer;

import java.util.List;

public class CustomerMapper {
    public static CustomerDto toDTO(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        return dto;
    }

    public static Customer toEntity(CustomerDto dto, List<Car> cars) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setCars(cars);
        return customer;
    }
}
