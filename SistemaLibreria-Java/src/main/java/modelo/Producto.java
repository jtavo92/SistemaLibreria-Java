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