package fr.esgi.rent.external.rental_property_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RentalPropertyPatchDTO {
    public String description;
    public String town;
    public String address;
    public String propertyType;

    @NotNull(message = "Rent Amount is required")
    @Min(value = 1, message = "Rent Amount must be more than 1")
    public Double rentAmount;

    public Double securityDepositAmount;
    public Double area;
    public Integer numberOfBedrooms;
    public Integer floorNumber;
    public Integer numberOfFloors;
    public Integer constructionYear;
    public String energyClassification;
    public boolean hasElevator;
    public boolean hasIntercom;
    public boolean hasBalcony;
    public boolean hasParkingSpace;
}
