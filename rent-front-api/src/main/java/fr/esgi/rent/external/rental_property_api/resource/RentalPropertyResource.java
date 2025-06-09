package fr.esgi.rent.external.rental_property_api.resource;

import fr.esgi.rent.external.rental_property_api.RentalPropertyService;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyCreateDTO;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyDTO;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyPatchDTO;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyUpdateDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/rental-properties")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class RentalPropertyResource {

    @Inject
    RentalPropertyService service;

    @GET
    public List<RentalPropertyDTO> getAll() {
        return service.getAllRentalProperties();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        RentalPropertyDTO dto = service.getRentalPropertyById(id);
        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dto).build();
    }

    @POST
    public Response createRentalProperty(@Valid RentalPropertyCreateDTO dto) {
        boolean success = service.createRentalPropertyRequest(dto);
        if (!success) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProperty(@PathParam("id") Long id, @Valid RentalPropertyUpdateDTO dto) {
        boolean success = service.updateRentalProperty(id, dto);
        if (!success) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @PATCH
    @Path("/{id}")
    public Response patchRentalProperty(@PathParam("id") Long id, @Valid RentalPropertyPatchDTO dto) {
        try {
            boolean success = service.patchRentalProperty(id, dto);
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
    public Response deleteRentalProperty(@PathParam("id") Long id) {
        try {
            boolean success = service.deleteRentalProperty(id);
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
