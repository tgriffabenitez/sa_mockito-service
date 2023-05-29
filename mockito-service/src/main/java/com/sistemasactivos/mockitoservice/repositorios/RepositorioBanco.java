package com.sistemasactivos.mockitoservice.repositorios;

import com.sistemasactivos.mockitoservice.modelos.Banco;

import java.util.List;

public interface RepositorioBanco {
    List<Banco> findAll();
    Banco findById(Long id);
    void update(Banco banco);
}
