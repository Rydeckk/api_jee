package com.example.rent_cars_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RentalCarPatchDto {
    private String brand;
    private String model;
    @NotNull
    private Double rentAmount;
    private Double securityDepositAmount;
    private Integer numberOfSeats;
    private Integer numberOfDoors;
    private Boolean hasAirConditioning;
}
