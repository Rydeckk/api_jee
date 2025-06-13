package fr.esgi.rent.properties.exception;

public class NotFoundRentalPropertyException extends RuntimeException {
    public NotFoundRentalPropertyException(String message) {
        super(message);
    }

    public NotFoundRentalPropertyException(Long id) {
        super("Rental property with id " + id + " not found");
    }
}