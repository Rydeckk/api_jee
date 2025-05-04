package fr.esgi.rent.properties.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalPropertyDTO {
    private String description;
    private String town;
    private String address;
    private String propertyType;
    private Double rentAmount;
    private Double securityDepositAmount;
    private Double area;
}