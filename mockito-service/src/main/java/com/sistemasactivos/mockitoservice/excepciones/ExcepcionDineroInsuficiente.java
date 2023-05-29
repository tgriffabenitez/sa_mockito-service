package com.sistemasactivos.mockitoservice.excepciones;

public class ExcepcionDineroInsuficiente extends RuntimeException {
    public ExcepcionDineroInsuficiente(String message) {
        super(message);
    }
}
