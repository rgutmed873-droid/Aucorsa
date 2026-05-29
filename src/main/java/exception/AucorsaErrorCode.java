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
    DB_CONNECTION_ERROR(1001, "No se pudo conectar con la base de datos"),
    DB_INSERT_ERROR    (1002, "Fallo al guardar el nuevo registro"),
    DB_UPDATE_ERROR    (1003, "No se pudo modificar el registro"),
    DB_DELETE_ERROR    (1004, "No se pudo eliminar el registro"),
    DB_QUERY_ERROR     (1005, "Fallo al obtener datos de la base de datos"),
    DB_DUPLICATE_KEY   (1006, "Ya existe un registro con ese identificador"),

    // ==================== VALIDACIÓN (2xxx) ====================
    VALIDATION_EMPTY_FIELDS    (2001, "Por favor, rellena todos los campos"),
    VALIDATION_INVALID_NUMBER  (2002, "Introduce un número entero válido"),
    VALIDATION_NEGATIVE_NUMBER (2003, "El número introducido de ser positivo"),
    VALIDATION_INVALID_FORMAT  (2004, "El formato introducido no es el correcto"),

    // ==================== REGISTRO NO ENCONTRADO (3xxx) ====================
    RECORD_NOT_FOUND    (3001, "No se encontró el registro solicitado"),
    BUS_NOT_FOUND       (3002, "No se encontró ningún autobús con esa matricula"),
    CONDUCTOR_NOT_FOUND (3003, "No se encontró ningún conductor con ese número"),
    LUGAR_NOT_FOUND     (3004, "No se encontró el lugar indicado"),
    ROUTE_NOT_FOUND     (3005, "No existe ninguna ruta con esos datos"),

    // ==================== IMAGEN / ARCHIVO (4xxx) ====================
    IMAGE_LOAD_ERROR(4001, "No se pudo cargar la imagen"),
    IMAGE_COPY_ERROR(4002, "No se pudo copiar la imagen"),
    IMAGE_NOT_FOUND (4003, "No se encontró la imagen en la ruta indicada"),
    IMAGE_INVALID   (4004, "El archivo no es una imagen válida");

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
