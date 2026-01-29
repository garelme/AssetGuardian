package com.akif.assetguardian.exception;

public class ResourceNotFoundException extends RuntimeException { // Miras ekle!
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
