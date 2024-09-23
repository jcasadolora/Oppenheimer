package com.nisum.oppenheimer.api.restful.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiExceptionAdviser is a global exception handler for the API.
 * It intercepts various types of exceptions that occur during
 * the request processing and transforms them into user-friendly
 * responses, maintaining a consistent error handling mechanism.
 *
 * It handles common exceptions like validation errors, bad requests,
 * and other server-side errors, providing meaningful messages and
 * HTTP status codes.
 *
 * The class is annotated with @RestControllerAdvice, meaning it will
 * handle exceptions across all @RestController annotated classes in
 * the application.
 */
@RestControllerAdvice
public class ApiExceptionAdviser {

    /**
     * Handles MethodArgumentNotValidException and returns a 400 Bad Request response
     * with details of validation failures.
     *
     * This exception occurs when the validation on an argument annotated
     * with @Valid fails. The method extracts field errors and returns them
     * as a map of field names and corresponding error messages.
     *
     * @param ex the MethodArgumentNotValidException thrown during validation
     * @return a ResponseEntity containing a map of field errors
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        var error = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a 400 Bad Request response
     * with details of validation failures.
     *
     * This exception occurs when the validation on an argument annotated
     * with @Valid fails. The method extracts field errors and returns them
     * as a map of field names and corresponding error messages.
     *
     * @param ex the MethodArgumentNotValidException thrown during validation
     * @return a ResponseEntity containing a map of field errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
          .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any other uncaught exceptions and returns a 500 Internal Server Error response.
     *
     * @param ex      the exception
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        var error = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
