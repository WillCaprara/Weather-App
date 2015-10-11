package models.clima;

import com.example.will.clima.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import models.Forecast;

/**
 * Created by Will on 9/26/2015.
 */
public class Actual {
    private double humedad;
    private double temperatura;
    private String localizacion;
    private long tiempo;
    private String icono;
    private double probabilidad;
    private String resumen;

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    //Metodos para configurar mis imagenes
    public int getImagenId(String icono){
        return Forecast.getImagenId(icono);
    }

    public String formatTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(localizacion));

        Date date = new Date(getTiempo() * 1000);

        String tiempo = simpleDateFormat.format(date);

        return tiempo;
    }
}
