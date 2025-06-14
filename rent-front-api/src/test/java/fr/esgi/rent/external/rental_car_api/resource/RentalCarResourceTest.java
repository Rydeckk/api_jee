package fr.esgi.rent.external.rental_car_api.resource;

import fr.esgi.rent.external.rental_car_api.RentalCarService;
import fr.esgi.rent.external.rental_car_api.dto.*;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentalCarResourceTest {

    private RentalCarService service;
    private RentalCarResource resource;

    private RentalCarDTO dto;
    private RentalCarCreateDTO createDto;
    private RentalCarUpdateDTO updateDto;
    private RentalCarPatchDTO patchDto;

    @BeforeEach
    void setup() {
        service = mock(RentalCarService.class);
        resource = new RentalCarResource();
        resource.service = service;

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
    void testGetAll() {
        when(service.getAllRentalCars()).thenReturn(List.of(dto));
        List<RentalCarDTO> result = resource.getAll();
        assertEquals(1, result.size());
        assertEquals("BMW", result.get(0).brand);
    }

    @Test
    void testGetById_found() {
        when(service.getRentalCarById(1L)).thenReturn(dto);
        Response response = resource.getById(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(dto, response.getEntity());
    }

    @Test
    void testGetById_notFound() {
        when(service.getRentalCarById(99L)).thenReturn(null);
        Response response = resource.getById(99L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testCreateRentalCar_success() {
        when(service.createRentalCarRequest(createDto)).thenReturn(true);
        Response response = resource.createRentalCar(createDto);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    void testCreateRentalCar_fail() {
        when(service.createRentalCarRequest(createDto)).thenReturn(false);
        Response response = resource.createRentalCar(createDto);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateRentalCar_success() {
        when(service.updateRentalCar(1L, updateDto)).thenReturn(true);
        Response response = resource.updateCar(1L, updateDto);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateRentalCar_fail() {
        when(service.updateRentalCar(1L, updateDto)).thenReturn(false);
        Response response = resource.updateCar(1L, updateDto);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testPatchRentalCar_success() {
        when(service.patchRentalCar(1L, patchDto)).thenReturn(true);
        Response response = resource.patchRentalCar(1L, patchDto);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testPatchRentalCar_notFound() {
        when(service.patchRentalCar(1L, patchDto)).thenReturn(false);
        Response response = resource.patchRentalCar(1L, patchDto);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testPatchRentalCar_badRequest() {
        when(service.patchRentalCar(1L, patchDto)).thenThrow(new BadRequestException("Invalid"));
        Response response = resource.patchRentalCar(1L, patchDto);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testPatchRentalCar_internalError() {
        when(service.patchRentalCar(1L, patchDto)).thenThrow(new RuntimeException("Error"));
        Response response = resource.patchRentalCar(1L, patchDto);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteRentalCar_success() {
        when(service.deleteRentalCar(1L)).thenReturn(true);
        Response response = resource.deleteRentalCar(1L);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteRentalCar_notFound() {
        when(service.deleteRentalCar(1L)).thenReturn(false);
        Response response = resource.deleteRentalCar(1L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteRentalCar_internalError() {
        when(service.deleteRentalCar(1L)).thenThrow(new RuntimeException("Error"));
        Response response = resource.deleteRentalCar(1L);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}