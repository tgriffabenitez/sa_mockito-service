package com.sistemasactivos.mockitoservice.servicios;

import com.sistemasactivos.mockitoservice.modelos.Cuenta;

import java.math.BigDecimal;

public interface ServicioCuenta {
    Cuenta findById(Long id);
    int revisarTotalTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId);

}
