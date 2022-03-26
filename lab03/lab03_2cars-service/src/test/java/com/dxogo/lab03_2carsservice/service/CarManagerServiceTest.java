package com.dxogo.lab03_2carsservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.dxogo.lab03_2carsservice.model.Car;
import com.dxogo.lab03_2carsservice.repository.CarRepository;

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
 
    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Car amg = new Car("mercedes", "amg");
        amg.setCarId(1L);
        Car a7 = new Car("audi", "a7");
        a7.setCarId(2L);
        Car leon = new Car("seat", "leon");
        leon.setCarId(3L);

        List<Car> allCars = Arrays.asList(amg, a7, leon);

        Mockito.when(carRepository.findById(amg.getCarId())).thenReturn(Optional.of(amg));
        Mockito.when(carRepository.findById(a7.getCarId())).thenReturn(Optional.of(a7));
        Mockito.when(carRepository.findById(leon.getCarId())).thenReturn(Optional.of(leon));
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
     void whenSearchValidId_thenCarShouldBeFound() {
         
        Long id = 1L;
        Car found = carManagerService.getCarDetails(id).orElse(null);

        // este ta na nesga
        System.out.println(found);
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

        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(amg.getModel(), a7.getModel(), leon.getModel());
    }
    

    private void verifyFindByCarIdIsCalledOnce() {
        Mockito.verify(carRepository, times(1)).findByCarId(Mockito.anyLong());
    }   

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, times(1)).findAll();
    }
    
}
