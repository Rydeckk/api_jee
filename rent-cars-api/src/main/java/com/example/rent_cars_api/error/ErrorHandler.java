package com.example.rent_cars_api.error;

import com.example.rent_cars_api.exception.NotFoundRentalCarException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundRentalCarException.class)
    public ErrorDto handleNotFoundRentalPropertyException(NotFoundRentalCarException notFoundRentalPropertyException) {
        return new ErrorDto(notFoundRentalPropertyException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValidException() {
        return new ErrorDto("La requête envoyée est invalide");
    }

}
