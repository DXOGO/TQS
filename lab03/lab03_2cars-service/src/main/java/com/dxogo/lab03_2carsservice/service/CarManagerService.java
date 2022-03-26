package com.dxogo.lab03_2carsservice.service;

import java.util.List;
import java.util.Optional;

import com.dxogo.lab03_2carsservice.model.Car;
import com.dxogo.lab03_2carsservice.repository.CarRepository;

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
