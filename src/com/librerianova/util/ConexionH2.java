package com.librerianova.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionH2 {

    private static final String URL =
            "jdbc:h2:mem:libreria;DB_CLOSE_DELAY=-1";

    private ConexionH2() {
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, "sa", "");
    }
}