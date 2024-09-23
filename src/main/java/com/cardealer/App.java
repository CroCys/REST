package com.cardealer;

import com.cardealer.dto.CarDTO;
import com.cardealer.repository.CarRepository;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        CarRepository carRepository = new CarRepository();
        CarDTO carDTO = carRepository.getCarById(1);
        System.out.println(carDTO.getModel());
    }
}
