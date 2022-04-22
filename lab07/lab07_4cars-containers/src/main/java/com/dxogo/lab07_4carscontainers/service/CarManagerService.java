package com.dxogo.lab07_4carscontainers.service;

import java.util.List;
import java.util.Optional;

import com.dxogo.lab07_4carscontainers.model.Car;
import com.dxogo.lab07_4carscontainers.repository.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManagerService {

    @Autowired
    private CarRepository carRepository;

    public Car save(Car car) { return carRepository.save(car); }

    public List<Car> getAllCars() { return carRepository.findAll(); }

    public Optional<Car> getCarDetails(Long id) { return carRepository.findByCarId(id); }
    
}
