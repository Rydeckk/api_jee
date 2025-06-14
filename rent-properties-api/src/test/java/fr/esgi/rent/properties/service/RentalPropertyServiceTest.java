package fr.esgi.rent.properties.service;

import fr.esgi.rent.properties.dto.CreateRentalPropertyDTO;
import fr.esgi.rent.properties.dto.RentalPropertyDTO;
import fr.esgi.rent.properties.dto.UpdateRentalPropertyDTO;
import fr.esgi.rent.properties.entity.EnergyClassification;
import fr.esgi.rent.properties.entity.PropertyType;
import fr.esgi.rent.properties.entity.RentalProperty;
import fr.esgi.rent.properties.exception.NotFoundRentalPropertyException;
import fr.esgi.rent.properties.repository.EnergyClassificationRepository;
import fr.esgi.rent.properties.repository.PropertyTypeRepository;
import fr.esgi.rent.properties.repository.RentalPropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalPropertyServiceTest {

    @Mock
    private RentalPropertyRepository rentalPropertyRepository;

    @Mock
    private PropertyTypeRepository propertyTypeRepository;

    @Mock
    private EnergyClassificationRepository energyClassificationRepository;

    @InjectMocks
    private RentalPropertyService rentalPropertyService;

    private RentalProperty testProperty;
    private PropertyType testPropertyType;
    private EnergyClassification testEnergyClassification;

    @BeforeEach
    void setUp() {
        testProperty = new RentalProperty();
        testProperty.setId(1L);
        testProperty.setDescription("Belle propriété");
        testProperty.setTown("Paris");
        testProperty.setAddress("123 Rue de la Paix");
        testProperty.setRentAmount(1200.0);
        testProperty.setSecurityDepositAmount(2400.0);
        testProperty.setArea(75.5);
        testProperty.setPropertyTypeId(1L);
        testProperty.setEnergyClassificationId(1L);

        testPropertyType = new PropertyType();
        testPropertyType.setId(1L);
        testPropertyType.setDesignation("Appartement");

        testEnergyClassification = new EnergyClassification();
        testEnergyClassification.setId(1L);
        testEnergyClassification.setDesignation("B");
    }

    @Test
    void getAllRentalProperties_ShouldReturnListOfProperties() {
        // Given
        List<RentalProperty> properties = Collections.singletonList(testProperty);
        when(rentalPropertyRepository.findAll()).thenReturn(properties);
        when(propertyTypeRepository.findById(1L)).thenReturn(Optional.of(testPropertyType));

        // When
        List<RentalPropertyDTO> result = rentalPropertyService.getAllRentalProperties();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Paris", result.getFirst().getTown());
        assertEquals("Appartement", result.getFirst().getPropertyType());
        verify(rentalPropertyRepository).findAll();
    }

    @Test
    void getRentalPropertyById_ShouldReturnProperty_WhenExists() {
        // Given
        when(rentalPropertyRepository.findById(1L)).thenReturn(Optional.of(testProperty));
        when(propertyTypeRepository.findById(1L)).thenReturn(Optional.of(testPropertyType));

        // When
        RentalPropertyDTO result = rentalPropertyService.getRentalPropertyById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Paris", result.getTown());
        assertEquals("Appartement", result.getPropertyType());
        verify(rentalPropertyRepository).findById(1L);
    }

    @Test
    void getRentalPropertyById_ShouldThrowException_WhenNotExists() {
        // Given
        when(rentalPropertyRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundRentalPropertyException.class, () -> rentalPropertyService.getRentalPropertyById(1L));
        verify(rentalPropertyRepository).findById(1L);
    }

    @Test
    void createRentalProperty_ShouldReturnCreatedProperty() {
        // Given
        CreateRentalPropertyDTO createDto = createRentalPropertyCreateDTO();
        when(propertyTypeRepository.findByDesignation("Appartement")).thenReturn(Optional.of(testPropertyType));
        when(energyClassificationRepository.findByDesignation("B")).thenReturn(Optional.of(testEnergyClassification));
        when(rentalPropertyRepository.save(any(RentalProperty.class))).thenReturn(testProperty);
        when(propertyTypeRepository.findById(1L)).thenReturn(Optional.of(testPropertyType));

        // When
        RentalPropertyDTO result = rentalPropertyService.createRentalProperty(createDto);

        // Then
        assertNotNull(result);
        assertEquals("Paris", result.getTown());
        assertEquals("Appartement", result.getPropertyType());
        verify(rentalPropertyRepository).save(any(RentalProperty.class));
    }

    @Test
    void createRentalProperty_ShouldCreateNewPropertyType_WhenNotExists() {
        // Given
        CreateRentalPropertyDTO createDto = createRentalPropertyCreateDTO();
        createDto.setPropertyType("Villa");
        
        PropertyType newPropertyType = new PropertyType();
        newPropertyType.setId(2L);
        newPropertyType.setDesignation("Villa");
        
        when(propertyTypeRepository.findByDesignation("Villa")).thenReturn(Optional.empty());
        when(propertyTypeRepository.save(any(PropertyType.class))).thenReturn(newPropertyType);
        when(energyClassificationRepository.findByDesignation("B")).thenReturn(Optional.of(testEnergyClassification));
        when(rentalPropertyRepository.save(any(RentalProperty.class))).thenReturn(testProperty);
        when(propertyTypeRepository.findById(any())).thenReturn(Optional.of(newPropertyType));

        // When
        RentalPropertyDTO result = rentalPropertyService.createRentalProperty(createDto);

        // Then
        assertNotNull(result);
        verify(propertyTypeRepository).save(any(PropertyType.class));
    }

    @Test
    void updateRentalProperty_ShouldReturnUpdatedProperty_WhenExists() {
        // Given
        CreateRentalPropertyDTO updateDto = createRentalPropertyCreateDTO();
        updateDto.setTown("Lyon");
        
        when(rentalPropertyRepository.existsById(1L)).thenReturn(true);
        when(rentalPropertyRepository.findById(1L)).thenReturn(Optional.of(testProperty));
        when(propertyTypeRepository.findByDesignation("Appartement")).thenReturn(Optional.of(testPropertyType));
        when(energyClassificationRepository.findByDesignation("B")).thenReturn(Optional.of(testEnergyClassification));
        when(rentalPropertyRepository.save(any(RentalProperty.class))).thenReturn(testProperty);
        when(propertyTypeRepository.findById(1L)).thenReturn(Optional.of(testPropertyType));

        // When
        RentalPropertyDTO result = rentalPropertyService.updateRentalProperty(1L, updateDto);

        // Then
        assertNotNull(result);
        verify(rentalPropertyRepository).save(any(RentalProperty.class));
    }

    @Test
    void updateRentalProperty_ShouldCreateNewProperty_WhenNotExists() {
        // Given
        CreateRentalPropertyDTO updateDto = createRentalPropertyCreateDTO();
        
        when(rentalPropertyRepository.existsById(1L)).thenReturn(false);
        when(propertyTypeRepository.findByDesignation("Appartement")).thenReturn(Optional.of(testPropertyType));
        when(energyClassificationRepository.findByDesignation("B")).thenReturn(Optional.of(testEnergyClassification));
        when(rentalPropertyRepository.save(any(RentalProperty.class))).thenReturn(testProperty);
        when(propertyTypeRepository.findById(1L)).thenReturn(Optional.of(testPropertyType));

        // When
        RentalPropertyDTO result = rentalPropertyService.updateRentalProperty(1L, updateDto);

        // Then
        assertNotNull(result);
        verify(rentalPropertyRepository).save(any(RentalProperty.class));
    }

    @Test
    void partialUpdateRentalProperty_ShouldReturnUpdatedProperty() {
        // Given
        UpdateRentalPropertyDTO patchDto = new UpdateRentalPropertyDTO();
        patchDto.setTown("Marseille");
        patchDto.setRentAmount(1500.0);
        
        when(rentalPropertyRepository.findById(1L)).thenReturn(Optional.of(testProperty));
        when(rentalPropertyRepository.save(any(RentalProperty.class))).thenReturn(testProperty);
        when(propertyTypeRepository.findById(1L)).thenReturn(Optional.of(testPropertyType));

        // When
        RentalPropertyDTO result = rentalPropertyService.partialUpdateRentalProperty(1L, patchDto);

        // Then
        assertNotNull(result);
        verify(rentalPropertyRepository).save(any(RentalProperty.class));
    }

    @Test
    void partialUpdateRentalProperty_ShouldThrowException_WhenNotExists() {
        // Given
        UpdateRentalPropertyDTO patchDto = new UpdateRentalPropertyDTO();
        when(rentalPropertyRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundRentalPropertyException.class, () -> rentalPropertyService.partialUpdateRentalProperty(1L, patchDto));
    }

    @Test
    void deleteRentalProperty_ShouldDeleteProperty_WhenExists() {
        // Given
        when(rentalPropertyRepository.existsById(1L)).thenReturn(true);

        // When
        rentalPropertyService.deleteRentalProperty(1L);

        // Then
        verify(rentalPropertyRepository).deleteById(1L);
    }

    @Test
    void deleteRentalProperty_ShouldThrowException_WhenNotExists() {
        // Given
        when(rentalPropertyRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(NotFoundRentalPropertyException.class, () -> rentalPropertyService.deleteRentalProperty(1L));
        verify(rentalPropertyRepository, never()).deleteById(anyLong());
    }

    private CreateRentalPropertyDTO createRentalPropertyCreateDTO() {
        CreateRentalPropertyDTO dto = new CreateRentalPropertyDTO();
        dto.setDescription("Belle propriété avec vue");
        dto.setTown("Paris");
        dto.setAddress("123 Rue de la Paix");
        dto.setPropertyType("Appartement");
        dto.setRentAmount(1200.0);
        dto.setSecurityDepositAmount(2400.0);
        dto.setArea(75.5);
        dto.setNumberOfBedrooms(2);
        dto.setFloorNumber(3);
        dto.setNumberOfFloors(5);
        dto.setConstructionYear("2010");
        dto.setEnergyClassification("B");
        dto.setHasElevator(true);
        dto.setHasIntercom(true);
        dto.setHasBalcony(true);
        dto.setHasParkingSpace(false);
        return dto;
    }
}