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
     * Metodo para insertar una ruta nueva en la tabla
     * @param route
     * @param con
     * @return
     * @throws SQLException
     */
    public boolean insertRoute(Route route, Connection con) throws SQLException{
        String sqlInsertarRuta = "INSERT INTO BDP (registro,numeroCoductor, idLugar, diaSemana) VALUE (?,?,?,?)";

        //Preparar la conexión con la base de datos para la consulta
        try (PreparedStatement ps = con.prepareStatement(sqlInsertarRuta)) {

            ps.setString(1, route.getRegistro());
            ps.setInt(2, route.getNumeroConductor());
            ps.setInt(3, route.getIdLugar());
            ps.setString(4, route.getDiaSemana());

            int filaAfectada = ps.executeUpdate();

            if (filaAfectada > 0) {
                return true;
            }else return false;

        } catch (Exception e) {
            System.out.println("Error al insertar ruta");
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo para eliminar una ruta
     * @param numeroConductor Variable de Conductor clave principal de la tabla Conductor
     * @param idLugar Variable de Lugar clave principal de la tabla Lugar
     * @param registro Variable de Bus clave principal de la tabla Bus
     * @param con Establezco el parámetro de la conexión y así no tiene que estar en el metodo
     * @return
     */

    public boolean deleteRoute(int numeroConductor, int idLugar, String registro, Connection con){
        String sqlEliminarRuta = "DELETE FROM BDP WHERE registro = ? AND numeroCoductor = ? AND idLugar = ? ";

        try (PreparedStatement ps = con.prepareStatement(sqlEliminarRuta)){

            ps.setInt(1,numeroConductor);
            ps.setInt(2,idLugar);
            ps.setString(3,registro);

            int filaAfectada = ps.executeUpdate();

            if (filaAfectada > 0) {
                return true;
            } else return false;

        }catch (Exception e){
            System.out.println("Error al eliminar ruta");
            throw new RuntimeException(e);
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

            int filaAfectada = ps.executeUpdate();

            if (filaAfectada > 0) {
                return true;
            }else return false;
        }catch (Exception e){
            System.out.println("Error al actualizar ruta");
            throw new RuntimeException(e);
        }
    }

}
