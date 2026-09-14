/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ADRIANO
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String categoria;

    public Producto(int id, String nombre, double precio) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.precio = precio;
        this.categoria = "General";
    }

    public Producto(int id, String nombre, double precio, String categoria) {
        this(id, nombre, precio);
        this.categoria = (categoria != null && !categoria.trim().isEmpty()) ? categoria.trim() : "General";
    }

    public double calcularPrecioFinal() {
        return this.precio;
    }

    public double calcularPrecioFinal(double porcentajeDescuento) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        return this.precio - (this.precio * (porcentajeDescuento / 100.0));
    }

    public double calcularPrecioFinal(double porcentajeDescuento, double costoEnvio) {
        if (costoEnvio < 0) {
            throw new IllegalArgumentException("El costo de envío no puede ser negativo.");
        }
        return calcularPrecioFinal(porcentajeDescuento) + costoEnvio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
}
=======
package modelo;

public class Producto {

    private final String nombre;
    private final double precio;

    public Producto(String nombre, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre no puede estar vacio.");
        }
        if (!Double.isFinite(precio) || precio <= 0) {
            throw new IllegalArgumentException("el precio debe ser mayor que cero.");
        }
        this.nombre = nombre.trim();
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void mostrarDatos() {
        System.out.printf("Producto: %s%nPrecio: S/ %.2f%n", nombre, precio);
    }
}

