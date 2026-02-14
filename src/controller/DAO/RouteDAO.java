package controller.DAO;

import model.Driver;
import model.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteDAO {

    /**
     * Metodo para insertar la ruta en la tabla BDP que es la ruta
     * @param registro Variable de Bus clave principal de la tabla Bus
     * @param numeroConductor Variable de Conductor clave principal de la tabla Conductor
     * @param idLugar Variable de Lugar clave principal de la tabla Lugar
     * @param diaSemana Variable String para el los días de la semana de las rutas
     * @param con Establezco el parámetro de la conexión y así no tiene que estar en el metodo
     * @return
     * @throws SQLException
     */

    public boolean insertRoute(String registro, int numeroConductor, int idLugar, String diaSemana, Connection con) throws SQLException{
        String sqlInsertarRuta = "INSERT INTO BDP (registro,numeroCoductor, idLugar, diaSemana) VALUE (?,?,?,?)";

        //Preparar la conexión con la base de datos para la consulta
        try (PreparedStatement ps = con.prepareStatement(sqlInsertarRuta)) {

            ps.setString(1, registro);
            ps.setInt(2, numeroConductor);
            ps.setInt(3, idLugar);
            ps.setString(4, diaSemana);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
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

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo para la consulta de Rutas de la tabla BDP
     * @param numConductor El parametro que le pedimos al usuario para consultar la tabla
     * @param con Establezco la conexión con la base de datos
     * @return
     * @throws SQLException
     */
    public Route consultRoute(int numConductor, Connection con) throws SQLException {

        String sqlConsultaRuta = "select registro, numeroConductor, idLugar, diaSEmana from BDP WHERE numConductor = ?";

        try (PreparedStatement ps = con.prepareStatement(sqlConsultaRuta)){


            ps.setInt(1,numConductor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                Route r = new Route();

                r.setRegistro(rs.getString("Registro"));
                r.setNumeroConductor(rs.getInt("NumeroConductor"));
                r.setIdLugar(rs.getInt("idLugar"));
                r.setDiaSemana(rs.getString("DiaSemana"));
                return r;
            }
            return null;

        } catch (SQLException e) {
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

        String sqlActualizarRuta = "UPDATE BDP SET diaSemana = ?" +
                "WHERE registro = ? AND numdriver = ? AND idLugar = ?";

        try(PreparedStatement ps = con.prepareStatement(sqlActualizarRuta)) {

            ps.setString(1,Dia);
            ps.setString(2,registro);
            ps.setInt(3, numeroConductor);
            ps.setInt(4, idLugar);

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
