package com.grayhatcorp.invetsa.invetsa.sistema_integral_monitoreo;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.grayhatcorp.invetsa.invetsa.Base_de_datos.SQLite;
import com.grayhatcorp.invetsa.invetsa.R;
import com.grayhatcorp.invetsa.invetsa.TouchView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class SistemaIntegralMonitoreo extends AppCompatActivity implements View.OnClickListener {
    ArrayAdapter<String> adapter;

    FrameLayout mRlView;

    private static String APP_DIRECTORY = "Invetsa/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "Imagen";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private String mPath;
    private Uri path;
    String imei = "";

    AlertDialog alertDialog_firmar_1, alertDialog_firmar_2;
    ImageView im_firma_1, im_firma_2, im_foto_jefe;
    Bitmap bm_firma_1 = null, bm_firma_2 = null, bm_foto_1 = null, bm_foto_2 = null, bm_foto_3 = null, bm_foto_4 = null, bm_foto_5 = null, bm_foto_jefe = null;
    Button bt_firmar_1, bt_firmar_2;

    ImageView im_foto_1, im_foto_2, im_foto_3, im_foto_4, im_foto_5;
    String nombre_foto1 = "", nombre_foto2 = "", nombre_foto3 = "", nombre_foto4 = "", nombre_foto5 = "", nombre_temporal = "";

    int numero_camara = 0;

    private static final int Date_id = 0;
    TextView fecha;
    AutoCompleteTextView autoGalpon, autoEmpresa, autoZona, autoGranja;
    Spinner sp_granja, sp_galpon, sp_zona, edad, sexo;
    EditText /*edad,sexo,*/nro_pollos;
    EditText editTextPcorporal1, editTextPcorporal2, editTextPcorporal3, editTextPcorporal4, editTextPcorporal5, editTextResultadoPcorporal;
    EditText editTextPBursa1, editTextPBursa2, editTextPBursa3, editTextPBursa4, editTextPBursa5, editTextResultadoPBursa;
    EditText editTextPBazo1, editTextPBazo2, editTextPBazo3, editTextPBazo4, editTextPBazo5, editTextResultadoPBazo;
    EditText editTextPTimo1, editTextPTimo2, editTextPTimo3, editTextPTimo4, editTextPTimo5, editTextResultadoPTimo;
    EditText editTextPHigado1, editTextPHigado2, editTextPHigado3, editTextPHigado4, editTextResultadoPHigado, editTextPHigado5;
    EditText editTextIndiceBursal1, editTextIndiceBursal2, editTextIndiceBursal3, editTextIndiceBursal4, editTextIndiceBursal5, editTextResultadoIndiceBursal;
    EditText editTextIndiceTimico1, editTextIndiceTimico2, editTextIndiceTimico3, editTextIndiceTimico4, editTextIndiceTimico5, editTextResultadoIndiceTimico;
    EditText editTextIndiceHepatico1, editTextIndiceHepatico2, editTextIndiceHepatico3, editTextIndiceHepatico4, editTextIndiceHepatico5, editTextResultadoIndiceHepatico;
    EditText editTextBursometro1, editTextBursometro2, editTextBursometro3, editTextBursometro4, editTextResultadoBursometro, editTextBursometro5;
    //Button guardar,cancelar;
    EditText comisura, paladar_duro, paladar_blando, necrosis_lengua, pechuga, pierna, esternon, fragil, flexible, duro;
    EditText favor_bursa, favor_bazo, relacion11, normal_apariencia, anormal_apariencia, normal_timo, anormal_timo;
    EditText normales_sacos, aero_abdominal, aero_toracica, normal_traquea, congestionada, hemorragica, mucosidad, normal_cornetes, congestionado_cornetes;
    EditText normal_higado, inflamado_higado, palido_friable, moteado_higado, puntoExudado_higado, llena_vesicula, vacia_vesicula, normal_proventriculo, proventriculitis;
    EditText grado_0, grado_1, grado_2, grado_3, normal_intestino, enteritis_yeyuno, equimosis, desp_mucosa, enteritis_mucoide, transito_rapido, gas_espuma, acervulina, maxima, tenella, necrafix;
    EditText normal_ciegos, tiflitis_erosiva, placas_diftericas, gaseosos, liquido, sangre;
    EditText reactiva_tonsillas, no_reactiva_tonsillas, presencia, ausencia, reactiva_placas, no_reactiva_placas;

    EditText observaciones, comentarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistema_integral_monitoreo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp_granja = (Spinner) findViewById(R.id.sp_Granja);
        sp_galpon = (Spinner) findViewById(R.id.sp_Galpon);
        sp_zona = (Spinner) findViewById(R.id.sp_Zona);

        mRlView = (FrameLayout) findViewById(R.id.content_sistema_integral_monitoreo);

        //autoGalpon=(AutoCompleteTextView)findViewById(R.id.autoGalpon);
        autoEmpresa = (AutoCompleteTextView) findViewById(R.id.autoEmpresa);
        // autoZona=(AutoCompleteTextView)findViewById(R.id.autoZona);
        // autoGranja=(AutoCompleteTextView)findViewById(R.id.autoGranja);
        //edad=(EditText)findViewById(R.id.editTextEdad);
        edad = (Spinner) findViewById(R.id.sp_edad);
        nro_pollos = (EditText) findViewById(R.id.editTextNroPollos);
        //sexo=(EditText)findViewById(R.id.editTextSexo);
        sexo = (Spinner) findViewById(R.id.sp_sexo);

        fecha = (TextView) findViewById(R.id.fecha);

        editTextPcorporal1 = (EditText) findViewById(R.id.editTextPcorporal1);
        editTextPcorporal2 = (EditText) findViewById(R.id.editTextPcorporal2);
        editTextPcorporal3 = (EditText) findViewById(R.id.editTextPcorporal3);
        editTextPcorporal4 = (EditText) findViewById(R.id.editTextPcorporal4);
        editTextPcorporal5 = (EditText) findViewById(R.id.editTextPcorporal5);
        editTextResultadoPcorporal = (EditText) findViewById(R.id.editTextResultadoPcorporal);
        editTextPBursa1 = (EditText) findViewById(R.id.editTextPBursa1);
        editTextPBursa2 = (EditText) findViewById(R.id.editTextPBursa2);
        editTextPBursa3 = (EditText) findViewById(R.id.editTextPBursa3);
        editTextPBursa4 = (EditText) findViewById(R.id.editTextPBursa4);
        editTextPBursa5 = (EditText) findViewById(R.id.editTextPBursa5);
        editTextResultadoPBursa = (EditText) findViewById(R.id.editTextResultadoPBursa);
        editTextPBazo1 = (EditText) findViewById(R.id.editTextPBazo1);
        editTextPBazo2 = (EditText) findViewById(R.id.editTextPBazo2);
        editTextPBazo3 = (EditText) findViewById(R.id.editTextPBazo3);
        editTextPBazo4 = (EditText) findViewById(R.id.editTextPBazo4);
        editTextPBazo5 = (EditText) findViewById(R.id.editTextPBazo5);
        editTextResultadoPBazo = (EditText) findViewById(R.id.editTextResultadoPBazo);
        editTextPTimo1 = (EditText) findViewById(R.id.editTextPTimo1);
        editTextPTimo2 = (EditText) findViewById(R.id.editTextPTimo2);
        editTextPTimo3 = (EditText) findViewById(R.id.editTextPTimo3);
        editTextPTimo4 = (EditText) findViewById(R.id.editTextPTimo4);
        editTextPTimo5 = (EditText) findViewById(R.id.editTextPTimo5);
        editTextResultadoPTimo = (EditText) findViewById(R.id.editTextResultadoPTimo);
        editTextPHigado1 = (EditText) findViewById(R.id.editTextPHigado1);
        editTextPHigado2 = (EditText) findViewById(R.id.editTextPHigado2);
        editTextPHigado3 = (EditText) findViewById(R.id.editTextPHigado3);
        editTextPHigado4 = (EditText) findViewById(R.id.editTextPHigado4);
        editTextPHigado5 = (EditText) findViewById(R.id.editTextPHigado5);
        editTextResultadoPHigado = (EditText) findViewById(R.id.editTextResultadoPHigado);
        editTextIndiceBursal1 = (EditText) findViewById(R.id.editTextIndiceBursal1);
        editTextIndiceBursal2 = (EditText) findViewById(R.id.editTextIndiceBursal2);
        editTextIndiceBursal3 = (EditText) findViewById(R.id.editTextIndiceBursal3);
        editTextIndiceBursal4 = (EditText) findViewById(R.id.editTextIndiceBursal4);
        editTextIndiceBursal5 = (EditText) findViewById(R.id.editTextIndiceBursal5);
        editTextResultadoIndiceBursal = (EditText) findViewById(R.id.editTextResultadoIndiceBursal);
        editTextIndiceTimico1 = (EditText) findViewById(R.id.editTextIndiceTimico1);
        editTextIndiceTimico2 = (EditText) findViewById(R.id.editTextIndiceTimico2);
        editTextIndiceTimico3 = (EditText) findViewById(R.id.editTextIndiceTimico3);
        editTextIndiceTimico4 = (EditText) findViewById(R.id.editTextIndiceTimico4);
        editTextIndiceTimico5 = (EditText) findViewById(R.id.editTextIndiceTimico5);
        editTextResultadoIndiceTimico = (EditText) findViewById(R.id.editTextResultadoIndiceTimico);
        editTextIndiceHepatico1 = (EditText) findViewById(R.id.editTextIndiceHepatico1);
        editTextIndiceHepatico2 = (EditText) findViewById(R.id.editTextIndiceHepatico2);
        editTextIndiceHepatico3 = (EditText) findViewById(R.id.editTextIndiceHepatico3);
        editTextIndiceHepatico4 = (EditText) findViewById(R.id.editTextIndiceHepatico4);
        editTextIndiceHepatico5 = (EditText) findViewById(R.id.editTextIndiceHepatico5);
        editTextResultadoIndiceHepatico = (EditText) findViewById(R.id.editTextResultadoIndiceHepatico);
        editTextBursometro1 = (EditText) findViewById(R.id.editTextBursometro1);
        editTextBursometro2 = (EditText) findViewById(R.id.editTextBursometro2);
        editTextBursometro3 = (EditText) findViewById(R.id.editTextBursometro3);
        editTextBursometro4 = (EditText) findViewById(R.id.editTextBursometro4);
        editTextBursometro5 = (EditText) findViewById(R.id.editTextBursometro5);
        editTextResultadoBursometro = (EditText) findViewById(R.id.editTextResultadoBursometro);

        comisura = (EditText) findViewById(R.id.comisura);
        paladar_duro = (EditText) findViewById(R.id.paladar_duro);
        paladar_blando = (EditText) findViewById(R.id.paladar_blando);
        necrosis_lengua = (EditText) findViewById(R.id.necrosis_lengua);
        pechuga = (EditText) findViewById(R.id.pechuga);
        pierna = (EditText) findViewById(R.id.pierna);
        esternon = (EditText) findViewById(R.id.esternon);
        fragil = (EditText) findViewById(R.id.fragil);
        flexible = (EditText) findViewById(R.id.flexible);
        duro = (EditText) findViewById(R.id.duro);
        favor_bursa = (EditText) findViewById(R.id.favor_bursa);
        favor_bazo = (EditText) findViewById(R.id.favor_bazo);
        relacion11 = (EditText) findViewById(R.id.relacion11);
        normal_apariencia = (EditText) findViewById(R.id.normal_apariencia);
        anormal_apariencia = (EditText) findViewById(R.id.anormal_apariencia);
        normal_timo = (EditText) findViewById(R.id.normal_timo);
        anormal_timo = (EditText) findViewById(R.id.anormal_timo);
        normales_sacos = (EditText) findViewById(R.id.normales_sacos);
        aero_abdominal = (EditText) findViewById(R.id.aereo_abdominal);
        aero_toracica = (EditText) findViewById(R.id.aereo_toracica);
        normal_traquea = (EditText) findViewById(R.id.normal_traquea);
        congestionada = (EditText) findViewById(R.id.congestionada_traquea);
        hemorragica = (EditText) findViewById(R.id.hemorragica);
        mucosidad = (EditText) findViewById(R.id.mucosidad);
        normal_cornetes = (EditText) findViewById(R.id.normal_cornetes);
        congestionado_cornetes = (EditText) findViewById(R.id.congestionado);
        normal_higado = (EditText) findViewById(R.id.normal_higado);
        inflamado_higado = (EditText) findViewById(R.id.inflamado_higado);
        palido_friable = (EditText) findViewById(R.id.palido_higado);
        moteado_higado = (EditText) findViewById(R.id.moteado_higado);
        puntoExudado_higado = (EditText) findViewById(R.id.puntoExudado_higado);
        llena_vesicula = (EditText) findViewById(R.id.llena_vesicula);
        vacia_vesicula = (EditText) findViewById(R.id.vacia_vesicula);
        normal_proventriculo = (EditText) findViewById(R.id.normal_proventriculo);
        proventriculitis = (EditText) findViewById(R.id.proventriculitis);
        grado_0 = (EditText) findViewById(R.id.grado_0);
        grado_1 = (EditText) findViewById(R.id.grado_1);
        grado_2 = (EditText) findViewById(R.id.grado_2);
        grado_3 = (EditText) findViewById(R.id.grado_3);
        normal_intestino = (EditText) findViewById(R.id.normal_intestino);
        enteritis_yeyuno = (EditText) findViewById(R.id.enteritis_yeyuno);
        equimosis = (EditText) findViewById(R.id.equimosis);
        desp_mucosa = (EditText) findViewById(R.id.desp_mucosa);
        enteritis_mucoide = (EditText) findViewById(R.id.enteritis_mucoide);
        transito_rapido = (EditText) findViewById(R.id.transito_rapido);
        gas_espuma = (EditText) findViewById(R.id.gas_espuma);
        acervulina = (EditText) findViewById(R.id.acervulina);
        maxima = (EditText) findViewById(R.id.maxima);
        tenella = (EditText) findViewById(R.id.tenella);
        necrafix = (EditText) findViewById(R.id.necratix);
        normal_ciegos = (EditText) findViewById(R.id.normal_ciegos);
        tiflitis_erosiva = (EditText) findViewById(R.id.tiflitis_erosiva);
        placas_diftericas = (EditText) findViewById(R.id.placas_diftericas);
        gaseosos = (EditText) findViewById(R.id.gaseosos);
        liquido = (EditText) findViewById(R.id.liquido);
        sangre = (EditText) findViewById(R.id.sangre);
        reactiva_tonsillas = (EditText) findViewById(R.id.reactiva_tonsillas);
        no_reactiva_tonsillas = (EditText) findViewById(R.id.no_reactiva_tonsillas);
        presencia = (EditText) findViewById(R.id.presencia);
        ausencia = (EditText) findViewById(R.id.ausencia);
        reactiva_placas = (EditText) findViewById(R.id.reactiva_placas);
        no_reactiva_placas = (EditText) findViewById(R.id.no_reactiva_placas);

        observaciones = (EditText) findViewById(R.id.editTextObservaciones);
        comentarios = (EditText) findViewById(R.id.editTextComentarios);

        im_firma_2 = (ImageView) findViewById(R.id.im_firma_2);
        im_firma_1 = (ImageView) findViewById(R.id.im_firma_1);

        bt_firmar_2 = (Button) findViewById(R.id.bt_firmar_2);
        bt_firmar_1 = (Button) findViewById(R.id.bt_firmar_1);


        im_foto_1 = (ImageView) findViewById(R.id.im_foto_1);
        im_foto_2 = (ImageView) findViewById(R.id.im_foto_2);
        im_foto_3 = (ImageView) findViewById(R.id.im_foto_3);
        im_foto_4 = (ImageView) findViewById(R.id.im_foto_4);
        im_foto_5 = (ImageView) findViewById(R.id.im_foto_5);
        im_foto_jefe = (ImageView) findViewById(R.id.im_foto_jefe);

        im_foto_jefe.setOnClickListener(this);
        im_foto_1.setOnClickListener(this);
        im_foto_2.setOnClickListener(this);
        im_foto_3.setOnClickListener(this);
        im_foto_4.setOnClickListener(this);
        im_foto_5.setOnClickListener(this);

        /*******AGREGUE ESTO********/
        im_foto_2.setVisibility(View.GONE);
        im_foto_3.setVisibility(View.GONE);
        im_foto_4.setVisibility(View.GONE);
        im_foto_5.setVisibility(View.GONE);


        bt_firmar_1.setOnClickListener(this);
        bt_firmar_2.setOnClickListener(this);


        Calendar c = Calendar.getInstance();
        fecha.setText("" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));

        cargar_empresa_en_la_lista();
        // cargar_granja_en_la_lista();
        //cargar_galpon_en_la_lista();


        editTextPcorporal1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPcorporal1, editTextPcorporal2, editTextPcorporal3, editTextPcorporal4, editTextPcorporal5, editTextResultadoPcorporal);

            }
        });
        editTextPcorporal2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPcorporal1, editTextPcorporal2, editTextPcorporal3, editTextPcorporal4, editTextPcorporal5, editTextResultadoPcorporal);

            }
        });
        editTextPcorporal3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPcorporal1, editTextPcorporal2, editTextPcorporal3, editTextPcorporal4, editTextPcorporal5, editTextResultadoPcorporal);

            }
        });
        editTextPcorporal4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPcorporal1, editTextPcorporal2, editTextPcorporal3, editTextPcorporal4, editTextPcorporal5, editTextResultadoPcorporal);

            }
        });
        editTextPcorporal5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPcorporal1, editTextPcorporal2, editTextPcorporal3, editTextPcorporal4, editTextPcorporal5, editTextResultadoPcorporal);

            }
        });


        //sumatoria EDITEXT BURSA
        editTextPBursa1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBursa1, editTextPBursa2, editTextPBursa3, editTextPBursa4, editTextPBursa5, editTextResultadoPBursa);
                ind_bursal(editTextPcorporal1, editTextPBursa1, editTextIndiceBursal1);

            }
        });

        editTextPBursa2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBursa1, editTextPBursa2, editTextPBursa3, editTextPBursa4, editTextPBursa5, editTextResultadoPBursa);
                ind_bursal(editTextPcorporal2, editTextPBursa2, editTextIndiceBursal2);
            }
        });

        editTextPBursa3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBursa1, editTextPBursa2, editTextPBursa3, editTextPBursa4, editTextPBursa5, editTextResultadoPBursa);
                ind_bursal(editTextPcorporal3, editTextPBursa3, editTextIndiceBursal3);
            }
        });

        editTextPBursa4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBursa1, editTextPBursa2, editTextPBursa3, editTextPBursa4, editTextPBursa5, editTextResultadoPBursa);
                ind_bursal(editTextPcorporal4, editTextPBursa4, editTextIndiceBursal4);
            }
        });

        editTextPBursa5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBursa1, editTextPBursa2, editTextPBursa3, editTextPBursa4, editTextPBursa5, editTextResultadoPBursa);
                ind_bursal(editTextPcorporal5, editTextPBursa5, editTextIndiceBursal5);
            }
        });


        //sumatoria de EDITEXT P BRAZO
        editTextPBazo1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBazo1, editTextPBazo2, editTextPBazo3, editTextPBazo4, editTextPBazo5, editTextResultadoPBazo);

            }
        });
        editTextPBazo2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBazo1, editTextPBazo2, editTextPBazo3, editTextPBazo4, editTextPBazo5, editTextResultadoPBazo);

            }
        });
        editTextPBazo3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBazo1, editTextPBazo2, editTextPBazo3, editTextPBazo4, editTextPBazo5, editTextResultadoPBazo);

            }
        });
        editTextPBazo4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBazo1, editTextPBazo2, editTextPBazo3, editTextPBazo4, editTextPBazo5, editTextResultadoPBazo);

            }
        });
        editTextPBazo5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPBazo1, editTextPBazo2, editTextPBazo3, editTextPBazo4, editTextPBazo5, editTextResultadoPBazo);

            }
        });
//sumatoria de EDIT TEXT TIMO
        editTextPTimo1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPTimo1, editTextPTimo2, editTextPTimo3, editTextPTimo4, editTextPTimo5, editTextResultadoPTimo);
                ind_bursal(editTextPcorporal1, editTextPTimo1, editTextIndiceTimico1);
            }
        });

        editTextPTimo2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPTimo1, editTextPTimo2, editTextPTimo3, editTextPTimo4, editTextPTimo5, editTextResultadoPTimo);
                ind_bursal(editTextPcorporal2, editTextPTimo2, editTextIndiceTimico2);
            }
        });

        editTextPTimo3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPTimo1, editTextPTimo2, editTextPTimo3, editTextPTimo4, editTextPTimo5, editTextResultadoPTimo);
                ind_bursal(editTextPcorporal3, editTextPTimo3, editTextIndiceTimico3);
            }
        });


        editTextPTimo4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPTimo1, editTextPTimo2, editTextPTimo3, editTextPTimo4, editTextPTimo5, editTextResultadoPTimo);
                ind_bursal(editTextPcorporal4, editTextPTimo4, editTextIndiceTimico4);
            }
        });


        editTextPTimo5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPTimo1, editTextPTimo2, editTextPTimo3, editTextPTimo4, editTextPTimo5, editTextResultadoPTimo);
                ind_bursal(editTextPcorporal5, editTextPTimo5, editTextIndiceTimico5);
            }
        });
//sumatoria de EDIT TEXT HIGADO
        editTextPHigado1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPHigado1, editTextPHigado2, editTextPHigado3, editTextPHigado4, editTextPHigado5, editTextResultadoPHigado);
                ind_hepatico(editTextPcorporal1, editTextPHigado1, editTextIndiceHepatico1);
            }
        });
        editTextPHigado2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPHigado1, editTextPHigado2, editTextPHigado3, editTextPHigado4, editTextPHigado5, editTextResultadoPHigado);
                ind_hepatico(editTextPcorporal2, editTextPHigado2, editTextIndiceHepatico2);
            }
        });
        editTextPHigado3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPHigado1, editTextPHigado2, editTextPHigado3, editTextPHigado4, editTextPHigado5, editTextResultadoPHigado);
                ind_hepatico(editTextPcorporal3, editTextPHigado3, editTextIndiceHepatico3);
            }
        });
        editTextPHigado4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPHigado1, editTextPHigado2, editTextPHigado3, editTextPHigado4, editTextPHigado5, editTextResultadoPHigado);
                ind_hepatico(editTextPcorporal4, editTextPHigado4, editTextIndiceHepatico4);
            }
        });
        editTextPHigado5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextPHigado1, editTextPHigado2, editTextPHigado3, editTextPHigado4, editTextPHigado5, editTextResultadoPHigado);
                ind_hepatico(editTextPcorporal5, editTextPHigado5, editTextIndiceHepatico5);
            }
        });


//sumatoria de EDIT TEXT INDICE BRUSCAL
        editTextIndiceBursal1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceBursal1, editTextIndiceBursal2, editTextIndiceBursal3, editTextIndiceBursal4, editTextIndiceBursal5, editTextResultadoIndiceBursal);

            }
        });
        editTextIndiceBursal2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceBursal1, editTextIndiceBursal2, editTextIndiceBursal3, editTextIndiceBursal4, editTextIndiceBursal5, editTextResultadoIndiceBursal);

            }
        });
        editTextIndiceBursal3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceBursal1, editTextIndiceBursal2, editTextIndiceBursal3, editTextIndiceBursal4, editTextIndiceBursal5, editTextResultadoIndiceBursal);

            }
        });
        editTextIndiceBursal4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceBursal1, editTextIndiceBursal2, editTextIndiceBursal3, editTextIndiceBursal4, editTextIndiceBursal5, editTextResultadoIndiceBursal);

            }
        });
        editTextIndiceBursal5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceBursal1, editTextIndiceBursal2, editTextIndiceBursal3, editTextIndiceBursal4, editTextIndiceBursal5, editTextResultadoIndiceBursal);

            }
        });

//sumatoria de EDIT TEXT INDICE TIMICO
        editTextIndiceTimico1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceTimico1, editTextIndiceTimico2, editTextIndiceTimico3, editTextIndiceTimico4, editTextIndiceTimico5, editTextResultadoIndiceTimico);

            }
        });
        editTextIndiceTimico2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceTimico1, editTextIndiceTimico2, editTextIndiceTimico3, editTextIndiceTimico4, editTextIndiceTimico5, editTextResultadoIndiceTimico);

            }
        });
        editTextIndiceTimico3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceTimico1, editTextIndiceTimico2, editTextIndiceTimico3, editTextIndiceTimico4, editTextIndiceTimico5, editTextResultadoIndiceTimico);

            }
        });
        editTextIndiceTimico4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceTimico1, editTextIndiceTimico2, editTextIndiceTimico3, editTextIndiceTimico4, editTextIndiceTimico5, editTextResultadoIndiceTimico);

            }
        });
        editTextIndiceTimico5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceTimico1, editTextIndiceTimico2, editTextIndiceTimico3, editTextIndiceTimico4, editTextIndiceTimico5, editTextResultadoIndiceTimico);

            }
        });

//sumatoria de EDIT TEXT INDICE HEPATICO
        editTextIndiceHepatico1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceHepatico1, editTextIndiceHepatico2, editTextIndiceHepatico3, editTextIndiceHepatico4, editTextIndiceHepatico5, editTextResultadoIndiceHepatico);

            }
        });
        editTextIndiceHepatico2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceHepatico1, editTextIndiceHepatico2, editTextIndiceHepatico3, editTextIndiceHepatico4, editTextIndiceHepatico5, editTextResultadoIndiceHepatico);

            }
        });
        editTextIndiceHepatico3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceHepatico1, editTextIndiceHepatico2, editTextIndiceHepatico3, editTextIndiceHepatico4, editTextIndiceHepatico5, editTextResultadoIndiceHepatico);

            }
        });
        editTextIndiceHepatico4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceHepatico1, editTextIndiceHepatico2, editTextIndiceHepatico3, editTextIndiceHepatico4, editTextIndiceHepatico5, editTextResultadoIndiceHepatico);

            }
        });
        editTextIndiceHepatico5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextIndiceHepatico1, editTextIndiceHepatico2, editTextIndiceHepatico3, editTextIndiceHepatico4, editTextIndiceHepatico5, editTextResultadoIndiceHepatico);

            }
        });


//sumatoria de EDIT TEXT BURSO METRO
        editTextBursometro1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextBursometro1, editTextBursometro2, editTextBursometro3, editTextBursometro4, editTextBursometro5, editTextResultadoBursometro);
            }
        });
        editTextBursometro2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextBursometro1, editTextBursometro2, editTextBursometro3, editTextBursometro4, editTextBursometro5, editTextResultadoBursometro);

            }
        });
        editTextBursometro3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextBursometro1, editTextBursometro2, editTextBursometro3, editTextBursometro4, editTextBursometro5, editTextResultadoBursometro);

            }
        });
        editTextBursometro4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextBursometro1, editTextBursometro2, editTextBursometro3, editTextBursometro4, editTextBursometro5, editTextResultadoBursometro);

            }
        });
        editTextBursometro5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado


            }

            @Override
            public void afterTextChanged(Editable editable) {

                sumar_tabla(editTextBursometro1, editTextBursometro2, editTextBursometro3, editTextBursometro4, editTextBursometro5, editTextResultadoBursometro);

            }
        });


//auto complentos de TEXTO AUTOCOOMPLENT...
        autoEmpresa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {//antes q haya cambiado el texto

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//En el texto cambiado

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String id_empresa = get_id_tabla("select id from empresa where nombre='" + autoEmpresa.getText().toString() + "'");
                if (id_empresa.length() > 0) {
                    cargar_compania_en_la_lista("select nombre from granja where id_empresa=" + id_empresa, sp_granja);
                    cargar_compania_en_la_lista("select zona from granja where id_empresa=" + id_empresa, sp_zona);
                }
            }
        });
        sp_granja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String id_empresa = get_id_tabla("select id from empresa where nombre='" + autoEmpresa.getText().toString() + "'");
                String id_granja = get_id_tabla("select id from granja where nombre='" + sp_granja.getSelectedItem().toString() + "' and id_empresa='" + id_empresa + "'");

                if (id_empresa.length() > 0 && id_granja.length() > 0) {
                    cargar_compania_en_la_lista("select codigo from galpon where id_granja=" + id_granja, sp_galpon);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_galpon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String id_empresa = get_id_tabla("select id from empresa where nombre='" + autoEmpresa.getText().toString() + "'");
                String id_granja = get_id_tabla("select id from granja where nombre='" + sp_granja.getSelectedItem().toString() + "' and id_empresa='" + id_empresa + "'");

                if (id_empresa.length() > 0 && id_granja.length() > 0) {
                    cargar_compania_en_la_lista("select zona from granja where id=" + id_granja, sp_zona);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //---------------------------CODIGO PARA LOS DIALOGOS DE HORA Y FECHA---------------------------------
        fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show Date dialog
                showDialog(Date_id);
            }
        });


//obtener IMEI
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();

        if (imei.equals("") == true) {
            mensaje_ok_cerrar("Necesita dar permisos para Obtener el número de Imei.");
        }
    }//fin oncreate

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

    private void guardar_formulario() {
        boolean sw_sim = false, sw_verificar_datos = false;

        int id_SIM = id_Sistema_Integral() + 1;
        if (id_SIM == 1) {
            SharedPreferences perfil = getSharedPreferences("perfil", MODE_PRIVATE);
            id_SIM = perfil.getInt("id_sistema_integral", 1);
        }


        int id_empresa = Integer.parseInt(get_id_tabla("select id from empresa where nombre='" + autoEmpresa.getText().toString() + "'"));
        int id_granja = -1;
        int id_galpon = -1;
        try {
            id_granja = Integer.parseInt(get_id_tabla("select id from granja where nombre='" + sp_granja.getSelectedItem().toString() + "' and id_empresa='" + id_empresa + "'"));
        } catch (Exception e) {
            id_granja = -1;
        }
        try {
            id_galpon = Integer.parseInt(get_id_tabla("select id from galpon where codigo='" + sp_galpon.getSelectedItem().toString() + "' and id_empresa='" + id_empresa + "'and id_granja='" + id_granja + "'"));
        } catch (Exception e) {
            id_galpon = -1;
        }

        //id TECNICO...
        SharedPreferences perfil = getSharedPreferences("perfil", MODE_PRIVATE);
        int id_tecnico = perfil.getInt("id", -1);
        sw_verificar_datos = verificar_datos_sim(id_empresa, id_granja, id_galpon, id_tecnico, imei, bm_firma_1, bm_firma_2);
        if (sw_verificar_datos) {
            String DIRECCION_FIRMA_JEFE = "Invetsa/SIM/" + id_SIM + "_jefe_" + fecha.getText() + ".jpeg";
            String DIRECCION_FIRMA_INVETSA = "Invetsa/SIM/" + id_SIM + "_invetsa_" + fecha.getText() + ".jpeg";
            String DIRECCION_IMAGEN_1 = "Invetsa/SIM/" + id_SIM + "_imagen_1_" + fecha.getText() + ".jpeg";
            String DIRECCION_IMAGEN_2 = "Invetsa/SIM/" + id_SIM + "_imagen_2_" + fecha.getText() + ".jpeg";
            String DIRECCION_IMAGEN_3 = "Invetsa/SIM/" + id_SIM + "_imagen_3_" + fecha.getText() + ".jpeg";
            String DIRECCION_IMAGEN_4 = "Invetsa/SIM/" + id_SIM + "_imagen_4_" + fecha.getText() + ".jpeg";
            String DIRECCION_IMAGEN_5 = "Invetsa/SIM/" + id_SIM + "_imagen_5_" + fecha.getText() + ".jpeg";
            String DIRECCION_FOTO_JEFE = "Invetsa/SIM/" + id_SIM + "_foto_jefe_" + fecha.getText() + ".jpeg";


            guardar_en_memoria(bm_firma_1, id_SIM + "_jefe_" + fecha.getText() + ".jpeg", "Invetsa/SIM");
            guardar_en_memoria(bm_firma_2, id_SIM + "_invetsa_" + fecha.getText() + ".jpeg", "Invetsa/SIM");
            guardar_en_memoria(bm_foto_1, id_SIM + "_imagen_1_" + fecha.getText() + ".jpeg", "Invetsa/SIM");
            guardar_en_memoria(bm_foto_2, id_SIM + "_imagen_2_" + fecha.getText() + ".jpeg", "Invetsa/SIM");
            guardar_en_memoria(bm_foto_3, id_SIM + "_imagen_3_" + fecha.getText() + ".jpeg", "Invetsa/SIM");
            guardar_en_memoria(bm_foto_4, id_SIM + "_imagen_4_" + fecha.getText() + ".jpeg", "Invetsa/SIM");
            guardar_en_memoria(bm_foto_5, id_SIM + "_imagen_5_" + fecha.getText() + ".jpeg", "Invetsa/SIM");
            guardar_en_memoria(bm_foto_jefe, id_SIM + "_foto_jefe_" + fecha.getText() + ".jpeg", "Invetsa/SIM");

            String codigo = getString(R.string.codigo_sistema_integral_monitoreo);
            String revision = getString(R.string.revision_sistema_integral_monitoreo);

            sw_sim = guardar_Sistema_Integral(id_SIM, codigo, revision, Integer.parseInt(edad.getSelectedItem().toString()), sexo.getSelectedItem().toString(), observaciones.getText().toString(), comentarios.getText().toString(), DIRECCION_IMAGEN_1, DIRECCION_IMAGEN_2, DIRECCION_IMAGEN_3, DIRECCION_IMAGEN_4, DIRECCION_IMAGEN_5, fecha.getText().toString(), Integer.parseInt(nro_pollos.getText().toString()), id_galpon, id_granja, id_empresa, DIRECCION_FIRMA_JEFE, DIRECCION_FIRMA_INVETSA, id_tecnico, DIRECCION_FOTO_JEFE);
            //guardar_peso(1,Double.parseDouble(editTextPcorporal1.getText().toString()),Double.parseDouble(editTextPBursa1.getText().toString()),Double.parseDouble(editTextPBazo1.getText().toString()),Double.parseDouble(editTextPTimo1.getText().toString()),Double.parseDouble(editTextPHigado1.getText().toString()),Double.parseDouble(editTextIndiceBursal1.getText().toString()),Double.parseDouble(editTextIndiceTimico1.getText().toString()),Double.parseDouble(editTextIndiceHepatico1.getText().toString()),Double.parseDouble(editTextBursometro1.getText().toString()),id_SIM);
            if (sw_sim) {
                guardar_peso(1, Double.parseDouble(editTextPcorporal1.getText().toString()), Double.parseDouble(editTextPBursa1.getText().toString()), Double.parseDouble(editTextPBazo1.getText().toString()), Double.parseDouble(editTextPTimo1.getText().toString()), Double.parseDouble(editTextPHigado1.getText().toString()), Double.parseDouble(editTextIndiceBursal1.getText().toString()), Double.parseDouble(editTextIndiceTimico1.getText().toString()), Double.parseDouble(editTextIndiceHepatico1.getText().toString()), Double.parseDouble(editTextBursometro1.getText().toString()), id_SIM);
                guardar_peso(2, Double.parseDouble(editTextPcorporal2.getText().toString()), Double.parseDouble(editTextPBursa2.getText().toString()), Double.parseDouble(editTextPBazo2.getText().toString()), Double.parseDouble(editTextPTimo2.getText().toString()), Double.parseDouble(editTextPHigado2.getText().toString()), Double.parseDouble(editTextIndiceBursal2.getText().toString()), Double.parseDouble(editTextIndiceTimico2.getText().toString()), Double.parseDouble(editTextIndiceHepatico2.getText().toString()), Double.parseDouble(editTextBursometro2.getText().toString()), id_SIM);
                guardar_peso(3, Double.parseDouble(editTextPcorporal3.getText().toString()), Double.parseDouble(editTextPBursa3.getText().toString()), Double.parseDouble(editTextPBazo3.getText().toString()), Double.parseDouble(editTextPTimo3.getText().toString()), Double.parseDouble(editTextPHigado3.getText().toString()), Double.parseDouble(editTextIndiceBursal3.getText().toString()), Double.parseDouble(editTextIndiceTimico3.getText().toString()), Double.parseDouble(editTextIndiceHepatico3.getText().toString()), Double.parseDouble(editTextBursometro3.getText().toString()), id_SIM);
                guardar_peso(4, Double.parseDouble(editTextPcorporal4.getText().toString()), Double.parseDouble(editTextPBursa4.getText().toString()), Double.parseDouble(editTextPBazo4.getText().toString()), Double.parseDouble(editTextPTimo4.getText().toString()), Double.parseDouble(editTextPHigado4.getText().toString()), Double.parseDouble(editTextIndiceBursal4.getText().toString()), Double.parseDouble(editTextIndiceTimico4.getText().toString()), Double.parseDouble(editTextIndiceHepatico4.getText().toString()), Double.parseDouble(editTextBursometro4.getText().toString()), id_SIM);
                guardar_peso(5, Double.parseDouble(editTextPcorporal5.getText().toString()), Double.parseDouble(editTextPBursa5.getText().toString()), Double.parseDouble(editTextPBazo5.getText().toString()), Double.parseDouble(editTextPTimo5.getText().toString()), Double.parseDouble(editTextPHigado5.getText().toString()), Double.parseDouble(editTextIndiceBursal5.getText().toString()), Double.parseDouble(editTextIndiceTimico5.getText().toString()), Double.parseDouble(editTextIndiceHepatico5.getText().toString()), Double.parseDouble(editTextBursometro5.getText().toString()), id_SIM);
                guardar_peso(6, Double.parseDouble(editTextResultadoPcorporal.getText().toString()), Double.parseDouble(editTextResultadoPBursa.getText().toString()), Double.parseDouble(editTextResultadoPBazo.getText().toString()), Double.parseDouble(editTextResultadoPTimo.getText().toString()), Double.parseDouble(editTextResultadoPHigado.getText().toString()), Double.parseDouble(editTextResultadoIndiceBursal.getText().toString()), Double.parseDouble(editTextResultadoIndiceTimico.getText().toString()), Double.parseDouble(editTextResultadoIndiceHepatico.getText().toString()), Double.parseDouble(editTextResultadoBursometro.getText().toString()), id_SIM);

                guardar_Puntuacion(1, "Pico(aftas)", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Comisura", Integer.parseInt(comisura.getText().toString()), 1, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Paladar Duro", Integer.parseInt(paladar_duro.getText().toString()), 1, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Paladar Blando", Integer.parseInt(paladar_blando.getText().toString()), 1, id_SIM);
                guardar_Detalle_Puntuacion_SIM(4, "Necrosis de la Punta de la Lengua", Integer.parseInt(necrosis_lengua.getText().toString()), 1, id_SIM);

                guardar_Puntuacion(2, "Petequias", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Pechuga", Integer.parseInt(pechuga.getText().toString()), 2, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Pierna", Integer.parseInt(pierna.getText().toString()), 2, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Esternon", Integer.parseInt(esternon.getText().toString()), 2, id_SIM);

                guardar_Puntuacion(3, "Tarsos", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Fragil", Integer.parseInt(fragil.getText().toString()), 3, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Flexible", Integer.parseInt(flexible.getText().toString()), 3, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Duro", Integer.parseInt(duro.getText().toString()), 3, id_SIM);

                guardar_Puntuacion(4, "Relacion Morfomotrica Bursa/Bazo", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "A favor de la Bursa", Integer.parseInt(favor_bursa.getText().toString()), 4, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "A favor de la Bursa", Integer.parseInt(favor_bazo.getText().toString()), 4, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Relacion 1/1", Integer.parseInt(relacion11.getText().toString()), 4, id_SIM);

                guardar_Puntuacion(5, "Apariencia de los Pliegues Internos de la Bursa", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normal", Integer.parseInt(normal_apariencia.getText().toString()), 5, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Anormal", Integer.parseInt(anormal_apariencia.getText().toString()), 5, id_SIM);

                guardar_Puntuacion(6, "Tamano y Apariencia del Timo", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normal", Integer.parseInt(normal_timo.getText().toString()), 6, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Normal", Integer.parseInt(anormal_timo.getText().toString()), 6, id_SIM);

                guardar_Puntuacion(7, "Sacos Aereos", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normales", Integer.parseInt(normales_sacos.getText().toString()), 7, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Aerosaculitis Abdominal", Integer.parseInt(aero_abdominal.getText().toString()), 7, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Aerosaculitis Toracica", Integer.parseInt(aero_toracica.getText().toString()), 7, id_SIM);

                guardar_Puntuacion(8, "Traquea", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normal", Integer.parseInt(normal_traquea.getText().toString()), 8, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Congestionada", Integer.parseInt(congestionada.getText().toString()), 8, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Hemorragica", Integer.parseInt(hemorragica.getText().toString()), 8, id_SIM);
                guardar_Detalle_Puntuacion_SIM(4, "Con Mucosidad", Integer.parseInt(mucosidad.getText().toString()), 8, id_SIM);

                guardar_Puntuacion(9, "Cornetes Nasales", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normal", Integer.parseInt(normal_cornetes.getText().toString()), 9, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Congestionado", Integer.parseInt(congestionado_cornetes.getText().toString()), 9, id_SIM);

                guardar_Puntuacion(10, "Higados", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normal", Integer.parseInt(normal_higado.getText().toString()), 10, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Inflamado", Integer.parseInt(inflamado_higado.getText().toString()), 10, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Palido Friable", Integer.parseInt(palido_friable.getText().toString()), 10, id_SIM);
                guardar_Detalle_Puntuacion_SIM(4, "Moteado", Integer.parseInt(moteado_higado.getText().toString()), 10, id_SIM);
                guardar_Detalle_Puntuacion_SIM(5, "Punto de exudado", Integer.parseInt(puntoExudado_higado.getText().toString()), 10, id_SIM);

                guardar_Puntuacion(11, "Vesicula Biliar", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "LLena", Integer.parseInt(llena_vesicula.getText().toString()), 11, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Vacia", Integer.parseInt(vacia_vesicula.getText().toString()), 11, id_SIM);

                guardar_Puntuacion(12, "Proventriculo", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normal", Integer.parseInt(normal_proventriculo.getText().toString()), 12, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Proventriculitis", Integer.parseInt(proventriculitis.getText().toString()), 12, id_SIM);

                guardar_Puntuacion(13, "Ulceracion de Mollejas", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "0", Integer.parseInt(grado_0.getText().toString()), 13, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "1", Integer.parseInt(grado_1.getText().toString()), 13, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "2", Integer.parseInt(grado_2.getText().toString()), 13, id_SIM);
                guardar_Detalle_Puntuacion_SIM(4, "3", Integer.parseInt(grado_3.getText().toString()), 13, id_SIM);

                guardar_Puntuacion(14, "Intestinos", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normal", Integer.parseInt(normal_intestino.getText().toString()), 14, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Enteritis (yeyuno)", Integer.parseInt(enteritis_yeyuno.getText().toString()), 14, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Equimosis", Integer.parseInt(equimosis.getText().toString()), 14, id_SIM);
                guardar_Detalle_Puntuacion_SIM(4, "Desp. Mucosa", Integer.parseInt(desp_mucosa.getText().toString()), 14, id_SIM);
                guardar_Detalle_Puntuacion_SIM(5, "Enteritis mucoide", Integer.parseInt(enteritis_mucoide.getText().toString()), 14, id_SIM);
                guardar_Detalle_Puntuacion_SIM(6, "Transito Rapido", Integer.parseInt(transito_rapido.getText().toString()), 14, id_SIM);
                guardar_Detalle_Puntuacion_SIM(7, "Gas/espuma", Integer.parseInt(gas_espuma.getText().toString()), 14, id_SIM);

                guardar_Puntuacion(15, "Coccidia", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Acervulina", Integer.parseInt(acervulina.getText().toString()), 15, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Maxima", Integer.parseInt(maxima.getText().toString()), 15, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Tenella", Integer.parseInt(tenella.getText().toString()), 15, id_SIM);
                guardar_Detalle_Puntuacion_SIM(4, "Necrafix", Integer.parseInt(necrafix.getText().toString()), 15, id_SIM);

                guardar_Puntuacion(16, "Ciegos", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Normal", Integer.parseInt(normal_ciegos.getText().toString()), 16, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Tiflitis Erosiva", Integer.parseInt(tiflitis_erosiva.getText().toString()), 16, id_SIM);
                guardar_Detalle_Puntuacion_SIM(3, "Placas diftericas", Integer.parseInt(placas_diftericas.getText().toString()), 16, id_SIM);
                guardar_Detalle_Puntuacion_SIM(4, "Gaseosos(cont)", Integer.parseInt(gaseosos.getText().toString()), 16, id_SIM);
                guardar_Detalle_Puntuacion_SIM(5, "Liquido(cont)", Integer.parseInt(liquido.getText().toString()), 16, id_SIM);
                guardar_Detalle_Puntuacion_SIM(6, "Sangre(cont)", Integer.parseInt(sangre.getText().toString()), 16, id_SIM);

                guardar_Puntuacion(17, "Tonsillas lleocecales", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Reactiva", Integer.parseInt(reactiva_tonsillas.getText().toString()), 17, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "No reactiva", Integer.parseInt(no_reactiva_tonsillas.getText().toString()), 17, id_SIM);

                guardar_Puntuacion(18, "Placa de Peyer", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Reactiva", Integer.parseInt(reactiva_placas.getText().toString()), 18, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "", Integer.parseInt(no_reactiva_placas.getText().toString()), 18, id_SIM);

                guardar_Puntuacion(19, "Necrosis cabeza de Femur", id_SIM);
                guardar_Detalle_Puntuacion_SIM(1, "Presencia", Integer.parseInt(presencia.getText().toString()), 19, id_SIM);
                guardar_Detalle_Puntuacion_SIM(2, "Ausencia", Integer.parseInt(ausencia.getText().toString()), 19, id_SIM);
            }
            if (sw_sim) {
                mensaje_ok_cerrar("Se guardo Correctamente.");
                //SistemaIntegralMonitoreo.this.finish();
            } else {
                mensaje_ok_error("Por favor complete los campos.");
            }
        }
    }

    private boolean verificar_datos_sim(int id_empresa, int id_granja, int id_galpon, int id_tecnico, String imei, Bitmap firma_1_empresa, Bitmap firma_2_invetsa) {
        boolean sw = true;
        if (id_empresa == -1) {
            sw = false;
            mensaje_ok_error("Por favor complete el campo de  " + Html.fromHtml("<b>EMPRESA</b>"));
        } else if (id_granja == -1) {
            sw = false;
            mensaje_ok_error("No existe ninguna " + Html.fromHtml("<b>GRANJA</b><br>") + " de la empresa selecionada en la Base de Datos del Celular." + Html.fromHtml("<b><br>ACTUALICE LOS DATOS DEL CELULAR</b>") + "");
        } else if (id_galpon == -1) {
            sw = false;
            mensaje_ok_error("No existe ningun " + Html.fromHtml("<b>GALPON</b><br>") + " de la empresa selecionada en la Base de Datos del Celular." + Html.fromHtml("<b><br>ACTUALICE LOS DATOS DEL CELULAR</b>") + "");
        } else if (firma_1_empresa == null) {
            sw = false;
            mensaje_ok_error("Se requiere la firma del " + Html.fromHtml("<br><b>JEFE DE PLANATA DE INCUBACIÓN.</b>"));
        } else if (firma_2_invetsa == null) {
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
        AlertDialog.Builder builder_dialogo = new AlertDialog.Builder(this);

        final LayoutInflater inflater = getLayoutInflater();

        final View dialoglayout = inflater.inflate(R.layout.mensaje_ok, null);
        final TextView tv_mensaje = (TextView) dialoglayout.findViewById(R.id.tv_mensaje);
        Button bt_ok = (Button) dialoglayout.findViewById(R.id.bt_ok);

        tv_mensaje.setText(mensaje);

        builder_dialogo.setView(dialoglayout);
        alertDialog_firmar_1 = builder_dialogo.create();
        alertDialog_firmar_1.show();


        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    alertDialog_firmar_1.dismiss();
                } catch (Exception e) {
                    alertDialog_firmar_1.hide();
                }
            }
        });
    }

    private void mensaje_ok_cerrar(String mensaje) {
        AlertDialog.Builder builder_dialogo = new AlertDialog.Builder(this);
        builder_dialogo.setCancelable(false);

        final LayoutInflater inflater = getLayoutInflater();

        final View dialoglayout = inflater.inflate(R.layout.mensaje_ok, null);
        final TextView tv_mensaje = (TextView) dialoglayout.findViewById(R.id.tv_mensaje);
        Button bt_ok = (Button) dialoglayout.findViewById(R.id.bt_ok);

        tv_mensaje.setText(mensaje);

        builder_dialogo.setView(dialoglayout);
        alertDialog_firmar_1 = builder_dialogo.create();
        alertDialog_firmar_1.show();


        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    alertDialog_firmar_1.dismiss();
                    finish();
                } catch (Exception e) {
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //ubicacion de la imagen
            Bitmap img_cargar;
            String uploadImage;

            switch (requestCode) {
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

                    if (numero_camara == 1) {
                        bm_foto_1 = bitmap;
                        im_foto_1.setImageBitmap(bitmap);
                        im_foto_1.setPadding(0, 0, 0, 0);
                        im_foto_1.setAdjustViewBounds(true);
                        im_foto_1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        nombre_foto1 = nombre_temporal;
                        im_foto_2.setVisibility(View.VISIBLE);
                    } else if (numero_camara == 2) {
                        bm_foto_2 = bitmap;
                        im_foto_2.setImageBitmap(bitmap);
                        im_foto_2.setPadding(0, 0, 0, 0);
                        im_foto_2.setAdjustViewBounds(true);
                        im_foto_2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        nombre_foto2 = nombre_temporal;
                        im_foto_3.setVisibility(View.VISIBLE);
                    } else if (numero_camara == 3) {
                        bm_foto_3 = bitmap;
                        im_foto_3.setImageBitmap(bitmap);
                        im_foto_3.setPadding(0, 0, 0, 0);
                        im_foto_3.setAdjustViewBounds(true);
                        im_foto_3.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        nombre_foto3 = nombre_temporal;
                        im_foto_4.setVisibility(View.VISIBLE);
                    } else if (numero_camara == 4) {
                        bm_foto_4 = bitmap;
                        im_foto_4.setImageBitmap(bitmap);
                        im_foto_4.setPadding(0, 0, 0, 0);
                        im_foto_4.setAdjustViewBounds(true);
                        im_foto_4.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        nombre_foto4 = nombre_temporal;
                        im_foto_5.setVisibility(View.VISIBLE);
                    } else if (numero_camara == 5) {
                        bm_foto_5 = bitmap;
                        im_foto_5.setImageBitmap(bitmap);
                        im_foto_5.setPadding(0, 0, 0, 0);
                        im_foto_5.setAdjustViewBounds(true);
                        im_foto_5.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        nombre_foto5 = nombre_temporal;
                    } else if (numero_camara == 6) {
                        bm_foto_jefe = bitmap;
                        im_foto_jefe.setImageBitmap(bitmap);
                        im_foto_jefe.setPadding(0, 0, 0, 0);
                        im_foto_jefe.setAdjustViewBounds(true);
                        im_foto_jefe.setScaleType(ImageView.ScaleType.FIT_CENTER);

                    }

                    //imagen cuadrado
                    //subir imagen de perfil al servidor...

                    //Convertir Bitmap a Drawable.


                    break;
                case SELECT_PICTURE:
                    path = data.getData();
                    try {//convertir Uri a BitMap
                        Bitmap tempBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(path));


                    } catch (Exception e) {

                    }
                    // perfil.setImageURI(path);
                    //  perfil.setImageDrawable(d);
                    break;

            }
        }

    }

    //Cargar Datos en los Spinner
    private void cargar_compania_en_la_lista(String sql, Spinner spinner) {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Cursor fila = bd.rawQuery("select * from compania ", null);
        Cursor fila = bd.rawQuery(sql, null);
        String[] lista = new String[fila.getCount()];
        int i = 0;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                String nombre = fila.getString(0);
                lista[i] = nombre;
                i++;
            } while (fila.moveToNext());

        } else {

        }
        bd.close();
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            spinner.setAdapter(adapter);

        } catch (Exception e) {

        }
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
                return new DatePickerDialog(SistemaIntegralMonitoreo.this, date_listener, year,
                        month, day);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(day);
            fecha.setText(date1);
        }
    };
//-----------------------HASTA AQUI CODIGO PARA LOS DIALOGOS PARA HORA - FECHA------------------------

    private void cargar_empresa_en_la_lista() {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from empresa ", null);
        String[] empresa = new String[fila.getCount()];
        int i = 0;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {

                String id = fila.getString(0);
                String nombre = fila.getString(1);
                empresa[i] = nombre;
                i++;
            } while (fila.moveToNext());

        } else {

        }
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, empresa);
        autoEmpresa.setAdapter(adapter);
    }


    private String get_id_tabla(String s) {
        SQLite admin = new SQLite(this, "invetsa", null, 1);

        String id = "-1";
        try {
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery(s, null);

            if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
                id = fila.getString(0);
            }
            bd.close();
        } catch (Exception e) {
            id = "-1";
        }

        return id;
    }


    private int id_Sistema_Integral() {
        int id = 0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from sistema_integral order by id desc ", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


            String codigo = fila.getString(0);
            try {
                if (codigo.equals("") == false && codigo.equals("0") == false && codigo.equals("null") == false) {
                    id = Integer.parseInt(codigo);
                }
            } catch (Exception e) {
                Log.e("id sistema  integral ", "" + e);
            }

        }
        bd.close();
        return id;
    }

    public boolean guardar_Sistema_Integral(int id, String codigo, String revision, int edad, String Sexo, String observaciones, String comentarios, String imagen1, String imagen2, String imagen3, String imagen4, String imagen5, String fecha, int nro_pollos, int id_galpon, int id_granja, int id_empresa, String firma_jefe, String firma_invetsa, int id_tecnico, String imagen_jefe) {
        boolean sw = false;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("codigo", codigo);
            registro.put("revision", revision);
            registro.put("edad", String.valueOf(edad));
            registro.put("sexo", Sexo);
            registro.put("observaciones", observaciones);
            registro.put("comentarios", comentarios);
            registro.put("imagen1", imagen1);
            registro.put("imagen2", imagen2);
            registro.put("imagen3", imagen3);
            registro.put("imagen4", imagen4);
            registro.put("imagen5", imagen5);
            registro.put("fecha", fecha);
            registro.put("nro_pollos", String.valueOf(nro_pollos));
            registro.put("id_galpon", String.valueOf(id_galpon));
            registro.put("id_granja", String.valueOf(id_granja));
            registro.put("id_empresa", String.valueOf(id_empresa));
            registro.put("imei", imei);
            registro.put("firma_invetsa", firma_invetsa);
            registro.put("firma_empresa", firma_jefe);
            registro.put("id_tecnico", String.valueOf(id_tecnico));
            registro.put("imagen_jefe", imagen_jefe);
            //registro.put("referencias",)
            bd.insert("sistema_integral", null, registro);

            sw = true;
        } catch (Exception e) {
            sw = false;
            Log.e("sql", "" + e);
        }

        bd.close();
        return sw;
    }

    public void guardar_peso(int id, double peso_corporal, double peso_bursa, double brazo, double peso_timo, double peso_higado, double indice_bursal, double indice_timico, double indice_hepatico, double bursometro, int id_sistema) {
        SQLite admin = new SQLite(this, "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("peso_corporal", String.valueOf(peso_corporal));
            registro.put("peso_bursa", String.valueOf(peso_bursa));
            registro.put("peso_brazo", String.valueOf(brazo));
            registro.put("peso_timo", String.valueOf(peso_timo));
            registro.put("peso_higado", String.valueOf(peso_higado));
            registro.put("indice_bursal", String.valueOf(indice_bursal));
            registro.put("indice_timico", String.valueOf(indice_timico));
            registro.put("indice_hepatico", String.valueOf(indice_hepatico));
            registro.put("bursometro", String.valueOf(bursometro));
            registro.put("id_sistema", String.valueOf(id_sistema));
            registro.put("imei", imei);
            bd.insert("peso", null, registro);

        } catch (Exception e) {
            Log.e("sql", "" + e);
        }

        bd.close();


    }

    public void guardar_Puntuacion(int id, String nombre, int id_sistema) {


        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("nombre", nombre);
            registro.put("id_sistema", String.valueOf(id_sistema));
            registro.put("imei", imei);
            bd.insert("puntuacion", null, registro);
        } catch (Exception e) {
            Log.e("sql", "" + e);
        }

        bd.close();
    }

    public void guardar_Detalle_Puntuacion_SIM(int id, String nombre, int estado, int id_puntuacion, int id_sistema) {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("nombre", nombre);
            registro.put("estado", String.valueOf(estado));
            registro.put("id_puntuacion", String.valueOf(id_puntuacion));
            registro.put("id_sistema", String.valueOf(id_sistema));
            registro.put("imei", imei);
            bd.insert("detalle_puntuacion", null, registro);

        } catch (Exception e) {
            Log.e("sql", "" + e);
        }

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

        if (requestCode == MY_PERMISSIONS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();

            }
        } else {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_foto_jefe:
                imagen_camara(6);
                break;
            case R.id.im_foto_1:
                imagen_camara(1);
                break;
            case R.id.im_foto_2:

                imagen_camara(2);
                break;
            case R.id.im_foto_3:
                imagen_camara(3);
                break;
            case R.id.im_foto_4:
                imagen_camara(4);
                break;
            case R.id.im_foto_5:
                imagen_camara(5);
                break;

            case R.id.bt_firmar_1:
                AlertDialog.Builder builder_dialogo = new AlertDialog.Builder(this);

                final LayoutInflater inflater = getLayoutInflater();

                final View dialoglayout = inflater.inflate(R.layout.firmar, null);
                final com.grayhatcorp.invetsa.invetsa.TouchView tc_view = (TouchView) dialoglayout.findViewById(R.id.tc_view);
                Button bt_borrar = (Button) dialoglayout.findViewById(R.id.bt_borrar);
                Button bt_listo = (Button) dialoglayout.findViewById(R.id.bt_listo);
                // Start the animation (looped playback by default).

                builder_dialogo.setView(dialoglayout);
                alertDialog_firmar_1 = builder_dialogo.create();
                alertDialog_firmar_1.show();


                bt_borrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FrameLayout savedImage2 = null;
                        savedImage2 = (FrameLayout) dialoglayout.findViewById(R.id.fl_view);
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
                        savedImage2 = (FrameLayout) dialoglayout.findViewById(R.id.fl_view);
                        savedImage2.setDrawingCacheEnabled(true);
                        savedImage2.buildDrawingCache();
                        bm_firma_1 = savedImage2.getDrawingCache();
                        guardar_en_memoria(bm_firma_1, "firma_1.jpeg", "Invetsa/SIM");
                        tc_view.limpiar();
                        savedImage2.removeAllViews();
                        savedImage2.addView(tc_view);
                        im_firma_1.setImageBitmap(bm_firma_1);
                        im_firma_1.setPadding(0, 0, 0, 0);
                        im_firma_1.setAdjustViewBounds(true);
                        im_firma_1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        alertDialog_firmar_1.hide();
                    }
                });
                break;

            case R.id.bt_firmar_2:
                AlertDialog.Builder builder_dialogo2 = new AlertDialog.Builder(this);

                final LayoutInflater inflater2 = getLayoutInflater();

                final View dialoglayout2 = inflater2.inflate(R.layout.firmar, null);
                final com.grayhatcorp.invetsa.invetsa.TouchView tc_view2 = (TouchView) dialoglayout2.findViewById(R.id.tc_view);
                Button bt_borrar2 = (Button) dialoglayout2.findViewById(R.id.bt_borrar);
                Button bt_listo2 = (Button) dialoglayout2.findViewById(R.id.bt_listo);
                // Start the animation (looped playback by default).

                builder_dialogo2.setView(dialoglayout2);
                alertDialog_firmar_2 = builder_dialogo2.create();
                alertDialog_firmar_2.show();


                bt_borrar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FrameLayout savedImage2 = null;
                        savedImage2 = (FrameLayout) dialoglayout2.findViewById(R.id.fl_view);
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
                        savedImage2 = (FrameLayout) dialoglayout2.findViewById(R.id.fl_view);
                        savedImage2.setDrawingCacheEnabled(true);
                        savedImage2.buildDrawingCache();
                        bm_firma_2 = savedImage2.getDrawingCache();
                        guardar_en_memoria(bm_firma_2, "firma_2.jpeg", "Invetsa/SIM");
                        tc_view2.limpiar();
                        savedImage2.removeAllViews();
                        savedImage2.addView(tc_view2);
                        im_firma_2.setImageBitmap(bm_firma_2);
                        im_firma_2.setPadding(0, 0, 0, 0);
                        im_firma_2.setAdjustViewBounds(true);
                        im_firma_2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        alertDialog_firmar_2.hide();
                    }
                });
                break;
        }
    }

    private void imagen_camara(int i) {
        numero_camara = i;

        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if (isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";
            nombre_temporal = imageName;

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    public void sumar_tabla(EditText numero1, EditText numero2, EditText numero3, EditText numero4, EditText numero5, EditText resultado) {
        try {
            double num1 = Double.parseDouble(numero1.getText().toString());
            double num2 = Double.parseDouble(numero2.getText().toString());
            double num3 = Double.parseDouble(numero3.getText().toString());
            double num4 = Double.parseDouble(numero4.getText().toString());
            double num5 = Double.parseDouble(numero5.getText().toString());
            resultado.setText(String.valueOf((num1 + num2 + num3 + num4 + num5) / 5));
            resultado.setText(String.format("%.2f", (num1 + num2 + num3 + num4 + num5) / 5));

        } catch (Exception e) {

        }
    }

    // la funcion bursal vale para indice bursal e indice tímico

    public void ind_bursal(EditText numero1, EditText numero2, EditText resultado) {
        double uno = 0, dos = 0;
        try {
            uno = Double.parseDouble(numero1.getText().toString());

        } catch (Exception e) {
            resultado.setText("0");
        }
        try {
            dos = Double.parseDouble(numero2.getText().toString());

        } catch (Exception e) {
            resultado.setText("0");
        }
        try {

            if ((dos == 0) && (uno != 0)) {
//                resultado.setText(String.format("%.2f", ((0 * 1000) /1)));
                resultado.setText(String.valueOf("0.00"));
            } else if ((dos != 0) && (uno == 0)) {
//                resultado.setText(String.format("%.2f", ((0 * 1000) /1)));
                resultado.setText(String.valueOf("0.00"));
            } else if ((dos == 0) && (uno == 0)) {
//                resultado.setText(String.format("%.2f", ((0 * 1000) /1)));
                resultado.setText(String.valueOf("0.00"));
            } else {
                resultado.setText(String.format("%.2f", ((dos * 1000) / uno)));
            }
        } catch (Exception e) {
            resultado.setText("0");
        }
    }
/* if ((Double.isNaN(uno)) && (Double.isNaN(dos))) {
        resultado.setText(String.valueOf(0.0));
    } else {
        resultado.setText(String.format("%.2f", ((dos * 1000) / uno)));
    }  */
//            resultado.setText(String.valueOf(33333));
//    Text.setText( String.format( "Value of a: %.2f", a ) );
    //resultado.setText(String.valueOf((dos*100)/uno));

    public void ind_hepatico(EditText numero1, EditText numero2, EditText resultado) {
        double uno = 0, dos = 0;
        try {
            uno = Double.parseDouble(numero1.getText().toString());

        } catch (Exception e) {
            resultado.setText("0");
        }
        try {
            dos = Double.parseDouble(numero2.getText().toString());
        } catch (Exception e) {
            resultado.setText("0");
        }
        try {

            if ((dos == 0) && (uno != 0)) {
                resultado.setText(String.valueOf(0.00));
            } else if ((dos != 0) && (uno == 0)) {
                resultado.setText(String.valueOf(0.00));
            } else if ((dos == 0) && (uno == 0)) {
                resultado.setText(String.valueOf(0.00));
            } else {
                resultado.setText(String.format("%.2f", ((dos * 100) / uno)));
            }

        } catch (Exception e) {
            resultado.setText("0");
        }
    }

    private void guardar_en_memoria(Bitmap bitmapImage, String nombre, String DIRECTORIO) {
        File file = null;
        FileOutputStream fos = null;
        try {
            // String APP_DIRECTORY = "Invetsa/";//nombre de directorio
            // String MEDIA_DIRECTORY = APP_DIRECTORY + "Firma";//nombre de la carpeta
            file = new File(Environment.getExternalStorageDirectory(), DIRECTORIO);
            File mypath = new File(file, nombre);//nombre del archivo imagen

            boolean isDirectoryCreated = file.exists();//pregunto si esxiste el directorio creado
            if (!isDirectoryCreated)
                isDirectoryCreated = file.mkdirs();

            if (isDirectoryCreated) {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            SistemaIntegralMonitoreo.this.finish();
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