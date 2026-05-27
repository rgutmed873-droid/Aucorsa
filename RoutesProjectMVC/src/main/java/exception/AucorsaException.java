package exception;

/**
 * Clase AucorsaException
 * Excepción verificada (checked) personalizada de la aplicación Aucorsa.
 * Siempre lleva asociado un {@link AucorsaErrorCode} que identifica el tipo de error.
 *
 * Uso típico en un controlador:
 * <pre>
 *   try (Connection con = ConnectionBBDD.getConexion()) {
 *       BusDAO.añadirBus(con, bus);
 *   } catch (AucorsaException e) {
 *       JOptionPane.showMessageDialog(view, e.getMessage(),
 *           "Error [" + e.getNumericCode() + "]", JOptionPane.ERROR_MESSAGE);
 *   }
 * </pre>
 */
public class AucorsaException extends Exception {

    // ==================== ATRIBUTOS ====================

    private final AucorsaErrorCode errorCode;

    // ==================== CONSTRUCTORES ====================

    /**
     * Constructor con solo el código de error.
     * @param errorCode Código de error del enum {@link AucorsaErrorCode}
     */
    public AucorsaException(AucorsaErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    /**
     * Constructor con código de error y detalle adicional (p.ej. el valor problemático).
     * @param errorCode Código de error
     * @param detail    Información adicional para depuración
     */
    public AucorsaException(AucorsaErrorCode errorCode, String detail) {
        super(errorCode + " → " + detail);
        this.errorCode = errorCode;
    }

    /**
     * Constructor con código de error y causa raíz (p.ej. una SQLException).
     * @param errorCode Código de error
     * @param cause     Excepción original que provocó este error
     */
    public AucorsaException(AucorsaErrorCode errorCode, Throwable cause) {
        super(errorCode.toString(), cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructor completo con código, detalle y causa raíz.
     * @param errorCode Código de error
     * @param detail    Información adicional
     * @param cause     Excepción original
     */
    public AucorsaException(AucorsaErrorCode errorCode, String detail, Throwable cause) {
        super(errorCode + " → " + detail, cause);
        this.errorCode = errorCode;
    }

    // ==================== GETTERS ====================

    /** Devuelve el código de error como elemento del enum. */
    public AucorsaErrorCode getErrorCode() { return errorCode; }

    /** Devuelve el código numérico del error (p.ej. 1001). */
    public int getNumericCode() { return errorCode.getCode(); }
}
