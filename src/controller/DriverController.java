package controller;

import controller.DAO.DriverDAO;
import model.Driver;
import view.DriverView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DriverController {

    private DriverView view;
    private DriverDAO dao;
    private Connection con;

    public DriverController(DriverView view, DriverDAO dao, Connection con) {
        this.view = view;
        this.dao = dao;
        this.con = con;

        initController();
        loadTable();
    }


    private void initController() {

        view.getBtnInsertar().addActionListener(e -> insertar());
        view.getBtnActualizar().addActionListener(e -> actualizar());
        view.getBtnEliminar().addActionListener(e -> eliminar());
    }

    private void insertar() {

        try{
            String nombre = view.getTxtNombre().getText();
            String apellido = view.getTxtApellidos().getText();
            int numeroDriver = Integer.parseInt(view.getTxtnumDriver().getText());

            Driver d = new Driver(nombre, apellido,numeroDriver);

            try {
                dao.insertDriver(d,con);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            loadTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,"El número del conductor debeser un entero");
        }



    }

    private void loadTable() {

        DefaultTableModel model = view.getModel();
        model.setRowCount(0);

        List<Driver> lista = null;
        try {
            lista = dao.consultDrivers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Driver d : lista) {
            model.addRow(new Object[]{
                    d.getNombre(),
                    d.getApellidos(),
                    d.getNumeroConductor()
            });
        }

    }
}
