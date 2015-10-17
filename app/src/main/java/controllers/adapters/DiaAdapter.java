package controllers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.will.clima.R;

import models.clima.Dia;

/**
 * Created by Will on 10/10/2015.
 */
public class DiaAdapter extends BaseAdapter {
    private Dia[] dias;
    private Context context;

    public DiaAdapter(Context context, Dia[] dias){
        this.context = context;
        this.dias = dias;
    }

    @Override
    public int getCount() {
        return dias.length;
    }

    @Override
    public Object getItem(int position) {
        return dias[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cache cache;

        //Pregunto si me pasaron algun objeto
        if(convertView == null){
            //1. Obtener la vista que voy a convertir de la vista que cree (item_dias_forecast)
            convertView = LayoutInflater
                            .from(context)
                                .inflate(R.layout.item_dias_forecast, null);

            //2. Obtener los valores que necesito de la pantalla que me traje en el paso 1
            cache = new Cache();
            cache.ivIconoDia = (ImageView)convertView.findViewById(R.id.ivIconoDia);
            cache.tvNombreDia = (TextView) convertView.findViewById(R.id.tvNombreDia);
            cache.tvTemperaturaDia = (TextView) convertView.findViewById(R.id.tvTemperaturaDia);

            //3. Mando a la vista el objeto que ya tiene la informacion necesaria
            convertView.setTag(cache);
        }
        else{
            //Si me pasaron algun objeto, obtenerlo
            cache = (Cache) convertView.getTag();
        }

        Dia dia = dias[position];
        cache.ivIconoDia.setImageResource(dia.getImagenId());
        cache.tvTemperaturaDia.setText(dia.getTemperaturaMaxima() + "");
        cache.tvNombreDia.setText(dia.DiaSemana());

        return convertView;
    }

    public static class Cache{
        private ImageView ivIconoDia;
        private TextView tvNombreDia;
        private TextView tvTemperaturaDia;
    }
}


