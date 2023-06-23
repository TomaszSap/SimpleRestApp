package com.tomaszsap.simplerestapp.exception;

import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler {
    @SneakyThrows
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {APIException.class})
    public ResponseEntity<Object> handleRequestException(APIException e) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(e.getMessage(), header, HttpStatus.valueOf(e.getStatusCode()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // Tworzenie listy komunikatów walidacji
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        // Tworzenie odpowiedzi z błędami walidacji
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
