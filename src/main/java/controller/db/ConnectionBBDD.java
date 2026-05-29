package controller.db;

import exception.AucorsaErrorCode;
import exception.AucorsaException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase ConnectionBBDD
 * Se encarga de abrir la conexión con la base de datos MySQL
 * Lanza {@link AucorsaException} con código {@link AucorsaErrorCode#DB_CONNECTION_ERROR}
 * si no puede establecer la conexión.
 */
public class ConnectionBBDD {

    // ==================== CONSTANTES DE CONEXIÓN ====================

    public static final String URL      = "jdbc:mysql://127.0.0.1:3306/Aucorsa";
    public static final String USER     = "root";
    public static final String PASSWORD = "";

    // ==================== MÉTODOS ====================

    /**
     * Crea y devuelve una nueva conexión activa a la base de datos.
     * @return Conexión activa a la base de datos
     * @throws AucorsaException si ocurre un error al establecer la conexión
     */
    public static Connection getConexion() throws AucorsaException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new AucorsaException(
                    AucorsaErrorCode.DB_CONNECTION_ERROR,
                    "URL: " + URL,
                    e
            );
        }
    }
}