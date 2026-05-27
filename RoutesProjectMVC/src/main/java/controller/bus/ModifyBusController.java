package controller.bus;

import controller.dao.BusDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Bus;
import view.bus.ModifyBusView;

import javax.swing.*;
import java.sql.Connection;

/**
 * Clase ModifyBusController
 * Controlador del formulario de modificación de buses.
 */
public class ModifyBusController {

    // ==================== ATRIBUTOS ====================

    private final ModifyBusView view;
    private final BusController busController;

    // ==================== CONSTRUCTOR ====================

    public ModifyBusController(ModifyBusView view, BusController busController) {
        this.view          = view;
        this.busController = busController;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnGuardar() .addActionListener(e -> confirmarModificacion());
    }

    // ==================== MÉTODOS ====================

    private void confirmarModificacion() {
        String tipo     = view.getTipo()    .getText().trim();
        String licencia = view.getLicencia().getText().trim();

        if (tipo.isEmpty() || licencia.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    AucorsaErrorCode.VALIDATION_EMPTY_FIELDS.getMessage(),
                    "Error [" + AucorsaErrorCode.VALIDATION_EMPTY_FIELDS.getCode() + "]",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar los cambios?",
                "Confirmar modificación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            view.dispose();
            return;
        }

        Bus bus = new Bus(view.getMatricula(), tipo, licencia);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (BusDAO.modificarBus(con, bus)) {
                JOptionPane.showMessageDialog(view, "Bus modificado correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                busController.cargarBuses();
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

        view.dispose();
    }
}