package fr.esgi.rent.properties.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRentalPropertyDTO {
    @NotBlank
    private String description;

    @NotBlank
    private String town;

    @NotBlank
    private String address;

    @NotBlank
    private String propertyType;

    @NotNull
    @Positive
    private Double rentAmount;

    @NotNull
    @Positive
    private Double securityDepositAmount;

    @NotNull
    @Positive
    private Double area;

    private Integer numberOfBedrooms;

    private Integer floorNumber;

    private Integer numberOfFloors;

    private String constructionYear;

    private String energyClassification;

    private Boolean hasElevator;

    private Boolean hasIntercom;

    private Boolean hasBalcony;

    private Boolean hasParkingSpace;
}