package com.dxogo.lab07_2cars.controller;

import java.io.IOException;
import java.util.List;

import com.dxogo.lab07_2cars.CarsServiceApplication;
import com.dxogo.lab07_2cars.JsonUtils;
import com.dxogo.lab07_2cars.model.Car;
import com.dxogo.lab07_2cars.repository.CarRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = CarsServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CarControllerITTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
     void whenValidInput_thenCreateCar() throws IOException, Exception {
        Car camaro = new Car("chevrolet", "camaro");
        camaro.setCarId(1L);

        mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(camaro)));

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly("camaro");
    }

    @Test
     void givenCars_whenGetCars_thenStatus200() throws Exception {
        Car amg = new Car("mercedes", "amg");
        amg.setCarId(1L);
        Car a7 = new Car("audi", "a7");
        a7.setCarId(2L);

        carRepository.saveAndFlush(amg);
        carRepository.saveAndFlush(a7);


        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].maker", is("mercedes")))
                .andExpect(jsonPath("$[0].model", is("amg")))
                .andExpect(jsonPath("$[1].maker", is("audi")))
                .andExpect(jsonPath("$[1].model", is("a7")));
    }


}
