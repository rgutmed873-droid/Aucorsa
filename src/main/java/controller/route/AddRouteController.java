package controller.route;

import controller.dao.RoutesDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Bus;
import model.Conductor;
import model.Lugar;
import model.Routes;
import view.routes.AddRouteView;

import javax.swing.*;
import java.sql.Connection;

/**
 * Clase AddRouteController
 * Controlador del formulario de alta de rutas.
 * Recoge la selección de los JComboBox de Bus, Conductor, Lugar y día.
 */
public class AddRouteController {

    // ==================== ATRIBUTOS ====================

    private final AddRouteView    view;
    private final controller.route.RouteController routeController;

    // ==================== CONSTRUCTOR ====================

    public AddRouteController(AddRouteView view, controller.route.RouteController routeController) {
        this.view            = view;
        this.routeController = routeController;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnAdd()     .addActionListener(e -> confirmarGuardado());
    }

    // ==================== MÉTODOS ====================

    private void confirmarGuardado() {
        Bus       bus       = view.getBusSeleccionado();
        Conductor conductor = view.getConductorSeleccionado();
        Lugar     lugar     = view.getLugarSeleccionado();
        String    dia       = view.getDiaSeleccionado();

        // Los combos pueden estar vacíos si la BD está vacía
        if (bus == null || conductor == null || lugar == null) {
            JOptionPane.showMessageDialog(view,
                    AucorsaErrorCode.VALIDATION_EMPTY_FIELDS.getMessage()
                    + "\nAsegúrate de que existen buses, conductores y lugares en la BD.",
                    "Error [" + AucorsaErrorCode.VALIDATION_EMPTY_FIELDS.getCode() + "]",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar la ruta?",
                "Confirmar guardado", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        Routes ruta = new Routes(
                bus.getMatricula(),
                conductor.getNumConductor(),
                lugar.getIdLugar(),
                dia
        );

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (RoutesDAO.insertarRuta(con, ruta)) {
                JOptionPane.showMessageDialog(view, "Ruta guardada correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                routeController.cargarRutas();
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,
                        AucorsaErrorCode.DB_INSERT_ERROR.getMessage(),
                        "Error [" + AucorsaErrorCode.DB_INSERT_ERROR.getCode() + "]",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }
}
