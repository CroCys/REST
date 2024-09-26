package com.cardealer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {
    private Customer customer;
    private String name;
    private List<Car> cars;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        name = "John Doe";
        cars = Collections.singletonList(new Car());
    }

    @Test
    void testNoArgConstructor() {
        Customer newCustomer = new Customer();
        assertNotNull(newCustomer);
    }

    @Test
    void testAllArgConstructor() {
        Customer newCustomer = new Customer(1, name, cars);
        assertEquals(1, newCustomer.getId());
        assertEquals("John Doe", newCustomer.getName());
        assertEquals(cars, newCustomer.getCars());
    }

    @Test
    void testSetAndGetId() {
        customer.setId(10);
        assertEquals(10, customer.getId());
    }

    @Test
    void testSetAndGetName() {
        customer.setName("Mary Jane");
        assertEquals("Mary Jane", customer.getName());
    }

    @Test
    void testSetAndGetCars() {
        customer.setCars(cars);
        assertEquals(cars, customer.getCars());
    }
}
