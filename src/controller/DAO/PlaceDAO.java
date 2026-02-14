package controller.DAO;

import controller.DB.ConexionDB;
import model.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaceDAO {


    /**
     * Metodo para insertar un nuevo Lugar en la tabla place
     * @param place Atributo para obtener los datos del modelo Lugar
     * @return
     * @throws SQLException
     */
    public boolean insertPlace(Place place) throws SQLException {

        String sqlInsertarLugar = "INSERT INTO place (idlugar,ciudad,sitio,cp) VALUES (?,?,?,?)";

        try (Connection con = ConexionDB.getConexion()){
            PreparedStatement ps = con.prepareStatement(sqlInsertarLugar);

            ps.setInt(1, place.idLugar);
            ps.setString(2, place.ciudad);
            ps.setString(3, place.sitio);
            ps.setInt(4, place.cp);

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Metodo para eliminar un lugar de la tabla
     * @param idLugar
     * @param ciudad
     * @param sitio
     * @param cp
     * @param con
     * @return
     */
    public boolean deletePlace(int idLugar, String ciudad, String sitio, int cp,Connection con){
        String sqlEliminarPlace = "DELETE FROM place WHERE idLugar = ? AND ciudad = ? AND sitio = ? AND cp = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarPlace)){

            ps.setInt(1,idLugar);
            ps.setString(2,ciudad);
            ps.setString(3,sitio);
            ps.setInt(4,cp);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo para consultar los lugares
     * @param idLugar
     * @param con
     * @return
     * @throws SQLException
     */
    public Place consultPlace(int idLugar, Connection con) throws SQLException {

        String sqlConsultaPlace = "select idLugar, ciudad, sitio, cp from place WHERE idplace = ?";

        try (PreparedStatement ps = con.prepareStatement(sqlConsultaPlace)){


            ps.setInt(1,idLugar);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                Place p = new Place();

                p.setIdLugar(rs.getInt("IDLugar"));
                p.setCiudad(rs.getString("Ciudad"));
                p.setSitio(rs.getString("Sitio"));
                p.setCp(rs.getInt("CP"));
                return p;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean updatePlace(int idLugar, String ciudad, String sitio, int cp, Connection con) throws SQLException {

        String sqlActualizarPlace = "UPDATE place SET idlugar = ?" +
                "WHERE idlugar = ? AND ciudad = ? AND sitio = ?  AND cp = ? ";

        try(PreparedStatement ps = con.prepareStatement(sqlActualizarPlace)) {

            ps.setInt(1, idLugar);
            ps.setString(2, ciudad);
            ps.setString(3, sitio);
            ps.setInt(4, cp);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
