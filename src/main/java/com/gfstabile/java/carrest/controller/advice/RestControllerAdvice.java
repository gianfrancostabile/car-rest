package com.gfstabile.java.carrest.controller.advice;

import com.gfstabile.java.carrest.entity.ValidationErrorCollection;
import com.gfstabile.java.carrest.exception.impl.car.CarAlreadyExistsException;
import com.gfstabile.java.carrest.exception.impl.car.CarNotFoundException;
import com.gfstabile.java.carrest.utilities.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main controller advice for unhandled exceptions
 *
 * @author G. F. STabile
 */
@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorCollection> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
            .getAllErrors()
            .stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.toList());
        return new ResponseEntity<>(new ValidationErrorCollection(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarAlreadyExistsException.class)
    public ResponseEntity<ValidationErrorCollection> handleCarAlreadyExistsException() {
        return this.buildUserErrorResponse(ErrorMessage.CAR_ALREADY_EXISTS);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ValidationErrorCollection> handleCarNotFoundException() {
        return this.buildUserErrorResponse(ErrorMessage.CAR_NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorCollection> handleHttpMessageNotReadableException() {
        return this.buildUserErrorResponse(ErrorMessage.BODY_MANDATORY);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ValidationErrorCollection> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException exception) {
        return this.buildUserErrorResponse(exception.getMessage());
    }

    private ResponseEntity<ValidationErrorCollection> buildUserErrorResponse(String... errors) {
        return new ResponseEntity<>(new ValidationErrorCollection(Arrays.asList(errors)), HttpStatus.BAD_REQUEST);
    }
}
