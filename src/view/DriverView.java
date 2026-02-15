package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DriverView extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtnumDriver;
    private JButton btnInsertar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JTable table;
    private DefaultTableModel model;

    public DriverView() {
        setTitle("Driver");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
        JPanel panelFormulario = new JPanel();

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelFormulario.add(txtApellidos);

        panelFormulario.add(new JLabel("Número Conductor:"));
        txtnumDriver = new JTextField();
        panelFormulario.add(txtnumDriver);

        btnInsertar = new JButton("Insertar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnInsertar);
        panelButtons.add(btnActualizar);
        panelButtons.add(btnEliminar);

        model = new DefaultTableModel();
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(panelFormulario, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    public void setBtnInsertar(JButton btnInsertar) {
        this.btnInsertar = btnInsertar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public JTextField getTxtApellidos() {
        return txtApellidos;
    }

    public void setTxtApellidos(JTextField txtApellidos) {
        this.txtApellidos = txtApellidos;
    }

    public JTextField getTxtnumDriver() {
        return txtnumDriver;
    }

    public void setTxtnumDriver(JTextField txtnumDriver) {
        this.txtnumDriver = txtnumDriver;
    }
}
