package com.cardealer.mapper;

import com.cardealer.dto.CarDto;
import com.cardealer.entity.Car;
import com.cardealer.entity.Customer;
import com.cardealer.entity.Manufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarMapperTest {
    private Car car;
    private CarDto carDto;
    private Manufacturer manufacturer;
    private final List<Customer> customers = Collections.singletonList(new Customer());

    @BeforeEach
    public void setUp() {
        manufacturer = new Manufacturer();
        car = new Car();
        car.setId(1);
        car.setCustomers(customers);
        car.setModel("Model X");
        car.setManufacturer(new Manufacturer());

        carDto = new CarDto();
        carDto.setId(1);
        carDto.setModel("Model X");
        carDto.setManufacturerId(1);
        List<Integer> customerIds = Collections.singletonList(1);
        carDto.setCustomerIds(customerIds);
    }

    @Test
    public void testToDto() {
        assertEquals(CarMapper.toDTO(car).getClass(), CarDto.class);
    }

    @Test
    public void testToEntity() {
        assertEquals(CarMapper.toEntity(carDto, manufacturer, customers).getClass(), Car.class);
    }
}
