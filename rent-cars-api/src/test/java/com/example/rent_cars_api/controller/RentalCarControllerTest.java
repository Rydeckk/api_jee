package com.example.rent_cars_api.controller;

import com.example.rent_cars_api.dto.request.RentalCarPatchDto;
import com.example.rent_cars_api.dto.response.RentalCarResponseDto;
import com.example.rent_cars_api.exception.NotFoundRentalCarException;
import com.example.rent_cars_api.mapper.RentalCarDtoMapper;
import com.example.rent_cars_api.model.RentalCar;
import com.example.rent_cars_api.service.RentalCarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentalCarController.class)
class RentalCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RentalCarService rentalCarService;

    @MockitoBean
    private RentalCarDtoMapper rentalCarDtoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private RentalCar testCar;
    private RentalCarResponseDto testCarDto;
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

        testCarDto = new RentalCarResponseDto("Toyota", "Corolla", 50.0, 500.0, 5, 4, true);

        patchDto = new RentalCarPatchDto();
        patchDto.setBrand("Nissan");
        patchDto.setRentAmount(60.0);
    }

    @Test
    void getRentalCars_ShouldReturnListOfCars_WhenCarsExist() throws Exception {
        RentalCar car2 = new RentalCar();
        car2.setId(2L);
        car2.setBrand("Honda");
        car2.setModel("Civic");

        List<RentalCar> cars = Arrays.asList(testCar, car2);
        List<RentalCarResponseDto> carDtos = Arrays.asList(
                testCarDto,
                new RentalCarResponseDto("Honda", "Civic", null, null, null, null, null)
        );

        when(rentalCarService.getRentalCars()).thenReturn(cars);
        when(rentalCarDtoMapper.mapToDtoList(cars)).thenReturn(carDtos);

        mockMvc.perform(get("/api/rent-cars-api/rental-cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].brand", is("Toyota")))
                .andExpect(jsonPath("$[0].model", is("Corolla")))
                .andExpect(jsonPath("$[1].brand", is("Honda")))
                .andExpect(jsonPath("$[1].model", is("Civic")));

        verify(rentalCarService, times(1)).getRentalCars();
        verify(rentalCarDtoMapper, times(1)).mapToDtoList(cars);
    }

    @Test
    void getRentalCars_ShouldReturnEmptyList_WhenNoCarsExist() throws Exception {
        when(rentalCarService.getRentalCars()).thenReturn(Collections.emptyList());
        when(rentalCarDtoMapper.mapToDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/rent-cars-api/rental-cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(rentalCarService, times(1)).getRentalCars();
        verify(rentalCarDtoMapper, times(1)).mapToDtoList(Collections.emptyList());
    }

    @Test
    void getRentalCarById_ShouldReturnCar_WhenCarExists() throws Exception {
        when(rentalCarService.getRentalCarById(1L)).thenReturn(testCar);
        when(rentalCarDtoMapper.mapToDto(testCar)).thenReturn(testCarDto);

        mockMvc.perform(get("/api/rent-cars-api/rental-cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brand", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")))
                .andExpect(jsonPath("$.rentAmount", is(50.0)))
                .andExpect(jsonPath("$.securityDepositAmount", is(500.0)))
                .andExpect(jsonPath("$.numberOfSeats", is(5)))
                .andExpect(jsonPath("$.numberOfDoors", is(4)))
                .andExpect(jsonPath("$.hasAirConditioning", is(true)));

        verify(rentalCarService, times(1)).getRentalCarById(1L);
        verify(rentalCarDtoMapper, times(1)).mapToDto(testCar);
    }

    @Test
    void getRentalCarById_ShouldReturnNotFound_WhenCarDoesNotExist() throws Exception {
        when(rentalCarService.getRentalCarById(1L))
                .thenThrow(new NotFoundRentalCarException("Le rental car 1 est introuvable"));

        mockMvc.perform(get("/api/rent-cars-api/rental-cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(rentalCarService, times(1)).getRentalCarById(1L);
        verify(rentalCarDtoMapper, never()).mapToDto(any());
    }

    @Test
    void createRentalCar_ShouldReturnCreatedCar_WhenValidInput() throws Exception {
        RentalCar newCar = new RentalCar();
        newCar.setBrand("Ford");
        newCar.setModel("Focus");
        newCar.setRentAmount(45.0);
        newCar.setSecurityDepositAmount(400.0);
        newCar.setNumberOfSeats(5);
        newCar.setNumberOfDoors(4);
        newCar.setHasAirConditioning(true);

        RentalCar savedCar = new RentalCar();
        savedCar.setId(3L);
        savedCar.setBrand("Ford");
        savedCar.setModel("Focus");
        savedCar.setRentAmount(45.0);
        savedCar.setSecurityDepositAmount(400.0);
        savedCar.setNumberOfSeats(5);
        savedCar.setNumberOfDoors(4);
        savedCar.setHasAirConditioning(true);

        RentalCarResponseDto savedCarDto = new RentalCarResponseDto("Ford", "Focus", 45.0, 400.0, 5, 4, true);

        when(rentalCarService.createRentalCar(any(RentalCar.class))).thenReturn(savedCar);
        when(rentalCarDtoMapper.mapToDto(savedCar)).thenReturn(savedCarDto);

        mockMvc.perform(post("/api/rent-cars-api/rental-cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brand", is("Ford")))
                .andExpect(jsonPath("$.model", is("Focus")))
                .andExpect(jsonPath("$.rentAmount", is(45.0)));

        verify(rentalCarService, times(1)).createRentalCar(any(RentalCar.class));
        verify(rentalCarDtoMapper, times(1)).mapToDto(savedCar);
    }

    @Test
    void createRentalCar_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        RentalCar invalidCar = new RentalCar();
        invalidCar.setBrand(null);
        invalidCar.setModel("Focus");

        mockMvc.perform(post("/api/rent-cars-api/rental-cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCar)))
                .andExpect(status().isBadRequest());

        verify(rentalCarService, never()).createRentalCar(any(RentalCar.class));
        verify(rentalCarDtoMapper, never()).mapToDto(any());
    }

    @Test
    void updateRentalCar_ShouldReturnUpdatedCar_WhenValidInput() throws Exception {
        RentalCar updatedCar = new RentalCar();
        updatedCar.setId(1L);
        updatedCar.setBrand("Honda");
        updatedCar.setModel("Civic");
        updatedCar.setRentAmount(55.0);
        updatedCar.setSecurityDepositAmount(550.0);
        updatedCar.setNumberOfSeats(4);
        updatedCar.setNumberOfDoors(2);
        updatedCar.setHasAirConditioning(false);

        RentalCarResponseDto updatedCarDto = new RentalCarResponseDto("Honda", "Civic", 55.0, 550.0, 4, 2, false);

        when(rentalCarService.updateRentalCar(eq(1L), any(RentalCar.class))).thenReturn(updatedCar);
        when(rentalCarDtoMapper.mapToDto(updatedCar)).thenReturn(updatedCarDto);

        mockMvc.perform(put("/api/rent-cars-api/rental-cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCar)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brand", is("Honda")))
                .andExpect(jsonPath("$.model", is("Civic")))
                .andExpect(jsonPath("$.rentAmount", is(55.0)));

        verify(rentalCarService, times(1)).updateRentalCar(eq(1L), any(RentalCar.class));
        verify(rentalCarDtoMapper, times(1)).mapToDto(updatedCar);
    }

    @Test
    void updateRentalCar_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        RentalCar invalidCar = new RentalCar();
        invalidCar.setBrand(null);

        mockMvc.perform(put("/api/rent-cars-api/rental-cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCar)))
                .andExpect(status().isBadRequest());

        verify(rentalCarService, never()).updateRentalCar(anyLong(), any(RentalCar.class));
        verify(rentalCarDtoMapper, never()).mapToDto(any());
    }

    @Test
    void partialUpdateRentalCar_ShouldReturnUpdatedCar_WhenValidInput() throws Exception {
        RentalCar updatedCar = new RentalCar();
        updatedCar.setId(1L);
        updatedCar.setBrand("Nissan");
        updatedCar.setModel("Corolla");
        updatedCar.setRentAmount(60.0);

        RentalCarResponseDto updatedCarDto = new RentalCarResponseDto("Nissan", "Corolla", 60.0, null, null, null, null);

        when(rentalCarService.partialUpdateRentalCar(eq(1L), any(RentalCarPatchDto.class)))
                .thenReturn(updatedCar);
        when(rentalCarDtoMapper.mapToDto(updatedCar)).thenReturn(updatedCarDto);

        mockMvc.perform(patch("/api/rent-cars-api/rental-cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brand", is("Nissan")))
                .andExpect(jsonPath("$.model", is("Corolla")))
                .andExpect(jsonPath("$.rentAmount", is(60.0)));

        verify(rentalCarService, times(1)).partialUpdateRentalCar(eq(1L), any(RentalCarPatchDto.class));
        verify(rentalCarDtoMapper, times(1)).mapToDto(updatedCar);
    }

    @Test
    void partialUpdateRentalCar_ShouldReturnNotFound_WhenCarDoesNotExist() throws Exception {
        when(rentalCarService.partialUpdateRentalCar(eq(1L), any(RentalCarPatchDto.class)))
                .thenThrow(new NotFoundRentalCarException("Le rental car 1 est introuvable"));

        mockMvc.perform(patch("/api/rent-cars-api/rental-cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
                .andExpect(status().isNotFound());

        verify(rentalCarService, times(1)).partialUpdateRentalCar(eq(1L), any(RentalCarPatchDto.class));
        verify(rentalCarDtoMapper, never()).mapToDto(any());
    }

    @Test
    void partialUpdateRentalCar_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        RentalCarPatchDto invalidDto = new RentalCarPatchDto();
        invalidDto.setBrand("");

        mockMvc.perform(patch("/api/rent-cars-api/rental-cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());

        verify(rentalCarService, never()).partialUpdateRentalCar(anyLong(), any(RentalCarPatchDto.class));
        verify(rentalCarDtoMapper, never()).mapToDto(any());
    }

    @Test
    void deleteRentalCar_ShouldReturnNoContent_WhenCarDeleted() throws Exception {
        doNothing().when(rentalCarService).deleteRentalCar(1L);

        mockMvc.perform(delete("/api/rent-cars-api/rental-cars/1"))
                .andExpect(status().isNoContent());

        verify(rentalCarService, times(1)).deleteRentalCar(1L);
    }
}