package com.grayhatcorp.invetsa.invetsa.servicio_mantenimiento.twin_shot;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grayhatcorp.invetsa.invetsa.Base_de_datos.SQLite;
import com.grayhatcorp.invetsa.invetsa.R;
import com.grayhatcorp.invetsa.invetsa.clase.clsServicio_mantenimiento;
import com.grayhatcorp.invetsa.invetsa.servicio_mantenimiento.Item_servicio_mantenimiento;

import java.util.ArrayList;
import java.util.Calendar;

public class Main_twin_shot extends AppCompatActivity implements View.OnClickListener {
    Button bt_nuevo,bt_fecha;
    ImageButton ib_filtro,ib_derecha,ib_izquierda;
    ListView lv_lista;
    TextView tv_mensaje;
    ArrayList<clsServicio_mantenimiento> historial;

    int anio=0;
    int mes=0;
    int dia=0;
    String filtro="ASC";
    private static final int Date_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_twin_shot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tv_mensaje=(TextView)findViewById(R.id.tv_mensaje);
        bt_nuevo=(Button)findViewById(R.id.bt_nuevo);
        bt_fecha=(Button)findViewById(R.id.bt_fecha);
        ib_filtro=(ImageButton)findViewById(R.id.ib_filtro);
        ib_derecha=(ImageButton)findViewById(R.id.ib_derecha);
        ib_izquierda=(ImageButton)findViewById(R.id.ib_izquierda);
        lv_lista=(ListView)findViewById(R.id.lv_lista);

        bt_nuevo.setOnClickListener(this);
        bt_fecha.setOnClickListener(this);
        ib_filtro.setOnClickListener(this);
        ib_izquierda.setOnClickListener(this);
        ib_derecha.setOnClickListener(this);



        Calendar c = Calendar.getInstance();
        anio = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH)+1;
        dia = c.get(Calendar.DAY_OF_MONTH);

        bt_fecha.setText("HOY");
        actualizar_lista(dia,mes,anio,filtro);

        lv_lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clsServicio_mantenimiento hi=new clsServicio_mantenimiento();
                hi=historial.get(i);
                abrir_detalle(hi);

            }
        });



    }

    @Override
    protected void onRestart() {
        Log.e("Restart","1");
        actualizar_lista(dia,mes,anio,filtro);
        super.onRestart();
    }

    private void abrir_detalle(clsServicio_mantenimiento hi) {
        Intent carrerra=new Intent(this,Detalle_twin_shot.class);
        carrerra.putExtra("id_twi",String.valueOf(hi.getId()));
        startActivity(carrerra);
    }

    //metodos para los dialogos de hora y fecha
    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        anio = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(this, date_listener, anio,mes, dia);

        }
        return null;
    }



    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            dia = day;
            mes = month + 1;
            anio = year;

            Calendar c = Calendar.getInstance();
            int anio_1 = c.get(Calendar.YEAR);
            int mes_1 = c.get(Calendar.MONTH) + 1;
            int dia_1 = c.get(Calendar.DAY_OF_MONTH);

            if (anio_1 == anio && mes_1 == mes && dia_1 == dia)
            {
                bt_fecha.setText("HOY");
            }
            else
            {
                bt_fecha.setText(dia+" de "+mes_numero_a_letra(mes)+" del "+anio);
            }
            actualizar_lista(dia,mes,anio,filtro);
        }
    };

    private void modificar_fecha(int numero_dia) {
        Calendar c = Calendar.getInstance();
        c.set(anio,mes-1,dia);

        c.add(Calendar.DAY_OF_YEAR,numero_dia);
        anio= c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH)+1;
        dia = c.get(Calendar.DAY_OF_MONTH);

        Calendar c1 = Calendar.getInstance();
        int anio_1 = c1.get(Calendar.YEAR);
        int mes_1 = c1.get(Calendar.MONTH) + 1;
        int dia_1 = c1.get(Calendar.DAY_OF_MONTH);

        if (anio_1 == anio && mes_1 == mes && dia_1 == dia)
        {
            bt_fecha.setText("HOY");
        }
        else
        {
            bt_fecha.setText(dia+" de "+mes_numero_a_letra(mes)+" del "+anio);
        }
        actualizar_lista(dia,mes,anio,filtro);
    }

    private void actualizar_lista(int dia,int mes, int anio,String filtro) {

        historial = new ArrayList<clsServicio_mantenimiento>();

        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String consuta="select s.id, s.fecha,s.codigo,s.revision,s.id_maquina,s.id_compania,m.codigo,c.nombre from servicio_mantenimiento s,compania c,maquina m where  s.id_compania=c.id and s.id_maquina=m.id and  s.formulario='TWIN SHOT' and s.fecha='"+anio+"-"+mes+"-"+dia+"' ORDER BY s.id "+filtro;
        try {
            Cursor fila = bd.rawQuery(consuta, null);

            if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

                do {
                    clsServicio_mantenimiento hi = new clsServicio_mantenimiento(Integer.parseInt(fila.getString(0)), fila.getString(1),  fila.getString(2), fila.getString(3), Integer.parseInt(fila.getString(4)), Integer.parseInt(fila.getString(5)), fila.getString(6), fila.getString(7));
                    historial.add(hi);
                } while (fila.moveToNext());
                tv_mensaje.setVisibility(View.INVISIBLE);
            } else
            {
                tv_mensaje.setVisibility(View.VISIBLE);
                tv_mensaje.setText("No hay Registro");
            }
            bd.close();
            Item_servicio_mantenimiento adaptador = new Item_servicio_mantenimiento(this, historial);
            lv_lista.setAdapter(adaptador);
        }catch (Exception e)
        {
            Log.e("Sqlite",""+e);
        }
       /*
        db.execSQL("create table hoja_verificacion(" +

                "id integer not null," +
                "fecha text," +
                "hora_ingreso text," +
                "hora_salida text," +
                "codigo varchar(20)," +
                "revision varchar(20)," +
                "firma_invetsa text," +
                "firma_empresa text," +
                "productividad decimal(4,2)," +
                "sumatoria_manipulacion_vacuna decimal(10,2)," +
                "promedio_mantenimiento decimal(10,2)," +
                "puntaje_control_indice decimal(10,2)," +
                "id_galpon integer," +
                "id_granja integer," +
                "id_empresa integer," +
                "imei text," +
                "id_tecnico integer"+
                ")");

        ArrayList<clsHoja_verificacion> historial = new ArrayList<clsHoja_verificacion>();

        SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
        Item_hoja_verificacion adaptador;
        if (perfil.getString("administrador", "0").equals("1") == true) {
            adaptador = new Item_hoja_verificacion(this,historial));
        } else {
            adaptador = new Item_hoja_verificacion (this,historial);
        }


        lv_lista.setAdapter(adaptador);
*/
    }


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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_nuevo:
                startActivity(new Intent(this, MaquinaTwinShot.class));
                break;
            case R.id.bt_fecha:

                showDialog(Date_id);
                break;
            case R.id.ib_derecha:
                modificar_fecha(1);
                break;
            case R.id.ib_izquierda:
                modificar_fecha(-1);

                break;
            case R.id.ib_filtro:
                AlertDialog.Builder   builder_dialogo = new AlertDialog.Builder(this);
                final AlertDialog alertDialog_filtro;
                final LayoutInflater inflater = getLayoutInflater();

                final View dialoglayout = inflater.inflate(R.layout.layout_filtro, null);
                Button bt_ascendente=(Button) dialoglayout.findViewById(R.id.bt_ascendente);
                Button bt_descendente=(Button) dialoglayout.findViewById(R.id.bt_descendente);


                builder_dialogo.setView(dialoglayout);
                alertDialog_filtro=builder_dialogo.create();
                alertDialog_filtro.show();


                bt_ascendente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filtro="ASC";
                        actualizar_lista(dia,mes,anio,filtro);
                        alertDialog_filtro.hide();
                    }
                });
                bt_descendente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        filtro="DESC";
                        actualizar_lista(dia,mes,anio,filtro);
                        alertDialog_filtro.hide();
                    }
                });
                break;
        }
    }
}
