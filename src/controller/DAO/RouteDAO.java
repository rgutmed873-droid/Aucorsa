package controller.DAO;

import controller.DB.ConexionDB;
import model.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteDAO {

    /**
     * Metodo para insertar la ruta
     * @param registro
     * @param numDriver
     * @param idLugar
     * @param diaSemana
     * @param con
     * @return
     * @throws SQLException
     */
    public boolean insertRoute(String registro, int numDriver, int idLugar, String diaSemana, Connection con) throws SQLException{
        String sqlInsertarRuta = "INSERT INTO BDP (registro,numeroCoductor, idLugar, diaSemana) VALUE (?,?,?,?)";

        //Preparar la conexión con la base de datos para la consulta
        try (PreparedStatement ps = con.prepareStatement(sqlInsertarRuta)) {

            ps.setString(1, registro);
            ps.setInt(2, numDriver);
            ps.setInt(3, idLugar);
            ps.setString(4, diaSemana);

            return ps.executeUpdate() > 0;



        } catch (Exception e) {
            throw new RuntimeException("Error al insertar la ruta",e);
        }

    }

    /**
     * Metodo para eliminar una ruta
     * @param numeroConductor Variable de Conductor clave principal de la tabla Conductor
     * @param con Establezco el parámetro de la conexión y así no tiene que estar en el metodo
     * @return
     */

    public boolean deleteRoute(int numeroConductor,Connection con){
        String sqlEliminarRuta = "DELETE FROM BDP WHERE registro = ? AND numeroCoductor = ? AND idLugar = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarRuta)){

            ps.setInt(1,numeroConductor);

            return ps.executeUpdate() > 0;


        }catch (Exception e){
            throw new RuntimeException("Error al eliminar la ruta",e);
        }
    }

    /**
     * Metodo para consultar la tabla de las rutas
     * @return
     * @throws SQLException
     */
    public ArrayList<Route> consultRoute() throws SQLException {

        String sqlConsultaRuta = "SELECT  * FROM BDP";
        ArrayList<Route> routes = new ArrayList<>();
        try (Connection con = ConexionDB.getConexion()){

            PreparedStatement ps = con.prepareStatement(sqlConsultaRuta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                Route routeConsult = new Route();

                routeConsult.setRegistro(rs.getString("Registro"));
                routeConsult.setNumeroConductor(rs.getInt("NumeroConductor"));
                routeConsult.setIdLugar(rs.getInt("idLugar"));
                routeConsult.setDiaSemana(rs.getString("DiaSemana"));
                routes.add(routeConsult);

            }

            return routes;

        } catch (SQLException e) {
            System.out.println("Error al consultar las rutas de la BBDD");
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo para actualizar las Rutas
     * @param registro
     * @param numeroConductor
     * @param idLugar
     * @param Dia
     * @param con
     * @return
     * @throws SQLException
     */
    public boolean updateRoute(String registro, int numeroConductor, int idLugar, String Dia, Connection con) throws SQLException {

        String sqlActualizarRuta = "UPDATE BDP" +
                "SET diaSemana = ?" +
                "WHERE diaSemana = ?";

        try(PreparedStatement ps = con.prepareStatement(sqlActualizarRuta)) {

            ps.setString(1,Dia);
            ps.setString(2,registro);
            ps.setInt(3, numeroConductor);
            ps.setInt(4, idLugar);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            System.out.println("Error al actualizar ruta");
            throw new RuntimeException(e);
        }
    }

}
