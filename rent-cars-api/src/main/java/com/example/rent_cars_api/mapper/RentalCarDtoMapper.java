package com.example.rent_cars_api.mapper;

import com.example.rent_cars_api.dto.response.RentalCarResponseDto;
import com.example.rent_cars_api.model.RentalCar;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalCarDtoMapper {

    public List<RentalCarResponseDto> mapToDtoList(List<RentalCar> rentalCars) {
        return rentalCars.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public RentalCarResponseDto mapToDto(RentalCar rentalCar) {
        return new RentalCarResponseDto(
                rentalCar.getBrand(),
                rentalCar.getModel(),
                rentalCar.getRentAmount(),
                rentalCar.getSecurityDepositAmount(),
                rentalCar.getNumberOfSeats(),
                rentalCar.getNumberOfDoors(),
                rentalCar.getHasAirConditioning()
        );
    }

}
