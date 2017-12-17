package com.grayhatcorp.invetsa.invetsa.hoja_verificacion;

/**
 * Created by HOME on 04/04/2017.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.grayhatcorp.invetsa.invetsa.Base_adapter_vacunadores;
import com.grayhatcorp.invetsa.invetsa.Base_de_datos.SQLite;
import com.grayhatcorp.invetsa.invetsa.R;
import com.grayhatcorp.invetsa.invetsa.TouchView;
import com.grayhatcorp.invetsa.invetsa.clase.clsVacunador;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class Detalle_Hoja_verificacion extends AppCompatActivity implements View.OnClickListener{

    ArrayList<clsVacunador> al_vacunadores ;

    private static String APP_DIRECTORY = "Invetsa/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "Imagen";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private String mPath;
    private Uri path;
    String[] language ={"Gray Hat Corp","Invetsa","Sofia","Delicia"};
    String[] TemperaturaVacunacion={"24,1°C","24,2°C","24,3°C","27,1°C"};
    String[]HumedadVacunacion={"60,1°C","60,2°C","65,3°C","68,8°C"};

    AlertDialog alertDialog_firmar_1,alertDialog_firmar_2,alerta_lista;




    JSONObject jso_mantenimiento=new JSONObject(),jso_control_indice=new JSONObject();

    TextView fecha,hora_ingreso,hora_salida;

    EditText et_responsable_invetsa,et_responsable_incubadora,et_maquinas;
    EditText et_antibiotico_1,et_antibiotico_2,et_antibiotico_3,et_antibiotico_4,et_dosis_1,et_dosis_2,et_dosis_3,et_dosis_4,et_tiempo_1,et_tiempo_2,et_tiempo_3,et_tiempo_4,et_vc_1,et_vc_2,et_vc_3,et_vc_4,et_vc;
    EditText et_colorante;

    CheckBox cb_1,cb_2,cb_3,cb_4,cb_5,cb_6,cb_7,cb_8,cb_9,cb_10,cb_11,cb_12,cb_13,cb_14,cb_15,cb_16,cb_17,cb_18,cb_19,cb_20;
    TextView tv_productividad,tv_MantenimientoyLimpiesa,tv_ManipulacionDisolucion,tv_IndicedeEficiencia,tv_Puntaje_total;

    TextView tv_Sumatoria_vacuna_congelada,tv_Promedio_Vacunadoras_ACCUVAC,tv_puntaje_control_indice;
    TextView tv_sum_1,tv_sum_2,tv_sum_3,tv_sum_4,tv_sum_5,tv_sum_6,tv_sum_7,tv_sum_8,tv_sum_9,tv_pro_1,tv_pro_2,tv_pro_3,tv_pro_4,tv_pro_5,tv_pro_6,tv_pro_7,tv_pro_8,tv_pro_9;


    AutoCompleteTextView auto_cobb,auto_ross;
    EditText et_hybro;
    TextView tv_nro_nacimiento;
    AutoCompleteTextView auto_empresa,autoTemperaturaVacunacion,autoHumedadVacunacion;
    AutoCompleteTextView auto_temperatura,auto_promedio_temperatura,auto_jeringas,auto_tuberia,auto_esterilizador;

    Spinner sp_granja,sp_unidad;

    Spinner sp_humedad,sp_ventilacion,sp_presion,sp_limpieza_vacunacion,sp_desinfeccion,sp_guantes_lentes,sp_calentador_agua,sp_recipiente_agua,sp_termometro;
    Spinner sp_soporte_ampollas,sp_rompe_ampollas,sp_agujas,sp_alcohol,sp_algodon,sp_papel,sp_colorante,sp_conectores,sp_mesa,sp_cambio_agujas;
    Spinner sp_cajas,sp_ventilacion_forzada,sp_presion_vacunacion,sp_limpiezaSala_vacunacion,sp_desinfeccion_sala_vacunacion;

    Button bt_modificar_lista;

    Button bt_firmar_1,bt_firmar_2;
    ImageView im_firma_1,im_firma_2,im_foto_jefe;
    Bitmap bm_firma_1=null,bm_firma_2=null,bm_foto_jefe=null;
    String imei="";


    TableLayout tb_mantenimiento_limpieza,tb_control_indice;

    LinearLayout ll_irregularidades;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.it_ok:
                guardar_formulario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    int inter=0;
    private static final int Date_id = 0;

    TableRow tr_control_indice_titulo,tr_control_indice_promedio,tr_control_indice_sumatoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoja_de_verificacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        hora_ingreso=(TextView)findViewById(R.id.et_hora_ingreso);
        hora_salida=(TextView)findViewById(R.id.et_hora_salida);
        fecha=(TextView) findViewById(R.id.fecha);

        et_responsable_invetsa=(EditText)findViewById(R.id.et_Responsable_invetsa);
        et_responsable_incubadora=(EditText)findViewById(R.id.et_Responsable_Incubadora);
        et_hybro=(EditText)findViewById(R.id.et_hybro);
        auto_cobb=(AutoCompleteTextView)findViewById(R.id.auto_cobb);
        auto_ross=(AutoCompleteTextView)findViewById(R.id.auto_ross);
        tv_nro_nacimiento=(TextView)findViewById(R.id.tv_nro_nacimiento);


        et_maquinas=(EditText)findViewById(R.id.et_Maquinas);
        cb_1=(CheckBox)findViewById(R.id.cb_1);
        cb_2=(CheckBox)findViewById(R.id.cb_2);
        cb_3=(CheckBox)findViewById(R.id.cb_3);
        cb_4=(CheckBox)findViewById(R.id.cb_4);
        cb_5=(CheckBox)findViewById(R.id.cb_5);
        cb_6=(CheckBox)findViewById(R.id.cb_6);
        cb_7=(CheckBox)findViewById(R.id.cb_7);
        cb_8=(CheckBox)findViewById(R.id.cb_8);
        cb_9=(CheckBox)findViewById(R.id.cb_9);
        cb_10=(CheckBox)findViewById(R.id.cb_10);
        cb_11=(CheckBox)findViewById(R.id.cb_11);
        cb_12=(CheckBox)findViewById(R.id.cb_12);
        cb_13=(CheckBox)findViewById(R.id.cb_13);
        cb_14=(CheckBox)findViewById(R.id.cb_14);
        cb_15=(CheckBox)findViewById(R.id.cb_15);
        cb_16=(CheckBox)findViewById(R.id.cb_16);
        cb_17=(CheckBox)findViewById(R.id.cb_17);
        cb_18=(CheckBox)findViewById(R.id.cb_18);
        cb_19=(CheckBox)findViewById(R.id.cb_19);
        cb_20=(CheckBox)findViewById(R.id.cb_20);

        tv_productividad=(TextView)findViewById(R.id.tv_Productividad);
        tv_MantenimientoyLimpiesa=(TextView)findViewById(R.id.tv_MantenimientoyLimpiesa);
        tv_ManipulacionDisolucion=(TextView)findViewById(R.id.tv_ManipulacionDisolucion);
        tv_IndicedeEficiencia=(TextView)findViewById(R.id.tv_IndicedeEficiencia);
        tv_Puntaje_total=(TextView)findViewById(R.id.tv_Puntaje_total);


        tv_Sumatoria_vacuna_congelada=(TextView)findViewById(R.id.tv_Sumatoria_vacuna_congelada);
        tv_Promedio_Vacunadoras_ACCUVAC=(TextView)findViewById(R.id.tv_Promedio_Vacunadoras_ACCUVAC);
        tv_puntaje_control_indice=(TextView)findViewById(R.id.tv_puntaje_control_indice);

        tv_sum_1=(TextView)findViewById(R.id.tv_sum_1);
        tv_sum_2=(TextView)findViewById(R.id.tv_sum_2);
        tv_sum_3=(TextView)findViewById(R.id.tv_sum_3);
        tv_sum_4=(TextView)findViewById(R.id.tv_sum_4);
        tv_sum_5=(TextView)findViewById(R.id.tv_sum_5);
        tv_sum_6=(TextView)findViewById(R.id.tv_sum_6);
        tv_sum_7=(TextView)findViewById(R.id.tv_sum_7);
        tv_sum_8=(TextView)findViewById(R.id.tv_sum_8);
        tv_sum_9=(TextView)findViewById(R.id.tv_sum_9);

        tv_pro_1=(TextView)findViewById(R.id.tv_pro_1);
        tv_pro_2=(TextView)findViewById(R.id.tv_pro_2);
        tv_pro_3=(TextView)findViewById(R.id.tv_pro_3);
        tv_pro_4=(TextView)findViewById(R.id.tv_pro_4);
        tv_pro_5=(TextView)findViewById(R.id.tv_pro_5);
        tv_pro_6=(TextView)findViewById(R.id.tv_pro_6);
        tv_pro_7=(TextView)findViewById(R.id.tv_pro_7);
        tv_pro_8=(TextView)findViewById(R.id.tv_pro_8);
        tv_pro_9=(TextView)findViewById(R.id.tv_pro_9);

        ll_irregularidades=(LinearLayout)findViewById(R.id.ll_irregularidades);

        auto_temperatura=(AutoCompleteTextView)findViewById(R.id.autoTemperatura);

        auto_empresa=(AutoCompleteTextView)findViewById(R.id.autoEmpresa);
        sp_unidad=(Spinner) findViewById(R.id.sp_Unidad);
        sp_granja=(Spinner)findViewById(R.id.sp_Granja);
        autoTemperaturaVacunacion=(AutoCompleteTextView)findViewById(R.id.autoTemperaturaVacunacion);
        autoHumedadVacunacion=(AutoCompleteTextView)findViewById(R.id.autoHumedadVacunacion);

        et_antibiotico_1=(EditText)findViewById(R.id.et_antibiotico_1);
        et_antibiotico_2=(EditText)findViewById(R.id.et_antibiotico_2);
        et_antibiotico_3=(EditText)findViewById(R.id.et_antibiotico_3);
        et_antibiotico_4=(EditText)findViewById(R.id.et_antibiotico_4);

        et_dosis_1=(EditText)findViewById(R.id.et_dosis_1);
        et_dosis_2=(EditText)findViewById(R.id.et_dosis_2);
        et_dosis_3=(EditText)findViewById(R.id.et_dosis_3);
        et_dosis_4=(EditText)findViewById(R.id.et_dosis_4);

        et_tiempo_1=(EditText)findViewById(R.id.et_tiempo_1);
        et_tiempo_2=(EditText)findViewById(R.id.et_tiempo_2);
        et_tiempo_3=(EditText)findViewById(R.id.et_tiempo_3);
        et_tiempo_4=(EditText)findViewById(R.id.et_tiempo_4);

        et_vc_1=(EditText)findViewById(R.id.et_vc_1);
        et_vc_2=(EditText)findViewById(R.id.et_vc_2);
        et_vc_3=(EditText)findViewById(R.id.et_vc_3);
        et_vc_4=(EditText)findViewById(R.id.et_vc_4);

        et_vc=(EditText)findViewById(R.id.et_vc);

        et_colorante=(EditText)findViewById(R.id.et_colorantes);

        sp_humedad=(Spinner)findViewById(R.id.sp_Humedad);
        sp_ventilacion=(Spinner)findViewById(R.id.sp_Ventilacion);
        sp_presion=(Spinner)findViewById(R.id.sp_Presion);
        sp_limpieza_vacunacion=(Spinner)findViewById(R.id.sp_LimpiezaVacunacion);
        sp_desinfeccion=(Spinner)findViewById(R.id.sp_Desinfeccion);
        sp_guantes_lentes=(Spinner)findViewById(R.id.sp_GuantesLentes);
        sp_calentador_agua=(Spinner)findViewById(R.id.sp_CalentadordeAgua);
        sp_recipiente_agua=(Spinner)findViewById(R.id.sp_RecipientedeAgua);
        sp_termometro=(Spinner)findViewById(R.id.sp_Termometro);

        auto_promedio_temperatura=(AutoCompleteTextView) findViewById(R.id.auto_PromedioTemperatura);


        sp_soporte_ampollas=(Spinner)findViewById(R.id.sp_SoporteAmpollas);
        sp_rompe_ampollas=(Spinner)findViewById(R.id.sp_RompeAmpollas);
        auto_jeringas=(AutoCompleteTextView) findViewById(R.id.auto_Jeringas);
        sp_agujas=(Spinner)findViewById(R.id.sp_Agujas);
        sp_alcohol=(Spinner)findViewById(R.id.sp_Alcohol);
        sp_algodon=(Spinner)findViewById(R.id.sp_algodon);
        sp_papel=(Spinner)findViewById(R.id.sp_Papel);
        //sp_colorante=(Spinner)findViewById(R.id.sp_colorantes);
        auto_tuberia=(AutoCompleteTextView) findViewById(R.id.auto_Tuberia);
        sp_conectores=(Spinner)findViewById(R.id.sp_Conectores);
        auto_esterilizador=(AutoCompleteTextView) findViewById(R.id.auto_Esterilizador);
        sp_mesa=(Spinner)findViewById(R.id.sp_Mesa);
        sp_cambio_agujas=(Spinner)findViewById(R.id.sp_CambioAgujas);

        sp_cajas=(Spinner)findViewById(R.id.sp_Cajas);
        sp_ventilacion_forzada=(Spinner)findViewById(R.id.sp_VentilacionForzada);
        sp_presion_vacunacion=(Spinner)findViewById(R.id.sp_PresionVacunacion);
        sp_limpiezaSala_vacunacion=(Spinner)findViewById(R.id.sp_LimpiezaSalaVacunacion);
        sp_desinfeccion_sala_vacunacion=(Spinner)findViewById(R.id.sp_DesinfeccionSalaVacunacion);

        tb_mantenimiento_limpieza=(TableLayout)findViewById(R.id.tb_mantenimiento_limpieza);
        tb_control_indice=(TableLayout)findViewById(R.id.tb_control_indice);
        tr_control_indice_titulo=(TableRow) findViewById(R.id.tr_control_indice_titulo);
        tr_control_indice_promedio=(TableRow)findViewById(R.id.tr_control_indice_promedio);
        tr_control_indice_sumatoria=(TableRow)findViewById(R.id.tr_control_indice_sumatoria);


        bt_modificar_lista=(Button)findViewById(R.id.bt_modificar_lista);

        bt_firmar_1=(Button)findViewById(R.id.bt_firmar_1);
        bt_firmar_2=(Button)findViewById(R.id.bt_firmar_2);

        im_firma_2=(ImageView)findViewById(R.id.im_firma_2);
        im_firma_1=(ImageView)findViewById(R.id.im_firma_1);
        im_foto_jefe=(ImageView)findViewById(R.id.im_foto_jefe);




        int id_hoja = 0;

        try {
               Bundle bundle=getIntent().getExtras();
               id_hoja=Integer.parseInt(bundle.getString("id_hoja"));
               cargar_hoja_verificacion(id_hoja);

        } catch (Exception e)
        {
            Log.e("Detalle",""+e);
            finish();
        }





    }//fin de Oncreate


    private String get_id_tabla(String s) {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(s, null);
        String id="";
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            id=fila.getString(0);
        }
        bd.close();
        return id;
    }

    //metodos para los dialogos de hora y fecha
    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(Detalle_Hoja_verificacion.this, date_listener, year,
                        month, day);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day) ;
            fecha.setText(date1);
        }
    };






public void cargar_hoja_verificacion(int id_hoja)
{
    SQLite admin = new SQLite(this,
            "invetsa", null, 1);
    SQLiteDatabase bd = admin.getWritableDatabase();
    String consulta="select h.*,gr.nombre as 'nombre_granja',ga.codigo as 'codigo_galpon',e.nombre as 'nombre_empresa' from hoja_verificacion h,empresa e,granja gr,galpon ga where h.id_granja=gr.id and h.id_empresa=e.id and h.id_galpon=ga.id and h.id='"+id_hoja+"'";
    Cursor fila = bd.rawQuery(consulta, null);


    if (fila.moveToFirst()) {

        //RECUPERACION DE DATOS DE HOJA DE VERIFICACION
        String id=fila.getString(0);
        fecha.setText(fila.getString(1));
        hora_ingreso.setText(fila.getString(2));
        hora_salida.setText(fila.getString(3));
        String codigo=fila.getString(4);
        String revision=fila.getString(5);
        String firma_invetsa=fila.getString(6);
        String firma_empresa=fila.getString(7);
        tv_productividad.setText(fila.getString(8));
        tv_Sumatoria_vacuna_congelada.setText(fila.getString(9));
        tv_Promedio_Vacunadoras_ACCUVAC.setText(fila.getString(10));
        tv_puntaje_control_indice.setText(fila.getString(11));
        String id_galpon=fila.getString(12);
        String id_granja=fila.getString(13);
        String id_empresa=fila.getString(14);
        String imagen_jefe=fila.getString(17);
        et_responsable_invetsa.setText(fila.getString(18));
        et_responsable_incubadora.setText(fila.getString(19));
        et_vc.setText(fila.getString(20));
        tv_Puntaje_total.setText(fila.getString(21));


        auto_empresa.setText(fila.getString(24));

        String[]lista=new String[1];
        lista[0]=fila.getString(22);
        sp_granja.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista));

        String[]galpon=new String[1];
        galpon[0]=fila.getString(23);
        sp_unidad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, galpon));

        bm_firma_1=imagen_en_vista(firma_empresa);
        bm_firma_2=imagen_en_vista(firma_invetsa);
        Bitmap bm_foto_jefe=imagen_en_vista(imagen_jefe);
        if(imagen_en_vista(firma_empresa)!=null)
        {
            im_firma_1.setImageBitmap(bm_firma_1);
        }
        if(imagen_en_vista(firma_invetsa)!=null)
        {
            im_firma_1.setImageBitmap(bm_firma_2);
        }
        if(bm_foto_jefe!=null)
        {
            im_foto_jefe.setImageBitmap(bm_foto_jefe);
        }

        cargar_linea_genetica(id_hoja);
        cargar_detalle_accion_uno(id_hoja);
        cargar_detalle_accion_dos(id_hoja);
        cargar_detalle_accion_tres(id_hoja);
        cargar_manipulacion_dilucion(id_hoja);
        cargar_mantenimiento_limpieza(id_hoja);
        cargar_control_indice(id_hoja);
        cargar_viabilidad_celular(id_hoja);



  }
    bd.close();
}
    private void cargar_linea_genetica(int id_hoja) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from linea_genetica where id_hoja_verificacion='"+id_hoja+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {

                switch (fila.getString(0))
                {
                    case "1":
                        tv_nro_nacimiento.setText(fila.getString(1));
                        auto_cobb.setText(fila.getString(2));
                        auto_ross.setText(fila.getString(3));
                        et_hybro.setText(fila.getString(4));
                        break;
                }
            } while(fila.moveToNext());

        }
        bd.close();
    }
    private void cargar_detalle_accion_uno(int id_hoja) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from detalle_accion where id_accion=1 and id_hoja_verificacion='"+id_hoja+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                String[] encontrado=new String[1];
                encontrado[0]=fila.getString(2);

                switch (fila.getString(0))
                {
                    case "1":  auto_temperatura.setText(fila.getString(2));break;
                    case "2":  sp_ventilacion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "3":  sp_limpieza_vacunacion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "4":  sp_humedad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "5":  sp_presion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "6":  sp_desinfeccion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                }
            } while(fila.moveToNext());

        }
        bd.close();
    }

    private void cargar_detalle_accion_dos(int id_hoja) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from detalle_accion where id_accion=2 and id_hoja_verificacion='"+id_hoja+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                String[] encontrado=new String[1];
                encontrado[0]=fila.getString(2);

                switch (fila.getString(0))
                {
                    case "1":  sp_guantes_lentes.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "2":  sp_recipiente_agua.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "3":  auto_promedio_temperatura.setText(fila.getString(2));break;
                    case "4":  sp_rompe_ampollas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "5":  sp_agujas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "6":  sp_algodon.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    //case "7":  et_colorante.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "8":  sp_conectores.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "9":  sp_calentador_agua.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "10": sp_termometro.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "11": sp_soporte_ampollas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "12": auto_jeringas.setText(fila.getString(2));break;
                    case "13": sp_alcohol.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "14": sp_papel.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "15": auto_tuberia.setText(fila.getString(2));break;
                    case "16": auto_esterilizador.setText(fila.getString(2));break;
                }
            } while(fila.moveToNext());

        }
        bd.close();
    }

    private void cargar_detalle_accion_tres(int id_hoja) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from detalle_accion where id_accion=3 and id_hoja_verificacion='"+id_hoja+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                String[] encontrado=new String[1];
                encontrado[0]=fila.getString(2);

                switch (fila.getString(0))
                {
                    case "1":  sp_mesa.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "2":  sp_cambio_agujas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "3":  autoTemperaturaVacunacion.setText(encontrado[0]);break;
                    case "4":  sp_ventilacion_forzada.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "5":  sp_limpiezaSala_vacunacion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "6":  et_maquinas.setText(encontrado[0]);break;
                    case "7":  sp_cajas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "8":  autoHumedadVacunacion.setText(encontrado[0]);break;
                    case "9":  sp_presion_vacunacion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;
                    case "10":  sp_desinfeccion_sala_vacunacion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encontrado));break;

                }
            } while(fila.moveToNext());

        }
        bd.close();
    }


    private void cargar_manipulacion_dilucion(int id_hoja) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from manipulacion_dilucion where  id_hoja_verificacion='"+id_hoja+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                double puntaje=0;
                try{
                    puntaje=Double.parseDouble(fila.getString(2));
                }catch (Exception e)
                {

                }
                switch (fila.getString(0))
                {
                    case "1":set_checkbox_in_value(puntaje,cb_1);break;
                    case "2":set_checkbox_in_value(puntaje,cb_2);break;
                    case "3":set_checkbox_in_value(puntaje,cb_3);break;
                    case "4":set_checkbox_in_value(puntaje,cb_4);break;
                    case "5":set_checkbox_in_value(puntaje,cb_5);break;
                    case "6":set_checkbox_in_value(puntaje,cb_6);break;
                    case "7":set_checkbox_in_value(puntaje,cb_7);break;
                    case "8":set_checkbox_in_value(puntaje,cb_8);break;
                    case "9":set_checkbox_in_value(puntaje,cb_9);break;
                    case "10":set_checkbox_in_value(puntaje,cb_10);break;
                    case "11":set_checkbox_in_value(puntaje,cb_11);break;
                    case "12":set_checkbox_in_value(puntaje,cb_12);break;
                    case "13":set_checkbox_in_value(puntaje,cb_13);break;
                    case "14":set_checkbox_in_value(puntaje,cb_14);break;
                    case "15":set_checkbox_in_value(puntaje,cb_15);break;
                    case "16":set_checkbox_in_value(puntaje,cb_16);break;
                    case "17":set_checkbox_in_value(puntaje,cb_17);break;
                    case "18":set_checkbox_in_value(puntaje,cb_18);break;
                    case "19":set_checkbox_in_value(puntaje,cb_19);break;
                    case "20":set_checkbox_in_value(puntaje,cb_20);break;


                }
            } while(fila.moveToNext());

        }
        bd.close();
    }
    private void cargar_mantenimiento_limpieza(int id_hoja) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from mantenimiento_limpieza where  id_hoja_verificacion='"+id_hoja+"' GROUB BY id_hoja_verificacion,id_vacunador,", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                double puntaje=0;
                try{
                    puntaje=Double.parseDouble(fila.getString(2));
                }catch (Exception e)
                {

                }
                switch (fila.getString(0))
                {
                    case "1":set_checkbox_in_value(puntaje,cb_1);break;
                    case "2":set_checkbox_in_value(puntaje,cb_2);break;
                    case "3":set_checkbox_in_value(puntaje,cb_3);break;
                    case "4":set_checkbox_in_value(puntaje,cb_4);break;
                    case "5":set_checkbox_in_value(puntaje,cb_5);break;
                    case "6":set_checkbox_in_value(puntaje,cb_6);break;
                    case "7":set_checkbox_in_value(puntaje,cb_7);break;
                    case "8":set_checkbox_in_value(puntaje,cb_8);break;
                    case "9":set_checkbox_in_value(puntaje,cb_9);break;
                    case "10":set_checkbox_in_value(puntaje,cb_10);break;
                    case "11":set_checkbox_in_value(puntaje,cb_11);break;
                    case "12":set_checkbox_in_value(puntaje,cb_12);break;
                    case "13":set_checkbox_in_value(puntaje,cb_13);break;
                    case "14":set_checkbox_in_value(puntaje,cb_14);break;
                    case "15":set_checkbox_in_value(puntaje,cb_15);break;
                    case "16":set_checkbox_in_value(puntaje,cb_16);break;
                    case "17":set_checkbox_in_value(puntaje,cb_17);break;
                    case "18":set_checkbox_in_value(puntaje,cb_18);break;
                    case "19":set_checkbox_in_value(puntaje,cb_19);break;
                    case "20":set_checkbox_in_value(puntaje,cb_20);break;


                }
            } while(fila.moveToNext());

        }
        bd.close();
    }
    public void cargar_mantenimiento_limpieza_por_fila(int id_hoja_verificacion,int id_vacunador,String nombre_vacunador)
    { int cont=0;
        TableRow tableRow =new TableRow(this);

        TextView nombre = new TextView(this);
        nombre.setText(nombre_vacunador);
        nombre.setWidth(250);
        nombre.setTextColor(Color.BLACK);
        nombre.setGravity(Gravity.CENTER);


        EditText numero = new EditText(this);
        numero.setEnabled(false);
        numero.setTextColor(Color.BLACK);;
        numero.setWidth(100);
        numero.setGravity(Gravity.CENTER);

        CheckBox cb1=new CheckBox(this);
        CheckBox cb2=new CheckBox(this);
        CheckBox cb3=new CheckBox(this);
        CheckBox cb4=new CheckBox(this);
        CheckBox cb5=new CheckBox(this);
        CheckBox cb6=new CheckBox(this);
        CheckBox cb7=new CheckBox(this);
        CheckBox cb8=new CheckBox(this);
        CheckBox cb9=new CheckBox(this);
        CheckBox cb10=new CheckBox(this);
        CheckBox cb11=new CheckBox(this);
        CheckBox cb12=new CheckBox(this);
        CheckBox cb13=new CheckBox(this);
        CheckBox cb14=new CheckBox(this);
        CheckBox cb15=new CheckBox(this);

        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Cursor fila = bd.rawQuery("select * from compania ", null);
        Cursor fila = bd.rawQuery("select * from mantenimiento_limpieza where id_hoja_verificacion='"+id_hoja_verificacion+"' and id_vacunador='"+id_vacunador+"' order by id asc", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            numero.setText(fila.getString(1));
            do {

                switch (fila.getString(0))
                {
                    case "1": if(fila.getString(2).equals("1")){cb1.setChecked(true);}break;
                    case "2": if(fila.getString(2).equals("1")){cb2.setChecked(true);}break;
                    case "3": if(fila.getString(2).equals("1")){cb3.setChecked(true);}break;
                    case "4": if(fila.getString(2).equals("1")){cb4.setChecked(true);}break;
                    case "5": if(fila.getString(2).equals("1")){cb5.setChecked(true);}break;
                    case "6": if(fila.getString(2).equals("1")){cb6.setChecked(true);}break;
                    case "7": if(fila.getString(2).equals("1")){cb7.setChecked(true);}break;
                    case "8": if(fila.getString(2).equals("1")){cb8.setChecked(true);}break;
                    case "9": if(fila.getString(2).equals("1")){cb9.setChecked(true);}break;
                    case "10": if(fila.getString(2).equals("1")){cb10.setChecked(true);}break;
                    case "11": if(fila.getString(2).equals("1")){cb11.setChecked(true);}break;
                    case "12": if(fila.getString(2).equals("1")){cb12.setChecked(true);}break;
                    case "13": if(fila.getString(2).equals("1")){cb13.setChecked(true);}break;
                    case "14": if(fila.getString(2).equals("1")){cb14.setChecked(true);}break;
                    case "15": if(fila.getString(2).equals("1")){cb15.setChecked(true);}break;

                }
                cont++;

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();

        tableRow.addView(nombre);
        tableRow.addView(numero);
        tableRow.addView(cb1);
        tableRow.addView(cb2);
        tableRow.addView(cb3);
        tableRow.addView(cb4);
        tableRow.addView(cb5);
        tableRow.addView(cb6);
        tableRow.addView(cb7);
        tableRow.addView(cb8);
        tableRow.addView(cb9);
        tableRow.addView(cb10);
        tableRow.addView(cb11);
        tableRow.addView(cb12);
        tableRow.addView(cb13);
        tableRow.addView(cb14);
        tableRow.addView(cb15);
        if(cont==15)
        {
            tb_mantenimiento_limpieza.addView(tableRow);
        }

    }
    private void cargar_control_indice(int id_hoja) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select ci.*,v.nombre as 'vacunador' from control_indice ci,vacunador v where  ci.id_vacunador=v.id and ci.id_hoja_verificacion='"+id_hoja+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            tb_control_indice.removeAllViews();
            tb_control_indice.addView(tr_control_indice_titulo);
            int i=1;
            do {

/*
                "id integer not null," +
                        "nro_pollos_vacunado integer default 0," +
                        "puntaje integer default 0," +
                        "nro_pollos_controlados integer default 0," +
                        "nro_pollos_no_vacunados integer default 0," +
                        "nro_heridos integer default 0," +
                        "nro_mojados integer default 0," +
                        "nro_mala_posicion integer default 0," +
                        "nro_pollos_vacunados_correctamente integer default 0," +
                        "id_hoja_verificacion integer," +
                        "id_vacunador integer," +
                        "imei text," +
                        "primary key(id,id_vacunador,id_hoja_verificacion)"+
*/
                String nro_pollos_avunados=fila.getString(1);
                String puntaje=fila.getString(2);
                String nro_pollos_controlados=fila.getString(3);
                String nro_pollos_no_vacunados=fila.getString(4);
                String nro_heridos=fila.getString(5);
                String nro_mojados=fila.getString(6);
                String nro_mala_posicion=fila.getString(7);
                String nro_pollos_vacunados_correctamente=fila.getString(8);
                String id_vacunador=fila.getString(10);
                String nombre_vacunador=fila.getString(12);




                    TableRow tableRow = new TableRow(this);

                        TextView nombre=new TextView(this);
                        nombre.setText(nombre_vacunador);
                        nombre.setTextColor(Color.BLACK);
                        nombre.setWidth(250);
                        nombre.setGravity(Gravity.CENTER);

                        EditText et_vacunados=new EditText(this);
                        et_vacunados.setText(nro_pollos_avunados);
                        et_vacunados.setWidth(100);
                        et_vacunados.setInputType(InputType.TYPE_CLASS_NUMBER);

                        EditText et_puntaje=new EditText(this);
                        et_puntaje.setText(puntaje);
                        et_puntaje.setWidth(100);
                        et_puntaje.setInputType(InputType.TYPE_CLASS_NUMBER);

                        EditText et_pollos_controlados=new EditText(this);
                        et_pollos_controlados.setText(nro_pollos_controlados);
                        et_pollos_controlados.setWidth(100);
                        et_pollos_controlados.setInputType(InputType.TYPE_CLASS_NUMBER);

                        EditText et_pollos_no_vacunados=new EditText(this);
                        et_pollos_no_vacunados.setText(nro_pollos_no_vacunados);
                        et_pollos_no_vacunados.setWidth(100);
                        et_pollos_no_vacunados.setInputType(InputType.TYPE_CLASS_NUMBER);

                        EditText et_pollos_heridos=new EditText(this);
                        et_pollos_heridos.setText(nro_heridos);
                        et_pollos_heridos.setWidth(100);
                        et_pollos_heridos.setInputType(InputType.TYPE_CLASS_NUMBER);

                        EditText et_pollos_mojados=new EditText(this);
                        et_pollos_mojados.setText(nro_mojados);
                        et_pollos_mojados.setWidth(100);
                        et_pollos_mojados.setInputType(InputType.TYPE_CLASS_NUMBER);

                        EditText et_mala_posicion=new EditText(this);
                        et_mala_posicion.setText(nro_mala_posicion);
                        et_mala_posicion.setWidth(100);
                        et_mala_posicion.setInputType(InputType.TYPE_CLASS_NUMBER);

                        EditText et_vacunados_correctamente=new EditText(this);
                        et_vacunados_correctamente.setText(nro_pollos_vacunados_correctamente);
                        et_vacunados_correctamente.setWidth(100);
                        et_vacunados_correctamente.setInputType(InputType.TYPE_CLASS_NUMBER);
/*
* modificacion de hoja de verificacion....
* */
                        EditText et_eficiencia=new EditText(this);
                        et_eficiencia.setText("0");
                        et_eficiencia.setWidth(100);
                        et_eficiencia.setInputType(InputType.TYPE_CLASS_NUMBER);



                        tableRow.addView(nombre);
                        tableRow.addView(et_vacunados);
                        tableRow.addView(et_puntaje);
                        tableRow.addView(et_pollos_controlados);
                        tableRow.addView(et_pollos_no_vacunados);
                        tableRow.addView(et_pollos_heridos);
                        tableRow.addView(et_pollos_mojados);
                        tableRow.addView(et_mala_posicion);
                        tableRow.addView(et_vacunados_correctamente);
                        tableRow.addView(et_eficiencia);


                        tb_control_indice.addView(tableRow);



                // fin de While. para recorrer toda la lista de Vacunadores seleccionados...








            } while(fila.moveToNext());
            tb_control_indice.addView(tr_control_indice_promedio);
            tb_control_indice.addView(tr_control_indice_sumatoria);
        }
        bd.close();
    }
    private void cargar_viabilidad_celular(int id_hoja) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from viabilidad_celular where id_hoja_verificacion='"+id_hoja+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {

                switch (fila.getString(0))
                {
                    case "1":
                        et_antibiotico_1.setText(fila.getString(1));
                        et_dosis_1.setText(fila.getString(2));
                        et_tiempo_1.setText(fila.getString(3));
                        et_vc_1.setText(fila.getString(4));
                        break;
                    case "2":
                        et_antibiotico_2.setText(fila.getString(1));
                        et_dosis_2.setText(fila.getString(2));
                        et_tiempo_2.setText(fila.getString(3));
                        et_vc_2.setText(fila.getString(4));
                        break;
                    case "3":
                        et_antibiotico_3.setText(fila.getString(1));
                        et_dosis_3.setText(fila.getString(2));
                        et_tiempo_3.setText(fila.getString(3));
                        et_vc_3.setText(fila.getString(4));
                        break;
                    case "4":
                        et_antibiotico_4.setText(fila.getString(1));
                        et_dosis_4.setText(fila.getString(2));
                        et_tiempo_4.setText(fila.getString(3));
                        et_vc_4.setText(fila.getString(4));
                        break;
                }
            } while(fila.moveToNext());

        }
        bd.close();
    }

    public void consulta_lista(String consulta,AutoCompleteTextView auto_completado)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(consulta, null);
        String []companias=new String[fila.getCount()];
        int i=0;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                String nombre=fila.getString(0);
                companias[i]=nombre;
                i++;
            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, companias);
        auto_completado.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

        }
    }

    private void guardar_formulario() {
        int id_hoja=id_hoja_verificacion()+1;
        int id_empresa = Integer.parseInt(get_id_tabla("select id from empresa where nombre='" + auto_empresa.getText().toString() + "'"));
        int id_granja=Integer.parseInt(get_id_tabla("select id from granja where nombre='" + sp_granja.getSelectedItem().toString() + "' and id_empresa='"+id_empresa+"'"));
        int id_galpon=Integer.parseInt(get_id_tabla("select id from galpon where codigo='" + sp_unidad.getSelectedItem().toString() + "' and id_empresa='"+id_empresa+"'"+" and id_granja='"+id_granja+"'"));

            guardar_mantenimiento_y_limpieza(id_hoja);
            guardar_control_de_indice(id_hoja);

    }

    private void cargar_vacunadores_en_tabla(ArrayList<clsVacunador> al_vacunador) {
        jso_mantenimiento=new JSONObject();
        int count=tb_mantenimiento_limpieza.getChildCount();
        tb_mantenimiento_limpieza.removeViews(1,count-1);
        //Cursor fila = bd.rawQuery("select * from compania ", null);
        inter=1;
        Iterator<clsVacunador> it=al_vacunador.iterator();
        while (it.hasNext()) {
            clsVacunador va = it.next();

            TableRow tableRow = new TableRow(this);
            final int id = va.getId();
            String nombre_v = va.getNombre();

            if (va.isEstado() == true) {


                TextView nombre = new TextView(this);
                nombre.setText(nombre_v);
                nombre.setWidth(250);
                nombre.setGravity(Gravity.CENTER);


                EditText numero = new EditText(this);
                numero.setText("" + inter);
                numero.setWidth(250);
                numero.setGravity(Gravity.CENTER);

                CheckBox cb1 = new CheckBox(this);
                CheckBox cb2 = new CheckBox(this);
                CheckBox cb3 = new CheckBox(this);
                CheckBox cb4 = new CheckBox(this);
                CheckBox cb5 = new CheckBox(this);
                CheckBox cb6 = new CheckBox(this);
                CheckBox cb7 = new CheckBox(this);
                CheckBox cb8 = new CheckBox(this);
                CheckBox cb9 = new CheckBox(this);
                CheckBox cb10 = new CheckBox(this);
                CheckBox cb11 = new CheckBox(this);
                CheckBox cb12 = new CheckBox(this);
                CheckBox cb13 = new CheckBox(this);
                CheckBox cb14 = new CheckBox(this);
                CheckBox cb15 = new CheckBox(this);


                cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 1 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 1 + ":" + inter, "1");
                                Toast.makeText(getApplicationContext(), "id=" + id + " c=" + 1, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 1 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 1 + ":" + inter, "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });

                cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 2 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 2 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 2 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 2 + ":" + inter, "0");

                            } catch (Exception e) {

                            }
                        }
                    }
                });

                cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 3 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 3 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 3 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 3 + ":" + inter, "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });

                cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 4 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 4 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 4 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 4 + ":" + inter, "0");

                            } catch (Exception e) {

                            }
                        }
                    }
                });

                cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 5 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 5 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 5 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 5 + ":" + inter, "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });

                cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 6 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 6 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 6);
                                jso_mantenimiento.put(id + " " + 6, "0");

                            } catch (Exception e) {

                            }
                        }
                    }
                });

                cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 7 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 7 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 7 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 7 + ":" + inter, "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });

                cb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 8 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 8 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 8 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 8 + ":" + inter, "0");

                            } catch (Exception e) {

                            }
                        }
                    }
                });

                cb9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 9 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 9 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 9 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 9 + ":" + inter, "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });

                cb10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 10 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 10 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 10 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 10 + ":" + inter, "0");

                            } catch (Exception e) {

                            }
                        }
                    }
                });


                cb11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 11 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 11 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 11 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 11 + ":" + inter, "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });

                cb12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 12 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 12 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 12 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 12 + ":" + inter, "0");

                            } catch (Exception e) {

                            }
                        }
                    }
                });

                cb13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 13 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 13 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 13 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 13 + ":" + inter, "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });

                cb14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 14 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 14 + ":" + inter, "1");
                                Toast.makeText(getApplicationContext(), "id=" + id + " c=" + 14, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 14 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 14 + ":" + inter, "0");

                            } catch (Exception e) {

                            }
                        }
                    }
                });

                cb15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 15 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 15 + ":" + inter, "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 15 + ":" + inter);
                                jso_mantenimiento.put(id + " " + 15 + ":" + inter, "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });


                try {

                    jso_mantenimiento.put(id + " " + 1 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 2 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 3 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 4 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 5 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 6 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 7 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 8 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 9 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 10 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 11 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 12 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 13 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 14 + ":" + inter, "0");
                    jso_mantenimiento.put(id + " " + 15 + ":" + inter, "0");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                tableRow.addView(nombre);
                tableRow.addView(numero);
                tableRow.addView(cb1);
                tableRow.addView(cb2);
                tableRow.addView(cb3);
                tableRow.addView(cb4);
                tableRow.addView(cb5);
                tableRow.addView(cb6);
                tableRow.addView(cb7);
                tableRow.addView(cb8);
                tableRow.addView(cb9);
                tableRow.addView(cb10);
                tableRow.addView(cb11);
                tableRow.addView(cb12);
                tableRow.addView(cb13);
                tableRow.addView(cb14);
                tableRow.addView(cb15);


                tb_mantenimiento_limpieza.addView(tableRow);


                inter++;

            }// fin de la condicion de ESTADO =true
        }
        //fin del While

    }

    private void cargar_tabla_control_indice(ArrayList<clsVacunador> al_vacunador) {
        jso_control_indice=new JSONObject();

        tb_control_indice.removeAllViews();
        tb_control_indice.addView(tr_control_indice_titulo);
        int i=1;

        Iterator<clsVacunador> it=al_vacunador.iterator();
        while (it.hasNext()) {
            clsVacunador va = it.next();

            TableRow tableRow = new TableRow(this);
            final int id = va.getId();
            String nombre_v = va.getNombre();

            if (va.isEstado() == true) {

                TextView nombre=new TextView(this);
                nombre.setText(nombre_v);
                nombre.setWidth(250);
                nombre.setGravity(Gravity.CENTER);

                EditText et_vacunados=new EditText(this);
                et_vacunados.setText("0");
                et_vacunados.setWidth(100);
                et_vacunados.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText et_puntaje=new EditText(this);
                et_puntaje.setText("0");
                et_puntaje.setWidth(100);
                et_puntaje.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText et_pollos_controlados=new EditText(this);
                et_pollos_controlados.setText("0");
                et_pollos_controlados.setWidth(100);
                et_pollos_controlados.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText et_pollos_no_vacunados=new EditText(this);
                et_pollos_no_vacunados.setText("0");
                et_pollos_no_vacunados.setWidth(100);
                et_pollos_no_vacunados.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText et_pollos_heridos=new EditText(this);
                et_pollos_heridos.setText("0");
                et_pollos_heridos.setWidth(100);
                et_pollos_heridos.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText et_pollos_mojados=new EditText(this);
                et_pollos_mojados.setText("0");
                et_pollos_mojados.setWidth(100);
                et_pollos_mojados.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText et_mala_posicion=new EditText(this);
                et_mala_posicion.setText("0");
                et_mala_posicion.setWidth(100);
                et_mala_posicion.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText et_vacunados_correctamente=new EditText(this);
                et_vacunados_correctamente.setText("0");
                et_vacunados_correctamente.setWidth(100);
                et_vacunados_correctamente.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText et_eficiencia=new EditText(this);
                et_eficiencia.setText("0");
                et_eficiencia.setWidth(100);
                et_eficiencia.setInputType(InputType.TYPE_CLASS_NUMBER);






                try {
                    jso_control_indice.put(id+" "+1,"0");
                    jso_control_indice.put(id+" "+2,"0");
                    jso_control_indice.put(id+" "+3,"0");
                    jso_control_indice.put(id+" "+4,"0");
                    jso_control_indice.put(id+" "+5,"0");
                    jso_control_indice.put(id+" "+6,"0");
                    jso_control_indice.put(id+" "+7,"0");
                    jso_control_indice.put(id+" "+8,"0");
                    jso_control_indice.put(id+" "+9,"0");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                tableRow.addView(nombre);
                tableRow.addView(et_vacunados);
                tableRow.addView(et_puntaje);
                tableRow.addView(et_pollos_controlados);
                tableRow.addView(et_pollos_no_vacunados);
                tableRow.addView(et_pollos_heridos);
                tableRow.addView(et_pollos_mojados);
                tableRow.addView(et_mala_posicion);
                tableRow.addView(et_vacunados_correctamente);
                tableRow.addView(et_eficiencia);


                tb_control_indice.addView(tableRow);



                et_vacunados.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            jso_control_indice.remove(id + " " + 1);
                            if(s.length()>0 && s.equals("")==false) {
                                jso_control_indice.put(id + " " + 1, s);

                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 1, "0");
                            }

                            sumatoria_control_indice(tv_sum_1,tv_pro_1,1);
                        }catch (Exception e)
                        {

                        }
                    }
                });

                et_puntaje.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            jso_control_indice.remove(id + " " + 2);
                            if(s.length()>0 && s.equals("")==false) {
                                jso_control_indice.put(id + " " + 2, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 2, "0");
                            }
                            sumatoria_control_indice(tv_sum_2,tv_pro_2,2);

                        }catch (Exception e)
                        {
                            Log.e("Suma control indice",""+e);
                        }
                    }
                });

                et_pollos_controlados.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            jso_control_indice.remove(id + " " + 3);
                            if(s.length()>0 && s.equals("")==false) {
                                jso_control_indice.put(id + " " + 3, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 3, "0");
                            }
                            sumatoria_control_indice(tv_sum_3,tv_pro_3,3);

                        }catch (Exception e)
                        {

                        }
                    }
                });

                et_pollos_no_vacunados.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            jso_control_indice.remove(id + " " + 4);
                            if(s.length()>0 && s.equals("")==false) {
                                jso_control_indice.put(id + " " + 4, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 4, "0");
                            }
                            sumatoria_control_indice(tv_sum_4,tv_pro_4,4);

                        }catch (Exception e)
                        {

                        }
                    }
                });

                et_pollos_heridos.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            jso_control_indice.remove(id + " " + 5);
                            if(s.length()>0 && s.equals("")==false) {
                                jso_control_indice.put(id + " " + 5, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 5, "0");
                            }
                            sumatoria_control_indice(tv_sum_5,tv_pro_5,5);

                        }catch (Exception e)
                        {

                        }
                    }
                });

                et_pollos_mojados.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            jso_control_indice.remove(id + " " + 6);
                            if(s.length()>0 && s.equals("")==false) {
                                jso_control_indice.put(id + " " + 6, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 6, "0");
                            }
                            sumatoria_control_indice(tv_sum_6,tv_pro_6,6);

                        }catch (Exception e)
                        {

                        }
                    }
                });

                et_mala_posicion.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            jso_control_indice.remove(id + " " + 7);
                            if(s.length()>0 && s.equals("")==false) {
                                jso_control_indice.put(id + " " + 7, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 7, "0");
                            }
                            sumatoria_control_indice(tv_sum_7,tv_pro_7,7);

                        }catch (Exception e)
                        {

                        }
                    }
                });

                et_vacunados_correctamente.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            jso_control_indice.remove(id + " " + 8);
                            if(s.length()>0 && s.equals("")==false) {
                                jso_control_indice.put(id + " " + 8, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 8, "0");
                            }
                            sumatoria_control_indice(tv_sum_8,tv_pro_8,8);

                        }catch (Exception e)
                        {

                        }
                    }
                });
            } //fin de la condicion de obtencion de ESTADO=true

        }// fin de While. para recorrer toda la lista de Vacunadores seleccionados...

        tb_control_indice.addView(tr_control_indice_promedio);
        tb_control_indice.addView(tr_control_indice_sumatoria);
    }

    private void guardar_control_de_indice(int id_hoja) {

        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from vacunador", null);
        int item=1;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                final String id=fila.getString(0);
                try {
                    int nro_pollos_vacunado=Integer.parseInt(jso_control_indice.get(id+" 1").toString());
                    int puntaje=Integer.parseInt(jso_control_indice.get(id+" 2").toString());
                    int nro_pollos_controlados=Integer.parseInt(jso_control_indice.get(id+" 3").toString());
                    int nro_pollos_no_vacunados=Integer.parseInt(jso_control_indice.get(id+" 4").toString());
                    int nro_heridos=Integer.parseInt(jso_control_indice.get(id+" 5").toString());
                    int nro_mojados=Integer.parseInt(jso_control_indice.get(id+" 6").toString());
                    int nro_mala_posicion=Integer.parseInt(jso_control_indice.get(id+" 7").toString());
                    int nro_pollos_vacunados_correctamente=Integer.parseInt(jso_control_indice.get(id+" 8").toString());
                    int id_hoja_verificacion=id_hoja;
                    int id_vacunador=Integer.parseInt(id);

                    guardar_control_indice(item,nro_pollos_vacunado,puntaje,nro_pollos_controlados,nro_pollos_no_vacunados,nro_heridos,nro_mojados,nro_mala_posicion,nro_pollos_vacunados_correctamente,id_hoja_verificacion,id_vacunador);

                } catch (JSONException e) {
                    Log.e("SqLite vacunador ",""+e);
                }
                item++;
            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
    }

    private void guardar_mantenimiento_y_limpieza(int id_hoja) {

        for(int i=0;i<jso_mantenimiento.length();i++)
        {

            Iterator<String> iter=jso_mantenimiento.keys();
            while (iter.hasNext())
            {
                String key=iter.next();
                try{
                    Object object=jso_mantenimiento.get(key);
                    String v=object.toString();
                    //  Dato dato=new Dato();
                    // dato.set_mantenimiento(key,Integer.parseInt(v));
                    //guardar_mantenimiento_limpieza(dato.getNumero(),dato.getItem(),dato.getValor(),id_hoja,dato.getId());
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private int id_hoja_verificacion()
    {int id=0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from hoja_verificacion order by id desc ", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


            String codigo= fila.getString(0);
            try {
                if (codigo.equals("") == false && codigo.equals("0") == false && codigo.equals("null") == false) {
                    id = Integer.parseInt(codigo);
                }
            }catch (Exception e)
            {
                Log.e("id hoja de verificacion",""+e);
            }

        }
        bd.close();
        return id;
    }
    private int id_empresa_por_nombre(String nombre)
    {int id=-1;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from empresa where nombre='"+nombre+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


            String codigo= fila.getString(0);
            if(codigo.equals("")==false && codigo.equals("0")==false )
            {
                id=Integer.parseInt(codigo);
            }

        }
        bd.close();
        return id;
    }

    private int id_granja_por_nombre(String nombre,int id_empresa)
    {int id=-1;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from granja where nombre='"+nombre+"' and id_empresa='"+id_empresa+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


            String codigo= fila.getString(0);
            if(codigo.equals("")==false && codigo.equals("0")==false )
            {
                id=Integer.parseInt(codigo);
            }

        }
        bd.close();
        return id;
    }

    private int id_galpon_por_codigo(String codigo,int id_empresa,int id_granja)
    {int id=-1;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from galpon where codigo='"+codigo+"' and id_empresa='"+id_empresa+"' and id_granja='"+id_granja+"'", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


            String valor= fila.getString(0);
            if(valor.equals("")==false && valor.equals("0")==false )
            {
                id=Integer.parseInt(valor);
            }

        }
        bd.close();
        return id;
    }


    public void guardar_hoja_verificacion(int id,String fecha,String hora_ingreso,String hora_salida,String codigo,String revision,String firma_invetsa,String firma_empresa,double productividad,double sumatoria_manipulacion_vacuna,double promedio_mantenimiento ,double puntaje_control_indice,int id_galpon_s,int id_granja_s,int id_empresa_s,int id_tecnico)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("fecha", fecha);
            registro.put("hora_ingreso", hora_ingreso);
            registro.put("hora_salida", hora_salida);
            registro.put("codigo", codigo);
            registro.put("revision", revision);
            registro.put("firma_invetsa", firma_invetsa);
            registro.put("firma_empresa", firma_empresa);
            registro.put("productividad", String.valueOf(productividad));
            registro.put("sumatoria_manipulacion_vacuna", String.valueOf(sumatoria_manipulacion_vacuna));
            registro.put("promedio_mantenimiento", String.valueOf(promedio_mantenimiento));
            registro.put("puntaje_control_indice", String.valueOf(puntaje_control_indice));
            registro.put("id_galpon", String.valueOf(id_galpon_s));
            registro.put("id_granja", String.valueOf(id_granja_s));
            registro.put("id_empresa", String.valueOf(id_empresa_s));
            registro.put("imei", imei);
            registro.put("id_tecnico", String.valueOf(id_tecnico));
            bd.insert("hoja_verificacion", null, registro);

        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }

        bd.close();
    }

    public void guardar_accion(int id,String nombre,int id_hoja_verificacion)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("nombre", nombre);
            registro.put("id_hoja_verificacion", String.valueOf(id_hoja_verificacion));
            registro.put("imei", imei);
            bd.insert("accion", null, registro);

        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }
        bd.close();
    }
    public void guardar_detalle_accion(int id,String esperado,String encontrado,int id_accion,int id_hoja_verificacion)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("esperado", esperado);
            registro.put("encontrado", encontrado);
            registro.put("id_accion", String.valueOf(id_accion));
            registro.put("id_hoja_verificacion", String.valueOf(id_hoja_verificacion));
            registro.put("imei", imei);
            bd.insert("detalle_accion", null, registro);

        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }
        bd.close();
    }

    public void guardar_manipulacion_dilucion(int id,String descripcion,double puntaje,int id_hoja_verificacion)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("descripcion",descripcion);
            registro.put("puntaje", String.valueOf(puntaje));
            registro.put("id_hoja_verificacion", String.valueOf(id_hoja_verificacion));
            registro.put("imei", imei);
            bd.insert("manipulacion_dilucion", null, registro);

        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }
        bd.close();
    }

    public void  sumatoria_control_indice(TextView tv_sumatoria,TextView tv_promedio ,int columna)
    {
        int sumatoria=0;
        float promedio=0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Cursor fila = bd.rawQuery("select * from compania ", null);
        Cursor fila = bd.rawQuery("select * from vacunador", null);
        int count=0;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                final String id=fila.getString(0);
                try {
                    int numero=Integer.parseInt(jso_control_indice.get(id+" "+columna).toString());
                    sumatoria+=numero;
                    count+=1;
                } catch (JSONException e) {
                    Log.e("SqLite control indice",""+e);
                }
            } while(fila.moveToNext());

        } else
        {

        }
        promedio=sumatoria/count;
        tv_sumatoria.setText(String.valueOf(sumatoria));
        tv_promedio.setText(String.valueOf(promedio));
        bd.close();
    }

    public void guardar_mantenimiento_limpieza(int id,int nro_twin_shot_zootec,int irregularidades,int id_hoja_verificacion,int id_vacunador)
    {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("nro_twin_shot_zootec",String.valueOf(nro_twin_shot_zootec));
            registro.put("irregularidades", String.valueOf(irregularidades));
            registro.put("id_hoja_verificacion", String.valueOf(id_hoja_verificacion));
            registro.put("id_vacunador", String.valueOf(id_vacunador));
            bd.insert("mantenimiento_limpieza", null, registro);
        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }
        bd.close();
    }

    public void guardar_control_indice(int id,int nro_pollos_vacunado,int puntaje,int nro_pollos_controlados,int nro_pollos_no_vacunados,int nro_heridos, int nro_mojados, int nro_mala_posicion, int nro_pollos_vacunados_correctamente, int id_hoja_verificacion, int id_vacunador)
    {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("nro_pollos_vacunado",String.valueOf(nro_pollos_vacunado));
            registro.put("puntaje", String.valueOf(puntaje));
            registro.put("nro_pollos_controlados", String.valueOf(nro_pollos_controlados));
            registro.put("nro_pollos_no_vacunados", String.valueOf(nro_pollos_no_vacunados));
            registro.put("nro_heridos", String.valueOf(nro_heridos ));
            registro.put("nro_mojados", String.valueOf( nro_mojados));
            registro.put("nro_mala_posicion", String.valueOf( nro_mala_posicion));
            registro.put("nro_pollos_vacunados_correctamente", String.valueOf(nro_pollos_vacunados_correctamente ));
            registro.put("id_hoja_verificacion", String.valueOf( id_hoja_verificacion));
            registro.put("id_vacunador", String.valueOf(id_vacunador ));
            registro.put("imei", imei);
            bd.insert("control_indice", null, registro);
        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }
        bd.close();
    }


    private void guardar_en_memoria(Bitmap bitmapImage,String nombre,String DIRECTORIO)
    {
        File file=null;
        FileOutputStream fos = null;
        try {
            // String APP_DIRECTORY = "Invetsa/";//nombre de directorio
            // String MEDIA_DIRECTORY = APP_DIRECTORY + "Firma";//nombre de la carpeta
            file = new File(Environment.getExternalStorageDirectory(), DIRECTORIO);
            File mypath=new File(file,nombre);//nombre del archivo imagen

            boolean isDirectoryCreated = file.exists();//pregunto si esxiste el directorio creado
            if(!isDirectoryCreated)
                isDirectoryCreated = file.mkdirs();

            if(isDirectoryCreated) {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap imagen_en_vista(String ubicacion)
    {
        // String mPath = Environment.getExternalStorageDirectory() + File.separator + "Taxi Elitex/Imagen"+ File.separator + "perfil.jpg";
        String mPath = Environment.getExternalStorageDirectory() + File.separator + ubicacion;
        Bitmap bitmap = BitmapFactory.decodeFile(mPath);
        return bitmap;
    }
    public void set_checkbox_in_value(double numero,CheckBox cb)
    {

        if(numero==0)
        {cb.setChecked(false);
        }
        else if(numero==0.1)
        {
            cb.setChecked(true);
        }
        else{
            cb.setChecked(false);
        }

    }

}