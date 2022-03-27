package com.dxogo.lab03_2_3carsmanager.repository;

import com.dxogo.lab03_2_3carsmanager.model.Car;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class CarRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindMakerByModel_thenReturnCar() {
        
        Car camaro = new Car("chevrolet", "camaro");
        camaro.setCarId(1L);
        entityManager.persistAndFlush(camaro); //ensure data is persisted at this point

        // test the query method of interest
        Car found = carRepository.findByCarId(camaro.getCarId()).orElse(null);
        assertThat( found.getModel() ).isEqualTo(camaro.getModel());
    }

    @Test
    void whenInvalidCarModel_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-99L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCars_whenFindAll_thenReturnAllCara() {
         Car amg = new Car("mercedes", "amg");
         amg.setCarId(1L);
         Car a7 = new Car("audi", "a7");
         a7.setCarId(2L);
         Car leon = new Car("seat", "leon");
         leon.setCarId(3L);

        entityManager.persist(amg);
        entityManager.persist(a7);
        entityManager.persist(leon);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(amg.getModel(), a7.getModel(), leon.getModel());
    }
    
}
