package controller.DAO;

import model.Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
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




}
