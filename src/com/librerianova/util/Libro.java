package com.librerianova.util;

import com.librerianova.modelo.Producto;
import java.math.BigDecimal;

public class Libro extends Producto {

    private String titulo;
    private String autor;

    public Libro(
            int id,
            String titulo,
            String autor,
            BigDecimal precio,
            int stock) {

        super(
                id,
                titulo,
                precio.doubleValue(),
                stock
        );

        this.titulo = titulo;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setTitulo(String titulo) {

        this.titulo = titulo;
        super.setNombre(titulo);
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setPrecio(BigDecimal precio) {

        super.setPrecio(precio.doubleValue());
    }

    @Override
    public double calcularPrecioVenta() {

        return getPrecio().doubleValue();
    }

    @Override
    public void mostrarDatos() {

        System.out.println(
                "ID: " + getId()
                + " | Titulo: " + titulo
                + " | Autor: " + autor
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

        return "Libro{"
                + "id=" + getId()
                + ", titulo='" + titulo + '\''
                + ", autor='" + autor + '\''
                + ", precio=" + getPrecio()
                + ", stock=" + getStock()
                + '}';
    }
}