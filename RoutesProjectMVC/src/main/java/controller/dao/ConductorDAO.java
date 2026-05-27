package controller.dao;

import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Conductor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ConductorDAO
 * DAO para la entidad Conductor. Lanza {@link AucorsaException} en lugar de
 * imprimir la traza, facilitando el manejo de errores en los controladores.
 */
public class ConductorDAO {

    // ==================== INSERTAR ====================

    /**
     * Inserta un nuevo Conductor en la base de datos.
     * @throws AucorsaException DB_DUPLICATE_KEY si el numConductor ya existe,
     *                          DB_INSERT_ERROR ante cualquier otro fallo SQL.
     */
    public boolean insertarConductor(Connection con, Conductor conductor) throws AucorsaException {
        String sql = "INSERT INTO Conductor (numConductor, nombre, apellido, imagen) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1, conductor.getNumConductor());
            ps.setString(2, conductor.getNombre());
            ps.setString(3, conductor.getApellido());
            ps.setString(4, conductor.getImagen());
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_DUPLICATE_KEY,
                    "numConductor=" + conductor.getNumConductor(), e);
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_INSERT_ERROR, e);
        }
    }

    // ==================== BUSCAR ====================

    /**
     * Busca un Conductor por su número identificativo. Devuelve null si no existe.
     * @throws AucorsaException DB_QUERY_ERROR ante fallo SQL.
     */
    public Conductor buscarConductor(Connection con, int numConductor) throws AucorsaException {
        String sql = "SELECT * FROM Conductor WHERE numConductor = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numConductor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Conductor(rs.getInt   ("numConductor"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("imagen"));
                }
            }
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_QUERY_ERROR, e);
        }
        return null;
    }

    // ==================== LISTAR ====================

    /**
     * Obtiene todos los conductores de la base de datos.
     * @throws AucorsaException DB_QUERY_ERROR ante fallo SQL.
     */
    public List<Conductor> mostrarTodosConductores(Connection con) throws AucorsaException {
        String sql = "SELECT * FROM Conductor";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Conductor> conductores = new ArrayList<>();
            while (rs.next()) {
                conductores.add(new Conductor(rs.getInt   ("numConductor"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("imagen")));
            }
            return conductores;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_QUERY_ERROR, e);
        }
    }

    // ==================== ELIMINAR ====================

    /**
     * Elimina el Conductor identificado por su número.
     * @throws AucorsaException DB_DELETE_ERROR ante fallo SQL.
     */
    public boolean eliminarConductor(Connection con, int numConductor) throws AucorsaException {
        String sql = "DELETE FROM Conductor WHERE numConductor = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numConductor);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_DELETE_ERROR, e);
        }
    }

    // ==================== MODIFICAR ====================

    /**
     * Actualiza nombre, apellido e imagen del Conductor.
     * @throws AucorsaException DB_UPDATE_ERROR ante fallo SQL.
     */
    public boolean modificarConductor(Connection con, Conductor conductor) throws AucorsaException {
        String sql = "UPDATE Conductor SET nombre = ?, apellido = ?, imagen = ? WHERE numConductor = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, conductor.getNombre());
            ps.setString(2, conductor.getApellido());
            ps.setString(3, conductor.getImagen());
            ps.setInt   (4, conductor.getNumConductor());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_UPDATE_ERROR, e);
        }
    }
}