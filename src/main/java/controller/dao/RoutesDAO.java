package controller.dao;

import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Routes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase RoutesDAO
 * DAO para la entidad Routes. La clave primaria compuesta es
 * (matricula, numConductor, idLugar).
 * Lanza {@link AucorsaException} en lugar de imprimir trazas.
 */
public class RoutesDAO {

    // ==================== INSERTAR ====================

    /**
     * Inserta una nueva ruta en la base de datos.
     * @throws AucorsaException DB_DUPLICATE_KEY si la combinación PK ya existe,
     *                          DB_INSERT_ERROR ante cualquier otro fallo SQL.
     */
    public static boolean insertarRuta(Connection con, Routes ruta) throws AucorsaException {
        String sql = "INSERT INTO Routes (matricula, numConductor, idLugar, diaSemana) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ruta.getMatricula());
            ps.setInt   (2, ruta.getNumConductor());
            ps.setInt   (3, ruta.getIdLugar());
            ps.setString(4, ruta.getDiaSemana());
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_DUPLICATE_KEY,
                    ruta.getMatricula() + " / conductor " + ruta.getNumConductor(), e);
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_INSERT_ERROR, e);
        }
    }

    // ==================== LISTAR ====================

    /**
     * Obtiene todas las rutas de la base de datos.
     * @throws AucorsaException DB_QUERY_ERROR ante fallo SQL.
     */
    public static List<Routes> mostrarTodasLasRutas(Connection con) throws AucorsaException {
        String sql = "SELECT * FROM Routes";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Routes> rutas = new ArrayList<>();
            while (rs.next()) {
                rutas.add(new Routes(rs.getString("matricula"),
                                     rs.getInt   ("numConductor"),
                                     rs.getInt   ("idLugar"),
                                     rs.getString("diaSemana")));
            }
            return rutas;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_QUERY_ERROR, e);
        }
    }

    // ==================== ELIMINAR ====================

    /**
     * Elimina la ruta identificada por la clave compuesta (matricula, numConductor, idLugar).
     * @throws AucorsaException DB_DELETE_ERROR ante fallo SQL.
     */
    public static boolean eliminarRuta(Connection con,
                                       String matricula,
                                       int numConductor,
                                       int idLugar) throws AucorsaException {
        String sql = "DELETE FROM Routes WHERE matricula = ? AND numConductor = ? AND idLugar = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            ps.setInt   (2, numConductor);
            ps.setInt   (3, idLugar);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_DELETE_ERROR, e);
        }
    }

    // ==================== MODIFICAR ====================

    /**
     * Actualiza el día de la semana de la ruta identificada por su clave compuesta.
     * @throws AucorsaException DB_UPDATE_ERROR ante fallo SQL.
     */
    public static boolean modificarRuta(Connection con, Routes ruta) throws AucorsaException {
        String sql = "UPDATE Routes SET diaSemana = ? WHERE matricula = ? AND numConductor = ? AND idLugar = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ruta.getDiaSemana());
            ps.setString(2, ruta.getMatricula());
            ps.setInt   (3, ruta.getNumConductor());
            ps.setInt   (4, ruta.getIdLugar());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new AucorsaException(AucorsaErrorCode.DB_UPDATE_ERROR, e);
        }
    }
}
