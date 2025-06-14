package fr.esgi.rent.properties.controller;

import fr.esgi.rent.properties.dto.CreateRentalPropertyDTO;
import fr.esgi.rent.properties.dto.RentalPropertyDTO;
import fr.esgi.rent.properties.dto.UpdateRentalPropertyDTO;
import fr.esgi.rent.properties.service.RentalPropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentalPropertyController.class)
class RentalPropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RentalPropertyService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_all_rental_properties() throws Exception {
        // Given
        List<RentalPropertyDTO> properties = Arrays.asList(
                createRentalPropertyDTO("Paris", "Appartement"),
                createRentalPropertyDTO("Lyon", "Studio")
        );
        when(service.getAllRentalProperties()).thenReturn(properties);

        // When & Then
        mockMvc.perform(get("/rental-properties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].town").value("Paris"))
                .andExpect(jsonPath("$[0].propertyType").value("Appartement"))
                .andExpect(jsonPath("$[1].town").value("Lyon"))
                .andExpect(jsonPath("$[1].propertyType").value("Studio"));
    }

    @Test
    void should_return_rental_property_by_id() throws Exception {
        // Given
        RentalPropertyDTO property = createRentalPropertyDTO("Nice", "Maison");
        when(service.getRentalPropertyById(1L)).thenReturn(property);

        // When & Then
        mockMvc.perform(get("/rental-properties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.town").value("Nice"))
                .andExpect(jsonPath("$.propertyType").value("Maison"));
    }

    @Test
    void should_create_rental_property() throws Exception {
        // Given
        CreateRentalPropertyDTO createDto = createRentalPropertyCreateDTO("Marseille", "Loft");
        RentalPropertyDTO expectedResponse = createRentalPropertyDTO("Marseille", "Loft");
        when(service.createRentalProperty(any(CreateRentalPropertyDTO.class))).thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.town").value("Marseille"))
                .andExpect(jsonPath("$.propertyType").value("Loft"));
    }

    @Test
    void should_update_rental_property() throws Exception {
        // Given
        CreateRentalPropertyDTO updateDto = createRentalPropertyCreateDTO("Bordeaux", "Villa");
        RentalPropertyDTO expectedResponse = createRentalPropertyDTO("Bordeaux", "Villa");
        when(service.updateRentalProperty(anyLong(), any(CreateRentalPropertyDTO.class))).thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(put("/rental-properties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.town").value("Bordeaux"))
                .andExpect(jsonPath("$.propertyType").value("Villa"));
    }

    @Test
    void should_partial_update_rental_property() throws Exception {
        // Given
        UpdateRentalPropertyDTO patchDto = new UpdateRentalPropertyDTO();
        patchDto.setRentAmount(1500.0);
        patchDto.setTown("Toulouse");
        RentalPropertyDTO expectedResponse = createRentalPropertyDTO("Toulouse", "Appartement");
        expectedResponse.setRentAmount(1500.0);
        when(service.partialUpdateRentalProperty(anyLong(), any(UpdateRentalPropertyDTO.class))).thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(patch("/rental-properties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.town").value("Toulouse"))
                .andExpect(jsonPath("$.rentAmount").value(1500.0));
    }

    @Test
    void should_delete_rental_property() throws Exception {
        // When & Then
        mockMvc.perform(delete("/rental-properties/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_validation_error_for_invalid_rental_property() throws Exception {
        // Given
        CreateRentalPropertyDTO invalidDto = new CreateRentalPropertyDTO();
        // Not setting required fields

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_validation_error_for_blank_description() throws Exception {
        // Given
        CreateRentalPropertyDTO invalidDto = createRentalPropertyCreateDTO("Paris", "Appartement");
        invalidDto.setDescription("");

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_validation_error_for_negative_rent_amount() throws Exception {
        // Given
        CreateRentalPropertyDTO invalidDto = createRentalPropertyCreateDTO("Paris", "Appartement");
        invalidDto.setRentAmount(-100.0);

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_validation_error_for_negative_security_deposit() throws Exception {
        // Given
        CreateRentalPropertyDTO invalidDto = createRentalPropertyCreateDTO("Paris", "Appartement");
        invalidDto.setSecurityDepositAmount(-200.0);

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_validation_error_for_negative_area() throws Exception {
        // Given
        CreateRentalPropertyDTO invalidDto = createRentalPropertyCreateDTO("Paris", "Appartement");
        invalidDto.setArea(-50.0);

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_validation_error_for_blank_town() throws Exception {
        // Given
        CreateRentalPropertyDTO invalidDto = createRentalPropertyCreateDTO("", "Appartement");

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_validation_error_for_blank_address() throws Exception {
        // Given
        CreateRentalPropertyDTO invalidDto = createRentalPropertyCreateDTO("Paris", "Appartement");
        invalidDto.setAddress("");

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_validation_error_for_blank_property_type() throws Exception {
        // Given
        CreateRentalPropertyDTO invalidDto = createRentalPropertyCreateDTO("Paris", "");

        // When & Then
        mockMvc.perform(post("/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    private CreateRentalPropertyDTO createRentalPropertyCreateDTO(String town, String propertyType) {
        CreateRentalPropertyDTO dto = new CreateRentalPropertyDTO();
        dto.setDescription("Belle propriété avec vue");
        dto.setTown(town);
        dto.setAddress("123 Rue de la Paix");
        dto.setPropertyType(propertyType);
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

    private RentalPropertyDTO createRentalPropertyDTO(String town, String propertyType) {
        RentalPropertyDTO dto = new RentalPropertyDTO();
        dto.setDescription("Belle propriété avec vue");
        dto.setTown(town);
        dto.setAddress("123 Rue de la Paix");
        dto.setPropertyType(propertyType);
        dto.setRentAmount(1200.0);
        dto.setSecurityDepositAmount(2400.0);
        dto.setArea(75.5);
        return dto;
    }
}