package controllers.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import models.clima.Dia;

/**
 * Created by Will on 10/10/2015.
 */
public class DiaAdapter extends BaseAdapter {
    private Dia[] dias;
    private Context context;

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
        return null;
    }

    public static class Cache{
        private ImageView ivIconoDia;
        private TextView tvNombreDia;
        private TextView tvTemperaturaDia;
    }
}


