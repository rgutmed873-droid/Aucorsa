package controller;

import controller.conductor.ConductorController;
import controller.dao.ConductorDAO;
import controller.db.ConnectionBBDD;
import exception.AucorsaErrorCode;
import exception.AucorsaException;
import model.Conductor;
import view.conductor.ConductorPanel;
import view.conductor.DetailsDriverView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DetailsDriverController
 * Controlador de la ficha de detalle de un conductor.
 * Gestiona navegación entre conductores, edición de datos e imagen.
 */
public class DetailsDriverController {

    // ==================== ATRIBUTOS ====================

    private final DetailsDriverView    detailsDriverView;
    private final ConductorController  conductorController;
    private final ConductorPanel       conductorPanel;
    private final ConductorDAO         conductorDAO;
    private List<Conductor>            conductores;

    private boolean edicionActiva = false;

    // ==================== CONSTRUCTOR ====================

    public DetailsDriverController(DetailsDriverView detailsDriverView,
                                   ConductorController conductorController,
                                   ConductorPanel conductorPanel,
                                   List<Conductor> conductores,
                                   ConductorDAO conductorDAO) {
        this.detailsDriverView  = detailsDriverView;
        this.conductorController = conductorController;
        this.conductorPanel     = conductorPanel;
        this.conductores        = conductores;
        this.conductorDAO       = conductorDAO;

        cargarConductor();

        detailsDriverView.getBtnEditar()   .addActionListener(e -> habilitarEdicion());
        detailsDriverView.getBtnSiguiente().addActionListener(e -> siguienteConductor());
        detailsDriverView.getBtnAnterior() .addActionListener(e -> anteriorConductor());
        detailsDriverView.getBtnCargar()   .addActionListener(e -> seleccionarNuevaImagen());
    }

    // ==================== NAVEGACIÓN ====================

    private void anteriorConductor() {
        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index > 0) {
            conductorPanel.getTablaVista().setRowSelectionInterval(index - 1, index - 1);
            cargarConductor();
        } else {
            JOptionPane.showMessageDialog(detailsDriverView, "Ya estás en el primer conductor.");
        }
    }

    private void siguienteConductor() {
        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index < conductores.size() - 1) {
            conductorPanel.getTablaVista().setRowSelectionInterval(index + 1, index + 1);
            cargarConductor();
        } else {
            JOptionPane.showMessageDialog(detailsDriverView, "Ya estás en el último conductor.");
        }
    }

    // ==================== EDICIÓN ====================

    private void habilitarEdicion() {
        if (!edicionActiva) {
            edicionActiva = true;
            detailsDriverView.getBtnEditar().setText("Guardar");
            detailsDriverView.habilitarTxt();
        } else {
            int opcion = JOptionPane.showConfirmDialog(
                    detailsDriverView, "¿Guardar cambios?", "Confirmar",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                return;
            }
            if (opcion == JOptionPane.YES_OPTION) {
                guardarCambios();
            } else {
                cargarConductor();
                desactivarEdicion();
            }
        }
    }

    private void guardarCambios() {
        String nombre    = detailsDriverView.getNombre()   .getText().trim();
        String apellidos = detailsDriverView.getApellidos().getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(detailsDriverView,
                    AucorsaErrorCode.VALIDATION_EMPTY_FIELDS.getMessage(),
                    "Error [" + AucorsaErrorCode.VALIDATION_EMPTY_FIELDS.getCode() + "]",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index == -1) return;

        // Conserva la imagen actual (no se pierde al editar los campos de texto)
        String imagenActual = conductores.get(index).getImagen();
        int numDriver = Integer.parseInt(detailsDriverView.getNunDriver().getText().trim());

        Conductor conductorEditado = new Conductor(numDriver, nombre, apellidos, imagenActual);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (conductorDAO.modificarConductor(con, conductorEditado)) {
                JOptionPane.showMessageDialog(detailsDriverView,
                        "Conductor actualizado correctamente.");
                conductores = conductorController.cargarConductores();
                cargarConductor();
                desactivarEdicion();
            } else {
                JOptionPane.showMessageDialog(detailsDriverView,
                        AucorsaErrorCode.DB_UPDATE_ERROR.getMessage(),
                        "Error [" + AucorsaErrorCode.DB_UPDATE_ERROR.getCode() + "]",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(detailsDriverView, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(detailsDriverView, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desactivarEdicion() {
        edicionActiva = false;
        detailsDriverView.getBtnEditar().setText("Editar");
        detailsDriverView.deshabilitarTxt();
    }

    // ==================== CARGA DE DATOS ====================

    /** Muestra los datos (texto + imagen) del conductor seleccionado en la tabla. */
    public void cargarConductor() {
        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index == -1 || index >= conductores.size()) return;

        Conductor c = conductores.get(index);
        detailsDriverView.setDatos(c.getNumConductor(), c.getNombre(), c.getApellido());
        detailsDriverView.getNumPagina().setText((index + 1) + " / " + conductores.size());

        cargarImagen(c);
    }

    private void cargarImagen(Conductor c) {
        String nombreImagen = c.getImagen();
        if (nombreImagen != null && !nombreImagen.isBlank()) {
            detailsDriverView.mostrarImagen("Imagenes/" + nombreImagen);
        } else {
            detailsDriverView.mostrarImagen(null);
        }
    }

    // ==================== IMAGEN ====================

    private void seleccionarNuevaImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes (JPG, PNG)", "jpg", "jpeg", "png"));

        if (fileChooser.showOpenDialog(detailsDriverView) != JFileChooser.APPROVE_OPTION) return;

        File archivoSeleccionado = fileChooser.getSelectedFile();
        String nombreArchivo = archivoSeleccionado.getName();

        // Copia el archivo a la carpeta local "Imagenes/"
        File carpetaDestino = new File("Imagenes");
        if (!carpetaDestino.exists()) carpetaDestino.mkdirs();

        File destino = new File(carpetaDestino, nombreArchivo);
        try {
            Files.copy(archivoSeleccionado.toPath(), destino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(detailsDriverView,
                    AucorsaErrorCode.IMAGE_COPY_ERROR.getMessage() + "\n" + e.getMessage(),
                    "Error [" + AucorsaErrorCode.IMAGE_COPY_ERROR.getCode() + "]",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index == -1) return;

        Conductor c = conductores.get(index);
        c.setImagen(nombreArchivo);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (conductorDAO.modificarConductor(con, c)) {
                cargarImagen(c);
                JOptionPane.showMessageDialog(detailsDriverView,
                        "Imagen actualizada y guardada correctamente.");
            } else {
                JOptionPane.showMessageDialog(detailsDriverView,
                        "Imagen copiada localmente pero no se pudo guardar en la BD.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (AucorsaException e) {
            JOptionPane.showMessageDialog(detailsDriverView, e.getMessage(),
                    "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(detailsDriverView, e.getMessage(),
                    "Error inesperado", JOptionPane.ERROR_MESSAGE);
        }
    }
}