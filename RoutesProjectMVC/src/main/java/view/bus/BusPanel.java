package view.bus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase BusPanel
 * Panel de la sección de buses.
 * Muestra una tabla con todos los buses cargados desde la base de datos.
 */
public class BusPanel extends JPanel {

    // ==================== ATRIBUTOS ====================

    // Tabla visual donde se muestran los buses
    private JTable tablaVista = new JTable();

    // Modelo de datos que alimenta la tabla
    private DefaultTableModel modeloTabla;

    // ==================== CONSTRUCTOR ====================

    public BusPanel() {

        setLayout(new BorderLayout());

        // Inicializa el modelo con las columnas de Bus, sin filas iniciales
        modeloTabla = new DefaultTableModel(
                new String[]{"Matricula", "Tipo", "Licencia"}, 0) {

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

    // ==================== GETTERS Y SETTERS ====================

    public JTable getTablaVista() { return tablaVista; }
    public void setTablaVista(JTable tablaVista) { this.tablaVista = tablaVista; }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public void setModeloTabla(DefaultTableModel modeloTabla) { this.modeloTabla = modeloTabla; }
}