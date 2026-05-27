package view.routes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase RoutePanel
 * Panel de la sección de rutas.
 * Muestra una tabla con todas las rutas (Bus, Conductor, Lugar, Día).
 */
public class RoutePanel extends JPanel {

    // ==================== ATRIBUTOS ====================

    private JTable            tablaVista;
    private DefaultTableModel modeloTabla;

    // ==================== CONSTRUCTOR ====================

    public RoutePanel() {
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(
                new String[]{"Matrícula Bus", "Nº Conductor", "ID Lugar", "Día semana"}, 0) {
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
