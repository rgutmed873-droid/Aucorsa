package controller.conductor;

import controller.DetailsDriverController;
import controller.dao.ConductorDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Conductor;
import view.conductor.AddConductorView;
import view.conductor.ConductorPanel;
import view.conductor.DetailsDriverView;
import view.conductor.ModifyConductorView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ConductorController
 * Controlador de la sección de conductores.
 */
public class ConductorController {

    // ==================== ATRIBUTOS ====================

    private final ConductorPanel       conductorPanel;
    private final ConductorController  self = this;
    private final ConductorDAO         conductorDAO;
    private List<Conductor>            conductores = new ArrayList<>();

    // ==================== CONSTRUCTOR ====================

    public ConductorController(ConductorPanel panel, ConductorDAO conductorDAO) {
        this.conductorPanel = panel;
        this.conductorDAO   = conductorDAO;

        // Doble clic en una fila abre la ficha detalle del conductor
        conductorPanel.getTablaVista().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 &&
                        conductorPanel.getTablaVista().getSelectedRow() != -1) {
                    new DetailsDriverController(
                            new DetailsDriverView(), self, conductorPanel,
                            conductores, conductorDAO);
                }
            }
        });

        cargarConductores();
    }

    // ==================== MÉTODOS ====================

    /**
     * Carga todos los conductores y refresca la tabla. Devuelve la lista actualizada.
     */
    public List<Conductor> cargarConductores() {
        try (Connection con = ConnectionBBDD.getConexion()) {
            conductores = conductorDAO.mostrarTodosConductores(con);
            if (conductores == null) conductores = new ArrayList<>();

            conductorPanel.getModeloTabla().setRowCount(0);
            for (Conductor c : conductores) {
                conductorPanel.getModeloTabla().addRow(
                        new Object[]{c.getNumConductor(), c.getNombre(), c.getApellido()});
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(conductorPanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(conductorPanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
        return conductores;
    }

    /** Abre el formulario para añadir un conductor. */
    public void añadirConductor() {
        new AddConductorController(new AddConductorView(), this, conductorDAO);
    }

    /** Elimina el conductor seleccionado en la tabla. */
    public void eliminarConductor() {
        int fila = conductorPanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(conductorPanel,
                    "Debes seleccionar un conductor para eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numConductor = (int) conductorPanel.getModeloTabla().getValueAt(fila, 0);

        int opcion = JOptionPane.showConfirmDialog(conductorPanel,
                "¿Eliminar el conductor nº " + numConductor + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (conductorDAO.eliminarConductor(con, numConductor)) {
                JOptionPane.showMessageDialog(conductorPanel,
                        "Conductor eliminado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                cargarConductores();
            } else {
                JOptionPane.showMessageDialog(conductorPanel,
                        AucorsaErrorCode.DB_DELETE_ERROR.getMessage(),
                        "Error [" + AucorsaErrorCode.DB_DELETE_ERROR.getCode() + "]",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(conductorPanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(conductorPanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Abre el formulario de modificación para el conductor seleccionado. */
    public void modificarConductor() {
        int fila = conductorPanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(conductorPanel,
                    "Debes seleccionar un conductor para modificar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int    num      = (int)    conductorPanel.getModeloTabla().getValueAt(fila, 0);
        String nombre   = (String) conductorPanel.getModeloTabla().getValueAt(fila, 1);
        String apellido = (String) conductorPanel.getModeloTabla().getValueAt(fila, 2);

        new ModifyConductorController(new ModifyConductorView(num, nombre, apellido), this, conductorDAO);
    }
}