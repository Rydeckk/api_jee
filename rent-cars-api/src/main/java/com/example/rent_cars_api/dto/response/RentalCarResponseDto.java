package com.example.rent_cars_api.dto.response;

public record RentalCarResponseDto (
         String brand,
         String model,
         Double rentAmount,
         Double securityDepositAmount,
         Integer numberOfSeats,
         Integer numberOfDoors,
         Boolean hasAirConditioning
){
}
