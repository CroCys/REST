package com.cardealer.service;

import com.cardealer.dto.CarDto;
import com.cardealer.repository.CarRepository;

import java.sql.SQLException;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarDto getCarById(int id) throws SQLException {
        return carRepository.getCarById(id);
    }

    public void addCar(CarDto carDTO) throws SQLException {
        carRepository.addCar(carDTO);
    }

    public void updateCar(CarDto carDTO) throws SQLException {
        carRepository.updateCar(carDTO);
    }

    public void deleteCar(int id) throws SQLException {
        carRepository.deleteCar(id);
    }
}
