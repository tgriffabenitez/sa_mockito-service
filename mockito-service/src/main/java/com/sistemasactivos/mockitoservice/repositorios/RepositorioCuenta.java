package com.sistemasactivos.mockitoservice.repositorios;

import com.sistemasactivos.mockitoservice.modelos.Cuenta;

import java.util.List;

public interface RepositorioCuenta {
    List<Cuenta> findAll();
    Cuenta findById(Long id);
    void update(Cuenta cuenta);
}
