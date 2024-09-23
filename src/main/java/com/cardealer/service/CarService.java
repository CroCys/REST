package com.cardealer.service;

import com.cardealer.dto.CarDTO;
import com.cardealer.repository.CarRepository;

import java.sql.SQLException;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarDTO getCarById(int id) throws SQLException {
        return carRepository.getCarById(id);
    }

    public void addCar(CarDTO carDTO) throws SQLException {
        carRepository.addCar(carDTO);
    }

    public void updateCar(CarDTO carDTO) throws SQLException {
        carRepository.updateCar(carDTO);
    }

    public void deleteCar(int id) throws SQLException {
        carRepository.deleteCar(id);
    }
}
