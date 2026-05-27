package controller.lugar;

import controller.dao.LugarDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Lugar;
import view.lugar.AddLugarView;

import javax.swing.*;
import java.sql.Connection;

/**
 * Clase AddLugarController
 * Controlador del formulario de alta de lugares.
 */
public class AddLugarController {

    // ==================== ATRIBUTOS ====================

    private final AddLugarView    view;
    private final LugarController lugarController;

    // ==================== CONSTRUCTOR ====================

    public AddLugarController(AddLugarView view, LugarController lugarController) {
        this.view            = view;
        this.lugarController = lugarController;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnAdd()     .addActionListener(e -> confirmarGuardado());
    }

    // ==================== MÉTODOS ====================

    private void confirmarGuardado() {
        String sId       = view.getIdLugar()  .getText().trim();
        String cp        = view.getCp()       .getText().trim();
        String ciudad    = view.getCiudad()   .getText().trim();
        String ubicacion = view.getUbicacion().getText().trim();

        // Validación: campos vacíos
        if (sId.isEmpty() || cp.isEmpty() || ciudad.isEmpty() || ubicacion.isEmpty()) {
            mostrarError(AucorsaErrorCode.VALIDATION_EMPTY_FIELDS);
            return;
        }

        // Validación: ID numérico
        int idLugar;
        try {
            idLugar = Integer.parseInt(sId);
        } catch (NumberFormatException ex) {
            mostrarError(AucorsaErrorCode.VALIDATION_INVALID_NUMBER);
            return;
        }

        if (idLugar <= 0) {
            mostrarError(AucorsaErrorCode.VALIDATION_NEGATIVE_NUMBER);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar el lugar?",
                "Confirmar guardado", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        Lugar lugar = new Lugar(idLugar, cp, ciudad, ubicacion);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (LugarDAO.añadirLugar(con, lugar)) {
                JOptionPane.showMessageDialog(view, "Lugar guardado correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                lugarController.cargarLugares();
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
