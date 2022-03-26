package com.dxogo.lab03_2carsservice.controller;

import java.util.List;

import com.dxogo.lab03_2carsservice.model.Car;
import com.dxogo.lab03_2carsservice.repository.CarRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
@TestPropertySource( locations = "application-integrationtest.properties")
public class CarControllerRestTemplateTest {
    
    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
     void whenValidInput_thenCreateCar() {
        Car camaro = new Car("chevrolet", "camaro");
        camaro.setCarId(1L);
        restTemplate.postForEntity("/api/cars", camaro, Car.class);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly("camaro");
    }

    @Test
    void givenCar_whenGetCar_thenStatus200()  {
        
        Car amg = new Car("mercedes", "amg");
        amg.setCarId(1L);
        Car a7 = new Car("audi", "a7");
        a7.setCarId(2L);

        carRepository.saveAndFlush(amg);
        carRepository.saveAndFlush(a7);


       ResponseEntity<List<Car>> response = restTemplate
               .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
               });

       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
       assertThat(response.getBody()).extracting(Car::getModel).containsExactly("amg", "a7");

   }

   @Test
   void getCar_returnsCarDetails() {

        Car leon = new Car("seat", "leon");
        leon.setCarId(1L);

        carRepository.saveAndFlush(leon);

        ResponseEntity<Car> entity = restTemplate.getForEntity("/api/cars/1", Car.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).extracting(Car::getModel).isEqualTo("leon");
   }


}
