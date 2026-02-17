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

            //Crear MVC
            DriverView view = new DriverView();
            DriverDAO driverDAO = new DriverDAO();
            Driver driver = new Driver();

            //Crear Controller
            new DriverController(view,driverDAO,driver,con);
        }catch (Exception e){
            e.printStackTrace();
        }

    }







}
