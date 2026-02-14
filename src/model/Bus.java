package model;

public class Bus {
    //Atributos principales
    public String registro;
    public String tipo;
    public String licencia;

    public Bus (){

    }

    //CONSTRUCTOR DE BUS
    public Bus(String registro, String tipo, String licencia) {
        this.registro = registro;
        this.tipo = tipo;
        this.licencia = licencia;
    }

    //GETTERS AND SETTERS
    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }


    @Override
    public String toString() {
        return "Bus{" +
                "registro='" + registro + '\'' +
                ", tipo='" + tipo + '\'' +
                ", licencia='" + licencia + '\'' +
                '}';
    }
}
