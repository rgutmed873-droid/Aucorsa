package model;

public class Driver {
    //ATRIBUTOS PRINCIPALES
    public String nombre;
    public String apellido;
    public int numeroConductor;

    public Driver (){

    }
    //CONSTRUCTOR DE BUS
    public Driver(String nombre, String apellidos, int numeroConductor) {
        this.nombre = nombre;
        this.apellido = apellidos;
        this.numeroConductor = numeroConductor;
    }

    //GETTERS AND SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellido;
    }

    public void setApellidos(String apellidos) {
        this.apellido = apellido;
    }

    public int getNumeroConductor() {
        return numeroConductor;
    }

    public void setNumeroConductor(int numeroConductor) {
        this.numeroConductor = numeroConductor;
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellido + '\'' +
                ", numeroConductor=" + numeroConductor +
                '}';
    }
}
