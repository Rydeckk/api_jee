package com.example.rent_cars_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("rental_car")
public class RentalCar {

    @Id
    private Long id;

    @NotNull
    @Column("brand")
    private String brand;

    @NotNull
    @Column("model")
    private String model;

    @NotNull
    @Column("rent_amount")
    private Double rentAmount;

    @NotNull
    @Column("security_deposit_amount")
    private Double securityDepositAmount;

    @NotNull
    @Column("number_of_seats")
    private Integer numberOfSeats;

    @NotNull
    @Column("number_of_doors")
    private Integer numberOfDoors;

    @NotNull
    @Column("has_air_conditioning")
    private Boolean hasAirConditioning;

}
