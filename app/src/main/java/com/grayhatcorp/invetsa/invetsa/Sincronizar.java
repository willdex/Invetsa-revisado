package com.grayhatcorp.invetsa.invetsa;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.grayhatcorp.invetsa.invetsa.Base_de_datos.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Sincronizar extends AppCompatActivity implements View.OnClickListener{
    //FORMULARIO  SPRAVAC - STWIN SHOT - ZOOTEC
    JSONArray jsa_servicio_mantenimiento;
    JSONArray jsa_inspeccion_visual;
    JSONArray jsa_inspeccion_funcionamiento;
    JSONArray jsa_detalle_inspeccion_visual;
    JSONArray jsa_detalle_inspeccion_funcionamiento;

    //SIM
    JSONArray jsa_sistema_integral;
    JSONArray jsa_puntuacion;
    JSONArray jsa_detalle_puntuacion;
    JSONArray jsa_peso;

    //HOJA DE VERIFICACION
    JSONArray js_hoja_verificacion;
    JSONArray js_accion;
    JSONArray js_detalle_accion;
    JSONArray js_manipulacion_dilucion;
    JSONArray js_control_indice;
    JSONArray js_linea_genetica;
    JSONArray js_viabilidad_celular;


    Button bt_sincronizar;
    ProgressBar pb_sincronizar;
    Suceso suceso;
    ProgressDialog pDialog;

    AlertDialog alertDialog_sincronizar;
    Handler handle=new Handler();
    Thread thread_sincronizar;
    int iteraccion=0;
    int iteraccion_correcto=0;
    int total=0;
    int iteraccion_servicio_mantenimiento=0;
    int total_servicio_mantenimiento=0;
    int iteraccion_hoja_verificacion=0;
    int total_hoja_verificacion=0;
    int iteraccion_sistema_integral=0;
    int total_sistema_integral=0;

    boolean sw_sincronizando=false;
    String ip;

    Boolean sw_final=true;

    AlertDialog alertDialog_firmar_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bt_sincronizar=(Button)findViewById(R.id.bt_sincronizar);
        pb_sincronizar=(ProgressBar) findViewById(R.id.pb_sincronizar);

        bt_sincronizar.setOnClickListener(this);

        ip=getString(R.string.servidor);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_sincronizar)
        {


            AlertDialog.Builder   builder_dialogo = new AlertDialog.Builder(this);
            final LayoutInflater inflater = getLayoutInflater();

            final View dialoglayout = inflater.inflate(R.layout.progress_dialogo, null);
            final TextView tv_detalle=(TextView) dialoglayout.findViewById(R.id.tv_detalle);
            final TextView tv_proceso=(TextView) dialoglayout.findViewById(R.id.tv_proceso);
            final ProgressBar progressBar=(ProgressBar)dialoglayout.findViewById(R.id.progressBar);
            Button bt_aceptar=(Button) dialoglayout.findViewById(R.id.bt_aceptar);
            bt_aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog_sincronizar.hide();
                }
            });
            // Start the animation (looped playback by default).

            builder_dialogo.setView(dialoglayout);
            alertDialog_sincronizar=builder_dialogo.create();
            alertDialog_sincronizar.setCancelable(false);
            alertDialog_sincronizar.show();

         sw_final=true;

          iteraccion=0;
          iteraccion_correcto=0;

           iteraccion_hoja_verificacion=0;
           iteraccion_servicio_mantenimiento=0;
           iteraccion_sistema_integral=0;

          total_servicio_mantenimiento=get_cantidad_servicio_mantenimiento();
          total_hoja_verificacion=get_cantidad_hoja_verificacion();
          total_sistema_integral=get_cantidad_sistema_integral();

          total=total_hoja_verificacion+total_servicio_mantenimiento+total_sistema_integral;
            progressBar.setMax(total);

            thread_sincronizar= new Thread(new Runnable() {
                @Override
                public void run() {
//1800 es 3 minutos.
                    while (iteraccion <=total+1) {

                       handle.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_proceso.setText(iteraccion+"/"+total);
                                progressBar.setProgress(iteraccion);

                                if(sw_sincronizando==false && iteraccion<=total && iteraccion_servicio_mantenimiento<=total_servicio_mantenimiento && total_servicio_mantenimiento>0)
                                {
                                    sw_sincronizando=true;

                                    int id_servicio=get_id_servicio_mantenimiento(iteraccion_servicio_mantenimiento);
                                    String nombre=get_formulario_servicio_mantenimiento(id_servicio);

                                    jsa_servicio_mantenimiento= cargar_servicio_mantenimiento(id_servicio);
                                    jsa_inspeccion_visual=cargar_inspeccion_visual(id_servicio);
                                    jsa_detalle_inspeccion_visual=cargar_detalle_inspeccion_visual(id_servicio);
                                    jsa_inspeccion_funcionamiento=cargar_inspeccion_funcionamiento(id_servicio);
                                    jsa_detalle_inspeccion_funcionamiento=cargar_detalle_inspeccion_funcionamiento(id_servicio);

                                    if(jsa_servicio_mantenimiento.length()>0 && jsa_inspeccion_visual.length()>0 && jsa_inspeccion_funcionamiento.length()>0 && jsa_detalle_inspeccion_visual.length()>0 && jsa_detalle_inspeccion_funcionamiento.length()>0) {
                                        tv_detalle.setText("Sincronizando el formulario "+nombre+" con número "+id_servicio);
                                        Servicio_sincronizar servicio_s = new Servicio_sincronizar();
                                        servicio_s.execute(ip + "frmSincronizar.php?opcion=cargar_datos_servicio_mantenimiento", "1", jsa_servicio_mantenimiento.toString(), jsa_inspeccion_visual.toString(), jsa_detalle_inspeccion_visual.toString(), jsa_inspeccion_funcionamiento.toString(), jsa_detalle_inspeccion_funcionamiento.toString());// parametro que recibe el doinbackground
                                    }
                                    else
                                    {
                                        tv_detalle.setText("preparando los formularios. . .");
                                        iteraccion++;
                                        iteraccion_servicio_mantenimiento++;
                                        sw_sincronizando=false;
                                        if(iteraccion>=total)
                                        {
                                            sw_final=true;
                                        }
                                    }

                                }
                                else if(sw_sincronizando==false && iteraccion<=total && iteraccion_sistema_integral<=total_sistema_integral && total_sistema_integral>0)
                                {
                                    sw_sincronizando=true;

                                    int id_sistema=get_id_sistema_integral(iteraccion_sistema_integral);

                                    jsa_sistema_integral=cargar_sistema_integral(id_sistema);
                                    jsa_puntuacion=cargar_puntuacion(id_sistema);
                                    jsa_detalle_puntuacion=cargar_detalle_puntuacion(id_sistema);
                                    jsa_peso=cargar_peso(id_sistema);

                                    if(jsa_sistema_integral.length()>0 && jsa_puntuacion.length()>0 && jsa_detalle_puntuacion.length()>0 && jsa_peso.length()>0) {
                                        tv_detalle.setText("Sincronizando el formulario Sistema Integral de Monitoreo con número "+id_sistema);
                                        Servicio_sincronizar servicio_s = new Servicio_sincronizar();
                                        servicio_s.execute(ip + "frmSincronizar.php?opcion=cargar_datos_sistema_integral", "2", jsa_sistema_integral.toString(), jsa_puntuacion.toString(), jsa_detalle_puntuacion.toString(), jsa_peso.toString());// parametro que recibe el doinbackground
                                    }
                                    else
                                    {
                                        tv_detalle.setText("preparando los formularios. . .");
                                        iteraccion++;
                                        iteraccion_sistema_integral++;
                                        sw_sincronizando=false;
                                        if(iteraccion>=total)
                                        {
                                            sw_final=true;
                                        }
                                    }
                                }
                                else if(sw_sincronizando==false && iteraccion<=total && iteraccion_hoja_verificacion<=total_hoja_verificacion && total_hoja_verificacion>0)
                                {
                                    sw_sincronizando=true;

                                    int id_hoja=get_id_hoja_verificacion(iteraccion_hoja_verificacion);

                                    js_hoja_verificacion=cargar_hoja_verificacion(id_hoja);
                                    js_linea_genetica=cargar_linea_genetica(id_hoja);
                                    js_accion=cargar_accion(id_hoja);
                                    js_detalle_accion=cargar_detalle_accion(id_hoja);
                                    js_manipulacion_dilucion=cargar_manipulacion_dilucion(id_hoja);
                                    js_control_indice=cargar_control_indice(id_hoja);
                                    js_viabilidad_celular=cargar_viabilidad_celular(id_hoja);

                                    if(js_hoja_verificacion.length()>0 && js_linea_genetica.length()>0 && js_accion.length()>0 && js_detalle_accion.length()>0 && js_manipulacion_dilucion.length()>0 && js_linea_genetica.length()>0) {
                                        tv_detalle.setText("Sincronizando el formulario Hoja de Verificacón con número "+id_hoja);
                                        Servicio_sincronizar servicio_s = new Servicio_sincronizar();
                                        servicio_s.execute(ip + "frmSincronizar.php?opcion=cargar_datos_hoja_verificacion", "3", js_hoja_verificacion.toString(), js_accion.toString(), js_detalle_accion.toString(), js_manipulacion_dilucion.toString(), js_control_indice.toString(), js_linea_genetica.toString(), js_viabilidad_celular.toString());// parametro que recibe el doinbackground
                                    }else
                                    {
                                        tv_detalle.setText("preparando los formularios. . .");
                                        iteraccion_hoja_verificacion++;
                                        iteraccion++;
                                        sw_sincronizando=false;
                                        if(iteraccion>=total)
                                        {
                                            sw_final=true;
                                        }
                                    }
                                }

                                if(sw_sincronizando==false && total<=iteraccion && sw_final==true)
                                {
                                    sw_final=false;
                                    sw_sincronizando=false;
                                    tv_detalle.setText("Finalizacion de la sincronizaciòn correctamente. Total : "+total+" Sincronizado : "+iteraccion_correcto);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    tv_proceso.setVisibility(View.INVISIBLE);
                                }
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });
            thread_sincronizar.start();











/*

            Servicio hilo_pedido = new Servicio();
            //FORMULARIO SPRAVAC - STWIN SHOT - ZOOTEC
            jsa_servicio_mantenimiento= cargar_servicio_mantenimiento();
            jsa_inspeccion_visual=cargar_inspeccion_visual();
            jsa_detalle_inspeccion_visual=cargar_detalle_inspeccion_visual();
            jsa_inspeccion_funcionamiento=cargar_inspeccion_funcionamiento();
            jsa_detalle_inspeccion_funcionamiento=cargar_detalle_inspeccion_funcionamiento();

            //SISTEMA INTEGRAL DE MONITOREO
            jsa_sistema_integral=cargar_sistema_integral();
            jsa_puntuacion=cargar_puntuacion();
            jsa_detalle_puntuacion=cargar_detalle_puntuacion();
            jsa_peso=cargar_peso();

            //HOJA DE VERIFICACION
            js_hoja_verificacion=cargar_hoja_verificacion();
            js_accion=cargar_accion();
            js_detalle_accion=cargar_detalle_accion();
            js_manipulacion_dilucion=cargar_manipulacion_dilucion();
            js_control_indice=cargar_control_indice();



            if(jsa_servicio_mantenimiento.length()>0 )
            {
                hilo_pedido.execute(ip + "frmSincronizar.php?opcion=cargar_datos_servicio_mantenimiento", "1", jsa_servicio_mantenimiento.toString(),jsa_inspeccion_visual.toString(),jsa_detalle_inspeccion_visual.toString(),jsa_inspeccion_funcionamiento.toString(),jsa_detalle_inspeccion_funcionamiento.toString());// parametro que recibe el doinbackground
                Log.i("Item", "inicio de insertar.!");
            }

            if( jsa_sistema_integral.length()>0)
            {
                hilo_pedido.execute(ip + "frmSincronizar.php?opcion=cargar_datos_sistema_integral", "2",jsa_sistema_integral.toString(),jsa_puntuacion.toString(),jsa_detalle_puntuacion.toString(),jsa_peso.toString());// parametro que recibe el doinbackground
                Log.i("Item", "inicio de insertar.!");
            }

            if(js_hoja_verificacion.length()>0 )
            {
                hilo_pedido.execute(ip + "frmSincronizar.php?opcion=cargar_datos_hoja_verificacion", "3",js_hoja_verificacion.toString(),js_accion.toString(),js_detalle_accion.toString(),js_manipulacion_dilucion.toString(),js_control_indice.toString() );// parametro que recibe el doinbackground
                Log.i("Item", "inicio de insertar.!");
            }
            else {
                Log.i("Item", "datos vacios..!");
            }
*/
        }
    }

    private JSONArray cargar_viabilidad_celular(int id_hoja) {
        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from viabilidad_celular where id_hoja_verificacion='"+id_hoja+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            do {
                try {

                    String id = fila.getString(0);
                    String antibiotico=fila.getString(1);
                    String dosis=fila.getString(2);
                    String tiempo=fila.getString(3);
                    String vc=fila.getString(4);
                    String id_hoja_verificacion = fila.getString(5);
                    String s_imei=fila.getString(6);


                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("antibiotico",antibiotico);
                    object.put("dosis",dosis);
                    object.put("tiempo",tiempo);
                    object.put("vc",vc);
                    object.put("id_hoja_verificacion",id_hoja_verificacion);
                    object.put("imei",s_imei);

                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    private JSONArray cargar_linea_genetica(int id_hoja) {
        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from linea_genetica where id_hoja_verificacion='"+id_hoja+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String descripcion=fila.getString(1);
                    String cobb=fila.getString(2);
                    String ross=fila.getString(3);
                    String hybro=fila.getString(4);
                    String id_hoja_verificacion = fila.getString(5);
                    String s_imei=fila.getString(6);


                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("descripcion",descripcion);
                    object.put("cobb",cobb);
                    object.put("ross",ross);
                    object.put("hybro",hybro);
                    object.put("id_hoja_verificacion",id_hoja_verificacion);
                    object.put("imei",s_imei);

                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    private JSONArray cargar_control_indice(int id_hoja) {
        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from control_indice where id_hoja_verificacion='"+id_hoja+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String nro_pollos_vacunado = fila.getString(1);
                    String puntaje = fila.getString(2);
                    String nro_pollos_controlados = fila.getString(3);
                    String nro_pollos_no_vacunados = fila.getString(4);
                    String nro_heridos = fila.getString(5);
                    String nro_mojados = fila.getString(6);
                    String nro_mala_posicion = fila.getString(7);
                    String nro_pollos_vacunados_correctamente = fila.getString(8);
                    String indice_eficiencia = fila.getString(9);
                    String id_hoja_verificacion = fila.getString(10);
                    String id_vacunador = fila.getString(11);
                    String s_imei = fila.getString(12);

                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("nro_pollos_vacunado",nro_pollos_vacunado);
                    object.put("puntaje",puntaje);
                    object.put("nro_pollos_controlados",nro_pollos_controlados);
                    object.put("nro_pollos_no_vacunados",nro_pollos_no_vacunados);
                    object.put("nro_heridos",nro_heridos);
                    object.put("nro_mojados",nro_mojados);
                    object.put("nro_mala_posicion",nro_mala_posicion);
                    object.put("nro_pollos_vacunados_correctamente",nro_pollos_vacunados_correctamente);
                    object.put("id_vacunador",id_vacunador);
                    object.put("indice_eficiencia",indice_eficiencia);
                    object.put("id_hoja_verificacion",id_hoja_verificacion);
                    object.put("imei",s_imei);


                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    private JSONArray cargar_manipulacion_dilucion(int id_hoja) {
        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from manipulacion_dilucion  where id_hoja_verificacion='"+id_hoja+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String descripcion = fila.getString(1);
                    String puntaje = fila.getString(2);
                    String id_hoja_verificacion = fila.getString(3);
                    String s_imei = fila.getString(4);

                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("descripcion",descripcion);
                    object.put("puntaje",puntaje);
                    object.put("id_hoja_verificacion",id_hoja_verificacion);
                    object.put("imei",s_imei);

                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    private JSONArray cargar_detalle_accion( int id_hoja) {
        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from detalle_accion where id_hoja_verificacion='"+id_hoja+"' ", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String esperado = fila.getString(1);
                    String encontrado = fila.getString(2);
                    String id_accion = fila.getString(3);
                    String id_hoja_verificacion = fila.getString(4);
                    String s_imei = fila.getString(5);

                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("esperado",esperado);
                    object.put("encontrado",encontrado);
                    object.put("id_accion",id_accion);
                    object.put("id_hoja_verificacion",id_hoja_verificacion);
                    object.put("imei",s_imei);

                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    private JSONArray cargar_accion(int id_hoja) {
        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from accion where id_hoja_verificacion='"+id_hoja+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {
                    String id = fila.getString(0);
                    String nombre = fila.getString(1);
                    String id_hoja_verificacion = fila.getString(2);
                    String s_imei = fila.getString(3);

                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("nombre",nombre);
                    object.put("id_hoja_verificacion",id_hoja_verificacion);
                    object.put("imei",s_imei);

                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    private int get_id_hoja_verificacion(int limite) {
        int id=0;

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from hoja_verificacion limit "+limite+",10", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            try {

                id = Integer.parseInt(fila.getString(0));

            } catch (Exception e)
            {
                id=0;
            }

        } else
        {

        }
        bd.close();
        return  id;

    }
    private int get_cantidad_hoja_verificacion() {
        int cantidad=0;

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select count(*) from hoja_verificacion ", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            try {

                cantidad = Integer.parseInt(fila.getString(0));

            } catch (Exception e)
            {
                cantidad=0;
            }

        } else
        {

        }
        bd.close();
        return  cantidad;

    }
    private JSONArray cargar_hoja_verificacion(int id_hoja) {


            JSONArray array=new JSONArray();

            SQLite admin = new SQLite(this,
                    "invetsa", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery("select * from hoja_verificacion where id='"+id_hoja+"'", null);

            if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


                    try {

                        String id = fila.getString(0);
                        String fecha = fila.getString(1);
                        String hora_ingreso = fila.getString(2);
                        String hora_salidas = fila.getString(3);
                        String codigo = fila.getString(4);
                        String revision = fila.getString(5);
                        String firma_invetsa = fila.getString(6);
                        String firma_empresa = fila.getString(7);
                        String productividad = fila.getString(8);
                        String sumatoria_manipulacion_vacuna = fila.getString(9);
                        String promedio_mantenimiento = fila.getString(10);
                        String puntaje_control_indice = fila.getString(11);
                        String id_galpon = fila.getString(12);
                        String id_granja = fila.getString(13);
                        String id_empresa = fila.getString(14);
                        String s_imei = fila.getString(15);
                        String id_tecnico = fila.getString(16);
                        String imagen_jefe = fila.getString(17);
                        String responsable_invetsa = fila.getString(18);
                        String responsable_incubadora = fila.getString(19);
                        String total_vc = fila.getString(20);
                        String puntaje_total = fila.getString(21);

                        String s_firma_invetsa=getStringImage(imagen_en_vista(firma_invetsa));
                        String s_firma_empresa=getStringImage(imagen_en_vista(firma_empresa));
                        String s_imagen_jefe=getStringImage(imagen_en_vista(imagen_jefe));

                        JSONObject object = new JSONObject();
                        object.put("id",id);
                        object.put("fecha",fecha);
                        object.put("hora_ingreso",hora_ingreso);
                        object.put("hora_salidas",hora_salidas);
                        object.put("codigo",codigo);
                        object.put("revision",revision);
                        object.put("firma_invetsa",s_firma_invetsa);
                        object.put("firma_empresa",s_firma_empresa);
                        object.put("productividad",productividad);
                        object.put("sumatoria_manipulacion_vacuna",sumatoria_manipulacion_vacuna);
                        object.put("promedio_mantenimiento",promedio_mantenimiento);
                        object.put("puntaje_control_indice",puntaje_control_indice);
                        object.put("id_galpon",id_galpon);
                        object.put("id_granja",id_granja);
                        object.put("id_empresa",id_empresa);
                        object.put("imei",s_imei);
                        object.put("id_tecnico",id_tecnico);
                        object.put("imagen_jefe",s_imagen_jefe);
                        object.put("responsable_invetsa",responsable_invetsa);
                        object.put("responsable_incubadora",responsable_incubadora);
                        object.put("total_vc",total_vc);
                        object.put("puntaje_total",puntaje_total);

                        array.put(object);
                    } catch (Exception e)
                    {

                    }

            } else
            {

            }
            bd.close();
            return  array;

    }


    public class Servicio_sincronizar extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null;  // url donde queremos obtener informacion
            String devuelve = "";

            if (params[1] == "1") {
                try {
                    HttpURLConnection urlConn;

                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();


                    //se crea el objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("servicio_mantenimiento",params[2]);
                    jsonParam.put("inspeccion_visual",params[3]);
                    jsonParam.put("detalle_inspeccion_visual",params[4]);
                    jsonParam.put("inspeccion_funcionamiento",params[5]);
                    jsonParam.put("detalle_inspeccion_funcionamiento",params[6]);


                    //Envio los prametro por metodo post
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }


                        JSONObject respuestaJSON = new JSONObject(result.toString());//Creo un JSONObject a partir del
                        suceso=new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));
                        // vacia los datos que estan registrados en nuestra base de datos SQLite..

                        if (suceso.getSuceso().equals("1")) {
                            devuelve="1";
                        } else  {
                            devuelve = "2";
                        }

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (params[1] == "2") {
                try {
                    HttpURLConnection urlConn;

                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();


                    //se crea el objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("sistema_integral",params[2]);
                    jsonParam.put("puntuacion",params[3]);
                    jsonParam.put("detalle_puntuacion",params[4]);
                    jsonParam.put("peso",params[5]);


                    //Envio los prametro por metodo post
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }


                        JSONObject respuestaJSON = new JSONObject(result.toString());//Creo un JSONObject a partir del
                        suceso=new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));
                        // vacia los datos que estan registrados en nuestra base de datos SQLite..

                        if (suceso.getSuceso().equals("1")) {

                            devuelve="3";
                        } else  {
                            devuelve = "4";
                        }

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (params[1] == "3") {
                try {
                    HttpURLConnection urlConn;

                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();


                    //se crea el objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("hoja_verificacion",params[2]);
                    jsonParam.put("accion",params[3]);
                    jsonParam.put("detalle_accion",params[4]);
                    jsonParam.put("manipulacion_dilucion",params[5]);
                    jsonParam.put("control_indice",params[6]);
                    jsonParam.put("linea_genetica",params[7]);
                    jsonParam.put("viabilidad_celular",params[8]);


                    //Envio los prametro por metodo post
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }


                        JSONObject respuestaJSON = new JSONObject(result.toString());//Creo un JSONObject a partir del
                        suceso=new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));
                        // vacia los datos que estan registrados en nuestra base de datos SQLite..

                        if (suceso.getSuceso().equals("1")) {

                            devuelve="5";
                        } else  {
                            devuelve = "6";
                        }

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return devuelve;
        }


        @Override
        protected void onPreExecute() {
            //para el progres Dialog
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("onPostExcute=", "" + s);

            if (s.equals("1")) {
                iteraccion++;
                iteraccion_correcto++;
                iteraccion_servicio_mantenimiento++;
                sw_sincronizando=false;
            }
            else if(s.equals("2"))
            {
                iteraccion++;
                iteraccion_servicio_mantenimiento++;
                sw_sincronizando=false;
            }
            else if (s.equals("3")) {
                iteraccion++;
                iteraccion_correcto++;
                iteraccion_sistema_integral++;
                sw_sincronizando=false;
            }
            else if(s.equals("4"))
            {
                iteraccion++;
                iteraccion_sistema_integral++;
                sw_sincronizando=false;
            }
            else if (s.equals("5")) {
                iteraccion++;
                iteraccion_correcto++;
                iteraccion_hoja_verificacion++;
                sw_sincronizando=false;
            }
            else if(s.equals("6"))
            {
                iteraccion++;
                iteraccion_hoja_verificacion++;
                sw_sincronizando=false;
            }
            else
            {
                iteraccion++;
                sw_sincronizando=false;
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

    }

    public JSONArray cargar_servicio_mantenimiento(int id_servicio)
    {

        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from servicio_mantenimiento where id='"+id_servicio+"' limit 1", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

                try {

                    String id = fila.getString(0);
                    String fecha = fila.getString(1);
                    String hora_ingreso = fila.getString(2);
                    String hora_salidas = fila.getString(3);
                    String codigo = fila.getString(4);
                    String revision = fila.getString(5);
                    String firma_jefe = fila.getString(6);
                    String firma_invetsa = fila.getString(7);
                    String id_maquina = fila.getString(8);
                    String id_tecnico = fila.getString(9);
                    String id_compania = fila.getString(10);
                    String s_imei = fila.getString(11);
                    String formulario = fila.getString(12);
                    String imagen_jefe = fila.getString(13);

                    String s_firma_invetsa=getStringImage(imagen_en_vista(firma_invetsa));
                    String s_firma_jefe=getStringImage(imagen_en_vista(firma_jefe));
                    String s_imagen_jefe=getStringImage(imagen_en_vista(imagen_jefe));

                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("fecha",fecha);
                    object.put("hora_ingreso",hora_ingreso);
                    object.put("hora_salidas",hora_salidas);
                    object.put("codigo",codigo);
                    object.put("revision",revision);
                    object.put("firma_jefe",s_firma_jefe);
                    object.put("firma_invetsa",s_firma_invetsa);
                    object.put("id_maquina",id_maquina);
                    object.put("id_tecnico",id_tecnico);
                    object.put("id_compania",id_compania);
                    object.put("imei",s_imei);
                    object.put("formulario",formulario);
                    object.put("imagen_jefe",s_imagen_jefe);

                    array.put(object);
                } catch (Exception e)
                {

                }

        } else
        {

        }
        bd.close();
        return  array;
    }
    public int get_id_servicio_mantenimiento(int limite)
    {
        int id=0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from servicio_mantenimiento limit "+limite+",10", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            id = Integer.parseInt(fila.getString(0));
        }
        bd.close();
        return id;
    }
    public String get_formulario_servicio_mantenimiento(int id_servicio)
    {
        String formulario="";
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select formulario from servicio_mantenimiento where id='"+id_servicio+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            formulario = fila.getString(0);
        }
        bd.close();
        return formulario;
    }
    public int get_cantidad_servicio_mantenimiento()
    {
        int cantidad=0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select count(*) from servicio_mantenimiento", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            cantidad = Integer.parseInt(fila.getString(0));
        }
        bd.close();
        return cantidad;
    }

    public JSONArray cargar_inspeccion_visual(int id_serv)
    {

        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from inspeccion_visual where id_servicio='"+id_serv+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String observaciones = fila.getString(1);
                    String piesas_cambiadas = fila.getString(2);
                    String id_servicio = fila.getString(3);
                    String s_imei = fila.getString(4);


                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("observaciones",observaciones);
                    object.put("piesas_cambiadas",piesas_cambiadas);
                    object.put("id_servicio",id_servicio);
                    object.put("imei",s_imei);


                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    public JSONArray cargar_detalle_inspeccion_visual(int id_serv)
    {

        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from detalle_inspeccion_visual where id_servicio='"+id_serv+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try{

                    String id = fila.getString(0);
                    String id_inspeccion = fila.getString(1);
                    String codigo = fila.getString(2);
                    String descripcion = fila.getString(3);
                    String estado = fila.getString(4);
                    String s_imei = fila.getString(5);
                    String id_servicio = fila.getString(6);


                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("id_inspeccion",id_inspeccion);
                    object.put("codigo",codigo);
                    object.put("descripcion",descripcion);
                    object.put("estado",estado);
                    object.put("imei",s_imei);
                    object.put("id_servicio",id_servicio);


                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {


        }
        bd.close();
        return  array;
    }

    public JSONArray cargar_inspeccion_funcionamiento(int id_serv)
    {

        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from inspeccion_funcionamiento where id_servicio='"+id_serv+"' ", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {
                    String id = fila.getString(0);
                    String observaciones = fila.getString(1);
                    String frecuencia = fila.getString(2);
                    String id_servicio = fila.getString(3);
                    String s_imei = fila.getString(4);

                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("observaciones",observaciones);
                    object.put("frecuencia",frecuencia);
                    object.put("id_servicio",id_servicio);
                    object.put("imei",s_imei);

                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    public JSONArray cargar_detalle_inspeccion_funcionamiento(int id_serv)
    {

        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from detalle_inspeccion_funcionamiento where id_servicio='"+id_serv+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String id_inspeccion = fila.getString(1);
                    String criterio_evaluacion = fila.getString(2);
                    String estado = fila.getString(3);
                    String s_imei = fila.getString(4);
                    String id_servicio = fila.getString(5);


                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("id_inspeccion",id_inspeccion);
                    object.put("criterio_evaluacion",criterio_evaluacion);
                    object.put("imei",s_imei);
                    object.put("estado",estado);
                    object.put("id_servicio",id_servicio);


                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    public int get_id_sistema_integral(int limite)
    {
        int id=0;

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from sistema_integral limit "+limite+", 10", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

                try {

                  id = Integer.parseInt(fila.getString(0));

                } catch (Exception e)
                {
                   id=0;
                }

        } else
        {

        }
        bd.close();
        return  id;
    }
    public int get_cantidad_sistema_integral()
    {
        int cantidad=0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select count(*) from sistema_integral ", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            try {
                cantidad = Integer.parseInt(fila.getString(0));
            } catch (Exception e)
            {
            cantidad=0;
            }

        } else
        {

        }
        bd.close();
        return cantidad;
    }
    public JSONArray cargar_sistema_integral(int id_sis)
    {

        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from sistema_integral where id='"+id_sis+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            try {

                String id = fila.getString(0);
                String codigo = fila.getString(1);
                String revision = fila.getString(2);
                String edad = fila.getString(3);
                String sexo = fila.getString(4);
                String observaciones = fila.getString(5);
                String comentarios = fila.getString(6);
                String imagen1 = fila.getString(7);
                String imagen2 = fila.getString(8);
                String imagen3 = fila.getString(9);
                String imagen4 = fila.getString(10);
                String imagen5 = fila.getString(11);
                String fecha= fila.getString(12);
                String nro_pollos=fila.getString(13);
                String id_galpon = fila.getString(14);
                String id_granja = fila.getString(15);
                String id_empresa = fila.getString(16);
                String s_imei = fila.getString(17);
                String firma_invetsa = fila.getString(18);
                String firma_empresa = fila.getString(19);
                String id_tecnico = fila.getString(20);
                String imagen_jefe = fila.getString(21);


                String s_imagen1=getStringImage(imagen_en_vista(imagen1));
                String s_imagen2=getStringImage(imagen_en_vista(imagen2));
                String s_imagen3=getStringImage(imagen_en_vista(imagen3));
                String s_imagen4=getStringImage(imagen_en_vista(imagen4));
                String s_imagen5=getStringImage(imagen_en_vista(imagen5));
                String s_firma_invetsa=getStringImage(imagen_en_vista(firma_invetsa));
                String s_firma_empresa=getStringImage(imagen_en_vista(firma_empresa));
                String s_imagen_jefe=getStringImage(imagen_en_vista(imagen_jefe));

                JSONObject object = new JSONObject();
                object.put("id",id);
                object.put("codigo",codigo);
                object.put("revision",revision);
                object.put("edad",edad);
                object.put("sexo",sexo);
                object.put("observaciones",observaciones);
                object.put("comentarios",comentarios);
                object.put("imagen1",s_imagen1);
                object.put("imagen2",s_imagen2);
                object.put("imagen3",s_imagen3);
                object.put("imagen4",s_imagen4);
                object.put("imagen5",s_imagen5);
                object.put("fecha",fecha);
                object.put("nro_pollos",nro_pollos);
                object.put("id_galpon",id_galpon);
                object.put("id_granja",id_granja);
                object.put("id_empresa",id_empresa);
                object.put("imei",s_imei);
                object.put("firma_invetsa",s_firma_invetsa);
                object.put("firma_empresa",s_firma_empresa);
                object.put("id_tecnico",id_tecnico);
                object.put("imagen_jefe",s_imagen_jefe);

                array.put(object);
            } catch (Exception e)
            {

            }

        } else
        {

        }
        bd.close();
        return  array;
    }
    public JSONArray cargar_puntuacion(int id_sis)
    {

        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from puntuacion where id_sistema='"+id_sis+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String nombre = fila.getString(1);
                    String id_sistema = fila.getString(2);
                    String s_imei = fila.getString(3);

                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("nombre",nombre);
                    object.put("id_sistema",id_sistema);
                    object.put("imei",s_imei);

                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    public JSONArray cargar_detalle_puntuacion(int id_sis)
    {

        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from detalle_puntuacion where id_sistema='"+id_sis+"' ", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String nombre = fila.getString(1);
                    String estado = fila.getString(2);
                    String id_puntuacion = fila.getString(3);
                    String id_sistema = fila.getString(4);
                    String s_imei = fila.getString(5);


                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("nombre",nombre);
                    object.put("estado",estado);
                    object.put("id_puntuacion",id_puntuacion);
                    object.put("id_sistema",id_sistema);
                    object.put("imei",s_imei);


                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    public JSONArray cargar_peso(int id_sis)
    {


        JSONArray array=new JSONArray();

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from peso where id_sistema='"+id_sis+"'", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                try {

                    String id = fila.getString(0);
                    String peso_corporal  = fila.getString(1);
                    String peso_bursa  = fila.getString(2);
                    String peso_brazo  = fila.getString(3);
                    String peso_timo  = fila.getString(4);
                    String peso_higado  = fila.getString(5);
                    String indice_bursal  = fila.getString(6);
                    String indice_timico  = fila.getString(7);
                    String indice_hepatico  = fila.getString(8);
                    String bursometro  = fila.getString(9);
                    String id_sistema = fila.getString(10);
                    String s_imei = fila.getString(11);




                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    object.put("peso_corporal",peso_corporal);
                    object.put("peso_bursa",peso_bursa);
                    object.put("peso_brazo",peso_brazo);
                    object.put("peso_timo",peso_timo);
                    object.put("peso_higado",peso_higado);
                    object.put("indice_bursal",indice_bursal);
                    object.put("indice_timico",indice_timico);
                    object.put("indice_hepatico",indice_hepatico);
                    object.put("bursometro",bursometro);
                    object.put("id_sistema",id_sistema);
                    object.put("imei",s_imei);

                    array.put(object);
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        return  array;
    }

    public Bitmap imagen_en_vista(String ubicacion)
    {
        // String mPath = Environment.getExternalStorageDirectory() + File.separator + "Taxi Elitex/Imagen"+ File.separator + "perfil.jpg";
        String mPath = Environment.getExternalStorageDirectory() + File.separator + ubicacion;
        Bitmap bitmap = BitmapFactory.decodeFile(mPath);
        return bitmap;
    }

    private String getStringImage(Bitmap bmp) {
        if(bmp!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
        else
            return "";
    }


    private void mensaje_ok_error(String mensaje) {
        AlertDialog.Builder   builder_dialogo = new AlertDialog.Builder(this);

        final LayoutInflater inflater = getLayoutInflater();

        final View dialoglayout = inflater.inflate(R.layout.mensaje_ok, null);
        final TextView tv_mensaje=(TextView)dialoglayout.findViewById(R.id.tv_mensaje);
        Button bt_ok=(Button) dialoglayout.findViewById(R.id.bt_ok);

        tv_mensaje.setText(mensaje);

        builder_dialogo.setView(dialoglayout);
        alertDialog_firmar_1=builder_dialogo.create();
        alertDialog_firmar_1.show();



        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    alertDialog_firmar_1.dismiss();
                }catch (Exception e)
                {
                    alertDialog_firmar_1.hide();
                }
            }
        });
    }

    private void mensaje_ok_cerrar(String mensaje) {
        AlertDialog.Builder   builder_dialogo = new AlertDialog.Builder(this);

        final LayoutInflater inflater = getLayoutInflater();

        final View dialoglayout = inflater.inflate(R.layout.mensaje_ok, null);
        final TextView tv_mensaje=(TextView)dialoglayout.findViewById(R.id.tv_mensaje);
        Button bt_ok=(Button) dialoglayout.findViewById(R.id.bt_ok);

        tv_mensaje.setText(mensaje);

        builder_dialogo.setView(dialoglayout);
        alertDialog_firmar_1=builder_dialogo.create();
        alertDialog_firmar_1.show();



        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    alertDialog_firmar_1.dismiss();
                    finish();
                }catch (Exception e)
                {
                    finish();
                }
            }
        });
    }

}