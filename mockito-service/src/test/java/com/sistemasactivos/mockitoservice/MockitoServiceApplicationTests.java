package com.sistemasactivos.mockitoservice;

import com.sistemasactivos.mockitoservice.excepciones.ExcepcionDineroInsuficiente;
import com.sistemasactivos.mockitoservice.modelos.Banco;
import com.sistemasactivos.mockitoservice.modelos.Cuenta;
import com.sistemasactivos.mockitoservice.repositorios.RepositorioBanco;
import com.sistemasactivos.mockitoservice.repositorios.RepositorioCuenta;
import com.sistemasactivos.mockitoservice.servicios.ServicioCuenta;
import com.sistemasactivos.mockitoservice.servicios.ServicioCuentaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    void contextLoads1() {
        when(repositorioCuenta.findById(1L)).thenReturn(Datos.crearCuenta001());
        when(repositorioCuenta.findById(2L)).thenReturn(Datos.crearCuenta002());
        when(repositorioBanco.findById(1L)).thenReturn(Datos.crearBanco());

        // verifico los saldos originales
        BigDecimal saldoOrigen = servicioCuenta.revisarSaldo( 1L);
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

        // verifico el total de transferencias
        int total = servicioCuenta.revisarTotalTransferencias(1L);
        assertEquals(1, total);

        // verifico si se han llamado y cuantas veces los métodos de los mocks
        verify(repositorioCuenta, times(3)).findById(1L);
        verify(repositorioCuenta, times(3)).findById(2L);
        verify(repositorioCuenta, times(2)).update(any(Cuenta.class));

        verify(repositorioBanco, times(2)).findById(1L);
        verify(repositorioBanco).update(any(Banco.class));
    }

    @Test
    void contextLoads2() {
        when(repositorioCuenta.findById(1L)).thenReturn(Datos.crearCuenta001());
        when(repositorioCuenta.findById(2L)).thenReturn(Datos.crearCuenta002());
        when(repositorioBanco.findById(1L)).thenReturn(Datos.crearBanco());

        // verifico los saldos originales
        BigDecimal saldoOrigen = servicioCuenta.revisarSaldo(1L);
        BigDecimal saldoDestino = servicioCuenta.revisarSaldo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        // transfiero 1200 de la cuenta 1 a la cuenta 2 (debe fallar)
        assertThrows(ExcepcionDineroInsuficiente.class, () -> {
            servicioCuenta.transferir(1L, 2L, new BigDecimal("1200"), 1L);
        });

        // verifico los saldos actualizados después de la transferencia
        saldoOrigen = servicioCuenta.revisarSaldo(1L);
        saldoDestino = servicioCuenta.revisarSaldo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        // verifico el total de transferencias
        int total = servicioCuenta.revisarTotalTransferencias(1L);
        assertEquals(0, total);

        // verifico si se han llamado y cuantas veces los métodos de los mocks
        verify(repositorioCuenta, times(3)).findById(1L);
        verify(repositorioCuenta, times(2)).findById(2L);
        verify(repositorioCuenta, never()).update(any(Cuenta.class));

        verify(repositorioBanco, times(1)).findById(1L);
        verify(repositorioBanco, never()).update(any(Banco.class));
    }
}
