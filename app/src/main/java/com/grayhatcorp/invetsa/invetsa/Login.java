package com.grayhatcorp.invetsa.invetsa;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grayhatcorp.invetsa.invetsa.Base_de_datos.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static android.Manifest.permission.READ_CONTACTS;
import static com.grayhatcorp.invetsa.invetsa.R.string.servidor;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {
    Suceso suceso;
    EditText codigo;
    ProgressDialog pDialog;
    android.os.Handler handle=new android.os.Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animacion);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //this.Screen.lockOrientation(this);
        getSupportActionBar().hide();

        Thread thread_sincronizar= new Thread(new Runnable() {
            @Override
            public void run() {
//1800 es 3 minutos.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    handle.post(new Runnable() {
                        @Override
                        public void run() {

                            try{
                                SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
                                if(perfil.getInt("id",0)!=0)
                                {
                                    startActivity(new Intent(Login.this,Menu_principal.class));
                                    finish();//
                                }
                                else
                                {
                                    /*getSupportActionBar().show();
                                    setContentView(R.layout.activity_login);
                                    codigo=(EditText)findViewById(R.id.codigo);*/
                                    verificar_sesion();
                                }
                            }catch (Exception e)
                            {
                                /*getSupportActionBar().show();
                                setContentView(R.layout.activity_login);
                                codigo=(EditText)findViewById(R.id.codigo);*/
                                Toast.makeText(getApplicationContext(),"Error al iniciar",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
            }
        });
        thread_sincronizar.start();
    }

    /*********************************************************************************************************/

    public void iniciar_sesion(View v)
    {

        if(codigo.getText().toString().length()>=4) {
            Servicio hilo_pedido = new Servicio();
            hilo_pedido.execute(getString(servidor) + "frmLogin.php?opcion=login", "1", codigo.getText().toString());// parametro que recibe el doinbackground
            Log.i("Iniciar sesion", "correcto");
        }
        else
        {
            Log.i("Iniciar sesion", "incorrecto");

        }
    }

    public void verificar_sesion(){
        String imei="";
        /*WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();*/
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei=telephonyManager.getDeviceId();
        if (imei!="") {
            Servicio hilo_pedido = new Servicio();
            hilo_pedido.execute(getString(R.string.servidor) + "frmLogin.php?opcion=login", "1", imei);// parametro que recibe el doinbackground
        }else{
            Toast.makeText(this,"No esta en un equipo válido", Toast.LENGTH_LONG).show();
        }
    }
    public class Servicio extends AsyncTask<String,Integer,String> {


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
                    jsonParam.put("codigo", params[2]);

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
                        vaciar_historial();
                        if (suceso.getSuceso().equals("1")) {
                            JSONArray usu=respuestaJSON.getJSONArray("perfil");

                            int id=Integer.parseInt(usu.getJSONObject(0).getString("id"));
                            String nombre=usu.getJSONObject(0).getString("nombre");
                            String codigo=usu.getJSONObject(0).getString("codigo");

                            SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
                            SharedPreferences.Editor editor=perfil.edit();
                            editor.putInt("id",id);
                            editor.putString("nombre",nombre);
                            editor.putString("codigo",codigo);
                            editor.commit();

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
            return devuelve;
        }


        @Override
        protected void onPreExecute() {
            //para el progres Dialog
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Autenticando ....");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();//ocultamos proggress dialog
            Log.e("onPostExcute=", "" + s);

            if (s.equals("1")) {
                iniciar_Principal();
                Toast.makeText(getApplicationContext(),getString(R.string.servidor),Toast.LENGTH_LONG).show();
                finish();//
            }
            else if(s.equals("2"))
            {
                Toast.makeText(getApplicationContext(),suceso.getMensaje(),Toast.LENGTH_LONG).show();
                finish();   //agregado para que termine la aplicacion en caso de no ser un usuario valido
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Error: Al conectar con el servidor.",Toast.LENGTH_SHORT).show();
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

    private void iniciar_Principal() {
        startActivity(new Intent(this,Menu_principal.class));
    }
/*
    private void cargar_historial_en_la_lista()
    {
        historial= new ArrayList<CHistorial>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "easymoto", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from pedido ", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {

                int id_pedido=Integer.parseInt(fila.getString(0));
                int id_usuario=Integer.parseInt(fila.getString(1));
                int id_moto=Integer.parseInt(fila.getString(2));
                int calificacion=Integer.parseInt(fila.getString(3));
                int tipo_pedido=Integer.parseInt(fila.getString(4));
                String mensaje=fila.getString(5);
                String fecha=fila.getString(6);
                String fecha_llegado=fila.getString(7);
                int estado=Integer.parseInt(fila.getString(8));
                double latitud=Double.parseDouble(fila.getString(9));
                double longitud=Double.parseDouble(fila.getString(10));
                String nombre=fila.getString(11);
                String apellido=fila.getString(12);
                String celular=fila.getString(13);
                String marca=fila.getString(14);
                String placa=fila.getString(15);
                int estado_moto=Integer.parseInt(fila.getString(16));

                CHistorial hi =new CHistorial( id_usuario,id_pedido,id_moto,calificacion,tipo_pedido,mensaje,fecha,fecha_llegado,estado,latitud,longitud,nombre,apellido,celular,marca, placa,estado_moto);
                historial.add(hi);
            } while(fila.moveToNext());

        } else
            Toast.makeText(this, "No hay registrados" ,
                    Toast.LENGTH_SHORT).show();

        bd.close();
        actualizar_lista();
    }
    */

    private void vaciar_historial()
    {
        try {

            SQLite admin = new SQLite(this,
                    "easymoto", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            db.execSQL("delete from servicio_mantenimiento");
            db.close();
            Log.e("sqlite ", "vaciar historial");
        }catch (Exception e)
        {
            Log.e("sql","Error al vaciar los datos del SQLite.");
        }
    }

}
