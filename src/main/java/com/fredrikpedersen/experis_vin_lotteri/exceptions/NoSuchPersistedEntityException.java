package com.fredrikpedersen.experis_vin_lotteri.exceptions;

public class NoSuchPersistedEntityException extends RuntimeException {

    public NoSuchPersistedEntityException(final String message) {
        super(message);
    }
}