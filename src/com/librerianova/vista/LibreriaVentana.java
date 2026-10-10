package com.librerianova.vista;

import com.librerianova.datos.LibreriaDB;
import com.librerianova.util.Validacion;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/** Ventana de escritorio sin dependencias gráficas externas. */
public class LibreriaVentana extends JFrame {
    private final Color azul=new Color(31,54,82), fondo=new Color(245,247,250);
    private final DefaultTableModel mc=modelo("ID","DNI","Nombre","Correo","Teléfono");
    private final DefaultTableModel mp=modelo("ID","Producto","Tipo","Detalle","Precio S/","Stock");
    private final DefaultTableModel mv=modelo("N° venta","Cliente","Fecha","Total S/");
    private final JTable tc=new JTable(mc),tp=new JTable(mp),tv=new JTable(mv);
    private final JTextField dni=new JTextField(12),nombreCliente=new JTextField(19),correo=new JTextField(19),telefono=new JTextField(12);
    private final JTextField nombreProducto=new JTextField(19),detalle=new JTextField(19),precio=new JTextField(10),stock=new JTextField(7);
    private final JComboBox<String> tipo=new JComboBox<>(new String[]{"Libro","Papelería"});
    private final JComboBox<Item> comboCliente=new JComboBox<>(),comboProducto=new JComboBox<>();
    private final JSpinner cantidad=new JSpinner(new SpinnerNumberModel(1,1,9999,1));
    private final JLabel estadisticas=new JLabel(""), pie=new JLabel("Base de datos local · H2");
    private Long editCliente=null,editProducto=null;

    public LibreriaVentana() throws SQLException {
        super("Librería Nova  |  Gestión comercial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setMinimumSize(new Dimension(850,620));setSize(1080,710);setLocationRelativeTo(null);
        getContentPane().setBackground(fondo);setLayout(new BorderLayout());
        JLabel titulo=new JLabel("  LIBRERÍA NOVA    /    Gestión de inventario y ventas");
        titulo.setOpaque(true);titulo.setBackground(azul);titulo.setForeground(Color.WHITE);titulo.setFont(new Font("SansSerif",Font.BOLD,18));titulo.setPreferredSize(new Dimension(100,65));add(titulo,BorderLayout.NORTH);
        JTabbedPane tabs=new JTabbedPane(); tabs.setFont(new Font("SansSerif",Font.PLAIN,15));
        tabs.add("Clientes",crearClientes());tabs.add("Productos",crearProductos());tabs.add("Ventas",crearVentas());tabs.add("Reportes",crearReportes());add(tabs,BorderLayout.CENTER);
        pie.setBorder(BorderFactory.createEmptyBorder(8,15,8,10));add(pie,BorderLayout.SOUTH);
        refrescar();
    }
    private static DefaultTableModel modelo(String... columnas){return new DefaultTableModel(columnas,0){@Override public boolean isCellEditable(int r,int c){return false;}};}
    private JPanel panel(){JPanel p=new JPanel(new BorderLayout(12,12));p.setBackground(new Color(245,247,250));p.setBorder(BorderFactory.createEmptyBorder(18,22,15,22));return p;}
    private JPanel formulario(){JPanel p=new JPanel(new FlowLayout(FlowLayout.LEFT,12,8));p.setBackground(Color.WHITE);p.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));return p;}
    private static void campo(JPanel p,String etiqueta,JComponent c){JPanel q=new JPanel(new BorderLayout(2,5));q.setBackground(Color.WHITE);q.add(new JLabel(etiqueta),BorderLayout.NORTH);q.add(c,BorderLayout.CENTER);p.add(q);}
    private JButton boton(String texto,Runnable accion){JButton b=new JButton(texto);b.setFocusPainted(false);b.addActionListener(e->accion.run());return b;}
    private JScrollPane tabla(JTable t){t.setRowHeight(29);t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);t.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,13));return new JScrollPane(t);}
    private JPanel crearClientes(){JPanel p=panel(),arr=new JPanel(new BorderLayout());JPanel f=formulario();
        campo(f,"DNI (8 dígitos)",dni);campo(f,"Nombre y apellidos",nombreCliente);campo(f,"Correo",correo);campo(f,"Teléfono (9 dígitos)",telefono);arr.add(f,BorderLayout.CENTER);
        JPanel acciones=new JPanel(new FlowLayout(FlowLayout.LEFT));acciones.add(boton("Guardar cliente",()->accion(this::guardarCliente)));acciones.add(boton("Nuevo / limpiar",this::limpiarCliente));acciones.add(boton("Editar seleccionado",this::editarCliente));acciones.add(boton("Eliminar",()->accion(this::eliminarCliente)));arr.add(acciones,BorderLayout.SOUTH);
        p.add(arr,BorderLayout.NORTH);p.add(tabla(tc),BorderLayout.CENTER);return p;}
    private JPanel crearProductos(){JPanel p=panel(),arr=new JPanel(new BorderLayout());JPanel f=formulario();campo(f,"Producto",nombreProducto);campo(f,"Tipo",tipo);campo(f,"Autor / categoría",detalle);campo(f,"Precio (S/)",precio);campo(f,"Stock",stock);arr.add(f,BorderLayout.CENTER);
        JPanel acciones=new JPanel(new FlowLayout(FlowLayout.LEFT));acciones.add(boton("Guardar producto",()->accion(this::guardarProducto)));acciones.add(boton("Nuevo / limpiar",this::limpiarProducto));acciones.add(boton("Editar seleccionado",this::editarProducto));acciones.add(boton("Eliminar",()->accion(this::eliminarProducto)));arr.add(acciones,BorderLayout.SOUTH);p.add(arr,BorderLayout.NORTH);p.add(tabla(tp),BorderLayout.CENTER);return p;}
    private JPanel crearVentas(){JPanel p=panel(),n=new JPanel(new BorderLayout());JPanel f=formulario();campo(f,"Cliente",comboCliente);campo(f,"Producto",comboProducto);campo(f,"Cantidad",cantidad);n.add(f,BorderLayout.CENTER);
        JPanel acciones=new JPanel(new FlowLayout(FlowLayout.LEFT));acciones.add(boton("Registrar venta",()->accion(this::guardarVenta)));acciones.add(boton("Ver detalle",()->accion(this::verDetalle)));acciones.add(new JLabel("  El precio registrado se considera precio final (incluye impuestos, si corresponde)."));n.add(acciones,BorderLayout.SOUTH);p.add(n,BorderLayout.NORTH);p.add(tabla(tv),BorderLayout.CENTER);return p;}
    private JPanel crearReportes(){JPanel p=panel();JPanel a=new JPanel(new BorderLayout());estadisticas.setFont(new Font("SansSerif",Font.BOLD,18));estadisticas.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));a.add(estadisticas,BorderLayout.NORTH);
        JTextArea ayuda=new JTextArea("RESUMEN DE OPERACIONES\n\n• Los clientes y productos se guardan en la base H2 ubicada en datos/libreria.mv.db.\n• Cada venta registra el cliente, producto, cantidad, precio y fecha.\n• El inventario disminuye automáticamente después de una venta confirmada.\n• Los datos permanecen disponibles al cerrar y volver a abrir el programa.\n\nPara consultar el historial completo utiliza la pestaña Ventas.");ayuda.setEditable(false);ayuda.setOpaque(false);ayuda.setFont(new Font("SansSerif",Font.PLAIN,16));ayuda.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));a.add(ayuda,BorderLayout.CENTER);p.add(a,BorderLayout.CENTER);p.add(boton("Actualizar resumen",()->accion(this::refrescar)),BorderLayout.SOUTH);return p;}
    private void accion(Trabajo t){try{t.ejecutar();}catch(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"No se pudo completar",JOptionPane.ERROR_MESSAGE);}}
    @FunctionalInterface private interface Trabajo{void ejecutar()throws Exception;}
    private void comprobar(boolean ok,String mensaje){if(!ok)throw new IllegalArgumentException(mensaje);}
    private static String valor(JTextField t){return t.getText().trim();}
    private void guardarCliente()throws SQLException {String d=valor(dni),n=valor(nombreCliente),c=valor(correo),t=valor(telefono);
        comprobar(Validacion.dniValido(d),"El DNI debe tener exactamente 8 dígitos.");comprobar(Validacion.textoValido(n),"Ingresa el nombre.");comprobar(Validacion.correoValido(c),"El correo no es válido.");comprobar(Validacion.telefonoValido(t),"El teléfono debe tener 9 dígitos.");
        LibreriaDB.guardarCliente(editCliente,d,n,c,t);limpiarCliente();refrescar();aviso("Cliente guardado.");}
    private void editarCliente(){int r=tc.getSelectedRow();if(r<0){aviso("Selecciona un cliente en la tabla.");return;}editCliente=((Number)tc.getValueAt(r,0)).longValue();dni.setText(tc.getValueAt(r,1).toString());nombreCliente.setText(tc.getValueAt(r,2).toString());correo.setText(tc.getValueAt(r,3).toString());telefono.setText(tc.getValueAt(r,4).toString());}
    private void eliminarCliente()throws SQLException {int r=tc.getSelectedRow();if(r<0){aviso("Selecciona un cliente.");return;}if(confirmar("¿Eliminar el cliente seleccionado?")){LibreriaDB.borrarCliente(((Number)tc.getValueAt(r,0)).longValue());limpiarCliente();refrescar();}}
    private void limpiarCliente(){editCliente=null;dni.setText("");nombreCliente.setText("");correo.setText("");telefono.setText("");}
    private void guardarProducto()throws SQLException {String n=valor(nombreProducto),d=valor(detalle);comprobar(!n.isEmpty(),"Ingresa el nombre del producto.");comprobar(!d.isEmpty(),"Ingresa el autor o categoría.");BigDecimal pr;int st;
        try{pr=new BigDecimal(valor(precio)).setScale(2,java.math.RoundingMode.HALF_UP);st=Integer.parseInt(valor(stock));}catch(NumberFormatException e){throw new IllegalArgumentException("Revisa el precio y stock (utiliza punto decimal).");}
        comprobar(pr.signum()>0&&pr.compareTo(new BigDecimal("100000"))<=0,"El precio debe ser mayor que cero y hasta 100000.");comprobar(st>=0,"El stock no puede ser negativo.");
        LibreriaDB.guardarProducto(editProducto,n,tipo.getSelectedItem().toString(),d,pr,st);limpiarProducto();refrescar();aviso("Producto guardado.");}
    private void editarProducto(){int r=tp.getSelectedRow();if(r<0){aviso("Selecciona un producto.");return;}editProducto=((Number)tp.getValueAt(r,0)).longValue();nombreProducto.setText(tp.getValueAt(r,1).toString());tipo.setSelectedItem(tp.getValueAt(r,2).toString());detalle.setText(tp.getValueAt(r,3).toString());precio.setText(tp.getValueAt(r,4).toString());stock.setText(tp.getValueAt(r,5).toString());}
    private void eliminarProducto()throws SQLException {int r=tp.getSelectedRow();if(r<0){aviso("Selecciona un producto.");return;}if(confirmar("¿Eliminar el producto seleccionado?")){LibreriaDB.borrarProducto(((Number)tp.getValueAt(r,0)).longValue());limpiarProducto();refrescar();}}
    private void limpiarProducto(){editProducto=null;nombreProducto.setText("");detalle.setText("");precio.setText("");stock.setText("");}
    private void guardarVenta()throws SQLException {Item c=(Item)comboCliente.getSelectedItem(),p=(Item)comboProducto.getSelectedItem();comprobar(c!=null&&p!=null,"Registra un cliente y un producto antes de vender.");long id=LibreriaDB.registrarVenta(c.id,p.id,(Integer)cantidad.getValue());refrescar();aviso("Venta N° "+id+" guardada correctamente.");}
    private void verDetalle()throws SQLException {int r=tv.getSelectedRow();if(r<0){aviso("Selecciona una venta.");return;}long id=((Number)tv.getValueAt(r,0)).longValue();StringBuilder s=new StringBuilder("VENTA N° "+id+"\n\n");for(Object[] fila:LibreriaDB.detalle(id)){s.append(fila[0]).append("  × ").append(fila[1]).append("  |  S/ ").append(fila[3]).append("\n");}JOptionPane.showMessageDialog(this,s.toString(),"Detalle de venta",JOptionPane.INFORMATION_MESSAGE);}
    private void refrescar()throws SQLException {cargar(mc,LibreriaDB.clientes());cargar(mp,LibreriaDB.productos());cargar(mv,LibreriaDB.ventas());comboCliente.removeAllItems();comboProducto.removeAllItems();for(Object[] r:LibreriaDB.clientes())comboCliente.addItem(new Item(((Number)r[0]).longValue(),r[2]+" · DNI "+r[1]));for(Object[] r:LibreriaDB.productos())comboProducto.addItem(new Item(((Number)r[0]).longValue(),r[1]+" · S/ "+r[4]+" · Stock "+r[5]));Object[] x=LibreriaDB.resumen().get(0);estadisticas.setText("Clientes: "+x[0]+"    |    Productos: "+x[1]+"    |    Ventas: "+x[2]+"    |    Ingresos: S/ "+x[3]);}
    private static void cargar(DefaultTableModel m,List<Object[]> rows){m.setRowCount(0);for(Object[] r:rows)m.addRow(r);}
    private void aviso(String s){JOptionPane.showMessageDialog(this,s,"Librería Nova",JOptionPane.INFORMATION_MESSAGE);}
    private boolean confirmar(String s){return JOptionPane.showConfirmDialog(this,s,"Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;}
    private static class Item {final long id;final String texto;Item(long i,String t){id=i;texto=t;}@Override public String toString(){return texto;}}
}
