package view.lugar;

import javax.swing.*;
import java.awt.*;

/**
 * Clase AddLugarView
 * Formulario para dar de alta un nuevo Lugar.
 */
public class AddLugarView extends JFrame {

    // ==================== ATRIBUTOS ====================

    private final JButton    btnAdd      = new JButton("Añadir");
    private final JButton    btnCancelar = new JButton("Cancelar");

    private final JTextField idLugar    = new JTextField(10);
    private final JTextField cp         = new JTextField(10);
    private final JTextField ciudad     = new JTextField(10);
    private final JTextField ubicacion  = new JTextField(10);

    // ==================== CONSTRUCTOR ====================

    public AddLugarView() {
        setTitle("Aucorsa - Añadir Lugar");
        setSize(320, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        camposPanel.add(new JLabel("ID Lugar:"));  camposPanel.add(idLugar);
        camposPanel.add(new JLabel("CP:"));        camposPanel.add(cp);
        camposPanel.add(new JLabel("Ciudad:"));    camposPanel.add(ciudad);
        camposPanel.add(new JLabel("Ubicación:")); camposPanel.add(ubicacion);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.add(btnAdd);
        botonesPanel.add(btnCancelar);

        add(camposPanel,   BorderLayout.CENTER);
        add(botonesPanel,  BorderLayout.SOUTH);
        setVisible(true);
    }

    // ==================== GETTERS ====================

    public JTextField getIdLugar()   { return idLugar;   }
    public JTextField getCp()        { return cp;        }
    public JTextField getCiudad()    { return ciudad;    }
    public JTextField getUbicacion() { return ubicacion; }

    public JButton getBtnAdd()      { return btnAdd;      }
    public JButton getBtnCancelar() { return btnCancelar; }
}
