package com.fredrikpedersen.experis_vin_lotteri.web;

import com.fredrikpedersen.experis_vin_lotteri.exceptions.NoSuchPersistedEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({NoSuchPersistedEntityException.class})
    public ResponseEntity<?> handleNoSuchPersistedEntityException(final NoSuchPersistedEntityException exception) {
        log.error("Tried retrieving non-existing entity: ", exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(final Exception exception) {
        log.error("An unexpected exception ocurred: ", exception);
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}