package com.example.rent_cars_api.exception;

public class NotFoundRentalCarException extends RuntimeException {
    public NotFoundRentalCarException(String message) {
        super(message);
    }
}
