package com.example.rent_cars_api.mapper;

import com.example.rent_cars_api.dto.response.RentalCarResponseDto;
import com.example.rent_cars_api.model.RentalCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RentalCarDtoMapperTest {

    @InjectMocks
    private RentalCarDtoMapper mapper;

    private RentalCar rentalCar;

    @BeforeEach
    void setUp() {
        rentalCar = new RentalCar();
        rentalCar.setBrand("Toyota");
        rentalCar.setModel("Corolla");
        rentalCar.setRentAmount(50.00);
        rentalCar.setSecurityDepositAmount(200.00);
        rentalCar.setNumberOfSeats(5);
        rentalCar.setNumberOfDoors(4);
        rentalCar.setHasAirConditioning(true);
    }

    @Test
    void mapToDto_shouldMapAllFields() {
        RentalCarResponseDto result = mapper.mapToDto(rentalCar);

        assertThat(result.brand()).isEqualTo("Toyota");
        assertThat(result.model()).isEqualTo("Corolla");
        assertThat(result.rentAmount()).isEqualTo(50.00);
        assertThat(result.securityDepositAmount()).isEqualTo(200.00);
        assertThat(result.numberOfSeats()).isEqualTo(5);
        assertThat(result.numberOfDoors()).isEqualTo(4);
        assertThat(result.hasAirConditioning()).isTrue();
    }

    @Test
    void mapToDtoList_shouldMapAllElements() {
        RentalCar secondCar = new RentalCar();
        secondCar.setBrand("Honda");
        secondCar.setModel("Civic");
        secondCar.setRentAmount(45.00);
        secondCar.setSecurityDepositAmount(180.00);
        secondCar.setNumberOfSeats(5);
        secondCar.setNumberOfDoors(4);
        secondCar.setHasAirConditioning(false);

        List<RentalCar> rentalCars = Arrays.asList(rentalCar, secondCar);

        List<RentalCarResponseDto> result = mapper.mapToDtoList(rentalCars);

        assertThat(result).hasSize(2);

        assertThat(result.getFirst().brand()).isEqualTo("Toyota");
        assertThat(result.get(0).model()).isEqualTo("Corolla");
        assertThat(result.get(0).hasAirConditioning()).isTrue();

        assertThat(result.get(1).brand()).isEqualTo("Honda");
        assertThat(result.get(1).model()).isEqualTo("Civic");
        assertThat(result.get(1).hasAirConditioning()).isFalse();
    }

    @Test
    void mapToDtoList_withEmptyList_shouldReturnEmptyList() {
        List<RentalCar> emptyList = List.of();

        List<RentalCarResponseDto> result = mapper.mapToDtoList(emptyList);

        assertThat(result).isEmpty();
    }
}
