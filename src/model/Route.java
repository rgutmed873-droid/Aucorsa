package model;

public class Route {

    //ATRIBUTOS PRINCIPALES
    private String registro;
    private int numeroConductor;
    private int idLugar;
    private String diaSemana;

    public Route(){

    }
    //CONSTRUCTOR CON ATRIBUTOS
    public Route(String diaSemana, int idLugar, String registro, int numeroConductor) {
        this.diaSemana = diaSemana;
        this.idLugar = idLugar;
        this.registro = registro;
        this.numeroConductor = numeroConductor;
    }

    //GETTERS AND SETTERS
    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public int getNumeroConductor() {
        return numeroConductor;
    }

    public void setNumeroConductor(int numeroConductor) {
        this.numeroConductor = numeroConductor;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "diaSemana='" + diaSemana + '\'' +
                ", registro='" + registro + '\'' +
                ", numeroConductor=" + numeroConductor +
                ", idLugar=" + idLugar +
                '}';
    }
}
