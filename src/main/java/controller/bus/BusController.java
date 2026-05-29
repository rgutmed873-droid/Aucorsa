package controller.bus;

import controller.dao.BusDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Bus;
import view.bus.AddBusView;
import view.bus.BusPanel;
import view.bus.ModifyBusView;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase BusController
 * Controlador de la sección de buses. Gestiona la carga de datos
 * y las operaciones CRUD usando excepciones propias.
 */
public class BusController {

    // ==================== ATRIBUTOS ====================

    private final BusPanel   busPanel;
    private List<Bus>        buses = new ArrayList<>();

    // ==================== CONSTRUCTOR ====================

    public BusController(BusPanel panel) {
        this.busPanel = panel;
        cargarBuses();
    }

    // ==================== MÉTODOS ====================

    /** Carga todos los buses de la BD y refresca la tabla. */
    public void cargarBuses() {
        try (Connection con = ConnectionBBDD.getConexion()) {
            buses = (List<Bus>) BusDAO.mostrarTodosLosBuses(con);
            busPanel.getModeloTabla().setRowCount(0);
            for (Bus b : buses) {
                busPanel.getModeloTabla().addRow(
                        new Object[]{b.getMatricula(), b.getTipo(), b.getLicencia()});
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(busPanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(busPanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Abre el formulario para añadir un nuevo bus. */
    public void añadirBus() {
        new AddBusController(new AddBusView(), this);
    }

    /** Elimina el bus seleccionado en la tabla tras pedir confirmación. */
    public void eliminarBus() {
        int fila = busPanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(busPanel,
                    AucorsaErrorCode.RECORD_NOT_FOUND.getMessage() + "\nSelecciona un bus primero.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = (String) busPanel.getModeloTabla().getValueAt(fila, 0);

        int opcion = JOptionPane.showConfirmDialog(busPanel,
                "¿Estás seguro de que quieres eliminar el bus " + matricula + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (BusDAO.eliminarBus(con, matricula)) {
                JOptionPane.showMessageDialog(busPanel,
                        "Bus eliminado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                cargarBuses();
            } else {
                JOptionPane.showMessageDialog(busPanel,
                        AucorsaErrorCode.DB_DELETE_ERROR.getMessage(),
                        "Error [" + AucorsaErrorCode.DB_DELETE_ERROR.getCode() + "]",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(busPanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(busPanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Abre el formulario de modificación con los datos del bus seleccionado. */
    public void modificarBus() {
        int fila = busPanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(busPanel,
                    AucorsaErrorCode.RECORD_NOT_FOUND.getMessage() + "\nSelecciona un bus primero.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = (String) busPanel.getModeloTabla().getValueAt(fila, 0);
        String tipo      = (String) busPanel.getModeloTabla().getValueAt(fila, 1);
        String licencia  = (String) busPanel.getModeloTabla().getValueAt(fila, 2);

        new ModifyBusController(new ModifyBusView(matricula, tipo, licencia), this);
    }
}