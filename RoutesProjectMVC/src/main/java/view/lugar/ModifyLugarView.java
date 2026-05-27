package view.lugar;

import javax.swing.*;
import java.awt.*;

/**
 * Clase ModifyLugarView
 * Formulario para modificar un Lugar existente.
 * El ID se muestra como etiqueta (no editable, es la clave primaria).
 */
public class ModifyLugarView extends JFrame {

    // ==================== ATRIBUTOS ====================

    private final JButton btnGuardar  = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final JLabel     idLugarValor;
    private final JTextField cp        = new JTextField(10);
    private final JTextField ciudad    = new JTextField(10);
    private final JTextField ubicacion = new JTextField(10);

    // ==================== CONSTRUCTOR ====================

    public ModifyLugarView(int idLugar, String cpActual,
                           String ciudadActual, String ubicacionActual) {
        setTitle("Aucorsa - Modificar Lugar");
        setSize(320, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        idLugarValor = new JLabel(String.valueOf(idLugar));
        cp       .setText(cpActual);
        ciudad   .setText(ciudadActual);
        ubicacion.setText(ubicacionActual);

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        camposPanel.add(new JLabel("ID Lugar:"));  camposPanel.add(idLugarValor);
        camposPanel.add(new JLabel("CP:"));        camposPanel.add(cp);
        camposPanel.add(new JLabel("Ciudad:"));    camposPanel.add(ciudad);
        camposPanel.add(new JLabel("Ubicación:")); camposPanel.add(ubicacion);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnCancelar);

        add(camposPanel,  BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // ==================== GETTERS ====================

    public int getIdLugar()         { return Integer.parseInt(idLugarValor.getText()); }
    public JTextField getCp()       { return cp;        }
    public JTextField getCiudad()   { return ciudad;    }
    public JTextField getUbicacion(){ return ubicacion; }

    public JButton getBtnGuardar()  { return btnGuardar;  }
    public JButton getBtnCancelar() { return btnCancelar; }
}
