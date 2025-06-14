package fr.esgi.rent.external.rental_car_api.resource;

import fr.esgi.rent.external.rental_car_api.RentalCarService;
import fr.esgi.rent.external.rental_car_api.dto.RentalCarCreateDTO;
import fr.esgi.rent.external.rental_car_api.dto.RentalCarDTO;
import fr.esgi.rent.external.rental_car_api.dto.RentalCarPatchDTO;
import fr.esgi.rent.external.rental_car_api.dto.RentalCarUpdateDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/rental-cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class RentalCarResource {
    @Inject
    RentalCarService service;

    @GET
    public List<RentalCarDTO> getAll() {
        return service.getAllRentalCars();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        RentalCarDTO dto = service.getRentalCarById(id);
        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dto).build();
    }

    @POST
    public Response createRentalCar(@Valid RentalCarCreateDTO dto) {
        boolean success = service.createRentalCarRequest(dto);
        if (!success) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCar(@PathParam("id") Long id, @Valid RentalCarUpdateDTO dto) {
        boolean success = service.updateRentalCar(id, dto);
        if (!success) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @PATCH
    @Path("/{id}")
    public Response patchRentalCar(@PathParam("id") Long id, @Valid RentalCarPatchDTO dto) {
        try {
            boolean success = service.patchRentalCar(id, dto);
            if (!success) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok().build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRentalCar(@PathParam("id") Long id) {
        try {
            boolean success = service.deleteRentalCar(id);
            if (success) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
