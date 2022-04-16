package com.dxogo.lab06_3carsmanager.controller;

import java.util.List;

import com.dxogo.lab06_3carsmanager.exception.ResourceNotFoundException;
import com.dxogo.lab06_3carsmanager.model.Car;
import com.dxogo.lab06_3carsmanager.service.CarManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarManagerService carManagerService;

    @PostMapping(path = "/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car newCar) {
        return new ResponseEntity<>(carManagerService.save(newCar), HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/cars")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping(path = "/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) throws ResourceNotFoundException {
        Car foundCar = carManagerService.getCarDetails(carId).orElseThrow(() -> new ResourceNotFoundException("Car with id " + carId + "not found!"));
        return ResponseEntity.ok().body(foundCar);
    }
    
}
