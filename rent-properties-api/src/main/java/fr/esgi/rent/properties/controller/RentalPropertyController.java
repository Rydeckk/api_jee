package fr.esgi.rent.properties.controller;

import fr.esgi.rent.properties.dto.CreateRentalPropertyDTO;
import fr.esgi.rent.properties.dto.RentalPropertyDTO;
import fr.esgi.rent.properties.dto.UpdateRentalPropertyDTO;
import fr.esgi.rent.properties.service.RentalPropertyService;
import fr.esgi.rent.properties.validator.RentalPropertyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rent-properties-api/rental-properties")
public class RentalPropertyController {

    private final RentalPropertyService rentalPropertyService;
    private final RentalPropertyValidator validator;

    @Autowired
    public RentalPropertyController(RentalPropertyService rentalPropertyService,
                                    RentalPropertyValidator validator) {
        this.rentalPropertyService = rentalPropertyService;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<List<RentalPropertyDTO>> getAllRentalProperties() {
        List<RentalPropertyDTO> properties = rentalPropertyService.getAllRentalProperties();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalPropertyDTO> getRentalPropertyById(@PathVariable Long id) {
        RentalPropertyDTO property = rentalPropertyService.getRentalPropertyById(id);
        return ResponseEntity.ok(property);
    }

    @PostMapping
    public ResponseEntity<?> createRentalProperty(@RequestBody CreateRentalPropertyDTO dto) {
        List<String> errors = validator.validateCreate(dto);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        rentalPropertyService.createRentalProperty(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRentalProperty(@PathVariable Long id,
                                                  @RequestBody CreateRentalPropertyDTO dto) {
        List<String> errors = validator.validateCreate(dto);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        rentalPropertyService.updateRentalProperty(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateRentalProperty(@PathVariable Long id,
                                                         @RequestBody UpdateRentalPropertyDTO dto) {
        List<String> errors = validator.validateUpdate(dto);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        rentalPropertyService.partialUpdateRentalProperty(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalProperty(@PathVariable Long id) {
        rentalPropertyService.deleteRentalProperty(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}