package controller.bus;

import controller.dao.BusDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Bus;
import view.bus.AddBusView;

import javax.swing.*;
import java.sql.Connection;

/**
 * Clase AddBusController
 * Controlador del formulario para dar de alta a los buses
 * Controla la inserción y validad en la Base de Datos usando excepciones propias
 */
public class AddBusController {

    // ==================== ATRIBUTOS ====================

    private final AddBusView    view;
    private final BusController busController;

    // ==================== CONSTRUCTOR ====================

    public AddBusController(AddBusView view, BusController busController) {
        this.view          = view;
        this.busController = busController;

        view.getBtnCancelar().addActionListener(e -> view.dispose());
        view.getBtnAdd()     .addActionListener(e -> confirmarGuardado());
    }

    // ==================== MÉTODOS ====================

    private void confirmarGuardado() {
        String matricula = view.getMatricula().getText().trim();
        String tipo      = view.getTipo()     .getText().trim();
        String licencia  = view.getLicencia() .getText().trim();

        // Validación: campos vacíos
        if (matricula.isEmpty() || tipo.isEmpty() || licencia.isEmpty()) {
            mostrarError(AucorsaErrorCode.VALIDATION_EMPTY_FIELDS);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(view,
                "¿Estás seguro de que quieres guardar el bus?",
                "Confirmar guardado", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        Bus bus = new Bus(matricula, tipo, licencia);

        // El try-with-resources cierra la Connection automáticamente al final del bloque llamando
        // a con.close(), y ese metodo declara throws SQLException por lo que saltaba un error
        // que se solucionaba añadiendo el ultimo catch (Exception e). Esto sucede en los futuros
        // try-with-resources de otras clases
        try (Connection con = ConnectionBBDD.getConexion()) {
            if (BusDAO.añadirBus(con, bus)) {
                JOptionPane.showMessageDialog(view, "Bus guardado correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                busController.cargarBuses();
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

        view.dispose();
    }

    /** Muestra un diálogo de error con el código y mensaje del enum. */
    private void mostrarError(AucorsaErrorCode code) {
        JOptionPane.showMessageDialog(view,
                code.getMessage(),
                "Error [" + code.getCode() + "]",
                JOptionPane.ERROR_MESSAGE);
    }
}