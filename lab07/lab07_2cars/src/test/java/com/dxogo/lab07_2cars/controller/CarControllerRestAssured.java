package com.dxogo.lab07_2cars.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.dxogo.lab07_2cars.JsonUtils;
import com.dxogo.lab07_2cars.model.Car;
import com.dxogo.lab07_2cars.service.CarManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(CarController.class)
public class CarControllerRestAssured {

    @Autowired private MockMvc mvcClient;

    @MockBean private CarManagerService carManagerService;

    @BeforeEach
    public void restAssured() { RestAssuredMockMvc.mockMvc(mvcClient); }

    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        
        Car camaro = new Car("chevrolet", "camaro");
        camaro.setCarId(1L);

        when( carManagerService.save(Mockito.any()) ).thenReturn( camaro);

        RestAssuredMockMvc.given()
            .header("Content-Type", "application/json")
            .body(JsonUtils.toJson(camaro))
            .post("/api/cars")
            .then().statusCode(201)
            .and().body("maker", equalTo("chevrolet"))
            .and().body("model", equalTo("camaro"));


        verify(carManagerService, times(1)).save(Mockito.any());
    }

    @Test
    public void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car amg = new Car("mercedes", "amg");
        Car a7 = new Car("audi", "a7");
        Car leon = new Car("seat", "leon");

        List<Car> allCars = Arrays.asList(amg, a7, leon);

        when( carManagerService.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc.given()
            .header("Content-Type", "application/json")
            .get("/api/cars")
            .then().assertThat().statusCode(200)
            .and().body("", hasSize(3))
            .and().body("maker[0]", is(amg.getMaker()))
            .and().body("model[0]", is(amg.getModel()))
            .and().body("maker[1]", is(a7.getMaker()))
            .and().body("model[1]", is(a7.getModel()))
            .and().body("maker[2]", is(leon.getMaker()))
            .and().body("model[2]", is(leon.getModel()));
        
            verify(carManagerService, times(1)).getAllCars();
    }

    @Test
    public void givenId_thenCheckIfValid() throws Exception {
        Car tesla = new Car("tesla", "model s");
        tesla.setCarId(1L);

        when(carManagerService.getCarDetails(tesla.getCarId())).thenReturn(Optional.of(tesla));

        RestAssuredMockMvc.given()
            .when()
            .get("/api/cars/1")
            .then().statusCode(200)
            .and().body("maker", equalTo("tesla"))
            .and().body("model", equalTo("model s"));

        verify(carManagerService, times(1)).getCarDetails(tesla.getCarId());
    }


    @Test
    public void givenWrongId_thenCheckIfInvalid() throws Exception {
        
        RestAssuredMockMvc.given()
            .when()
            .get("/api/cars/1")
            .then().statusCode(404);

        verify(carManagerService, times(1)).getCarDetails(1L);
    }

}
