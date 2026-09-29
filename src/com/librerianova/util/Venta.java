package com.librerianova.util;

import com.librerianova.modelo.Cliente;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Venta {

    private static final BigDecimal IGV =
            new BigDecimal("0.18");

    private static final int MAX_ITEMS = 20;

    private final int id;
    private final Cliente cliente;
    private final List<DetalleVenta> detalles;
    private final LocalDateTime fecha;

    public Venta(int id, Cliente cliente) {

        if (cliente == null) {

            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        this.id = id;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
        this.fecha = LocalDateTime.now();
    }

    public void agregarDetalle(
            DetalleVenta detalle) {

        if (detalle == null) {

            throw new IllegalArgumentException(
                    "El detalle no puede ser nulo."
            );
        }

        if (detalles.size() >= MAX_ITEMS) {

            throw new IllegalStateException(
                    "Se alcanzo el maximo de items por pedido."
            );
        }

        detalles.add(detalle);
    }

    public BigDecimal calcularSubtotal() {

        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleVenta detalle : detalles) {

            subtotal = subtotal.add(
                    detalle.calcularSubtotal()
            );
        }

        return subtotal;
    }

    public BigDecimal calcularIGV() {

        return calcularSubtotal().multiply(IGV);
    }

    public BigDecimal calcularTotal() {

        return calcularSubtotal().add(
                calcularIGV()
        );
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<DetalleVenta> getDetalles() {

        return Collections.unmodifiableList(
                detalles
        );
    }

    public void mostrarResumen() {

        System.out.println();
        System.out.println("========== DETALLE DEL PEDIDO ==========");
        System.out.println("Pedido: " + id);

        System.out.println(
                "Fecha: "
                + fecha.format(
                        DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy HH:mm"
                        )
                )
        );

        System.out.println(
                "Cliente: "
                + cliente.getNombre()
        );

        System.out.println("----------------------------------------");

        for (DetalleVenta detalle : detalles) {

            System.out.println(
                    "  - " + detalle
            );
        }

        System.out.println("----------------------------------------");

        System.out.println(
                "Subtotal: S/ "
                + String.format(
                        "%.2f",
                        calcularSubtotal()
                )
        );

        System.out.println(
                "IGV: S/ "
                + String.format(
                        "%.2f",
                        calcularIGV()
                )
        );

        System.out.println(
                "Total: S/ "
                + String.format(
                        "%.2f",
                        calcularTotal()
                )
        );

        System.out.println(
                "========================================"
        );
    }
}