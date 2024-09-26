package com.cardealer.service;

import com.cardealer.dto.CarDto;
import com.cardealer.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;
    private CarDto carDTO;

    @BeforeEach
    void setUp() {
        carDTO = new CarDto();
        carDTO.setId(1);
        carDTO.setModel("Tesla");
        carDTO.setCustomerIds(Collections.singletonList(3));
        carDTO.setManufacturerId(3);
    }

    @Test
    void testGetCarById() throws SQLException {
        when(carRepository.getCarById(1)).thenReturn(carDTO);
        CarDto result = carService.getCarById(1);
        assertEquals(carDTO, result);
        verify(carRepository, times(1)).getCarById(1);
    }

    @Test
    void testGetCarByIdException() throws SQLException {
        when(carRepository.getCarById(1)).thenThrow(SQLException.class);
        assertThrows(SQLException.class, () -> carService.getCarById(1));
        verify(carRepository, times(1)).getCarById(1);
    }

    @Test
    void testAddCar() throws SQLException {
        carService.addCar(carDTO);
        verify(carRepository, times(1)).addCar(carDTO);
    }

    @Test
    void testAddCarException() throws SQLException {
        doThrow(SQLException.class).when(carRepository).addCar(carDTO);
        assertThrows(SQLException.class, () -> carService.addCar(carDTO));
        verify(carRepository, times(1)).addCar(carDTO);
    }

    @Test
    void testUpdateCar() throws SQLException {
        carService.updateCar(carDTO);
        verify(carRepository, times(1)).updateCar(carDTO);
    }

    @Test
    void testUpdateCarException() throws SQLException {
        doThrow(SQLException.class).when(carRepository).updateCar(carDTO);
        assertThrows(SQLException.class, () -> carService.updateCar(carDTO));
        verify(carRepository, times(1)).updateCar(carDTO);
    }

    @Test
    void testDeleteCar() throws SQLException {
        carService.deleteCar(1);
        verify(carRepository, times(1)).deleteCar(1);
    }

    @Test
    void testDeleteCarException() throws SQLException {
        doThrow(SQLException.class).when(carRepository).deleteCar(1);
        assertThrows(SQLException.class, () -> carService.deleteCar(1));
        verify(carRepository, times(1)).deleteCar(1);
    }
}
