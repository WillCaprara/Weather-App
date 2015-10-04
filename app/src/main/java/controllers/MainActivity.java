package controllers;

import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.will.clima.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import models.ClimaActual;
import models.VentanaAlerta;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private ClimaActual climaActual;

    /*ImageView ivIcono;
    TextView tvLocalizacion;
    TextView tvTiempo;
    TextView tvGrados;
    TextView tvHumedadValor;
    TextView tvProbabilidadValor;
    TextView tvResumen;*/

    @Bind(R.id.ivIcono) ImageView ivIcono;
    @Bind(R.id.tvLocalizacion) TextView tvLocalizacion;
    @Bind(R.id.tvGrados) TextView tvGrados;
    @Bind(R.id.tvTiempo) TextView tvTiempo;
    @Bind(R.id.tvHumedadValor) TextView tvHumedadValor;
    @Bind(R.id.tvProbabilidadValor) TextView tvProbabilidadValor;
    @Bind(R.id.tvResumen) TextView tvResumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*ivIcono = (ImageView) findViewById(R.id.ivIcono);
        tvLocalizacion = (TextView) findViewById(R.id.tvLocalizacion);
        tvTiempo = (TextView) findViewById(R.id.tvTiempo);
        tvGrados = (TextView) findViewById(R.id.tvGrados);
        tvHumedadValor = (TextView) findViewById(R.id.tvHumedadValor);
        tvProbabilidadValor = (TextView) findViewById(R.id.tvProbabilidadValor);
        tvResumen = (TextView) findViewById(R.id.tvResumen);*/

        String apiKey = "8dad74e58f4f47a4925bd01cebf810ca";
        double latitud = 37.8267;
        //double latitud = 9999;
        double longitud = -122.423;
        String forecast = "https://api.forecast.io/forecast/" + apiKey + "/" + latitud + "," + longitud;

        if(redDisponible()){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecast).build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try{
                        if(response.isSuccessful()){
                            String responseJsonString = response.body().string();
                            climaActual = obtenerClimaActual(responseJsonString);

                            //Programacion asincrona, sin este new Runnable no funcionaria
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ivIcono.setImageResource(climaActual.getImagenId());
                                    tvLocalizacion.setText(climaActual.getLocalizacion());

                                    tvTiempo.setText(climaActual.formatTime());

                                    tvGrados.setText(String.valueOf(climaActual.getTemperatura()));
                                    tvHumedadValor.setText(String.valueOf(climaActual.getHumedad()));
                                    tvProbabilidadValor.setText(String.valueOf(climaActual.getProbabilidad()));
                                    tvResumen.setText(climaActual.getResumen());
                                }
                            });

                            //Log.d(TAG, response.body().string());
                            //Log.v(TAG, jsonObject.toString());
                            //Log.v(TAG, currentWeatherInfo.getString("time"));
                        }
                        else{
                            alertaErrorUsuario();
                        }
                    }
                    catch (Exception e){
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
        }
        else{
            Toast.makeText(this, "La red de telcel no esta disponible", Toast.LENGTH_LONG).show();
        }
    }

    //El throws JSONException indica que la excepcion se maneja desde donde se llamo el metodo obtnerClimaActual.
    //Si no tuvieramos el throws JSONException, se tendria que poner un bloque try/catch en el metodo obtenerClimaActual
    private ClimaActual obtenerClimaActual(String responseJsonString) throws JSONException{
        JSONObject responseJsonObject = new JSONObject(responseJsonString);
        JSONObject currentWeatherInfo = responseJsonObject.getJSONObject("currently");

        climaActual = new ClimaActual();

        climaActual.setIcono(currentWeatherInfo.getString("icon"));
        climaActual.setLocalizacion(responseJsonObject.getString("timezone"));

        long timeStampFromService = currentWeatherInfo.getLong("time");
        long javaTimeStamp = timeStampFromService * 1000L;
        //Date date = new Date(javaTimeStamp);

        climaActual.setTiempo(javaTimeStamp);
        //tvTiempo.setText("Son las: " + new SimpleDateFormat("hh:mm").format(date));

        climaActual.setTemperatura(currentWeatherInfo.getDouble("temperature"));
        climaActual.setHumedad(currentWeatherInfo.getDouble("humidity"));
        climaActual.setProbabilidad(currentWeatherInfo.getDouble("precipProbability"));
        climaActual.setResumen(currentWeatherInfo.getString("summary"));

        return climaActual;
    }

    private boolean redDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean estaDisponible = false;

        if(networkInfo != null && networkInfo.isConnected()){
            estaDisponible = true;
        }

        return estaDisponible;
    }

    private void alertaErrorUsuario() {
        VentanaAlerta ventanaAlerta = new VentanaAlerta();

        ventanaAlerta.show(getFragmentManager(), "My dialog");
    }
}
