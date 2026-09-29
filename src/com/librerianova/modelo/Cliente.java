package com.librerianova.modelo;

import com.librerianova.util.Validacion;

public class Cliente {

    private int id;
    private String dni;
    private String nombre;
    private String correo;
    private String telefono;

    // Constructor completo
    public Cliente(
            int id,
            String dni,
            String nombre,
            String correo,
            String telefono) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }

        if (!Validacion.dniValido(dni)) {
            throw new IllegalArgumentException(
                    "El DNI debe contener 8 números."
            );
        }

        if (!Validacion.textoValido(nombre)) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        if (!Validacion.correoValido(correo)) {
            throw new IllegalArgumentException(
                    "El correo no tiene un formato válido."
            );
        }

        if (!Validacion.telefonoValido(telefono)) {
            throw new IllegalArgumentException(
                    "El teléfono debe contener entre 7 y 15 números."
            );
        }

        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    // Constructor para nuevos clientes.
    // El ID se coloca automáticamente desde Menu.
    public Cliente(
            int id,
            String dni,
            String nombre,
            String correo,
            String telefono,
            boolean nuevo) {

        this(id, dni, nombre, correo, telefono);
    }

    // Cliente de ejemplo que ya está registrado
    public static Cliente clienteRegistrado() {

        return new Cliente(
                1,
                "72886138",
                "JOSÉ GUSTAVO ZAPATA DE LA CRUZ",
                "jose.zapata@gmail.com",
                "999999999"
        );
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
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
        System.out.println("DNI: " + dni);
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        System.out.println("Teléfono: " + telefono);
    }
}