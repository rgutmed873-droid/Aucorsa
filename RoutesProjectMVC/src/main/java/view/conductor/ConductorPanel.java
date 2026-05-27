package view.conductor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase ConductorPanel
 * Panel de la sección de conductores.
 * Muestra una tabla con todos los conductores cargados desde la base de datos.
 */
public class ConductorPanel extends JPanel {

    // ==================== ATRIBUTOS ====================

    // Tabla visual donde se mostrarán los conductores
    JTable tablaVista = new JTable();

    // Modelo de datos que alimenta la tabla
    DefaultTableModel modeloTabla = new DefaultTableModel();

    // ==================== CONSTRUCTOR ====================

    public ConductorPanel() {

        setLayout(new BorderLayout());

        // Inicializa el modelo con las columnas de Conductor, sin filas iniciales
        modeloTabla = new DefaultTableModel(
                new String[]{"NumConductor", "Nombre", "Apellido"}, 0) {

            // Hace no editable ninguna celda de la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Asocia el modelo a la tabla
        tablaVista = new JTable(modeloTabla);

        // Configura el comportamiento de selección de la tabla
        tablaVista.setFillsViewportHeight(true);
        tablaVista.setRowSelectionAllowed(true);
        tablaVista.setColumnSelectionAllowed(false);
        tablaVista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Añade la tabla dentro de un JScrollPane para permitir desplazamiento
        add(new JScrollPane(tablaVista), BorderLayout.CENTER);
    }

    // ==================== GETTERS ====================

    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    public JTable getTablaVista() { return tablaVista; }
}