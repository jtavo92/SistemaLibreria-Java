package modelo;

public class Cliente {

    private final int id;
    private final String nombre;
    private final String correo;
    private final String telefono;

    public Cliente(int id, String nombre, String correo, String telefono) {
        if (id <= 0) {
            throw new IllegalArgumentException("el ID debe ser mayor que cero.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre no puede estar vacio.");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("el correo no puede estar vacio.");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("el telefono no puede estar vacio.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.correo = correo.trim();
        this.telefono = telefono.trim();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void mostrarDatos() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        System.out.println("Telefono: " + telefono);
    }
}