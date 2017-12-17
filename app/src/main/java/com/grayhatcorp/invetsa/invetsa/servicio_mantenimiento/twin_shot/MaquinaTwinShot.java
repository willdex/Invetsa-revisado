package com.grayhatcorp.invetsa.invetsa.servicio_mantenimiento.twin_shot;

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
        import android.text.Html;
        import android.util.Log;
        import android.view.KeyEvent;
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
        import android.widget.ImageView;
        import android.widget.RadioButton;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import com.grayhatcorp.invetsa.invetsa.Base_de_datos.SQLite;
        import com.grayhatcorp.invetsa.invetsa.R;
        import com.grayhatcorp.invetsa.invetsa.TouchView;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.util.Calendar;

public class MaquinaTwinShot extends AppCompatActivity implements View.OnClickListener {//implements View.OnClickListener
    ArrayAdapter<String> adapter;

    private static String APP_DIRECTORY = "Invetsa/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "Imagen";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private String mPath;
    private Uri path;

    AlertDialog alertDialog_firmar_1,alertDialog_firmar_2;
    Button bt_firmar_1,bt_firmar_2;
    ImageView im_firma_1,im_firma_2,im_foto_jefe;
    Bitmap bm_firma_1=null,bm_firma_2=null,bm_foto_jefe=null;
    String imei="";

    //Spinner scompania;
TextView tv_fecha;
    Button horaIngreso,horaSalida;
    AutoCompleteTextView autoCompania,autoMaquina,autoDireccion,autoPlantaEncubacion;
    EditText UltimaVisita,encargadoMaquinas,JefePlanta;
    EditText Observacion_InspecionVisual,Piezas_Cambiadas_Inspeccion,Observaciones_Funcionamiento,Frecuencia_de_Uso,Observaciones_Limpieza,CantidadAvesVacunadas;
    EditText bueno_bolas_acero,regular_bolas_acero,malo_bolas_acero;
    EditText bueno_resortes,regular_resortes,malo_resortes;
    EditText bueno_bolitas_teflon,regular_bolitas_teflon,malo_bolitas_teflon;
    EditText bueno_tuercas_b_aluminio,regular_tuercas_b_aluminio,malo_tuercas_b_aluminio;
    EditText bueno_tuerca_collarin,regular_tuerca_collarin,malo_tuerca_collarin;
    EditText bueno_empaque_asiento,regular_empaque_asiento,malo_empaque_asiento;
    EditText bueno_empaque_captura,regular_empaque_captura,malo_empaque_captura;
    EditText bueno_tuberia_pequena,regular_tuberia_pequena,malo_tuberia_pequena;
    EditText bueno_tuberia_latex,regular_tuberia_latex,malo_tuberia_latex;

    RadioButton bueno_base_aluminio,regular_base_aluminio,malo_base_aluminio;
    RadioButton bueno_bloque_posterior,regular_bloque_posterior,malo_bloque_posterior;
    RadioButton bueno_bloque_cilindro,regular_bloque_cilindro,malo_bloque_cilindro;
    RadioButton bueno_reguladores_salida,regular_reguladores_salida,malo_reguladores_salida;
    RadioButton bueno_bloque_jeringa,regular_bloque_jeringa,malo_bloque_jeringa;
    RadioButton bueno_acopladura_colder_hembra,regular_acopladura_colder_hembra,malo_acopladura_colder_hembra;
    RadioButton bueno_bloque_delantero,regular_bloque_delantero,malo_bloque_delantero;
    RadioButton bueno_distribuidor_aire,regular_distribuidor_aire,malo_distribuidor_aire;
    RadioButton bueno_valvula_control,regular_valvula_control,malo_valvula_control;
    RadioButton bueno_conector_r_entrada,regular_conector_r_entrada,malo_conector_r_entrada;
    RadioButton bueno_repts_valvula_control,regular_repts_valvula_control,malo_repts_valvula_control;
    RadioButton bueno_silbato,regular_silbato,malo_silbato;
    RadioButton bueno_placa_twin,regular_placa_twin,malo_placa_twin;
    RadioButton bueno_conector_r_hembra,regular_conector_r_hembra,malo_conector_r_hembra;
    RadioButton bueno_conector_r_macho,regular_conector_r_macho,malo_conector_r_macho;
    RadioButton bueno_conector_aire_twin,regular_conector_aire_twin,malo_conector_aire_twin;
    RadioButton bueno_valvula_4salidas,regular_valvula_4salidas,malo_valvula_4salidas;
    RadioButton bueno_seguro_tapa,regular_seguro_tapa,malo_seguro_tapa;
    RadioButton bueno_valvula_1salida,regular_valvula_1salida,malo_valvula_1salida;
    RadioButton bueno_subplaca_sencilla,regular_subplaca_sencilla,malo_subplaca_sencilla;
    RadioButton bueno_seguro_valvula,regular_seguro_valvula,malo_seguro_valvula;
    RadioButton bueno_relay_sensor,regular_relay_sensor,malo_relay_sensor;
    RadioButton bueno_clamp_tuberia12,regular_clamp_tuberia12,malo_clamp_tuberia12;
    RadioButton bueno_celula_negativa,regular_celula_negativa,malo_celula_negativa;
    RadioButton bueno_clamp_tuberia516,regular_clamp_tuberia516,malo_clamp_tuberia516;
    RadioButton bueno_valvula_aguja,regular_valvula_aguja,malo_valvula_aguja;
    RadioButton bueno_detector_posicion,regular_detector_posicion,malo_detector_posicion;
    RadioButton bueno_valvula_impulso,regular_valvula_impulso,malo_valvula_impulso;
    RadioButton bueno_conector532,regular_conector532,malo_conector532;
    RadioButton bueno_patas_caucho,regular_patas_caucho,malo_patas_caucho;
    RadioButton bueno_acopladura_colder,regular_acopladura_colder,malo_acopladura_colder;
    RadioButton bueno_detertor_posiciones,regular_detertor_posiciones,malo_detertor_posiciones;
    RadioButton bueno_switch_3posiciones,regular_switch_3posiciones,malo_switch_3posiciones;
    RadioButton bueno_cilindro_fuerza,regular_cilindro_fuerza,malo_cilindro_fuerza;
    RadioButton bueno_regulador_aire,regular_regulador_aire,malo_regulador_aire;
    RadioButton bueno_cilindro_ajuste,regular_cilindro_ajuste,malo_cilindro_ajuste;
    RadioButton bueno_manometro,regular_manometro,malo_manometro;
    RadioButton bueno_conector_r_532,regular_conector_r_532,malo_conector_r_532;
    RadioButton bueno_contador_total,regular_contador_total,malo_contador_total;
    RadioButton bueno_conector_aire_bronce,regular_conector_aire_bronce,malo_conector_aire_bronce;
    RadioButton bueno_protector_cont_total,regular_protector_cont_total,malo_protector_cont_total;
    RadioButton bueno_sello_cilindro,regular_sello_cilindro,malo_sello_cilindro;
    RadioButton bueno_contador_prefijado,regular_contador_prefijado,malo_contador_prefijado;
    RadioButton bueno_retenedor_jeringa,regular_retenedor_jeringa,malo_retenedor_jeringa;
    RadioButton bueno_protector_cont_prefijado,regular_protector_cont_prefijado,malo_protector_cont_prefijado;
    RadioButton bueno_sostenedor_aguja,regular_sostenedor_aguja,malo_sostenedor_aguja;
    RadioButton bueno_reestablecedor_contador,regular_reestablecedor_contador,malo_reestablecedor_contador;
    RadioButton bueno_jeringa_descartable,regular_jeringa_descartable,malo_jeringa_descartable;
    RadioButton bueno_ubicacion_maquina,malo_ubicacion_maquina;
    RadioButton bueno_presion_compresora,malo_presion_compresora;
    RadioButton bueno_presion_maquina,malo_presion_maquina;
    RadioButton bueno_activacion,malo_activacion;
    RadioButton bueno_funcionamiento_contadores,malo_funcionamiento_contadores;
    RadioButton bueno_funcionamiento_silbato,malo_funcionamiento_silbato;
    RadioButton bueno_salida_aguja,malo_salida_aguja;
    RadioButton bueno_calibracion,malo_calibracion;
    RadioButton bueno_materiales_utilizados,malo_materiales_utilizados;
    RadioButton bueno_cambio_piezas,malo_cambio_piezas;
    RadioButton bueno_limpieza_placa,malo_limpieza_placa;
    RadioButton bueno_esterilizado_valvula,malo_esterilizado_valvula;
    RadioButton bueno_limpieza_modulo,malo_limpieza_modulo;
    RadioButton bueno_limpieza_cerebro,malo_limpieza_cerebro;
    RadioButton bueno_secado,malo_secado;
    RadioButton bueno_proteccion,malo_proteccion;

    private static final int Date_id = 0;
    private static final int Time_id = 1;
    private static final int Time_id_salida = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquina_twin_shot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoCompania=(AutoCompleteTextView)findViewById(R.id.autoCompania);
        autoMaquina=(AutoCompleteTextView)findViewById(R.id.autoMaquina);
        autoDireccion=(AutoCompleteTextView)findViewById(R.id.autoDireccion);
        autoPlantaEncubacion=(AutoCompleteTextView)findViewById(R.id.autoPlantaEncubacion);
        encargadoMaquinas=(EditText)findViewById(R.id.TextViewEncargado);
        horaIngreso=(Button)findViewById(R.id.et_hora_ingreso);
        horaSalida=(Button)findViewById(R.id.et_hora_salida);
        UltimaVisita=(EditText)findViewById(R.id.TextViewUltimaVisita);
        tv_fecha=(TextView) findViewById(R.id.fecha);

        Observacion_InspecionVisual=(EditText)findViewById(R.id.txtObservacionesInspeccionVisual);
        Piezas_Cambiadas_Inspeccion=(EditText)findViewById(R.id.txtPiezasCambiadasInspeccionVisual);
        Observaciones_Funcionamiento=(EditText)findViewById(R.id.txtObservacionesInspeccionFuncionamiento);
        Frecuencia_de_Uso=(EditText)findViewById(R.id.txtPiezasFrecuenciadeUsoInspeccionFuncionamiento);
        Observaciones_Limpieza=(EditText)findViewById(R.id.txtObservacionesLimpiezaDesinfeccion);
        CantidadAvesVacunadas=(EditText)findViewById(R.id.txtAvesVacunadasLimpiezaDesinfeccion);


        bueno_base_aluminio=(RadioButton)findViewById(R.id.BuenoBaseAluminio);
        //regular_base_aluminio=(RadioButton)findViewById(R.id.RegularBaseAluminio);
        malo_base_aluminio=(RadioButton)findViewById(R.id.MaloBaseAluminio);
        bueno_bolas_acero=(EditText)findViewById(R.id.BuenoBolitasAcero);
        //regular_bolas_acero=(EditText)findViewById(R.id.RegularBolitasAcero);
        malo_bolas_acero=(EditText)findViewById(R.id.MaloBolitasAcero);
        bueno_bloque_posterior=(RadioButton)findViewById(R.id.BuenoBloquePosteriror);
        //regular_bloque_posterior=(RadioButton)findViewById(R.id.RegularBloquePosteriror);
        malo_bloque_posterior=(RadioButton)findViewById(R.id.MaloBloquePosteriror);
        bueno_resortes=(EditText)findViewById(R.id.BuenoResortes);
        //regular_resortes=(EditText)findViewById(R.id.RegularResortes);
        malo_resortes=(EditText)findViewById(R.id.MaloResortes);
        bueno_bloque_cilindro=(RadioButton)findViewById(R.id.BuenoBloqueCilindro);
        //regular_bloque_cilindro=(RadioButton)findViewById(R.id.RegularBloqueCilindro);
        malo_bloque_cilindro=(RadioButton)findViewById(R.id.MaloBloqueCilindro);
        bueno_reguladores_salida=(RadioButton)findViewById(R.id.BuenoReguladoresdesalida);
        //regular_reguladores_salida=(RadioButton)findViewById(R.id.RegularReguladoresdesalida);
        malo_reguladores_salida=(RadioButton)findViewById(R.id.MaloReguladoresdesalida);
        bueno_bloque_jeringa=(RadioButton)findViewById(R.id.BuenoBloquedeJeringa);
        //regular_bloque_jeringa=(RadioButton)findViewById(R.id.RegularBloquedeJeringa);
        malo_bloque_jeringa=(RadioButton)findViewById(R.id.MaloBloquedeJeringa);

        bueno_acopladura_colder_hembra=(RadioButton)findViewById(R.id.BuenoAcopladuraColderHembra);
        //regular_acopladura_colder_hembra=(RadioButton)findViewById(R.id.RegularAcopladuraColderHembra);
        malo_acopladura_colder_hembra=(RadioButton)findViewById(R.id.MaloAcopladuraColderHembra);

        bueno_bloque_delantero=(RadioButton)findViewById(R.id.BuenoBoqueDelantero);
        //regular_bloque_delantero=(RadioButton)findViewById(R.id.RegularBoqueDelantero);
        malo_bloque_delantero=(RadioButton)findViewById(R.id.MaloBoqueDelantero);
        bueno_distribuidor_aire=(RadioButton)findViewById(R.id.BuenoDistribuidorAire);
        //regular_distribuidor_aire=(RadioButton)findViewById(R.id.RegularDistribuidorAire);
        malo_distribuidor_aire=(RadioButton)findViewById(R.id.MaloDistribuidorAire);
        bueno_valvula_control=(RadioButton)findViewById(R.id.BuenoValuvulaControl);
        //regular_valvula_control=(RadioButton)findViewById(R.id.RegularValuvulaControl);
        malo_valvula_control=(RadioButton)findViewById(R.id.MaloValuvulaControl);
        bueno_conector_r_entrada=(RadioButton)findViewById(R.id.BuenoConectorRapidodeEntrada);
        //regular_conector_r_entrada=(RadioButton)findViewById(R.id.RegularConectorRapidodeEntrada);
        malo_conector_r_entrada=(RadioButton)findViewById(R.id.MaloConectorRapidodeEntrada);
        bueno_silbato=(RadioButton)findViewById(R.id.BuenoSilbato);
        //regular_silbato=(RadioButton)findViewById(R.id.RegularSilbato);
        malo_silbato=(RadioButton)findViewById(R.id.MaloSilbato);
        bueno_repts_valvula_control=(RadioButton)findViewById(R.id.BuenoRPTOValvuladeControl);
        //regular_repts_valvula_control=(RadioButton)findViewById(R.id.RegularRPTOValvuladeControl);
        malo_repts_valvula_control=(RadioButton)findViewById(R.id.MaloRPTOValvuladeControl);
        bueno_conector_r_hembra=(RadioButton)findViewById(R.id.BuenoConectorRapidoHembra);
        //regular_conector_r_hembra=(RadioButton)findViewById(R.id.RegularConectorRapidoHembra);
        malo_conector_r_hembra=(RadioButton)findViewById(R.id.MaloConectorRapidoHembra);    //reparado
        bueno_placa_twin=(RadioButton)findViewById(R.id.BuenoPlacaTwinTouch);
        //regular_placa_twin=(RadioButton)findViewById(R.id.RegularPlacaTwinTouch);
        malo_placa_twin=(RadioButton)findViewById(R.id.MaloPlacaTwinTouch);
        bueno_conector_r_macho=(RadioButton)findViewById(R.id.BuenoConectorRapidoMacho);
        //regular_conector_r_macho=(RadioButton)findViewById(R.id.RegularConectorRapidoMacho);
        malo_conector_r_macho=(RadioButton)findViewById(R.id.MaloConectorRapidoMacho);
        bueno_bolitas_teflon=(EditText)findViewById(R.id.BuenoBolitasdeTeflon);
        //regular_bolitas_teflon=(EditText)findViewById(R.id.RegularBolitasdeTeflon);
        malo_bolitas_teflon=(EditText)findViewById(R.id.MaloBolitasdeTeflon);
        bueno_valvula_4salidas=(RadioButton)findViewById(R.id.BuenoValvula4Salidas);
        //regular_valvula_4salidas=(RadioButton)findViewById(R.id.RegularValvula4Salidas);
        malo_valvula_4salidas=(RadioButton)findViewById(R.id.MaloValvula4Salidas);
        bueno_conector_aire_twin=(RadioButton)findViewById(R.id.BuenoConectordeAireTwinShot);
        //regular_conector_aire_twin=(RadioButton)findViewById(R.id.RegularConectordeAireTwinShot);
        malo_conector_aire_twin=(RadioButton)findViewById(R.id.MaloConectordeAireTwinShot);
        bueno_valvula_1salida=(RadioButton)findViewById(R.id.BuenoValvulade1Salida);
        //regular_valvula_1salida=(RadioButton)findViewById(R.id.RegularValvulade1Salida);
        malo_valvula_1salida=(RadioButton)findViewById(R.id.MaloValvulade1Salida);
        bueno_seguro_tapa=(RadioButton)findViewById(R.id.BuenoSegurodeTapa);
        //regular_seguro_tapa=(RadioButton)findViewById(R.id.RegularSegurodeTapa);
        malo_seguro_tapa=(RadioButton)findViewById(R.id.MaloSegurodeTapa);
        bueno_subplaca_sencilla=(RadioButton)findViewById(R.id.BuenoSubplacaSencilla);
        //regular_subplaca_sencilla=(RadioButton)findViewById(R.id.RegularSubplacaSencilla);
        malo_subplaca_sencilla=(RadioButton)findViewById(R.id.MaloSubplacaSencilla);
        bueno_tuercas_b_aluminio=(EditText)findViewById(R.id.BuenoTuercasBaseAluminio);
        //regular_tuercas_b_aluminio=(EditText)findViewById(R.id.RegularTuercasBaseAluminio);
        malo_tuercas_b_aluminio=(EditText)findViewById(R.id.MaloTuercasBaseAluminio);
        bueno_relay_sensor=(RadioButton)findViewById(R.id.BuenoRelaySensor);
        //regular_relay_sensor=(RadioButton)findViewById(R.id.RegularRelaySensor);
        malo_relay_sensor=(RadioButton)findViewById(R.id.MaloRelaySensor);
        bueno_seguro_valvula=(RadioButton)findViewById(R.id.BuenoSegurodeValvula);
        //regular_seguro_valvula=(RadioButton)findViewById(R.id.RegularSegurodeValvula);
        malo_seguro_valvula=(RadioButton)findViewById(R.id.MaloSegurodeValvula);
        bueno_celula_negativa=(RadioButton)findViewById(R.id.BuenoCelulaNegativa);
        //regular_celula_negativa=(RadioButton)findViewById(R.id.RegularCelulaNegativa);
        malo_celula_negativa=(RadioButton)findViewById(R.id.MaloCelulaNegativa);
        bueno_clamp_tuberia12=(RadioButton)findViewById(R.id.BuenoClamTuberia);
        //regular_clamp_tuberia12=(RadioButton)findViewById(R.id.RegularClamTuberia);
        malo_clamp_tuberia12=(RadioButton)findViewById(R.id.MaloClamTuberia);
        bueno_clamp_tuberia516=(RadioButton)findViewById(R.id.BuenoClamTuberia516);
        //regular_clamp_tuberia516=(RadioButton)findViewById(R.id.RegularClamTuberia516);
        malo_clamp_tuberia516=(RadioButton)findViewById(R.id.MaloClamTuberia516);
        bueno_valvula_aguja=(RadioButton)findViewById(R.id.BuenoValvuladelaAguja);
        //regular_valvula_aguja=(RadioButton)findViewById(R.id.RegularValvuladelaAguja);
        malo_valvula_aguja=(RadioButton)findViewById(R.id.MaloValvuladelaAguja);
        bueno_detector_posicion=(RadioButton)findViewById(R.id.BuenoDetectordePosicion);
        //regular_detector_posicion=(RadioButton)findViewById(R.id.RegularDetectordePosicion);
        malo_detector_posicion=(RadioButton)findViewById(R.id.MaloDetectordePosicion);
        bueno_valvula_impulso=(RadioButton)findViewById(R.id.BuenoValvuladeImpulso);
        //regular_valvula_impulso=(RadioButton)findViewById(R.id.RegularValvuladeImpulso);
        malo_valvula_impulso=(RadioButton)findViewById(R.id.MaloValvuladeImpulso);
        bueno_conector532=(RadioButton)findViewById(R.id.BuenoConector532);
        //regular_conector532=(RadioButton)findViewById(R.id.RegularConector532);
        malo_conector532=(RadioButton)findViewById(R.id.MaloConector532);
        bueno_patas_caucho=(RadioButton)findViewById(R.id.BuenoPatasdeCaucho);
        //regular_patas_caucho=(RadioButton)findViewById(R.id.RegularPatasdeCaucho);
        malo_patas_caucho=(RadioButton)findViewById(R.id.MaloPatasdeCaucho);
        //bueno_detertor_posiciones=(RadioButton)findViewById(R.id.BuenoDetectordePosicion);
        //regular_detertor_posiciones=(RadioButton)findViewById(R.id.RegularDetectordePosicion);
        //malo_detertor_posiciones=(RadioButton)findViewById(R.id.MaloDetectordePosicion);
        bueno_acopladura_colder=(RadioButton)findViewById(R.id.BuenoAcopladuraColderMacho);
        //regular_acopladura_colder=(RadioButton)findViewById(R.id.RegularAcopladuraColderMacho);
        malo_acopladura_colder=(RadioButton)findViewById(R.id.MaloAcopladuraColderMacho);
        bueno_switch_3posiciones=(RadioButton)findViewById(R.id.BuenoSwitchTresPosiciones);
        //regular_switch_3posiciones=(RadioButton)findViewById(R.id.RegularSwitchTresPosiciones);
        malo_switch_3posiciones=(RadioButton)findViewById(R.id.MaloSwitchTresPosiciones);
        bueno_cilindro_fuerza=(RadioButton)findViewById(R.id.BuenoCilindrodeFuerza);
        //regular_cilindro_fuerza=(RadioButton)findViewById(R.id.RegularCilindrodeFuerza);
        malo_cilindro_fuerza=(RadioButton)findViewById(R.id.MaloCilindrodeFuerza);
        bueno_regulador_aire=(RadioButton)findViewById(R.id.BuenoReguladordeAire);
        //regular_regulador_aire=(RadioButton)findViewById(R.id.RegularReguladordeAire);
        malo_regulador_aire=(RadioButton)findViewById(R.id.MaloReguladordeAire);
        bueno_cilindro_ajuste=(RadioButton)findViewById(R.id.BuenoCilindrodeAjuste);
        //regular_cilindro_ajuste=(RadioButton)findViewById(R.id.RegularCilindrodeAjuste);
        malo_cilindro_ajuste=(RadioButton)findViewById(R.id.MaloCilindrodeAjuste);
        bueno_manometro=(RadioButton)findViewById(R.id.BuenoManometro);
        //regular_manometro=(RadioButton)findViewById(R.id.RegularManometro);
        malo_manometro=(RadioButton)findViewById(R.id.MaloManometro);
        bueno_conector_r_532=(RadioButton)findViewById(R.id.BuenoConectorRapido532);
        //regular_conector_r_532=(RadioButton)findViewById(R.id.RegularConector532);
        malo_conector_r_532=(RadioButton)findViewById(R.id.MaloConectorRapido532);
        bueno_contador_total=(RadioButton)findViewById(R.id.BuenoContadorTotal);
        //regular_contador_total=(RadioButton)findViewById(R.id.RegularContadorTotal);
        malo_contador_total=(RadioButton)findViewById(R.id.MaloContadorTotal);
        bueno_conector_aire_bronce=(RadioButton)findViewById(R.id.BuenoConectordeAiredeBronce);
        //regular_conector_aire_bronce=(RadioButton)findViewById(R.id.RegularConectordeAiredeBronce);
        malo_conector_aire_bronce=(RadioButton)findViewById(R.id.MaloConectordeAiredeBronce);
        bueno_protector_cont_total=(RadioButton)findViewById(R.id.BuenoProtectorContadorTotal);
        //regular_protector_cont_total=(RadioButton)findViewById(R.id.RegularProtectorContadorTotal);
        malo_protector_cont_total=(RadioButton)findViewById(R.id.MaloProtectorContadorTotal);
        bueno_sello_cilindro=(RadioButton)findViewById(R.id.BuenoSellodeCilindrodeFuerza);
        //regular_sello_cilindro=(RadioButton)findViewById(R.id.RegularSellodeCilindrodeFuerza);
        malo_sello_cilindro=(RadioButton)findViewById(R.id.MaloSellodeCilindrodeFuerza);
        bueno_contador_prefijado=(RadioButton)findViewById(R.id.BuenoContadorPrefijado);
        //regular_contador_prefijado=(RadioButton)findViewById(R.id.RegularContadorPrefijado);
        malo_contador_prefijado=(RadioButton)findViewById(R.id.MaloContadorPrefijado);
        bueno_retenedor_jeringa=(RadioButton)findViewById(R.id.BuenoRetenedordeJeringa);
        //regular_retenedor_jeringa=(RadioButton)findViewById(R.id.RegularRetenedordeJeringa);
        malo_retenedor_jeringa=(RadioButton)findViewById(R.id.MaloRetenedordeJeringa);
        bueno_protector_cont_prefijado=(RadioButton)findViewById(R.id.BuenoProtectorContadorPrefijado);
        //regular_protector_cont_prefijado=(RadioButton)findViewById(R.id.RegularProtectorContadorPrefijado);
        malo_protector_cont_prefijado=(RadioButton)findViewById(R.id.MaloProtectorContadorPrefijado);
        bueno_sostenedor_aguja=(RadioButton)findViewById(R.id.BuenoSostenedorDelaAguja);
        //regular_sostenedor_aguja=(RadioButton)findViewById(R.id.RegularSostenedorDelaAguja);
        malo_sostenedor_aguja=(RadioButton)findViewById(R.id.MaloSostenedorDelaAguja);//
        bueno_reestablecedor_contador=(RadioButton)findViewById(R.id.BuenoRestablecedordeContador);
        //regular_reestablecedor_contador=(RadioButton)findViewById(R.id.RegularRestablecedordeContador);
        malo_reestablecedor_contador=(RadioButton)findViewById(R.id.MaloRestablecedordeContador);
        bueno_jeringa_descartable=(RadioButton)findViewById(R.id.BuenoJeringaDescartable);
        //regular_jeringa_descartable=(RadioButton)findViewById(R.id.RegularJeringaDescartable);
        malo_jeringa_descartable=(RadioButton)findViewById(R.id.MaloJeringaDescartable);
        bueno_tuerca_collarin=(EditText)findViewById(R.id.BuenoTuercaCollarindeAguja);
        //regular_tuerca_collarin=(EditText)findViewById(R.id.RegularTuercaCollarindeAguja);
        malo_tuerca_collarin=(EditText)findViewById(R.id.MaloTuercaCollarindeAguja);
        bueno_empaque_asiento=(EditText)findViewById(R.id.BuenoEmpaqueoAsiento);
        //regular_empaque_asiento=(EditText)findViewById(R.id.RegularEmpaqueoAsiento);
        malo_empaque_asiento=(EditText)findViewById(R.id.MaloEmpaqueoAsiento);
        bueno_empaque_captura=(EditText)findViewById(R.id.BuenoEmpaqueoCaptura);
        //regular_empaque_captura=(EditText)findViewById(R.id.RegularEmpaqueoCaptura);
        malo_empaque_captura=(EditText)findViewById(R.id.MaloEmpaqueoCaptura);
        bueno_tuberia_pequena=(EditText)findViewById(R.id.BuenoTuberiaPequena);
        //regular_tuberia_pequena=(EditText)findViewById(R.id.RegularTuberiaPequena);
        malo_tuberia_pequena=(EditText)findViewById(R.id.MaloTuberiaPequena);
        bueno_tuberia_latex=(EditText)findViewById(R.id.BuenoTuberiaLatex);
        //regular_tuberia_latex=(EditText)findViewById(R.id.RegularTuberiaLatex);
        malo_tuberia_latex=(EditText)findViewById(R.id.MaloTuberiaLatex);
        bueno_ubicacion_maquina=(RadioButton)findViewById(R.id.BuenoUbicaciondelaMAquina);
        malo_ubicacion_maquina=(RadioButton)findViewById(R.id.MaloUbicaciondelaMAquina);
        bueno_presion_compresora=(RadioButton)findViewById(R.id.BuenoPresionCompresora);
        malo_presion_compresora=(RadioButton)findViewById(R.id.MaloPresionCompresora);
        bueno_presion_maquina=(RadioButton)findViewById(R.id.BuenoPresionMAquina);
        malo_presion_maquina=(RadioButton)findViewById(R.id.MaloPresionMAquina);
        bueno_activacion=(RadioButton)findViewById(R.id.BuenoActivacion);
        malo_activacion=(RadioButton)findViewById(R.id.MaloActivacion);
        bueno_funcionamiento_contadores=(RadioButton)findViewById(R.id.BuenoFuncionesDeContadores);
        malo_funcionamiento_contadores=(RadioButton)findViewById(R.id.MaloFuncionesDeContadores);
        bueno_funcionamiento_silbato=(RadioButton)findViewById(R.id.BuenoFuncionesdeSilvato);
        malo_funcionamiento_silbato=(RadioButton)findViewById(R.id.MaloFuncionesdeSilvato);
        bueno_salida_aguja=(RadioButton)findViewById(R.id.BuenoSalidadeAguja);
        malo_salida_aguja=(RadioButton)findViewById(R.id.MaloSalidadeAguja);
        bueno_calibracion=(RadioButton)findViewById(R.id.BuenoCalibracion);
        malo_calibracion=(RadioButton)findViewById(R.id.MaloCalibracion);
        bueno_materiales_utilizados=(RadioButton)findViewById(R.id.BuenoMaterialesUtilizados);
        malo_materiales_utilizados=(RadioButton)findViewById(R.id.MaloMaterialesUtilizados);
        bueno_cambio_piezas=(RadioButton)findViewById(R.id.BuenoCambioPiezasDescartables);
        malo_cambio_piezas=(RadioButton)findViewById(R.id.MaloCambioPiezasDescartables);
        bueno_limpieza_placa=(RadioButton)findViewById(R.id.BuenoLimpiezadePlacaTwin);
        malo_limpieza_placa=(RadioButton)findViewById(R.id.MaloLimpiezadePlacaTwin);
        bueno_esterilizado_valvula=(RadioButton)findViewById(R.id.BuenoEsterilizadodeValvula);
        malo_esterilizado_valvula=(RadioButton)findViewById(R.id.MaloEsterilizadodeValvula);
        bueno_limpieza_modulo=(RadioButton)findViewById(R.id.BuenoLimpiezaModuloInyector);
        malo_limpieza_modulo=(RadioButton)findViewById(R.id.MaloLimpiezaModuloInyector);
        bueno_limpieza_cerebro=(RadioButton)findViewById(R.id.BuenoLimpiezadelCerebro);
        malo_limpieza_cerebro=(RadioButton)findViewById(R.id.MaloLimpiezadelCerebro);
        bueno_secado=(RadioButton)findViewById(R.id.BuenoSecado);
        malo_secado=(RadioButton)findViewById(R.id.MaloSecado);
        bueno_proteccion=(RadioButton)findViewById(R.id.BuenoProteccion);
        malo_proteccion=(RadioButton)findViewById(R.id.MaloProteccion);



        bt_firmar_1=(Button)findViewById(R.id.bt_firmar_1);
        bt_firmar_2=(Button)findViewById(R.id.bt_firmar_2);


        im_firma_2=(ImageView)findViewById(R.id.im_firma_2);
        im_firma_1=(ImageView)findViewById(R.id.im_firma_1);
        im_foto_jefe=(ImageView)findViewById(R.id.im_foto_jefe);

        // scompania=(Spinner)findViewById(R.id.compania);
        cargar_compania_en_la_lista();
        cargar_maquina_en_la_lista();

        Calendar c=Calendar.getInstance();
        tv_fecha.setText(""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));


        im_foto_jefe.setOnClickListener(this);
        bt_firmar_1.setOnClickListener(this);
        bt_firmar_2.setOnClickListener(this);

        Calendar ca=Calendar.getInstance();
        tv_fecha.setText(""+ca.get(Calendar.YEAR)+"-"+(ca.get(Calendar.MONTH)+1)+"-"+ca.get(Calendar.DAY_OF_MONTH));
        //---------------------------CODIGO PARA LOS DIALOGOS DE HORA Y FECHA---------------------------------
        tv_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show Date dialog
                showDialog(Date_id);
            }
        });

        horaIngreso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id);
            }
        });

        horaSalida.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id_salida);
            }
        });

//obtenemos e imei
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei=telephonyManager.getDeviceId();

        if(imei.equals("")==true)
        {
            mensaje_ok_cerrar("Necesita dar permisos para Obtener el número de Imei.");
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
    private void guardar_formulario() {
        boolean sw_tw=false,sw_verificar_datos=false;
        int id_servicio=id_servicio_mantenimiento()+1;
        if (id_servicio == 1)
        {
            SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
            id_servicio=perfil.getInt("id_servicio_mantenimiento",1);
        }

        int id_maquina=id_maquina_por_codigo(autoMaquina.getText().toString());

        int id_compania=id_compania_por_nombre(autoCompania.getText().toString());

        //id TECNICO...
        SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
        int id_tecnico=perfil.getInt("id",-1);
        sw_verificar_datos=verificar_datos_se(id_maquina,id_compania,id_tecnico,imei,bm_firma_1,bm_firma_2);
        if(sw_verificar_datos) {
            String DIRECCION_FIRMA_JEFE = "Invetsa/Servicio_mantenimiento/" + id_servicio + "_jefe_" + tv_fecha.getText() + ".jpeg";
            String DIRECCION_FIRMA_INVETSA = "Invetsa/Servicio_mantenimiento/" + id_servicio + "_invetsa_" + tv_fecha.getText() + ".jpeg";
            String DIRECCION_FOTO_JEFE = "Invetsa/Servicio_mantenimiento/" + id_servicio + "_foto_jefe_" + tv_fecha.getText() + ".jpeg";

            guardar_en_memoria(bm_firma_1, id_servicio + "_jefe_" + tv_fecha.getText() + ".jpeg", "Invetsa/Servicio_mantenimiento");
            guardar_en_memoria(bm_firma_2, id_servicio + "_invetsa_" + tv_fecha.getText() + ".jpeg", "Invetsa/Servicio_mantenimiento");
            guardar_en_memoria(bm_foto_jefe, id_servicio + "_foto_jefe_" + tv_fecha.getText() + ".jpeg", "Invetsa/Servicio_mantenimiento");

            String codigo = getString(R.string.codigo_twin_shot);
            String revision = getString(R.string.revision_twin_shot);

            sw_tw = guardar_servicio_mantenimiento(id_servicio, tv_fecha.getText().toString(), horaIngreso.getText().toString(), horaSalida.getText().toString(), codigo, revision, DIRECCION_FIRMA_JEFE, DIRECCION_FIRMA_INVETSA, String.valueOf(id_maquina), String.valueOf(id_tecnico), String.valueOf(id_compania), "TWIN SHOT",DIRECCION_FOTO_JEFE);
            if (sw_tw) {
                int id_inspeccion = 1;
                guardar_inspeccion_visual(id_inspeccion, Observacion_InspecionVisual.getText().toString(), Piezas_Cambiadas_Inspeccion.getText().toString(), id_servicio);

                guardar_detalle_inspeccion_visual(1, id_inspeccion, id_servicio, "AV-131", "BASE DE ALUMINIO", get_estado(bueno_base_aluminio.isChecked(), malo_base_aluminio.isChecked()));
                guardar_detalle_inspeccion_visual(2, id_inspeccion, id_servicio, "AV-1331", "BLOQUE POSTERIOR", get_estado(bueno_bloque_posterior.isChecked(), malo_bloque_posterior.isChecked()));
                guardar_detalle_inspeccion_visual(3, id_inspeccion, id_servicio, "AV-13413", "BLOQUE DE CILINDRO DE FUERZA", get_estado(bueno_bloque_cilindro.isChecked(), malo_bloque_cilindro.isChecked()));
                guardar_detalle_inspeccion_visual(4, id_inspeccion, id_servicio, "AV-13421", "BLOQUE DE JERINGA", get_estado(bueno_bloque_jeringa.isChecked(), malo_bloque_jeringa.isChecked()));
                guardar_detalle_inspeccion_visual(5, id_inspeccion, id_servicio, "AV-13511", "BLOQUE DELANTERO GUIA", get_estado(bueno_bloque_delantero.isChecked(), malo_bloque_delantero.isChecked()));
                guardar_detalle_inspeccion_visual(6, id_inspeccion, id_servicio, "AV-137", "VALVULA DE CONTROL", get_estado(bueno_valvula_control.isChecked(), malo_valvula_control.isChecked()));
                guardar_detalle_inspeccion_visual(7, id_inspeccion, id_servicio, "AV-1378", "REPTS. DE VALVULA DE CONTROL", get_estado(bueno_repts_valvula_control.isChecked(), malo_repts_valvula_control.isChecked()));
                guardar_detalle_inspeccion_visual(8, id_inspeccion, id_servicio, "AV-111", "PLACA TWIN TOUCH", get_estado(bueno_placa_twin.isChecked(), malo_placa_twin.isChecked()));

                guardar_detalle_inspeccion_visual(9, id_inspeccion, id_servicio, "AV-114", "BOLITAS DE TEFLON (bueno)", Integer.parseInt(bueno_bolitas_teflon.getText().toString()));
                //guardar_detalle_inspeccion_visual(10, id_inspeccion, id_servicio, "AV-114", "BOLITAS DE TEFLON (regular)", Integer.parseInt(regular_bolitas_teflon.getText().toString()));
                guardar_detalle_inspeccion_visual(11, id_inspeccion, id_servicio, "AV-114", "BOLITAS DE TEFLON (malo)", Integer.parseInt(malo_bolitas_teflon.getText().toString()));


                guardar_detalle_inspeccion_visual(12, id_inspeccion, id_servicio, "AV-117", "CONECTOR DE AIRE DE TWIN SHOT", get_estado(bueno_conector_aire_twin.isChecked(), malo_conector_aire_twin.isChecked()));
                guardar_detalle_inspeccion_visual(13, id_inspeccion, id_servicio, "AV-121", "SEGURO DE TAPA", get_estado(bueno_seguro_tapa.isChecked(), malo_seguro_tapa.isChecked()));

                guardar_detalle_inspeccion_visual(14, id_inspeccion, id_servicio, "AV-118", "TUERCAS DE BASE DE ALUMINIO (bueno)", Integer.parseInt(bueno_tuercas_b_aluminio.getText().toString()));
                //guardar_detalle_inspeccion_visual(15, id_inspeccion, id_servicio, "AV-118", "TUERCAS DE BASE DE ALUMINIO (regular)", Integer.parseInt(regular_tuercas_b_aluminio.getText().toString()));
                guardar_detalle_inspeccion_visual(16, id_inspeccion, id_servicio, "AV-118", "TUERCAS DE BASE DE ALUMINIO (malo)", Integer.parseInt(malo_tuercas_b_aluminio.getText().toString()));

                guardar_detalle_inspeccion_visual(17, id_inspeccion, id_servicio, "AV-1357", "SEGURO DE VALVULA", get_estado(bueno_seguro_valvula.isChecked(), malo_seguro_valvula.isChecked()));
                guardar_detalle_inspeccion_visual(18, id_inspeccion, id_servicio, "5647", "CLAMP DE TUBERIA DE 1/2", get_estado(bueno_clamp_tuberia12.isChecked(), malo_clamp_tuberia12.isChecked()));
                guardar_detalle_inspeccion_visual(19, id_inspeccion, id_servicio, "5548", "CLAMP DE TUBERIA DE 5/16", get_estado(bueno_clamp_tuberia516.isChecked(), malo_clamp_tuberia516.isChecked()));
                guardar_detalle_inspeccion_visual(20, id_inspeccion, id_servicio, "5732", "DETECTOR DE POSICION", get_estado(bueno_detector_posicion.isChecked(), malo_detector_posicion.isChecked()));
                guardar_detalle_inspeccion_visual(21, id_inspeccion, id_servicio, "3543", "CONECTOR DE 5/32", get_estado(bueno_conector532.isChecked(), malo_conector532.isChecked()));
                guardar_detalle_inspeccion_visual(22, id_inspeccion, id_servicio, "AV-1346", "ACOPLADURA COLDER MACHO", get_estado(bueno_acopladura_colder.isChecked(), malo_acopladura_colder.isChecked()));
                guardar_detalle_inspeccion_visual(23, id_inspeccion, id_servicio, "AV-1344", "CILINDRO DE FUERZA", get_estado(bueno_cilindro_fuerza.isChecked(), malo_cilindro_fuerza.isChecked()));
                guardar_detalle_inspeccion_visual(24, id_inspeccion, id_servicio, "AV-1345", "CILINDRO DE AJUSTE", get_estado(bueno_cilindro_ajuste.isChecked(), malo_cilindro_ajuste.isChecked()));
                guardar_detalle_inspeccion_visual(25, id_inspeccion, id_servicio, "5720", "CONECTOR RAPIDO 5/32", get_estado(bueno_conector_r_532.isChecked(), malo_conector_r_532.isChecked()));
                guardar_detalle_inspeccion_visual(26, id_inspeccion, id_servicio, "5641", "CONECTOR DE AIRE DE BRONCE", get_estado(bueno_conector_aire_bronce.isChecked(), malo_conector_aire_bronce.isChecked()));
                guardar_detalle_inspeccion_visual(27, id_inspeccion, id_servicio, "AV-13411", "SELLO DE CILINDRO DE FUERZA", get_estado(bueno_sello_cilindro.isChecked(), malo_sello_cilindro.isChecked()));
                guardar_detalle_inspeccion_visual(28, id_inspeccion, id_servicio, "AV-13422", "RETENEDOR DE JERINGA", get_estado(bueno_retenedor_jeringa.isChecked(), malo_retenedor_jeringa.isChecked()));
                guardar_detalle_inspeccion_visual(29, id_inspeccion, id_servicio, "AV-13521", "SOSTENEDOR DE LA AGUJA", get_estado(bueno_sostenedor_aguja.isChecked(), malo_sostenedor_aguja.isChecked()));

                guardar_detalle_inspeccion_visual(30, id_inspeccion, id_servicio, "AV-13522", "TUERCA COLLARIN DE LA AGUJA (bueno)", Integer.parseInt(bueno_tuerca_collarin.getText().toString()));
                //guardar_detalle_inspeccion_visual(31, id_inspeccion, id_servicio, "AV-13522", "TUERCA COLLARIN DE LA AGUJA (regular)", Integer.parseInt(regular_tuerca_collarin.getText().toString()));
                guardar_detalle_inspeccion_visual(32, id_inspeccion, id_servicio, "AV-13522", "TUERCA COLLARIN DE LA AGUJA (malo)", Integer.parseInt(malo_tuerca_collarin.getText().toString()));

                guardar_detalle_inspeccion_visual(33, id_inspeccion, id_servicio, "AV-1374", "EMPAQUE O ASIENTO (bueno)", Integer.parseInt(bueno_empaque_asiento.getText().toString()));
                //guardar_detalle_inspeccion_visual(34, id_inspeccion, id_servicio, "AV-1374", "EMPAQUE O ASIENTO (regular)", Integer.parseInt(regular_empaque_asiento.getText().toString()));
                guardar_detalle_inspeccion_visual(35, id_inspeccion, id_servicio, "AV-1374", "EMPAQUE O ASIENTO (malo)", Integer.parseInt(malo_empaque_asiento.getText().toString()));

                guardar_detalle_inspeccion_visual(36, id_inspeccion, id_servicio, "AV-1375", "EMPAQUE O CAPTURA (bueno)", Integer.parseInt(bueno_empaque_captura.getText().toString()));
                //guardar_detalle_inspeccion_visual(37, id_inspeccion, id_servicio, "AV-1375", "EMPAQUE O CAPTURA (regular)", Integer.parseInt(regular_empaque_captura.getText().toString()));
                guardar_detalle_inspeccion_visual(38, id_inspeccion, id_servicio, "AV-1375", "EMPAQUE O CAPTURA (malo)", Integer.parseInt(malo_empaque_captura.getText().toString()));

                guardar_detalle_inspeccion_visual(39, id_inspeccion, id_servicio, "AV-1377", "BOLITAS DE ACERO (bueno)", Integer.parseInt(bueno_bolas_acero.getText().toString()));
                //guardar_detalle_inspeccion_visual(40, id_inspeccion, id_servicio, "AV-1377", "BOLITAS DE ACERO (regular)", Integer.parseInt(regular_bolas_acero.getText().toString()));
                guardar_detalle_inspeccion_visual(41, id_inspeccion, id_servicio, "AV-1377", "BOLITAS DE ACERO (malo)", Integer.parseInt(malo_bolas_acero.getText().toString()));

                guardar_detalle_inspeccion_visual(42, id_inspeccion, id_servicio, "AV-1376", "RESORTES (bueno)", Integer.parseInt(bueno_resortes.getText().toString()));
                //guardar_detalle_inspeccion_visual(43, id_inspeccion, id_servicio, "AV-1376", "RESORTES (regular)", Integer.parseInt(regular_resortes.getText().toString()));
                guardar_detalle_inspeccion_visual(44, id_inspeccion, id_servicio, "AV-1376", "RESORTES (malo)", Integer.parseInt(malo_resortes.getText().toString()));

                guardar_detalle_inspeccion_visual(45, id_inspeccion, id_servicio, "AV-13523", "REGULADORES SALIDA DE AGUJA", get_estado(bueno_reguladores_salida.isChecked(), malo_reguladores_salida.isChecked()));
                guardar_detalle_inspeccion_visual(46, id_inspeccion, id_servicio, "AV-1347", "ACOPLADURA COLDER HEMBRA", get_estado(bueno_acopladura_colder_hembra.isChecked(), malo_acopladura_colder_hembra.isChecked()));
                guardar_detalle_inspeccion_visual(47, id_inspeccion, id_servicio, "AV-230", "DISTRIBUIDOR DE AIRE", get_estado(bueno_distribuidor_aire.isChecked(), malo_distribuidor_aire.isChecked()));
                guardar_detalle_inspeccion_visual(48, id_inspeccion, id_servicio, "AV-1032", "CONECTOR RAPIDO ENTRADA", get_estado(bueno_conector_r_entrada.isChecked(), malo_conector_r_entrada.isChecked()));
                guardar_detalle_inspeccion_visual(49, id_inspeccion, id_servicio, "5430", "SILBATO", get_estado(bueno_silbato.isChecked(), malo_silbato.isChecked()));
                guardar_detalle_inspeccion_visual(50, id_inspeccion, id_servicio, "5443", "CONECTOR RAPIDO HEMBRA", get_estado(bueno_conector_r_hembra.isChecked(), malo_conector_r_hembra.isChecked()));
                guardar_detalle_inspeccion_visual(51, id_inspeccion, id_servicio, "5445", "CONECTOR RAPIDO MACHO", get_estado(bueno_conector_r_macho.isChecked(), malo_conector_r_macho.isChecked()));
                guardar_detalle_inspeccion_visual(52, id_inspeccion, id_servicio, "5728", "VALVULA DE CUATRO SALIDAS", get_estado(bueno_valvula_4salidas.isChecked(), malo_valvula_4salidas.isChecked()));
                guardar_detalle_inspeccion_visual(53, id_inspeccion, id_servicio, "5700", "VALVULA DE UNA SALIDA", get_estado(bueno_valvula_1salida.isChecked(), malo_valvula_1salida.isChecked()));
                guardar_detalle_inspeccion_visual(54, id_inspeccion, id_servicio, "5709", " SUBPLACA SENCILLA", get_estado(bueno_subplaca_sencilla.isChecked(), malo_subplaca_sencilla.isChecked()));
                guardar_detalle_inspeccion_visual(55, id_inspeccion, id_servicio, "5730", "RELAY SENSOR", get_estado(bueno_relay_sensor.isChecked(), malo_relay_sensor.isChecked()));
                guardar_detalle_inspeccion_visual(56, id_inspeccion, id_servicio, "5701", "CELULA NEGATIVA", get_estado(bueno_celula_negativa.isChecked(), malo_celula_negativa.isChecked()));
                guardar_detalle_inspeccion_visual(57, id_inspeccion, id_servicio, "5738", "VALVULA DE LA AGUJA", get_estado(bueno_valvula_aguja.isChecked(), malo_valvula_aguja.isChecked()));
                guardar_detalle_inspeccion_visual(58, id_inspeccion, id_servicio, "5703", "VALVULA DE IMPULSO", get_estado(bueno_valvula_impulso.isChecked(), malo_valvula_impulso.isChecked()));
                guardar_detalle_inspeccion_visual(59, id_inspeccion, id_servicio, "5600", "PATAS DE CAUCHO", get_estado(bueno_patas_caucho.isChecked(), malo_patas_caucho.isChecked()));
                guardar_detalle_inspeccion_visual(60, id_inspeccion, id_servicio, "5710", "SWITCH DE TRES POSICIONES", get_estado(bueno_switch_3posiciones.isChecked(), malo_switch_3posiciones.isChecked()));
                guardar_detalle_inspeccion_visual(61, id_inspeccion, id_servicio, "5726", "REGULADOR DE AIRE", get_estado(bueno_regulador_aire.isChecked(), malo_regulador_aire.isChecked()));
                guardar_detalle_inspeccion_visual(62, id_inspeccion, id_servicio, "5724", "MANOMETRO", get_estado(bueno_manometro.isChecked(), malo_manometro.isChecked()));
                guardar_detalle_inspeccion_visual(63, id_inspeccion, id_servicio, "5423", "CONTADOR TOTAL", get_estado(bueno_contador_total.isChecked(), malo_contador_total.isChecked()));
                guardar_detalle_inspeccion_visual(64, id_inspeccion, id_servicio, "5426", "PROTECTOR DE CONT. TOTAL", get_estado(bueno_protector_cont_total.isChecked(), malo_protector_cont_total.isChecked()));
                guardar_detalle_inspeccion_visual(65, id_inspeccion, id_servicio, "5422", "CONTADOR PREFIJADO", get_estado(bueno_contador_prefijado.isChecked(), malo_contador_prefijado.isChecked()));
                guardar_detalle_inspeccion_visual(66, id_inspeccion, id_servicio, "5418", "RE-ESTABLECEDOR DE CONTADOR", get_estado(bueno_reestablecedor_contador.isChecked(), malo_reestablecedor_contador.isChecked()));
                guardar_detalle_inspeccion_visual(67, id_inspeccion, id_servicio, "5425", "PROTECTOR DE CONT.PREFIJADO", get_estado(bueno_protector_cont_prefijado.isChecked(), malo_protector_cont_prefijado.isChecked()));
                guardar_detalle_inspeccion_visual(68, id_inspeccion, id_servicio, "AV-138", "JERINGA DESCARTABLE", get_estado(bueno_jeringa_descartable.isChecked(), malo_jeringa_descartable.isChecked()));

                guardar_detalle_inspeccion_visual(69, id_inspeccion, id_servicio, "3520", "TUBERIA PEQUENA (bueno)", Integer.parseInt(bueno_tuberia_pequena.getText().toString()));
                //guardar_detalle_inspeccion_visual(70, id_inspeccion, id_servicio, "3520", "TUBERIA PEQUENA (regular)", Integer.parseInt(regular_tuberia_pequena.getText().toString()));
                guardar_detalle_inspeccion_visual(71, id_inspeccion, id_servicio, "3520", "TUBERIA PEQUENA (malo)", Integer.parseInt(malo_tuberia_pequena.getText().toString()));

                guardar_detalle_inspeccion_visual(72, id_inspeccion, id_servicio, "SV-11155", "TUBERIA LATEX (bueno)", Integer.parseInt(bueno_tuberia_latex.getText().toString()));
                //guardar_detalle_inspeccion_visual(73, id_inspeccion, id_servicio, "SV-11155", "TUBERIA LATEX (regular)", Integer.parseInt(regular_tuberia_latex.getText().toString()));
                guardar_detalle_inspeccion_visual(74, id_inspeccion, id_servicio, "SV-11155", "TUBERIA LATEX (malo)", Integer.parseInt(malo_tuberia_latex.getText().toString()));

                ///REGISTRO DE INSPECCION E FUNCIONAMIENTO
                int id_funcionamiento = 1;
                guardar_inspeccion_funcionamiento(id_funcionamiento, Observaciones_Funcionamiento.getText().toString(), Frecuencia_de_Uso.getText().toString(), id_servicio);

                //REGISTO DE LOS DETALLES DE INSPECCION EL  FUNCIONAMIENTO,
                guardar_detalle_inspeccion_funcionamiento(1, id_funcionamiento, id_servicio, "UBICACION DE MAQUINA", get_estado(bueno_ubicacion_maquina.isChecked(), malo_ubicacion_maquina.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(2, id_funcionamiento, id_servicio, "PRESION DE COMPRESORA", get_estado(bueno_presion_compresora.isChecked(), malo_presion_compresora.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(3, id_funcionamiento, id_servicio, "PRESION DE MAQUINA", get_estado(bueno_presion_maquina.isChecked(), malo_presion_maquina.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(4, id_funcionamiento, id_servicio, "ACTIVACION", get_estado(bueno_activacion.isChecked(), malo_activacion.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(5, id_funcionamiento, id_servicio, "FUNCIONAMIENTO DE CONTADORES", get_estado(bueno_funcionamiento_contadores.isChecked(), malo_funcionamiento_contadores.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(6, id_funcionamiento, id_servicio, "FUNCIONAMIENTO DE SILBATO", get_estado(bueno_funcionamiento_silbato.isChecked(), malo_funcionamiento_silbato.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(7, id_funcionamiento, id_servicio, "SALIDA DE AGUJA", get_estado(bueno_salida_aguja.isChecked(), malo_salida_aguja.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(8, id_funcionamiento, id_servicio, "CALIBRACION", get_estado(bueno_calibracion.isChecked(), malo_calibracion.isChecked()));

                ///REGISTRO DE LIMPIEZA Y DESINFECCION
                id_funcionamiento = 2;
                guardar_inspeccion_funcionamiento(id_funcionamiento, Observaciones_Limpieza.getText().toString(), CantidadAvesVacunadas.getText().toString(), id_servicio);

                //REGISTO DETALLE DE LIMPIEZA Y DESINFECCION,
                guardar_detalle_inspeccion_funcionamiento(1, id_funcionamiento, id_servicio, "MATERIALES UTILIZADOS", get_estado(bueno_materiales_utilizados.isChecked(), malo_materiales_utilizados.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(2, id_funcionamiento, id_servicio, "CAMBIO DE PIEZAS DESCARTABLES", get_estado(bueno_cambio_piezas.isChecked(), malo_cambio_piezas.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(3, id_funcionamiento, id_servicio, "LIMPIEZA DE PLACA TWIN TOUCH", get_estado(bueno_limpieza_placa.isChecked(), malo_limpieza_placa.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(4, id_funcionamiento, id_servicio, "ESTERILIZADO DE VALVULA", get_estado(bueno_esterilizado_valvula.isChecked(), malo_esterilizado_valvula.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(5, id_funcionamiento, id_servicio, "LIMPIEZA DE MODULO INYECTOR", get_estado(bueno_limpieza_modulo.isChecked(), malo_limpieza_modulo.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(6, id_funcionamiento, id_servicio, "LIMPIEZA DE CEREBRO", get_estado(bueno_limpieza_cerebro.isChecked(), malo_limpieza_cerebro.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(7, id_funcionamiento, id_servicio, "SECADO", get_estado(bueno_secado.isChecked(), malo_secado.isChecked()));
                guardar_detalle_inspeccion_funcionamiento(8, id_funcionamiento, id_servicio, "PROTECCION", get_estado(bueno_proteccion.isChecked(), malo_proteccion.isChecked()));

/*
 registro.put("id",String.valueOf(id));
        registro.put("observaciones", observaciones);
        registro.put("frecuencia", frecuencia);
        registro.put("id_servicio", String.valueOf(id_servicio));
        bd.insert("inspeccion_funcionamiento", null, registro);
*/
            }
            if (sw_tw) {
                mensaje_ok_cerrar("Se guardo Correctamente.");
            } else {
                mensaje_ok_error("Por favor complete los campos.");
            }
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
                return new DatePickerDialog(MaquinaTwinShot.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(MaquinaTwinShot.this, time_listener, hour,
                        minute, false);
            case Time_id_salida:

                // Open the timepicker dialog
                return new TimePickerDialog(MaquinaTwinShot.this, time_listener_salida, hour,
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
            tv_fecha.setText(date1);
        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener()
    {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            //String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            //horaIngreso.setText(time1);
            if ((Integer.parseInt(String.valueOf(minute))>=0) && (Integer.parseInt(String.valueOf(minute))<=9)) {
                String time1 = String.valueOf(hour) + ":"+"0"+ String.valueOf(minute);
                horaIngreso.setText(time1);
            }else{
                String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
                horaIngreso.setText(time1);
            }
        }
    };
    TimePickerDialog.OnTimeSetListener time_listener_salida = new TimePickerDialog.OnTimeSetListener()
    {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            //String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            //horaSalida.setText(time1);
            if ((Integer.parseInt(String.valueOf(minute))>=0) && (Integer.parseInt(String.valueOf(minute))<=9)) {
                String time1 = String.valueOf(hour) + ":"+"0"+ String.valueOf(minute);
                horaSalida.setText(time1);
            }else{
                String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
                horaSalida.setText(time1);
            }
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

    public boolean guardar_servicio_mantenimiento(int id,String fecha,String hora_ingreso,String hora_salida,String codigo,String revision,String firma_jefe,String firma_invetsa,String id_maquina,String id_tecnico,String id_compania,String formulario,String imagen_jefe)
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
            registro.put("imagen_jefe", imagen_jefe);
            bd.insert("servicio_mantenimiento", null, registro);

            sw=true;
        }catch (Exception e)
        {
            sw=false;
            Log.e("sql",""+e);
        }

        bd.close();
        return sw;
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
        registro.put("imei", imei);
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
        registro.put("imei", imei);
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.im_foto_jefe:
                imagen_camara();
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
    public int get_estado(boolean bueno,/*boolean regular,*/boolean malo)
    {
        int estado=0;
        if(bueno)
        {
            estado=1;
        /*}else if(regular==true)
        {
            estado=2;*/
        }else if(malo)
        {
            estado=3;
        }
        return estado;
    }
    private void guardar_en_memoria(Bitmap bitmapImage, String nombre, String DIRECTORIO)
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
                            MaquinaTwinShot.this.finish();
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
