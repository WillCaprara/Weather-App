package models.clima;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Will on 10/10/2015.
 */
public class Dia implements Parcelable {
    private double temperaturaMaxima;
    private String icono;
    private String resumen;
    private long tiempo;
    private String localizacion;

    public Dia(){

    }

    //Al momento de crear un constructor diferente al default (vacio), es necesario crear (de manera explicita) un constructor vacio
    public Dia(Parcel in){
        temperaturaMaxima = in.readDouble();
        icono = in.readString();
        resumen = in.readString();
        tiempo = in.readLong();
        localizacion = in.readString();
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
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

    public String DiaSemana(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
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
        //Expone las variables para poderlas obtener
        dest.writeDouble(temperaturaMaxima);
        dest.writeString(icono);
        dest.writeString(resumen);
        dest.writeLong(tiempo);
        dest.writeString(localizacion);
    }

    //Generic
    public static final Creator<Dia> CREATOR = new Creator<Dia>() {
        @Override
        public Dia createFromParcel(Parcel source) {
            return new Dia(source);
        }

        @Override
        public Dia[] newArray(int size) {
            return new Dia[size];
        }
    };
}
