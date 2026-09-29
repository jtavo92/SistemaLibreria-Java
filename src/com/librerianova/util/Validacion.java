package com.librerianova.util;

public class Validacion {

    // Valida que un texto no esté vacío
    public static boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    // Valida que un número sea mayor o igual a cero
    public static boolean numeroPositivo(double numero) {
        return numero >= 0;
    }

    // Valida que el precio sea mayor que cero
    public static boolean precioValido(double precio) {
        return precio > 0;
    }

    // Valida que el DNI tenga 8 números
    public static boolean dniValido(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }

    // Valida un correo electrónico básico
    public static boolean correoValido(String correo) {
        return correo != null
                && correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    // Valida un teléfono de entre 7 y 15 números
    public static boolean telefonoValido(String telefono) {
        return telefono != null
                && telefono.matches("\\d{7,15}");
    }

    // Valida las opciones del menú
    public static boolean opcionMenuValida(int opcion) {
        return opcion >= 1 && opcion <= 4;
    }
}