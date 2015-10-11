package controllers;

import android.app.ListActivity;
import android.os.Bundle;

import com.example.will.clima.R;

/**
 * Created by Will on 10/10/2015.
 */
public class DiasForecastActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dias_forecast);


    }
}
