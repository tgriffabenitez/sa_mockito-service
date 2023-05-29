package com.sistemasactivos.mockitoservice;

import com.sistemasactivos.mockitoservice.repositorios.RepositorioBanco;
import com.sistemasactivos.mockitoservice.repositorios.RepositorioCuenta;
import com.sistemasactivos.mockitoservice.servicios.ServicioCuenta;
import com.sistemasactivos.mockitoservice.servicios.ServicioCuentaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class MockitoServiceApplicationTests {

    RepositorioCuenta repositorioCuenta;
    RepositorioBanco repositorioBanco;

    ServicioCuenta servicioCuenta;

    @BeforeEach
    void setUp() {
        repositorioCuenta = mock(RepositorioCuenta.class);
        repositorioBanco = mock(RepositorioBanco.class);
        servicioCuenta = new ServicioCuentaImpl(repositorioCuenta, repositorioBanco);
    }

    @Test
    void contextLoads() {
        when(repositorioCuenta.findById(1L)).thenReturn(Datos.CUENTA_001);
        when(repositorioCuenta.findById(2L)).thenReturn(Datos.CUENTA_002);
        when(repositorioBanco.findById(1L)).thenReturn(Datos.BANCO);

        // verifico los saldos originales
        BigDecimal saldoOrigen = servicioCuenta.revisarSaldo(1L);
        BigDecimal saldoDestino = servicioCuenta.revisarSaldo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        // transfiero 100 de la cuenta 1 a la cuenta 2
        servicioCuenta.transferir(1L, 2L, new BigDecimal("100"), 1L);

        // verifico los saldos actualizados después de la transferencia
        saldoOrigen = servicioCuenta.revisarSaldo(1L);
        saldoDestino = servicioCuenta.revisarSaldo(2L);

        assertEquals("900", saldoOrigen.toPlainString());
        assertEquals("2100", saldoDestino.toPlainString());
    }
}
