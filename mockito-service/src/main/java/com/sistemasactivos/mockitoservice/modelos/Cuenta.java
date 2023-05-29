package com.sistemasactivos.mockitoservice.modelos;

import com.sistemasactivos.mockitoservice.excepciones.ExcepcionDineroInsuficiente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Cuenta {
    private Long id;
    private String persona;
    private BigDecimal saldo;

    public Cuenta(Long id, String persona, BigDecimal saldo) {
        this.id = id;
        this.persona = persona;
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto) {
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0)
            throw new ExcepcionDineroInsuficiente("Dinero insuficiente en la cuenta");

        this.saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto) {
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(id, cuenta.id) && Objects.equals(persona, cuenta.persona) && Objects.equals(saldo, cuenta.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, persona, saldo);
    }
}
