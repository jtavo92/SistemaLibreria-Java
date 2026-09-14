package modelo;

public class Producto {

    private final int id;
    private final String nombre;
    private final double precio;
    private final String categoria;

    public Producto(String nombre, double precio) {
        this(1, nombre, precio, "General");
    }

    public Producto(int id, String nombre, double precio) {
        this(id, nombre, precio, "General");
    }

    public Producto(int id, String nombre, double precio, String categoria) {
        if (id <= 0) {
            throw new IllegalArgumentException("el ID debe ser mayor que cero.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre no puede estar vacio.");
        }
        if (!Double.isFinite(precio) || precio <= 0) {
            throw new IllegalArgumentException("el precio debe ser mayor que cero.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.precio = precio;
        this.categoria = categoria == null || categoria.trim().isEmpty()
                ? "General" : categoria.trim();
    }

    public double calcularPrecioFinal() {
        return precio;
    }

    public double calcularPrecioFinal(double porcentajeDescuento) {
        if (!Double.isFinite(porcentajeDescuento)
                || porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("el descuento debe estar entre 0 y 100.");
        }
        return precio - precio * porcentajeDescuento / 100.0;
    }

    public double calcularPrecioFinal(double porcentajeDescuento, double costoEnvio) {
        if (!Double.isFinite(costoEnvio) || costoEnvio < 0) {
            throw new IllegalArgumentException("el costo de envio no puede ser negativo.");
        }
        return calcularPrecioFinal(porcentajeDescuento) + costoEnvio;
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

    public String getCategoria() {
        return categoria;
    }

    public void mostrarDatos() {
        System.out.printf("Producto: %s%nPrecio: S/ %.2f%n", nombre, precio);
    }
}

