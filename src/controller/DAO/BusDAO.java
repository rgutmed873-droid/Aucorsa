package controller.DAO;

import model.Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusDAO {

    /**
     * Metodo para insertar Bus en la base de datos
     * @param bus Atributo para obtener los datos del modelo Bus
     * @param con Establezco el parámetro de la conexión y así no tiene que estar en el metodo
     * @return
     */
    public boolean insertBus(Bus bus, Connection con) {
        String sqlinsertarBus = "INSERT INTO Bus (register, type, license) VALUES (?, ?, ?)";

        //Preparar para hacer la conexión de la consulta
        try (PreparedStatement ps = con.prepareStatement(sqlinsertarBus)){

            //Setear los datos para la consulta
            ps.setString(1,bus.getRegistro());
            ps.setString(2,bus.getTipo());
            ps.setString(3,bus.getLicencia());

            // Devuelve un entero y como es mayor que 0 se actualiza la tabla del bus
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo para la consulta de buses
     * @param registro
     * @param con
     * @return
     * @throws SQLException
     */
    public Bus consultBus(String registro, Connection con) throws SQLException {

        String sqlConsultaBus = "select registro, tipo, licencia from Bus WHERE registro = ?";

        try (PreparedStatement ps = con.prepareStatement(sqlConsultaBus)){


            ps.setString(1,registro);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                Bus b = new Bus();

                b.setRegistro(rs.getString("nombre"));
                b.setTipo(rs.getString("apellido"));
                b.setLicencia(rs.getString("licencia"));

                return b;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteBus(String registro, String tipo, String licencia,Connection con){
        String sqlEliminarBus = "DELETE FROM bus WHERE registro = ? AND tipo = ? AND licencia = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarBus)){

            ps.setString(1, registro);
            ps.setString(2, tipo);
            ps.setString(3, licencia);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean updateBus(String registro, String tipo, String licencia, Connection con) throws SQLException {

        String sqlActualizarBus = "UPDATE bus SET registro = ?" +
                "WHERE registro = ? AND tipo = ? AND licencia = ? ";

        try(PreparedStatement ps = con.prepareStatement(sqlActualizarBus)) {

            ps.setString(1,registro);
            ps.setString(2,tipo);
            ps.setString(3, licencia);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
