package com.caito.productos.api.exceptions.customs;

/**
 * @author claudio.vilas
 * date 09/20223
 */

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
