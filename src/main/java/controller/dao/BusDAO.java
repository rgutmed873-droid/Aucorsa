package controller.dao;

import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Bus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase BusDAO
 * DAO para la entidad Bus. Todas las operaciones lanzan {@link AucorsaException}
 * en lugar de imprimir la traza, permitiendo al controlador mostrar mensajes
 * de error con código al usuario.
 */
public class BusDAO {

    // ==================== INSERTAR ====================

    /**
     * Inserta un nuevo Bus en la base de datos.
     * @throws AucorsaException con código DB_DUPLICATE_KEY si la matrícula ya existe,
     *                          o DB_INSERT_ERROR ante cualquier otro fallo SQL.
     */
    public static boolean añadirBus(Connection con, Bus bus) throws AucorsaException {
        String sql = "INSERT INTO Bus (matricula, tipo, licencia) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bus.getMatricula());
            ps.setString(2, bus.getTipo());
            ps.setString(3, bus.getLicencia());
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_DUPLICATE_KEY,
                    "matrícula=" + bus.getMatricula(), e);
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_INSERT_ERROR, e);
        }
    }

    // ==================== BUSCAR ====================

    /**
     * Busca un Bus por matrícula. Devuelve null si no existe.
     * @throws AucorsaException con código DB_QUERY_ERROR ante fallo SQL.
     */
    public static Bus buscarBus(Connection con, String matricula) throws AucorsaException {
        String sql = "SELECT * FROM Bus WHERE matricula = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Bus(rs.getString("matricula"),
                            rs.getString("tipo"),
                            rs.getString("licencia"));
                }
            }
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_QUERY_ERROR, e);
        }
        return null;
    }

    // ==================== LISTAR ====================

    /**
     * Obtiene todos los buses de la base de datos.
     * @throws AucorsaException con código DB_QUERY_ERROR ante fallo SQL.
     */
    public static List<Bus> mostrarTodosLosBuses(Connection con) throws AucorsaException {
        String sql = "SELECT * FROM Bus";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Bus> buses = new ArrayList<>();
            while (rs.next()) {
                buses.add(new Bus(rs.getString("matricula"),
                        rs.getString("tipo"),
                        rs.getString("licencia")));
            }
            return buses;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_QUERY_ERROR, e);
        }
    }

    // ==================== ELIMINAR ====================

    /**
     * Elimina el Bus identificado por matrícula.
     * @throws AucorsaException con código DB_DELETE_ERROR ante fallo SQL.
     */
    public static boolean eliminarBus(Connection con, String matricula) throws AucorsaException {
        String sql = "DELETE FROM Bus WHERE matricula = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_DELETE_ERROR, e);
        }
    }

    // ==================== MODIFICAR ====================

    /**
     * Actualiza tipo y licencia del Bus identificado por matrícula.
     * @throws AucorsaException con código DB_UPDATE_ERROR ante fallo SQL.
     */
    public static boolean modificarBus(Connection con, Bus bus) throws AucorsaException {
        String sql = "UPDATE Bus SET tipo = ?, licencia = ? WHERE matricula = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bus.getTipo());
            ps.setString(2, bus.getLicencia());
            ps.setString(3, bus.getMatricula());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_UPDATE_ERROR, e);
        }
    }
}