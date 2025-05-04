package fr.esgi.rent.properties.validator;

import fr.esgi.rent.properties.dto.CreateRentalPropertyDTO;
import fr.esgi.rent.properties.dto.UpdateRentalPropertyDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalPropertyValidator {

    public List<String> validateCreate(CreateRentalPropertyDTO dto) {
        List<String> errors = new ArrayList<>();

        // Check required fields
        if (!StringUtils.hasText(dto.getDescription())) {
            errors.add("Description is required");
        }

        if (!StringUtils.hasText(dto.getTown())) {
            errors.add("Town is required");
        }

        if (!StringUtils.hasText(dto.getAddress())) {
            errors.add("Address is required");
        }

        if (!StringUtils.hasText(dto.getPropertyType())) {
            errors.add("Property type is required");
        }

        if (dto.getRentAmount() == null) {
            errors.add("Rent amount is required");
        } else if (dto.getRentAmount() <= 0) {
            errors.add("Rent amount must be positive");
        }

        if (dto.getSecurityDepositAmount() == null) {
            errors.add("Security deposit amount is required");
        } else if (dto.getSecurityDepositAmount() <= 0) {
            errors.add("Security deposit amount must be positive");
        }

        if (dto.getArea() == null) {
            errors.add("Area is required");
        } else if (dto.getArea() <= 0) {
            errors.add("Area must be positive");
        }

        return errors;
    }

    public List<String> validateUpdate(UpdateRentalPropertyDTO dto) {
        List<String> errors = new ArrayList<>();

        // Check for positive values if present
        if (dto.getRentAmount() != null && dto.getRentAmount() <= 0) {
            errors.add("Rent amount must be positive");
        }

        if (dto.getSecurityDepositAmount() != null && dto.getSecurityDepositAmount() <= 0) {
            errors.add("Security deposit amount must be positive");
        }

        if (dto.getArea() != null && dto.getArea() <= 0) {
            errors.add("Area must be positive");
        }

        return errors;
    }
}