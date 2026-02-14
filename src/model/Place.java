package model;

public class Place {

    //ATRIBUTOS PRINCIPALES
    public int idLugar;
    public String ciudad;
    public String sitio;
    public int cp;

    public Place (){

    }
    //CONSTRUCTOR CON ATRIBUTOS
    public Place(int idLugar, String ciudad, String sitio, int cp) {
        this.idLugar = idLugar;
        this.ciudad = ciudad;
        this.sitio = sitio;
        this.cp = cp;
    }

    //GETTERS AND SETTERS
    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "idLugar=" + idLugar +
                ", ciudad='" + ciudad + '\'' +
                ", sitio='" + sitio + '\'' +
                ", cp=" + cp +
                '}';
    }
}
