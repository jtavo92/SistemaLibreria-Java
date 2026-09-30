package com.librerianova.modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorProductos {

    private final ArrayList<Producto> productos;

    public GestorProductos() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {

        if (producto == null) {

            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        productos.add(producto);
    }

    public Producto buscarPorId(int id) {

        for (Producto producto : productos) {

            if (producto.getId() == id) {
                return producto;
            }
        }

        return null;
    }

    public boolean eliminarPorId(int id) {

        Producto producto = buscarPorId(id);

        if (producto != null) {
            return productos.remove(producto);
        }

        return false;
    }

    public void listarProductos() {

        if (productos.isEmpty()) {

            System.out.println(
                    "No hay productos registrados."
            );

            return;
        }

        for (Producto producto : productos) {

            // POLIMORFISMO
            producto.mostrarDatos();
        }
    }

    public int cantidadProductos() {
        return productos.size();
    }

    public List<Producto> getProductos() {
        return productos;
    }
}