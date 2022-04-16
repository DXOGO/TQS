package com.dxogo.lab06_3carsmanager.service;

import java.util.List;
import java.util.Optional;

import com.dxogo.lab06_3carsmanager.model.Car;
import com.dxogo.lab06_3carsmanager.repository.CarRepository;

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
