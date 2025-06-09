package fr.esgi.rent.external.rental_property_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RentalPropertyUpdateDTO {
    @NotBlank(message = "Description must not be blank")
    public String description;

    @NotBlank(message = "Town must not be blank")
    public String town;

    @NotBlank(message = "Address must not be blank")
    public String address;

    @NotBlank(message = "Property Type must not be blank")
    public String propertyType;

    @NotNull(message = "Rent Amount is required")
    @Min(value = 1, message = "Rent Amount must be more than 1")
    public Double rentAmount;

    @NotNull(message = "Security Deposit Amount is required")
    @Min(value = 1, message = "Security Deposit Amount must be more than 1")
    public Double securityDepositAmount;

    @NotNull(message = "Area is required")
    @Min(value = 9, message = "Area must be more than 9m2")
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
