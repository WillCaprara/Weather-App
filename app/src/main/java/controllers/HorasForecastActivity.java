package controllers;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.will.clima.R;

import java.util.Arrays;

import controllers.adapters.HoraAdapter;
import models.clima.Hora;

public class HorasForecastActivity extends ListActivity {
    private Hora[] horas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas_forecast);

        Intent intent = getIntent();

        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HORAS_FORECAST);

        horas = Arrays.copyOf(parcelables, parcelables.length, Hora[].class);

        HoraAdapter horaAdapter = new HoraAdapter(this, horas);

        setListAdapter(horaAdapter);
    }
}
