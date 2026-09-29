package com.librerianova.util;

import com.librerianova.modelo.Producto;
import java.math.BigDecimal;

public class DetalleVenta {

    private final Producto producto;
    private final int cantidad;

    public DetalleVenta(
            Producto producto,
            int cantidad) {

        if (producto == null) {

            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        if (cantidad <= 0) {

            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }

        this.producto = producto;
        this.cantidad = cantidad;
    }

    public BigDecimal calcularSubtotal() {

        return BigDecimal
                .valueOf(producto.calcularPrecioVenta())
                .multiply(
                        BigDecimal.valueOf(cantidad)
                );
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {

        return producto.getNombre()
                + " x " + cantidad
                + " = S/ "
                + String.format(
                        "%.2f",
                        calcularSubtotal()
                );
    }
}