package fr.esgi.rent.external.rental_car_api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.rent.external.rental_car_api.dto.RentalCarCreateDTO;
import fr.esgi.rent.external.rental_car_api.dto.RentalCarDTO;
import fr.esgi.rent.external.rental_car_api.dto.RentalCarPatchDTO;
import fr.esgi.rent.external.rental_car_api.dto.RentalCarUpdateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@ApplicationScoped
public class RentalCarService {
    private final String backendUrl;
    final HttpClient client;
    final ObjectMapper mapper;

    public RentalCarService() {
        this("http://localhost:8082/api/rent-cars-api", HttpClient.newHttpClient(), new ObjectMapper());
    }

    public RentalCarService(String backendUrl, HttpClient client, ObjectMapper mapper) {
        this.backendUrl = backendUrl;
        this.client = client;
        this.mapper = mapper;
    }

    public List<RentalCarDTO> getAllRentalCars() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(backendUrl + "/rental-cars"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error : " + response.statusCode());
            }

            return mapper.readValue(response.body(), new TypeReference<List<RentalCarDTO>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    public RentalCarDTO getRentalCarById(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(backendUrl + "/rental-cars/" + id))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                return null;
            } else if (response.statusCode() != 200) {
                throw new RuntimeException("Error : " + response.statusCode());
            }

            return mapper.readValue(response.body(), RentalCarDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    public boolean createRentalCarRequest(RentalCarCreateDTO dto) {
        try {
            String json = mapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(backendUrl + "/rental-cars"))
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

    public boolean updateRentalCar(Long id, RentalCarUpdateDTO dto) {
        try {
            String json = mapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(backendUrl + "/rental-cars/" + id))
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

    public boolean patchRentalCar(Long id, RentalCarPatchDTO dto) {
        try {
            String json = mapper.writeValueAsString(dto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(backendUrl + "/rental-cars/" + id))
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

    public boolean deleteRentalCar(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(backendUrl + "/rental-cars/" + id))
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
