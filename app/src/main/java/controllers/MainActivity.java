package controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.will.clima.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import models.clima.Actual;
import models.VentanaAlerta;
import models.clima.Dia;
import models.clima.Hora;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Actual actual;

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
    @Bind(R.id.ivActualizar) ImageView ivActualizar;
    @Bind(R.id.pbActualizar) ProgressBar pbActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final double latitud = 37.8267;
        final double longitud = -122.423;

        pbActualizar.setVisibility(View.INVISIBLE);

        ivActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerForecast(latitud, longitud);
            }
        });

        obtenerForecast(latitud, longitud);
    }

    private void obtenerForecast(double latitud, double longitud) {
        String apiKey = "8dad74e58f4f47a4925bd01cebf810ca";
        String forecast = "https://api.forecast.io/forecast/" + apiKey + "/" + latitud + "," + longitud;

        if(redDisponible()){
            verificaProgressBar();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecast).build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            verificaProgressBar();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            verificaProgressBar();
                        }
                    });

                    try{
                        if(response.isSuccessful()){
                            String responseJsonString = response.body().string();
                            actual = obtenerClimaActual(responseJsonString);

                            //Programacion asincrona, sin este new Runnable no funcionaria
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    actualizaInformacion();
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

    private void actualizaInformacion(){
        ivIcono.setImageResource(actual.getImagenId(actual.getIcono()));
        tvLocalizacion.setText(actual.getLocalizacion());
        tvTiempo.setText(actual.formatTime());
        tvGrados.setText(String.valueOf(actual.getTemperatura()));
        tvHumedadValor.setText(String.valueOf(actual.getHumedad()));
        tvProbabilidadValor.setText(String.valueOf(actual.getProbabilidad()));
        tvResumen.setText(actual.getResumen());
    }

    //El throws JSONException indica que la excepcion se maneja desde donde se llamo el metodo obtnerClimaActual.
    //Si no tuvieramos el throws JSONException, se tendria que poner un bloque try/catch en el metodo obtenerClimaActual
    private Actual obtenerClimaActual(String responseJsonString) throws JSONException{
        JSONObject responseJsonObject = new JSONObject(responseJsonString);
        JSONObject currentWeatherInfo = responseJsonObject.getJSONObject("currently");

        actual = new Actual();

        actual.setIcono(currentWeatherInfo.getString("icon"));
        actual.setLocalizacion(responseJsonObject.getString("timezone"));

        long timeStampFromService = currentWeatherInfo.getLong("time");
        long javaTimeStamp = timeStampFromService * 1000L;
        //Date date = new Date(javaTimeStamp);

        actual.setTiempo(javaTimeStamp);
        //tvTiempo.setText("Son las: " + new SimpleDateFormat("hh:mm").format(date));

        actual.setTemperatura(currentWeatherInfo.getDouble("temperature"));
        actual.setHumedad(currentWeatherInfo.getDouble("humidity"));
        actual.setProbabilidad(currentWeatherInfo.getDouble("precipProbability"));
        actual.setResumen(currentWeatherInfo.getString("summary"));

        return actual;
    }

    public Dia[] obtenerDiasForecast(String jsonResult) throws JSONException{
        JSONObject forecast = new JSONObject(jsonResult);
        JSONObject dias = forecast.getJSONObject("daily");
        JSONArray diasData = dias.getJSONArray("data");

        Dia[] diasArray = new Dia[diasData.length()];

        for(int i = 0; i < diasData.length(); i++){
            JSONObject currentDia = diasData.getJSONObject(i);

            Dia dia = new Dia();

            dia.setTiempo(currentDia.getLong("time"));
            dia.setIcono(currentDia.getString("icon"));
            dia.setLocalizacion(forecast.getString("timezone"));
            dia.setTemperaturaMaxima(currentDia.getDouble("temperatureMax"));
            dia.setResumen(currentDia.getString("summary"));

            diasArray[i] = dia;
        }

        return diasArray;
    }

    public Hora[] obtenerHoras(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject horas = forecast.getJSONObject("hourly");
        JSONArray horasData = horas.getJSONArray("data");

        Hora[] horasArray = new Hora[horasData.length()];

        for (int i = 0; i < horasData.length(); i++){
            JSONObject currentHora = horasData.getJSONObject(i);

            Hora hora = new Hora();

            hora.setLocalizacion(forecast.getString("timezone"));
            hora.setResumen(currentHora.getString("summary"));
            hora.setIcono(currentHora.getString("icon"));
            hora.setTiempo(currentHora.getLong("time"));
            hora.setTemperatura(currentHora.getDouble("temperature"));

            horasArray[i] = hora;
        }

        return horasArray;
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

    private void verificaProgressBar(){
        if(pbActualizar.getVisibility() == View.INVISIBLE){
            pbActualizar.setVisibility(View.VISIBLE);
            ivActualizar.setVisibility(View.INVISIBLE);
        }
        else{
            pbActualizar.setVisibility(View.INVISIBLE);
            ivActualizar.setVisibility(View.VISIBLE);
        }
    }
}
