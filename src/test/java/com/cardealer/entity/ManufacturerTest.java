package com.cardealer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManufacturerTest {
    private Manufacturer manufacturer;
    private String name;
    private List<Car> cars;

    @BeforeEach
    public void setUp() {
        manufacturer = new Manufacturer();
        name = "John Doe";
        cars = Collections.singletonList(new Car());
    }

    @Test
    void testNoArgsConstructor() {
        Manufacturer newManufacturer = new Manufacturer();
        assertNotNull(newManufacturer);
    }

    @Test
    void testAllArgsConstructor() {
        Manufacturer newManufacturer = new Manufacturer(1, name, cars);
        assertEquals(1, newManufacturer.getId());
        assertEquals(name, newManufacturer.getName());
        assertEquals(cars, newManufacturer.getCars());
    }

    @Test
    void testSetAndGetId() {
        manufacturer.setId(2);
        assertEquals(2, manufacturer.getId());
    }

    @Test
    void testSetAndGetName() {
        manufacturer.setName("Bruce Lee");
        assertEquals("Bruce Lee", manufacturer.getName());
    }

    @Test
    void testSetAndGetCars() {
        manufacturer.setCars(cars);
        assertEquals(cars, manufacturer.getCars());
    }
}
