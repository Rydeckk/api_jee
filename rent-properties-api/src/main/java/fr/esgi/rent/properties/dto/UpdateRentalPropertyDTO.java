package fr.esgi.rent.properties.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRentalPropertyDTO {
    private String description;

    private String town;

    private String address;

    private String propertyType;

    @Positive
    private Double rentAmount;

    @Positive
    private Double securityDepositAmount;

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