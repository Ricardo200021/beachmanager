package com.floripapp.beachmanager.exception;

public class BeachNotFoundException extends RuntimeException {
    public BeachNotFoundException(Long id) {
        super("Praia não encontrada com ID: " + id);
    }
}
