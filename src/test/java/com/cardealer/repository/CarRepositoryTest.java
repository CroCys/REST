package com.cardealer.repository;

import com.cardealer.dto.CarDto;
import com.cardealer.entity.Car;
import com.cardealer.entity.Customer;
import com.cardealer.entity.Manufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarRepositoryTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarRepository carRepoUnderTest;

    private CarDto carDto;
    private Car car;

    @BeforeEach
    void setUp() {
        carDto = new CarDto();
        carDto.setId(1);
        carDto.setModel("Tesla");
        carDto.setCustomerIds(Collections.singletonList(3));
        carDto.setManufacturerId(3);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(3);
        Customer customer = new Customer();
        customer.setId(3);
        car = new Car();
        car.setId(1);
        car.setModel("Tesla");
        car.setManufacturer(manufacturer);
        car.setCustomers(Collections.singletonList(customer));
    }

    @Test
    void testGetCarById() throws SQLException {
        when(carRepository.getCarById(1)).thenReturn(carDto);

        CarDto result = carRepository.getCarById(1);

        assertEquals(carDto, result);

        verify(carRepository, times(1)).getCarById(1);
    }

    @Test
    void testAddCar() throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(3);

        Car car = new Car();
        car.setId(1);
        car.setModel("Tesla");
        car.setManufacturer(manufacturer);
        car.setCustomers(Collections.emptyList());

        doNothing().when(carRepository).addCar(carDto);

        carRepository.addCar(carDto);

        verify(carRepository, times(1)).addCar(carDto);
    }

//    @Test
//    void testUpdateCar() throws SQLException {
//        Manufacturer manufacturer = new Manufacturer();
//        manufacturer.setId(3);
//
//        Car car = new Car();
//        car.setId(carDto.getId());
//        car.setModel(carDto.getModel());
//        car.setManufacturer(manufacturer);
//        car.setCustomers(Collections.emptyList());
//
//        when(carRepository.getCarById(carDto.getId())).thenReturn(carDto);
//
//        Car mappedCar = CarMapper.toEntity(carDto, manufacturer, Collections.emptyList());
//
//        carRepoUnderTest.updateCar(carDto);
//
//        verify(carRepository, times(1)).updateCar(eq(carDto));
//    }

    @Test
    void testDeleteCar() throws SQLException {
        carRepository.deleteCar(1);
        verify(carRepository, times(1)).deleteCar(1);
    }
}
