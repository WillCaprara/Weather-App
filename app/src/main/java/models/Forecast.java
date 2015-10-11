package models;

import com.example.will.clima.R;

import models.clima.Actual;
import models.clima.Dia;
import models.clima.Hora;

/**
 * Created by Will on 10/10/2015.
 */
public class Forecast {
    private Actual[] actual;
    private Dia[] dias;
    private Hora[] horas;

    public Actual[] getActual() {
        return actual;
    }

    public void setActual(Actual[] actual) {
        this.actual = actual;
    }

    public Dia[] getDias() {
        return dias;
    }

    public void setDias(Dia[] dias) {
        this.dias = dias;
    }

    public Hora[] getHoras() {
        return horas;
    }

    public void setHoras(Hora[] horas) {
        this.horas = horas;
    }

    public static int getImagenId(String icono){
        int idImagen = R.drawable.clear_day;

        switch (icono){
            case "clear-day": idImagen = R.drawable.clear_day;
                break;
            case "clear-night": idImagen = R.drawable.clear_night;
                break;
            case "rain": idImagen = R.drawable.rain;
                break;
            case "snow": idImagen = R.drawable.snow;
                break;
            case "sleet": idImagen = R.drawable.sleet;
                break;
            case "wind": idImagen = R.drawable.wind;
                break;
            case "fog": idImagen = R.drawable.fog;
                break;
            case "cloudy": idImagen = R.drawable.cloudy;
                break;
            case "partly-cloudy-day": idImagen = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night": idImagen = R.drawable.cloudy_night;
                break;
        }

        return idImagen;
    }
}
