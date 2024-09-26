package com.cardealer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarTest {
    private Car car;
    private Manufacturer manufacturer;
    private List<Customer> customers;

    @BeforeEach
    void setUp() {
        List<Car> cars = Collections.singletonList(new Car());
        manufacturer = new Manufacturer(1, "Tesla", cars);
        customers = Arrays.asList(
                new Customer(1, "John Doe", cars),
                new Customer(2, "Jane Smith", cars)
        );
        car = new Car();
    }

    @Test
    void testNoArgsConstructor() {
        Car newCar = new Car();
        assertNotNull(newCar);
    }

    @Test
    void testAllArgsConstructor() {
        Car newCar = new Car(1, "Model S", manufacturer, customers);
        assertEquals(1, newCar.getId());
        assertEquals("Model S", newCar.getModel());
        assertEquals(manufacturer, newCar.getManufacturer());
        assertEquals(customers, newCar.getCustomers());
    }

    @Test
    void testSetAndGetId() {
        car.setId(10);
        assertEquals(10, car.getId());
    }

    @Test
    void testSetAndGetModel() {
        car.setModel("Model 3");
        assertEquals("Model 3", car.getModel());
    }

    @Test
    void testSetAndGetManufacturer() {
        car.setManufacturer(manufacturer);
        assertEquals(manufacturer, car.getManufacturer());
    }

    @Test
    void testSetAndGetCustomers() {
        car.setCustomers(customers);
        assertEquals(customers, car.getCustomers());
    }
}
