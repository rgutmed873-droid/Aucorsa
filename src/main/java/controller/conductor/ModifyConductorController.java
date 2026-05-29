package controller.conductor;

import controller.dao.ConductorDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Conductor;
import view.conductor.ModifyConductorView;

import javax.swing.*;
import java.sql.Connection;

/**
 * Clase ModifyConductorController
 * Controlador del formulario de modificación de conductores.
 */
public class ModifyConductorController {

    // ==================== ATRIBUTOS ====================

    private final ModifyConductorView  view;
    private final ConductorController  conductorController;
    private final ConductorDAO         conductorDAO;

    // ==================== CONSTRUCTOR ====================

    public ModifyConductorController(ModifyConductorView view,
                                     ConductorController conductorController,
                                     ConductorDAO conductorDAO) {
        this.view               = view;
        this.conductorController = conductorController;
        this.conductorDAO       = conductorDAO;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnGuardar() .addActionListener(e -> confirmarModificacion());
    }

    // ==================== MÉTODOS ====================

    private void confirmarModificacion() {
        String nombre   = view.getNombre() .getText().trim();
        String apellido = view.getApellido().getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    AucorsaErrorCode.VALIDATION_EMPTY_FIELDS.getMessage(),
                    "Error [" + AucorsaErrorCode.VALIDATION_EMPTY_FIELDS.getCode() + "]",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar los cambios?",
                "Confirmar modificación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        Conductor conductor = new Conductor(view.getNumConductor(), nombre, apellido, null);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (conductorDAO.modificarConductor(con, conductor)) {
                JOptionPane.showMessageDialog(view, "Conductor modificado correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                conductorController.cargarConductores();
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