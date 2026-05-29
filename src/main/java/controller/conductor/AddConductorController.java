package controller.conductor;

import controller.dao.ConductorDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Conductor;
import view.conductor.AddConductorView;

import javax.swing.*;
import java.sql.Connection;

/**
 * Clase AddConductorController
 * Controlador del formulario de alta de conductores.
 */
public class AddConductorController {

    // ==================== ATRIBUTOS ====================

    private final AddConductorView      view;
    private final ConductorController   conductorController;
    private final ConductorDAO          conductorDAO;

    // ==================== CONSTRUCTOR ====================

    public AddConductorController(AddConductorView view,
                                  ConductorController conductorController,
                                  ConductorDAO conductorDAO) {
        this.view               = view;
        this.conductorController = conductorController;
        this.conductorDAO       = conductorDAO;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnAdd()     .addActionListener(e -> confirmarGuardado());
    }

    // ==================== MÉTODOS ====================

    private void confirmarGuardado() {
        String sNum     = view.getNumConductor().getText().trim();
        String nombre   = view.getNombre()      .getText().trim();
        String apellido = view.getApellidos()   .getText().trim();

        // Validación: campos vacíos
        if (sNum.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            mostrarError(AucorsaErrorCode.VALIDATION_EMPTY_FIELDS);
            return;
        }

        // Validación: número válido
        int numDriver;
        try {
            numDriver = Integer.parseInt(sNum);
        } catch (NumberFormatException ex) {
            mostrarError(AucorsaErrorCode.VALIDATION_INVALID_NUMBER);
            return;
        }

        // Validación: número positivo
        if (numDriver <= 0) {
            mostrarError(AucorsaErrorCode.VALIDATION_NEGATIVE_NUMBER);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar el conductor?",
                "Confirmar guardado", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        Conductor conductor = new Conductor(numDriver, nombre, apellido, null);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (conductorDAO.insertarConductor(con, conductor)) {
                JOptionPane.showMessageDialog(view, "Conductor guardado correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                conductorController.cargarConductores();
                view.dispose();
            } else {
                mostrarError(AucorsaErrorCode.DB_INSERT_ERROR);
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarError(AucorsaErrorCode code) {
        JOptionPane.showMessageDialog(view,
                code.getMessage(),
                "Error [" + code.getCode() + "]",
                JOptionPane.ERROR_MESSAGE);
    }
}