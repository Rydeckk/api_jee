package com.example.rent_cars_api.controller;

import com.example.rent_cars_api.dto.request.RentalCarPatchDto;
import com.example.rent_cars_api.dto.response.RentalCarResponseDto;
import com.example.rent_cars_api.mapper.RentalCarDtoMapper;
import com.example.rent_cars_api.model.RentalCar;
import com.example.rent_cars_api.service.RentalCarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("api/rent-cars-api/rental-cars")
public class RentalCarController {

    private final RentalCarService rentalCarService;
    private final RentalCarDtoMapper rentalCarDtoMapper;

    public RentalCarController(RentalCarService rentalCarService, RentalCarDtoMapper rentalCarDtoMapper) {
        this.rentalCarService = rentalCarService;
        this.rentalCarDtoMapper = rentalCarDtoMapper;
    }

    @GetMapping
    public List<RentalCarResponseDto> getRentalCars() {
        return rentalCarDtoMapper.mapToDtoList(rentalCarService.getRentalCars());
    }

    @GetMapping("/{id}")
    public RentalCarResponseDto getRentalCarById(@PathVariable Long id) {
        return rentalCarDtoMapper.mapToDto(rentalCarService.getRentalCarById(id));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public RentalCarResponseDto createRentalCar(@Valid @RequestBody RentalCar rentalCar) {
        return rentalCarDtoMapper.mapToDto(rentalCarService.createRentalCar(rentalCar));
    }

    @PutMapping("/{id}")
    public RentalCarResponseDto updateRentalCar(@PathVariable Long id, @Valid @RequestBody RentalCar updatedCar) {
        return rentalCarDtoMapper.mapToDto(rentalCarService.updateRentalCar(id, updatedCar));
    }

    @PatchMapping("/{id}")
    public RentalCarResponseDto partialUpdateRentalCar(@PathVariable Long id, @Valid @RequestBody RentalCarPatchDto updatedCar) {
        return rentalCarDtoMapper.mapToDto(rentalCarService.partialUpdateRentalCar(id, updatedCar));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteRentalCar(@PathVariable Long id) {
        rentalCarService.deleteRentalCar(id);
    }
}
