package modelo;

public class Pedido {

    private int id;
    private Cliente cliente;
    private Producto producto;
    private int cantidad;

    public Pedido(int id, Cliente cliente, Producto producto, int cantidad) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public void mostrarDatos() {
        System.out.println("ID Pedido: " + id);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Producto: " + producto.getNombre());
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Subtotal: S/ " + calcularSubtotal());
    }
}