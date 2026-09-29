package com.librerianova.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class GestorArchivos {

    private static final Path CARPETA_DATOS =
            Path.of("datos");

    private static final Path ARCHIVO_PRODUCTOS =
            CARPETA_DATOS.resolve("productos.txt");

    private GestorArchivos() {
    }

    public static void guardarProducto(
            String nombre,
            double precio) {

        try {

            Files.createDirectories(
                    CARPETA_DATOS
            );

            String linea =
                    nombre
                    + "|"
                    + String.format("%.2f", precio)
                    + System.lineSeparator();

            Files.writeString(
                    ARCHIVO_PRODUCTOS,
                    linea,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {

            System.out.println(
                    "No se pudo guardar el producto: "
                    + e.getMessage()
            );
        }
    }
}