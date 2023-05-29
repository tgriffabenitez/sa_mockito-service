package com.sistemasactivos.mockitoservice.modelos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Banco {
    private Long id;
    private String nombre;
    private int totalTransferencias;

    public Banco(Long id, String nombre, int totalTransferencias) {
        this.id = id;
        this.nombre = nombre;
        this.totalTransferencias = totalTransferencias;
    }
}
