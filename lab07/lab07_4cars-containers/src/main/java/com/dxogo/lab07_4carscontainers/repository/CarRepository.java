package com.dxogo.lab07_4carscontainers.repository;

import java.util.List;
import java.util.Optional;

import com.dxogo.lab07_4carscontainers.model.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    public Optional<Car> findByCarId(Long id);    
    public List<Car> findAll();

}