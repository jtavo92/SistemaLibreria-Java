package com.librerianova.modelo;

import com.librerianova.util.Validacion;

public class Cliente {

    private final int id;
    private final String dni;
    private final String nombre;
    private final String correo;
    private final String telefono;

    // SOBRECARGA DE CONSTRUCTORES
    public Cliente(int id, String dni, String nombre, String correo, String telefono) {
        this(id, dni, nombre, correo, telefono, true);
    }

    // Segundo constructor
    public Cliente(int id, String dni, String nombre, String correo,
                   String telefono, boolean validar) {

        if (validar) {

            if (!Validacion.dniValido(dni)) {
                throw new IllegalArgumentException(
                        "El DNI debe tener 8 digitos."
                );
            }

            if (!Validacion.textoValido(nombre)) {
                throw new IllegalArgumentException(
                        "El nombre no puede estar vacio."
                );
            }

            if (!Validacion.correoValido(correo)) {
                throw new IllegalArgumentException(
                        "El correo no es valido."
                );
            }

            if (!Validacion.telefonoValido(telefono)) {
                throw new IllegalArgumentException(
                        "El telefono no es valido."
                );
            }
        }

        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
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

        System.out.println(
                "ID: " + id
                + " | DNI: " + dni
                + " | Nombre: " + nombre
                + " | Correo: " + correo
                + " | Telefono: " + telefono
        );
    }

    @Override
    public String toString() {

        return "Cliente{"
                + "id=" + id
                + ", dni='" + dni + '\''
                + ", nombre='" + nombre + '\''
                + ", correo='" + correo + '\''
                + ", telefono='" + telefono + '\''
                + '}';
    }
}