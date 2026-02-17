package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DriverView extends JFrame {

    //ATRIBUTOS PRINCIPALES
    //Campo de texto para los diferentes atributos
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtnumDriver;

    //Botones para disparar las acciones
    private JButton btnInsertar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    //Tabla visual donde se muestran los conductores
    private JTable table;

    //Modelo que almacena los datos que se mostrarán en la tabla
    private DefaultTableModel model;

    //Constructor de la vista y encargada de inicializar los componentes gráficos
    public DriverView() {

        //Establecer titulo de la ventana
        setTitle("Driver");
        //Tamaño de la ventana
        setSize(600,600);
        //Cerrar la aplicación cuando cierras la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    /**
     * Metodo para ejecutar los componentes de la vista del DriverView
     */
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

    // GETTERS AND SETTERS
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
