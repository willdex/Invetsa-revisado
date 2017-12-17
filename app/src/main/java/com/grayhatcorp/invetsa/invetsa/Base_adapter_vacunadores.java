package com.grayhatcorp.invetsa.invetsa;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.grayhatcorp.invetsa.invetsa.clase.clsVacunador;

import java.util.ArrayList;

/**
 * Created by HOME on 30/03/2017.
 */

public class Base_adapter_vacunadores  extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<clsVacunador> items;
    public Context context;


    public Base_adapter_vacunadores (Context context,Activity activity, ArrayList<clsVacunador> items) {
        this.activity = activity;
        this.items = items;
        this.context=context;
    }

    @Override
    public int getCount() {
        return items.size();
    }
    public LayoutInflater getLayoutInflater()
    {
        return  (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<clsVacunador> Direccion) {
        for (int i = 0; i < Direccion.size(); i++) {
            items.add(Direccion.get(i));
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_lista_empleados, null);
        }

        final clsVacunador dir = items.get(position);


        TextView tv_nombre_vacunador = (TextView) v.findViewById(R.id.tv_nombre_vacunador);
        final CheckBox cb_estado = (CheckBox) v.findViewById(R.id.cb_estado);

        cb_estado.setChecked(dir.isEstado());
        tv_nombre_vacunador.setText(dir.getNombre());

        if (dir.isEstado())
        {
            cb_estado.setChecked(true);
        }
        else
        {
            cb_estado.setChecked(false);
        }
        cb_estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_estado.isChecked() == true) {
                    items.remove(position);
                    clsVacunador nuevo = new clsVacunador(dir.getId(), dir.getNombre(), true);
                    items.add(position,nuevo);
                } else
                {
                    items.remove(position);
                    clsVacunador nuevo = new clsVacunador(dir.getId(), dir.getNombre(), false);
                    items.add(position,nuevo);
                }

            }
        });







        return v;
    }


}
