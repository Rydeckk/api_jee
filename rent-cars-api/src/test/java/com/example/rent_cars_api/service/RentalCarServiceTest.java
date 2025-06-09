package com.example.rent_cars_api.service;


import com.example.rent_cars_api.dto.request.RentalCarPatchDto;
import com.example.rent_cars_api.exception.NotFoundRentalCarException;
import com.example.rent_cars_api.model.RentalCar;
import com.example.rent_cars_api.repository.RentalCarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalCarServiceTest {

    @Mock
    private RentalCarRepository rentalCarRepository;

    @InjectMocks
    private RentalCarService rentalCarService;

    private RentalCar testCar;
    private RentalCarPatchDto patchDto;

    @BeforeEach
    void setUp() {
        testCar = new RentalCar();
        testCar.setId(1L);
        testCar.setBrand("Toyota");
        testCar.setModel("Corolla");
        testCar.setRentAmount(50.0);
        testCar.setSecurityDepositAmount(500.0);
        testCar.setNumberOfSeats(5);
        testCar.setNumberOfDoors(4);
        testCar.setHasAirConditioning(true);

        patchDto = new RentalCarPatchDto();
    }

    @Test
    void getRentalCars_ShouldReturnListOfCars() {
        List<RentalCar> expectedCars = Arrays.asList(testCar, new RentalCar());
        when(rentalCarRepository.findAll()).thenReturn(expectedCars);

        List<RentalCar> result = rentalCarService.getRentalCars();

        assertEquals(2, result.size());
        verify(rentalCarRepository, times(1)).findAll();
    }

    @Test
    void getRentalCars_ShouldReturnEmptyList_WhenNoCarsExist() {
        when(rentalCarRepository.findAll()).thenReturn(List.of());

        List<RentalCar> result = rentalCarService.getRentalCars();

        assertTrue(result.isEmpty());
        verify(rentalCarRepository, times(1)).findAll();
    }

    @Test
    void getRentalCarById_ShouldReturnCar_WhenCarExists() {
        when(rentalCarRepository.findById(1L)).thenReturn(Optional.of(testCar));

        RentalCar result = rentalCarService.getRentalCarById(1L);

        assertNotNull(result);
        assertEquals(testCar.getId(), result.getId());
        assertEquals(testCar.getBrand(), result.getBrand());
        verify(rentalCarRepository, times(1)).findById(1L);
    }

    @Test
    void getRentalCarById_ShouldThrowException_WhenCarNotFound() {
        when(rentalCarRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundRentalCarException exception = assertThrows(
                NotFoundRentalCarException.class,
                () -> rentalCarService.getRentalCarById(1L)
        );
        assertEquals("Le rental car 1 est introuvable", exception.getMessage());
        verify(rentalCarRepository, times(1)).findById(1L);
    }

    @Test
    void createRentalCar_ShouldReturnSavedCar() {
        when(rentalCarRepository.save(testCar)).thenReturn(testCar);

        RentalCar result = rentalCarService.createRentalCar(testCar);

        assertNotNull(result);
        assertEquals(testCar.getId(), result.getId());
        verify(rentalCarRepository, times(1)).save(testCar);
    }

    @Test
    void updateRentalCar_ShouldSetIdAndSave_WhenCarExists() {
        RentalCar updatedCar = new RentalCar();
        updatedCar.setBrand("Honda");
        updatedCar.setModel("Civic");

        when(rentalCarRepository.existsById(1L)).thenReturn(true);
        when(rentalCarRepository.save(any(RentalCar.class))).thenReturn(updatedCar);

        RentalCar result = rentalCarService.updateRentalCar(1L, updatedCar);

        assertNotNull(result);
        assertEquals(1L, updatedCar.getId());
        verify(rentalCarRepository, times(1)).existsById(1L);
        verify(rentalCarRepository, times(1)).save(updatedCar);
    }

    @Test
    void updateRentalCar_ShouldNotSetId_WhenCarNotExists() {
        RentalCar updatedCar = new RentalCar();
        updatedCar.setBrand("Honda");

        when(rentalCarRepository.existsById(1L)).thenReturn(false);
        when(rentalCarRepository.save(any(RentalCar.class))).thenReturn(updatedCar);

        RentalCar result = rentalCarService.updateRentalCar(1L, updatedCar);

        assertNotNull(result);
        assertNull(updatedCar.getId());
        verify(rentalCarRepository, times(1)).existsById(1L);
        verify(rentalCarRepository, times(1)).save(updatedCar);
    }

    @Test
    void partialUpdateRentalCar_ShouldUpdateAllFields_WhenAllFieldsProvided() {
        patchDto.setBrand("Nissan");
        patchDto.setModel("Altima");
        patchDto.setRentAmount(60.0);
        patchDto.setSecurityDepositAmount(600.0);
        patchDto.setNumberOfSeats(4);
        patchDto.setNumberOfDoors(2);
        patchDto.setHasAirConditioning(false);

        when(rentalCarRepository.findById(1L)).thenReturn(Optional.of(testCar));
        when(rentalCarRepository.save(any(RentalCar.class))).thenReturn(testCar);

        RentalCar result = rentalCarService.partialUpdateRentalCar(1L, patchDto);

        assertNotNull(result);
        assertEquals("Nissan", testCar.getBrand());
        assertEquals("Altima", testCar.getModel());
        assertEquals(60.0, testCar.getRentAmount());
        assertEquals(600.0, testCar.getSecurityDepositAmount());
        assertEquals(4, testCar.getNumberOfSeats());
        assertEquals(2, testCar.getNumberOfDoors());
        assertFalse(testCar.getHasAirConditioning());
        verify(rentalCarRepository, times(1)).findById(1L);
        verify(rentalCarRepository, times(1)).save(testCar);
    }

    @Test
    void partialUpdateRentalCar_ShouldUpdateOnlyProvidedFields_WhenSomeFieldsNull() {
        String originalModel = testCar.getModel();

        patchDto.setBrand("Nissan");
        patchDto.setModel(null);
        patchDto.setRentAmount(60.0);
        patchDto.setSecurityDepositAmount(null);
        patchDto.setNumberOfSeats(null);
        patchDto.setNumberOfDoors(null);
        patchDto.setHasAirConditioning(null);

        when(rentalCarRepository.findById(1L)).thenReturn(Optional.of(testCar));
        when(rentalCarRepository.save(any(RentalCar.class))).thenReturn(testCar);

        RentalCar result = rentalCarService.partialUpdateRentalCar(1L, patchDto);

        assertNotNull(result);
        assertEquals("Nissan", testCar.getBrand());
        assertEquals(originalModel, testCar.getModel());
        assertEquals(60.0, testCar.getRentAmount());
        verify(rentalCarRepository, times(1)).findById(1L);
        verify(rentalCarRepository, times(1)).save(testCar);
    }

    @Test
    void partialUpdateRentalCar_ShouldThrowException_WhenCarNotFound() {
        when(rentalCarRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundRentalCarException exception = assertThrows(
                NotFoundRentalCarException.class,
                () -> rentalCarService.partialUpdateRentalCar(1L, patchDto)
        );
        assertEquals("Le rental car 1 est introuvable", exception.getMessage());
        verify(rentalCarRepository, times(1)).findById(1L);
        verify(rentalCarRepository, never()).save(any());
    }

    @Test
    void deleteRentalCar_ShouldCallRepositoryDelete() {
        rentalCarService.deleteRentalCar(1L);

        verify(rentalCarRepository, times(1)).deleteById(1L);
    }

    @Test
    void partialUpdateRentalCar_ShouldHandleRentAmountCorrectly_WhenNotNull() {
        Double newRentAmount = 75.0;
        patchDto.setRentAmount(newRentAmount);

        when(rentalCarRepository.findById(1L)).thenReturn(Optional.of(testCar));
        when(rentalCarRepository.save(any(RentalCar.class))).thenReturn(testCar);

        rentalCarService.partialUpdateRentalCar(1L, patchDto);

        assertEquals(newRentAmount, testCar.getRentAmount());
        verify(rentalCarRepository, times(1)).save(testCar);
    }
}