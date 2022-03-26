package com.dxogo.lab03_2carsservice.repository;

import java.util.List;
import java.util.Optional;

import com.dxogo.lab03_2carsservice.model.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    public Optional<Car> findByCarId(Long id);    
    public List<Car> findAll();

}