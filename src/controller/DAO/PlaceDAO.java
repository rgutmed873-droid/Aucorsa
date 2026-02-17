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
     * Metodo para insertar un nuevo Lugar en la tabla place
     * @param place Atributo para obtener los datos del modelo Lugar
     * @return
     * @throws SQLException
     */
    public boolean insertPlace(Place place, Connection con) throws SQLException {

        String sqlInsertarLugar = "INSERT INTO place (idlugar,ciudad,sitio,cp) VALUES (?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sqlInsertarLugar)){

            ps.setInt(1, place.idLugar);
            ps.setString(2, place.ciudad);
            ps.setString(3, place.sitio);
            ps.setInt(4, place.cp);

            int filaAfectada = ps.executeUpdate();
            if (filaAfectada > 0) {
                return true;
            }else return false;
        }catch (Exception e){
            System.out.println("Error al insertar lugar");
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo para eliminar un lugar de la tabla
     * @param idLugar
     * @param con
     * @return
     */
    public boolean deletePlace(int idLugar, Connection con){
        String sqlEliminarPlace = "DELETE FROM place WHERE idLugar = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarPlace)){

            ps.setInt(1,idLugar);

           int filaAfectada = ps.executeUpdate();

           if (filaAfectada > 0) {
                return true;
           } return  false;

        }catch (Exception e){
            System.out.println("Error al eliminar lugar");
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo para consultar los lugares
     * @return
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
     * @param idLugar
     * @param ciudad
     * @param sitio
     * @param cp
     * @param con
     * @return
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

            int filaAfectada = ps.executeUpdate();

            if (filaAfectada > 0) {
                return true;
            } else return false;
        }catch (Exception e){
            System.out.println("Error al actualizar lugar");
            throw new RuntimeException(e);
        }
    }
}
