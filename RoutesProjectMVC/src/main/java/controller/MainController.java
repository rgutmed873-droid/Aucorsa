package controller;

import controller.bus.BusController;
import controller.conductor.ConductorController;
import controller.dao.ConductorDAO;
import controller.lugar.LugarController;
import controller.route.RouteController;
import view.AucorsaView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Clase MainController
 * Controlador principal de la aplicación Aucorsa.
 * Coordina BusController, ConductorController, LugarController y RouteController
 * y gestiona la barra de herramientas de la ventana principal.
 */
public class MainController {

    // ==================== ATRIBUTOS ====================

    private final AucorsaView        aucorsaView;
    private final BusController      busController;
    private final ConductorController conductorController;
    private final LugarController    lugarController;
    private final RouteController    routeController;

    // ==================== CONSTRUCTOR ====================

    public MainController(AucorsaView view) {
        this.aucorsaView = view;

        busController       = new BusController      (aucorsaView.getBusPanel());
        conductorController = new ConductorController(aucorsaView.getConductorPanel(),
                new ConductorDAO());
        lugarController     = new LugarController    (aucorsaView.getLugarPanel());
        routeController     = new RouteController    (aucorsaView.getRoutePanel());

        // ── Botones globales ──────────────────────────────────────
        aucorsaView.getBtnRefresh().addActionListener(e -> refrescarTabla());
        aucorsaView.getBtnAdd()    .addActionListener(e -> añadir());
        aucorsaView.getBtnDelete() .addActionListener(e -> eliminar());
        aucorsaView.getBtnModify() .addActionListener(e -> modificar());

        // ── Cierre de ventana con confirmación ────────────────────
        aucorsaView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int resultado = JOptionPane.showConfirmDialog(
                        aucorsaView,
                        "¿Estás seguro de que quieres salir?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );
                if (resultado == JOptionPane.YES_OPTION) System.exit(0);
            }
        });
    }

    // ==================== MÉTODOS PRIVADOS ====================

    /** Devuelve el título de la pestaña activa. */
    private String tabActiva() {
        int idx = aucorsaView.getTabs().getSelectedIndex();
        return aucorsaView.getTabs().getTitleAt(idx);
    }

    private void refrescarTabla() {
        switch (tabActiva()) {
            case "Bus"     -> busController      .cargarBuses();
            case "Drivers" -> conductorController.cargarConductores();
            case "Lugar"   -> lugarController    .cargarLugares();
            case "Route"   -> routeController    .cargarRutas();
        }
    }

    private void añadir() {
        switch (tabActiva()) {
            case "Bus"     -> busController      .añadirBus();
            case "Drivers" -> conductorController.añadirConductor();
            case "Lugar"   -> lugarController    .añadirLugar();
            case "Route"   -> routeController    .añadirRuta();
        }
    }

    private void eliminar() {
        switch (tabActiva()) {
            case "Bus"     -> busController      .eliminarBus();
            case "Drivers" -> conductorController.eliminarConductor();
            case "Lugar"   -> lugarController    .eliminarLugar();
            case "Route"   -> routeController    .eliminarRuta();
        }
    }

    private void modificar() {
        switch (tabActiva()) {
            case "Bus"     -> busController      .modificarBus();
            case "Drivers" -> conductorController.modificarConductor();
            case "Lugar"   -> lugarController    .modificarLugar();
            case "Route"   -> routeController    .modificarRuta();
        }
    }
}