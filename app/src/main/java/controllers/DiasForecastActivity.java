package controllers;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.will.clima.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import controllers.adapters.DiaAdapter;
import models.clima.Dia;

/**
 * Created by Will on 10/10/2015.
 */
public class DiasForecastActivity extends ListActivity {
    private Dia[] dias;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dias_forecast);

        //Obtener el intento que me estan mandando cuando abran la vista de los dias
        Intent intent = getIntent();

        //Obtenermos el arreglo que enviamos con el putExtra al crear el intento hacia esta vista desde main activity
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DIAS_FORECAST);

        //Serializamos el arreglo que se obtuvo del intent
        dias = Arrays.copyOf(parcelables, parcelables.length, Dia[].class);

        //Pasamos los adatos al adaptador para que llene la vista
        DiaAdapter diaAdapter = new DiaAdapter(this, dias);

        //Este metodo se puede utilizar de esta manera, es decir, pasar como parametro nuestra clase "DiaAdapter",
        //porque nuestra clase "DiasForecastActivity" EXTIENDE la clase "ListActivity"
        setListAdapter(diaAdapter);
    }
}
