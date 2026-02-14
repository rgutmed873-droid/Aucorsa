package controller.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/AUCORSA";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Metodo para establecer conexión con la base de datos
     * @return Devuelte la conexión con la base de datos de los atributos principales
     * @throws SQLException
     */
    public static Connection getConexion() throws SQLException {

        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
