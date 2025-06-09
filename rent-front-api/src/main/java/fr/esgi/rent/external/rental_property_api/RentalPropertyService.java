package fr.esgi.rent.external.rental_property_api;

import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyCreateDTO;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyPatchDTO;
import fr.esgi.rent.external.rental_property_api.dto.RentalPropertyUpdateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@ApplicationScoped
public class RentalPropertyService {

    private final String BACKEND_URL = "http://localhost:8081/rent-properties-api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<RentalPropertyDTO> getAllRentalProperties() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BACKEND_URL + "/rental-properties"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error : " + response.statusCode());
            }

            return mapper.readValue(response.body(), new TypeReference<List<RentalPropertyDTO>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    public RentalPropertyDTO getRentalPropertyById(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BACKEND_URL + "/rental-properties/" + id))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                return null;
            } else if (response.statusCode() != 200) {
                throw new RuntimeException("Error : " + response.statusCode());
            }

            return mapper.readValue(response.body(), RentalPropertyDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    public boolean createRentalPropertyRequest(RentalPropertyCreateDTO dto) {
        try {
            String json = mapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BACKEND_URL + "/rental-properties"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            return response.statusCode() == 201;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRentalProperty(Long id, RentalPropertyUpdateDTO dto) {
        try {
            String json = mapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BACKEND_URL + "/rental-properties/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            return response.statusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean patchRentalProperty(Long id, RentalPropertyPatchDTO dto) {
        try {
            String json = mapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BACKEND_URL + "/rental-properties/" + id))
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            int status = response.statusCode();
            if (status == 404) {
                return false;
            } else if (status == 400) {
                throw new BadRequestException("Invalid request");
            } else if (status == 200) {
                return true;
            } else {
                throw new RuntimeException("Unexpected status code " + status);
            }
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteRentalProperty(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BACKEND_URL + "/rental-properties/" + id))
                    .DELETE()
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            return response.statusCode() == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
