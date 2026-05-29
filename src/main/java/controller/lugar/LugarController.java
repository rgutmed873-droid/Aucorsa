package controller.lugar;

import controller.dao.LugarDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Lugar;
import view.lugar.AddLugarView;
import view.lugar.LugarPanel;
import view.lugar.ModifyLugarView;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase LugarController
 * Controlador de la sección de lugares.
 * Gestiona la carga de datos y las operaciones CRUD.
 */
public class LugarController {

    // ==================== ATRIBUTOS ====================

    private final LugarPanel  lugarPanel;
    private List<Lugar>       lugares = new ArrayList<>();

    // ==================== CONSTRUCTOR ====================

    public LugarController(LugarPanel panel) {
        this.lugarPanel = panel;
        cargarLugares();
    }

    // ==================== MÉTODOS ====================

    /** Carga todos los lugares de la BD y refresca la tabla. */
    public void cargarLugares() {
        try (Connection con = ConnectionBBDD.getConexion()) {
            lugares = LugarDAO.mostrarTodosLosLugares(con);
            if (lugares == null) lugares = new ArrayList<>();

            lugarPanel.getModeloTabla().setRowCount(0);
            for (Lugar l : lugares) {
                lugarPanel.getModeloTabla().addRow(new Object[]{
                        l.getIdLugar(), l.getCP(), l.getCiudad(), l.getUbicacion()
                });
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(lugarPanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(lugarPanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Abre el formulario para añadir un nuevo lugar. */
    public void añadirLugar() {
        new AddLugarController(new AddLugarView(), this);
    }

    /** Elimina el lugar seleccionado en la tabla tras pedir confirmación. */
    public void eliminarLugar() {
        int fila = lugarPanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(lugarPanel,
                    "Debes seleccionar un lugar para eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idLugar = (int) lugarPanel.getModeloTabla().getValueAt(fila, 0);

        int opcion = JOptionPane.showConfirmDialog(lugarPanel,
                "¿Estás seguro de que quieres eliminar el lugar " + idLugar + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) return;

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (LugarDAO.eliminarLugar(con, idLugar)) {
                JOptionPane.showMessageDialog(lugarPanel,
                        "Lugar eliminado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                cargarLugares();
            } else {
                JOptionPane.showMessageDialog(lugarPanel,
                        AucorsaErrorCode.DB_DELETE_ERROR.getMessage(),
                        "Error [" + AucorsaErrorCode.DB_DELETE_ERROR.getCode() + "]",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(lugarPanel, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(lugarPanel, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Abre el formulario de modificación con los datos del lugar seleccionado. */
    public void modificarLugar() {
        int fila = lugarPanel.getTablaVista().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(lugarPanel,
                    "Debes seleccionar un lugar para modificar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int    id        = (int)    lugarPanel.getModeloTabla().getValueAt(fila, 0);
        String cp        = (String) lugarPanel.getModeloTabla().getValueAt(fila, 1);
        String ciudad    = (String) lugarPanel.getModeloTabla().getValueAt(fila, 2);
        String ubicacion = (String) lugarPanel.getModeloTabla().getValueAt(fila, 3);

        new ModifyLugarController(new ModifyLugarView(id, cp, ciudad, ubicacion), this);
    }
}
