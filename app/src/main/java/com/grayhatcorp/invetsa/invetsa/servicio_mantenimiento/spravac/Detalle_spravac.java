package com.grayhatcorp.invetsa.invetsa.servicio_mantenimiento.spravac;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.grayhatcorp.invetsa.invetsa.Base_de_datos.SQLite;
import com.grayhatcorp.invetsa.invetsa.R;
import com.grayhatcorp.invetsa.invetsa.TouchView;
import com.grayhatcorp.invetsa.invetsa.sistema_integral_monitoreo.SistemaIntegralMonitoreo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class Detalle_spravac extends AppCompatActivity implements View.OnClickListener {
    ArrayAdapter<String> adapter;

    //modificacion desde Rodrigo 2154545

    AlertDialog alertDialog_firmar_1,alertDialog_firmar_2;




    AutoCompleteTextView autoCompania,autoMaquina,autoDireccion;
    EditText encargador,ultima_visita;
    TextView fecha,hora_ingreso,hora_salida;
    EditText observacion_inspeccion,piezas_cambiadas_inspeccion,observacion_funcionamiento,frecuencia_uso_funcionamiento,observacion_desinfeccion,cantidad_aves_desinfeccion;

    Button bt_nuevo,bt_formulario;
    ImageButton ib_izquierda,ib_derecha;

    int id_spr=0;
    private static final int Date_id = 0;
    private static final int Time_id = 1;
    private static final int Time_id_salida = 2;

    RadioButton bueno_cubierta,regular_cubierta,malo_cubierta;
    RadioButton bueno_foski,regular_foski,malo_foski;
    RadioButton bueno_seguro_foski,regular_seguro_foski,malo_seguro_foski;
    RadioButton bueno_mesa,regular_mesa,malo_mesa;
    RadioButton bueno_seguro,regular_seguro,malo_seguro;
    RadioButton bueno_garruchas,regular_garruchas,malo_garruchas;
    RadioButton bueno_ensamblaje,regular_ensamblaje,malo_ensamblaje;
    RadioButton bueno_regulador_aire,regular_regulador_aire,malo_regulador_aire;
    RadioButton bueno_ensamblaje_jeringa,regular_ensamblaje_jeringa,malo_ensamblaje_jeringa;
    RadioButton bueno_manometro,regular_manometro,malo_manometro;
    RadioButton bueno_base_aluminio,regular_base_aluminio,malo_base_aluminio;
    RadioButton bueno_conector_rapido_5,regular_conector_rapido_5,malo_conector_rapido_5;
    RadioButton bueno_valvula_control,regular_valvula_control,malo_valvula_control;
    RadioButton bueno_conector_hembra,regular_conector_hembra,malo_conector_hembra;
    RadioButton bueno_repuesto_valvula,regular_repuesto_valvula,malo_repuesto_valvula;
    RadioButton bueno_conector_macho,regular_conector_macho,malo_conector_macho;
    RadioButton bueno_acopladura_rapida,regular_acopladura_rapida,malo_acopladura_rapida;
    RadioButton bueno_meniscos_calibradores,regular_meniscos_calibradores,malo_meniscos_calibradores;
    RadioButton bueno_seguro_estructura,regular_seguro_estructura,malo_seguro_estructura;
    RadioButton bueno_cilindro_fuerza,regular_cilindro_fuerza,malo_cilindro_fuerza;
    RadioButton bueno_jeringa_descartables,regular_jeringa_descartables,malo_jeringa_descartables;
    RadioButton bueno_sub_placa_asociable,regular_sub_placa_asociable,malo_sub_placa_asociable;
    RadioButton bueno_tuberia_polyvinico,regular_tuberia_polyvinico,malo_tuberia_polyvinico;
    RadioButton bueno_sub_placa_sencilla,regular_sub_placa_sencilla,malo_sub_placa_sencilla;
    RadioButton bueno_tuberia_pequeña,regular_tuberia_pequeña,malo_tuberia_pequeña;
    RadioButton bueno_frasco_nalgene,regular_frasco_nalgene,malo_frasco_nalgene;
    RadioButton bueno_tuberia_teflon,regular_tuberia_teflon,malo_tuberia_teflon;
    RadioButton bueno_acrilico_fijadores,regular_acrilico_fijadores,malo_acrilico_fijadores;
    RadioButton bueno_tuberia_latex,regular_tuberia_latex,malo_tuberia_latex;
    RadioButton bueno_ensamblaje_boquilla,regular_ensamblaje_boquilla,malo_ensamblaje_boquilla;
    RadioButton bueno_acopladora_90,regular_acopladora_90,malo_acopladora_90;
    RadioButton bueno_motor,regular_motor,malo_motor;
    RadioButton bueno_acopladora_tee,regular_acopladora_tee,malo_acopladora_tee;
    RadioButton bueno_boquilla,regular_boquilla,malo_boquilla;
    RadioButton bueno_reguladora_aire_compresora,regular_reguladora_aire_compresora,malo_reguladora_aire_compresora;
    RadioButton bueno_sostenedor_boquilla,regular_sostenedor_boquilla,malo_sostenedor_boquilla;
    RadioButton bueno_tanque_compresora,regular_tanque_compresora,malo_tanque_compresora;
    RadioButton bueno_clan_adhesivo,regular_clan_adhesivo,malo_clan_adhesivo;
    RadioButton bueno_ruedas,regular_ruedas,malo_ruedas;
    RadioButton bueno_logo_spravac,regular_logo_spravac,malo_logo_spravac;
    RadioButton bueno_cables_electricos,regular_cables_electricos,malo_cables_electricos;
    RadioButton bueno_cortinas,regular_cortinas,malo_cortinas;
    RadioButton bueno_valvula_purgar,regular_valvula_purgar,malo_valvula_purgar;
    RadioButton bueno_varillas,regular_varillas,malo_varillas;
    RadioButton bueno_manguera,regular_manguera,malo_manguera;
    RadioButton bueno_nivelacion,malo_nivelacion;
    RadioButton bueno_presion_compresora,malo_presion_compresora;
    RadioButton bueno_presion_maquina,malo_presion_maquina;
    RadioButton bueno_retiro_cajas,malo_retiro_cajas;
    RadioButton bueno_activacion_foski,malo_activacion_foski;
    RadioButton bueno_boquillas,malo_boquillas;
    RadioButton bueno_dispersion,malo_dispersion;
    RadioButton bueno_calibracion,malo_calibracion;
    RadioButton bueno_materiales_utlizados,malo_materiales_utlizados;
    RadioButton bueno_cambio,malo_cambio;
    RadioButton bueno_enjuague,malo_enjuague;
    RadioButton bueno_esterilizado,malo_esterilizado;
    RadioButton bueno_esterilizado_tuberia,malo_esterilizado_tuberia;
    RadioButton bueno_esterilizado_boquilla,malo_esterilizado_boquilla;
    RadioButton bueno_secado,malo_secado;
    RadioButton bueno_proteccion,malo_proteccion;

    Button bt_firmar_1,bt_firmar_2;
    ImageView im_firma_1,im_firma_2,im_foto_jefe;
    Bitmap bm_firma_1=null,bm_firma_2=null;
    String imei="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_spravac);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoCompania = (AutoCompleteTextView)findViewById(R.id.autoCompania);
        autoMaquina = (AutoCompleteTextView)findViewById(R.id.autoMaquina);
        autoDireccion=(AutoCompleteTextView)findViewById(R.id.autoDireccion);
        encargador=(EditText)findViewById(R.id.TextViewEncargado);

        hora_ingreso=(TextView)findViewById(R.id.et_hora_ingreso);
        hora_salida=(TextView)findViewById(R.id.et_hora_salida);
        fecha=(TextView) findViewById(R.id.fecha);

        ultima_visita=(EditText)findViewById(R.id.TextViewUltimaVisita);


        observacion_inspeccion=(EditText)findViewById(R.id.txtObservaciones_inspeccion);
        piezas_cambiadas_inspeccion=(EditText)findViewById(R.id.txtPiezasCambiadas_inspeccion);
        observacion_funcionamiento=(EditText)findViewById(R.id.txtObservaciones_funcionamiento);
        frecuencia_uso_funcionamiento=(EditText)findViewById(R.id.txtPiezasFrecuenciadeUso_funcionamiento);
        observacion_desinfeccion=(EditText)findViewById(R.id.txtObservaciones_desinfeccion);
        cantidad_aves_desinfeccion=(EditText)findViewById(R.id.txtAvesVacunadas_desinfeccion);



        bueno_cubierta=(RadioButton)findViewById(R.id.BuenoCubierta);
        //regular_cubierta=(RadioButton)findViewById(R.id.Regularcubierta);
        malo_cubierta=(RadioButton)findViewById(R.id.Malocubierta);

        bueno_foski=(RadioButton)findViewById(R.id.BuenoFoski);
        //regular_foski=(RadioButton)findViewById(R.id.RegularFoski);
        malo_foski=(RadioButton)findViewById(R.id.MaloFoski);

        bueno_mesa=(RadioButton)findViewById(R.id.BuenoMesa);
        //regular_mesa=(RadioButton)findViewById(R.id.RegularMesa);
        malo_mesa=(RadioButton)findViewById(R.id.MaloMesa);

        bueno_seguro=(RadioButton)findViewById(R.id.BuenoSeguroEstructura);
        //regular_seguro=(RadioButton)findViewById(R.id.RegularSeguroEstructura);
        malo_seguro=(RadioButton)findViewById(R.id.MaloSeguroEstructura);

        bueno_seguro_foski=(RadioButton)findViewById(R.id.BuenoSeguro_foski);
        //regular_seguro_foski=(RadioButton)findViewById(R.id.RegularSeguro_foski);
        malo_seguro_foski=(RadioButton)findViewById(R.id.MaloSeguro_foski);

        bueno_garruchas=(RadioButton)findViewById(R.id.BuenoGarruchas);
        //regular_garruchas=(RadioButton)findViewById(R.id.RegularGarruchas);
        malo_garruchas=(RadioButton)findViewById(R.id.MaloGarruchas);

        bueno_ensamblaje=(RadioButton)findViewById(R.id.BuenoEnsamblaje);
        //regular_ensamblaje=(RadioButton)findViewById(R.id.RegularEnsamblaje);
        malo_ensamblaje=(RadioButton)findViewById(R.id.MaloEnsamblaje);

        bueno_regulador_aire=(RadioButton)findViewById(R.id.BuenoReguladorAire);
        //regular_regulador_aire=(RadioButton)findViewById(R.id.RegularReguladorAire);
        malo_regulador_aire=(RadioButton)findViewById(R.id.MaloReguladorAire);

        bueno_ensamblaje_jeringa=(RadioButton)findViewById(R.id.BuenoEmsanblajeJeringa);
        //regular_ensamblaje_jeringa=(RadioButton)findViewById(R.id.RegularEmsanblajeJeringa);
        malo_ensamblaje_jeringa=(RadioButton)findViewById(R.id.MaloEmsanblajeJeringa);

        bueno_manometro=(RadioButton)findViewById(R.id.BuenoManometro);
        //regular_manometro=(RadioButton)findViewById(R.id.RegularManometro);
        malo_manometro=(RadioButton)findViewById(R.id.MaloMAnometro);

        bueno_base_aluminio=(RadioButton)findViewById(R.id.BuenoBaseAluminio);
        //regular_base_aluminio=(RadioButton)findViewById(R.id.RegularBaseAluminio);
        malo_base_aluminio=(RadioButton)findViewById(R.id.MaloBaseAluminio);

        bueno_conector_rapido_5=(RadioButton)findViewById(R.id.BuenoConectorRapido5);
        //regular_conector_rapido_5=(RadioButton)findViewById(R.id.RegularConectorRapido5);
        malo_conector_rapido_5=(RadioButton)findViewById(R.id.MaloConectorRapido5);

        bueno_valvula_control=(RadioButton)findViewById(R.id.BuenoValvuladeControl);
        //regular_valvula_control=(RadioButton)findViewById(R.id.RegularValvuladeControl);
        malo_valvula_control=(RadioButton)findViewById(R.id.MaloValvuladeControl);

        bueno_conector_hembra=(RadioButton)findViewById(R.id.BuenoConectorHembra);
        //regular_conector_hembra=(RadioButton)findViewById(R.id.RegularConectorHembra);
        malo_conector_hembra=(RadioButton)findViewById(R.id.MaloConectorHembra);

        bueno_repuesto_valvula=(RadioButton)findViewById(R.id.BuenoRepuestodeValvula);
        //regular_repuesto_valvula=(RadioButton)findViewById(R.id.RegularRepuestodeValvula);
        malo_repuesto_valvula=(RadioButton)findViewById(R.id.MaloRepuestodeValvula);

        bueno_repuesto_valvula=(RadioButton)findViewById(R.id.BuenoRepuestodeValvula);
        //regular_repuesto_valvula=(RadioButton)findViewById(R.id.RegularRepuestodeValvula);
        malo_repuesto_valvula=(RadioButton)findViewById(R.id.MaloRepuestodeValvula);

        bueno_conector_macho=(RadioButton)findViewById(R.id.BuenoConectorMacho);
        //regular_conector_macho=(RadioButton)findViewById(R.id.RegularConectorMacho);
        malo_conector_macho=(RadioButton)findViewById(R.id.MaloConectorMacho);

        bueno_acopladura_rapida=(RadioButton)findViewById(R.id.BuenoAcopladuraRapida);
        //regular_acopladura_rapida=(RadioButton)findViewById(R.id.RegularAcopladuraRapida);
        malo_acopladura_rapida=(RadioButton)findViewById(R.id.MaloAcopladuraRapida);

        bueno_meniscos_calibradores=(RadioButton)findViewById(R.id.BuenoMeniscosCalibradores);
        //regular_meniscos_calibradores=(RadioButton)findViewById(R.id.RegularMeniscosCalibradores);
        malo_meniscos_calibradores=(RadioButton)findViewById(R.id.MaloMeniscosCalibradores);

        bueno_seguro_estructura=(RadioButton)findViewById(R.id.BuenoSeguroEstructura);
        //regular_seguro_estructura=(RadioButton)findViewById(R.id.RegularSeguroEstructura);
        malo_seguro_estructura=(RadioButton)findViewById(R.id.MaloSeguroEstructura);

        bueno_cilindro_fuerza=(RadioButton)findViewById(R.id.BuenoCilindroFuerza);
        //regular_cilindro_fuerza=(RadioButton)findViewById(R.id.RegularCilindroFuerza);
        malo_cilindro_fuerza=(RadioButton)findViewById(R.id.MaloCilindroFuerza);

        bueno_jeringa_descartables=(RadioButton)findViewById(R.id.BuenoJeringasDescartables);
        //egular_jeringa_descartables=(RadioButton)findViewById(R.id.RegularJeringasDescartables);
        malo_jeringa_descartables=(RadioButton)findViewById(R.id.MaloJeringasDescartables);

        bueno_sub_placa_asociable=(RadioButton)findViewById(R.id.BuenoSUBPLACAASOCIABLE);
        //regular_sub_placa_asociable=(RadioButton)findViewById(R.id.RegularSUBPLACAASOCIABLE);
        malo_sub_placa_asociable=(RadioButton)findViewById(R.id.MaloSUBPLACAASOCIABLE);

        bueno_tuberia_polyvinico=(RadioButton)findViewById(R.id.BuenoTUBERIADEPOLYVINICO);
        //regular_tuberia_polyvinico=(RadioButton)findViewById(R.id.RegularTUBERIADEPOLYVINICO);
        malo_tuberia_polyvinico=(RadioButton)findViewById(R.id.MaloTUBERIADEPOLYVINICO);

        bueno_sub_placa_sencilla=(RadioButton)findViewById(R.id.BuenoSUBPLACASENCILLA);
        //regular_sub_placa_sencilla=(RadioButton)findViewById(R.id.RegularSUBPLACASENCILLA);
        malo_sub_placa_sencilla=(RadioButton)findViewById(R.id.MaloSUBPLACASENCILLA);

        bueno_tuberia_pequeña=(RadioButton)findViewById(R.id.BuenoTUBERIAPEQUEÑA);
        //regular_tuberia_pequeña=(RadioButton)findViewById(R.id.RegularTUBERIAPEQUEÑA);
        malo_tuberia_pequeña=(RadioButton)findViewById(R.id.MaloTUBERIAPEQUEÑA);

        bueno_frasco_nalgene=(RadioButton)findViewById(R.id.BuenoFRASCONALGENE);
        //regular_frasco_nalgene=(RadioButton)findViewById(R.id.RegularFRASCONALGENE);
        malo_frasco_nalgene=(RadioButton)findViewById(R.id.MaloFRASCONALGENE);

        bueno_tuberia_teflon=(RadioButton)findViewById(R.id.BuenoTUBERIATEFLON);
        //regular_tuberia_teflon=(RadioButton)findViewById(R.id.RegularTUBERIATEFLON);
        malo_tuberia_teflon=(RadioButton)findViewById(R.id.MaloTUBERIATEFLON);

        bueno_acrilico_fijadores=(RadioButton)findViewById(R.id.BuenoACRILICOSFIJADORES);
        //regular_acrilico_fijadores=(RadioButton)findViewById(R.id.RegularACRILICOSFIJADORES);
        malo_acrilico_fijadores=(RadioButton)findViewById(R.id.MaloACRILICOSFIJADORES);

        bueno_tuberia_latex=(RadioButton)findViewById(R.id.BuenoTUBERIALATEX);
        //regular_tuberia_latex=(RadioButton)findViewById(R.id.RegularTUBERIALATEX);
        malo_tuberia_latex=(RadioButton)findViewById(R.id.MaloTUBERIALATEX);

        bueno_ensamblaje_boquilla=(RadioButton)findViewById(R.id.BuenoENSAMBLAJEDEBOQUILLA);
        //regular_ensamblaje_boquilla=(RadioButton)findViewById(R.id.RegularENSAMBLAJEDEBOQUILLA);
        malo_ensamblaje_boquilla=(RadioButton)findViewById(R.id.MaloENSAMBLAJEDEBOQUILLA);

        bueno_acopladora_90=(RadioButton)findViewById(R.id.BuenoACOPLADORA90);
        //regular_acopladora_90=(RadioButton)findViewById(R.id.RegularACOPLADORA90);
        malo_acopladora_90=(RadioButton)findViewById(R.id.MaloACOPLADORA90);

        bueno_motor=(RadioButton)findViewById(R.id.BuenoMOTOR);
        //regular_motor=(RadioButton)findViewById(R.id.RegularMOTOR);
        malo_motor=(RadioButton)findViewById(R.id.MaloMOTOR);

        bueno_acopladora_tee=(RadioButton)findViewById(R.id.BuenoACOPLADORATEE);
        //regular_acopladora_tee=(RadioButton)findViewById(R.id.RegularACOPLADORATEE);
        malo_acopladora_tee=(RadioButton)findViewById(R.id.MaloACOPLADORATEE);


        bueno_boquilla=(RadioButton)findViewById(R.id.BuenoBOQUILLA);
        //regular_boquilla=(RadioButton)findViewById(R.id.RegularBOQUILLA);
        malo_boquilla=(RadioButton)findViewById(R.id.MaloBOQUILLA);

        bueno_reguladora_aire_compresora=(RadioButton)findViewById(R.id.BuenoREGULADORDEAIRECOMPRESORA);
        //regular_reguladora_aire_compresora=(RadioButton)findViewById(R.id.RegularREGULADORDEAIRECOMPRESORA);
        malo_reguladora_aire_compresora=(RadioButton)findViewById(R.id.MaloREGULADORDEAIRECOMPRESORA);

        bueno_sostenedor_boquilla=(RadioButton)findViewById(R.id.BuenoSOSTENEDORDEBOQUILLA);
        //regular_sostenedor_boquilla=(RadioButton)findViewById(R.id.RegularSOSTENEDORDEBOQUILLA);
        malo_sostenedor_boquilla=(RadioButton)findViewById(R.id.MaloSOSTENEDORDEBOQUILLA);

        bueno_tanque_compresora=(RadioButton)findViewById(R.id.BuenoTANQUECOMPRESORA);
        //regular_tanque_compresora=(RadioButton)findViewById(R.id.RegularTANQUECOMPRESORA);
        malo_tanque_compresora=(RadioButton)findViewById(R.id.MaloTANQUECOMPRESORA);

        bueno_clan_adhesivo=(RadioButton)findViewById(R.id.BuenoClanAdhesivo);
        //regular_clan_adhesivo=(RadioButton)findViewById(R.id.RegularClanAdhesivo);
        malo_clan_adhesivo=(RadioButton)findViewById(R.id.MaloClanAdhesivo);

        bueno_ruedas=(RadioButton)findViewById(R.id.BuenoRUEDAS);
        //regular_ruedas=(RadioButton)findViewById(R.id.RegularRUEDAS);
        malo_ruedas=(RadioButton)findViewById(R.id.MaloRUEDAS);

        bueno_logo_spravac=(RadioButton)findViewById(R.id.BuenoLogoSpravac);
        //regular_logo_spravac=(RadioButton)findViewById(R.id.RegularLogoSpravac);
        malo_logo_spravac=(RadioButton)findViewById(R.id.MaloLogoSpravac);

        bueno_cables_electricos=(RadioButton)findViewById(R.id.BuenoCableselectricos);
        //regular_cables_electricos=(RadioButton)findViewById(R.id.RegularCableselectricos);
        malo_cables_electricos=(RadioButton)findViewById(R.id.MaloCableselectricos);

        bueno_cortinas=(RadioButton)findViewById(R.id.BuenoCORTINAS);
        //regular_cortinas=(RadioButton)findViewById(R.id.RegularCORTINAS);
        malo_cortinas=(RadioButton)findViewById(R.id.MaloCORTINAS);

        bueno_valvula_purgar=(RadioButton)findViewById(R.id.BuenoValvulaPurgar);
        //regular_valvula_purgar=(RadioButton)findViewById(R.id.RegularValvulaPurgar);
        malo_valvula_purgar=(RadioButton)findViewById(R.id.MaloValvulaPurgar);

        bueno_varillas=(RadioButton)findViewById(R.id.BuenoVARILLAS);
        //regular_varillas=(RadioButton)findViewById(R.id.RegularVARILLAS);
        malo_varillas=(RadioButton)findViewById(R.id.MaloVARILLAS);

        bueno_manguera=(RadioButton)findViewById(R.id.BuenoMANGUERA);
        //regular_manguera=(RadioButton)findViewById(R.id.RegularMANGUERA);
        malo_manguera=(RadioButton)findViewById(R.id.MaloMANGUERA);

        bueno_nivelacion=(RadioButton)findViewById(R.id.BuenoNivelacion);
        malo_nivelacion=(RadioButton)findViewById(R.id.MaloNivelacion);

        bueno_presion_compresora=(RadioButton)findViewById(R.id.BuenoPRESIONDECOMPRESORA);
        malo_presion_compresora=(RadioButton)findViewById(R.id.MaloPRESIONDECOMPRESORA);

        bueno_presion_maquina=(RadioButton)findViewById(R.id.BuenoPRESIONDEMAQUINA);
        malo_presion_maquina=(RadioButton)findViewById(R.id.MaloPRESIONDEMAQUINA);

        bueno_retiro_cajas=(RadioButton)findViewById(R.id.BuenoRetirodeCajas);
        malo_retiro_cajas=(RadioButton)findViewById(R.id.MaloRetirodeCajas);

        bueno_activacion_foski=(RadioButton)findViewById(R.id.BuenoACTIVACIONDEFOSKI);
        malo_activacion_foski=(RadioButton)findViewById(R.id.MaloACTIVACIONDEFOSKI);

        bueno_boquillas=(RadioButton)findViewById(R.id.BuenoBOQUILLAS);
        malo_boquillas=(RadioButton)findViewById(R.id.MaloBOQUILLAS);

        bueno_dispersion=(RadioButton)findViewById(R.id.BuenoDISPERSION);
        malo_dispersion=(RadioButton)findViewById(R.id.MaloDISPERSION);

        bueno_calibracion=(RadioButton)findViewById(R.id.BuenoCALIBRACION);
        malo_calibracion=(RadioButton)findViewById(R.id.MaloCALIBRACION);

        bueno_materiales_utlizados=(RadioButton)findViewById(R.id.BuenoMATERIALESUTILIZADOS);
        malo_materiales_utlizados=(RadioButton)findViewById(R.id.MaloMATERIALESUTILIZADOS);

        bueno_cambio=(RadioButton)findViewById(R.id.BuenoCAMBIO);
        malo_cambio=(RadioButton)findViewById(R.id.MaloCAMBIO);

        bueno_enjuague=(RadioButton)findViewById(R.id.BuenoEnjuague);
        malo_enjuague=(RadioButton)findViewById(R.id.MaloEnjuague);

        bueno_esterilizado=(RadioButton)findViewById(R.id.BuenoEsterilizado);
        malo_esterilizado=(RadioButton)findViewById(R.id.MaloEsterilizado);

        bueno_esterilizado_tuberia=(RadioButton)findViewById(R.id.BuenoESTERILIZADODETUBERIAS);
        malo_esterilizado_tuberia=(RadioButton)findViewById(R.id.MaloESTERILIZADODETUBERIAS);

        bueno_esterilizado_boquilla=(RadioButton)findViewById(R.id.BuenoESTERILIZADODEBOQUILLAS);
        malo_esterilizado_boquilla=(RadioButton)findViewById(R.id.MaloESTERILIZADODEBOQUILLAS);

        bueno_secado=(RadioButton)findViewById(R.id.BuenoSECADO);
        malo_secado=(RadioButton)findViewById(R.id.MaloSECADO);

        bueno_proteccion=(RadioButton)findViewById(R.id.BuenoPROTECCION);
        malo_proteccion=(RadioButton)findViewById(R.id.MaloPROTECCION);


        bt_firmar_1=(Button)findViewById(R.id.bt_firmar_1);
        bt_firmar_2=(Button)findViewById(R.id.bt_firmar_2);


        im_firma_2=(ImageView)findViewById(R.id.im_firma_2);
        im_firma_1=(ImageView)findViewById(R.id.im_firma_1);
        im_foto_jefe=(ImageView)findViewById(R.id.im_foto_jefe);

        bt_nuevo=(Button)findViewById(R.id.bt_nuevo);
        bt_formulario=(Button)findViewById(R.id.bt_formulario);
        ib_izquierda=(ImageButton)findViewById(R.id.ib_izquierda);
        ib_derecha=(ImageButton)findViewById(R.id.ib_derecha);


        bt_nuevo.setOnClickListener(this);
        ib_izquierda.setOnClickListener(this);
        ib_derecha.setOnClickListener(this);
/*
        cargar_compania_en_la_lista();
        cargar_maquina_en_la_lista();
*/
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei=telephonyManager.getDeviceId();

        if(imei.equals("")==true)
        {
            Toast.makeText(this,"Necesita dar permisos para Obtener el número de Imei.",Toast.LENGTH_SHORT).show();
        }




        bt_firmar_1.setOnClickListener(this);
        bt_firmar_2.setOnClickListener(this);

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



        try {
            Bundle bundle=getIntent().getExtras();
            id_spr=Integer.parseInt(bundle.getString("id_spr"));
            cargar_servicio_mantenimiento(id_spr);

        } catch (Exception e)
        {
            Log.e("Detalle",""+e);
            finish();
        }

    }//fin de Oncreate

    private void cargar_servicio_mantenimiento(int id_spr) {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bt_formulario.setText("FORMULARIO NRO:"+id_spr);
        
        String consulta="select s.*,c.nombre,m.codigo from servicio_mantenimiento s,compania c,maquina m where  s.id_compania=c.id and s.id_maquina=m.id and s.id='"+id_spr+"'";
        Cursor fila = bd.rawQuery(consulta, null);

        if (fila.moveToFirst()) {
        fecha.setText(fila.getString(1));
        hora_ingreso.setText(fila.getString(2));
        hora_salida.setText(fila.getString(3));
        String codigo=fila.getString(4);
        String revision=fila.getString(5);
        String firma_jefe=fila.getString(6);
        String firma_invetsa=fila.getString(7);
        String id_maquina=fila.getString(8);
        String id_compania=fila.getString(10);
        String foto_jefe=fila.getString(12);

        autoCompania.setText(fila.getString(13));
        autoMaquina.setText(fila.getString(14));

        bm_firma_1=imagen_en_vista(firma_jefe);
        bm_firma_2=imagen_en_vista(firma_invetsa);
        Bitmap bm_foto_jefe=imagen_en_vista(foto_jefe);

            if(bm_firma_1!=null)
            {
                im_firma_1.setImageBitmap(bm_firma_1);
            }
            if(bm_firma_2!=null)
            {
                im_firma_2.setImageBitmap(bm_firma_2);
            }
            if(bm_foto_jefe!=null)
            {
                im_foto_jefe.setImageBitmap(bm_foto_jefe);
            }
            cargar_inspeccion_visual(id_spr);
            cargar_inspeccion_funcionamiento(id_spr);
            cargar_detalle_inspeccion_visual(id_spr);
            cargar_detalle_inspeccion_funcionamiento(id_spr);
        }
    }

    private void cargar_inspeccion_visual(int id_spr) {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String consulta="select * from inspeccion_visual where  id_servicio='"+id_spr+"'";
        Cursor fila = bd.rawQuery(consulta, null);

        if (fila.moveToFirst()) {
            observacion_inspeccion.setText(fila.getString(1));
            piezas_cambiadas_inspeccion.setText(fila.getString(2));
        }
    }
    private void cargar_inspeccion_funcionamiento(int id_spr) {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String consulta="select * from inspeccion_funcionamiento where  id_servicio='"+id_spr+"'";

        Cursor fila = bd.rawQuery(consulta, null);

        if (fila.moveToFirst()) {
            do {
                try {
                    switch(fila.getString(0))
                    {
                        case "1":
                            observacion_funcionamiento.setText(fila.getString(1));
                            frecuencia_uso_funcionamiento.setText(fila.getString(2));

                            break;
                        case "2":
                            observacion_desinfeccion.setText(fila.getString(1));
                            cantidad_aves_desinfeccion.setText(fila.getString(2));
                            break;
                    }
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());
        }
    }

    private void cargar_detalle_inspeccion_visual(int id_spr) {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        
        String consulta="select * from detalle_inspeccion_visual where  id_servicio='"+id_spr+"'";
        Cursor fila = bd.rawQuery(consulta, null);

        if (fila.moveToFirst()) {
         do {
                try {
                    int estado=Integer.parseInt(fila.getString(4));
                    switch(fila.getString(0))
                    {
                        case "1":set_estado_por_numero(estado,bueno_cubierta,regular_cubierta,malo_cubierta);break;
                        case "2":set_estado_por_numero(estado,bueno_mesa,regular_mesa,malo_mesa);break;
                        case "3":set_estado_por_numero(estado,bueno_garruchas,regular_garruchas,malo_garruchas);break;
                        case "4":set_estado_por_numero(estado,bueno_ensamblaje_jeringa,regular_ensamblaje_jeringa,malo_ensamblaje_jeringa);break;
                        case "5":set_estado_por_numero(estado,bueno_base_aluminio,regular_base_aluminio,malo_base_aluminio);break;
                        case "6":set_estado_por_numero(estado,bueno_valvula_control,regular_valvula_control,malo_valvula_control);break;
                        case "7":set_estado_por_numero(estado,bueno_repuesto_valvula,regular_repuesto_valvula,malo_repuesto_valvula);break;
                        case "8":set_estado_por_numero(estado,bueno_acopladura_rapida,regular_acopladura_rapida,malo_acopladura_rapida);break;
                        case "9":set_estado_por_numero(estado,bueno_seguro,regular_seguro,malo_seguro);break;
                        case "10":set_estado_por_numero(estado,bueno_cilindro_fuerza,regular_cilindro_fuerza,malo_cilindro_fuerza);break;
                        case "11":set_estado_por_numero(estado,bueno_sub_placa_asociable,regular_sub_placa_asociable,malo_sub_placa_asociable);break;
                        case "12":set_estado_por_numero(estado,bueno_sub_placa_sencilla,regular_sub_placa_sencilla,malo_sub_placa_sencilla);break;
                        case "13":set_estado_por_numero(estado,bueno_frasco_nalgene,regular_frasco_nalgene,malo_frasco_nalgene);break;
                        case "14":set_estado_por_numero(estado,bueno_acrilico_fijadores,regular_acrilico_fijadores,malo_acrilico_fijadores);break;
                        case "15":set_estado_por_numero(estado,bueno_ensamblaje_boquilla,regular_ensamblaje_boquilla,malo_ensamblaje_boquilla);break;
                        case "16":set_estado_por_numero(estado,bueno_acopladora_90,regular_acopladora_90,malo_acopladora_90);break;
                        case "17":set_estado_por_numero(estado,bueno_acopladora_tee,regular_acopladora_tee,malo_acopladora_tee);break;
                        case "18":set_estado_por_numero(estado,bueno_boquilla,regular_boquilla,malo_boquilla);break;
                        case "19":set_estado_por_numero(estado,bueno_sostenedor_boquilla,regular_sostenedor_boquilla,malo_sostenedor_boquilla);break;
                        case "20":set_estado_por_numero(estado,bueno_clan_adhesivo,regular_clan_adhesivo,malo_clan_adhesivo);break;
                        case "21":set_estado_por_numero(estado,bueno_logo_spravac,regular_logo_spravac,malo_logo_spravac);break;
                        case "22":set_estado_por_numero(estado,bueno_cortinas,regular_cortinas,malo_cortinas);break;
                        case "23":set_estado_por_numero(estado,bueno_varillas,regular_varillas,malo_varillas);break;
                        case "24":set_estado_por_numero(estado,bueno_foski,regular_foski,malo_foski);break;
                        case "25":set_estado_por_numero(estado,bueno_seguro_foski,regular_seguro_foski,malo_seguro_foski);break;
                        case "26":set_estado_por_numero(estado,bueno_ensamblaje,regular_ensamblaje,malo_ensamblaje);break;
                        case "27":set_estado_por_numero(estado,bueno_regulador_aire,regular_regulador_aire,malo_regulador_aire);break;
                        case "28":set_estado_por_numero(estado,bueno_manometro,regular_manometro,malo_manometro);break;
                        case "29":set_estado_por_numero(estado,bueno_conector_rapido_5,regular_conector_rapido_5,malo_conector_rapido_5);break;
                        case "30":set_estado_por_numero(estado,bueno_conector_hembra,regular_conector_hembra,malo_conector_hembra);break;
                        case "31":set_estado_por_numero(estado,bueno_conector_macho,regular_conector_macho,malo_conector_macho);break;
                        case "32":set_estado_por_numero(estado,bueno_meniscos_calibradores,regular_meniscos_calibradores,malo_meniscos_calibradores);break;
                        case "33":set_estado_por_numero(estado,bueno_jeringa_descartables,regular_jeringa_descartables,malo_jeringa_descartables);break;
                        case "34":set_estado_por_numero(estado,bueno_tuberia_polyvinico,regular_tuberia_polyvinico,malo_tuberia_polyvinico);break;
                        case "35":set_estado_por_numero(estado,bueno_tuberia_pequeña,regular_tuberia_pequeña,malo_tuberia_pequeña);break;
                        case "36":set_estado_por_numero(estado,bueno_tuberia_teflon,regular_tuberia_teflon,malo_tuberia_teflon);break;
                        case "37":set_estado_por_numero(estado,bueno_tuberia_latex,regular_tuberia_latex,malo_tuberia_latex);break;
                        case "38":set_estado_por_numero(estado,bueno_motor,regular_motor,malo_motor);break;
                        case "39":set_estado_por_numero(estado,bueno_manometro,regular_manometro,malo_manometro);break;
                        case "40":set_estado_por_numero(estado,bueno_reguladora_aire_compresora,regular_reguladora_aire_compresora,malo_reguladora_aire_compresora);break;
                        case "41":set_estado_por_numero(estado,bueno_tanque_compresora,regular_tanque_compresora,malo_tanque_compresora);break;
                        case "42":set_estado_por_numero(estado,bueno_ruedas,regular_ruedas,malo_ruedas);break;
                        case "43":set_estado_por_numero(estado,bueno_cables_electricos,regular_cables_electricos,malo_cables_electricos);break;
                        case "44":set_estado_por_numero(estado,bueno_valvula_purgar,regular_valvula_purgar,malo_valvula_purgar);break;
                        case "45":set_estado_por_numero(estado,bueno_manguera,regular_manguera,malo_manguera);break;          

                    }
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());
        }
}

    private void cargar_detalle_inspeccion_funcionamiento(int id_spr) {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        
        String consulta="select * from detalle_inspeccion_funcionamiento where  id_servicio='"+id_spr+"'";
        Cursor fila = bd.rawQuery(consulta, null);

        if (fila.moveToFirst()) {
         do {
                try {
                    int estado=Integer.parseInt(fila.getString(3));
                    RadioButton regular=null;
                    switch(fila.getString(1))
                    {
                        case "1":
                                
                                switch(fila.getString(0))
                                {
                                    case "1":set_estado_por_numero(estado,bueno_nivelacion,regular,malo_nivelacion);break;
                                    case "2":set_estado_por_numero(estado,bueno_presion_compresora,regular,malo_presion_compresora);break;
                                    case "3":set_estado_por_numero(estado,bueno_presion_maquina,regular,malo_presion_maquina);break;
                                    case "4":set_estado_por_numero(estado,bueno_retiro_cajas,regular,malo_retiro_cajas);break;
                                    case "5":set_estado_por_numero(estado,bueno_activacion_foski,regular,malo_activacion_foski);break;
                                    case "6":set_estado_por_numero(estado,bueno_boquillas,regular,malo_boquillas);break;
                                    case "7":set_estado_por_numero(estado,bueno_dispersion,regular,malo_dispersion);break;
                                    case "8":set_estado_por_numero(estado,bueno_calibracion,regular,malo_calibracion);break;
                                }
                        break;

                         case "2":
                                switch(fila.getString(0))
                                {
                                    case "1":set_estado_por_numero(estado,bueno_materiales_utlizados,regular,malo_materiales_utlizados);break;
                                    case "2":set_estado_por_numero(estado,bueno_cambio,regular,malo_cambio);break;
                                    case "3":set_estado_por_numero(estado,bueno_enjuague,regular,malo_enjuague);break;
                                    case "4":set_estado_por_numero(estado,bueno_esterilizado,regular,malo_esterilizado);break;
                                    case "5":set_estado_por_numero(estado,bueno_esterilizado_tuberia,regular,malo_esterilizado_tuberia);break;
                                    case "6":set_estado_por_numero(estado,bueno_esterilizado_boquilla,regular,malo_esterilizado_boquilla);break;
                                    case "7":set_estado_por_numero(estado,bueno_secado,regular,malo_secado);break;
                                    case "8":set_estado_por_numero(estado,bueno_proteccion,regular,malo_proteccion);break;
                                }
                        break;
                        
    
                    }
                } catch (Exception e)
                {

                }

            } while(fila.moveToNext());
        }


}

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
        int id_servicio=id_servicio_mantenimiento()+1;
        int id_maquina=id_maquina_por_codigo(autoMaquina.getText().toString());

        int id_compania=id_compania_por_nombre(autoCompania.getText().toString());

        //id TECNICO...
        SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
        int id_tecnico=perfil.getInt("id",-1);
        if(id_tecnico==0)
        {
            finish();
        }
        if(id_servicio != -1 && id_maquina!=-1 && id_compania!=-1 && id_tecnico!=-1)
        {

            String DIRECCION_FIRMA_JEFE="Invetsa/Servicio_mantenimiento/"+id_servicio+"_jefe_"+fecha.getText()+".jpeg";
            String DIRECCION_FIRMA_INVETSA="Invetsa/Servicio_mantenimiento/"+id_servicio+"_invetsa_"+fecha.getText()+".jpeg";

            guardar_en_memoria(bm_firma_1,id_servicio+"_jefe_"+fecha.getText()+".jpeg","Invetsa/Servicio_mantenimiento");
            guardar_en_memoria(bm_firma_2,id_servicio+"_invetsa_"+fecha.getText()+".jpeg","Invetsa/Servicio_mantenimiento");

            String codigo=getString(R.string.codigo_spravac);
            String revision=getString(R.string.revision_spravac);

            guardar_servicio_mantenimiento(id_servicio,fecha.getText().toString(),hora_ingreso.getText().toString(),hora_salida.getText().toString(),codigo,revision,DIRECCION_FIRMA_JEFE,DIRECCION_FIRMA_INVETSA,String.valueOf(id_maquina),String.valueOf(id_tecnico),String.valueOf(id_compania),"SPRAVAC");

            int id_inspeccion=id_inspeccion_visual()+1;
            guardar_inspeccion_visual(id_inspeccion,observacion_inspeccion.getText().toString(),piezas_cambiadas_inspeccion.getText().toString(),id_servicio);

            guardar_detalle_inspeccion_visual(1,id_inspeccion,id_servicio,"SV-2001","CUBIERTA DE ACRILICO",get_estado(bueno_cubierta.isChecked(),regular_cubierta.isChecked(),malo_cubierta.isChecked()));
            guardar_detalle_inspeccion_visual(2,id_inspeccion,id_servicio,"SV-602","MESA DE METAL",get_estado(bueno_mesa.isChecked(),regular_mesa.isChecked(),malo_mesa.isChecked()));
            guardar_detalle_inspeccion_visual(3,id_inspeccion,id_servicio,"S/CODIGO","GARRUCHAS",get_estado(bueno_garruchas.isChecked(),regular_garruchas.isChecked(),malo_garruchas.isChecked()));
            guardar_detalle_inspeccion_visual(4,id_inspeccion,id_servicio,"SV-100","ENSAMBLAJE DE JERINGA",get_estado(bueno_ensamblaje_jeringa.isChecked(),regular_ensamblaje_jeringa.isChecked(),malo_ensamblaje_jeringa.isChecked()));
            guardar_detalle_inspeccion_visual(5,id_inspeccion,id_servicio,"SV-101","BASE DE ALUMINIO",get_estado(bueno_base_aluminio.isChecked(),regular_base_aluminio.isChecked(),malo_base_aluminio.isChecked()));
            guardar_detalle_inspeccion_visual(6,id_inspeccion,id_servicio,"SV-106","VALVULA DE CONTROL",get_estado(bueno_valvula_control.isChecked(),regular_valvula_control.isChecked(),malo_valvula_control.isChecked()));
            guardar_detalle_inspeccion_visual(7,id_inspeccion,id_servicio,"SV-1378","REPTS. DE VALVULA DE CONTROL",get_estado(bueno_repuesto_valvula.isChecked(),regular_repuesto_valvula.isChecked(),malo_repuesto_valvula.isChecked()));
            guardar_detalle_inspeccion_visual(8,id_inspeccion,id_servicio,"SV-934","ACOPLADURA RAPIDA DE VALVULA",get_estado(bueno_acopladura_rapida.isChecked(),regular_acopladura_rapida.isChecked(),malo_acopladura_rapida.isChecked()));
            guardar_detalle_inspeccion_visual(9,id_inspeccion,id_servicio,"SV-104","SEGURO",get_estado(bueno_seguro.isChecked(),regular_seguro.isChecked(),malo_seguro.isChecked()));
            guardar_detalle_inspeccion_visual(10,id_inspeccion,id_servicio,"SV-1344","CILINDRO DE FUERZA",get_estado(bueno_cilindro_fuerza.isChecked(),regular_cilindro_fuerza.isChecked(),malo_cilindro_fuerza.isChecked()));
            guardar_detalle_inspeccion_visual(11,id_inspeccion,id_servicio,"5708","SUBPLACA ASOCIABLE",get_estado(bueno_sub_placa_asociable.isChecked(),regular_sub_placa_asociable.isChecked(),malo_sub_placa_asociable.isChecked()));
            guardar_detalle_inspeccion_visual(12,id_inspeccion,id_servicio,"5709","SUBPLACA SENCILLA",get_estado(bueno_sub_placa_sencilla.isChecked(),regular_sub_placa_sencilla.isChecked(),malo_sub_placa_sencilla.isChecked()));
            guardar_detalle_inspeccion_visual(13,id_inspeccion,id_servicio,"SV-2008","FRASCO NALGENE",get_estado(bueno_frasco_nalgene.isChecked(),regular_frasco_nalgene.isChecked(),malo_frasco_nalgene.isChecked()));
            guardar_detalle_inspeccion_visual(14,id_inspeccion,id_servicio,"SV-607","ACRILICOS FIJADORES DE FRASCOS",get_estado(bueno_acrilico_fijadores.isChecked(),regular_acrilico_fijadores.isChecked(),malo_acrilico_fijadores.isChecked()));
            guardar_detalle_inspeccion_visual(15,id_inspeccion,id_servicio,"SV-2002","ENSAMBLAJE DE BOQUILLAS",get_estado(bueno_ensamblaje_boquilla.isChecked(),regular_ensamblaje_boquilla.isChecked(),malo_ensamblaje_boquilla.isChecked()));
            guardar_detalle_inspeccion_visual(16,id_inspeccion,id_servicio,"SV-1030","ACOPLADORA 90",get_estado(bueno_acopladora_90.isChecked(),regular_acopladora_90.isChecked(),malo_acopladora_90.isChecked()));
            guardar_detalle_inspeccion_visual(17,id_inspeccion,id_servicio,"SV-933","ACOPLADORA TEE",get_estado(bueno_acopladora_tee.isChecked(),regular_acopladora_tee.isChecked(),malo_acopladora_tee.isChecked()));
            guardar_detalle_inspeccion_visual(18,id_inspeccion,id_servicio,"SV-936","BOQUILLAS",get_estado(bueno_boquilla.isChecked(),regular_boquilla.isChecked(),malo_boquilla.isChecked()));
            guardar_detalle_inspeccion_visual(19,id_inspeccion,id_servicio,"SV-611","SOSTENEDOR DE BOQUILLA",get_estado(bueno_sostenedor_boquilla.isChecked(),regular_sostenedor_boquilla.isChecked(),malo_sostenedor_boquilla.isChecked()));
            guardar_detalle_inspeccion_visual(20,id_inspeccion,id_servicio,"SV-608","CLAMP. ADHESIVOS DE 1/4",get_estado(bueno_clan_adhesivo.isChecked(),regular_clan_adhesivo.isChecked(),malo_clan_adhesivo.isChecked()));
            guardar_detalle_inspeccion_visual(21,id_inspeccion,id_servicio,"SV-609","LOGO DE SPRA-VAC",get_estado(bueno_logo_spravac.isChecked(),regular_logo_spravac.isChecked(),malo_logo_spravac.isChecked()));
            guardar_detalle_inspeccion_visual(22,id_inspeccion,id_servicio,"SV-605","CORTINAS",get_estado(bueno_cortinas.isChecked(),regular_cortinas.isChecked(),malo_cortinas.isChecked()));
            guardar_detalle_inspeccion_visual(23,id_inspeccion,id_servicio,"SV-606","VARILLAS",get_estado(bueno_varillas.isChecked(),regular_varillas.isChecked(),malo_varillas.isChecked()));
            guardar_detalle_inspeccion_visual(24,id_inspeccion,id_servicio,"SV-2011","FOSKI SWITCH",get_estado(bueno_foski.isChecked(),regular_foski.isChecked(),malo_foski.isChecked()));
            guardar_detalle_inspeccion_visual(25,id_inspeccion,id_servicio,"SV-1139","SEGURO DE FOSKI",get_estado(bueno_seguro_foski.isChecked(),regular_seguro_foski.isChecked(),malo_seguro_foski.isChecked()));
            guardar_detalle_inspeccion_visual(26,id_inspeccion,id_servicio,"SV-2004","ENSAMBLAJE FILTRO REGULADOR",get_estado(bueno_ensamblaje.isChecked(),regular_ensamblaje.isChecked(),malo_ensamblaje.isChecked()));
            guardar_detalle_inspeccion_visual(27,id_inspeccion,id_servicio,"SV-809","REGULADOR DE AIRE",get_estado(bueno_regulador_aire.isChecked(),regular_regulador_aire.isChecked(),malo_regulador_aire.isChecked()));
            guardar_detalle_inspeccion_visual(28,id_inspeccion,id_servicio,"SV-810","MANOMETRO",get_estado(bueno_manometro.isChecked(),regular_manometro.isChecked(),malo_manometro.isChecked()));
            guardar_detalle_inspeccion_visual(29,id_inspeccion,id_servicio,"S720","CONECTOR RAPIDO 5/32",get_estado(bueno_conector_rapido_5.isChecked(),regular_conector_rapido_5.isChecked(),malo_conector_rapido_5.isChecked()));
            guardar_detalle_inspeccion_visual(30,id_inspeccion,id_servicio,"SV-817","CONECTOR RAPIDO HEMBRA",get_estado(bueno_conector_hembra.isChecked(),regular_conector_hembra.isChecked(),malo_conector_hembra.isChecked()));
            guardar_detalle_inspeccion_visual(31,id_inspeccion,id_servicio,"SV-818","CONECTOR RAPIDO MACHO",get_estado(bueno_conector_macho.isChecked(),regular_conector_macho.isChecked(),malo_conector_macho.isChecked()));
            guardar_detalle_inspeccion_visual(32,id_inspeccion,id_servicio,"SV-1163","MENISCO CALIBRADOR",get_estado(bueno_meniscos_calibradores.isChecked(),regular_meniscos_calibradores.isChecked(),malo_meniscos_calibradores.isChecked()));
            guardar_detalle_inspeccion_visual(33,id_inspeccion,id_servicio,"5083","JERINGA DESCARTABLE",get_estado(bueno_jeringa_descartables.isChecked(),regular_jeringa_descartables.isChecked(),malo_jeringa_descartables.isChecked()));
            guardar_detalle_inspeccion_visual(34,id_inspeccion,id_servicio,"5441","TUBERIA DE POLYVINILO",get_estado(bueno_tuberia_polyvinico.isChecked(),regular_tuberia_polyvinico.isChecked(),malo_tuberia_polyvinico.isChecked()));
            guardar_detalle_inspeccion_visual(35,id_inspeccion,id_servicio,"3520","TUBERIA PEQUEÑA",get_estado(bueno_tuberia_pequeña.isChecked(),regular_tuberia_pequeña.isChecked(),malo_tuberia_pequeña.isChecked()));
            guardar_detalle_inspeccion_visual(36,id_inspeccion,id_servicio,"SV-1110","TUBERIA DE TEFLON",get_estado(bueno_tuberia_teflon.isChecked(),regular_tuberia_teflon.isChecked(),malo_tuberia_teflon.isChecked()));
            guardar_detalle_inspeccion_visual(37,id_inspeccion,id_servicio,"SV-1115","TUBERIA LATEX",get_estado(bueno_tuberia_latex.isChecked(),regular_tuberia_latex.isChecked(),malo_tuberia_latex.isChecked()));
            guardar_detalle_inspeccion_visual(38,id_inspeccion,id_servicio,"","MOTOR",get_estado(bueno_motor.isChecked(),regular_motor.isChecked(),malo_motor.isChecked()));
            guardar_detalle_inspeccion_visual(39,id_inspeccion,id_servicio,"","MANOMETRO",get_estado(bueno_manometro.isChecked(),regular_manometro.isChecked(),malo_manometro.isChecked()));
            guardar_detalle_inspeccion_visual(40,id_inspeccion,id_servicio,"","REGULADOR DE AIRE",get_estado(bueno_reguladora_aire_compresora.isChecked(),regular_reguladora_aire_compresora.isChecked(),malo_reguladora_aire_compresora.isChecked()));
            guardar_detalle_inspeccion_visual(41,id_inspeccion,id_servicio,"","TANQUE DE COMPRESORA",get_estado(bueno_tanque_compresora.isChecked(),regular_tanque_compresora.isChecked(),malo_tanque_compresora.isChecked()));
            guardar_detalle_inspeccion_visual(42,id_inspeccion,id_servicio,"","RUEDAS",get_estado(bueno_ruedas.isChecked(),regular_ruedas.isChecked(),malo_ruedas.isChecked()));
            guardar_detalle_inspeccion_visual(43,id_inspeccion,id_servicio,"","CABLES ELECTRICOS",get_estado(bueno_cables_electricos.isChecked(),regular_cables_electricos.isChecked(),malo_cables_electricos.isChecked()));
            guardar_detalle_inspeccion_visual(44,id_inspeccion,id_servicio,"","VALVULA DE PURGAR",get_estado(bueno_valvula_purgar.isChecked(),regular_valvula_purgar.isChecked(),malo_valvula_purgar.isChecked()));
            guardar_detalle_inspeccion_visual(45,id_inspeccion,id_servicio,"","MANGUERA",get_estado(bueno_manguera.isChecked(),regular_manguera.isChecked(),malo_manguera.isChecked()));


            ///REGISTRO DE INSPECCION E FUNCIONAMIENTO
            int id_funcionamiento=1;
            guardar_inspeccion_funcionamiento(id_funcionamiento,observacion_funcionamiento.getText().toString(),frecuencia_uso_funcionamiento.getText().toString(),id_servicio);

            //REGISTO DE LOS DETALLES DE INSPECCION EL  FUNCIONAMIENTO,
            guardar_detalle_inspeccion_funcionamiento(1,id_funcionamiento,id_servicio,"NIVELACION DE LA MAQUINA",get_estado(bueno_nivelacion.isChecked(),false,malo_nivelacion.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(2,id_funcionamiento,id_servicio,"PRESION DE COMPRESORA",get_estado(bueno_presion_compresora.isChecked(),false,malo_presion_compresora.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(3,id_funcionamiento,id_servicio,"PRESION DE MAQUINA",get_estado(bueno_presion_maquina.isChecked(),false,malo_presion_maquina.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(4,id_funcionamiento,id_servicio,"RETIRO DE CAJAS",get_estado(bueno_retiro_cajas.isChecked(),false,malo_retiro_cajas.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(5,id_funcionamiento,id_servicio,"ACTIVACION DE FOSKI",get_estado(bueno_activacion_foski.isChecked(),false,malo_activacion_foski.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(6,id_funcionamiento,id_servicio,"BOQUILLAS",get_estado(bueno_boquillas.isChecked(),false,malo_boquillas.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(7,id_funcionamiento,id_servicio,"DISPERSION DE SOLUCION VACUNAL",get_estado(bueno_dispersion.isChecked(),false,malo_dispersion.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(8,id_funcionamiento,id_servicio,"CALIBRACION",get_estado(bueno_calibracion.isChecked(),false,malo_calibracion.isChecked()));

            ///REGISTRO DE LIMPIEZA Y DESINFECCION
            id_funcionamiento=2;
            guardar_inspeccion_funcionamiento(id_funcionamiento,observacion_desinfeccion.getText().toString(),cantidad_aves_desinfeccion.getText().toString(),id_servicio);

            //REGISTO DETALLE DE LIMPIEZA Y DESINFECCION,
            guardar_detalle_inspeccion_funcionamiento(1,id_funcionamiento,id_servicio,"MATERIALES UTILIZADOS",get_estado(bueno_materiales_utlizados.isChecked(),false,malo_materiales_utlizados.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(2,id_funcionamiento,id_servicio,"CAMBIO DE PIEZAS DESCARTABLES",get_estado(bueno_cambio.isChecked(),false,malo_cambio.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(3,id_funcionamiento,id_servicio,"ENJUAGUE DE TUBERIAS",get_estado(bueno_enjuague.isChecked(),false,malo_enjuague.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(4,id_funcionamiento,id_servicio,"ESTERILIZADO DE VALVULAS",get_estado(bueno_esterilizado.isChecked(),false,malo_esterilizado.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(5,id_funcionamiento,id_servicio,"ESTERILIZADO DE TUBERIAS",get_estado(bueno_esterilizado_tuberia.isChecked(),false,malo_esterilizado_tuberia.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(6,id_funcionamiento,id_servicio,"ESTERILIZADO DE BOUILLAS",get_estado(bueno_esterilizado_boquilla.isChecked(),false,malo_esterilizado_boquilla.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(7,id_funcionamiento,id_servicio,"SECADO",get_estado(bueno_secado.isChecked(),false,malo_secado.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(8,id_funcionamiento,id_servicio,"PROTECCION",get_estado(bueno_proteccion.isChecked(),false,malo_proteccion.isChecked()));




/*
 registro.put("id",String.valueOf(id));
        registro.put("observaciones", observaciones);
        registro.put("frecuencia", frecuencia);
        registro.put("id_servicio", String.valueOf(id_servicio));
        bd.insert("inspeccion_funcionamiento", null, registro);
*/
            Toast.makeText(this,"Se registro correctamente",Toast.LENGTH_SHORT).show();
            Log.w("Spravac","Se registro correctamente.");
        }
        else
        {
            Log.e("Spravac","Error al registrar un servicio de mantenimiento de la maquina Spravac");
        }

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
                return new DatePickerDialog(this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(this, time_listener, hour,
                        minute, false);
            case Time_id_salida:

                // Open the timepicker dialog
                return new TimePickerDialog(this, time_listener_salida, hour,
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
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            hora_ingreso.setText(time1);
        }
    };
    TimePickerDialog.OnTimeSetListener time_listener_salida = new TimePickerDialog.OnTimeSetListener()
    {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            hora_salida.setText(time1);
        }
    };

//-----------------------HASTA AQUI CODIGO PARA LOS DIALOGOS PARA HORA - FECHA------------------------

    private void cargar_compania_en_la_lista()
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from compania ", null);
        String []companias=new String[fila.getCount()];
        int i=0;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                String id=fila.getString(0);
                String nombre=fila.getString(1);
                companias[i]=nombre;
                i++;
            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, companias);
        autoCompania.setAdapter(adapter);
    }
    private void cargar_maquina_en_la_lista()
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from maquina ", null);
        String []maquina=new String[fila.getCount()];
        int i=0;
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)

            do {
                String codigo=fila.getString(1);
                maquina[i]=codigo;
                i++;
            } while(fila.moveToNext());

        } else
        {

        }
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, maquina);
        autoMaquina.setAdapter(adapter);
    }


    private int id_maquina_ultimo()
    {int id=-1;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select max(id) from maquina ", null);
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
    private int id_maquina_por_codigo(String texto)
    {int id=-1;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from maquina where codigo='"+texto+"'", null);
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
    private int id_compania_por_nombre(String texto)
    {int id=-1;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from compania where nombre='"+texto+"'", null);
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
    private int id_inspeccion_visual()
    {int id=0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select max(id) from inspeccion_visual ", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


            String codigo= fila.getString(0);
            try {
                if (codigo.equals("") == false && codigo.equals("0") == false) {
                    id = Integer.parseInt(codigo);
                }
            }catch (Exception e)
            {
                Log.e("id inspeccion visual",""+e);
            }

        }
        bd.close();
        return id;
    }
    private int id_inspeccion_funcionamiento()
    {int id=0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select max(id) from inspeccion_funcionamiento ", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


            String codigo= fila.getString(0);
            try{
                if(codigo.equals("")==false && codigo.equals("0")==false )
                {
                    id=Integer.parseInt(codigo);
                }
            }catch (Exception e)
            {
                Log.e("id inspeccion",""+e);
            }

        }
        bd.close();
        return id;
    }
    private int id_servicio_mantenimiento()
    {int id=0;
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select id from servicio_mantenimiento order by id desc ", null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)


            String codigo= fila.getString(0);
            try {
                if (codigo.equals("") == false && codigo.equals("0") == false && codigo.equals("null") == false) {
                    id = Integer.parseInt(codigo);
                }
            }catch (Exception e)
            {
                Log.e("id mantenimiento",""+e);
            }

        }
        bd.close();
        return id;
    }
    public void guardar_servicio_mantenimiento(int id,String fecha,String hora_ingreso,String hora_salida,String codigo,String revision,String firma_jefe,String firma_invetsa,String id_maquina,String id_tecnico,String id_compania,String formulario)
    {


        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        try {
            registro.put("id", String.valueOf(id));
            registro.put("fecha", fecha);
            registro.put("hora_ingreso", hora_ingreso);
            registro.put("hora_salidas", hora_salida);
            registro.put("codigo", codigo);
            registro.put("revision", revision);
            registro.put("firma_jefe_planta", firma_jefe);
            registro.put("firma_invetsa", firma_invetsa);
            registro.put("id_maquina", id_maquina);
            registro.put("id_tecnico", id_tecnico);
            registro.put("id_compania", id_compania);
            registro.put("imei", imei);
            registro.put("formulario", formulario);
            bd.insert("servicio_mantenimiento", null, registro);

        }catch (Exception e)
        {
            Log.e("sql",""+e);
        }

        bd.close();
    }

    public void guardar_inspeccion_visual(int id,String observaciones,String piesas_cambiadas,int id_servicio)
    {


        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("id",String.valueOf(id));
        registro.put("observaciones", observaciones);
        registro.put("piesas_cambiadas", piesas_cambiadas);
        registro.put("id_servicio", String.valueOf(id_servicio));
        registro.put("imei",imei);
        bd.insert("inspeccion_visual", null, registro);

        bd.close();
    }

    public void guardar_detalle_inspeccion_visual(int id,int id_inspeccion,int id_servicio, String codigo,String descripcion,int estado)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("id",String.valueOf(id));
        registro.put("id_inspeccion", String.valueOf(id_inspeccion));
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("estado",String.valueOf(estado));
        registro.put("imei",imei);
        registro.put("id_servicio",String.valueOf(id_servicio));
        bd.insert("detalle_inspeccion_visual", null, registro);

        bd.close();
    }

    public void guardar_inspeccion_funcionamiento(int id,String observaciones,String frecuencia,int id_servicio)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("id",String.valueOf(id));
        registro.put("observaciones", observaciones);
        registro.put("frecuencia_de_uso", frecuencia);
        registro.put("id_servicio", String.valueOf(id_servicio));
        registro.put("imei",imei);
        bd.insert("inspeccion_funcionamiento", null, registro);

        bd.close();
    }

    public void guardar_detalle_inspeccion_funcionamiento(int id,int id_inspeccion,int id_servicio, String criterio_evaluacion,int estado)
    {
        SQLite admin = new SQLite(this,
                "invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("id",String.valueOf(id));
        registro.put("id_inspeccion", String.valueOf(id_inspeccion));
        registro.put("criterio_evaluacion", criterio_evaluacion);
        registro.put("estado",String.valueOf(estado));
        registro.put("imei",imei);
        registro.put("id_servicio",String.valueOf(id_servicio));
        bd.insert("detalle_inspeccion_funcionamiento", null, registro);
        bd.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.bt_nuevo:
                startActivity( new Intent(this,MaquinaSpravac.class));
                finish();
                break;
            case  R.id.ib_izquierda:
                int aux_sim=0;
                aux_sim=get_id_izquierda(id_spr);
                if(aux_sim!=-1)
                {   id_spr=aux_sim;
                    cargar_servicio_mantenimiento(id_spr);
                }
                else
                {
                    mensaje_ok_error("No existe ningun registro.");
                }
                break;
            case  R.id.ib_derecha:
                int aux_sim1=0;
                aux_sim1=get_id_derecha(id_spr);
                if(aux_sim1!=-1)
                {   id_spr=aux_sim1;
                    cargar_servicio_mantenimiento(id_spr);
                }
                else
                {
                    mensaje_ok_error("No existe ningun registro.");
                }
                break;
            case R.id.bt_firmar_1:
                AlertDialog.Builder   builder_dialogo = new AlertDialog.Builder(this);

                final LayoutInflater inflater = getLayoutInflater();

                final View dialoglayout = inflater.inflate(R.layout.firmar, null);
                final com.grayhatcorp.invetsa.invetsa.TouchView tc_view=(TouchView)dialoglayout.findViewById(R.id.tc_view);
                Button bt_borrar=(Button) dialoglayout.findViewById(R.id.bt_borrar);
                Button bt_listo=(Button) dialoglayout.findViewById(R.id.bt_listo);
                // Start the animation (looped playback by default).

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
                        guardar_en_memoria(bm_firma_1,"firma_1.jpeg","Invetsa/Servicio_mantenimiento");
                        tc_view.limpiar();
                        savedImage2.removeAllViews();
                        savedImage2.addView(tc_view);
                        im_firma_1.setImageBitmap(bm_firma_1);
                        alertDialog_firmar_1.hide();
                    }
                });
                break;

            case R.id.bt_firmar_2:
                AlertDialog.Builder   builder_dialogo2 = new AlertDialog.Builder(this);

                final LayoutInflater inflater2 = getLayoutInflater();

                final View dialoglayout2 = inflater2.inflate(R.layout.firmar, null);
                final com.grayhatcorp.invetsa.invetsa.TouchView tc_view2=(TouchView)dialoglayout2.findViewById(R.id.tc_view);
                Button bt_borrar2=(Button) dialoglayout2.findViewById(R.id.bt_borrar);
                Button bt_listo2=(Button) dialoglayout2.findViewById(R.id.bt_listo);
                // Start the animation (looped playback by default).

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
                        guardar_en_memoria(bm_firma_2,"firma_2.jpeg","Invetsa/Servicio_mantenimiento");
                        tc_view2.limpiar();
                        savedImage2.removeAllViews();
                        savedImage2.addView(tc_view2);
                        im_firma_2.setImageBitmap(bm_firma_2);
                        alertDialog_firmar_2.hide();
                    }
                });
                break;
        }
    }

    public int get_estado(boolean bueno,boolean regular,boolean malo)
    {
        int estado=0;
        if(bueno==true)
        {
            estado=1;
        }else if(regular==true)
        {
            estado=2;
        }else if(malo==true)
        {
            estado=3;
        }
        return estado;
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
     public void set_estado_por_numero(int estado,RadioButton rb_bueno,RadioButton rb_regultar, RadioButton rb_malo)
    {

        if(estado==1)
        {
            rb_bueno.setChecked(true);

            
        }else if(estado==2)
        {

            try {
                rb_regultar.setChecked(true);
            }catch (Exception e)
            {

            }
        }else if(estado==3)
        {
            rb_malo.setChecked(true);

        }
        
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
    private int get_id_izquierda(int id_sim) {
        int id=-1;
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        try {
            Cursor fila = bd.rawQuery("select id from servicio_mantenimiento where  id<'" + id_sim + "' ORDER BY id DESC limit 1  ", null);
            if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
                id=Integer.parseInt(fila.getString(0));
            }
            bd.close();
        }catch (Exception e)
        {
            id=-1;
        }
        return  id;
    }
    private int get_id_derecha(int id_sim) {
        int id=-1;
        SQLite admin = new SQLite(this,"invetsa", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        try {
            Cursor fila = bd.rawQuery("select id from servicio_mantenimiento where  id>'" + id_sim + "' ORDER BY id ASC limit 1  ", null);
            if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
                id=Integer.parseInt(fila.getString(0));
            }
            bd.close();
        }catch (Exception e)
        {
            id=-1;
        }
        return  id;
    }

}
