package fr.esgi.rent.external.rental_property_api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyDTO;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyCreateDTO;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyUpdateDTO;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyPatchDTO;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RentalPropertyServiceTest {

    private RentalPropertyService service;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private HttpResponse<String> mockResponseString;
    private HttpResponse<Void> mockResponseVoid;
    private RentalPropertyDTO dto1;
    private RentalPropertyDTO dto2;
    private RentalPropertyCreateDTO createDto;
    private RentalPropertyUpdateDTO updateDto;
    private RentalPropertyPatchDTO patchDto;

    @BeforeEach
    void setup() throws Exception {
        httpClient = mock(HttpClient.class);
        objectMapper = new ObjectMapper();
        mockResponseString = mock(HttpResponse.class);
        mockResponseVoid = mock(HttpResponse.class);

        service = new RentalPropertyService("http://fake-url", httpClient, objectMapper);

        dto1 = new RentalPropertyDTO();
        dto1.address = "42 avenue des Champs-Élysées";
        dto1.area = 55.5;
        dto1.description = "Appartement lumineux au cœur de Paris";
        dto1.propertyType = "FLAT";
        dto1.rentAmount = 1200.0;
        dto1.securityDepositAmount = 2400.0;
        dto1.town = "Paris";

        dto2 = new RentalPropertyDTO();
        dto2.address = "54 avenue des Champs-Élysées";
        dto2.area = 55.5;
        dto2.description = "Appartement luxueux au cœur de Paris";
        dto2.propertyType = "FLAT";
        dto2.rentAmount = 1500.0;
        dto2.securityDepositAmount = 2400.0;
        dto2.town = "Paris";

        createDto = new RentalPropertyCreateDTO();
        createDto.description = "Appartement bien situé près du métro";
        createDto.town = "Neuilly-sur-Seine";
        createDto.address = "90 rue de la Victoire";
        createDto.propertyType = "FLAT";
        createDto.rentAmount = 1040.9;
        createDto.securityDepositAmount = 1250.9;
        createDto.area = 50.69;
        createDto.numberOfBedrooms = 3;
        createDto.floorNumber = 2;
        createDto.numberOfFloors = 5;
        createDto.constructionYear = 1989;
        createDto.energyClassification = "B";
        createDto.hasElevator = true;
        createDto.hasIntercom = true;
        createDto.hasBalcony = true;
        createDto.hasParkingSpace = true;

        updateDto = new RentalPropertyUpdateDTO();
        updateDto.description = "Appartement rénové avec balcon";
        updateDto.town = "Neuilly-sur-Seine";
        updateDto.address = "90 rue de la Victoire";
        updateDto.propertyType = "FLAT";
        updateDto.rentAmount = 1100.0;
        updateDto.securityDepositAmount = 1300.0;
        updateDto.area = 55.0;
        updateDto.numberOfBedrooms = 3;
        updateDto.floorNumber = 2;
        updateDto.numberOfFloors = 5;
        updateDto.constructionYear = 1990;
        updateDto.energyClassification = "B";
        updateDto.hasElevator = true;
        updateDto.hasIntercom = false;
        updateDto.hasBalcony = true;
        updateDto.hasParkingSpace = true;

        patchDto = new RentalPropertyPatchDTO();
        patchDto.rentAmount = 1600.0;

    }

    @Test
    void testGetAllRentalProperties_success() throws Exception {
        String json = objectMapper.writeValueAsString(List.of(dto1, dto2));

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponseString);
        when(mockResponseString.statusCode()).thenReturn(200);
        when(mockResponseString.body()).thenReturn(json);

        List<RentalPropertyDTO> result = service.getAllRentalProperties();
        assertEquals(2, result.size());
        assertEquals("Paris", result.get(0).town);
        assertEquals("Paris", result.get(1).town);
    }

    @Test
    void testGetRentalPropertyById_notFound() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponseString);
        when(mockResponseString.statusCode()).thenReturn(404);

        assertNull(service.getRentalPropertyById(1L));
    }

    @Test
    void testCreateRentalPropertyRequest_success() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(201);

        boolean result = service.createRentalPropertyRequest(createDto);
        assertTrue(result);
    }

    @Test
    void testUpdateRentalProperty_success() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(200);

        boolean result = service.updateRentalProperty(1L, updateDto);
        assertTrue(result);
    }

    @Test
    void testPatchRentalProperty_success() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(200);

        boolean result = service.patchRentalProperty(1L, patchDto);
        assertTrue(result);
    }

    @Test
    void testPatchRentalProperty_badRequest() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(400);

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.patchRentalProperty(1L, patchDto)
        );

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void testDeleteRentalProperty_success() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(204);

        assertTrue(service.deleteRentalProperty(1L));
    }
}
