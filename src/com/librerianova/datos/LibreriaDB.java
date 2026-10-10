package com.librerianova.datos;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Operaciones de persistencia del módulo gráfico, con parámetros SQL y transacciones. */
public final class LibreriaDB {
    private static final String URL = "jdbc:h2:file:./datos/libreria;DB_CLOSE_ON_EXIT=FALSE";
    private LibreriaDB() {}
    private static Connection abrir() throws SQLException { return DriverManager.getConnection(URL, "sa", ""); }

    public static void iniciar() throws SQLException {
        new java.io.File("datos").mkdirs();
        try (Connection c = abrir(); Statement s = c.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS clientes (id IDENTITY PRIMARY KEY, dni VARCHAR(8) NOT NULL UNIQUE, nombre VARCHAR(120) NOT NULL, correo VARCHAR(160) NOT NULL, telefono VARCHAR(9) NOT NULL)");
            s.execute("CREATE TABLE IF NOT EXISTS productos (id IDENTITY PRIMARY KEY, nombre VARCHAR(140) NOT NULL, tipo VARCHAR(30) NOT NULL, detalle VARCHAR(140) NOT NULL, precio DECIMAL(12,2) NOT NULL CHECK (precio > 0), stock INT NOT NULL CHECK (stock >= 0))");
            s.execute("CREATE TABLE IF NOT EXISTS ventas (id IDENTITY PRIMARY KEY, cliente_id BIGINT NOT NULL REFERENCES clientes(id), fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, total DECIMAL(12,2) NOT NULL)");
            s.execute("CREATE TABLE IF NOT EXISTS detalle_venta (id IDENTITY PRIMARY KEY, venta_id BIGINT NOT NULL REFERENCES ventas(id), producto_id BIGINT NOT NULL REFERENCES productos(id), cantidad INT NOT NULL CHECK (cantidad > 0), precio_unitario DECIMAL(12,2) NOT NULL, subtotal DECIMAL(12,2) NOT NULL)");
        }
    }
    private static List<Object[]> consultar(String sql) throws SQLException {
        try (Connection c = abrir(); PreparedStatement p = c.prepareStatement(sql); ResultSet r = p.executeQuery()) {
            List<Object[]> out = new ArrayList<>();
            int n = r.getMetaData().getColumnCount();
            while (r.next()) { Object[] v = new Object[n]; for(int i=0;i<n;i++)v[i]=r.getObject(i+1); out.add(v); }
            return out;
        }
    }
    public static List<Object[]> clientes() throws SQLException { return consultar("SELECT id,dni,nombre,correo,telefono FROM clientes ORDER BY id DESC"); }
    public static List<Object[]> productos() throws SQLException { return consultar("SELECT id,nombre,tipo,detalle,precio,stock FROM productos ORDER BY id DESC"); }
    public static List<Object[]> ventas() throws SQLException { return consultar("SELECT v.id,c.nombre,FORMATDATETIME(v.fecha,'dd/MM/yyyy HH:mm'),v.total FROM ventas v JOIN clientes c ON c.id=v.cliente_id ORDER BY v.id DESC"); }
    public static List<Object[]> resumen() throws SQLException { return consultar("SELECT (SELECT COUNT(*) FROM clientes),(SELECT COUNT(*) FROM productos),(SELECT COUNT(*) FROM ventas),(SELECT COALESCE(SUM(total),0) FROM ventas)"); }
    public static List<Object[]> detalle(long ventaId) throws SQLException {
        try(Connection c=abrir(); PreparedStatement p=c.prepareStatement("SELECT p.nombre,d.cantidad,d.precio_unitario,d.subtotal FROM detalle_venta d JOIN productos p ON p.id=d.producto_id WHERE d.venta_id=?")) {
            p.setLong(1,ventaId); try(ResultSet r=p.executeQuery()){ List<Object[]> rows=new ArrayList<>(); while(r.next()) rows.add(new Object[]{r.getString(1),r.getInt(2),r.getBigDecimal(3),r.getBigDecimal(4)}); return rows; }
        }
    }
    public static void guardarCliente(Long id,String dni,String nombre,String correo,String telefono) throws SQLException {
        String sql=id==null?"INSERT INTO clientes(dni,nombre,correo,telefono) VALUES(?,?,?,?)":"UPDATE clientes SET dni=?,nombre=?,correo=?,telefono=? WHERE id=?";
        try(Connection c=abrir(); PreparedStatement p=c.prepareStatement(sql)) {p.setString(1,dni);p.setString(2,nombre);p.setString(3,correo);p.setString(4,telefono);if(id!=null)p.setLong(5,id);p.executeUpdate();}
    }
    public static void borrarCliente(long id) throws SQLException {
        try(Connection c=abrir(); PreparedStatement p=c.prepareStatement("DELETE FROM clientes WHERE id=?")){p.setLong(1,id);p.executeUpdate();}
    }
    public static void guardarProducto(Long id,String nombre,String tipo,String detalle,BigDecimal precio,int stock) throws SQLException {
        String sql=id==null?"INSERT INTO productos(nombre,tipo,detalle,precio,stock) VALUES(?,?,?,?,?)":"UPDATE productos SET nombre=?,tipo=?,detalle=?,precio=?,stock=? WHERE id=?";
        try(Connection c=abrir(); PreparedStatement p=c.prepareStatement(sql)) {p.setString(1,nombre);p.setString(2,tipo);p.setString(3,detalle);p.setBigDecimal(4,precio);p.setInt(5,stock);if(id!=null)p.setLong(6,id);p.executeUpdate();}
    }
    public static void borrarProducto(long id) throws SQLException {
        try(Connection c=abrir(); PreparedStatement p=c.prepareStatement("DELETE FROM productos WHERE id=?")){p.setLong(1,id);p.executeUpdate();}
    }
    /** Venta y descuento de stock, todo confirmado o todo revertido. */
    public static long registrarVenta(long clienteId, long productoId, int cantidad) throws SQLException {
        try(Connection c=abrir()) {
            c.setAutoCommit(false);
            try {
                BigDecimal precio; int stock;
                try(PreparedStatement p=c.prepareStatement("SELECT precio,stock FROM productos WHERE id=? FOR UPDATE")){
                    p.setLong(1,productoId);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("El producto ya no existe.");precio=r.getBigDecimal(1);stock=r.getInt(2);}}
                if(cantidad<=0) throw new SQLException("La cantidad debe ser positiva.");
                if(stock<cantidad) throw new SQLException("Stock insuficiente. Disponible: "+stock);
                try(PreparedStatement p=c.prepareStatement("UPDATE productos SET stock=stock-? WHERE id=?")){p.setInt(1,cantidad);p.setLong(2,productoId);p.executeUpdate();}
                BigDecimal total=precio.multiply(BigDecimal.valueOf(cantidad));long id;
                try(PreparedStatement p=c.prepareStatement("INSERT INTO ventas(cliente_id,total) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS)){
                    p.setLong(1,clienteId);p.setBigDecimal(2,total);p.executeUpdate();try(ResultSet r=p.getGeneratedKeys()){r.next();id=r.getLong(1);}}
                try(PreparedStatement p=c.prepareStatement("INSERT INTO detalle_venta(venta_id,producto_id,cantidad,precio_unitario,subtotal) VALUES(?,?,?,?,?)")){
                    p.setLong(1,id);p.setLong(2,productoId);p.setInt(3,cantidad);p.setBigDecimal(4,precio);p.setBigDecimal(5,total);p.executeUpdate();}
                c.commit();return id;
            } catch(SQLException | RuntimeException ex){c.rollback();throw ex;}
        }
    }
}
