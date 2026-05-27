package view.routes;

import model.Bus;
import model.Conductor;
import model.Lugar;
import model.Routes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Clase ModifyRouteView
 * Formulario para modificar una Ruta existente.
 *
 * La clave primaria (matricula + numConductor + idLugar) se muestra
 * como etiquetas no editables.  Solo se puede cambiar el día de la semana
 * mediante un JComboBox.
 *
 * Nota: si el requisito fuera cambiar también el bus/conductor/lugar
 * habría que borrar la ruta antigua e insertar una nueva (la PK cambia).
 * Para ese caso se usa directamente AddRouteView + eliminar la anterior.
 */
public class ModifyRouteView extends JFrame {

    // ==================== CONSTANTES ====================

    private static final String[] DIAS = {
        "Lunes", "Martes", "Miércoles", "Jueves",
        "Viernes", "Sábado", "Domingo"
    };

    // ==================== ATRIBUTOS ====================

    private final JButton btnGuardar  = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    // Campos de solo lectura (clave primaria compuesta)
    private final JLabel lblMatricula;
    private final JLabel lblConductor;
    private final JLabel lblLugar;

    // Único campo editable
    private final JComboBox<String> comboDia = new JComboBox<>(DIAS);

    // ==================== CONSTRUCTOR ====================

    /**
     * @param ruta La ruta que se va a modificar (para precargar los valores actuales)
     */
    public ModifyRouteView(Routes ruta) {
        setTitle("Aucorsa - Modificar Ruta");
        setSize(360, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        lblMatricula = new JLabel(ruta.getMatricula());
        lblConductor = new JLabel(String.valueOf(ruta.getNumConductor()));
        lblLugar     = new JLabel(String.valueOf(ruta.getIdLugar()));

        // Preselecciona el día actual en el combo
        comboDia.setSelectedItem(ruta.getDiaSemana());

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        camposPanel.add(new JLabel("Matrícula Bus:"));  camposPanel.add(lblMatricula);
        camposPanel.add(new JLabel("Nº Conductor:"));   camposPanel.add(lblConductor);
        camposPanel.add(new JLabel("ID Lugar:"));        camposPanel.add(lblLugar);
        camposPanel.add(new JLabel("Día semana:"));      camposPanel.add(comboDia);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnCancelar);

        add(camposPanel,  BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // ==================== GETTERS ====================

    public String getMatricula()      { return lblMatricula.getText();      }
    public int    getNumConductor()   { return Integer.parseInt(lblConductor.getText()); }
    public int    getIdLugar()        { return Integer.parseInt(lblLugar.getText());     }
    public String getDiaSeleccionado(){ return (String) comboDia.getSelectedItem();      }

    public JButton getBtnGuardar()  { return btnGuardar;  }
    public JButton getBtnCancelar() { return btnCancelar; }
}
