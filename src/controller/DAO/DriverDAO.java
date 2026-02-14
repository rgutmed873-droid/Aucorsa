package controller.DAO;

import model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDAO {

    /**
     * Metodo para consultar los conductores
     * @param numConductor variable para el número del Conductor
     * @param con Establezco el parámetro de la conexión y así no tiene que estar en el metodo
     * @return
     * @throws SQLException
     */
    public Driver consultDriver(int numConductor, Connection con) throws SQLException {

        String sqlConsultaConductor = "select nombre, apellido from CONDUCTOR WHERE numConductor = ?";

        try (PreparedStatement ps = con.prepareStatement(sqlConsultaConductor)){


            ps.setInt(1,numConductor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                Driver c = new Driver();

                c.setNombre(rs.getString("nombre"));
                c.setApellidos(rs.getString("apellido"));
                return c;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo para insertar Conductores en la base de datos
     * @param driver Atributo para obtener los datos del modelo Conductor
     * @param con Establezco el parámetro de la conexión y así no tiene que estar en el metodo
     * @return
     * @throws SQLException
     */
    public boolean insertDriver(Driver driver, Connection con) throws SQLException {
        String sqlInsertarConductor = "INSERT INTO CONDUCTOR (nombre, apellido, numeroConductor) VALUES (?, ?,?)";

        try (PreparedStatement ps = con.prepareStatement(sqlInsertarConductor)){

            ps.setString(1, driver.getNombre());
            ps.setString(2,driver.getApellidos());
            ps.setInt(3, driver.getNumeroConductor());

            return ps.executeUpdate() > 0;
        }

    }

    /**
     * Metodo para borrar o eliminar un Conductor
     * @param nombre
     * @param apellido
     * @param numDriver
     * @param con
     * @return
     */
    public boolean deleteDriver(String nombre, String apellido, int numDriver,Connection con){
        String sqlEliminarDriver = "DELETE FROM driver WHERE numdriver = ? AND apellido = ? AND numdriver = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarDriver)){

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, numDriver);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo para actualizar la tabla de los conductores
     * @param nombre
     * @param apellido
     * @param numDriver
     * @param con
     * @return
     * @throws SQLException
     */
    public boolean updateDriver(String nombre, String apellido, int numDriver, Connection con) throws SQLException {

        String sqlActualizarDriver = "UPDATE driver SET numDriver = ?" +
                "WHERE nombre = ? AND apellido = ? AND numDriver = ? ";

        try(PreparedStatement ps = con.prepareStatement(sqlActualizarDriver)) {

            ps.setString(1,nombre);
            ps.setString(2,apellido);
            ps.setInt(3, numDriver);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
