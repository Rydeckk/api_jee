package fr.esgi.rent.external.rental_car_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.rent.external.rental_car_api.dto.*;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RentalCarServiceTest {

    private RentalCarService service;
    private HttpClient mockClient;
    private ObjectMapper mapper;
    private HttpResponse<String> mockResponseString;
    private HttpResponse<Void> mockResponseVoid;

    private RentalCarDTO dto;
    private RentalCarCreateDTO createDto;
    private RentalCarUpdateDTO updateDto;
    private RentalCarPatchDTO patchDto;

    @BeforeEach
    void setup() {
        mockClient = mock(HttpClient.class);
        mapper = new ObjectMapper();
        mockResponseString = mock(HttpResponse.class);
        mockResponseVoid = mock(HttpResponse.class);

        service = new RentalCarService("http://fake-url", mockClient, mapper);

        dto = new RentalCarDTO();
        dto.brand = "BMW";
        dto.model = "Serie 1";
        dto.rentAmount = 790.9;
        dto.securityDepositAmount = 1550.9;
        dto.numberOfSeats = 5;
        dto.numberOfDoors = 4;
        dto.hasAirConditioning = true;

        createDto = new RentalCarCreateDTO();
        createDto.brand = "BMW";
        createDto.model = "Serie 1";
        createDto.rentAmount = 790.9;
        createDto.securityDepositAmount = 1550.9;
        createDto.numberOfSeats = 5;
        createDto.numberOfDoors = 4;
        createDto.hasAirConditioning = true;

        updateDto = new RentalCarUpdateDTO();
        updateDto.brand = "BMW";
        updateDto.model = "Serie 2";
        updateDto.rentAmount = 800.0;
        updateDto.securityDepositAmount = 1600.0;
        updateDto.numberOfSeats = 5;
        updateDto.numberOfDoors = 5;
        updateDto.hasAirConditioning = true;

        patchDto = new RentalCarPatchDTO();
        patchDto.rentAmount = 820.0;
    }

    @Test
    void testGetAllRentalCars_success() throws Exception {
        String json = mapper.writeValueAsString(List.of(dto));
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseString);
        when(mockResponseString.statusCode()).thenReturn(200);
        when(mockResponseString.body()).thenReturn(json);

        List<RentalCarDTO> result = service.getAllRentalCars();
        assertEquals(1, result.size());
        assertEquals("BMW", result.get(0).brand);
    }

    @Test
    void testGetRentalCarById_found() throws Exception {
        String json = mapper.writeValueAsString(dto);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseString);
        when(mockResponseString.statusCode()).thenReturn(200);
        when(mockResponseString.body()).thenReturn(json);

        RentalCarDTO result = service.getRentalCarById(1L);
        assertEquals("BMW", result.brand);
    }

    @Test
    void testGetRentalCarById_notFound() throws Exception {
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseString);
        when(mockResponseString.statusCode()).thenReturn(404);

        RentalCarDTO result = service.getRentalCarById(1L);
        assertNull(result);
    }

    @Test
    void testCreateRentalCar_success() throws Exception {
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(201);

        boolean result = service.createRentalCarRequest(createDto);
        assertTrue(result);
    }

    @Test
    void testUpdateRentalCar_success() throws Exception {
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(200);

        boolean result = service.updateRentalCar(1L, updateDto);
        assertTrue(result);
    }

    @Test
    void testPatchRentalCar_success() throws Exception {
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(200);

        boolean result = service.patchRentalCar(1L, patchDto);
        assertTrue(result);
    }

    @Test
    void testPatchRentalCar_notFound() throws Exception {
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(404);

        boolean result = service.patchRentalCar(1L, patchDto);
        assertFalse(result);
    }

    @Test
    void testPatchRentalCar_badRequest() throws Exception {
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(400);

        assertThrows(BadRequestException.class, () -> service.patchRentalCar(1L, patchDto));
    }

    @Test
    void testDeleteRentalCar_success() throws Exception {
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponseVoid);
        when(mockResponseVoid.statusCode()).thenReturn(204);

        boolean result = service.deleteRentalCar(1L);
        assertTrue(result);
    }
}