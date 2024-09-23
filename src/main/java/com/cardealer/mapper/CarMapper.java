package com.cardealer.mapper;

import com.cardealer.dto.CarDTO;
import com.cardealer.entity.Car;
import com.cardealer.entity.Customer;
import com.cardealer.entity.Manufacturer;

import java.util.List;
import java.util.stream.Collectors;

public class CarMapper {
    public static CarDTO toDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setModel(car.getModel());
        dto.setManufacturerId(car.getManufacturer().getId());
        dto.setCustomerIds(car.getCustomers().stream().map(Customer::getId).collect(Collectors.toList()));
        return dto;
    }

    public static Car toEntity(CarDTO dto, Manufacturer manufacturer, List<Customer> customers) {
        Car car = new Car();
        car.setId(dto.getId());
        car.setModel(dto.getModel());
        car.setManufacturer(manufacturer);
        car.setCustomers(customers);
        return car;
    }
}
