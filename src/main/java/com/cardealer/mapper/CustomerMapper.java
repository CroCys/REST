package com.cardealer.mapper;

import com.cardealer.dto.CustomerDTO;
import com.cardealer.entity.Car;
import com.cardealer.entity.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setCarIds(customer.getCars().stream().map(Car::getId).collect(Collectors.toList()));
        return dto;
    }

    public static Customer toEntity(CustomerDTO dto, List<Car> cars) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setCars(cars);
        return customer;
    }
}
