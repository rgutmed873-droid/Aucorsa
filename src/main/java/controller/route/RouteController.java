package controller.route;

import controller.dao.BusDAO;
import controller.dao.LugarDAO;
import controller.dao.RoutesDAO;
import controller.db.ConnectionBBDD;
import controller.route.AddRouteController;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Bus;
import model.Conductor;
import model.Lugar;
import model.Routes;
import view.routes.AddRouteView;
import view.routes.ModifyRouteView;
import view.routes.RoutePanel;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase RouteController
 * Controlador de la sección de rutas.
 * Gestiona la carga de datos y las operaciones CRUD.
 * Para añadir una ruta carga previamente las listas de Bus, Conductor
 * y Lugar de la BD para rellenar los JComboBox.
 */
public class RouteController {

    // ==================== ATRIBUTOS ====================

    private final RoutePanel       routePanel;
    private List<Routes>           rutas       = new ArrayList<>();

    // Listas de entidades relacionadas (para los JComboBox)
    private List<Bus>              buses       = new ArrayList<>();
    private List<Conductor>        conductores = new ArrayList<>();
    private List<Lugar>            lugares     = new ArrayList<>();

    // ==================== CONSTRUCTOR ====================

    public RouteController(RoutePanel panel) {
        this.routePanel = panel;
        cargarRutas();
    }

    // ==================== MÉTODOS ====================

    /** Carga todas las rutas de la BD y refresca la tabla. */
    public void cargarRutas() {
        try (Connection con = ConnectionBBDD.getConexion()) {
            rutas = RoutesDAO.mostrarTodasLasRutas(con);
            if (rutas == null) rutas = new ArrayList<>();

            routePanel.getModeloTabla().setRowCount(0);
            for (Routes r : rutas) {
                routePanel.getModeloTabla().addRow(new Object[]{
                        r.getMatricula(), r.getNumConductor(),
                        r.getIdLugar(),   r.getDiaSemana()
                });
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(routePanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(routePanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga buses, conductores y lugares de la BD (necesarios para los JComboBox)
     * y abre el formulario de añadir ruta.
     */
    public void añadirRuta() {
        try (Connection con = ConnectionBBDD.getConexion()) {
            buses       = BusDAO.mostrarTodosLosBuses(con);
            // ConductorDAO es de instancia, se reutiliza el DAO compartido a través
            // del MainController → aquí se instancia uno local puntual
            controller.dao.ConductorDAO cDAO = new controller.dao.ConductorDAO();
            conductores = cDAO.mostrarTodosConductores(con);
            lugares     = LugarDAO.mostrarTodosLosLugares(con);
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(routePanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(routePanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }

        new AddRouteController(new AddRouteView(buses, conductores, lugares), this);
    }

    /** Elimina la ruta seleccionada en la tabla tras pedir confirmación. */
    public void eliminarRuta() {
        int fila = routePanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(routePanel,
                    "Debes seleccionar una ruta para eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula    = (String) routePanel.getModeloTabla().getValueAt(fila, 0);
        int    numConductor = (int)    routePanel.getModeloTabla().getValueAt(fila, 1);
        int    idLugar      = (int)    routePanel.getModeloTabla().getValueAt(fila, 2);

        int opcion = JOptionPane.showConfirmDialog(routePanel,
                "¿Estás seguro de que quieres eliminar esta ruta?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (RoutesDAO.eliminarRuta(con, matricula, numConductor, idLugar)) {
                JOptionPane.showMessageDialog(routePanel,
                        "Ruta eliminada correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                cargarRutas();
            } else {
                JOptionPane.showMessageDialog(routePanel,
                        AucorsaErrorCode.DB_DELETE_ERROR.getMessage(),
                        "Error [" + AucorsaErrorCode.DB_DELETE_ERROR.getCode() + "]",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(routePanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(routePanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Abre el formulario de modificación con los datos de la ruta seleccionada. */
    public void modificarRuta() {
        int fila = routePanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(routePanel,
                    "Debes seleccionar una ruta para modificar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula    = (String) routePanel.getModeloTabla().getValueAt(fila, 0);
        int    numConductor = (int)    routePanel.getModeloTabla().getValueAt(fila, 1);
        int    idLugar      = (int)    routePanel.getModeloTabla().getValueAt(fila, 2);
        String dia          = (String) routePanel.getModeloTabla().getValueAt(fila, 3);

        Routes ruta = new Routes(matricula, numConductor, idLugar, dia);
        new ModifyRouteController(new ModifyRouteView(ruta), this);
    }
}
