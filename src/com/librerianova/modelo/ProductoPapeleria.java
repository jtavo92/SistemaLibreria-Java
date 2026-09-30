package com.librerianova.modelo;

public class ProductoPapeleria extends Producto {

    private String categoria;

    public ProductoPapeleria(
            int id,
            String nombre,
            double precio,
            String categoria,
            int stock) {

        super(id, nombre, precio, stock);

        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public void mostrarDatos() {

        System.out.println(
                "ID: " + getId()
                + " | Producto: " + getNombre()
                + " | Categoria: " + categoria
                + " | Precio: S/ "
                + String.format(
                        "%.2f",
                        getPrecio().doubleValue()
                )
                + " | Stock: " + getStock()
        );
    }

    @Override
    public String toString() {

        return "ProductoPapeleria{"
                + "id=" + getId()
                + ", nombre='" + getNombre() + '\''
                + ", categoria='" + categoria + '\''
                + ", precio=" + getPrecio()
                + ", stock=" + getStock()
                + '}';
    }
}