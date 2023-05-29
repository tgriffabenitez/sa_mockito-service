package com.sistemasactivos.mockitoservice.servicios;

import com.sistemasactivos.mockitoservice.modelos.Banco;
import com.sistemasactivos.mockitoservice.modelos.Cuenta;
import com.sistemasactivos.mockitoservice.repositorios.RepositorioBanco;
import com.sistemasactivos.mockitoservice.repositorios.RepositorioCuenta;

import java.math.BigDecimal;

public class ServicioCuentaImpl implements ServicioCuenta {
    private RepositorioCuenta repositorioCuenta;
    private RepositorioBanco repositorioBanco;

    public ServicioCuentaImpl(RepositorioCuenta repositorioCuenta, RepositorioBanco repositorioBanco) {
        this.repositorioCuenta = repositorioCuenta;
        this.repositorioBanco = repositorioBanco;
    }

    @Override
    public Cuenta findById(Long id) {
        return repositorioCuenta.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = repositorioBanco.findById(bancoId);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = repositorioCuenta.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto) {
        Banco banco = repositorioBanco.findById(1L); // dejo el id hardcodeado
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias); // incremento el total de transferencias
        repositorioBanco.update(banco); // actualizo el banco

        Cuenta cuentaOrigen = repositorioCuenta.findById(numeroCuentaOrigen);
        cuentaOrigen.debito(monto);
        repositorioCuenta.update(cuentaOrigen);

        Cuenta cuentaDestino = repositorioCuenta.findById(numeroCuentaDestino);
        cuentaDestino.credito(monto);
        repositorioCuenta.update(cuentaDestino);

    }
}
