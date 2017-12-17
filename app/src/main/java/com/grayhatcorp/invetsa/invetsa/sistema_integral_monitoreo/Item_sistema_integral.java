package com.grayhatcorp.invetsa.invetsa.sistema_integral_monitoreo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grayhatcorp.invetsa.invetsa.R;
import com.grayhatcorp.invetsa.invetsa.clase.clsHoja_verificacion;
import com.grayhatcorp.invetsa.invetsa.clase.clsSistema_integral;

import java.util.ArrayList;

/**
 * Created by HOME on 04/04/2017.
 */

public class Item_sistema_integral extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<clsSistema_integral> items;


    public Item_sistema_integral (Activity activity, ArrayList<clsSistema_integral> items) {
        this.activity = activity;
        this.items = items;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<clsSistema_integral> Historial) {
        for (int i = 0; i < Historial.size(); i++) {
            items.add(Historial.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_hoja_verificacion, null);
        }

        final clsSistema_integral dir = items.get(position);



        TextView tv_detalle = (TextView) v.findViewById(R.id.tv_detalle);
        TextView tv_nombre_empresa = (TextView) v.findViewById(R.id.tv_nombre_empresa);

        tv_detalle.setText("Formulario Nro:"+dir.getId());
        tv_nombre_empresa.setText("Empresa : "+dir.getNombre_empresa());



        return v;
    }








}
