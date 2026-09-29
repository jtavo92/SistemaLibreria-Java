package com.librerianova.util;

public class Validacion {

    public Validacion() {
    }

    public static boolean textoValido(String texto) {

        return texto != null
                && !texto.trim().isEmpty();
    }

    public static boolean numeroPositivo(double numero) {

        return numero > 0;
    }

    public static boolean precioValido(double precio) {

        return precio > 0
                && precio <= 100000;
    }

    public static boolean dniValido(String dni) {

        return dni != null
                && dni.matches("\\d{8}");
    }

    public static boolean correoValido(String correo) {

        return correo != null
                && correo.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                );
    }

    public static boolean telefonoValido(
            String telefono) {

        return telefono != null
                && telefono.matches("\\d{9}");
    }

    public static boolean opcionMenuValida(
            int opcion,
            int minimo,
            int maximo) {

        return opcion >= minimo
                && opcion <= maximo;
    }
}