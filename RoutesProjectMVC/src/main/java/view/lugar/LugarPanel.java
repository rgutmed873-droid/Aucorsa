package view.lugar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase LugarPanel
 * Panel de la sección de lugares.
 * Muestra una tabla con todos los lugares cargados desde la base de datos.
 */
public class LugarPanel extends JPanel {

    // ==================== ATRIBUTOS ====================

    private JTable             tablaVista;
    private DefaultTableModel  modeloTabla;

    // ==================== CONSTRUCTOR ====================

    public LugarPanel() {
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "CP", "Ciudad", "Ubicación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaVista = new JTable(modeloTabla);
        tablaVista.setFillsViewportHeight(true);
        tablaVista.setRowSelectionAllowed(true);
        tablaVista.setColumnSelectionAllowed(false);
        tablaVista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tablaVista), BorderLayout.CENTER);
    }

    // ==================== GETTERS ====================

    public JTable            getTablaVista()  { return tablaVista;  }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}
