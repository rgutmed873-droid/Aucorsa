package controller.DAO;

import controller.DB.ConexionDB;
import model.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceDAO {

    /**
     * Metodo para insertar un nuevo lugar
     * @param idLugar Parametro para el idLugar de la tabla place
     * @param ciudad Parametro para la ciudad de la tabla place
     * @param sitio Parametro para el sitio de la tabla
     * @param cp Parametro para el cp de la tabla
     * @param con Establezco conexión con la base datos
     * @return Devuelve la insercción en la tabla
     * @throws SQLException
     */
    public boolean insertPlace(int idLugar, String ciudad, String sitio, int cp, Connection con) throws SQLException {

        String sqlInsertarLugar = "INSERT INTO place (idlugar,ciudad,sitio,cp) VALUES (?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sqlInsertarLugar)){

            ps.setInt(1, idLugar);
            ps.setString(2, ciudad);
            ps.setString(3, sitio);
            ps.setInt(4, cp);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException("Error al insertar un nuevo lugar",e);
        }
    }

    /**
     * Metodo para eliminar un lugar de la tabla
     * @param idLugar Parametro para el idLugar para borrar datos de la tabla
     * @param con Establezco conexión con la base de datos
     * @return Devuelve que ha borrado una fila de la base de datos
     */
    public boolean deletePlace(int idLugar, Connection con){
        String sqlEliminarPlace = "DELETE FROM place WHERE idLugar = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarPlace)){

            ps.setInt(1,idLugar);

           return ps.executeUpdate() > 0;

        }catch (Exception e){
           throw new RuntimeException("Error al eliminar el lugar",e);
        }
    }

    /**
     * Metodo para consultar los lugares
     * @return Listado de los lugares a consultar en la base de datos
     * @throws SQLException
     */
    public ArrayList<Place> consultPlace() throws SQLException {

        String sqlConsultaPlace = "SELECT * FROM place";
        ArrayList<Place> places = new ArrayList<>();

        try (Connection con = ConexionDB.getConexion()){

            PreparedStatement ps = con.prepareStatement(sqlConsultaPlace);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                Place placeConsult = new Place();

                placeConsult.setIdLugar(rs.getInt("IDLugar"));
                placeConsult.setCiudad(rs.getString("Ciudad"));
                placeConsult.setSitio(rs.getString("Sitio"));
                placeConsult.setCp(rs.getInt("CP"));

                places.add(placeConsult);
            }
            return places;

        } catch (SQLException e) {
            System.out.println("Error al consultar los lugares");
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo para actualizar los lugares de la tabla
     * @param idLugar Parametro
     * @param ciudad Parametro
     * @param sitio Parametro
     * @param cp Parametro
     * @param con Establezco conexión con la base de datos
     * @return Cambio en la tabla
     * @throws SQLException
     */
    public boolean updatePlace(int idLugar, String ciudad, String sitio, int cp, Connection con) throws SQLException {

        String sqlActualizarPlace = "UPDATE place " +
                "SET idlugar = ?, ciudad = ?, sitio = ?, cp = ? " +
                "WHERE idlugar = ? ";

        try(PreparedStatement ps = con.prepareStatement(sqlActualizarPlace)) {

            ps.setInt(1, idLugar);
            ps.setString(2, ciudad);
            ps.setString(3, sitio);
            ps.setInt(4, cp);

            return ps.executeUpdate() > 0;


        }catch (Exception e){
           throw new RuntimeException("Error al actualizar el lugar",e);
        }
    }
}
