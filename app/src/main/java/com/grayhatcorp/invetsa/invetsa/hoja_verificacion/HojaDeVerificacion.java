package com.grayhatcorp.invetsa.invetsa.hoja_verificacion;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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

import static com.grayhatcorp.invetsa.invetsa.R.layout.activity_hoja_de_verificacion;

public class HojaDeVerificacion extends AppCompatActivity implements View.OnClickListener{
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
    AutoCompleteTextView auto_temperatura,auto_promedio_temperatura,auto_jeringas,auto_esterilizador;

    Spinner sp_granja,sp_unidad;

    Spinner sp_humedad,sp_ventilacion,sp_presion,sp_limpieza_vacunacion,sp_desinfeccion,sp_guantes_lentes,sp_calentador_agua,sp_recipiente_agua,sp_termometro;
    Spinner sp_soporte_ampollas,sp_rompe_ampollas,sp_agujas,sp_alcohol,sp_algodon,sp_papel,sp_conectores,sp_mesa,sp_cambio_agujas;
    Spinner sp_cajas,sp_ventilacion_forzada,sp_presion_vacunacion,sp_limpiezaSala_vacunacion,sp_desinfeccion_sala_vacunacion;
    Spinner auto_tuberia;
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
    private static final int Time_id = 1;
    private static final int Time_id_salida = 2;

    TableRow tr_control_indice_titulo,tr_control_indice_promedio,tr_control_indice_sumatoria;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_hoja_de_verificacion);
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
        //auto_tuberia=(AutoCompleteTextView) findViewById(R.id.auto_Tuberia);
        auto_tuberia=(Spinner)findViewById(R.id.auto_Tuberia);
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




/*
        cargar_tabla_vacunadoores();
        cargar_tabla_control_indice();
*/
        consulta_lista("select nombre from empresa",auto_empresa);
        cargar_autocompletado();

        TableRow.LayoutParams params=(TableRow.LayoutParams)ll_irregularidades.getLayoutParams();
        params.span=1;
        ll_irregularidades.setLayoutParams(params);

        im_foto_jefe.setOnClickListener(this);
        bt_firmar_1.setOnClickListener(this);
        bt_firmar_2.setOnClickListener(this);
        bt_modificar_lista.setOnClickListener(this);

        cb_1.setOnClickListener(this);
        cb_2.setOnClickListener(this);
        cb_3.setOnClickListener(this);
        cb_4.setOnClickListener(this);
        cb_5.setOnClickListener(this);
        cb_6.setOnClickListener(this);
        cb_7.setOnClickListener(this);
        cb_8.setOnClickListener(this);
        cb_9.setOnClickListener(this);
        cb_10.setOnClickListener(this);
        cb_11.setOnClickListener(this);
        cb_12.setOnClickListener(this);
        cb_13.setOnClickListener(this);
        cb_14.setOnClickListener(this);
        cb_15.setOnClickListener(this);
        cb_16.setOnClickListener(this);
        cb_17.setOnClickListener(this);
        cb_18.setOnClickListener(this);
        cb_19.setOnClickListener(this);
        cb_20.setOnClickListener(this);


        Calendar c=Calendar.getInstance();
        fecha.setText(""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));

        auto_empresa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String id_empresa = get_id_tabla("select id from empresa where nombre='" + auto_empresa.getText().toString() + "'");
                if(id_empresa.length()>0) {
                    cargar_compania_en_la_lista("select nombre from granja where id_empresa=" + id_empresa, sp_granja);
                }
            }
        });
        sp_granja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String id_empresa = get_id_tabla("select id from empresa where nombre='" + auto_empresa.getText().toString() + "'");
                String id_granja=get_id_tabla("select id from granja where nombre='" + sp_granja.getSelectedItem().toString() + "' and id_empresa='"+id_empresa+"'");
                if(id_empresa.length()>0 && id_granja.length()>0) {
                    cargar_compania_en_la_lista("select codigo from galpon where id_granja=" + id_granja, sp_unidad);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //llenar el AutoCompletar

        ArrayAdapter<String> vacunacion = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,TemperaturaVacunacion);
        AutoCompleteTextView actv2= (AutoCompleteTextView)findViewById(R.id.autoTemperaturaVacunacion);
        actv2.setThreshold(1);
        actv2.setAdapter(vacunacion);

        ArrayAdapter<String> Humedad = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,HumedadVacunacion);
        AutoCompleteTextView actv3= (AutoCompleteTextView)findViewById(R.id.autoHumedadVacunacion);
        actv3.setThreshold(1);
        actv3.setAdapter(Humedad);

        //---------------------------CODIGO PARA LOS DIALOGOS DE HORA Y FECHA---------------------------------
        fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show Date dialog
                showDialog(Date_id);
            }
        });


        hora_ingreso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id);
            }
        });

        hora_salida.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Show time dialog
                showDialog(Time_id_salida);
            }
        });




        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei=telephonyManager.getDeviceId();

        if(imei.equals("")==true)
        {
            mensaje_ok_cerrar("Necesita dar permisos para Obtener el número de Imei.");
        }
    }//fin de Oncreate

    private void cargar_tabla_vacunadoores() {
        jso_mantenimiento=new JSONObject();
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Cursor fila = bd.rawQuery("select * from compania ", null);
        Cursor fila = bd.rawQuery("select * from vacunador order by nombre asc", null);

        inter=1;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                TableRow tableRow =new TableRow(this);
                final String id=fila.getString(0);
                String nombre_v=fila.getString(1);

                TextView nombre=new TextView(this);
                nombre.setText(nombre_v);
                nombre.setWidth(250);
                nombre.setGravity(Gravity.CENTER);


                final TextView numero=new TextView(this);
                numero.setText(""+inter);
                numero.setWidth(250);
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


                cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 1+":"+numero.getText());
                            jso_mantenimiento.put(id + " " + 1+":"+numero.getText(), "1");
                            Toast.makeText(getApplicationContext(),"id="+id+" c="+1,Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 1+":"+numero.getText());
                                jso_mantenimiento.put(id + " " + 1+":"+numero.getText(), "0");

                            }catch (Exception e)
                            {

                            }

                        }
                    }
                });

                cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 2+":"+numero.getText());
                            jso_mantenimiento.put(id + " " + 2+":"+numero.getText(), "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 2+":"+numero.getText());
                                jso_mantenimiento.put(id + " " + 2+":"+numero.getText(), "0");

                            }catch (Exception e)
                            {

                            }
                        }
                    }
                });

                cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 3+":"+numero.getText());
                            jso_mantenimiento.put(id + " " + 3+":"+numero.getText(), "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 3+":"+numero.getText());
                                jso_mantenimiento.put(id + " " + 3+":"+numero.getText(), "0");

                            }catch (Exception e)
                            {

                            }

                        }
                    }
                });

                cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 4+":"+numero.getText());
                            jso_mantenimiento.put(id + " " + 4+":"+numero.getText(), "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 4+":"+numero.getText());
                                jso_mantenimiento.put(id + " " + 4+":"+numero.getText(), "0");

                            }catch (Exception e)
                            {

                            }
                        }
                    }
                });

                cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 5+":"+numero.getText());
                            jso_mantenimiento.put(id + " " + 5+":"+numero.getText(), "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 5+":"+numero.getText());
                                jso_mantenimiento.put(id + " " + 5+":"+numero.getText(), "0");

                            }catch (Exception e)
                            {

                            }

                        }
                    }
                });

                cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 6+":"+numero.getText());
                            jso_mantenimiento.put(id + " " + 6+":"+numero.getText(), "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 6+":"+numero.getText());
                                jso_mantenimiento.put(id + " " + 6+":"+numero.getText(), "0");

                            }catch (Exception e)
                            {

                            }
                        }
                    }
                });

                cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            int item=inter;
                            jso_mantenimiento.remove(id + " " + 7+":"+item);
                            jso_mantenimiento.put(id + " " + 7+":"+item, "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                int item=inter;
                                jso_mantenimiento.remove(id + " " + 7+":"+item);
                                jso_mantenimiento.put(id + " " + 7+":"+item, "0");

                            }catch (Exception e)
                            {

                            }

                        }
                    }
                });

                cb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 8+":"+inter);
                            jso_mantenimiento.put(id + " " + 8+":"+inter, "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 8+":"+inter);
                                jso_mantenimiento.put(id + " " + 8+":"+inter, "0");

                            }catch (Exception e)
                            {

                            }
                        }
                    }
                });

                cb9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 9+":"+inter);
                            jso_mantenimiento.put(id + " " + 9+":"+inter, "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 9+":"+inter);
                                jso_mantenimiento.put(id + " " + 9+":"+inter, "0");

                            }catch (Exception e)
                            {

                            }

                        }
                    }
                });

                cb10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 10+":"+inter);
                            jso_mantenimiento.put(id + " " + 10+":"+inter, "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 10+":"+inter);
                                jso_mantenimiento.put(id + " " + 10+":"+inter, "0");

                            }catch (Exception e)
                            {

                            }
                        }
                    }
                });


                cb11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 11+":"+inter);
                            jso_mantenimiento.put(id + " " + 11+":"+inter, "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 11+":"+inter);
                                jso_mantenimiento.put(id + " " + 11+":"+inter, "0");

                            }catch (Exception e)
                            {

                            }

                        }
                    }
                });

                cb12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 12+":"+inter);
                            jso_mantenimiento.put(id + " " + 12+":"+inter, "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 12+":"+inter);
                                jso_mantenimiento.put(id + " " + 12+":"+inter, "0");

                            }catch (Exception e)
                            {

                            }
                        }
                    }
                });

                cb13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 13+":"+inter);
                            jso_mantenimiento.put(id + " " + 13+":"+inter, "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 13+":"+inter);
                                jso_mantenimiento.put(id + " " + 13+":"+inter, "0");

                            }catch (Exception e)
                            {

                            }

                        }
                    }
                });

                cb14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 14+":"+inter);
                            jso_mantenimiento.put(id + " " + 14+":"+inter, "1");
                            Toast.makeText(getApplicationContext(),"id="+id+" c="+14,Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 14+":"+inter);
                                jso_mantenimiento.put(id + " " + 14+":"+inter, "0");

                            }catch (Exception e)
                            {

                            }
                        }
                    }
                });

                cb15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        { try {
                            jso_mantenimiento.remove(id + " " + 15+":"+inter);
                            jso_mantenimiento.put(id + " " + 15+":"+inter, "1");

                        }catch (Exception e)
                        {

                        }
                        }else
                        {
                            try {
                                jso_mantenimiento.remove(id + " " + 15+":"+inter);
                                jso_mantenimiento.put(id + " " + 15+":"+inter, "0");

                            }catch (Exception e)
                            {

                            }

                        }
                    }
                });




                try {

                    jso_mantenimiento.put(id+" "+1+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+2+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+3+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+4+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+5+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+6+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+7+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+8+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+9+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+10+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+11+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+12+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+13+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+14+":"+inter,"0");
                    jso_mantenimiento.put(id+" "+15+":"+inter,"0");


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

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();

    }

    private void cargar_vacunador()
    {
        al_vacunadores= new ArrayList<clsVacunador>();
        jso_control_indice=new JSONObject();
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Cursor fila = bd.rawQuery("select * from compania ", null);
        Cursor fila = bd.rawQuery("select * from vacunador order by nombre asc", null);
        tb_control_indice.removeAllViews();
        tb_control_indice.addView(tr_control_indice_titulo);
        int i=1;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                TableRow tableRow =new TableRow(this);
                int id=Integer.parseInt(fila.getString(0));
                String nombre_v=fila.getString(1);

                clsVacunador vacunador =new clsVacunador(id,nombre_v,false);
                al_vacunadores.add(vacunador);

            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
    }
    private void cargar_tabla_control_indice() {
        jso_control_indice=new JSONObject();
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Cursor fila = bd.rawQuery("select * from compania ", null);
        Cursor fila = bd.rawQuery("select * from vacunador", null);
        tb_control_indice.removeAllViews();
        tb_control_indice.addView(tr_control_indice_titulo);
        int i=1;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                TableRow tableRow =new TableRow(this);
                final String id=fila.getString(0);
                String nombre_v=fila.getString(1);

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
                            if(s.length()>0 && !s.equals("")) {
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

                et_eficiencia.addTextChangedListener(new TextWatcher() {

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
                            jso_control_indice.remove(id + " " + 9);
                            if(s.length()>0 && !s.equals("")) {
                                jso_control_indice.put(id + " " + 9, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 9, "0");
                            }
                            sumatoria_control_indice(tv_sum_9,tv_pro_9,9);

                        }catch (Exception e)
                        {

                        }
                    }
                });
            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();

        tb_control_indice.addView(tr_control_indice_promedio);
        tb_control_indice.addView(tr_control_indice_sumatoria);
    }


    private String get_id_tabla(String s) {
        SQLite admin = new SQLite(this,"invetsa", null, 1);

        String id="-1";
        try{
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery(s, null);

            if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
                id=fila.getString(0);
            }
            bd.close();
        }catch (Exception e)
        {
            id="-1";
        }


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
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(HojaDeVerificacion.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(HojaDeVerificacion.this, time_listener, hour,
                        minute, false);
            case Time_id_salida:

                // Open the timepicker dialog
                return new TimePickerDialog(HojaDeVerificacion.this, time_listener_salida, hour,
                        minute, false);


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
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener()
    {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            if ((Integer.parseInt(String.valueOf(minute))>=0) && (Integer.parseInt(String.valueOf(minute))<=9)) {
                String time1 = String.valueOf(hour) + ":"+"0"+ String.valueOf(minute);
                hora_ingreso.setText(time1);
            }else{
                String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
                hora_ingreso.setText(time1);
            }
        }
    };
    TimePickerDialog.OnTimeSetListener time_listener_salida = new TimePickerDialog.OnTimeSetListener()
    {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            if ((Integer.parseInt(String.valueOf(minute))>=0) && (Integer.parseInt(String.valueOf(minute))<=9)) {
                String time1 = String.valueOf(hour) + ":"+"0"+ String.valueOf(minute);
                hora_ingreso.setText(time1);
            }else {
                String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
                hora_salida.setText(time1);
            }
        }
    };





    private void cargar_compania_en_la_lista(String sql,Spinner spinner)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Cursor fila = bd.rawQuery("select * from compania ", null);
        Cursor fila = bd.rawQuery(sql, null);
        String []lista=new String[fila.getCount()];
        int i=0;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                String nombre=fila.getString(0);
                lista[i]=nombre;
                i++;
            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            spinner.setAdapter(adapter);
        }catch (Exception e)
        {

        }
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

    public void guardar(String title, String message, String cliente, String id_pedido, String nombre, String latitud, String longitud, String tipo, String fecha, String hora) {
        SQLite admin = new SQLite(this, "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("titulo", title);
        registro.put("mensaje", message);
        registro.put("cliente", cliente);
        registro.put("nombre", nombre);
        registro.put("tipo", tipo);
        registro.put("fecha", fecha);
        registro.put("hora", hora);
        registro.put("latitud", latitud);
        registro.put("longitud", longitud);
        registro.put("id_pedido", id_pedido);
        bd.insert("notificacion", null, registro);
        bd.close();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();

            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            //ubicacion de la imagen
            Bitmap img_cargar;
            String uploadImage;

            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });

                    //Convertir MPath a Bitmap
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);

                        bm_foto_jefe=bitmap;
                        im_foto_jefe.setImageBitmap(bitmap);
                        im_foto_jefe.setPadding(0,0,0,0);
                        im_foto_jefe.setAdjustViewBounds(true);
                        im_foto_jefe.setScaleType(ImageView.ScaleType.FIT_CENTER);


                    //imagen cuadrado
                    //subir imagen de perfil al servidor...

                    //Convertir Bitmap a Drawable.


                    break;


            }
        }

    }
    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(R.id.cb_1==id||R.id.cb_2==id||R.id.cb_3==id||R.id.cb_4==id||R.id.cb_5==id||R.id.cb_6==id||R.id.cb_7==id||R.id.cb_8==id||R.id.cb_9==id||R.id.cb_10==id||R.id.cb_11==id||R.id.cb_12==id||R.id.cb_13==id||R.id.cb_14==id||R.id.cb_15==id||R.id.cb_16==id||R.id.cb_17==id||R.id.cb_18==id||R.id.cb_19==id||R.id.cb_20==id)
        {
            String sum=String.format("%.1f",sumatoria_manipulacion_dilucion());
            String sumatoria=coma_a_punto(sum);
            tv_Sumatoria_vacuna_congelada.setText(sumatoria);
            tv_ManipulacionDisolucion.setText(sumatoria);
        }



        switch (view.getId())
        {
            case R.id.im_foto_jefe:
                imagen_camara();
                break;
            case R.id.sp_Unidad:
                Toast.makeText(this,sp_granja.getSelectedItem().toString(),Toast.LENGTH_SHORT);
                break;

            case R.id.bt_firmar_1:
                AlertDialog.Builder   builder_dialogo = new AlertDialog.Builder(this);

                final LayoutInflater inflater = getLayoutInflater();

                final View dialoglayout = inflater.inflate(R.layout.firmar, null);
                final com.grayhatcorp.invetsa.invetsa.TouchView tc_view=(TouchView)dialoglayout.findViewById(R.id.tc_view);
                TextView tv_titulo=(TextView) dialoglayout.findViewById(R.id.tv_titulo);
                Button bt_borrar=(Button) dialoglayout.findViewById(R.id.bt_borrar);
                Button bt_listo=(Button) dialoglayout.findViewById(R.id.bt_listo);
                // Start the animation (looped playback by default).

                tv_titulo.setText("FIRMA DEL JEFE DE PLANTA");
                builder_dialogo.setView(dialoglayout);
                alertDialog_firmar_1=builder_dialogo.create();
                alertDialog_firmar_1.show();


                bt_borrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FrameLayout savedImage2 = null;
                        savedImage2 = (FrameLayout)dialoglayout.findViewById(R.id.fl_view);
                        savedImage2.setDrawingCacheEnabled(true);
                        savedImage2.buildDrawingCache();
                        tc_view.limpiar();
                        savedImage2.removeAllViews();
                        savedImage2.addView(tc_view);
                    }
                });
                bt_listo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FrameLayout savedImage2 = null;
                        savedImage2 = (FrameLayout)dialoglayout.findViewById(R.id.fl_view);
                        savedImage2.setDrawingCacheEnabled(true);
                        savedImage2.buildDrawingCache();
                        bm_firma_1 = savedImage2.getDrawingCache();
                        guardar_en_memoria(bm_firma_1,"firma_1.jpeg","Invetsa/Hoja_verificacion");
                        tc_view.limpiar();
                        savedImage2.removeAllViews();
                        savedImage2.addView(tc_view);
                        im_firma_1.setImageBitmap(bm_firma_1);
                        alertDialog_firmar_1.hide();
                        im_firma_1.setPadding(0,0,0,0);
                        im_firma_1.setAdjustViewBounds(true);
                        im_firma_1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                });
                break;

            case R.id.bt_firmar_2:
                AlertDialog.Builder   builder_dialogo2 = new AlertDialog.Builder(this);

                final LayoutInflater inflater2 = getLayoutInflater();

                final View dialoglayout2 = inflater2.inflate(R.layout.firmar, null);
                final com.grayhatcorp.invetsa.invetsa.TouchView tc_view2=(TouchView)dialoglayout2.findViewById(R.id.tc_view);
                TextView tv_titulo1=(TextView) dialoglayout2.findViewById(R.id.tv_titulo);
                Button bt_borrar2=(Button) dialoglayout2.findViewById(R.id.bt_borrar);
                Button bt_listo2=(Button) dialoglayout2.findViewById(R.id.bt_listo);
                // Start the animation (looped playback by default).
                tv_titulo1.setText("FIRMAR DEL TECNICO");
                builder_dialogo2.setView(dialoglayout2);
                alertDialog_firmar_2=builder_dialogo2.create();
                alertDialog_firmar_2.show();


                bt_borrar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FrameLayout savedImage2 = null;
                        savedImage2 = (FrameLayout)dialoglayout2.findViewById(R.id.fl_view);
                        savedImage2.setDrawingCacheEnabled(true);
                        savedImage2.buildDrawingCache();
                        tc_view2.limpiar();
                        savedImage2.removeAllViews();
                        savedImage2.addView(tc_view2);
                    }
                });
                bt_listo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FrameLayout savedImage2 = null;
                        savedImage2 = (FrameLayout)dialoglayout2.findViewById(R.id.fl_view);
                        savedImage2.setDrawingCacheEnabled(true);
                        savedImage2.buildDrawingCache();
                        bm_firma_2 = savedImage2.getDrawingCache();
                        guardar_en_memoria(bm_firma_2,"firma_2.jpeg","Invetsa/Hoja_verificacion");
                        tc_view2.limpiar();
                        savedImage2.removeAllViews();
                        savedImage2.addView(tc_view2);
                        im_firma_2.setImageBitmap(bm_firma_2);
                        alertDialog_firmar_2.hide();
                        im_firma_2.setPadding(0,0,0,0);
                        im_firma_2.setAdjustViewBounds(true);
                        im_firma_2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                });
                break;

            case R.id.bt_modificar_lista:
                cargar_vacunador();
                final AlertDialog.Builder   alerta_build_lista = new AlertDialog.Builder(this);

                final LayoutInflater inflater3 = getLayoutInflater();

                final View dialoglayout3 = inflater3.inflate(R.layout.lista_empleados, null);
                final ListView lv_lista=(ListView)dialoglayout3.findViewById(R.id.lv_lista_empleados);
                Button bt_cancelar=(Button) dialoglayout3.findViewById(R.id.bt_cancelar);
                Button bt_agregar=(Button) dialoglayout3.findViewById(R.id.bt_agregar);

                Base_adapter_vacunadores adaptador = new Base_adapter_vacunadores(getApplicationContext(),this,al_vacunadores);
                lv_lista.setAdapter(adaptador);

                // Start the animation (looped playback by default).

                alerta_build_lista.setView(dialoglayout3);
                alerta_lista=alerta_build_lista.create();
                alerta_lista.setCancelable(false);
                alerta_lista.show();


                bt_agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alerta_lista.hide();
                        cargar_vacunadores_en_tabla(al_vacunadores);
                        cargar_tabla_control_indice(al_vacunadores);
                    }
                });
                bt_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alerta_lista.hide();

                    }
                });
                break;
        }
    }

    private void imagen_camara() {


        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    private void guardar_formulario() {
        boolean sw_hoja=false,sw_verificar_datos=false;
        int id_hoja=id_hoja_verificacion()+1;
        if (id_hoja == 1)
        {
            SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
            id_hoja=perfil.getInt("id_hoja_verificacion",1);
        }

        int id_empresa = Integer.parseInt(get_id_tabla("select id from empresa where nombre='" + auto_empresa.getText().toString() + "'"));
        int id_granja =-1;
        int id_galpon =-1;
        try{id_granja=Integer.parseInt(get_id_tabla("select id from granja where nombre='" + sp_granja.getSelectedItem().toString() + "' and id_empresa='"+id_empresa+"'"));}catch (Exception e){id_granja=-1;}
        try{id_galpon=Integer.parseInt(get_id_tabla("select id from galpon where codigo='" + sp_unidad.getSelectedItem().toString() + "' and id_empresa='"+id_empresa+"'"+" and id_granja='"+id_granja+"'"));}catch (Exception e){id_galpon=-1;}


        //id TECNICO...
        SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
        int id_tecnico=perfil.getInt("id",-1);

        sw_verificar_datos=verificar_datos_sim(id_empresa,id_granja,id_galpon,id_tecnico,imei,bm_firma_1,bm_firma_2);
        if(sw_verificar_datos)
        {
            String DIRECCION_FIRMA_JEFE="Invetsa/Hoja_verificacion/"+id_hoja+"_jefe_"+fecha.getText()+".jpeg";
            String DIRECCION_FIRMA_INVETSA="Invetsa/hoja_verificacion/"+id_hoja+"_invetsa_"+fecha.getText()+".jpeg";
            String DIRECCION_FOTO_JEFE="Invetsa/hoja_verificacion/"+id_hoja+"_foto_jefe_"+fecha.getText()+".jpeg";

            guardar_en_memoria(bm_firma_1,id_hoja+"_jefe_"+fecha.getText()+".jpeg","Invetsa/Hoja_verificacion");
            guardar_en_memoria(bm_firma_2,id_hoja+"_invetsa_"+fecha.getText()+".jpeg","Invetsa/Hoja_verificacion");
            guardar_en_memoria(bm_foto_jefe,id_hoja+"_foto_jefe_"+fecha.getText()+".jpeg","Invetsa/Hoja_verificacion");

            String codigo=getString(R.string.codigo_hoja_de_verificacion);
            String revision=getString(R.string.revision_hoja_de_verificacion);
            double total_vc=Double.parseDouble(et_vc.getText().toString());
            double s_puntaje_control_indice=Double.parseDouble(tv_puntaje_control_indice.getText().toString());
            double s_promedio_vacunadores_avacub=Double.parseDouble(tv_Promedio_Vacunadoras_ACCUVAC.getText().toString());
            double s_vacuna_congelada=Double.parseDouble(tv_Sumatoria_vacuna_congelada.getText().toString());
            double s_productividad=Double.parseDouble(tv_productividad.getText().toString());
            sw_hoja=guardar_hoja_verificacion(id_hoja, fecha.getText().toString(), hora_ingreso.getText().toString(), hora_salida.getText().toString(), codigo, revision,DIRECCION_FIRMA_INVETSA, DIRECCION_FIRMA_JEFE,s_productividad,s_vacuna_congelada,s_promedio_vacunadores_avacub,s_puntaje_control_indice,id_galpon,id_granja,id_empresa,id_tecnico,DIRECCION_FOTO_JEFE,et_responsable_invetsa.getText().toString(),et_responsable_incubadora.getText().toString(),total_vc,tv_Puntaje_total.getText().toString());
            if(sw_hoja) {
                guardar_linea_genetica(1, tv_nro_nacimiento.getText().toString(), auto_cobb.getText().toString(), auto_ross.getText().toString(), et_hybro.getText().toString(), id_hoja);

                int id_accion = 1;
                guardar_accion(id_accion, "Laboratorio de preparación de vacuna", id_hoja);
                guardar_detalle_accion(1, "Temperatura 24 a 27 ºC", auto_temperatura.getText().toString(), id_accion, id_hoja);
                guardar_detalle_accion(2, "Ventilación forzada", sp_ventilacion.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(3, "Limpieza después c/vacunación", sp_limpieza_vacunacion.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(4, "Humedad 65 % HR", sp_humedad.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(5, "Presión positiva", sp_presion.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(6, "Desinfección post limpieza", sp_desinfeccion.getSelectedItem().toString(), id_accion, id_hoja);

                id_accion = 2;
                guardar_accion(id_accion, "Equipo Invetsa-Temp y otros", id_hoja);
                guardar_detalle_accion(1, "Guantes y Lentes", sp_guantes_lentes.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(2, "Recipiente de agua", sp_recipiente_agua.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(3, "Tº 27 a 37 ºC (promedio 32 ºC)", auto_promedio_temperatura.getText().toString(), id_accion, id_hoja);
                guardar_detalle_accion(4, "Rompe ampollas", sp_rompe_ampollas.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(5, "Aguja 18 G x 18 1 ½” (rosada)", sp_agujas.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(6, "Algodón", sp_algodon.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(7, "Colorante Marek dosis", et_colorante.getText().toString(), id_accion, id_hoja);
                guardar_detalle_accion(8, "Conectores “Y”", sp_conectores.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(9, "Calentador de agua", sp_calentador_agua.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(10, "Termómetro", sp_termometro.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(11, "Soporte de ampollas", sp_soporte_ampollas.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(12, "Jeringas  5 y/o 10 ml", auto_jeringas.getText().toString(), id_accion, id_hoja);
                guardar_detalle_accion(13, "Alcohol", sp_alcohol.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(14, "Papel Toalla", sp_papel.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(15, "Tubería nueva para vacuna", auto_tuberia.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(16, "Esterilizador/Autoclave", auto_esterilizador.getText().toString(), id_accion, id_hoja);

                id_accion = 3;
                guardar_accion(id_accion, "Sala de vacunación", id_hoja);
                guardar_detalle_accion(1, "Mesa circular-lineal-individual", sp_mesa.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(2, "Cambio agujas c/2,000 pollos", sp_cambio_agujas.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(3, "T º 24 a 27 º C", autoTemperaturaVacunacion.getText().toString(), id_accion, id_hoja);
                guardar_detalle_accion(4, "Ventilación forzada", sp_ventilacion_forzada.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(5, "Limpieza después c/vacunación", sp_limpiezaSala_vacunacion.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(6, "Nº de maquinas", et_maquinas.getText().toString(), id_accion, id_hoja);
                guardar_detalle_accion(7, "Cajas plásticas / cartón", sp_cajas.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(8, "Humedad 60 a 70 % HR", autoHumedadVacunacion.getText().toString(), id_accion, id_hoja);
                guardar_detalle_accion(9, "Presión negativa", sp_presion_vacunacion.getSelectedItem().toString(), id_accion, id_hoja);
                guardar_detalle_accion(10, "Desinfección post limpieza", sp_desinfeccion_sala_vacunacion.getSelectedItem().toString(), id_accion, id_hoja);


                guardar_manipulacion_dilucion(1, "Verificación del nivel de nitrógeno en formato (mínimo 6 pulgadas), obligatorio los días de vacunación       15  pulg.", get_double_to_checkbox(cb_1), id_hoja);
                guardar_manipulacion_dilucion(2, "Diluyente usado en buenas condiciones rojo y. transparente              Gentamicina 10%  9 ml  x  800ml ", get_double_to_checkbox(cb_2), id_hoja);
                guardar_manipulacion_dilucion(3, "Jeringas descartables individuales para cada tipo de vacuna, colorante y antibiótico    (no usan) (Lab. Invesa)", get_double_to_checkbox(cb_3), id_hoja);
                guardar_manipulacion_dilucion(4, "Tiempo mínimo para añadir antibiótico y colorante antes de preparar la vacuna  15 minutos", get_double_to_checkbox(cb_4), id_hoja);
                guardar_manipulacion_dilucion(5, "Uso de colorante y dosis de 0.5 ml para bolsas 200 y 400 ml , 1 ml para 800 ml y 1.5 ml para 1200 y 1600 ml", get_double_to_checkbox(cb_5), id_hoja);
                guardar_manipulacion_dilucion(6, "Jeringa cargada con 2 ml de diluyente para extraer la vacuna ya descongelada ", get_double_to_checkbox(cb_6), id_hoja);
                guardar_manipulacion_dilucion(7, "Uso de guantes y protector ocular para manipulación de vacunas congeladas al retirar la vacuna del tanque ", get_double_to_checkbox(cb_7), id_hoja);
                guardar_manipulacion_dilucion(8, "Buenas condiciones de funcionamiento del Invetsa-temp de 27 a 37 º C (promedio 32º C)", get_double_to_checkbox(cb_8), id_hoja);
                guardar_manipulacion_dilucion(9, "Numero de ampollas retiradas por vez para descongelación, máximo 2 ampollas     2 ampollas", get_double_to_checkbox(cb_9), id_hoja);
                guardar_manipulacion_dilucion(10, "Tiempo máximo para descongelamiento de la ampolla de 1 minuto y reconstitución en diluyente 30 segundos", get_double_to_checkbox(cb_10), id_hoja);
                guardar_manipulacion_dilucion(11, "Uso de pajilla de aluminio para descongelamiento de las ampollas en el agua, evita introducir la mano en el agua", get_double_to_checkbox(cb_11), id_hoja);
                guardar_manipulacion_dilucion(12, "Favorecer el descongelamiento de la ampolla en forma suave (movimiento circulares y verticales)", get_double_to_checkbox(cb_12), id_hoja);
                guardar_manipulacion_dilucion(13, "Secar las ampollas con papel toalla y usar el rompe ampollas", get_double_to_checkbox(cb_13), id_hoja);
                guardar_manipulacion_dilucion(14, "Uso del soporte de ampollas, absorción y reconstitución de la vacuna en el diluyente suave y sin presión en Jeringa", get_double_to_checkbox(cb_14), id_hoja);
                guardar_manipulacion_dilucion(15, "Uso de aguja adecuada para la extracción de la vacuna, 18 G x 1 ½” (color rosado)", get_double_to_checkbox(cb_15), id_hoja);
                guardar_manipulacion_dilucion(16, "Realización de enjuague de ampollas (incluye cuerpo y tapa de la ampolla)", get_double_to_checkbox(cb_16), id_hoja);
                guardar_manipulacion_dilucion(17, "Secuencia correcta de preparación de la vacuna (antibiótico, colorante y 15 minutos después  vacunas congeladas)", get_double_to_checkbox(cb_17), id_hoja);
                guardar_manipulacion_dilucion(18, "Tiempo de consumo de la solución vacunal preparada máximo 45 minutos y homogenizar la vacuna cada 10 minut.", get_double_to_checkbox(cb_18), id_hoja);
                guardar_manipulacion_dilucion(19, "Perfecta distribución de las mangueras que conducen la vacuna (levemente estiradas, evitando el efecto hamaca)", get_double_to_checkbox(cb_19), id_hoja);
                guardar_manipulacion_dilucion(20, "Conteo celular - % de Viabilidad mayor a 86 %  Vaxxitek  x  4000 ds  Serie RM 470   Vence 13-07-18 ", get_double_to_checkbox(cb_20), id_hoja);


                guardar_mantenimiento_y_limpieza(id_hoja);
                guardar_control_de_indice(id_hoja);


                guardar_viabilidad_celular(1, et_antibiotico_1, et_dosis_1, et_tiempo_1, et_vc_1, id_hoja);
                guardar_viabilidad_celular(2, et_antibiotico_2, et_dosis_2, et_tiempo_2, et_vc_2, id_hoja);
                guardar_viabilidad_celular(3, et_antibiotico_3, et_dosis_3, et_tiempo_3, et_vc_3, id_hoja);
                guardar_viabilidad_celular(4, et_antibiotico_4, et_dosis_4, et_tiempo_4, et_vc_4, id_hoja);
            }
            if(sw_hoja)
            {
                mensaje_ok_cerrar("Se guardo Correctamente.");
                //HojaDeVerificacion.this.finish();
            }
            else
            {
                mensaje_ok_error("Por favor complete los campos.");
            }
        }
    }

    private void cargar_vacunadores_en_tabla(ArrayList<clsVacunador> al_vacunador) {
       boolean sw_lista=false;
        jso_mantenimiento = new JSONObject();
        int count = tb_mantenimiento_limpieza.getChildCount();

        tb_mantenimiento_limpieza.removeViews(1, count - 1);

        TableRow.LayoutParams params=(TableRow.LayoutParams)ll_irregularidades.getLayoutParams();
        params.span=1;
        ll_irregularidades.setLayoutParams(params);

        //Cursor fila = bd.rawQuery("select * from compania ", null);
        inter=1;
        Iterator<clsVacunador> it=al_vacunador.iterator();
        int lista=al_vacunador.size();
        while (it.hasNext()) {
            clsVacunador va = it.next();

            TableRow tableRow = new TableRow(this);
            final int id = va.getId();
            String nombre_v = va.getNombre();

            if (va.isEstado()) {

                sw_lista=true;


                TextView nombre = new TextView(this);
                nombre.setText(nombre_v);
                nombre.setWidth(250);
                nombre.setTextColor(Color.BLACK);
                nombre.setGravity(Gravity.CENTER);


                final EditText numero = new EditText(this);
                numero.setText("" + inter);
                numero.setTextColor(Color.BLACK);;
                numero.setWidth(100);
                numero.setGravity(Gravity.CENTER);

                final CheckBox cb1 = new CheckBox(this);
                final CheckBox cb2 = new CheckBox(this);
                final CheckBox cb3 = new CheckBox(this);
                final CheckBox cb4 = new CheckBox(this);
                final CheckBox cb5 = new CheckBox(this);
                final CheckBox cb6 = new CheckBox(this);
                final CheckBox cb7 = new CheckBox(this);
                final CheckBox cb8 = new CheckBox(this);
                final CheckBox cb9 = new CheckBox(this);
                final CheckBox cb10 = new CheckBox(this);
                final CheckBox cb11 = new CheckBox(this);
                final CheckBox cb12 = new CheckBox(this);
                final CheckBox cb13 = new CheckBox(this);
                final CheckBox cb14 = new CheckBox(this);
                final CheckBox cb15 = new CheckBox(this);

                numero.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        try {
                            jso_mantenimiento.remove(id + " " + 1 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 2 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 3 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 4 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 5 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 6 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 7 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 8 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 9 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 10 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 11 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 12 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 13 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 14 + ":" +charSequence.toString());
                            jso_mantenimiento.remove(id + " " + 15 + ":" +charSequence.toString());
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        try {
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 1 + ":" + numero.getText(),cb1);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 2 + ":" + numero.getText(),cb2);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 3 + ":" + numero.getText(),cb3);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 4 + ":" + numero.getText(),cb4);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 5 + ":" + numero.getText(),cb5);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 6 + ":" + numero.getText(),cb6);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 7 + ":" + numero.getText(),cb7);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 8 + ":" + numero.getText(),cb8);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 9 + ":" + numero.getText(),cb9);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 10 + ":" + numero.getText(),cb10);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 11 + ":" + numero.getText(),cb11);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 12 + ":" + numero.getText(),cb12);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 13 + ":" + numero.getText(),cb13);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 14 + ":" + numero.getText(),cb14);
                            modificar_json_lista_vacunadores(jso_mantenimiento,id + " " + 15 + ":" + numero.getText(),cb15);
                        } catch (Exception e) {

                        }
                    }
                });



                cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                jso_mantenimiento.remove(id + " " + 1 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 1 + ":" + numero.getText(), "1");
                                Toast.makeText(getApplicationContext(), "id=" + id + " c=" + 1+" inter="+numero.getText(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 1 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 1 + ":" +numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 2 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 2 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 2 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 2 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 3 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 3 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 3 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 3 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 4 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 4 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 4 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 4 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 5 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 5 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 5 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 5 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 6 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 6 + ":" + numero.getText(), "1");

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
                                jso_mantenimiento.remove(id + " " + 7 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 7 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 7 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 7 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 8 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 8 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 8 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 8 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 9 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 9 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 9 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 9 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 10 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 10 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 10 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 10 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 11 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 11 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 11 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 11 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 12 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 12 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 12 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 12 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 13 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 13 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 13 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 13 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 14 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 14 + ":" + numero.getText(), "1");
                                Toast.makeText(getApplicationContext(), "id=" + id + " c=" + 14, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 14 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 14 + ":" + numero.getText(), "0");

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
                                jso_mantenimiento.remove(id + " " + 15 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 15 + ":" + numero.getText(), "1");

                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                jso_mantenimiento.remove(id + " " + 15 + ":" + numero.getText());
                                jso_mantenimiento.put(id + " " + 15 + ":" + numero.getText(), "0");

                            } catch (Exception e) {

                            }

                        }
                    }
                });


                try {

                    jso_mantenimiento.put(id + " " + 1 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 2 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 3 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 4 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 5 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 6 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 7 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 8 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 9 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 10 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 11 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 12 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 13 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 14 + ":" + numero.getText(), "0");
                    jso_mantenimiento.put(id + " " + 15 + ":" + numero.getText(), "0");


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

        if(sw_lista)
        {
            params.span=15;
            ll_irregularidades.setLayoutParams(params);
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

            if (va.isEstado()) {

                TextView nombre=new TextView(this);
                nombre.setText(nombre_v);
                nombre.setWidth(250);
                nombre.setTextColor(Color.BLACK);
                nombre.setGravity(Gravity.CENTER);

                EditText et_vacunados=new EditText(this);
                et_vacunados.setText("0");
                nombre.setTextColor(Color.BLACK);
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
                            if(s.length()>0 && !s.equals("")) {
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

                et_eficiencia.addTextChangedListener(new TextWatcher() {

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
                            jso_control_indice.remove(id + " " + 9);
                            if(s.length()>0 && !s.equals("")) {
                                jso_control_indice.put(id + " " + 9, s);
                            }
                            else
                            {
                                jso_control_indice.put(id + " " + 9, "0");
                            }
                            sumatoria_control_indice(tv_sum_9,tv_pro_9,9);

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
                    int indice_eficiencia=Integer.parseInt(jso_control_indice.get(id+" 9").toString());
                    int id_hoja_verificacion=id_hoja;
                    int id_vacunador=Integer.parseInt(id);

                    guardar_control_indice(item,nro_pollos_vacunado,puntaje,nro_pollos_controlados,nro_pollos_no_vacunados,nro_heridos,nro_mojados,nro_mala_posicion,nro_pollos_vacunados_correctamente, indice_eficiencia, id_hoja_verificacion,id_vacunador);

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


    public void guardar_viabilidad_celular(int id,EditText antibiotico,EditText dosis,EditText tiempo,EditText vc,int id_hoja)
    {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf( id));
            registro.put("antibiotico",antibiotico.getText().toString());
            registro.put("dosis",dosis.getText().toString());
            registro.put("tiempo",tiempo.getText().toString());
            registro.put("vc",vc.getText().toString());
            registro.put("id_hoja_verificacion",String.valueOf(id_hoja));
            registro.put("imei",imei);
            bd.insert("viabilidad_celular", null, registro);
        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }
        bd.close();
    }

    private void guardar_mantenimiento_y_limpieza(int id_hoja) {
try {

    Iterator<String> iter = jso_mantenimiento.keys();
    String key = iter.next();
    while (iter.hasNext()) {
        try {
            Object object = jso_mantenimiento.get(key);
            String v = object.toString();
            int irregularidad = Integer.parseInt(v);
            int id_vacunador = get_id_vacunador(key);
            int maquina = get_maquina(key);
            int id_irregularidad = get_irregularidad(key);

            guardar_mantenimiento_limpieza(id_irregularidad, maquina, irregularidad, id_hoja, id_vacunador);
                /*
                  Dato dato=new Dato();
                  jso_mantenimiento.remove(id + " " + 1+":"+inter);
                 dato.set_mantenimiento(key,Integer.parseInt(v));

                */
        } catch (JSONException e) {
            e.printStackTrace();
        }
        key = iter.next();
    }
}catch (Exception e)
{
    e.printStackTrace();
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
            if(!valor.equals("") && !valor.equals("0") )
            {
                id=Integer.parseInt(valor);
            }

        }
        bd.close();
        return id;
    }


    public boolean guardar_hoja_verificacion(int id,String fecha,String hora_ingreso,String hora_salida,String codigo,String revision,String firma_invetsa,String firma_empresa,double productividad,double sumatoria_manipulacion_vacuna,double promedio_mantenimiento ,double puntaje_control_indice,int id_galpon_s,int id_granja_s,int id_empresa_s,int id_tecnico,String nombre_foto_jefe,String responsable_invetsa,String responsable_incubadora,double total_vc,String puntaje_total)
    {
        boolean sw=false;
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
            registro.put("imagen_jefe", nombre_foto_jefe);
            registro.put("responsable_invetsa", responsable_invetsa);
            registro.put("responsable_incubadora", responsable_incubadora);
            registro.put("total_vc", String.valueOf(total_vc));
            registro.put("puntaje_total", puntaje_total);
            bd.insert("hoja_verificacion", null, registro);
            sw=true;

        }catch (Exception e)
        {sw=false;
            Log.e("sql",""+e);
        }

        bd.close();
        return sw;
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
    public void guardar_linea_genetica(int id,String descripcion,String cobb,String ross,String hybro,int id_hoja_verificacion)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("descripcion",descripcion);
            registro.put("cobb", cobb);
            registro.put("ross", ross);
            registro.put("hybro", hybro);
            registro.put("id_hoja_verificacion", String.valueOf(id_hoja_verificacion));
            registro.put("imei", imei);
            bd.insert("linea_genetica", null, registro);

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

    public void guardar_mantenimiento_limpieza(int id,int nro_maquina,int irregularidades,int id_hoja_verificacion,int id_vacunador)
    {
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("nro_maquina",String.valueOf(nro_maquina));
            registro.put("irregularidades", String.valueOf(irregularidades));
            registro.put("id_hoja_verificacion", String.valueOf(id_hoja_verificacion));
            registro.put("id_vacunador", String.valueOf(id_vacunador));
            registro.put("imei", imei);
            bd.insert("mantenimiento_limpieza", null, registro);
        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }
        bd.close();
    }

    public void guardar_control_indice(int id,int nro_pollos_vacunado,int puntaje,int nro_pollos_controlados,int nro_pollos_no_vacunados,int nro_heridos, int nro_mojados, int nro_mala_posicion, int nro_pollos_vacunados_correctamente, int indice_eficiencia, int id_hoja_verificacion, int id_vacunador)
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
            registro.put("indice_eficiencia", String.valueOf(indice_eficiencia ));
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

            if(isDirectoryCreated && bitmapImage!= null) {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean verificar_datos_se(int id_maquina, int id_compania, int id_tecnico, String imei, Bitmap bm_firma_1, Bitmap bm_firma_2) {
        boolean sw = true;
        if (id_compania == -1) {
            sw = false;
            mensaje_ok_error("Por favor complete el campo de  " + Html.fromHtml("<b>COMPAÑIA</b>"));
        } else if (id_maquina == -1) {
            sw = false;
            mensaje_ok_error("Por favor complete el campo de  " + Html.fromHtml("<b>MAQUINA</b>"));
        } else if (bm_firma_1 == null) {
            sw = false;
            mensaje_ok_error("Se requiere la firma del " + Html.fromHtml("<br><b>JEFE DE PLANATA DE INCUBACIÓN.</b>"));
        } else if (bm_firma_2 == null) {
            sw = false;
            mensaje_ok_error("Se requiere la firma del " + Html.fromHtml("<br><b>TENICO DE INVETSA.</b>"));
        } else if (id_tecnico == -1) {
            sw = false;
            mensaje_ok_error("No existe ningun dato del Tecnico." + Html.fromHtml("<br><b>VUELVA A INICIAR SESION</b>"));
        } else if (imei.equals("") == true) {
            sw = false;
            mensaje_ok_error("Habilitar los permisos para obtener el " + Html.fromHtml("<b>IMEI</b>") + " del celular.");
        }
        return sw;
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
        builder_dialogo.setCancelable(false);

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

    public void cargar_autocompletado()
    {
        String[] temperatura=getResources().getStringArray(R.array.Temperatura);
        ArrayAdapter<String> adapter_temperatura = new ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line, temperatura);
        String[] jeringas=getResources().getStringArray(R.array.Jeringas);
        ArrayAdapter<String> adapter_jeringas = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, jeringas);
        String[] tuberia=getResources().getStringArray(R.array.Tuberia);
        ArrayAdapter<String> adapter_tuberia = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, tuberia);
        String[] esterizador=getResources().getStringArray(R.array.Esterilizador);
        ArrayAdapter<String> adapter_esterilizador = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, esterizador);

        auto_temperatura.setAdapter(adapter_temperatura);
        auto_promedio_temperatura.setAdapter(adapter_temperatura);
        auto_jeringas.setAdapter(adapter_jeringas);
        //auto_tuberia.setAdapter(adapter_tuberia); //COMENTE ESTO AL CAMBIAR A SPINNER
        auto_esterilizador.setAdapter(adapter_esterilizador);
    }

    public double get_double_to_checkbox(CheckBox cb)
    {
        double i=0;
        if(cb.isChecked())
        {
            i=0.1;
        }
        return i;
    }
    public double sumatoria_manipulacion_dilucion()
    {
        double sumatoria=0;
        double c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20;
        c1=get_double_to_checkbox(cb_1);
        c2=get_double_to_checkbox(cb_2);
        c3=get_double_to_checkbox(cb_3);
        c4=get_double_to_checkbox(cb_4);
        c5=get_double_to_checkbox(cb_5);
        c6=get_double_to_checkbox(cb_6);
        c7=get_double_to_checkbox(cb_7);
        c8=get_double_to_checkbox(cb_8);
        c9=get_double_to_checkbox(cb_9);
        c10=get_double_to_checkbox(cb_10);
        c11=get_double_to_checkbox(cb_11);
        c12=get_double_to_checkbox(cb_12);
        c13=get_double_to_checkbox(cb_13);
        c14=get_double_to_checkbox(cb_14);
        c15=get_double_to_checkbox(cb_15);
        c16=get_double_to_checkbox(cb_16);
        c17=get_double_to_checkbox(cb_17);
        c18=get_double_to_checkbox(cb_18);
        c19=get_double_to_checkbox(cb_19);
        c20=get_double_to_checkbox(cb_20);


            sumatoria=c1+c2+c3+c4+c5+c6+c7+c8+c9+c10+c11+c12+c13+c14+c15+c16+c17+c18+c19+c20;
        return sumatoria;

    }

    public void modificar_json_lista_vacunadores(JSONObject json,String nombre,CheckBox cb)
    {
        if (cb.isChecked()) {
            try {
                json.remove(nombre);
                json.put(nombre, "1");
            } catch (Exception e) {

            }
        } else {
            try {
                json.remove(nombre);
                json.put(nombre, "0");

            } catch (Exception e) {

            }

        }
    }

    public int get_id_vacunador(String titulo)
    {int id=0;
        String dato="";

        for (int i=0;i<titulo.length();i++)
        {
            if(titulo.charAt(i)==' ')
            {
                id=Integer.parseInt(titulo.substring(0,i));
                i=titulo.length();
            }
        }
        return id;
    }

    public int get_irregularidad(String titulo)
    {int irregularidad=0,inicio=0,fin=0;
        String dato="";

        for (int i=0;i<titulo.length();i++)
        {
            if(titulo.charAt(i)==' ')
            {
                inicio=i+1;
            }
            if(titulo.charAt(i)==':')
            {
                fin=i;
                i=titulo.length();
            }
        }
        irregularidad=Integer.parseInt(titulo.substring(inicio,fin));
        return irregularidad;
    }
    public int get_maquina(String titulo)
    {int maquina=0,inicio=0;
        String dato="";

        for (int i=titulo.length()-1;i>=0;i--)
        {
            if(titulo.charAt(i)==':')
            {
                inicio=i+1;
                i=-1;
            }
        }
        maquina=Integer.parseInt(titulo.substring(inicio,titulo.length()));
        return maquina;
    }
    public String coma_a_punto(String titulo)
    {
String valor="";
        for (int i=0;i<titulo.length();i++)
        {
            if(titulo.charAt(i)==',')
            {
                valor=valor+".";
            }
            else
            {
                valor=valor+String.valueOf(titulo.charAt(i));
            }
        }

        return valor;
    }

    private boolean verificar_datos_sim(int id_empresa, int id_granja, int id_galpon, int id_tecnico, String imei,Bitmap firma_1_empresa,Bitmap firma_2_invetsa) {
        boolean sw=true;
        if(id_empresa==-1)
        {
            sw=false;
            mensaje_ok_error("Por favor complete el campo de  "+ Html.fromHtml("<b>EMPRESA</b>"));
        }else if(id_granja==-1)
        {
            sw=false;
            mensaje_ok_error("No existe ninguna "+Html.fromHtml("<b>GRANJA</b><br>")+" de la empresa selecionada en la Base de Datos del Celular."+Html.fromHtml("<b><br>ACTUALICE LOS DATOS DEL CELULAR</b>")+"");
        }else if(id_galpon==-1)
        {
            sw=false;
            mensaje_ok_error("No existe ningun "+Html.fromHtml("<b>GALPON</b><br>")+" de la empresa selecionada en la Base de Datos del Celular."+Html.fromHtml("<b><br>ACTUALICE LOS DATOS DEL CELULAR</b>")+"");
        }
        else if( firma_1_empresa==null)
        {
            sw=false;
            mensaje_ok_error("Se requiere la firma del "+Html.fromHtml("<br><b>JEFE DE PLANATA DE INCUBACIÓN.</b>"));
        }
        else if( firma_2_invetsa==null)
        {
            sw=false;
            mensaje_ok_error("Se requiere la firma del "+Html.fromHtml("<br><b>TENICO DE INVETSA.</b>"));
        }
        else if(id_tecnico==-1)
        {
            sw=false;
            mensaje_ok_error("No existe ningun dato del Tecnico."+Html.fromHtml("<br><b>VUELVA A INICIAR SESION</b>"));
        }
        else if(imei.equals("")==true)
        {
            sw=false;
            mensaje_ok_error("Habilitar los permisos para obtener el "+Html.fromHtml("<b>IMEI</b>")+ " del celular.");
        }
        return  sw;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Confirmación")
                    .setMessage("Salir de la hoja actual?\nSe perderán todos los cambios")
                    .setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            //Salir
                            HojaDeVerificacion.this.finish();
                        }
                    })
                    .show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);

    }


}