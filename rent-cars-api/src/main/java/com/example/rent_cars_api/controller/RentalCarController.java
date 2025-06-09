package com.example.rent_cars_api.controller;

import com.example.rent_cars_api.dto.RentalCarPatchDto;
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

    public RentalCarController(RentalCarService rentalCarService) {
        this.rentalCarService = rentalCarService;
    }

    @GetMapping
    public List<RentalCar> getRentalCars() {
        return rentalCarService.getRentalCars();
    }

    @GetMapping("/{id}")
    public RentalCar getRentalCarById(@PathVariable Long id) {
        return rentalCarService.getRentalCarById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public RentalCar createRentalCar(@Valid @RequestBody RentalCar rentalCar) {
        return rentalCarService.createRentalCar(rentalCar);
    }

    @PutMapping("/{id}")
    public RentalCar updateRentalCar(@PathVariable Long id, @Valid @RequestBody RentalCar updatedCar) {
        return rentalCarService.updateRentalCar(id, updatedCar);
    }

    @PatchMapping("/{id}")
    public RentalCar partialUpdateRentalCar(@PathVariable Long id, @Valid @RequestBody RentalCarPatchDto updatedCar) {
        return rentalCarService.partialUpdateRentalCar(id, updatedCar);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteRentalCar(@PathVariable Long id) {
        rentalCarService.deleteRentalCar(id);
    }
}
