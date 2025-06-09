package com.example.rent_cars_api.service;

import com.example.rent_cars_api.dto.RentalCarPatchDto;
import com.example.rent_cars_api.exception.NotFoundRentalCarException;
import com.example.rent_cars_api.model.RentalCar;
import com.example.rent_cars_api.repository.RentalCarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalCarService {
    private final RentalCarRepository rentalCarRepository;

    public RentalCarService(RentalCarRepository rentalCarRepository) {
        this.rentalCarRepository = rentalCarRepository;
    }

    public List<RentalCar> getRentalCars() {
        List<RentalCar> rentalCars = new ArrayList<>();
        rentalCarRepository.findAll().forEach(rentalCars::add);
        return rentalCars;
    }

    public RentalCar getRentalCarById(Long id) {
        return rentalCarRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundRentalCarException("Le rental car " + id + " est introuvable")
                );
    }

    public RentalCar createRentalCar(RentalCar rentalCar) {
        return rentalCarRepository.save(rentalCar);
    }

    public RentalCar updateRentalCar(Long id, RentalCar updatedCar) {
        if (rentalCarRepository.existsById(id)) {
            updatedCar.setId(id);
        }
        return rentalCarRepository.save(updatedCar);
    }

    public RentalCar partialUpdateRentalCar(Long id, RentalCarPatchDto updatedCar) {
        RentalCar existingCar = rentalCarRepository.findById(id)
                .orElseThrow(() -> new NotFoundRentalCarException("Le rental car " + id + " est introuvable"));
        if (updatedCar.getBrand() != null) {
            existingCar.setBrand(updatedCar.getBrand());
        }
        if (updatedCar.getModel() != null) {
            existingCar.setModel(updatedCar.getModel());
        }
        existingCar.setRentAmount(updatedCar.getRentAmount());
        if (updatedCar.getSecurityDepositAmount() != null) {
            existingCar.setSecurityDepositAmount(updatedCar.getSecurityDepositAmount());
        }
        if (updatedCar.getNumberOfSeats() != null) {
            existingCar.setNumberOfSeats(updatedCar.getNumberOfSeats());
        }
        if (updatedCar.getNumberOfDoors() != null) {
            existingCar.setNumberOfDoors(updatedCar.getNumberOfDoors());
        }
        if (updatedCar.getHasAirConditioning() != null) {
            existingCar.setHasAirConditioning(updatedCar.getHasAirConditioning());
        }
        return rentalCarRepository.save(existingCar);
    }

    public void deleteRentalCar(Long id) {
        rentalCarRepository.deleteById(id);
    }
}
