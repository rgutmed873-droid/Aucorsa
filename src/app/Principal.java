package app;

import controller.DAO.DriverDAO;
import controller.DB.ConexionDB;
import controller.DriverController;
import model.Driver;
import view.DriverView;

import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
    public static  void main(String args[]){
//        ArrayList<Driver> drivers = new ArrayList<>();
//        DriverDAO driverDAO = new DriverDAO();

        try{
            //Crear conexion
            Connection con = ConexionDB.getConexion();

            //Crear la vista
            DriverView view = new DriverView();
            //Crear el DAO
            DriverDAO driverDAO = new DriverDAO();

            //Crear Controller
            new DriverController(view,driverDAO,con);

            //Mostrar la ventana
            view.setVisible(true);

        }catch (Exception e){
            e.printStackTrace();
        }

    }







}
