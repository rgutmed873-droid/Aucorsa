package controller.lugar;

import controller.dao.LugarDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Lugar;
import view.lugar.ModifyLugarView;

import javax.swing.*;
import java.sql.Connection;

/**
 * Clase ModifyLugarController
 * Controlador del formulario de modificación de lugares.
 */
public class ModifyLugarController {

    // ==================== ATRIBUTOS ====================

    private final ModifyLugarView view;
    private final LugarController lugarController;

    // ==================== CONSTRUCTOR ====================

    public ModifyLugarController(ModifyLugarView view, LugarController lugarController) {
        this.view            = view;
        this.lugarController = lugarController;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnGuardar() .addActionListener(e -> confirmarModificacion());
    }

    // ==================== MÉTODOS ====================

    private void confirmarModificacion() {
        String cp        = view.getCp()       .getText().trim();
        String ciudad    = view.getCiudad()   .getText().trim();
        String ubicacion = view.getUbicacion().getText().trim();

        if (cp.isEmpty() || ciudad.isEmpty() || ubicacion.isEmpty()) {
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

        Lugar lugar = new Lugar(view.getIdLugar(), cp, ciudad, ubicacion);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (LugarDAO.modificarLugar(con, lugar)) {
                JOptionPane.showMessageDialog(view, "Lugar modificado correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                lugarController.cargarLugares();
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
