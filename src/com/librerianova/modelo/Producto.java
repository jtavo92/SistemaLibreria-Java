package com.librerianova.modelo;

import com.librerianova.util.Vendible;
import java.math.BigDecimal;

public abstract class Producto implements Vendible {

    private final int id;
    private String nombre;
    private BigDecimal precio;
    private int stock;

    public Producto(
            int id,
            String nombre,
            double precio,
            int stock) {

        this.id = id;
        this.nombre = nombre;
        this.precio = BigDecimal.valueOf(precio);

        setStock(stock);
    }

    // SOBRECARGA
    public Producto(
            String nombre,
            double precio) {

        this(0, nombre, precio, 0);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = BigDecimal.valueOf(precio);
    }

    // ==============================
    // STOCK
    // ==============================

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo."
            );
        }

        this.stock = stock;
    }

    public boolean tieneStock() {
        return stock > 0;
    }

    @Override
    public double calcularPrecioVenta() {

        return precio.doubleValue();
    }

    // METODO ABSTRACTO
    public abstract void mostrarDatos();
}