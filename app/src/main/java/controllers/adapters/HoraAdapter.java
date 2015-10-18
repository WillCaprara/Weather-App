package controllers.adapters;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.will.clima.R;

import models.clima.Hora;

/**
 * Created by Will on 10/17/2015.
 */
public class HoraAdapter extends BaseAdapter {
    private Context context;
    private Hora[] horas;

    public HoraAdapter(Context context, Hora[] horas){
        this.context = context;
        this.horas = horas;
    }

    @Override
    public int getCount() {
       return horas.length;
    }

    @Override
    public Object getItem(int position) {
        return horas[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cache cache;

        if(convertView == null){
            convertView = LayoutInflater.from(context)
                                .inflate(R.layout.item_horas_forecast, null);
            cache = new Cache();
            cache.tvHora = (TextView) convertView.findViewById(R.id.tvHora);
            cache.ivIconoHora = (ImageView) convertView.findViewById(R.id.ivIconoHora);
            cache.tvResumen = (TextView) convertView.findViewById(R.id.tvResumenHora);
            cache.tvTemperatura = (TextView) convertView.findViewById(R.id.tvTemperaturaHora);

            convertView.setTag(cache);
        }
        else{
            cache = (Cache)convertView.getTag();
        }

        Hora hora = horas[position];
        cache.tvHora.setText(hora.obtenerHora());
        cache.ivIconoHora.setImageResource(hora.getImagenId());
        cache.tvResumen.setText(hora.getResumen());
        cache.tvTemperatura.setText(hora.getTemperatura() + "");

        return  convertView;
    }

    public static class Cache{
        private TextView tvHora;
        private ImageView ivIconoHora;
        private TextView tvResumen;
        private TextView tvTemperatura;
    }
}
