package menu;

import com.librerianova.modelo.Cliente;
import com.librerianova.modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Producto> productos = new ArrayList<>();

    private static int siguienteIdCliente = 2;
    private static int siguienteIdProducto = 6;

    public static void mostrarMenu() {

        cargarDatosIniciales();

        int opcion;

        do {

            mostrarEncabezado();

            System.out.println("  +--------------------------------------+");
            System.out.println("  |              MENU PRINCIPAL          |");
            System.out.println("  +--------------------------------------+");
            System.out.println("  |  1.  Catalogo de productos           |");
            System.out.println("  |  2.  Buscar producto                 |");
            System.out.println("  |  3.  Registrar cliente               |");
            System.out.println("  |  4.  Consultar clientes              |");
            System.out.println("  |  5.  Registrar producto              |");
            System.out.println("  |  6.  Registrar pedido                |");
            System.out.println("  |  7.  Informacion de la libreria     |");
            System.out.println("  |  8.  Ayuda                           |");
            System.out.println("  |  9.  Salir                           |");
            System.out.println("  +--------------------------------------+");
            System.out.print("     Seleccione una opcion: ");

            opcion = leerEntero();

            System.out.println();

            switch (opcion) {

                case 1:
                    mostrarProductos();
                    break;

                case 2:
                    buscarProducto();
                    break;

                case 3:
                    registrarCliente();
                    break;

                case 4:
                    mostrarClientes();
                    break;

                case 5:
                    registrarProducto();
                    break;

                case 6:
                    registrarPedido();
                    break;

                case 7:
                    mostrarInformacion();
                    break;

                case 8:
                    mostrarAyuda();
                    break;

                case 9:
                    despedida();
                    break;

                default:
                    System.out.println(
                            "  [!] Opcion invalida. Seleccione entre 1 y 9."
                    );
            }

            if (opcion != 9) {

                System.out.println();
                System.out.println("  --------------------------------------");
                System.out.println("       Presione ENTER para continuar...");
                System.out.println("  --------------------------------------");

                scanner.nextLine();
            }

        } while (opcion != 9);
    }

    // ==================================================
    // ENCABEZADO
    // ==================================================

    private static void mostrarEncabezado() {

        System.out.println();
        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |                                                |");
        System.out.println("  |              L I B R E R I A   N O V A       |");
        System.out.println("  |                                                |");
        System.out.println("  |          Sistema de gestion de libreria       |");
        System.out.println("  |                                                |");
        System.out.println("  ==================================================");
        System.out.println();
    }

    // ==================================================
    // DATOS INICIALES
    // ==================================================

    private static void cargarDatosIniciales() {

        if (!productos.isEmpty()) {
            return;
        }

        // PRODUCTOS DE EJEMPLO

        productos.add(
                new Producto(
                        1,
                        "Cuaderno Universitario",
                        12.50
                )
        );

        productos.add(
                new Producto(
                        2,
                        "Lapicero Azul",
                        2.50
                )
        );

        productos.add(
                new Producto(
                        3,
                        "Agenda 2026",
                        25.00
                )
        );

        productos.add(
                new Producto(
                        4,
                        "Folder A4",
                        4.50
                )
        );

        productos.add(
                new Producto(
                        5,
                        "Libro de Programacion Java",
                        65.00
                )
        );

        // CLIENTE DE EJEMPLO

        clientes.add(
                Cliente.clienteRegistrado()
        );
    }

    // ==================================================
    // 1. MOSTRAR PRODUCTOS
    // ==================================================

    private static void mostrarProductos() {

        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |              CATALOGO DE PRODUCTOS            |");
        System.out.println("  ==================================================");

        if (productos.isEmpty()) {

            System.out.println();
            System.out.println("  No existen productos registrados.");

            return;
        }

        System.out.println();

        System.out.printf(
                "  %-5s %-32s %12s%n",
                "ID",
                "PRODUCTO",
                "PRECIO"
        );

        System.out.println(
                "  --------------------------------------------------"
        );

        for (Producto producto : productos) {

            System.out.printf(
                    "  %-5d %-32s S/ %8.2f%n",
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio()
            );
        }

        System.out.println(
                "  --------------------------------------------------"
        );

        System.out.println(
                "  Total de productos: " + productos.size()
        );
    }

    // ==================================================
    // 2. BUSCAR PRODUCTO
    // ==================================================

    private static void buscarProducto() {

        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |                BUSCAR PRODUCTO                |");
        System.out.println("  ==================================================");

        System.out.println();
        System.out.print("  Ingrese el nombre del producto: ");

        String busqueda = scanner.nextLine().trim();

        if (busqueda.isEmpty()) {

            System.out.println();
            System.out.println("  [!] Debe ingresar un nombre.");

            return;
        }

        boolean encontrado = false;

        for (Producto producto : productos) {

            if (producto.getNombre()
                    .toLowerCase()
                    .contains(busqueda.toLowerCase())) {

                System.out.println();
                System.out.println("  [OK] Producto encontrado");

                System.out.println(
                        "  --------------------------------------"
                );

                System.out.println(
                        "  ID      : " + producto.getId()
                );

                System.out.println(
                        "  Nombre  : " + producto.getNombre()
                );

                System.out.printf(
                        "  Precio  : S/ %.2f%n",
                        producto.getPrecio()
                );

                encontrado = true;
            }
        }

        if (!encontrado) {

            System.out.println();
            System.out.println(
                    "  [!] No se encontro ningun producto."
            );
        }
    }

    // ==================================================
    // 3. REGISTRAR CLIENTE
    // ==================================================

    private static void registrarCliente() {

        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |                REGISTRAR CLIENTE              |");
        System.out.println("  ==================================================");

        try {

            System.out.println();
            System.out.println(
                    "  El ID sera generado automaticamente."
            );

            System.out.println();

            System.out.print("  DNI              : ");
            String dni = scanner.nextLine().trim();

            System.out.print("  Nombre completo   : ");
            String nombre = scanner.nextLine().trim();

            System.out.print("  Correo            : ");
            String correo = scanner.nextLine().trim();

            System.out.print("  Telefono          : ");
            String telefono = scanner.nextLine().trim();

            int nuevoId = siguienteIdCliente;

            Cliente nuevoCliente = new Cliente(
                    nuevoId,
                    dni,
                    nombre,
                    correo,
                    telefono
            );

            clientes.add(nuevoCliente);

            siguienteIdCliente++;

            System.out.println();
            System.out.println(
                    "  +--------------------------------------+"
            );
            System.out.println(
                    "  |     CLIENTE REGISTRADO CORRECTAMENTE |"
            );
            System.out.println(
                    "  +--------------------------------------+"
            );

            System.out.println();
            System.out.println(
                    "  ID        : " + nuevoCliente.getId()
            );

            System.out.println(
                    "  DNI       : " + nuevoCliente.getDni()
            );

            System.out.println(
                    "  Nombre    : " + nuevoCliente.getNombre()
            );

            System.out.println(
                    "  Correo    : " + nuevoCliente.getCorreo()
            );

            System.out.println(
                    "  Telefono  : " + nuevoCliente.getTelefono()
            );

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println(
                    "  [ERROR] No se pudo registrar el cliente."
            );

            System.out.println(
                    "  Motivo: " + e.getMessage()
            );
        }
    }

    // ==================================================
    // 4. MOSTRAR CLIENTES
    // ==================================================

    private static void mostrarClientes() {

        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |               CLIENTES REGISTRADOS            |");
        System.out.println("  ==================================================");

        if (clientes.isEmpty()) {

            System.out.println();
            System.out.println(
                    "  No existen clientes registrados."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "  Solo se muestra la informacion necesaria."
        );
        System.out.println(
                "  Los datos de contacto se mantienen ocultos."
        );
        System.out.println();

        for (Cliente cliente : clientes) {

            System.out.println(
                    "  +------------------------------------------+"
            );

            System.out.println(
                    "  | ID       : " + cliente.getId()
            );

            System.out.println(
                    "  | DNI      : " + ocultarDni(cliente.getDni())
            );

            System.out.println(
                    "  | Nombre   : " + cliente.getNombre()
            );

            System.out.println(
                    "  | Correo   : [DATO PROTEGIDO]"
            );

            System.out.println(
                    "  | Telefono : [DATO PROTEGIDO]"
            );

            System.out.println(
                    "  +------------------------------------------+"
            );
        }

        System.out.println();
        System.out.println(
                "  Total de clientes: " + clientes.size()
        );
    }

    // ==================================================
    // PROTEGER DNI
    // ==================================================

    private static String ocultarDni(String dni) {

        if (dni == null || dni.length() != 8) {
            return "[DATO PROTEGIDO]";
        }

        return "****" + dni.substring(4);
    }

    // ==================================================
    // 5. REGISTRAR PRODUCTO
    // ==================================================

    private static void registrarProducto() {

        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |               REGISTRAR PRODUCTO              |");
        System.out.println("  ==================================================");

        try {

            System.out.println();
            System.out.println(
                    "  El ID sera generado automaticamente."
            );

            System.out.println();

            System.out.print("  Nombre del producto : ");

            String nombre = scanner.nextLine().trim();

            System.out.print("  Precio              : S/ ");

            double precio = leerDouble();

            int nuevoId = siguienteIdProducto;

            Producto nuevoProducto = new Producto(
                    nuevoId,
                    nombre,
                    precio
            );

            productos.add(nuevoProducto);

            siguienteIdProducto++;

            System.out.println();
            System.out.println(
                    "  +--------------------------------------+"
            );
            System.out.println(
                    "  |     PRODUCTO REGISTRADO CORRECTAMENTE|"
            );
            System.out.println(
                    "  +--------------------------------------+"
            );

            System.out.println();

            System.out.println(
                    "  ID       : " + nuevoProducto.getId()
            );

            System.out.println(
                    "  Producto : " + nuevoProducto.getNombre()
            );

            System.out.printf(
                    "  Precio   : S/ %.2f%n",
                    nuevoProducto.getPrecio()
            );

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println(
                    "  [ERROR] No se pudo registrar el producto."
            );

            System.out.println(
                    "  Motivo: " + e.getMessage()
            );
        }
    }

    // ==================================================
    // 6. REGISTRAR PEDIDO
    // ==================================================

    private static void registrarPedido() {

        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |                 REGISTRAR PEDIDO              |");
        System.out.println("  ==================================================");

        if (clientes.isEmpty()) {

            System.out.println();
            System.out.println(
                    "  [!] No existen clientes registrados."
            );

            return;
        }

        if (productos.isEmpty()) {

            System.out.println();
            System.out.println(
                    "  [!] No existen productos registrados."
            );

            return;
        }

        Cliente cliente = clientes.get(0);

        System.out.println();
        System.out.println("  CLIENTE");
        System.out.println(
                "  ------------------------------------------"
        );

        System.out.println(
                "  ID       : " + cliente.getId()
        );

        System.out.println(
                "  DNI      : " + ocultarDni(cliente.getDni())
        );

        System.out.println(
                "  Nombre   : " + cliente.getNombre()
        );

        System.out.println();

        mostrarProductos();

        System.out.println();

        System.out.print(
                "  Ingrese ID del producto: "
        );

        int idProducto = leerEntero();

        Producto productoSeleccionado = null;

        for (Producto producto : productos) {

            if (producto.getId() == idProducto) {

                productoSeleccionado = producto;
                break;
            }
        }

        if (productoSeleccionado == null) {

            System.out.println();
            System.out.println(
                    "  [!] No existe un producto con ese ID."
            );

            return;
        }

        System.out.print(
                "  Ingrese cantidad: "
        );

        int cantidad = leerEntero();

        if (cantidad <= 0) {

            System.out.println();
            System.out.println(
                    "  [!] La cantidad debe ser mayor que cero."
            );

            return;
        }

        double subtotal =
                productoSeleccionado.getPrecio()
                * cantidad;

        double igv =
                subtotal * 0.18;

        double total =
                subtotal + igv;

        System.out.println();

        System.out.println(
                "  =================================================="
        );

        System.out.println(
                "  |              RESUMEN DEL PEDIDO              |"
        );

        System.out.println(
                "  =================================================="
        );

        System.out.println();

        System.out.println(
                "  Cliente       : " + cliente.getNombre()
        );

        System.out.println(
                "  DNI           : " + ocultarDni(cliente.getDni())
        );

        System.out.println();

        System.out.println(
                "  Producto      : "
                + productoSeleccionado.getNombre()
        );

        System.out.println(
                "  Cantidad      : " + cantidad
        );

        System.out.printf(
                "  Precio unidad : S/ %.2f%n",
                productoSeleccionado.getPrecio()
        );

        System.out.println(
                "  ------------------------------------------"
        );

        System.out.printf(
                "  Subtotal      : S/ %.2f%n",
                subtotal
        );

        System.out.printf(
                "  IGV (18%%)     : S/ %.2f%n",
                igv
        );

        System.out.println(
                "  ------------------------------------------"
        );

        System.out.printf(
                "  TOTAL         : S/ %.2f%n",
                total
        );

        System.out.println();

        System.out.println(
                "  [OK] Pedido registrado correctamente."
        );
    }

    // ==================================================
    // 7. INFORMACION
    // ==================================================

    private static void mostrarInformacion() {

        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |           INFORMACION DE LA LIBRERIA          |");
        System.out.println("  ==================================================");

        System.out.println();

        System.out.println(
                "  Nombre       : Libreria Nova"
        );

        System.out.println(
                "  Tipo         : Sistema de gestion de libreria"
        );

        System.out.println(
                "  Productos    : " + productos.size()
        );

        System.out.println(
                "  Clientes     : " + clientes.size()
        );

        System.out.println();

        System.out.println(
                "  FUNCIONES DEL SISTEMA"
        );

        System.out.println(
                "  ------------------------------------------"
        );

        System.out.println(
                "  [1] Gestion de productos"
        );

        System.out.println(
                "  [2] Gestion de clientes"
        );

        System.out.println(
                "  [3] Registro de pedidos"
        );

        System.out.println(
                "  [4] Calculo de IGV"
        );

        System.out.println(
                "  [5] Validacion de datos"
        );

        System.out.println(
                "  [6] ID automatico"
        );

        System.out.println(
                "  [7] Proteccion de datos"
        );

        System.out.println(
                "  ------------------------------------------"
        );
    }

    // ==================================================
    // 8. AYUDA
    // ==================================================

    private static void mostrarAyuda() {

        System.out.println();
        System.out.println("  ==================================================");
        System.out.println("  |                    AYUDA                       |");
        System.out.println("  ==================================================");

        System.out.println();

        System.out.println(
                "  Los ID de clientes y productos"
        );

        System.out.println(
                "  son generados automaticamente."
        );

        System.out.println();

        System.out.println(
                "  Los datos ingresados son validados"
        );

        System.out.println(
                "  antes de ser registrados."
        );

        System.out.println();

        System.out.println(
                "  Los datos personales de los clientes"
        );

        System.out.println(
                "  no se muestran innecesariamente."
        );

        System.out.println();

        System.out.println(
                "  El sistema inicia con:"
        );

        System.out.println(
                "  - 5 productos de ejemplo"
        );

        System.out.println(
                "  - 1 cliente registrado"
        );

        System.out.println();

        System.out.println(
                "  Los nuevos registros reciben"
        );

        System.out.println(
                "  automaticamente el siguiente ID."
        );
    }

    // ==================================================
    // 9. SALIR
    // ==================================================

    private static void despedida() {

        System.out.println();

        System.out.println(
                "  =================================================="
        );

        System.out.println(
                "  |                                                |"
        );

        System.out.println(
                "  |        GRACIAS POR USAR LIBRERIA NOVA        |"
        );

        System.out.println(
                "  |                                                |"
        );

        System.out.println(
                "  |              Hasta pronto!                    |"
        );

        System.out.println(
                "  |                                                |"
        );

        System.out.println(
                "  =================================================="
        );

        System.out.println();
    }

    // ==================================================
    // LEER ENTERO
    // ==================================================

    private static int leerEntero() {

        while (!scanner.hasNextInt()) {

            System.out.println();

            System.out.println(
                    "  [!] Ingrese un numero valido."
            );

            scanner.nextLine();

            System.out.print(
                    "  Ingrese nuevamente: "
            );
        }

        int numero = scanner.nextInt();

        scanner.nextLine();

        return numero;
    }

    // ==================================================
    // LEER DECIMAL
    // ==================================================

    private static double leerDouble() {

        while (!scanner.hasNextDouble()) {

            System.out.println();

            System.out.println(
                    "  [!] Ingrese un precio valido."
            );

            scanner.nextLine();

            System.out.print(
                    "  Ingrese nuevamente: S/ "
            );
        }

        double numero = scanner.nextDouble();

        scanner.nextLine();

        return numero;
    }
}