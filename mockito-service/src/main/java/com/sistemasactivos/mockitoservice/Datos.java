package com.sistemasactivos.mockitoservice;

import com.sistemasactivos.mockitoservice.modelos.Banco;
import com.sistemasactivos.mockitoservice.modelos.Cuenta;

import java.math.BigDecimal;

public class Datos {

//    public static final Cuenta CUENTA_001 = new Cuenta(1L, "Andres", new BigDecimal("1000"));
//    public static final Cuenta CUENTA_002 = new Cuenta(2L, "John", new BigDecimal("2000"));
//    public static final Banco BANCO = new Banco(1L, "El banco financiero", 0);

    public static Cuenta crearCuenta001() {
        return new Cuenta(1L, "Andres", new BigDecimal("1000"));
    }

    public static Cuenta crearCuenta002() {
        return new Cuenta(2L, "John", new BigDecimal("2000"));
    }

    public static Banco crearBanco() {
        return new Banco(1L, "El banco financiero", 0);
    }

}