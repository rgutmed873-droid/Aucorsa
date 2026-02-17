package controller.DAO;

import controller.DB.ConexionDB;
import model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverDAO {

    private Connection con;

    /**
     * Metodo para consultar los conductores
     * @return El listado de los conductores que hay en la base de datos
     * @throws SQLException
     */
    public ArrayList<Driver> consultDrivers(Connection con) throws SQLException {

        String sqlConsultaConductor = "SELECT * FROM driver";
        ArrayList<Driver> drivers = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sqlConsultaConductor)){

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
     * Metodo para insertar un conductor
     * @param nombre
     * @param apellido
     * @param numDriver
     * @param con
     * @return
     * @throws SQLException
     */
    public boolean insertDriver(String nombre, String apellido, int numDriver, Connection con) {
        String sqlInsertarConductor = "INSERT INTO CONDUCTOR (nombre, apellido, numeroConductor) VALUES (?, ?,?)";

        try (PreparedStatement ps = con.prepareStatement(sqlInsertarConductor)){

            ps.setString(1, nombre);
            ps.setString(2,apellido);
            ps.setInt(3, numDriver);

            return ps.executeUpdate() > 0;


        } catch (Exception e) {
            throw new RuntimeException("Error al insertar",e);
        }
    }

    /**
     * Metodo para borrar o eliminar un Conductor
     * @param numDriver
     * @param con
     * @return
     */
    public boolean deleteDriver(int numDriver,Connection con){
        String sqlEliminarDriver = "DELETE FROM driver WHERE numdriver = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarDriver)){

            ps.setInt(1, numDriver);

            return ps.executeUpdate()>0;


        }catch (Exception e){
            throw new RuntimeException("Error al eliminar",e);
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
    public boolean updateDriver(String nombre, String apellido, int numDriver, Connection con) {

        String sqlActualizarDriver = "UPDATE driver " +
                "SET nombre = ?, apellido = ? " +
                "WHERE numDriver = ? ";

        try(PreparedStatement ps = con.prepareStatement(sqlActualizarDriver)) {

            ps.setString(1,nombre);
            ps.setString(2,apellido);
            ps.setInt(3, numDriver);

            return ps.executeUpdate() >0;


        }catch (Exception e){
            throw new RuntimeException("Error al actualizar",e);
        }
    }
}
