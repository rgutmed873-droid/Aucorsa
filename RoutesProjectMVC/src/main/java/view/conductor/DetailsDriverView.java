package view.conductor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Clase DetailsDriverView
 * Vista del detalle/ficha de un conductor.
 *
 * CORRECCIÓN DE IMAGEN: se usa ImageIO.read() + Graphics2D en lugar de
 * getScaledInstance(), que era asíncrono y no mantenía la proporción de aspecto.
 */
public class DetailsDriverView extends JFrame {

    // ==================== ATRIBUTOS ====================

    JTextField nombre    = new JTextField(10);
    JTextField apellidos = new JTextField(10);
    JTextField nunDriver = new JTextField(10);

    JButton btnEditar    = new JButton("Editar");
    JButton btnCargar    = new JButton("Cargar imagen");
    JButton btnAnterior  = new JButton("<<");
    JButton btnSiguiente = new JButton(">>");

    JLabel numPagina = new JLabel("página");

    /** Tamaño fijo reservado para el área de imagen (mantiene el layout estable). */
    private static final int IMG_W = 150;
    private static final int IMG_H = 150;

    JLabel etiquetaImagen = new JLabel("Sin imagen", SwingConstants.CENTER);

    JPanel panelPrincipal   = new JPanel();
    JPanel panelCentro      = new JPanel();
    JPanel panelCentroEste  = new JPanel();
    JPanel panelCentroOeste = new JPanel();
    JPanel panelSur         = new JPanel();
    JPanel panelCamposSur   = new JPanel();

    // ==================== CONSTRUCTOR ====================

    public DetailsDriverView() {
        setTitle("Ficha conductor");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        etiquetaImagen.setPreferredSize(new Dimension(IMG_W, IMG_H));
        etiquetaImagen.setMinimumSize(new Dimension(IMG_W, IMG_H));
        etiquetaImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        etiquetaImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelPrincipal  .setLayout(new BorderLayout(10, 10));
        panelCentro     .setLayout(new BorderLayout());
        panelCentroEste .setLayout(new BoxLayout(panelCentroEste,  BoxLayout.Y_AXIS));
        panelCentroOeste.setLayout(new BoxLayout(panelCentroOeste, BoxLayout.Y_AXIS));
        panelCamposSur  .setLayout(new FlowLayout());
        panelSur        .setLayout(new BorderLayout(10, 10));

        panelCamposSur.add(btnAnterior);
        panelCamposSur.add(numPagina);
        panelCamposSur.add(btnSiguiente);
        panelSur.add(btnEditar,     BorderLayout.EAST);
        panelSur.add(panelCamposSur, BorderLayout.CENTER);

        nombre  .setAlignmentX(Component.CENTER_ALIGNMENT);
        apellidos.setAlignmentX(Component.CENTER_ALIGNMENT);
        nunDriver.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentroEste.add(new JLabel("Nombre:"));
        panelCentroEste.add(nombre);
        panelCentroEste.add(Box.createVerticalStrut(15));
        panelCentroEste.add(new JLabel("Apellidos:"));
        panelCentroEste.add(apellidos);
        panelCentroEste.add(Box.createVerticalStrut(15));
        panelCentroEste.add(new JLabel("Nº conductor:"));
        panelCentroEste.add(nunDriver);
        panelCentroEste.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 30));

        btnCargar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentroOeste.add(etiquetaImagen);
        panelCentroOeste.add(Box.createVerticalStrut(10));
        panelCentroOeste.add(btnCargar);
        panelCentroOeste.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 10));

        panelCentro.add(panelCentroOeste, BorderLayout.WEST);
        panelCentro.add(panelCentroEste,  BorderLayout.EAST);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelSur,    BorderLayout.SOUTH);

        nunDriver.setEnabled(false);
        nombre   .setEnabled(false);
        apellidos.setEnabled(false);

        add(panelPrincipal);
        setVisible(true);
    }

    // ==================== MÉTODOS ====================

    public void habilitarTxt() {
        nombre   .setEnabled(true);
        apellidos.setEnabled(true);
    }

    public void deshabilitarTxt() {
        nunDriver.setEnabled(false);
        nombre   .setEnabled(false);
        apellidos.setEnabled(false);
    }

    public void setDatos(int numDriver, String nombreDAO, String apellidosDAO) {
        nunDriver.setText(String.valueOf(numDriver));
        nombre   .setText(nombreDAO);
        apellidos.setText(apellidosDAO);
    }

    /**
     * Muestra la imagen escalada manteniendo la proporción de aspecto.
     *
     * CORRECCIÓN respecto a la versión anterior:
     *   • Usa ImageIO.read() (síncrono) en lugar de new ImageIcon() (puede ser asíncrono).
     *   • Calcula el ratio correcto para no deformar la imagen.
     *   • Usa Graphics2D con interpolación bilineal para mayor calidad.
     *
     * @param rutaCompleta Ruta absoluta o relativa al archivo de imagen.
     */
    public void mostrarImagen(String rutaCompleta) {
        if (rutaCompleta == null || rutaCompleta.isBlank()) {
            etiquetaImagen.setIcon(null);
            etiquetaImagen.setText("Sin imagen");
            return;
        }

        File archivo = new File(rutaCompleta);
        if (!archivo.exists()) {
            etiquetaImagen.setIcon(null);
            etiquetaImagen.setText("No encontrada");
            return;
        }

        try {
            BufferedImage original = ImageIO.read(archivo);

            if (original == null) {
                // ImageIO no reconoce el formato
                etiquetaImagen.setIcon(null);
                etiquetaImagen.setText("Formato no válido");
                return;
            }

            // Calcula ratio preservando aspecto (fit inside IMG_W x IMG_H)
            double ratio = Math.min(
                    (double) IMG_W / original.getWidth(),
                    (double) IMG_H / original.getHeight()
            );
            int newW = (int) (original.getWidth()  * ratio);
            int newH = (int) (original.getHeight() * ratio);

            // Escala con Graphics2D (calidad superior y resultado síncrono)
            BufferedImage scaled = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaled.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(original, 0, 0, newW, newH, null);
            g2d.dispose();

            etiquetaImagen.setIcon(new ImageIcon(scaled));
            etiquetaImagen.setText("");

        } catch (IOException e) {
            etiquetaImagen.setIcon(null);
            etiquetaImagen.setText("Error al cargar");
        }
    }

    // ==================== GETTERS ====================

    public JTextField getNombre()    { return nombre;    }
    public JTextField getApellidos() { return apellidos; }
    public JTextField getNunDriver() { return nunDriver; }

    public JButton getBtnEditar()    { return btnEditar;    }
    public JButton getBtnCargar()    { return btnCargar;    }
    public JButton getBtnAnterior()  { return btnAnterior;  }
    public JButton getBtnSiguiente() { return btnSiguiente; }

    public JLabel getNumPagina()      { return numPagina;      }
    public JLabel getEtiquetaImagen() { return etiquetaImagen; }

    public JPanel getPanelPrincipal()    { return panelPrincipal;   }
    public JPanel getPanelCentro()       { return panelCentro;      }
    public JPanel getPanelCentroEste()   { return panelCentroEste;  }
    public JPanel getPanelCentroOeste()  { return panelCentroOeste; }
    public JPanel getPanelSur()          { return panelSur;         }
    public JPanel getPanelCamposSur()    { return panelCamposSur;   }
}