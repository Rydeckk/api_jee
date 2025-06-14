package fr.esgi.rent.external.rental_property_api.resource;

import fr.esgi.rent.external.rental_property_api.RentalPropertyService;
import fr.esgi.rent.external.rental_property_api.dto.*;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class RentalPropertyResourceTest {

    private RentalPropertyService service;
    private RentalPropertyResource resource;

    private RentalPropertyDTO dto;
    private RentalPropertyCreateDTO createDto;
    private RentalPropertyUpdateDTO updateDto;
    private RentalPropertyPatchDTO patchDto;

    @BeforeEach
    void setup() {
        service = mock(RentalPropertyService.class);
        resource = new RentalPropertyResource();
        resource.service = service;

        dto = new RentalPropertyDTO();
        dto.address = "12 rue test";
        dto.area = 55.5;
        dto.description = "Beau logement";
        dto.propertyType = "FLAT";
        dto.rentAmount = 1000.0;
        dto.securityDepositAmount = 1500.0;
        dto.town = "Paris";

        createDto = new RentalPropertyCreateDTO();
        createDto.description = "Appartement bien situé";
        createDto.town = "Paris";
        createDto.address = "12 rue test";
        createDto.propertyType = "FLAT";
        createDto.rentAmount = 1000.0;
        createDto.securityDepositAmount = 1500.0;
        createDto.area = 55.5;

        updateDto = new RentalPropertyUpdateDTO();
        updateDto.description = "Appartement mis à jour";
        updateDto.town = "Paris";
        updateDto.address = "12 rue test";
        updateDto.propertyType = "FLAT";
        updateDto.rentAmount = 1100.0;
        updateDto.securityDepositAmount = 1600.0;
        updateDto.area = 60.0;

        patchDto = new RentalPropertyPatchDTO();
        patchDto.rentAmount = 1200.0;
    }

    @Test
    void testGetAll() {
        when(service.getAllRentalProperties()).thenReturn(List.of(dto));
        List<RentalPropertyDTO> result = resource.getAll();
        assertEquals(1, result.size());
    }

    @Test
    void testGetById_found() {
        when(service.getRentalPropertyById(1L)).thenReturn(dto);
        Response response = resource.getById(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(dto, response.getEntity());
    }

    @Test
    void testGetById_notFound() {
        when(service.getRentalPropertyById(99L)).thenReturn(null);
        Response response = resource.getById(99L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testCreateRentalProperty_success() {
        when(service.createRentalPropertyRequest(createDto)).thenReturn(true);
        Response response = resource.createRentalProperty(createDto);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    void testCreateRentalProperty_fail() {
        when(service.createRentalPropertyRequest(createDto)).thenReturn(false);
        Response response = resource.createRentalProperty(createDto);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateRentalProperty_success() {
        when(service.updateRentalProperty(1L, updateDto)).thenReturn(true);
        Response response = resource.updateProperty(1L, updateDto);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateRentalProperty_fail() {
        when(service.updateRentalProperty(1L, updateDto)).thenReturn(false);
        Response response = resource.updateProperty(1L, updateDto);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testPatchRentalProperty_success() {
        when(service.patchRentalProperty(1L, patchDto)).thenReturn(true);
        Response response = resource.patchRentalProperty(1L, patchDto);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testPatchRentalProperty_notFound() {
        when(service.patchRentalProperty(99L, patchDto)).thenReturn(false);
        Response response = resource.patchRentalProperty(99L, patchDto);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testPatchRentalProperty_badRequest() {
        when(service.patchRentalProperty(1L, patchDto)).thenThrow(new BadRequestException("Invalid"));
        Response response = resource.patchRentalProperty(1L, patchDto);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testPatchRentalProperty_internalError() {
        when(service.patchRentalProperty(1L, patchDto)).thenThrow(new RuntimeException("Unexpected"));
        Response response = resource.patchRentalProperty(1L, patchDto);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteRentalProperty_success() {
        when(service.deleteRentalProperty(1L)).thenReturn(true);
        Response response = resource.deleteRentalProperty(1L);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteRentalProperty_notFound() {
        when(service.deleteRentalProperty(99L)).thenReturn(false);
        Response response = resource.deleteRentalProperty(99L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteRentalProperty_internalError() {
        when(service.deleteRentalProperty(1L)).thenThrow(new RuntimeException("Error"));
        Response response = resource.deleteRentalProperty(1L);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}