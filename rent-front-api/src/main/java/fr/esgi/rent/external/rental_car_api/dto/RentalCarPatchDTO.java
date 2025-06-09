package fr.esgi.rent.external.rental_car_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RentalCarPatchDTO {

    public String brand;
    public String model;

    @NotNull(message = "Rent Amount is required")
    @Min(value = 1, message = "Rent Amount must be more than 1")
    public double rentAmount;

    public double securityDepositAmount;
    public int numberOfSeats;
    public int numberOfDoors;
    public boolean hasAirConditioning;
}
