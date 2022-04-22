package com.dxogo.lab07_4carscontainers.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

import com.dxogo.lab07_4carscontainers.JsonUtils;
import com.dxogo.lab07_4carscontainers.model.Car;
import com.dxogo.lab07_4carscontainers.service.CarManagerService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CarController.class) // Spring MVC test that focuses only on Spring MVC components.
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carManagerService;

    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        
        Car camaro = new Car("chevrolet", "camaro");
        camaro.setCarId(1L);

        when( carManagerService.save(Mockito.any()) ).thenReturn( camaro);

        mvc.perform(
            post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(camaro)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.maker", is("chevrolet")))
            .andExpect(jsonPath("$.model", is("camaro")));


        verify(carManagerService, times(1)).save(Mockito.any());
    }
    
    @Test
    public void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car amg = new Car("mercedes", "amg");
        Car a7 = new Car("audi", "a7");
        Car leon = new Car("seat", "leon");

        List<Car> allCars = Arrays.asList(amg, a7, leon);

        when( carManagerService.getAllCars()).thenReturn(allCars);

        mvc.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(amg.getMaker())))
                .andExpect(jsonPath("$[0].model", is(amg.getModel())))
                .andExpect(jsonPath("$[1].maker", is(a7.getMaker())))
                .andExpect(jsonPath("$[1].model", is(a7.getModel())))
                .andExpect(jsonPath("$[2].maker", is(leon.getMaker())))
                .andExpect(jsonPath("$[2].model", is(leon.getModel())));
        
                verify(carManagerService, times(1)).getAllCars();
    }

    @Test
    public void givenId_thenCheckIfValid() throws Exception {
        Car tesla = new Car("tesla", "model s");
        tesla.setCarId(1L);

        when(carManagerService.getCarDetails(tesla.getCarId())).thenReturn(Optional.of(tesla));

        mvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("maker").value("tesla"))
                .andExpect(jsonPath("model").value("model s"));

        verify(carManagerService, times(1)).getCarDetails(tesla.getCarId());
    }

    @Test
    public void givenWrongId_thenCheckIfInvalid() throws Exception {
        
        mvc.perform(get("/api/cars/1"))
                .andExpect(status().isNotFound());

        verify(carManagerService, times(1)).getCarDetails(1L);
    }



}
