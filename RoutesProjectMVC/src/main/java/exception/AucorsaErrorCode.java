package exception;

/**
 * Enum AucorsaErrorCode
 * Define todos los códigos de error personalizados de la aplicación Aucorsa.
 * Cada entrada agrupa un código numérico único y un mensaje descriptivo.
 *
 * Rangos:
 *   1xxx → Errores de base de datos
 *   2xxx → Errores de validación
 *   3xxx → Registros no encontrados
 *   4xxx → Errores de imagen/archivo
 */
public enum AucorsaErrorCode {

    // ==================== BASE DE DATOS (1xxx) ====================
    DB_CONNECTION_ERROR(1001, "Error de conexión con la base de datos"),
    DB_INSERT_ERROR    (1002, "Error al insertar el registro en la base de datos"),
    DB_UPDATE_ERROR    (1003, "Error al actualizar el registro en la base de datos"),
    DB_DELETE_ERROR    (1004, "Error al eliminar el registro de la base de datos"),
    DB_QUERY_ERROR     (1005, "Error al consultar la base de datos"),
    DB_DUPLICATE_KEY   (1006, "El registro ya existe (clave duplicada)"),

    // ==================== VALIDACIÓN (2xxx) ====================
    VALIDATION_EMPTY_FIELDS    (2001, "Todos los campos deben estar completos"),
    VALIDATION_INVALID_NUMBER  (2002, "El valor debe ser un número entero válido"),
    VALIDATION_NEGATIVE_NUMBER (2003, "El número debe ser un valor positivo"),
    VALIDATION_INVALID_FORMAT  (2004, "El formato del campo introducido no es válido"),

    // ==================== REGISTRO NO ENCONTRADO (3xxx) ====================
    RECORD_NOT_FOUND    (3001, "El registro no fue encontrado en la base de datos"),
    BUS_NOT_FOUND       (3002, "El autobús especificado no fue encontrado"),
    CONDUCTOR_NOT_FOUND (3003, "El conductor especificado no fue encontrado"),
    LUGAR_NOT_FOUND     (3004, "El lugar especificado no fue encontrado"),
    ROUTE_NOT_FOUND     (3005, "La ruta especificada no fue encontrada"),

    // ==================== IMAGEN / ARCHIVO (4xxx) ====================
    IMAGE_LOAD_ERROR(4001, "Error al cargar la imagen desde el disco"),
    IMAGE_COPY_ERROR(4002, "Error al copiar la imagen al directorio destino"),
    IMAGE_NOT_FOUND (4003, "La imagen no fue encontrada en la ruta indicada"),
    IMAGE_INVALID   (4004, "El archivo seleccionado no es una imagen válida");

    // ==================== ATRIBUTOS ====================

    private final int    code;
    private final String message;

    // ==================== CONSTRUCTOR ====================

    AucorsaErrorCode(int code, String message) {
        this.code    = code;
        this.message = message;
    }

    // ==================== GETTERS ====================

    /** Código numérico del error. */
    public int getCode() { return code; }

    /** Descripción legible del error. */
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "[" + code + "] " + message;
    }
}
