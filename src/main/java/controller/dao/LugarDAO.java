package controller.dao;

import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Lugar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase LugarDAO
 * DAO para la entidad Lugar. Proporciona operaciones CRUD sobre la tabla "Lugar".
 * Lanza {@link AucorsaException} en lugar de imprimir trazas.
 */
public class LugarDAO {

    // ==================== INSERTAR ====================

    /**
     * Inserta un nuevo Lugar en la base de datos.
     * @throws AucorsaException DB_DUPLICATE_KEY si el idLugar ya existe,
     *                          DB_INSERT_ERROR ante cualquier otro fallo SQL.
     */
    public static boolean añadirLugar(Connection con, Lugar lugar) throws AucorsaException {
        String sql = "INSERT INTO Lugar (idLugar, cp, ciudad, ubicacion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1, lugar.getIdLugar());
            ps.setString(2, lugar.getCP());
            ps.setString(3, lugar.getCiudad());
            ps.setString(4, lugar.getUbicacion());
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_DUPLICATE_KEY,
                    "idLugar=" + lugar.getIdLugar(), e);
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_INSERT_ERROR, e);
        }
    }

    // ==================== BUSCAR ====================

    /**
     * Busca un Lugar por su identificador. Devuelve null si no existe.
     * @throws AucorsaException DB_QUERY_ERROR ante fallo SQL.
     */
    public static Lugar buscarLugar(Connection con, int idLugar) throws AucorsaException {
        String sql = "SELECT * FROM Lugar WHERE idLugar = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idLugar);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Lugar(rs.getInt   ("idLugar"),
                                     rs.getString("cp"),
                                     rs.getString("ciudad"),
                                     rs.getString("ubicacion"));
                }
            }
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_QUERY_ERROR, e);
        }
        return null;
    }

    // ==================== LISTAR ====================

    /**
     * Obtiene todos los lugares de la base de datos.
     * @throws AucorsaException DB_QUERY_ERROR ante fallo SQL.
     */
    public static List<Lugar> mostrarTodosLosLugares(Connection con) throws AucorsaException {
        String sql = "SELECT * FROM Lugar";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Lugar> lugares = new ArrayList<>();
            while (rs.next()) {
                lugares.add(new Lugar(rs.getInt   ("idLugar"),
                                      rs.getString("cp"),
                                      rs.getString("ciudad"),
                                      rs.getString("ubicacion")));
            }
            return lugares;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_QUERY_ERROR, e);
        }
    }

    // ==================== ELIMINAR ====================

    /**
     * Elimina el Lugar identificado por idLugar.
     * @throws AucorsaException DB_DELETE_ERROR ante fallo SQL.
     */
    public static boolean eliminarLugar(Connection con, int idLugar) throws AucorsaException {
        String sql = "DELETE FROM Lugar WHERE idLugar = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idLugar);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_DELETE_ERROR, e);
        }
    }

    // ==================== MODIFICAR ====================

    /**
     * Actualiza cp, ciudad y ubicacion del Lugar.
     * @throws AucorsaException DB_UPDATE_ERROR ante fallo SQL.
     */
    public static boolean modificarLugar(Connection con, Lugar lugar) throws AucorsaException {
        String sql = "UPDATE Lugar SET cp = ?, ciudad = ?, ubicacion = ? WHERE idLugar = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, lugar.getCP());
            ps.setString(2, lugar.getCiudad());
            ps.setString(3, lugar.getUbicacion());
            ps.setInt   (4, lugar.getIdLugar());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_UPDATE_ERROR, e);
        }
    }
}
