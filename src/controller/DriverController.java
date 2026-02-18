package controller;

import controller.DAO.DriverDAO;
import model.Driver;
import view.DriverView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DriverController {
    //Atributos principales
    private DriverView view;
    private DriverDAO dao;
    private Connection con;

    //CONSTRUCTOR PRINCIPAL DEL DRIVER CONTROLLER
    public DriverController(DriverView view, DriverDAO dao,Connection con) {

        this.view = view;
        this.dao = dao;
        this.con = con;

        eventos();
        loadTable();
    }

    /**
     * Metodo que he puesto para cargar las opciones que se pueden hacer en la aplicación
     */
    private void eventos() {
        view.getBtnInsertar().addActionListener(e -> insertar());
        view.getBtnActualizar().addActionListener(e -> actualizar());
        view.getBtnEliminar().addActionListener(e -> eliminar());

        view.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cargarSeleccion();
            }
        });
    }

    /**
     * Metodo del botón eliminar driver pero en el controlador que es el encargado de todo
     */
    private void eliminar() {

        try {

            int numero = Integer.parseInt(view.getTxtnumDriver().getText());

            if (dao.deleteDriver(numero,con)){
                JOptionPane.showMessageDialog(view,"Eliminado correctamente");
                loadTable();
                limpiarCampos();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, " El número del conductor tiene que ser un entero");
        }

    }

    /**
     * Metodo del botón actualizar de driver pero el controller es el que hace todo
     */
    private void actualizar() {

        try{
            String nombre = view.getTxtNombre().getText();
            String apellido = view.getTxtApellidos().getText();
            int numDriver = Integer.parseInt(view.getTxtnumDriver().getText());

           if(dao.updateDriver(nombre,apellido,numDriver,con)){
               JOptionPane.showMessageDialog(view,"Actualizado correctamente");
               loadTable();
               limpiarCampos();

           }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "El número del conductor tiene que ser un entero");
        }
    }

    /**
     * Metodo del botón insertar de driver
     */
    private void insertar() {

        try{
            String nombre = view.getTxtNombre().getText();
            String apellido = view.getTxtApellidos().getText();

            if (nombre.isEmpty() || apellido.isEmpty()){
                JOptionPane.showMessageDialog(view,"Campos vacíos");
                return;
            }

            int numero = Integer.parseInt(view.getTxtnumDriver().getText());

            if (dao.insertDriver(nombre,apellido,numero,con)){
                JOptionPane.showMessageDialog(view,"Driver insertado");
                loadTable();
                limpiarCampos();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "El número del conductor debeser un entero");
        }

    }

    /**
     * Metodo para cargar los datos que tiene la tabla
     */
    private void loadTable() {

        DefaultTableModel model = view.getModel();
        model.setRowCount(0);


        try {
            for (Driver d : dao.consultDrivers(con)) {
                model.addRow(new Object[]{
                        d.getNombre(),
                        d.getApellidos(),
                        d.getNumeroConductor()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error al cargar los datos. Consulte con su administrador");
        }

    }

    /**
     * Metodo para que cargue los datos de la opción elegida
     */
    private void cargarSeleccion(){

        int fila = view.getTable().getSelectedRow();

        if (fila != 1){

            view.getTxtNombre().setText(view.getTable().getValueAt(fila,0).toString());
            view.getTxtApellidos().setText(view.getTable().getValueAt(fila,1).toString());
            view.getTxtnumDriver().setText(view.getTable().getValueAt(fila,2).toString());

        }
    }

    /**
     * Metodo que he puesto para que se quede limpio y bien visto
     */
    private void limpiarCampos(){

        view.getTxtNombre().setText("");
        view.getTxtApellidos().setText("");
        view.getTxtnumDriver().setText("");
    }
}
