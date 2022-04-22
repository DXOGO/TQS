package com.dxogo.lab07_4carscontainers.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static io.restassured.RestAssured.given;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.dxogo.lab07_4carscontainers.JsonUtils;
import com.dxogo.lab07_4carscontainers.model.Car;
import com.dxogo.lab07_4carscontainers.repository.CarRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@SuppressWarnings("rawtypes")
public class CarControllerTestContainer {
    
    @LocalServerPort int randomServerPort;

    @Autowired private CarRepository repository;

    
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
    
    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        
        Car camaro = new Car("chevrolet", "camaro");
        camaro.setCarId(1L);
        repository.saveAndFlush(camaro);

        given().header("Content-Type", "application/json")
            .body(JsonUtils.toJson(camaro))
            .post(getBaseUrl()+"/api/cars")
            .then().statusCode(201)
            .and().body("maker", equalTo("chevrolet"))
            .and().body("model", equalTo("camaro"));
    }    

    @Test
    public void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car amg = new Car("mercedes", "amg");
        repository.saveAndFlush(amg);
        Car a7 = new Car("audi", "a7");
        repository.saveAndFlush(a7);
        Car leon = new Car("seat", "leon");
        repository.saveAndFlush(leon);

        given().when()
            .get(getBaseUrl()+"/api/cars")
            .then().statusCode(200)
            .and().body("", hasSize(3))
            .and().body("maker[0]", is("mercedes"))
            .and().body("model[0]", is("amg"))
            .and().body("maker[1]", is("audi"))
            .and().body("model[1]", is("a7"))
            .and().body("maker[2]", is("seat"))
            .and().body("model[2]", is("leon"));
    }

    @Test
    public void givenId_thenCheckIfValid() throws Exception {
        Car tesla = new Car("tesla", "model s");
        tesla.setCarId(1L);
        repository.saveAndFlush(tesla);

        given().when()
            .get(getBaseUrl()+"/api/cars/"+tesla.getCarId().intValue())
            .then().statusCode(200)
            .and().body("maker", equalTo("tesla"))
            .and().body("model", equalTo("model s"));
    }

    @Test
    public void givenWrongId_thenCheckIfInvalid() throws Exception {
        
        given().when()
            .get(getBaseUrl()+"/api/cars/55")
            .then().statusCode(404);
    }

    public String getBaseUrl() { return "http://localhost:" + randomServerPort; }
    
}
