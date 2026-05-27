package controller.route;

import controller.dao.RoutesDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Routes;
import view.routes.ModifyRouteView;

import javax.swing.*;
import java.sql.Connection;

/**
 * Clase ModifyRouteController
 * Controlador del formulario de modificación de rutas.
 * Solo permite cambiar el día de la semana (la clave primaria no cambia).
 */
public class ModifyRouteController {

    // ==================== ATRIBUTOS ====================

    private final ModifyRouteView view;
    private final controller.route.RouteController routeController;

    // ==================== CONSTRUCTOR ====================

    public ModifyRouteController(ModifyRouteView view, controller.route.RouteController routeController) {
        this.view            = view;
        this.routeController = routeController;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnGuardar() .addActionListener(e -> confirmarModificacion());
    }

    // ==================== MÉTODOS ====================

    private void confirmarModificacion() {
        String dia = view.getDiaSeleccionado();

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar los cambios?",
                "Confirmar modificación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            view.dispose();
            return;
        }

        Routes ruta = new Routes(
                view.getMatricula(),
                view.getNumConductor(),
                view.getIdLugar(),
                dia
        );

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (RoutesDAO.modificarRuta(con, ruta)) {
                JOptionPane.showMessageDialog(view, "Ruta modificada correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                routeController.cargarRutas();
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,
                        AucorsaErrorCode.DB_UPDATE_ERROR.getMessage(),
                        "Error [" + AucorsaErrorCode.DB_UPDATE_ERROR.getCode() + "]",
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
