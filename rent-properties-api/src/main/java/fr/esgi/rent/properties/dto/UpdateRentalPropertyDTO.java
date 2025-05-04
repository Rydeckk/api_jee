package fr.esgi.rent.properties.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class UpdateRentalPropertyDTO {
    @Nullable
    private String description;

    @Nullable
    private String town;

    @Nullable
    private String address;

    @Nullable
    private String propertyType;

    @Nullable
    private Double rentAmount;

    @Nullable
    private Double securityDepositAmount;

    @Nullable
    private Double area;

    @Nullable
    private Integer numberOfBedrooms;

    @Nullable
    private Integer floorNumber;

    @Nullable
    private Integer numberOfFloors;

    @Nullable
    private String constructionYear;

    @Nullable
    private String energyClassification;

    @Nullable
    private Boolean hasElevator;

    @Nullable
    private Boolean hasIntercom;

    @Nullable
    private Boolean hasBalcony;

    @Nullable
    private Boolean hasParkingSpace;
}