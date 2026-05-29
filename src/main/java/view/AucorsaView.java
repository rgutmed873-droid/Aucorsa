package view;

import view.bus.BusPanel;
import view.conductor.ConductorPanel;
import view.lugar.LugarPanel;
import view.routes.RoutePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Clase AucorsaView
 * Vista principal de la aplicación Aucorsa.
 * Contiene el panel de pestañas con las cuatro secciones (Bus, Drivers, Lugar, Route)
 * y la barra de herramientas con los botones de acción globales.
 */
public class AucorsaView extends JFrame {

    // ==================== ATRIBUTOS ====================

    private JTabbedPane    tabs           = new JTabbedPane();

    private BusPanel       busPanel       = new BusPanel();
    private ConductorPanel conductorPanel = new ConductorPanel();
    private LugarPanel     lugarPanel     = new LugarPanel();
    private RoutePanel     routePanel     = new RoutePanel();

    private JLabel  etiquetaEstado = new JLabel("Estado...");

    JButton btnAdd     = new JButton("Añadir");
    JButton btnDelete  = new JButton("Borrar");
    JButton btnRefresh = new JButton("Refrescar");
    JButton btnModify  = new JButton("Modificar");

    JPanel panelNorte   = new JPanel();
    JPanel botoneriaPanel = new JPanel();

    // ==================== CONSTRUCTOR ====================

    public AucorsaView() {
        super("Aucorsa - Ventana principal");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(900, 600);
        setLayout(new BorderLayout());

        tabs.add("Bus",     busPanel);
        tabs.add("Drivers", conductorPanel);
        tabs.add("Lugar",   lugarPanel);
        tabs.add("Route",   routePanel);

        add(tabs, BorderLayout.CENTER);

        panelNorte.setLayout(new BorderLayout());
        botoneriaPanel.setLayout(new FlowLayout());
        botoneriaPanel.add(btnAdd);
        botoneriaPanel.add(btnDelete);
        botoneriaPanel.add(btnModify);
        botoneriaPanel.add(btnRefresh);

        panelNorte.add(botoneriaPanel,   BorderLayout.NORTH);
        panelNorte.add(etiquetaEstado,   BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);
    }

    // ==================== GETTERS Y SETTERS ====================

    public JTabbedPane    getTabs()           { return tabs;           }
    public BusPanel       getBusPanel()       { return busPanel;       }
    public ConductorPanel getConductorPanel() { return conductorPanel; }
    public LugarPanel     getLugarPanel()     { return lugarPanel;     }
    public RoutePanel     getRoutePanel()     { return routePanel;     }
    public JLabel         getEtiquetaEstado() { return etiquetaEstado; }
    public JButton        getBtnAdd()         { return btnAdd;         }
    public JButton        getBtnDelete()      { return btnDelete;      }
    public JButton        getBtnRefresh()     { return btnRefresh;     }
    public JButton        getBtnModify()      { return btnModify;      }
    public JPanel         getPanelNorte()     { return panelNorte;     }
    public JPanel         getBotoneriaPanel() { return botoneriaPanel; }

    public void setTabs(JTabbedPane tabs)               { this.tabs           = tabs;           }
    public void setBusPanel(BusPanel p)                 { this.busPanel       = p;              }
    public void setConductorPanel(ConductorPanel p)     { this.conductorPanel = p;              }
    public void setLugarPanel(LugarPanel p)             { this.lugarPanel     = p;              }
    public void setRoutePanel(RoutePanel p)             { this.routePanel     = p;              }
    public void setEtiquetaEstado(JLabel l)             { this.etiquetaEstado = l;              }
    public void setBtnAdd(JButton b)                    { this.btnAdd         = b;              }
    public void setBtnDelete(JButton b)                 { this.btnDelete      = b;              }
    public void setBtnRefresh(JButton b)                { this.btnRefresh     = b;              }
    public void setBtnModify(JButton b)                 { this.btnModify      = b;              }
    public void setPanelNorte(JPanel p)                 { this.panelNorte     = p;              }
    public void setBotoneriaPanel(JPanel p)             { this.botoneriaPanel = p;              }
}