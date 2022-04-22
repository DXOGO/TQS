package com.dxogo.lab07_2cars.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.dxogo.lab07_2cars.model.Car;
import com.dxogo.lab07_2cars.repository.CarRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CarManagerServiceTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {

        // these expectations provide an alternative to the use of the repository
        Car amg = new Car("mercedes", "amg");
        amg.setCarId(1L);
        Car a7 = new Car("audi", "a7");
        a7.setCarId(2L);
        Car leon = new Car("seat", "leon");
        leon.setCarId(3L);

        List<Car> allCars = Arrays.asList(amg, a7, leon);

        Mockito.when(carRepository.findByCarId(amg.getCarId())).thenReturn(Optional.of(amg));
        Mockito.when(carRepository.findByCarId(a7.getCarId())).thenReturn(Optional.of(a7));
        Mockito.when(carRepository.findByCarId(leon.getCarId())).thenReturn(Optional.of(leon));
        Mockito.when(carRepository.findByCarId(-99L)).thenReturn(null);
        Mockito.when(carRepository.findByCarId(-99L)).thenReturn(Optional.empty());
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
    }

    @Test
    void whenSearchValidId_thenCarShouldBeFound() {

        Long id = 1L;
        Car found = carManagerService.getCarDetails(id).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getModel()).isEqualTo("amg");
        assertThat(found.getCarId()).isEqualTo(id);

        verifyFindByCarIdIsCalledOnce();
    }

    @Test
    void whenSearchInvalidId_thenCarShouldNotBeFound() {
        Optional<Car> fromDb = carManagerService.getCarDetails(-99L);

        assertThat(fromDb.isEmpty()).isTrue();

        verifyFindByCarIdIsCalledOnce();
    }

    @Test
    public void given3Cars_whengetAll_thenReturn3Records() {
        Car amg = new Car("mercedes", "amg");
        amg.setCarId(1L);
        Car a7 = new Car("audi", "a7");
        a7.setCarId(2L);
        Car leon = new Car("seat", "leon");
        leon.setCarId(3L);

        List<Car> allCars = carManagerService.getAllCars();

        verifyFindAllCarsIsCalledOnce();

        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(amg.getModel(), a7.getModel(),
                leon.getModel());
    }

    private void verifyFindByCarIdIsCalledOnce() {
        Mockito.verify(carRepository, times(1)).findByCarId(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, times(1)).findAll();
    }

}
