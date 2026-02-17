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

    private DriverView view;
    private DriverDAO dao;
    private Connection con;

    public DriverController(DriverView view, DriverDAO dao,Connection con) {
        this.view = view;
        this.dao = dao;
        this.con = con;

        eventos();
        loadTable();
    }


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

    private void cargarSeleccion(){

        int fila = view.getTable().getSelectedRow();

        if (fila != 1){

            view.getTxtNombre().setText(view.getTable().getValueAt(fila,0).toString());
            view.getTxtApellidos().setText(view.getTable().getValueAt(fila,1).toString());
            view.getTxtnumDriver().setText(view.getTable().getValueAt(fila,2).toString());

        }
    }

    private void limpiarCampos(){

        view.getTxtNombre().setText("");
        view.getTxtApellidos().setText("");
        view.getTxtnumDriver().setText("");
    }
}
