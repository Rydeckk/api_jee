package fr.esgi.rent.properties.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("rental_property")
public class RentalProperty {
    @Id
    private Long id;

    @Column("description")
    private String description;

    @Column("town")
    private String town;

    @Column("address")
    private String address;

    @Column("property_type_id")
    private Long propertyTypeId;

    @Column("rent_amount")
    private Double rentAmount;

    @Column("security_deposit_amount")
    private Double securityDepositAmount;

    @Column("area")
    private Double area;

    @Column("number_of_bedrooms")
    private Integer numberOfBedrooms;

    @Column("floor_number")
    private Integer floorNumber;

    @Column("number_of_floors")
    private Integer numberOfFloors;

    @Column("construction_year")
    private String constructionYear;

    @Column("energy_classification_id")
    private Long energyClassificationId;

    @Column("has_elevator")
    private Boolean hasElevator;

    @Column("has_intercom")
    private Boolean hasIntercom;

    @Column("has_balcony")
    private Boolean hasBalcony;

    @Column("has_parking_space")
    private Boolean hasParkingSpace;
}