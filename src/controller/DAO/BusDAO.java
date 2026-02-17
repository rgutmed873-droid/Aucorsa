package controller.DAO;

import controller.DB.ConexionDB;
import model.Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusDAO {

    /**
     * Metodo para insertar un bus
     * @param registro Parametro para el registro del bus
     * @param tipo Parametro para el tipo de bus
     * @param licencia Parametro para la licencia del bus
     * @param con Establezco conexión con la base de datos
     * @return Devuelve una inserción en la base de datos de la tabla Bus
     * @throws SQLException
     */
    public boolean insertBus(String registro, String tipo, String licencia, Connection con) throws SQLException{
        String sqlinsertarBus = "INSERT INTO Bus (register, type, license) VALUES (?, ?, ?)";

        //Preparar para hacer la conexión de la consulta
        try (PreparedStatement ps = con.prepareStatement(sqlinsertarBus)){

            //Setear los datos para la consulta
            ps.setString(1,registro);
            ps.setString(2,tipo);
            ps.setString(3,licencia);


            return ps.executeUpdate() > 0;



        }catch (RuntimeException e){
            throw new RuntimeException("Error al insertar el bus",e);
        }
    }

    /**
     * Metodo de consulta para ver los buses que hay
     * @return
     * @throws SQLException
     */
    public ArrayList<Bus> consultBus() throws SQLException {

        String sqlConsultaBus = "SELECT  * FROM Bus";
        ArrayList<Bus> buses = new ArrayList<>();

        //Establecer conexión con la base de datos
        try (Connection con = ConexionDB.getConexion()){
            //Preparar para hacer la conexión de la consulta
            PreparedStatement ps = con.prepareStatement(sqlConsultaBus);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                //Nueva instancia para la consulta de los buses
                Bus busConsult = new Bus();
                //Datos que se consiguen de la consulta
                busConsult.setRegistro(rs.getString("registro"));
                busConsult.setTipo(rs.getString("tipo"));
                busConsult.setLicencia(rs.getString("licencia"));

                //Añadir a la array list de los buses para poder ver todos los datos de la consulta
                buses.add(busConsult);
            }
            return buses;

        } catch (SQLException e) {
            System.out.println("Error en la consulta de BBDD");
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo para borrar o eliminar un autobús
     * @param registro
     * @param con
     * @return
     */
    public boolean deleteBus(String registro,Connection con){
        String sqlEliminarBus = "DELETE FROM bus WHERE registro = ? ";
        //Preparar la conexion para la consulta
        try (PreparedStatement ps = con.prepareStatement(sqlEliminarBus)){
            //SETTERS de los datos
            ps.setString(1, registro);


            return ps.executeUpdate() >0;


        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el bus",e);
        }
    }

    /**
     * Metodo para actualizar la tabla del autobus
     * @param registro
     * @param tipo
     * @param licencia
     * @param con
     * @return
     * @throws SQLException
     */
    public boolean updateBus(String registro, String tipo, String licencia, Connection con) throws SQLException {

        String sqlActualizarBus = "UPDATE bus " +
                "SET registro = ?,  tipo = ?, licencia = ? " +
                "WHERE registro = ? ";
        //Preparar la conexion para la consulta
        try(PreparedStatement ps = con.prepareStatement(sqlActualizarBus)) {
            //SETTERS DE LOS DATOS
            ps.setString(1,registro);
            ps.setString(2,tipo);
            ps.setString(3, licencia);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            System.out.println("Error al actualizar bus");
            throw new RuntimeException(e);
        }
    }
}
