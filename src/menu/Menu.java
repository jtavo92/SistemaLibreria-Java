package menu;

import com.librerianova.modelo.Cliente;
import com.librerianova.modelo.GestorClientes;
import com.librerianova.modelo.GestorProductos;
import com.librerianova.modelo.Producto;
import com.librerianova.modelo.ProductoPapeleria;

import com.librerianova.util.DetalleVenta;
import com.librerianova.util.Libro;
import com.librerianova.util.Validacion;
import com.librerianova.util.Venta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final GestorClientes gestorClientes =
            new GestorClientes();

    private static final GestorProductos gestorProductos =
            new GestorProductos();

    private static final List<Venta> ventas =
            new ArrayList<>();

    private static int siguienteIdCliente = 1;
    private static int siguienteIdProducto = 1;
    private static int siguienteIdVenta = 1;

    private static boolean datosCargados = false;

    // ========================================
    // MENU PRINCIPAL
    // ========================================

    public static void mostrarMenu() {

        cargarDatosIniciales();

        int opcion;

        do {

            mostrarEncabezado();

            System.out.println("1. PRODUCTOS");
            System.out.println("2. CLIENTES");
            System.out.println("3. PEDIDOS");
            System.out.println("4. REPORTES");
            System.out.println("5. INFORMACION");
            System.out.println("6. AYUDA");
            System.out.println("7. SALIR");

            System.out.println(
                    "----------------------------------------"
            );

            opcion = leerEntero(
                    "Seleccione una opcion: "
            );

            switch (opcion) {

                case 1 -> menuProductos();

                case 2 -> menuClientes();

                case 3 -> menuPedidos();

                case 4 -> menuReportes();

                case 5 -> mostrarInformacion();

                case 6 -> mostrarAyuda();

                case 7 -> despedida();

                default ->
                        System.out.println(
                                "Opcion no valida."
                        );
            }

        } while (opcion != 7);
    }

    // ========================================
    // ENCABEZADO
    // ========================================

    private static void mostrarEncabezado() {

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "          SISTEMA DE LIBRERIA"
        );

        System.out.println(
                "========================================"
        );
    }

    // ========================================
    // DATOS INICIALES
    // ========================================

    private static void cargarDatosIniciales() {

        if (datosCargados) {
            return;
        }

        datosCargados = true;

        // ====================================
        // LIBRO 1
        // ====================================

        gestorProductos.agregarProducto(
                new Libro(
                        siguienteIdProducto++,
                        "Java desde cero",
                        "Autor Demo",
                        new BigDecimal("45.90"),
                        10
                )
        );

        // ====================================
        // LIBRO 2
        // ====================================

        gestorProductos.agregarProducto(
                new Libro(
                        siguienteIdProducto++,
                        "Programacion POO",
                        "Autor Demo",
                        new BigDecimal("55.00"),
                        8
                )
        );

        // ====================================
        // PRODUCTO DE PAPELERIA
        // ====================================

        gestorProductos.agregarProducto(
                new ProductoPapeleria(
                        siguienteIdProducto++,
                        "Cuaderno A4",
                        12.50,
                        "Cuadernos",
                        5
                )
        );

        // ====================================
        // CLIENTE DEMO
        // ====================================

        Cliente clienteDemo = new Cliente(
                siguienteIdCliente++,
                "70000000",
                "Cliente Demo",
                "demo@correo.com",
                "999999999"
        );

        gestorClientes.agregarCliente(
                clienteDemo
        );
    }

    // ========================================
    // PRODUCTOS
    // ========================================

    private static void menuProductos() {

        int opcion;

        do {

            System.out.println();

            System.out.println(
                    "========== PRODUCTOS =========="
            );

            System.out.println(
                    "1. Registrar producto"
            );

            System.out.println(
                    "2. Listar productos"
            );

            System.out.println(
                    "3. Buscar producto"
            );

            System.out.println(
                    "4. Actualizar producto"
            );

            System.out.println(
                    "5. Eliminar producto"
            );

            System.out.println(
                    "6. Volver"
            );

            opcion = leerEntero(
                    "Seleccione una opcion: "
            );

            switch (opcion) {

                case 1 -> registrarProducto();

                case 2 -> mostrarProductos();

                case 3 -> buscarProducto();

                case 4 -> actualizarProducto();

                case 5 -> eliminarProducto();

                case 6 -> {
                }

                default ->
                        System.out.println(
                                "Opcion no valida."
                        );
            }

        } while (opcion != 6);
    }

    // ========================================
    // LISTAR PRODUCTOS
    // ========================================

    private static void mostrarProductos() {

        System.out.println();

        System.out.println(
                "========== LISTA DE PRODUCTOS =========="
        );

        gestorProductos.listarProductos();

        System.out.println(
                "Cantidad de productos: "
                + gestorProductos.cantidadProductos()
        );
    }

    // ========================================
    // BUSCAR PRODUCTO
    // ========================================

    private static void buscarProducto() {

        int id = leerEntero(
                "Ingrese el ID del producto: "
        );

        Producto producto =
                gestorProductos.buscarPorId(id);

        if (producto == null) {

            System.out.println(
                    "No se encontro el producto."
            );

            return;
        }

        producto.mostrarDatos();
    }

    // ========================================
    // REGISTRAR PRODUCTO
    // ========================================

    private static void registrarProducto() {

        System.out.println();

        System.out.println(
                "========== REGISTRAR PRODUCTO =========="
        );

        System.out.println(
                "1. Libro"
        );

        System.out.println(
                "2. Producto de papeleria"
        );

        int tipo = leerEntero(
                "Seleccione el tipo: "
        );

        if (tipo != 1 && tipo != 2) {

            System.out.println(
                    "Tipo no valido."
            );

            return;
        }

        String nombre = leerTexto(
                "Ingrese el nombre: "
        );

        double precio = leerPrecio(
                "Ingrese el precio: "
        );

        // ====================================
        // LIBRO
        // ====================================

        if (tipo == 1) {

            String autor = leerTexto(
                    "Ingrese el autor: "
            );

            int stock = leerEnteroNoNegativo(
                    "Ingrese el stock: "
            );

            Libro libro = new Libro(
                    siguienteIdProducto++,
                    nombre,
                    autor,
                    BigDecimal.valueOf(precio),
                    stock
            );

            gestorProductos.agregarProducto(
                    libro
            );

            System.out.println(
                    "Libro registrado correctamente."
            );

        } else {

            // =================================
            // PAPELERIA
            // =================================

            String categoria = leerTexto(
                    "Ingrese la categoria: "
            );

            int stock = leerEnteroNoNegativo(
                    "Ingrese el stock: "
            );

            ProductoPapeleria producto =
                    new ProductoPapeleria(
                            siguienteIdProducto++,
                            nombre,
                            precio,
                            categoria,
                            stock
                    );

            gestorProductos.agregarProducto(
                    producto
            );

            System.out.println(
                    "Producto de papeleria registrado correctamente."
            );
        }
    }

    // ========================================
    // ACTUALIZAR PRODUCTO
    // ========================================

    private static void actualizarProducto() {

        int id = leerEntero(
                "Ingrese el ID del producto: "
        );

        Producto producto =
                gestorProductos.buscarPorId(id);

        if (producto == null) {

            System.out.println(
                    "No se encontro el producto."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Producto actual:"
        );

        producto.mostrarDatos();

        System.out.println();

        String nuevoNombre = leerTexto(
                "Ingrese el nuevo nombre: "
        );

        double nuevoPrecio = leerPrecio(
                "Ingrese el nuevo precio: "
        );

        int nuevoStock = leerEnteroNoNegativo(
                "Ingrese el nuevo stock: "
        );

        producto.setNombre(
                nuevoNombre
        );

        producto.setPrecio(
                nuevoPrecio
        );

        producto.setStock(
                nuevoStock
        );

        // ====================================
        // DATOS DEL LIBRO
        // ====================================

        if (producto instanceof Libro libro) {

            String nuevoAutor = leerTexto(
                    "Ingrese el nuevo autor: "
            );

            libro.setAutor(
                    nuevoAutor
            );
        }

        // ====================================
        // DATOS DE PAPELERIA
        // ====================================

        if (producto instanceof ProductoPapeleria papeleria) {

            String nuevaCategoria = leerTexto(
                    "Ingrese la nueva categoria: "
            );

            papeleria.setCategoria(
                    nuevaCategoria
            );
        }

        gestorProductos.actualizarProducto(producto);

        System.out.println(
                "Producto actualizado correctamente."
        );
    }

    // ========================================
    // ELIMINAR PRODUCTO
    // ========================================

    private static void eliminarProducto() {

        int id = leerEntero(
                "Ingrese el ID del producto: "
        );

        boolean eliminado =
                gestorProductos.eliminarPorId(id);

        if (eliminado) {

            System.out.println(
                    "Producto eliminado correctamente."
            );

        } else {

            System.out.println(
                    "No se encontro el producto."
            );
        }
    }

    // ========================================
    // CLIENTES
    // ========================================

    private static void menuClientes() {

        int opcion;

        do {

            System.out.println();

            System.out.println(
                    "========== CLIENTES =========="
            );

            System.out.println(
                    "1. Registrar cliente"
            );

            System.out.println(
                    "2. Listar clientes"
            );

            System.out.println(
                    "3. Buscar cliente"
            );

            System.out.println(
                    "4. Eliminar cliente"
            );

            System.out.println(
                    "5. Volver"
            );

            opcion = leerEntero(
                    "Seleccione una opcion: "
            );

            switch (opcion) {

                case 1 -> registrarCliente();

                case 2 -> listarClientes();

                case 3 -> buscarCliente();

                case 4 -> eliminarCliente();

                case 5 -> {
                }

                default ->
                        System.out.println(
                                "Opcion no valida."
                        );
            }

        } while (opcion != 5);
    }

    // ========================================
    // REGISTRAR CLIENTE
    // ========================================

    private static void registrarCliente() {

        System.out.println();

        System.out.println(
                "========== REGISTRAR CLIENTE =========="
        );

        /*
         * IMPORTANTE:
         * Aqui NO se pregunta por DNI.
         *
         * El DNI solamente se pregunta
         * cuando se registra un pedido.
         */

        String nombre = leerTexto(
                "Ingrese nombre: "
        );

        String correo;

        while (true) {

            correo = leerTexto(
                    "Ingrese correo: "
            );

            if (Validacion.correoValido(correo)) {
                break;
            }

            System.out.println(
                    "Error: el correo no es valido."
            );
        }

        String telefono;

        while (true) {

            telefono = leerTexto(
                    "Ingrese telefono: "
            );

            if (Validacion.telefonoValido(telefono)) {
                break;
            }

            System.out.println(
                    "Error: el telefono debe tener 9 digitos."
            );
        }

        /*
         * Tu clase Cliente actualmente exige
         * un DNI valido de 8 digitos.
         *
         * Como el DNI no se pregunta durante
         * el registro del cliente, se utiliza
         * un valor interno temporal.
         */

        String dni = "00000000";

        Cliente cliente = new Cliente(
                siguienteIdCliente++,
                dni,
                nombre,
                correo,
                telefono
        );

        gestorClientes.agregarCliente(
                cliente
        );

        System.out.println(
                "Cliente registrado correctamente."
        );
    }

    // ========================================
    // LISTAR CLIENTES
    // ========================================

    private static void listarClientes() {

        System.out.println();

        System.out.println(
                "========== LISTA DE CLIENTES =========="
        );

        gestorClientes.listarClientes();

        System.out.println(
                "Cantidad de clientes: "
                + gestorClientes.cantidadClientes()
        );
    }

    // ========================================
    // BUSCAR CLIENTE
    // ========================================

    private static void buscarCliente() {

        String dni = leerTexto(
                "Ingrese DNI del cliente: "
        );

        Cliente cliente =
                gestorClientes.buscarPorDni(dni);

        if (cliente == null) {

            System.out.println(
                    "No se encontro el cliente."
            );

            return;
        }

        cliente.mostrarDatos();
    }

    // ========================================
    // ELIMINAR CLIENTE
    // ========================================

    private static void eliminarCliente() {

        String dni = leerTexto(
                "Ingrese DNI del cliente: "
        );

        boolean eliminado =
                gestorClientes.eliminarPorDni(dni);

        if (eliminado) {

            System.out.println(
                    "Cliente eliminado correctamente."
            );

        } else {

            System.out.println(
                    "No se encontro el cliente."
            );
        }
    }

    // ========================================
    // PEDIDOS
    // ========================================

    private static void menuPedidos() {

        int opcion;

        do {

            System.out.println();

            System.out.println(
                    "========== PEDIDOS =========="
            );

            System.out.println(
                    "1. Registrar pedido"
            );

            System.out.println(
                    "2. Listar pedidos"
            );

            System.out.println(
                    "3. Buscar pedido"
            );

            System.out.println(
                    "4. Volver"
            );

            opcion = leerEntero(
                    "Seleccione una opcion: "
            );

            switch (opcion) {

                case 1 -> registrarPedido();

                case 2 -> listarPedidos();

                case 3 -> buscarPedido();

                case 4 -> {
                }

                default ->
                        System.out.println(
                                "Opcion no valida."
                        );
            }

        } while (opcion != 4);
    }

    // ========================================
    // REGISTRAR PEDIDO
    // ========================================

    private static void registrarPedido() {

        if (gestorProductos.cantidadProductos() == 0) {

            System.out.println(
                    "No existen productos registrados."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "========== REGISTRAR PEDIDO =========="
        );

        // ====================================
        // DNI
        // ====================================

        String tieneDni = leerTexto(
                "¿El cliente tiene DNI? (S/N): "
        );

        while (!tieneDni.equalsIgnoreCase("S")
                && !tieneDni.equalsIgnoreCase("N")) {

            System.out.println(
                    "Error: responda S o N."
            );

            tieneDni = leerTexto(
                    "¿El cliente tiene DNI? (S/N): "
            );
        }

        Cliente cliente;

        // ====================================
        // SI TIENE DNI
        // ====================================

        if (tieneDni.equalsIgnoreCase("S")) {

            String dni;

            while (true) {

                dni = leerTexto(
                        "Ingrese DNI del cliente: "
                );

                if (!Validacion.dniValido(dni)) {

                    System.out.println(
                            "Error: el DNI debe tener 8 digitos."
                    );

                    continue;
                }

                cliente =
                        gestorClientes.buscarPorDni(dni);

                if (cliente == null) {

                    System.out.println(
                            "No se encontro un cliente con ese DNI."
                    );

                    continue;
                }

                break;
            }

        } else {

            // =================================
            // SI NO TIENE DNI
            // =================================
            //
            // NO SE PREGUNTA NADA MAS.
            //
            // Se crea un cliente temporal
            // para poder generar el pedido.
            // =================================

            cliente = new Cliente(
                    0,
                    "00000000",
                    "Cliente sin DNI",
                    "sin-dni@librerianova.com",
                    "999999999"
            );
        }

        // ====================================
        // CREAR VENTA
        // ====================================

        Venta venta =
                new Venta(
                        siguienteIdVenta,
                        cliente
                );

        // ====================================
        // AGREGAR PRODUCTOS
        // ====================================

        while (true) {

            mostrarProductos();

            int idProducto = leerEntero(
                    "Ingrese ID del producto, o 0 para terminar: "
            );

            if (idProducto == 0) {
                break;
            }

            Producto producto =
                    gestorProductos.buscarPorId(
                            idProducto
                    );

            if (producto == null) {

                System.out.println(
                        "No se encontro el producto."
                );

                continue;
            }

            // =================================
            // VERIFICAR STOCK
            // =================================

            int stockDisponible =
                    producto.getStock();

            if (stockDisponible <= 0) {

                System.out.println(
                        "El producto no tiene stock disponible."
                );

                continue;
            }

            int cantidad =
                    leerEnteroPositivo(
                            "Ingrese cantidad: "
                    );

            // =================================
            // CANTIDAD YA AGREGADA
            // =================================

            int cantidadEnVenta =
                    obtenerCantidadEnVenta(
                            venta,
                            producto
                    );

            int stockDisponibleReal =
                    stockDisponible
                    - cantidadEnVenta;

            if (cantidad > stockDisponibleReal) {

                System.out.println(
                        "No hay stock suficiente."
                );

                System.out.println(
                        "Stock disponible: "
                        + stockDisponibleReal
                );

                continue;
            }

            // =================================
            // AGREGAR DETALLE
            // =================================

            try {

                DetalleVenta detalle =
                        new DetalleVenta(
                                producto,
                                cantidad
                        );

                venta.agregarDetalle(
                        detalle
                );

                System.out.println(
                        "Producto agregado al pedido."
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Error: "
                        + e.getMessage()
                );

                continue;
            }

            String continuar = leerTexto(
                    "Desea agregar otro producto? (S/N): "
            );

            while (!continuar.equalsIgnoreCase("S")
                    && !continuar.equalsIgnoreCase("N")) {

                System.out.println(
                        "Error: responda S o N."
                );

                continuar = leerTexto(
                        "Desea agregar otro producto? (S/N): "
                );
            }

            if (!continuar.equalsIgnoreCase("S")) {
                break;
            }
        }

        // ====================================
        // SIN PRODUCTOS
        // ====================================

        if (venta.getDetalles().isEmpty()) {

            System.out.println(
                    "El pedido no tiene productos."
            );

            System.out.println(
                    "El pedido no sera registrado."
            );

            return;
        }

        // ====================================
        // RESUMEN
        // ====================================

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "          RESUMEN DEL PEDIDO"
        );

        System.out.println(
                "========================================"
        );

        venta.mostrarResumen();

        System.out.println();

        System.out.println(
                "IMPORTANTE: el stock aun NO ha sido descontado."
        );

        // ====================================
        // CONFIRMAR
        // ====================================

        String confirmar = leerTexto(
                "Confirma la venta? (S/N): "
        );

        while (!confirmar.equalsIgnoreCase("S")
                && !confirmar.equalsIgnoreCase("N")) {

            System.out.println(
                    "Error: responda S o N."
            );

            confirmar = leerTexto(
                    "Confirma la venta? (S/N): "
            );
        }

        // ====================================
        // CANCELAR
        // ====================================

        if (!confirmar.equalsIgnoreCase("S")) {

            System.out.println();

            System.out.println(
                    "Venta cancelada."
            );

            System.out.println(
                    "El stock no ha sido modificado."
            );

            return;
        }

        // ====================================
        // VALIDAR STOCK FINAL
        // ====================================

        if (!validarStockVenta(venta)) {

            System.out.println();

            System.out.println(
                    "La venta no puede confirmarse."
            );

            System.out.println(
                    "El stock no ha sido modificado."
            );

            return;
        }

        // ====================================
        // DESCONTAR STOCK
        // ====================================

        actualizarStock(venta);

        // ====================================
        // REGISTRAR VENTA
        // ====================================

        siguienteIdVenta++;

        ventas.add(venta);

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "     VENTA REGISTRADA CORRECTAMENTE"
        );

        System.out.println(
                "========================================"
        );

        venta.mostrarResumen();
    }

    // ========================================
    // VALIDAR STOCK
    // ========================================

    private static boolean validarStockVenta(
            Venta venta) {

        for (DetalleVenta detalle :
                venta.getDetalles()) {

            Producto producto =
                    detalle.getProducto();

            int stock =
                    producto.getStock();

            int cantidad =
                    detalle.getCantidad();

            if (cantidad > stock) {

                System.out.println();

                System.out.println(
                        "Stock insuficiente para: "
                        + producto.getNombre()
                );

                System.out.println(
                        "Stock actual: "
                        + stock
                );

                System.out.println(
                        "Cantidad solicitada: "
                        + cantidad
                );

                return false;
            }
        }

        return true;
    }

    // ========================================
    // ACTUALIZAR STOCK
    // ========================================

    private static void actualizarStock(
            Venta venta) {

        for (DetalleVenta detalle :
                venta.getDetalles()) {

            Producto producto =
                    detalle.getProducto();

            int stockActual =
                    producto.getStock();

            int cantidadVendida =
                    detalle.getCantidad();

            producto.setStock(
                    stockActual - cantidadVendida
            );

            gestorProductos.actualizarProducto(producto);
        }
    }

    // ========================================
    // OBTENER CANTIDAD EN VENTA
    // ========================================

    private static int obtenerCantidadEnVenta(
            Venta venta,
            Producto producto) {

        int cantidadTotal = 0;

        for (DetalleVenta detalle :
                venta.getDetalles()) {

            if (detalle.getProducto().getId()
                    == producto.getId()) {

                cantidadTotal +=
                        detalle.getCantidad();
            }
        }

        return cantidadTotal;
    }

    // ========================================
    // LISTAR PEDIDOS
    // ========================================

    private static void listarPedidos() {

        System.out.println();

        System.out.println(
                "========== LISTA DE PEDIDOS =========="
        );

        if (ventas.isEmpty()) {

            System.out.println(
                    "No hay pedidos registrados."
            );

            return;
        }

        for (Venta venta : ventas) {

            System.out.println(
                    "Pedido: "
                    + venta.getId()
                    + " | Cliente: "
                    + venta.getCliente().getNombre()
                    + " | Total: S/ "
                    + String.format(
                            "%.2f",
                            venta.calcularTotal()
                    )
            );
        }
    }

    // ========================================
    // BUSCAR PEDIDO
    // ========================================

    private static void buscarPedido() {

        int id = leerEntero(
                "Ingrese ID del pedido: "
        );

        for (Venta venta : ventas) {

            if (venta.getId() == id) {

                venta.mostrarResumen();

                return;
            }
        }

        System.out.println(
                "No se encontro el pedido."
        );
    }

    // ========================================
    // REPORTES
    // ========================================

    private static void menuReportes() {

        int opcion;

        do {

            System.out.println();

            System.out.println(
                    "========== REPORTES =========="
            );

            System.out.println(
                    "1. Reporte del dia"
            );

            System.out.println(
                    "2. Reporte general de ventas"
            );

            System.out.println(
                    "3. Volver"
            );

            opcion = leerEntero(
                    "Seleccione una opcion: "
            );

            switch (opcion) {

                case 1 -> reporteDelDia();

                case 2 -> reporteGeneral();

                case 3 -> {
                }

                default ->
                        System.out.println(
                                "Opcion no valida."
                        );
            }

        } while (opcion != 3);
    }

    // ========================================
    // REPORTE DEL DIA
    // ========================================

    private static void reporteDelDia() {

        LocalDate hoy =
                LocalDate.now();

        int pedidos = 0;

        int productosVendidos = 0;

        BigDecimal total =
                BigDecimal.ZERO;

        for (Venta venta : ventas) {

            if (venta.getFecha()
                    .toLocalDate()
                    .equals(hoy)) {

                pedidos++;

                total = total.add(
                        venta.calcularTotal()
                );

                for (DetalleVenta detalle :
                        venta.getDetalles()) {

                    productosVendidos +=
                            detalle.getCantidad();
                }
            }
        }

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "             REPORTE DEL DIA"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Fecha: "
                + hoy.format(
                        DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy"
                        )
                )
        );

        System.out.println(
                "Pedidos realizados: "
                + pedidos
        );

        System.out.println(
                "Productos vendidos: "
                + productosVendidos
        );

        System.out.println(
                "Total vendido: S/ "
                + String.format(
                        "%.2f",
                        total
                )
        );

        System.out.println(
                "========================================"
        );
    }

    // ========================================
    // REPORTE GENERAL
    // ========================================

    private static void reporteGeneral() {

        BigDecimal total =
                BigDecimal.ZERO;

        int productosVendidos = 0;

        for (Venta venta : ventas) {

            total = total.add(
                    venta.calcularTotal()
            );

            for (DetalleVenta detalle :
                    venta.getDetalles()) {

                productosVendidos +=
                        detalle.getCantidad();
            }
        }

        System.out.println();

        System.out.println(
                "========== REPORTE GENERAL =========="
        );

        System.out.println(
                "Cantidad de pedidos: "
                + ventas.size()
        );

        System.out.println(
                "Productos vendidos: "
                + productosVendidos
        );

        System.out.println(
                "Total vendido: S/ "
                + String.format(
                        "%.2f",
                        total
                )
        );
    }

    // ========================================
    // INFORMACION
    // ========================================

    private static void mostrarInformacion() {

        System.out.println();

        System.out.println(
                "========== INFORMACION =========="
        );

        System.out.println(
                "Sistema de Libreria"
        );

        System.out.println(
                "Gestion de productos, clientes y pedidos."
        );

        System.out.println(
                "El sistema utiliza herencia."
        );

        System.out.println(
                "El sistema utiliza polimorfismo."
        );

        System.out.println(
                "El sistema utiliza una interfaz."
        );

        System.out.println(
                "El sistema utiliza una clase abstracta."
        );

        System.out.println(
                "Todos los productos controlan stock."
        );
    }

    // ========================================
    // AYUDA
    // ========================================

    private static void mostrarAyuda() {

        System.out.println();

        System.out.println(
                "========== AYUDA =========="
        );

        System.out.println(
                "Use los numeros del menu para navegar."
        );

        System.out.println(
                "El DNI se solicita solamente al registrar un pedido."
        );

        System.out.println(
                "Si el cliente tiene DNI, debe tener 8 digitos."
        );

        System.out.println(
                "Todos los productos tienen control de stock."
        );

        System.out.println(
                "No se puede vender una cantidad mayor al stock."
        );

        System.out.println(
                "Los productos con stock 0 no pueden venderse."
        );

        System.out.println(
                "El stock solo se descuenta al confirmar la venta."
        );

        System.out.println(
                "Si se cancela una venta, el stock no cambia."
        );
    }

    // ========================================
    // DESPEDIDA
    // ========================================

    private static void despedida() {

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "     Gracias por usar el sistema."
        );

        System.out.println(
                "========================================"
        );
    }

    // ========================================
    // LEER ENTERO
    // ========================================

    private static int leerEntero(
            String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: ingrese un numero entero valido."
                );
            }
        }
    }

    // ========================================
    // ENTERO POSITIVO
    // ========================================

    private static int leerEnteroPositivo(
            String mensaje) {

        while (true) {

            int valor =
                    leerEntero(mensaje);

            if (valor > 0) {
                return valor;
            }

            System.out.println(
                    "Error: el valor debe ser mayor que cero."
            );
        }
    }

    // ========================================
    // ENTERO NO NEGATIVO
    // ========================================

    private static int leerEnteroNoNegativo(
            String mensaje) {

        while (true) {

            int valor =
                    leerEntero(mensaje);

            if (valor >= 0) {
                return valor;
            }

            System.out.println(
                    "Error: el stock no puede ser negativo."
            );
        }
    }

    // ========================================
    // PRECIO
    // ========================================

    private static double leerPrecio(
            String mensaje) {

        while (true) {

            double precio =
                    leerDouble(mensaje);

            if (Validacion.precioValido(precio)) {
                return precio;
            }

            System.out.println(
                    "Error: ingrese un precio mayor que cero."
            );
        }
    }

    // ========================================
    // DOUBLE
    // ========================================

    private static double leerDouble(
            String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return Double.parseDouble(
                        scanner.nextLine()
                                .trim()
                                .replace(',', '.')
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: ingrese un numero valido."
                );
            }
        }
    }

    // ========================================
    // TEXTO
    // ========================================

    private static String leerTexto(
            String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto =
                    scanner.nextLine().trim();

            if (Validacion.textoValido(texto)) {
                return texto;
            }

            System.out.println(
                    "Error: el valor no puede estar vacio."
            );
        }
    }
}