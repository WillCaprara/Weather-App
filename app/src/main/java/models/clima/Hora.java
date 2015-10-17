package models.clima;

/**
 * Created by Will on 10/10/2015.
 */
public class Hora {
    private long tiempo;
    private double temperatura;
    private String resumen;
    private String icono;
    private String localizacion;

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getIcono() {
        return icono;
    }

    public int getImagenId(){
        return Forecast.getImagenId(icono);
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
}
