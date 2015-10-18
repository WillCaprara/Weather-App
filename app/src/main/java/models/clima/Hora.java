package models.clima;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Will on 10/10/2015.
 */
public class Hora implements Parcelable {
    private long tiempo;
    private double temperatura;
    private String resumen;
    private String icono;
    private String localizacion;

    public Hora(){

    }

    public Hora(Parcel in){
        tiempo = in.readLong();
        temperatura = in.readDouble();
        resumen = in.readString();
        icono = in.readString();
        localizacion = in.readString();
    }

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

    public String obtenerHora(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(localizacion));
        Date tiempoDia = new Date(tiempo * 1000);

        return simpleDateFormat.format(tiempoDia);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Exponer las variables
        dest.writeLong(tiempo);
        dest.writeDouble(temperatura);
        dest.writeString(resumen);
        dest.writeString(icono);
        dest.writeString(localizacion);
    }

    public static final Creator<Hora> CREATOR = new Creator<Hora>() {
        @Override
        public Hora createFromParcel(Parcel source) {
            return new Hora(source);
        }

        @Override
        public Hora[] newArray(int size) {
            return new Hora[size];
        }
    };
}
