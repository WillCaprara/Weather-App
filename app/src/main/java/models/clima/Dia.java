package models.clima;

import models.Forecast;

/**
 * Created by Will on 10/10/2015.
 */
public class Dia {
    private double temperaturaMaxima;
    private String icono;
    private String resumen;
    private long tiempo;
    private String localizacion;

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public String getIcono() {
        return icono;
    }

    public int getImagenId(String icono){
        return Forecast.getImagenId(icono);
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
}
