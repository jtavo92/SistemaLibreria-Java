package modelo;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {

        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Constructor compatible con el Menu actual
    public Producto(int id, String nombre, double precio) {
        this(id, nombre, precio, 0);
    }

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void mostrarDatos() {
        System.out.println("ID: " + id);
        System.out.println("Producto: " + nombre);
        System.out.println("Precio: S/ " + precio);
        System.out.println("Stock: " + stock);
    }
}
