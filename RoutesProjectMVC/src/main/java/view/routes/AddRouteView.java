package view.routes;

import model.Bus;
import model.Conductor;
import model.Lugar;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Clase AddRouteView
 * Formulario para crear una nueva Ruta.
 *
 * Usa tres JComboBox (Bus, Conductor, Lugar) para que el usuario
 * seleccione entidades ya existentes en la BD, y un JComboBox
 * adicional para elegir el día de la semana.
 */
public class AddRouteView extends JFrame {

    // ==================== CONSTANTES ====================

    private static final String[] DIAS = {
        "Lunes", "Martes", "Miércoles", "Jueves",
        "Viernes", "Sábado", "Domingo"
    };

    // ==================== ATRIBUTOS ====================

    private final JButton btnAdd      = new JButton("Añadir");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final JComboBox<Bus>       comboBus       = new JComboBox<>();
    private final JComboBox<Conductor> comboConductor = new JComboBox<>();
    private final JComboBox<Lugar>     comboLugar     = new JComboBox<>();
    private final JComboBox<String>    comboDia       = new JComboBox<>(DIAS);

    // ==================== CONSTRUCTOR ====================

    /**
     * @param buses       Lista de buses disponibles en la BD
     * @param conductores Lista de conductores disponibles en la BD
     * @param lugares     Lista de lugares disponibles en la BD
     */
    public AddRouteView(List<Bus> buses, List<Conductor> conductores, List<Lugar> lugares) {
        setTitle("Aucorsa - Añadir Ruta");
        setSize(380, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Rellena los combos con los datos recibidos
        for (Bus b       : buses)       comboBus      .addItem(b);
        for (Conductor c : conductores) comboConductor.addItem(c);
        for (Lugar l     : lugares)     comboLugar    .addItem(l);

        /*
         * Los JComboBox muestran el toString() de cada objeto, por lo que
         * Bus, Conductor y Lugar ya tienen implementados toString() con
         * información identificativa (matrícula, numConductor, idLugar/ciudad).
         */

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        camposPanel.add(new JLabel("Bus:"));        camposPanel.add(comboBus);
        camposPanel.add(new JLabel("Conductor:")); camposPanel.add(comboConductor);
        camposPanel.add(new JLabel("Lugar:"));      camposPanel.add(comboLugar);
        camposPanel.add(new JLabel("Día semana:")); camposPanel.add(comboDia);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.add(btnAdd);
        botonesPanel.add(btnCancelar);

        add(camposPanel,  BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // ==================== GETTERS ====================

    /** Devuelve el Bus seleccionado en el combo, o null si está vacío. */
    public Bus getBusSeleccionado() {
        return (Bus) comboBus.getSelectedItem();
    }

    /** Devuelve el Conductor seleccionado en el combo, o null si está vacío. */
    public Conductor getConductorSeleccionado() {
        return (Conductor) comboConductor.getSelectedItem();
    }

    /** Devuelve el Lugar seleccionado en el combo, o null si está vacío. */
    public Lugar getLugarSeleccionado() {
        return (Lugar) comboLugar.getSelectedItem();
    }

    /** Devuelve el día de la semana seleccionado. */
    public String getDiaSeleccionado() {
        return (String) comboDia.getSelectedItem();
    }

    public JButton getBtnAdd()      { return btnAdd;      }
    public JButton getBtnCancelar() { return btnCancelar; }
}
