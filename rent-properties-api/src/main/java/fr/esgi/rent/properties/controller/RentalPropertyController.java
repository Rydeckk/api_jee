package fr.esgi.rent.properties.controller;

import fr.esgi.rent.properties.dto.CreateRentalPropertyDTO;
import fr.esgi.rent.properties.dto.RentalPropertyDTO;
import fr.esgi.rent.properties.dto.UpdateRentalPropertyDTO;
import fr.esgi.rent.properties.service.RentalPropertyService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rental-properties")
public class RentalPropertyController {

    private final RentalPropertyService rentalPropertyService;

    public RentalPropertyController(RentalPropertyService rentalPropertyService) {
        this.rentalPropertyService = rentalPropertyService;
    }

    @GetMapping
    public ResponseEntity<List<RentalPropertyDTO>> getAllRentalProperties() {
        List<RentalPropertyDTO> properties = rentalPropertyService.getAllRentalProperties();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalPropertyDTO> getRentalPropertyById(@PathVariable("id") Long id) {
        RentalPropertyDTO property = rentalPropertyService.getRentalPropertyById(id);
        return ResponseEntity.ok(property);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalPropertyDTO createRentalProperty(@Valid @RequestBody CreateRentalPropertyDTO dto) {
        return rentalPropertyService.createRentalProperty(dto);
    }

    @PutMapping("/{id}")
    public RentalPropertyDTO updateRentalProperty(@PathVariable("id") Long id,
                                                  @Valid @RequestBody CreateRentalPropertyDTO dto) {
        return rentalPropertyService.updateRentalProperty(id, dto);
    }

    @PatchMapping("/{id}")
    public RentalPropertyDTO partialUpdateRentalProperty(@PathVariable("id") Long id,
                                                         @Valid @RequestBody UpdateRentalPropertyDTO dto) {
        return rentalPropertyService.partialUpdateRentalProperty(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalProperty(@PathVariable("id") Long id) {
        rentalPropertyService.deleteRentalProperty(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}