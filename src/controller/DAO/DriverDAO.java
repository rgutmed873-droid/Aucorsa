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
        //Variable que se le pasa al prepared para la consulta
        String sqlConsultaConductor = "SELECT * FROM driver";

        //ArrayList de conductores en la que vamos a consultar los conductores que tenemos en nuestra tabla
        ArrayList<Driver> drivers = new ArrayList<>();
        //Preparamos la conexión para hacer la consulta
        try (PreparedStatement ps = con.prepareStatement(sqlConsultaConductor)){
            //Resultado de la conexión de la consulta
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Driver driverConsult = new Driver();

                driverConsult.setNombre(rs.getString("nombre"));
                driverConsult.setApellidos(rs.getString("apellidos"));
                driverConsult.setNumeroConductor(rs.getInt("numero_conductor"));

                drivers.add(driverConsult);

            }

            return drivers; //Devuelvo a los conductores que se han consultado

        } catch (SQLException e) {
            System.out.println("Error al consultar la BBDD.");
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo para insertar un conductor
     * @param nombre Parametro para el nombre del conductor
     * @param apellido Parametro para el apellido del conductor
     * @param numDriver Parametro para el número que tiene el conductor
     * @param con Establezco la conexión con la base de datos
     * @return Devuelve una inserción en la base de dato ya que se ha modificado la tabla
     * @throws SQLException
     */
    public boolean insertDriver(String nombre, String apellido, int numDriver, Connection con) {
        String sqlInsertarConductor = "INSERT INTO CONDUCTOR (nombre, apellido, numeroConductor) VALUES (?, ?,?)";
        //Preparamos la conexión para hacer la consulta
        try (PreparedStatement ps = con.prepareStatement(sqlInsertarConductor)){
            //SETEAMOS LOS DATOS
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
     * @param numDriver Parametro para el número del conductor que pido para eliminar un conductor de la tabla
     * @param con Establezco la conexión con la base de datos
     * @return Devuelve la actualización que se ha realizado en la tabla quees eliminar un conductor
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
     * @param nombre Parametro para actualizar el nombre
     * @param apellido Parametro para actualizar el apellido
     * @param numDriver Parametro para actualizar el número de conductor
     * @param con Establezco conexión con la base de datos
     * @return Devuelvo la actualización que se ha hecho en la tabla de cambiar conductor
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
