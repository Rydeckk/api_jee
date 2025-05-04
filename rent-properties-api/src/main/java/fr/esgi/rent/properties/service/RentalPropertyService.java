package fr.esgi.rent.properties.service;

import fr.esgi.rent.properties.dto.CreateRentalPropertyDTO;
import fr.esgi.rent.properties.dto.RentalPropertyDTO;
import fr.esgi.rent.properties.dto.UpdateRentalPropertyDTO;
import fr.esgi.rent.properties.entity.EnergyClassification;
import fr.esgi.rent.properties.entity.PropertyType;
import fr.esgi.rent.properties.entity.RentalProperty;
import fr.esgi.rent.properties.exception.ResourceNotFoundException;
import fr.esgi.rent.properties.repository.EnergyClassificationRepository;
import fr.esgi.rent.properties.repository.PropertyTypeRepository;
import fr.esgi.rent.properties.repository.RentalPropertyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalPropertyService {

    private final RentalPropertyRepository rentalPropertyRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final EnergyClassificationRepository energyClassificationRepository;

    @Autowired
    public RentalPropertyService(RentalPropertyRepository rentalPropertyRepository,
                                 PropertyTypeRepository propertyTypeRepository,
                                 EnergyClassificationRepository energyClassificationRepository) {
        this.rentalPropertyRepository = rentalPropertyRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.energyClassificationRepository = energyClassificationRepository;
    }

    public List<RentalPropertyDTO> getAllRentalProperties() {
        List<RentalPropertyDTO> rentalPropertyDTOs = new ArrayList<>();
        Iterable<RentalProperty> properties = rentalPropertyRepository.findAll();

        for (RentalProperty property : properties) {
            RentalPropertyDTO dto = convertToDTO(property);
            rentalPropertyDTOs.add(dto);
        }

        return rentalPropertyDTOs;
    }

    public RentalPropertyDTO getRentalPropertyById(Long id) {
        RentalProperty property = rentalPropertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RentalProperty", id));

        return convertToDTO(property);
    }

    @Transactional
    public void createRentalProperty(CreateRentalPropertyDTO dto) {
        RentalProperty property = new RentalProperty();
        property.setDescription(dto.getDescription());
        property.setTown(dto.getTown());
        property.setAddress(dto.getAddress());
        property.setRentAmount(dto.getRentAmount());
        property.setSecurityDepositAmount(dto.getSecurityDepositAmount());
        property.setArea(dto.getArea());
        property.setNumberOfBedrooms(dto.getNumberOfBedrooms());
        property.setFloorNumber(dto.getFloorNumber());
        property.setNumberOfFloors(dto.getNumberOfFloors());
        property.setConstructionYear(dto.getConstructionYear());
        property.setHasElevator(dto.getHasElevator());
        property.setHasIntercom(dto.getHasIntercom());
        property.setHasBalcony(dto.getHasBalcony());
        property.setHasParkingSpace(dto.getHasParkingSpace());

        // Property Type
        PropertyType propertyType = getOrCreatePropertyType(dto.getPropertyType());
        property.setPropertyTypeId(propertyType.getId());

        // Energy Classification
        if (dto.getEnergyClassification() != null) {
            EnergyClassification energyClassification = getOrCreateEnergyClassification(dto.getEnergyClassification());
            property.setEnergyClassificationId(energyClassification.getId());
        }

        rentalPropertyRepository.save(property);
    }

    @Transactional
    public void updateRentalProperty(Long id, CreateRentalPropertyDTO dto) {
        RentalProperty property;

        if (rentalPropertyRepository.existsById(id)) {
            property = rentalPropertyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("RentalProperty", id));
        } else {
            property = new RentalProperty();
            property.setId(id);
        }

        property.setDescription(dto.getDescription());
        property.setTown(dto.getTown());
        property.setAddress(dto.getAddress());
        property.setRentAmount(dto.getRentAmount());
        property.setSecurityDepositAmount(dto.getSecurityDepositAmount());
        property.setArea(dto.getArea());
        property.setNumberOfBedrooms(dto.getNumberOfBedrooms());
        property.setFloorNumber(dto.getFloorNumber());
        property.setNumberOfFloors(dto.getNumberOfFloors());
        property.setConstructionYear(dto.getConstructionYear());
        property.setHasElevator(dto.getHasElevator());
        property.setHasIntercom(dto.getHasIntercom());
        property.setHasBalcony(dto.getHasBalcony());
        property.setHasParkingSpace(dto.getHasParkingSpace());

        // Property Type
        PropertyType propertyType = getOrCreatePropertyType(dto.getPropertyType());
        property.setPropertyTypeId(propertyType.getId());

        // Energy Classification
        if (dto.getEnergyClassification() != null) {
            EnergyClassification energyClassification = getOrCreateEnergyClassification(dto.getEnergyClassification());
            property.setEnergyClassificationId(energyClassification.getId());
        }

        rentalPropertyRepository.save(property);
    }

    @Transactional
    public void partialUpdateRentalProperty(Long id, UpdateRentalPropertyDTO dto) {
        RentalProperty property = rentalPropertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RentalProperty", id));

        if (dto.getDescription() != null) {
            property.setDescription(dto.getDescription());
        }

        if (dto.getTown() != null) {
            property.setTown(dto.getTown());
        }

        if (dto.getAddress() != null) {
            property.setAddress(dto.getAddress());
        }

        if (dto.getRentAmount() != null) {
            property.setRentAmount(dto.getRentAmount());
        }

        if (dto.getSecurityDepositAmount() != null) {
            property.setSecurityDepositAmount(dto.getSecurityDepositAmount());
        }

        if (dto.getArea() != null) {
            property.setArea(dto.getArea());
        }

        if (dto.getNumberOfBedrooms() != null) {
            property.setNumberOfBedrooms(dto.getNumberOfBedrooms());
        }

        if (dto.getFloorNumber() != null) {
            property.setFloorNumber(dto.getFloorNumber());
        }

        if (dto.getNumberOfFloors() != null) {
            property.setNumberOfFloors(dto.getNumberOfFloors());
        }

        if (dto.getConstructionYear() != null) {
            property.setConstructionYear(dto.getConstructionYear());
        }

        if (dto.getHasElevator() != null) {
            property.setHasElevator(dto.getHasElevator());
        }

        if (dto.getHasIntercom() != null) {
            property.setHasIntercom(dto.getHasIntercom());
        }

        if (dto.getHasBalcony() != null) {
            property.setHasBalcony(dto.getHasBalcony());
        }

        if (dto.getHasParkingSpace() != null) {
            property.setHasParkingSpace(dto.getHasParkingSpace());
        }

        // Property Type
        if (dto.getPropertyType() != null) {
            PropertyType propertyType = getOrCreatePropertyType(dto.getPropertyType());
            property.setPropertyTypeId(propertyType.getId());
        }

        // Energy Classification
        if (dto.getEnergyClassification() != null) {
            EnergyClassification energyClassification = getOrCreateEnergyClassification(dto.getEnergyClassification());
            property.setEnergyClassificationId(energyClassification.getId());
        }

        rentalPropertyRepository.save(property);
    }

    public void deleteRentalProperty(Long id) {
        if (!rentalPropertyRepository.existsById(id)) {
            throw new ResourceNotFoundException("RentalProperty", id);
        }

        rentalPropertyRepository.deleteById(id);
    }

    private RentalPropertyDTO convertToDTO(RentalProperty property) {
        RentalPropertyDTO dto = new RentalPropertyDTO();
        dto.setDescription(property.getDescription());
        dto.setTown(property.getTown());
        dto.setAddress(property.getAddress());
        dto.setRentAmount(property.getRentAmount());
        dto.setSecurityDepositAmount(property.getSecurityDepositAmount());
        dto.setArea(property.getArea());

        // Property Type
        if (property.getPropertyTypeId() != null) {
            Optional<PropertyType> propertyType = propertyTypeRepository.findById(property.getPropertyTypeId());
            propertyType.ifPresent(type -> dto.setPropertyType(type.getDesignation()));
        }

        return dto;
    }

    private PropertyType getOrCreatePropertyType(String designation) {
        Optional<PropertyType> propertyType = propertyTypeRepository.findByDesignation(designation);

        if (propertyType.isPresent()) {
            return propertyType.get();
        } else {
            PropertyType newPropertyType = new PropertyType();
            newPropertyType.setDesignation(designation);
            return propertyTypeRepository.save(newPropertyType);
        }
    }

    private EnergyClassification getOrCreateEnergyClassification(String designation) {
        Optional<EnergyClassification> energyClassification = energyClassificationRepository.findByDesignation(designation);

        if (energyClassification.isPresent()) {
            return energyClassification.get();
        } else {
            EnergyClassification newEnergyClassification = new EnergyClassification();
            newEnergyClassification.setDesignation(designation);
            return energyClassificationRepository.save(newEnergyClassification);
        }
    }
}