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

    private DriverView vistaConductor;
    private DriverDAO daoConductor;
    Driver driver;
    private Connection con;

    public DriverController(DriverView view, DriverDAO dao, Driver driver, Connection con) {
        this.vistaConductor = view;
        this.daoConductor = dao;
        this.driver = driver;
        this.con = con;


        iniciarController();
        loadTable();
    }


    private void iniciarController() {
        vistaConductor.getBtnInsertar().addActionListener(e -> insertar());
        vistaConductor.getBtnActualizar().addActionListener(e -> actualizar());
        vistaConductor.getBtnEliminar().addActionListener(e -> eliminar());
    }

    private void eliminar() {

        try {
            String nombre = vistaConductor.getTxtNombre().getText();
            String apellido = vistaConductor.getTxtApellidos().getText();
            int numDriver = Integer.parseInt(vistaConductor.getTxtnumDriver().getText());

            daoConductor.deleteDriver(nombre,apellido,numDriver,con);

            loadTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaConductor, " El número del conductor tiene que ser un entero");
        }catch (Exception e){
            JOptionPane.showMessageDialog(vistaConductor, "Error al eliminar el conductor");
            e.printStackTrace();
        }

    }

    private void actualizar() {

        try{
            String nombre = vistaConductor.getTxtNombre().getText();
            String apellido = vistaConductor.getTxtApellidos().getText();
            int numDriver = Integer.parseInt(vistaConductor.getTxtnumDriver().getText());

            daoConductor.updateDriver(nombre,apellido,numDriver,con);

            loadTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaConductor, "El número del conductor tiene que ser un entero");
        }catch (Exception e){
            JOptionPane.showMessageDialog(vistaConductor,"Error al actualizar");
            e.printStackTrace();
        }
    }

    private void insertar() {

        try{
            String nombre = vistaConductor.getTxtNombre().getText();
            String apellido = vistaConductor.getTxtApellidos().getText();
            int numeroDriver = Integer.parseInt(vistaConductor.getTxtnumDriver().getText());

            Driver d = new Driver(nombre, apellido,numeroDriver);

            try {
                daoConductor.insertDriver(d,con);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            loadTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaConductor, "El número del conductor debeser un entero");
        }

    }

    private void loadTable() {

        DefaultTableModel model = vistaConductor.getModel();
        model.setRowCount(0);

        List<Driver> lista = null;
        try {
            lista = daoConductor.consultDrivers();
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
