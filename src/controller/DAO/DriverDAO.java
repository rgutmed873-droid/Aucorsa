package controller.DAO;

import controller.DB.ConexionDB;
import model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {

    private Connection con;

    /**
     * Metodo para consultar los conductores
     * @return El listado de los conductores que hay en la base de datos
     * @throws SQLException
     */
    public ArrayList<Driver> consultDrivers() throws SQLException {

        String sqlConsultaConductor = "SELECT * FROM driver";
        ArrayList<Driver> drivers = new ArrayList<>();

        try (Connection con = ConexionDB.getConexion()){

            PreparedStatement ps = con.prepareStatement(sqlConsultaConductor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Driver driverConsult = new Driver();

                driverConsult.setNombre(rs.getString("nombre"));
                driverConsult.setApellidos(rs.getString("apellidos"));
                driverConsult.setNumeroConductor(rs.getInt("numero_conductor"));

                drivers.add(driverConsult);

            }

            return drivers;

        } catch (SQLException e) {
            System.out.println("Error al consultar la BBDD.");
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

            int filasAfectas= ps.executeUpdate();

            if (filasAfectas > 0){
                return true;
            } else return false;

        } catch (RuntimeException e) {
            System.out.println("Error al insertar en la BBDD");
            throw new RuntimeException(e);
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
        String sqlEliminarDriver = "DELETE FROM driver WHERE numdriver = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarDriver)){

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, numDriver);

            int filaAfectada = ps.executeUpdate();

            if (filaAfectada > 0){
                return true;
            }else return false;
        }catch (Exception e){
            System.out.println("Error al eliminar en la BBDD");
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

        String sqlActualizarDriver = "UPDATE driver " +
                "SET nombre = ?, apellido = ? " +
                "WHERE numDriver = ? ";

        try(PreparedStatement ps = con.prepareStatement(sqlActualizarDriver)) {

            ps.setString(1,nombre);
            ps.setString(2,apellido);
            ps.setInt(3, numDriver);

            int filaAfectada = ps.executeUpdate();

            if (filaAfectada > 0){
                return true;
            } else return false;

        }catch (Exception e){
            System.out.println("Error al actualizar en la BBDD");
            throw new RuntimeException(e);
        }
    }
}
