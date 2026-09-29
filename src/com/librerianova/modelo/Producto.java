package com.librerianova.modelo;

import com.librerianova.util.Validacion;

public class Producto {

    private int id;
    private String nombre;
    private double precio;

    // Constructor para productos nuevos
    public Producto(int id, String nombre, double precio) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }

        if (!Validacion.textoValido(nombre)) {
            throw new IllegalArgumentException(
                    "El nombre del producto no puede estar vacío."
            );
        }

        if (!Validacion.precioValido(precio)) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Constructor anterior para no romper otras partes del proyecto
    public Producto(String nombre, double precio) {

        if (!Validacion.textoValido(nombre)) {
            throw new IllegalArgumentException(
                    "El nombre del producto no puede estar vacio."
            );
        }

        if (!Validacion.precioValido(precio)) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que 0."
            );
        }

        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void mostrarDatos() {

        System.out.println("ID: " + id);
        System.out.println("Producto: " + nombre);
        System.out.printf("Precio: S/ %.2f%n", precio);
    }
}
