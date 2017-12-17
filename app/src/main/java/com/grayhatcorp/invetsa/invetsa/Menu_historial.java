package com.grayhatcorp.invetsa.invetsa;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Menu_historial extends AppCompatActivity {

    private static final int Date_id = 0;
    TextView toolbarTitle;
    int dia,mes,anio;
    String s_mes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_historial);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Calendar c = Calendar.getInstance();
        anio = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH)+1;
        dia = c.get(Calendar.DAY_OF_MONTH);
        toolbarTitle= (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(dia+" de "+mes_numero_a_letra(mes)+" del "+anio);

        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Log.e("menu","Click");  // DO SOMETHING HERE
                showDialog(Date_id);
            }
        });

    }


    //metodos para los dialogos de hora y fecha
    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        dia = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(Menu_historial.this, date_listener, anio,mes, dia);

        }
        return null;
    }



    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            dia=day;
            mes=month+1;
            anio=year;


            toolbarTitle.setText(dia+" de "+mes_numero_a_letra(mes)+" del "+anio);
        }
    };

    public String mes_numero_a_letra(int i)
    {
        String mes="";
        switch (i)
        {
            case 1: mes="Enero"; break;
            case 2: mes="Febrero"; break;
            case 3: mes="Marzo"; break;
            case 4: mes="Abril"; break;
            case 5: mes="Mayo"; break;
            case 6: mes="Junio"; break;
            case 7: mes="Julio"; break;
            case 8: mes="Agosto"; break;
            case 9: mes="Septiembre"; break;
            case 10: mes="Octubre"; break;
            case 11: mes="Noviembre"; break;
            case 12: mes="Diciembre"; break;
        }

       return  mes;
    }
}
