package com.librerianova.modelo;

import com.librerianova.util.ConexionH2;
import com.librerianova.util.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorProductos {

    public GestorProductos() {
        try (Connection conexion = ConexionH2.obtenerConexion();
                Statement sentencia = conexion.createStatement()) {

            sentencia.execute("""
                    CREATE TABLE IF NOT EXISTS productos (
                        id INT PRIMARY KEY,
                        tipo VARCHAR(20) NOT NULL,
                        nombre VARCHAR(200) NOT NULL,
                        precio DECIMAL(10, 2) NOT NULL,
                        stock INT NOT NULL,
                        autor VARCHAR(200),
                        categoria VARCHAR(100)
                    )
                    """);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo inicializar la base de datos de productos.", e);
        }
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo.");
        }

        String sql = """
                INSERT INTO productos
                    (tipo, nombre, precio, stock, autor, categoria, id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionH2.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            asignarParametros(sentencia, producto);
            sentencia.setInt(7, producto.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo guardar el producto.", e);
        }
    }

    public Producto buscarPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";

        try (Connection conexion = ConexionH2.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            try (ResultSet resultados = sentencia.executeQuery()) {
                return resultados.next() ? crearProducto(resultados) : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo buscar el producto.", e);
        }
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = """
                UPDATE productos
                SET tipo = ?, nombre = ?, precio = ?, stock = ?,
                    autor = ?, categoria = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionH2.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            asignarParametros(sentencia, producto);
            sentencia.setInt(7, producto.getId());
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo actualizar el producto.", e);
        }
    }

    public boolean eliminarPorId(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conexion = ConexionH2.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo eliminar el producto.", e);
        }
    }

    public void listarProductos() {
        List<Producto> productos = getProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (Producto producto : productos) {
            producto.mostrarDatos();
        }
    }

    public int cantidadProductos() {
        String sql = "SELECT COUNT(*) FROM productos";

        try (Connection conexion = ConexionH2.obtenerConexion();
                Statement sentencia = conexion.createStatement();
                ResultSet resultados = sentencia.executeQuery(sql)) {

            resultados.next();
            return resultados.getInt(1);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo contar los productos.", e);
        }
    }

    public List<Producto> getProductos() {
        String sql = "SELECT * FROM productos ORDER BY id";
        List<Producto> productos = new ArrayList<>();

        try (Connection conexion = ConexionH2.obtenerConexion();
                Statement sentencia = conexion.createStatement();
                ResultSet resultados = sentencia.executeQuery(sql)) {

            while (resultados.next()) {
                productos.add(crearProducto(resultados));
            }
            return productos;
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo listar los productos.", e);
        }
    }

    private void asignarParametros(
            PreparedStatement sentencia,
            Producto producto) throws SQLException {

        if (producto instanceof Libro libro) {
            sentencia.setString(1, "LIBRO");
            sentencia.setString(5, libro.getAutor());
            sentencia.setNull(6, java.sql.Types.VARCHAR);
        } else if (producto instanceof ProductoPapeleria papeleria) {
            sentencia.setString(1, "PAPELERIA");
            sentencia.setNull(5, java.sql.Types.VARCHAR);
            sentencia.setString(6, papeleria.getCategoria());
        } else {
            throw new IllegalArgumentException(
                    "Tipo de producto no soportado.");
        }

        sentencia.setString(2, producto.getNombre());
        sentencia.setBigDecimal(3, producto.getPrecio());
        sentencia.setInt(4, producto.getStock());
    }

    private Producto crearProducto(ResultSet resultados) throws SQLException {
        int id = resultados.getInt("id");
        String nombre = resultados.getString("nombre");
        double precio = resultados.getBigDecimal("precio").doubleValue();
        int stock = resultados.getInt("stock");

        return switch (resultados.getString("tipo")) {
            case "LIBRO" -> new Libro(
                    id,
                    nombre,
                    resultados.getString("autor"),
                    resultados.getBigDecimal("precio"),
                    stock);
            case "PAPELERIA" -> new ProductoPapeleria(
                    id,
                    nombre,
                    precio,
                    resultados.getString("categoria"),
                    stock);
            default -> throw new SQLException("Tipo de producto desconocido.");
        };
    }
}