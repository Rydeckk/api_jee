package fr.esgi.rent.properties.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class CreateRentalPropertyDTO {
    @NonNull
    private String description;

    @NonNull
    private String town;

    @NonNull
    private String address;

    @NonNull
    private String propertyType;

    @NonNull
    private Double rentAmount;

    @NonNull
    private Double securityDepositAmount;

    @NonNull
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