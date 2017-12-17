package com.grayhatcorp.invetsa.invetsa.servicio_mantenimiento.zootec;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Base64;
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
import com.grayhatcorp.invetsa.invetsa.Suceso;
import com.grayhatcorp.invetsa.invetsa.TouchView;
import com.grayhatcorp.invetsa.invetsa.sistema_integral_monitoreo.SistemaIntegralMonitoreo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Calendar;

public class Detalle_zootec extends  AppCompatActivity  implements View.OnClickListener {
    ArrayAdapter<String> adapter;

    Suceso suceso;

    AlertDialog alertDialog_firmar_1,alertDialog_firmar_2;

    ImageView im_firma_1=null,im_firma_2=null,im_foto_jefe;
    Bitmap bm_firma_1=null,bm_firma_2=null;
    String imei="";

    int id_zoo=0;
    Button bt_nuevo,bt_formulario;
    ImageButton ib_izquierda,ib_derecha;

    private static final int Date_id = 0;
    private static final int Time_id = 1;
    private static final int Time_id_salida = 2;

    AutoCompleteTextView autoCompania,autoMaquina,autoDireccion;
    EditText encargado,ultima_visita;
    TextView fecha,hora_ingreso,hora_salida;
    EditText observacion_inspeccion,piezas_cambiadas_inspeccion,observacion_funcionamiento,frecuencia_uso_funcionamiento,observacion_desinfeccion,cantidad_aves_desinfeccion;




    RadioButton bueno_plato_divisor_gabinete,regular_plato_divisor_gabinete,malo_plato_divisor_gabinete;
    RadioButton bueno_cubierta_trasera,regular_cubierta_trasera,malo_cubierta_trasera;
    RadioButton bueno_bisagra_cubierta,regular_bisagra_cubierta,malo_bisagra_cubierta;
    RadioButton bueno_cubierta_del_frente,regular_cubierta_del_frente,malo_cubierta_del_frente;
    RadioButton bueno_tapa_inferior_gabinete,regular_tapa_inferior_gabinete,malo_tapa_inferior_gabinete;
    RadioButton bueno_pata_de_goma,regular_pata_de_goma,malo_pata_de_goma;
    RadioButton bueno_tornillo_cubierta_plato,regular_tornillo_cubierta_plato,malo_tornillo_cubierta_plato;
    RadioButton bueno_tuerca_montaje_plato,regular_tuerca_montaje_plato,malo_tuerca_montaje_plato;
    RadioButton bueno_boton_toque,regular_boton_toque,malo_boton_toque;
    RadioButton bueno_micro_valvula_cilidro,regular_micro_valvula_cilidro,malo_micro_valvula_cilidro;
    RadioButton bueno_cubierta_plato_colocacion,regular_cubierta_plato_colocacion,malo_cubierta_plato_colocacion;
    RadioButton bueno_plato_colocacion,regular_plato_colocacion,malo_plato_colocacion;
    RadioButton bueno_plato_colocacion_emsamblaje,regular_plato_colocacion_emsamblaje,malo_plato_colocacion_emsamblaje;
    RadioButton bueno_tornillo_para_ZT40x2,regular_tornillo_para_ZT40x2,malo_tornillo_para_ZT40x2;
    RadioButton bueno_placa_montaje_TTP,regular_placa_montaje_TTP,malo_placa_montaje_TTP;
    RadioButton bueno_conector_Y_4mm,regular_conector_Y_4mm,malo_conector_Y_4mm;
    RadioButton bueno_sensor_sangrado,regular_sensor_sangrado,malo_sensor_sangrado;
    RadioButton bueno_tornillo_para_ZT403NSx2,regular_tornillo_para_ZT403NSx2,malo_tornillo_para_ZT403NSx2;
    RadioButton bueno_boton_tactil,regular_boton_tactil,malo_boton_tactil;
    RadioButton bueno_TTP_cuerpo_placa,regular_TTP_cuerpo_placa,malo_TTP_cuerpo_placa;
    RadioButton bueno_conjunto_placa_tactil,regular_conjunto_placa_tactil,malo_conjunto_placa_tactil;
    RadioButton bueno_jeringa_secundaria01,regular_jeringa_secundaria01,malo_jeringa_secundaria01;
    RadioButton bueno_jeringa_secundaria02,regular_jeringa_secundaria02,malo_jeringa_secundaria02;
    RadioButton bueno_tuerca_seguridad_punta,regular_tuerca_seguridad_punta,malo_tuerca_seguridad_punta;
    RadioButton bueno_conector_ZT621NS,regular_conector_ZT621NS,malo_conector_ZT621NS;
    RadioButton bueno_tuberia_conectar_jeringasPSx10NS,regular_tuberia_conectar_jeringasPSx10NS,malo_tuberia_conectar_jeringasPSx10NS;
    RadioButton bueno_tuberia_conectar_jeringasPSx10,regular_tuberia_conectar_jeringasPSx10,malo_tuberia_conectar_jeringasPSx10;
    RadioButton bueno_juego_empaques_1millon01,regular_juego_empaques_1millon01,malo_juego_empaques_1millon01;
    RadioButton bueno_juego_empaques_1millon02,regular_juego_empaques_1millon02,malo_juego_empaques_1millon02;
    RadioButton bueno_manometro,regular_manometro,malo_manometro;
    RadioButton bueno_contador_de_lote,regular_contador_de_lote,malo_contador_de_lote;
    RadioButton bueno_contador_totalizador,regular_contador_totalizador,malo_contador_totalizador;
    RadioButton bueno_cubierta_cont_lote,regular_cubierta_cont_lote,malo_cubierta_cont_lote;
    RadioButton bueno_cubierta_cont_totalizador,regular_cubierta_cont_totalizador,malo_cubierta_cont_totalizador;
    RadioButton bueno_placa_fijar_cont_lote,regular_placa_fijar_cont_lote,malo_placa_fijar_cont_lote;
    RadioButton bueno_placa_fijar_cont_totalizador,regular_placa_fijar_cont_totalizador,malo_placa_fijar_cont_totalizador;
    RadioButton bueno_filtro_regulador,regular_filtro_regulador,malo_filtro_regulador;
    RadioButton bueno_conector_espigado14,regular_conector_espigado14,malo_conector_espigado14;
    RadioButton bueno_conector_macho_aire,regular_conector_macho_aire,malo_conector_macho_aire;
    RadioButton bueno_conector_hembra_aire,regular_conector_hembra_aire,malo_conector_hembra_aire;
    RadioButton bueno_acople_reductor_manometro,regular_acople_reductor_manometro,malo_acople_reductor_manometro;
    RadioButton bueno_tapon_de_gomaValAguja,regular_tapon_de_gomaValAguja,malo_tapon_de_gomaValAguja;

    RadioButton bueno_empaque_conector_macho_airex10,regular_empaque_conector_macho_airex10,malo_empaque_conector_macho_airex10;
    RadioButton bueno_plato_montaje_tornillos_manometro,regular_plato_montaje_tornillos_manometro,malo_plato_montaje_tornillos_manometro;
    RadioButton bueno_boton_reinicio_contador,regular_boton_reinicio_contador,malo_boton_reinicio_contador;
    RadioButton bueno_mecanismo_interno_botonReiniciador,regular_mecanismo_interno_botonReiniciador,malo_mecanismo_interno_botonReiniciador;
    RadioButton bueno_interruptor_encendido_apagado,regular_interruptor_encendido_apagado,malo_interruptor_encendido_apagado;
    RadioButton bueno_mec_interruptor_ONOF,regular_mec_interruptor_ONOF,malo_mec_interruptor_ONOF;
    RadioButton bueno_boton_prueba,regular_boton_prueba,malo_boton_prueba;
    RadioButton bueno_mec_interno_boton_prueba,regular_mec_interno_boton_prueba,malo_mec_interno_boton_prueba;
    RadioButton bueno_interruptor_preSeleccion,regular_interruptor_preSeleccion,malo_interruptor_preSeleccion;
    RadioButton bueno_mec_interruptor_preSeleccion,regular_mec_interruptor_preSeleccion,malo_mec_interruptor_preSeleccion;
    RadioButton bueno_valvula_neumaticaAguja,regular_valvula_neumaticaAguja,malo_valvula_neumaticaAguja;
    RadioButton bueno_celula_logica_azul,regular_celula_logica_azul,malo_celula_logica_azul;
    RadioButton bueno_celula_logica_gris,regular_celula_logica_gris,malo_celula_logica_gris;
    RadioButton bueno_celula_logica_amarilla,regular_celula_logica_amarilla,malo_celula_logica_amarilla;
    RadioButton bueno_valvula_activacionZT35,regular_valvula_activacionZT35,malo_valvula_activacionZT35;
    RadioButton bueno_ONOFF_interruptor_prueba,regular_ONOFF_interruptor_prueba,malo_ONOFF_interruptor_prueba;
    RadioButton bueno_ONOFF_mec_cambio_prueba,regular_ONOFF_mec_cambio_prueba,malo_ONOFF_mec_cambio_prueba;
    RadioButton bueno_simple_doble_mec_interruptorModo,regular_simple_doble_mec_interruptorModo,malo_simple_doble_mec_interruptorModo;
    RadioButton bueno_simple_doble_mec_interruptor,regular_simple_doble_mec_interruptor,malo_simple_doble_mec_interruptor;
    RadioButton bueno_conector_recto4mm,regular_conector_recto4mm,malo_conector_recto4mm;
    RadioButton bueno_valvula_retencion_linea,regular_valvula_retencion_linea,malo_valvula_retencion_linea;
    RadioButton bueno_valvula_unidireccional,regular_valvula_unidireccional,malo_valvula_unidireccional;
    RadioButton bueno_conectorT_4mm,regular_conectorT_4mm,malo_conectorT_4mm;
    RadioButton bueno_distribuidor_aire_multiple,regular_distribuidor_aire_multiple,malo_distribuidor_aire_multiple;
    RadioButton bueno_conector_cruz4mm,regular_conector_cruz4mm,malo_conector_cruz4mm;
    RadioButton bueno_valvula3salidas,regular_valvula3salidas,malo_valvula3salidas;
    RadioButton bueno_celula_base,regular_celula_base,malo_celula_base;
    RadioButton bueno_valvula_impulso,regular_valvula_impulso,malo_valvula_impulso;
    RadioButton bueno_espaciador_entrada_segundaJeringa,regular_espaciador_entrada_segundaJeringa,malo_espaciador_entrada_segundaJeringa;
    RadioButton bueno_cilidro_aire_tuercaMontaje,regular_cilidro_aire_tuercaMontaje,malo_cilidro_aire_tuercaMontaje;
    RadioButton bueno_conector4mm_cilindro_aire,regular_conector4mm_cilindro_aire,malo_conector4mm_cilindro_aire;
    RadioButton bueno_detector_manetico,regular_detector_manetico,malo_detector_manetico;
    RadioButton bueno_conector_acople,regular_conector_acople,malo_conector_acople;
    RadioButton bueno_impulsorEspaciador_jeringaSecundaria,regular_impulsorEspaciador_jeringaSecundaria,malo_impulsorEspaciador_jeringaSecundaria;
    RadioButton bueno_resorteSostenedor_jeringa,regular_resorteSostenedor_jeringa,malo_resorteSostenedor_jeringa;
    RadioButton bueno_sostenedor_jeringa,regular_sostenedor_jeringa,malo_sostenedor_jeringa;
    RadioButton bueno_tornillos_fijacion6x20,regular_tornillos_fijacion6x20,malo_tornillos_fijacion6x20;
    RadioButton bueno_tornillos_fijacion6x50,regular_tornillos_fijacion6x50,malo_tornillos_fijacion6x50;
    RadioButton bueno_placa_mont_sostenedorJeringa,regular_placa_mont_sostenedorJeringa,malo_placa_mont_sostenedorJeringa;
    RadioButton bueno_plato_guiaSostenedor_jeringa,regular_plato_guiaSostenedor_jeringa,malo_plato_guiaSostenedor_jeringa;
    RadioButton bueno_probeta_calibrador,regular_probeta_calibrador,malo_probeta_calibrador;
    RadioButton bueno_pieza_finalPiston,regular_pieza_finalPiston,malo_pieza_finalPiston;
    RadioButton bueno_resortes_embolo,regular_resortes_embolo,malo_resortes_embolo;
    RadioButton bueno_tuerca_tapaEmbolo,regular_tuerca_tapaEmbolo,malo_tuerca_tapaEmbolo;
    RadioButton bueno_tornillo_4x16,regular_tornillo_4x16,malo_tornillo_4x16;
    RadioButton bueno_aceite_vaselinaSpray,regular_aceite_vaselinaSpray,malo_aceite_vaselinaSpray;

    RadioButton bueno_ubicacion_maquina,malo_ubicacion_maquina;
    RadioButton bueno_presion_compresora,malo_presion_compresora;
    RadioButton bueno_presion_maquina,malo_presion_maquina;
    RadioButton bueno_activacion,malo_activacion;
    RadioButton bueno_funcion_contadores,malo_funcion_contadores;
    RadioButton bueno_funcion_silbato,malo_funcion_silbato;
    RadioButton bueno_salida_aguja,malo_salida_aguja;
    RadioButton bueno_calibracion,malo_calibracion;
    RadioButton bueno_materiales_utilizados,malo_materiales_utilizados;
    RadioButton bueno_cambio_descartables,malo_cambio_descartables;
    RadioButton bueno_limpieza_plato_colocacion,malo_limpieza_plato_colocacion;
    RadioButton bueno_esterilizado_jeringa,malo_esterilizado_jeringa;
    RadioButton bueno_limpieza_mod_inyector,malo_limpieza_mod_inyector;
    RadioButton bueno_limpieza_cerebro,malo_limpieza_cerebro;
    RadioButton bueno_secado,malo_secado;
    RadioButton bueno_proteccion,malo_proteccion;

    Button bt_firmar_1,bt_firmar_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_zootec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoCompania = (AutoCompleteTextView)findViewById(R.id.autoCompania);
        autoMaquina = (AutoCompleteTextView)findViewById(R.id.autoMaquina);
        autoDireccion=(AutoCompleteTextView)findViewById(R.id.autoDireccion);
        encargado=(EditText)findViewById(R.id.TextViewEncargado);
        hora_ingreso=(TextView)findViewById(R.id.et_hora_ingreso);
        hora_salida=(TextView)findViewById(R.id.et_hora_salida);
        ultima_visita=(EditText)findViewById(R.id.TextViewUltimaVisita);
        fecha=(TextView) findViewById(R.id.fecha);

        observacion_inspeccion=(EditText)findViewById(R.id.txtObservaciones_inspeccion);
        piezas_cambiadas_inspeccion=(EditText)findViewById(R.id.txtPiezasCambiadas_inspeccion);
        observacion_funcionamiento=(EditText)findViewById(R.id.txtObservaciones_funcionamiento);
        frecuencia_uso_funcionamiento=(EditText)findViewById(R.id.txtPiezasFrecuenciadeUso_funcionamiento);
        observacion_desinfeccion=(EditText)findViewById(R.id.txtObservaciones_desinfeccion);
        cantidad_aves_desinfeccion=(EditText)findViewById(R.id.txtAvesVacunadas_desinfeccion);

        bueno_plato_divisor_gabinete=(RadioButton)findViewById(R.id.BuenoPlatoDivisor);
        //regular_plato_divisor_gabinete=(RadioButton)findViewById(R.id.RegularPlatoDivisor);
        malo_plato_divisor_gabinete=(RadioButton)findViewById(R.id.MaloPlatoDivisor);

        bueno_cubierta_trasera=(RadioButton)findViewById(R.id.BuenoCubiertaTraseraconLogo);
        //regular_cubierta_trasera=(RadioButton)findViewById(R.id.RegularCubiertaTraseraconLogo);
        malo_cubierta_trasera=(RadioButton)findViewById(R.id.MaloCubiertaTraseraconLogo);

        bueno_bisagra_cubierta=(RadioButton)findViewById(R.id.BuenoBisagraParaLaCubierta);
        //regular_bisagra_cubierta=(RadioButton)findViewById(R.id.RegularBisagraParaLaCubierta);
        malo_bisagra_cubierta=(RadioButton)findViewById(R.id.MaloBisagraParaLaCubierta);

        bueno_cubierta_del_frente=(RadioButton)findViewById(R.id.BuenoCubiertaEnAceroInox);
        //regular_cubierta_del_frente=(RadioButton)findViewById(R.id.RegularCubiertaEnAceroInox);
        malo_cubierta_del_frente=(RadioButton)findViewById(R.id.MaloCubiertaEnAceroInox);

        bueno_tapa_inferior_gabinete=(RadioButton)findViewById(R.id.BuenoTapaInferiordelGabinete);
        //regular_tapa_inferior_gabinete=(RadioButton)findViewById(R.id.RegularTapaInferiordelGabinete);
        malo_tapa_inferior_gabinete=(RadioButton)findViewById(R.id.MaloTapaInferiordelGabinete);

        bueno_pata_de_goma=(RadioButton)findViewById(R.id.BuenoPatadeGoma);
        //regular_pata_de_goma=(RadioButton)findViewById(R.id.RegularPatadeGoma);
        malo_pata_de_goma=(RadioButton)findViewById(R.id.MaloPatadeGoma);

        bueno_tornillo_cubierta_plato=(RadioButton)findViewById(R.id.BuenoTornilloCuniertadePlato);
        //regular_tornillo_cubierta_plato=(RadioButton)findViewById(R.id.RegularTornilloCuniertadePlato);
        malo_tornillo_cubierta_plato=(RadioButton)findViewById(R.id.MaloTornilloCuniertadePlato);

        bueno_tuerca_montaje_plato=(RadioButton)findViewById(R.id.BuenoTuercaMontajedelPlatodeColocacion);
        //regular_tuerca_montaje_plato=(RadioButton)findViewById(R.id.RegularTuercaMontajedelPlatodeColocacion);
        malo_tuerca_montaje_plato=(RadioButton)findViewById(R.id.MaloTuercaMontajedelPlatodeColocacion);

        bueno_boton_toque=(RadioButton)findViewById(R.id.BuenoBotondeToque);
        //regular_boton_toque=(RadioButton)findViewById(R.id.RegularBotondeToque);
        malo_boton_toque=(RadioButton)findViewById(R.id.MaloBotondeToque);

        bueno_micro_valvula_cilidro=(RadioButton)findViewById(R.id.BuenoMicroValvulaCilindro);
        //regular_micro_valvula_cilidro=(RadioButton)findViewById(R.id.RegularMicroValvulaCilindro);
        malo_micro_valvula_cilidro=(RadioButton)findViewById(R.id.MaloMicroValvulaCilindro);

        bueno_cubierta_plato_colocacion=(RadioButton)findViewById(R.id.BuenoCubiertadelPlatoColocacion);
        //regular_cubierta_plato_colocacion=(RadioButton)findViewById(R.id.RegularCubiertadelPlatoColocacion);
        malo_cubierta_plato_colocacion=(RadioButton)findViewById(R.id.MaloCubiertadelPlatoColocacion);

        bueno_plato_colocacion=(RadioButton)findViewById(R.id.BuenoPlatodeColocacion);
        //regular_plato_colocacion=(RadioButton)findViewById(R.id.RegularPlatodeColocacion);
        malo_plato_colocacion=(RadioButton)findViewById(R.id.MaloPlatodeColocacion);

        bueno_plato_colocacion_emsamblaje=(RadioButton)findViewById(R.id.BuenoPlatoColocacionEmsamblaje);
        //regular_plato_colocacion_emsamblaje=(RadioButton)findViewById(R.id.RegularPlatoColocacionEmsamblaje);
        malo_plato_colocacion_emsamblaje=(RadioButton)findViewById(R.id.MaloPlatoColocacionEmsamblaje);

        bueno_tornillo_para_ZT40x2=(RadioButton)findViewById(R.id.BuenoTornilloZt40pns);
        //regular_tornillo_para_ZT40x2=(RadioButton)findViewById(R.id.RegularTornilloZt40pns);
        malo_tornillo_para_ZT40x2=(RadioButton)findViewById(R.id.MaloTornilloZt40pns);

        bueno_placa_montaje_TTP=(RadioButton)findViewById(R.id.BuenoPlacadeMontajeTTP);
        //regular_placa_montaje_TTP=(RadioButton)findViewById(R.id.RegularPlacadeMontajeTTP);
        malo_placa_montaje_TTP=(RadioButton)findViewById(R.id.MaloPlacadeMontajeTTP);

        bueno_conector_Y_4mm=(RadioButton)findViewById(R.id.BuenoConectorY4mm);
        //regular_conector_Y_4mm=(RadioButton)findViewById(R.id.RegularConectorY4mm);
        malo_conector_Y_4mm=(RadioButton)findViewById(R.id.MaloConectorY4mm);

        bueno_sensor_sangrado=(RadioButton)findViewById(R.id.BuenoSensordeSangrado);
        //regular_sensor_sangrado=(RadioButton)findViewById(R.id.RegularSensordeSangrado);
        malo_sensor_sangrado=(RadioButton)findViewById(R.id.MaloSensordeSangrado);

        bueno_tornillo_para_ZT403NSx2=(RadioButton)findViewById(R.id.BuenoTornilloZT403NS);
        //regular_tornillo_para_ZT403NSx2=(RadioButton)findViewById(R.id.RegularTornilloZT403NS);
        malo_tornillo_para_ZT403NSx2=(RadioButton)findViewById(R.id.MaloTornilloZT403NS);

        bueno_boton_tactil=(RadioButton)findViewById(R.id.BuenoBotonTactil);
        //regular_boton_tactil=(RadioButton)findViewById(R.id.RegularBotonTactil);
        malo_boton_tactil=(RadioButton)findViewById(R.id.MaloBotonTactil);

        bueno_TTP_cuerpo_placa=(RadioButton)findViewById(R.id.BuenoCuerpodelaPlaca);
        //regular_TTP_cuerpo_placa=(RadioButton)findViewById(R.id.RegularCuerpodelaPlaca);
        malo_TTP_cuerpo_placa=(RadioButton)findViewById(R.id.MaloCuerpodelaPlaca);

        bueno_conjunto_placa_tactil=(RadioButton)findViewById(R.id.BuenoConjuntodePlacaTactil);
        //regular_conjunto_placa_tactil=(RadioButton)findViewById(R.id.RegularConjuntodePlacaTactil);
        malo_conjunto_placa_tactil=(RadioButton)findViewById(R.id.MaloConjuntodePlacaTactil);

        bueno_jeringa_secundaria01=(RadioButton)findViewById(R.id.BuenoJeringaSecundaria01ml);
        //regular_jeringa_secundaria01=(RadioButton)findViewById(R.id.RegularJeringaSecundaria01ml);
        malo_jeringa_secundaria01=(RadioButton)findViewById(R.id.MaloJeringaSecundaria01ml);

        bueno_jeringa_secundaria02=(RadioButton)findViewById(R.id.BuenoJeringaSecundario2ml);
        //regular_jeringa_secundaria02=(RadioButton)findViewById(R.id.RegularJeringaSecundario2ml);
        malo_jeringa_secundaria02=(RadioButton)findViewById(R.id.MaloJeringaSecundario2ml);

        bueno_tuerca_seguridad_punta=(RadioButton)findViewById(R.id.BuenoTuercaSeguridadPuntaJeringa);
        //regular_tuerca_seguridad_punta=(RadioButton)findViewById(R.id.RegularTuercaSeguridadPuntaJeringa);
        malo_tuerca_seguridad_punta=(RadioButton)findViewById(R.id.MaloTuercaSeguridadPuntaJeringa);

        bueno_conector_ZT621NS=(RadioButton)findViewById(R.id.BuenoConectorZT621NS);
        //regular_conector_ZT621NS=(RadioButton)findViewById(R.id.RegularConectorZT621NS);
        malo_conector_ZT621NS=(RadioButton)findViewById(R.id.MaloConectorZT621NS);

        bueno_tuberia_conectar_jeringasPSx10NS=(RadioButton)findViewById(R.id.BuenoTuberiaParaJeringasx10NS);
        //regular_tuberia_conectar_jeringasPSx10NS=(RadioButton)findViewById(R.id.RegularTuberiaParaJeringasx10NS);
        malo_tuberia_conectar_jeringasPSx10NS=(RadioButton)findViewById(R.id.MaloTuberiaParaJeringasx10NS);

        bueno_tuberia_conectar_jeringasPSx10=(RadioButton)findViewById(R.id.BuenoTuberiaParaConectarJeringasx10);
        //regular_tuberia_conectar_jeringasPSx10=(RadioButton)findViewById(R.id.RegularTuberiaParaConectarJeringasx10);
        malo_tuberia_conectar_jeringasPSx10=(RadioButton)findViewById(R.id.MaloTuberiaParaConectarJeringasx10);

        bueno_juego_empaques_1millon01=(RadioButton)findViewById(R.id.BuenoJuegoEmpaques01ml);
        //regular_juego_empaques_1millon01=(RadioButton)findViewById(R.id.RegularJuegoEmpaques01ml);
        malo_juego_empaques_1millon01=(RadioButton)findViewById(R.id.MaloJuegoEmpaques01ml);

        bueno_juego_empaques_1millon02=(RadioButton)findViewById(R.id.BuenoJuegoEmpaques02ml);
        //regular_juego_empaques_1millon02=(RadioButton)findViewById(R.id.RegularJuegoEmpaques02ml);
        malo_juego_empaques_1millon02=(RadioButton)findViewById(R.id.MaloJuegoEmpaques02ml);

        bueno_manometro=(RadioButton)findViewById(R.id.BuenoManometro);
        //regular_manometro=(RadioButton)findViewById(R.id.RegularManometro);
        malo_manometro=(RadioButton)findViewById(R.id.MaloManometro);

        bueno_contador_de_lote=(RadioButton)findViewById(R.id.BuenoContadordeLote);
        //regular_contador_de_lote=(RadioButton)findViewById(R.id.RegularContadordeLote);
        malo_contador_de_lote=(RadioButton)findViewById(R.id.MaloContadordeLote);

        bueno_contador_totalizador=(RadioButton)findViewById(R.id.BuenoContadortotalizador);
        //regular_contador_totalizador=(RadioButton)findViewById(R.id.RegularContadortotalizador);
        malo_contador_totalizador=(RadioButton)findViewById(R.id.MaloContadortotalizador);

        bueno_cubierta_cont_lote=(RadioButton)findViewById(R.id.BuenoCubiertaConLote);
        //regular_cubierta_cont_lote=(RadioButton)findViewById(R.id.RegularCubiertaConLote);
        malo_cubierta_cont_lote=(RadioButton)findViewById(R.id.MaloCubiertaConLote);

        bueno_cubierta_cont_totalizador=(RadioButton)findViewById(R.id.BuenoCubiertaContTotalizador);
        //regular_cubierta_cont_totalizador=(RadioButton)findViewById(R.id.RegularCubiertaContTotalizador);
        malo_cubierta_cont_totalizador=(RadioButton)findViewById(R.id.MaloCubiertaContTotalizador);

        bueno_placa_fijar_cont_lote=(RadioButton)findViewById(R.id.BuenoPlacaFijarContLote);
        //regular_placa_fijar_cont_lote=(RadioButton)findViewById(R.id.RegularPlacaFijarContLote);
        malo_placa_fijar_cont_lote=(RadioButton)findViewById(R.id.MaloPlacaFijarContLote);

        bueno_placa_fijar_cont_totalizador=(RadioButton)findViewById(R.id.BuenoPlacaFijarContotalizador);
        //regular_placa_fijar_cont_totalizador=(RadioButton)findViewById(R.id.RegularPlacaFijarContotalizador);
        malo_placa_fijar_cont_totalizador=(RadioButton)findViewById(R.id.MaloPlacaFijarContotalizador);

        bueno_filtro_regulador=(RadioButton)findViewById(R.id.BuenoFiltroRegulador);
        //regular_filtro_regulador=(RadioButton)findViewById(R.id.RegularFiltroRegulador);
        malo_filtro_regulador=(RadioButton)findViewById(R.id.MaloFiltroRegulador);

        bueno_conector_espigado14=(RadioButton)findViewById(R.id.BuenoConectorEspigado);
        //regular_conector_espigado14=(RadioButton)findViewById(R.id.RegularConectorEspigado);
        malo_conector_espigado14=(RadioButton)findViewById(R.id.MaloConectorEspigado);

        bueno_conector_macho_aire=(RadioButton)findViewById(R.id.BuenoConectorMAchoAire);
        //regular_conector_macho_aire=(RadioButton)findViewById(R.id.RegularConectorMAchoAire);
        malo_conector_macho_aire=(RadioButton)findViewById(R.id.MaloConectorMAchoAire);

        bueno_conector_hembra_aire=(RadioButton)findViewById(R.id.BuenoConectorHembraaire);
        //regular_conector_hembra_aire=(RadioButton)findViewById(R.id.RegularConectorHembraaire);
        malo_conector_hembra_aire=(RadioButton)findViewById(R.id.MaloConectorHembraaire);

        bueno_acople_reductor_manometro=(RadioButton)findViewById(R.id.BuenoAcopleReductorMAnometro);
        //regular_acople_reductor_manometro=(RadioButton)findViewById(R.id.RegularAcopleReductorMAnometro);
        malo_acople_reductor_manometro=(RadioButton)findViewById(R.id.MaloAcopleReductorMAnometro);

        bueno_tapon_de_gomaValAguja=(RadioButton)findViewById(R.id.BuenoTapondeGoma);
        //regular_tapon_de_gomaValAguja=(RadioButton)findViewById(R.id.RegularTapondeGoma);
        malo_tapon_de_gomaValAguja=(RadioButton)findViewById(R.id.MaloTapondeGoma);

        bueno_empaque_conector_macho_airex10=(RadioButton)findViewById(R.id.BuenoEmpaqueConectorMAcho);
        //regular_empaque_conector_macho_airex10=(RadioButton)findViewById(R.id.RegularEmpaqueConectorMAcho);
        malo_empaque_conector_macho_airex10=(RadioButton)findViewById(R.id.MaloEmpaqueConectorMAcho);

        bueno_plato_montaje_tornillos_manometro=(RadioButton)findViewById(R.id.BuenoPlatoMontajeTornilloManometro);
        //regular_plato_montaje_tornillos_manometro=(RadioButton)findViewById(R.id.RegularPlatoMontajeTornilloManometro);
        malo_plato_montaje_tornillos_manometro=(RadioButton)findViewById(R.id.MaloPlatoMontajeTornilloManometro);

        bueno_boton_reinicio_contador=(RadioButton)findViewById(R.id.BuenoBotondeReiniciodelContador);
        //regular_boton_reinicio_contador=(RadioButton)findViewById(R.id.RegularBotondeReiniciodelContador);
        malo_boton_reinicio_contador=(RadioButton)findViewById(R.id.MaloBotondeReiniciodelContador);

        bueno_mecanismo_interno_botonReiniciador=(RadioButton)findViewById(R.id.BuenoMecanismoInternoBoton);
        //regular_mecanismo_interno_botonReiniciador=(RadioButton)findViewById(R.id.RegularMecanismoInternoBoton);
        malo_mecanismo_interno_botonReiniciador=(RadioButton)findViewById(R.id.MaloMecanismoInternoBoton);

        bueno_interruptor_encendido_apagado=(RadioButton)findViewById(R.id.BuenoInterruptorENcendido);
        //regular_interruptor_encendido_apagado=(RadioButton)findViewById(R.id.RegularInterruptorENcendido);
        malo_interruptor_encendido_apagado=(RadioButton)findViewById(R.id.MaloInterruptorENcendido);

        bueno_mec_interruptor_ONOF=(RadioButton)findViewById(R.id.BuenoMecanismoInternoBoton);
        //regular_mec_interruptor_ONOF=(RadioButton)findViewById(R.id.RegularMecanismoInternoBoton);
        malo_mec_interruptor_ONOF=(RadioButton)findViewById(R.id.MaloMecanismoInternoBoton);

        bueno_boton_prueba=(RadioButton)findViewById(R.id.BuenoBotondePrueba);
        //regular_boton_prueba=(RadioButton)findViewById(R.id.RegularBotondePrueba);
        malo_boton_prueba=(RadioButton)findViewById(R.id.MaloBotondePrueba);

        bueno_mec_interno_boton_prueba=(RadioButton)findViewById(R.id.BuenoMecanismoInternoBoton);
        //regular_mec_interno_boton_prueba=(RadioButton)findViewById(R.id.RegularMecanismoInternoBoton);
        malo_mec_interno_boton_prueba=(RadioButton)findViewById(R.id.MaloMecanismoInternoBoton);

        bueno_interruptor_preSeleccion=(RadioButton)findViewById(R.id.BuenoInterruptorPReseleccion);
        //regular_interruptor_preSeleccion=(RadioButton)findViewById(R.id.RegularInterruptorPReseleccion);
        malo_interruptor_preSeleccion=(RadioButton)findViewById(R.id.MaloInterruptorPReseleccion);

        bueno_mec_interruptor_preSeleccion=(RadioButton)findViewById(R.id.BuenoMecInterPreSeleccion);
        //regular_mec_interruptor_preSeleccion=(RadioButton)findViewById(R.id.RegularMecInterPreSeleccion);
        malo_mec_interruptor_preSeleccion=(RadioButton)findViewById(R.id.MaloMecInterPreSeleccion);

        bueno_valvula_neumaticaAguja=(RadioButton)findViewById(R.id.BuenoValvulaNeumaticaAguja);
        //regular_valvula_neumaticaAguja=(RadioButton)findViewById(R.id.RegularValvulaNeumaticaAguja);
        malo_valvula_neumaticaAguja=(RadioButton)findViewById(R.id.MaloValvulaNeumaticaAguja);

        bueno_celula_logica_azul=(RadioButton)findViewById(R.id.BuenoCelulaLogicaAzul);
        //regular_celula_logica_azul=(RadioButton)findViewById(R.id.RegularCelulaLogicaAzul);
        malo_celula_logica_azul=(RadioButton)findViewById(R.id.MaloCelulaLogicaAzul);

        bueno_celula_logica_gris=(RadioButton)findViewById(R.id.BuenoCelulaLogicaGris);
        //regular_celula_logica_gris=(RadioButton)findViewById(R.id.RegularCelulaLogicaGris);
        malo_celula_logica_gris=(RadioButton)findViewById(R.id.MaloCelulaLogicaGris);

        bueno_celula_logica_amarilla=(RadioButton)findViewById(R.id.BuenoCelulaLogicaamarrilla);
        //regular_celula_logica_amarilla=(RadioButton)findViewById(R.id.RegularCelulaLogicaamarrilla);
        malo_celula_logica_amarilla=(RadioButton)findViewById(R.id.MaloCelulaLogicaamarrilla);

        bueno_valvula_activacionZT35=(RadioButton)findViewById(R.id.BuenoValvulaDeActivacion);
        //regular_valvula_activacionZT35=(RadioButton)findViewById(R.id.RegularValvulaDeActivacion);
        malo_valvula_activacionZT35=(RadioButton)findViewById(R.id.MaloValvulaDeActivacion);

        bueno_ONOFF_interruptor_prueba=(RadioButton)findViewById(R.id.BuenoOnOffInterruptor);
        //regular_ONOFF_interruptor_prueba=(RadioButton)findViewById(R.id.RegularOnOffInterruptor);
        malo_ONOFF_interruptor_prueba=(RadioButton)findViewById(R.id.MaloOnOffInterruptor);

        bueno_ONOFF_mec_cambio_prueba=(RadioButton)findViewById(R.id.BuenoOnOffMecanismodeCambio);
        //regular_ONOFF_mec_cambio_prueba=(RadioButton)findViewById(R.id.RegularOnOffMecanismodeCambio);
        malo_ONOFF_mec_cambio_prueba=(RadioButton)findViewById(R.id.MaloOnOffMecanismodeCambio);

        bueno_simple_doble_mec_interruptorModo=(RadioButton)findViewById(R.id.BuenoSimpleDobleMecanismodelInterruptor);
        //regular_simple_doble_mec_interruptorModo=(RadioButton)findViewById(R.id.RegularSimpleDobleMecanismodelInterruptor);
        malo_simple_doble_mec_interruptorModo=(RadioButton)findViewById(R.id.MaloSimpleDobleMecanismodelInterruptor);

        bueno_simple_doble_mec_interruptor=(RadioButton)findViewById(R.id.BuenoSimpleDobleMecanismo);
        //regular_simple_doble_mec_interruptor=(RadioButton)findViewById(R.id.RegularSimpleDobleMecanismo);
        malo_simple_doble_mec_interruptor=(RadioButton)findViewById(R.id.MaloSimpleDobleMecanismo);

        bueno_conector_recto4mm=(RadioButton)findViewById(R.id.BuenoConectorrecto4mm);
        //regular_conector_recto4mm=(RadioButton)findViewById(R.id.RegularConectorrecto4mm);
        malo_conector_recto4mm=(RadioButton)findViewById(R.id.MaloConectorrecto4mm);

        bueno_valvula_retencion_linea=(RadioButton)findViewById(R.id.BuenoValvulaRetencionLinea);
        //regular_valvula_retencion_linea=(RadioButton)findViewById(R.id.RegularValvulaRetencionLinea);
        malo_valvula_retencion_linea=(RadioButton)findViewById(R.id.MaloValvulaRetencionLinea);

        bueno_valvula_unidireccional=(RadioButton)findViewById(R.id.BuenoValuvulaUnidireccional);
        //regular_valvula_unidireccional=(RadioButton)findViewById(R.id.RegularValuvulaUnidireccional);
        malo_valvula_unidireccional=(RadioButton)findViewById(R.id.MaloValuvulaUnidireccional);

        bueno_conectorT_4mm=(RadioButton)findViewById(R.id.BuenoConectorT4mm);
        //regular_conectorT_4mm=(RadioButton)findViewById(R.id.RegularConectorT4mm);
        malo_conectorT_4mm=(RadioButton)findViewById(R.id.MaloConectorT4mm);

        bueno_distribuidor_aire_multiple=(RadioButton)findViewById(R.id.BuenoDistribuidordeAireMultiple);
        //regular_distribuidor_aire_multiple=(RadioButton)findViewById(R.id.RegularDistribuidordeAireMultiple);
        malo_distribuidor_aire_multiple=(RadioButton)findViewById(R.id.MaloDistribuidordeAireMultiple);
        bueno_conector_cruz4mm=(RadioButton)findViewById(R.id.BuenoConectorenCruz4mm);
        //regular_conector_cruz4mm=(RadioButton)findViewById(R.id.RegularConectorenCruz4mm);
        malo_conector_cruz4mm=(RadioButton)findViewById(R.id.MaloConectorenCruz4mm);
        bueno_valvula3salidas=(RadioButton)findViewById(R.id.BuenoValvula3salidasMac);
        //regular_valvula3salidas=(RadioButton)findViewById(R.id.RegularValvula3salidasMac);
        malo_valvula3salidas=(RadioButton)findViewById(R.id.MaloValvula3salidasMac);
        bueno_celula_base=(RadioButton)findViewById(R.id.BuenoCelulabaseAmarillo);
        //regular_celula_base=(RadioButton)findViewById(R.id.RegularCelulabaseAmarillo);
        malo_celula_base=(RadioButton)findViewById(R.id.MaloCelulabaseAmarillo);
        bueno_valvula_impulso=(RadioButton)findViewById(R.id.BuenoValvuladeImpulsoConector);
        //regular_valvula_impulso=(RadioButton)findViewById(R.id.RegularValvuladeImpulsoConector);
        malo_valvula_impulso=(RadioButton)findViewById(R.id.MaloValvuladeImpulsoConector);
        bueno_espaciador_entrada_segundaJeringa=(RadioButton)findViewById(R.id.BuenoEspaciadorentradaSegundaJeringa);
        //regular_espaciador_entrada_segundaJeringa=(RadioButton)findViewById(R.id.RegularEspaciadorentradaSegundaJeringa);
        malo_espaciador_entrada_segundaJeringa=(RadioButton)findViewById(R.id.MaloEspaciadorentradaSegundaJeringa);
        bueno_cilidro_aire_tuercaMontaje=(RadioButton)findViewById(R.id.BuenoCilindroAireTuercaMontaje);
        //regular_cilidro_aire_tuercaMontaje=(RadioButton)findViewById(R.id.RegularCilindroAireTuercaMontaje);
        malo_cilidro_aire_tuercaMontaje=(RadioButton)findViewById(R.id.MaloCilindroAireTuercaMontaje);
        bueno_conector4mm_cilindro_aire=(RadioButton)findViewById(R.id.BuenoConectorde4mmCilindroaire);
        //regular_conector4mm_cilindro_aire=(RadioButton)findViewById(R.id.RegularConectorde4mmCilindroaire);
        malo_conector4mm_cilindro_aire=(RadioButton)findViewById(R.id.MaloConectorde4mmCilindroaire);
        bueno_detector_manetico=(RadioButton)findViewById(R.id.BuenoDetectorMAnetico);
        //regular_detector_manetico=(RadioButton)findViewById(R.id.RegularDetectorMAnetico);
        malo_detector_manetico=(RadioButton)findViewById(R.id.MaloDetectorMAnetico);
        bueno_conector_acople=(RadioButton)findViewById(R.id.BuenoImpulsadorEspaciador);
        //regular_conector_acople=(RadioButton)findViewById(R.id.RegularImpulsadorEspaciador);
        malo_conector_acople=(RadioButton)findViewById(R.id.MaloImpulsadorEspaciador);
        bueno_impulsorEspaciador_jeringaSecundaria=(RadioButton)findViewById(R.id.BuenoImpulsadorEspaciador);
        //regular_impulsorEspaciador_jeringaSecundaria=(RadioButton)findViewById(R.id.RegularImpulsadorEspaciador);
        malo_impulsorEspaciador_jeringaSecundaria=(RadioButton)findViewById(R.id.MaloImpulsadorEspaciador);
        bueno_resorteSostenedor_jeringa=(RadioButton)findViewById(R.id.BuenoResortessostenedorJeringa);
        //regular_resorteSostenedor_jeringa=(RadioButton)findViewById(R.id.RegularResortessostenedorJeringa);
        malo_resorteSostenedor_jeringa=(RadioButton)findViewById(R.id.MaloResortessostenedorJeringa);
        bueno_sostenedor_jeringa=(RadioButton)findViewById(R.id.BuenosostenedorJeringa);
        //regular_sostenedor_jeringa=(RadioButton)findViewById(R.id.RegularsostenedorJeringa);
        malo_sostenedor_jeringa=(RadioButton)findViewById(R.id.MalosostenedorJeringa);
        bueno_tornillos_fijacion6x20=(RadioButton)findViewById(R.id.BuenoTornillosdeFijacion6x20);
        //regular_tornillos_fijacion6x20=(RadioButton)findViewById(R.id.RegularTornillosdeFijacion6x20);
        malo_tornillos_fijacion6x20=(RadioButton)findViewById(R.id.MaloTornillosdeFijacion6x20);
        bueno_tornillos_fijacion6x50=(RadioButton)findViewById(R.id.BuenoTornillosFijacion6x50);
        //regular_tornillos_fijacion6x50=(RadioButton)findViewById(R.id.RegularTornillosFijacion6x50);
        malo_tornillos_fijacion6x50=(RadioButton)findViewById(R.id.MaloTornillosFijacion6x50);
        bueno_placa_mont_sostenedorJeringa=(RadioButton)findViewById(R.id.BuenoPlacaMonsostenedorJeringa);
        //regular_placa_mont_sostenedorJeringa=(RadioButton)findViewById(R.id.RegularPlacaMonsostenedorJeringa);
        malo_placa_mont_sostenedorJeringa=(RadioButton)findViewById(R.id.MaloPlacaMonsostenedorJeringa);
        bueno_plato_guiaSostenedor_jeringa=(RadioButton)findViewById(R.id.BuenoPlatosostenedorDeJeringa);
        //regular_plato_guiaSostenedor_jeringa=(RadioButton)findViewById(R.id.RegularPlatosostenedorDeJeringa);
        malo_plato_guiaSostenedor_jeringa=(RadioButton)findViewById(R.id.MaloPlatosostenedorDeJeringa);
        bueno_probeta_calibrador=(RadioButton)findViewById(R.id.BuenoProbetaCalibrador);
        //regular_probeta_calibrador=(RadioButton)findViewById(R.id.RegularProbetaCalibrador);
        malo_probeta_calibrador=(RadioButton)findViewById(R.id.MaloProbetaCalibrador);
        bueno_pieza_finalPiston=(RadioButton)findViewById(R.id.BuenoPiezafinalPistonJeringa);
        //regular_pieza_finalPiston=(RadioButton)findViewById(R.id.RegularPiezafinalPistonJeringa);
        malo_pieza_finalPiston=(RadioButton)findViewById(R.id.MaloPiezafinalPistonJeringa);
        bueno_resortes_embolo=(RadioButton)findViewById(R.id.BuenoResortesEmbolo);
        //regular_resortes_embolo=(RadioButton)findViewById(R.id.RegularResortesEmbolo);
        malo_resortes_embolo=(RadioButton)findViewById(R.id.MaloResortesEmbolo);
        bueno_tuerca_tapaEmbolo=(RadioButton)findViewById(R.id.BuenoTuercaTapaEmbolo);
        //regular_tuerca_tapaEmbolo=(RadioButton)findViewById(R.id.RegularTuercaTapaEmbolo);
        malo_tuerca_tapaEmbolo=(RadioButton)findViewById(R.id.MaloTuercaTapaEmbolo);
        bueno_tornillo_4x16=(RadioButton)findViewById(R.id.BuenoTornillo4x16);
        //regular_tornillo_4x16=(RadioButton)findViewById(R.id.RegularTornillo4x16);
        malo_tornillo_4x16=(RadioButton)findViewById(R.id.MaloTornillo4x16);
        bueno_aceite_vaselinaSpray=(RadioButton)findViewById(R.id.BuenoAceiteVegetal);
        //regular_aceite_vaselinaSpray=(RadioButton)findViewById(R.id.RegularAceiteVegetal);
        malo_aceite_vaselinaSpray=(RadioButton)findViewById(R.id.MaloAceiteVegetal);
        bueno_ubicacion_maquina=(RadioButton)findViewById(R.id.BuenoUbicaciondelaMAquina);
        malo_ubicacion_maquina=(RadioButton)findViewById(R.id.MaloUbicaciondelaMAquina);
        bueno_presion_compresora=(RadioButton)findViewById(R.id.BuenoPresionCompresora);
        malo_presion_compresora=(RadioButton)findViewById(R.id.MaloPresionCompresora);
        bueno_presion_maquina=(RadioButton)findViewById(R.id.BuenoPresionMAquina);malo_presion_maquina=(RadioButton)findViewById(R.id.MaloPresionMAquina);
        bueno_activacion=(RadioButton)findViewById(R.id.BuenoActivacion);malo_activacion=(RadioButton)findViewById(R.id.MaloActivacion);
        bueno_funcion_contadores=(RadioButton)findViewById(R.id.BuenoFuncionesDeContadores);malo_funcion_contadores=(RadioButton)findViewById(R.id.MaloFuncionesDeContadores);
        bueno_funcion_silbato=(RadioButton)findViewById(R.id.BuenoFuncionesdeSilvato);malo_funcion_silbato=(RadioButton)findViewById(R.id.MaloFuncionesdeSilvato);
        bueno_salida_aguja=(RadioButton)findViewById(R.id.BuenoSalidadeAguja);malo_salida_aguja=(RadioButton)findViewById(R.id.MaloSalidadeAguja);
        bueno_calibracion=(RadioButton)findViewById(R.id.BuenoCalibracion);malo_calibracion=(RadioButton)findViewById(R.id.MaloCalibracion);
        bueno_materiales_utilizados=(RadioButton)findViewById(R.id.BuenoMaterialesUtilizados);malo_materiales_utilizados=(RadioButton)findViewById(R.id.MaloMaterialesUtilizados);
        bueno_cambio_descartables=(RadioButton)findViewById(R.id.BuenoCambioPiezasDescartables);malo_cambio_descartables=(RadioButton)findViewById(R.id.MaloCambioPiezasDescartables);
        bueno_limpieza_plato_colocacion=(RadioButton)findViewById(R.id.BuenoLimpiezadePlatoColocaion);malo_limpieza_plato_colocacion=(RadioButton)findViewById(R.id.MaloLimpiezadePlatoColocaion);
        bueno_esterilizado_jeringa=(RadioButton)findViewById(R.id.BuenoEsterilizadodeJeringa);malo_esterilizado_jeringa=(RadioButton)findViewById(R.id.MaloEsterilizadodeJeringa);
        bueno_limpieza_mod_inyector=(RadioButton)findViewById(R.id.BuenoLimpiezaModuloInyector);malo_limpieza_mod_inyector=(RadioButton)findViewById(R.id.MaloLimpiezaModuloInyector);
        bueno_limpieza_cerebro=(RadioButton)findViewById(R.id.BuenoLimpiezadelCerebro);malo_limpieza_cerebro=(RadioButton)findViewById(R.id.MaloLimpiezadelCerebro);
        bueno_secado=(RadioButton)findViewById(R.id.BuenoSecado);malo_secado=(RadioButton)findViewById(R.id.MaloSecado);
        bueno_proteccion=(RadioButton)findViewById(R.id.BuenoProteccion);malo_proteccion=(RadioButton)findViewById(R.id.MaloProteccion);



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
        Calendar c=Calendar.getInstance();
        fecha.setText(""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));


        cargar_compania_en_la_lista();
        cargar_maquina_en_la_lista();
*/
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei=telephonyManager.getDeviceId();

        if(imei.equals("")==true)
        {
            mensaje_ok_error("Necesita dar permisos para Obtener el número de Imei.");
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
            id_zoo=Integer.parseInt(bundle.getString("id_zoo"));
            cargar_servicio_mantenimiento(id_zoo);

        } catch (Exception e)
        {
            Log.e("Detalle",""+e);
            finish();
        }
    }


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
            String foto_jefe=fila.getString(13);

            autoCompania.setText(fila.getString(14));
            autoMaquina.setText(fila.getString(15));

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
                    int estado=0;
                    try{estado=Integer.parseInt(fila.getString(4));}
                    catch (Exception e)
                    {

                    }
                    switch(fila.getString(0))
                    {
                        case "1":set_estado_por_numero(estado,bueno_plato_divisor_gabinete,regular_plato_divisor_gabinete,malo_plato_divisor_gabinete);break;
                        case "2":set_estado_por_numero(estado,bueno_cubierta_trasera,regular_cubierta_trasera,malo_cubierta_trasera);break;
                        case "3":set_estado_por_numero(estado,bueno_bisagra_cubierta,regular_bisagra_cubierta,malo_bisagra_cubierta);break;
                        case "4":set_estado_por_numero(estado,bueno_cubierta_del_frente,regular_cubierta_del_frente,malo_cubierta_del_frente);break;
                        case "5":set_estado_por_numero(estado,bueno_tapa_inferior_gabinete,regular_tapa_inferior_gabinete,malo_tapa_inferior_gabinete);break;
                        case "6":set_estado_por_numero(estado,bueno_pata_de_goma,regular_pata_de_goma,malo_pata_de_goma);break;
                        case "7":set_estado_por_numero(estado,bueno_tornillo_cubierta_plato,regular_tornillo_cubierta_plato,malo_tornillo_cubierta_plato);break;
                        case "8":set_estado_por_numero(estado,bueno_tuerca_montaje_plato,regular_tuerca_montaje_plato,malo_tuerca_montaje_plato);break;
                        case "9":set_estado_por_numero(estado,bueno_boton_toque,regular_boton_toque,malo_boton_toque);break;
                        case "10":set_estado_por_numero(estado,bueno_micro_valvula_cilidro,regular_micro_valvula_cilidro,malo_micro_valvula_cilidro);break;
                        case "11":set_estado_por_numero(estado,bueno_cubierta_plato_colocacion,regular_cubierta_plato_colocacion,malo_cubierta_plato_colocacion);break;
                        case "12":set_estado_por_numero(estado,bueno_plato_colocacion,regular_plato_colocacion,malo_plato_colocacion);break;
                        case "13":set_estado_por_numero(estado,bueno_plato_colocacion_emsamblaje,regular_plato_colocacion_emsamblaje,malo_plato_colocacion_emsamblaje);break;
                        case "14":set_estado_por_numero(estado,bueno_tornillo_para_ZT40x2,regular_tornillo_para_ZT40x2,malo_tornillo_para_ZT40x2);break;
                        case "15":set_estado_por_numero(estado,bueno_placa_montaje_TTP,regular_placa_montaje_TTP,malo_placa_montaje_TTP);break;
                        case "16":set_estado_por_numero(estado,bueno_conector_Y_4mm,regular_conector_Y_4mm,malo_conector_Y_4mm);break;
                        case "17":set_estado_por_numero(estado,bueno_sensor_sangrado,regular_sensor_sangrado,malo_sensor_sangrado);break;
                        case "18":set_estado_por_numero(estado,bueno_tornillo_para_ZT403NSx2,regular_tornillo_para_ZT403NSx2,malo_tornillo_para_ZT403NSx2);break;
                        case "19":set_estado_por_numero(estado,bueno_boton_tactil,regular_boton_tactil,malo_boton_tactil);break;
                        case "20":set_estado_por_numero(estado,bueno_TTP_cuerpo_placa,regular_TTP_cuerpo_placa,malo_TTP_cuerpo_placa);break;
                        case "21":set_estado_por_numero(estado,bueno_conjunto_placa_tactil,regular_conjunto_placa_tactil,malo_conjunto_placa_tactil);break;
                        case "22":set_estado_por_numero(estado,bueno_jeringa_secundaria01,regular_jeringa_secundaria01,malo_jeringa_secundaria01);break;
                        case "23":set_estado_por_numero(estado,bueno_jeringa_secundaria02,regular_jeringa_secundaria02,malo_jeringa_secundaria02);break;
                        case "24":set_estado_por_numero(estado,bueno_tuerca_seguridad_punta,regular_tuerca_seguridad_punta,malo_tuerca_seguridad_punta);break;
                        case "25":set_estado_por_numero(estado,bueno_conector_ZT621NS,regular_conector_ZT621NS,malo_conector_ZT621NS);break;
                        case "26":set_estado_por_numero(estado,bueno_tuberia_conectar_jeringasPSx10NS,regular_tuberia_conectar_jeringasPSx10NS,malo_tuberia_conectar_jeringasPSx10NS);break;
                        case "27":set_estado_por_numero(estado,bueno_tuberia_conectar_jeringasPSx10,regular_tuberia_conectar_jeringasPSx10,malo_tuberia_conectar_jeringasPSx10);break;
                        case "28":set_estado_por_numero(estado,bueno_juego_empaques_1millon01,regular_juego_empaques_1millon01,malo_juego_empaques_1millon01);break;
                        case "29":set_estado_por_numero(estado,bueno_juego_empaques_1millon02,regular_juego_empaques_1millon02,malo_juego_empaques_1millon02);break;
                        case "30":set_estado_por_numero(estado,bueno_manometro,regular_manometro,malo_manometro);break;
                        case "31":set_estado_por_numero(estado,bueno_contador_de_lote,regular_contador_de_lote,malo_contador_de_lote);break;
                        case "32":set_estado_por_numero(estado,bueno_contador_totalizador,regular_contador_totalizador,malo_contador_totalizador);break;
                        case "33":set_estado_por_numero(estado,bueno_cubierta_cont_lote,regular_cubierta_cont_lote,malo_cubierta_cont_lote);break;
                        case "34":set_estado_por_numero(estado,bueno_cubierta_cont_totalizador,regular_cubierta_cont_totalizador,malo_cubierta_cont_totalizador);break;
                        case "35":set_estado_por_numero(estado,bueno_placa_fijar_cont_lote,regular_placa_fijar_cont_lote,malo_placa_fijar_cont_lote);break;
                        case "36":set_estado_por_numero(estado,bueno_placa_fijar_cont_totalizador,regular_placa_fijar_cont_totalizador,malo_placa_fijar_cont_totalizador);break;
                        case "37":set_estado_por_numero(estado,bueno_filtro_regulador,regular_filtro_regulador,malo_filtro_regulador);break;
                        case "38":set_estado_por_numero(estado,bueno_conector_espigado14,regular_conector_espigado14,malo_conector_espigado14);break;
                        case "39":set_estado_por_numero(estado,bueno_conector_macho_aire,regular_conector_macho_aire,malo_conector_macho_aire);break;
                        case "40":set_estado_por_numero(estado,bueno_conector_hembra_aire,regular_conector_hembra_aire,malo_conector_hembra_aire);break;
                        case "41":set_estado_por_numero(estado,bueno_acople_reductor_manometro,regular_acople_reductor_manometro,malo_acople_reductor_manometro);break;
                        case "42":set_estado_por_numero(estado,bueno_tapon_de_gomaValAguja,regular_tapon_de_gomaValAguja,malo_tapon_de_gomaValAguja);break;
                        case "43":set_estado_por_numero(estado,bueno_empaque_conector_macho_airex10,regular_empaque_conector_macho_airex10,malo_empaque_conector_macho_airex10);break;
                        case "44":set_estado_por_numero(estado,bueno_plato_montaje_tornillos_manometro,regular_plato_montaje_tornillos_manometro,malo_plato_montaje_tornillos_manometro);break;
                        case "45":set_estado_por_numero(estado,bueno_boton_reinicio_contador,regular_boton_reinicio_contador,malo_boton_reinicio_contador);break;
                        case "46":set_estado_por_numero(estado,bueno_mecanismo_interno_botonReiniciador,regular_mecanismo_interno_botonReiniciador,malo_mecanismo_interno_botonReiniciador);break;
                        case "47":set_estado_por_numero(estado,bueno_interruptor_encendido_apagado,regular_interruptor_encendido_apagado,malo_interruptor_encendido_apagado);break;
                        case "48":set_estado_por_numero(estado,bueno_mec_interruptor_ONOF,regular_mec_interruptor_ONOF,malo_mec_interruptor_ONOF);break;
                        case "49":set_estado_por_numero(estado,bueno_boton_prueba,regular_boton_prueba,malo_boton_prueba);break;
                        case "50":set_estado_por_numero(estado,bueno_mec_interno_boton_prueba,regular_mec_interno_boton_prueba,malo_mec_interno_boton_prueba);break;
                        case "51":set_estado_por_numero(estado,bueno_interruptor_preSeleccion,regular_interruptor_preSeleccion,malo_interruptor_preSeleccion);break;
                        case "52":set_estado_por_numero(estado,bueno_mec_interruptor_preSeleccion,regular_mec_interruptor_preSeleccion,malo_mec_interruptor_preSeleccion);break;
                        case "53":set_estado_por_numero(estado,bueno_valvula_neumaticaAguja,regular_valvula_neumaticaAguja,malo_valvula_neumaticaAguja);break;
                        case "54":set_estado_por_numero(estado,bueno_celula_logica_azul,regular_celula_logica_azul,malo_celula_logica_azul);break;
                        case "55":set_estado_por_numero(estado,bueno_celula_logica_gris,regular_celula_logica_gris,malo_celula_logica_gris);break;
                        case "56":set_estado_por_numero(estado,bueno_celula_logica_amarilla,regular_celula_logica_amarilla,malo_celula_logica_amarilla);break;
                        case "57":set_estado_por_numero(estado,bueno_valvula_activacionZT35,regular_valvula_activacionZT35,malo_valvula_activacionZT35);break;
                        case "58":set_estado_por_numero(estado,bueno_ONOFF_interruptor_prueba,regular_ONOFF_interruptor_prueba,malo_ONOFF_interruptor_prueba);break;
                        case "59":set_estado_por_numero(estado,bueno_ONOFF_mec_cambio_prueba,regular_ONOFF_mec_cambio_prueba,malo_ONOFF_mec_cambio_prueba);break;
                        case "60":set_estado_por_numero(estado,bueno_simple_doble_mec_interruptorModo,regular_simple_doble_mec_interruptorModo,malo_simple_doble_mec_interruptorModo);break;
                        case "61":set_estado_por_numero(estado,bueno_simple_doble_mec_interruptor,regular_simple_doble_mec_interruptor,malo_simple_doble_mec_interruptor);break;
                        case "62":set_estado_por_numero(estado,bueno_conector_recto4mm,regular_conector_recto4mm,malo_conector_recto4mm);break;
                        case "63":set_estado_por_numero(estado,bueno_valvula_retencion_linea,regular_valvula_retencion_linea,malo_valvula_retencion_linea);break;
                        case "64":set_estado_por_numero(estado,bueno_valvula_unidireccional,regular_valvula_unidireccional,malo_valvula_unidireccional);break;
                        case "65":set_estado_por_numero(estado,bueno_conectorT_4mm,regular_conectorT_4mm,malo_conectorT_4mm);break;
                        case "66":set_estado_por_numero(estado,bueno_distribuidor_aire_multiple,regular_distribuidor_aire_multiple,malo_distribuidor_aire_multiple);break;
                        case "67":set_estado_por_numero(estado,bueno_conector_cruz4mm,regular_conector_cruz4mm,malo_conector_cruz4mm);break;
                        case "68":set_estado_por_numero(estado,bueno_valvula3salidas,regular_valvula3salidas,malo_valvula3salidas);break;
                        case "69":set_estado_por_numero(estado,bueno_celula_base,regular_celula_base,malo_celula_base);break;
                        case "70":set_estado_por_numero(estado,bueno_valvula_impulso,regular_valvula_impulso,malo_valvula_impulso);break;
                        case "71":set_estado_por_numero(estado,bueno_espaciador_entrada_segundaJeringa,regular_espaciador_entrada_segundaJeringa,malo_espaciador_entrada_segundaJeringa);break;
                        case "72":set_estado_por_numero(estado,bueno_cilidro_aire_tuercaMontaje,regular_cilidro_aire_tuercaMontaje,malo_cilidro_aire_tuercaMontaje);break;
                        case "73":set_estado_por_numero(estado,bueno_conector4mm_cilindro_aire,regular_conector4mm_cilindro_aire,malo_conector4mm_cilindro_aire);break;
                        case "74":set_estado_por_numero(estado,bueno_detector_manetico,regular_detector_manetico,malo_detector_manetico);break;
                        case "75":set_estado_por_numero(estado,bueno_conector_acople,regular_conector_acople,malo_conector_acople);break;
                        case "76":set_estado_por_numero(estado,bueno_impulsorEspaciador_jeringaSecundaria,regular_impulsorEspaciador_jeringaSecundaria,malo_impulsorEspaciador_jeringaSecundaria);break;
                        case "77":set_estado_por_numero(estado,bueno_resorteSostenedor_jeringa,regular_resorteSostenedor_jeringa,malo_resorteSostenedor_jeringa);break;
                        case "78":set_estado_por_numero(estado,bueno_sostenedor_jeringa,regular_sostenedor_jeringa,malo_sostenedor_jeringa);break;
                        case "79":set_estado_por_numero(estado,bueno_tornillos_fijacion6x20,regular_tornillos_fijacion6x20,malo_tornillos_fijacion6x20);break;
                        case "80":set_estado_por_numero(estado,bueno_tornillos_fijacion6x50,regular_tornillos_fijacion6x50,malo_tornillos_fijacion6x50);break;
                        case "81":set_estado_por_numero(estado,bueno_placa_mont_sostenedorJeringa,regular_placa_mont_sostenedorJeringa,malo_placa_mont_sostenedorJeringa);break;
                        case "82":set_estado_por_numero(estado,bueno_plato_guiaSostenedor_jeringa,regular_plato_guiaSostenedor_jeringa,malo_plato_guiaSostenedor_jeringa);break;
                        case "83":set_estado_por_numero(estado,bueno_probeta_calibrador,regular_probeta_calibrador,malo_probeta_calibrador);break;
                        case "84":set_estado_por_numero(estado,bueno_pieza_finalPiston,regular_pieza_finalPiston,malo_pieza_finalPiston);break;
                        case "85":set_estado_por_numero(estado,bueno_resortes_embolo,regular_resortes_embolo,malo_resortes_embolo);break;
                        case "86":set_estado_por_numero(estado,bueno_tuerca_tapaEmbolo,regular_tuerca_tapaEmbolo,malo_tuerca_tapaEmbolo);break;
                        case "87":set_estado_por_numero(estado,bueno_tornillo_4x16,regular_tornillo_4x16,malo_tornillo_4x16);break;
                        case "88":set_estado_por_numero(estado,bueno_aceite_vaselinaSpray,regular_aceite_vaselinaSpray,malo_aceite_vaselinaSpray);break;

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
                                case "1":set_estado_por_numero(estado,bueno_ubicacion_maquina,regular,malo_ubicacion_maquina);break;
                                case "2":set_estado_por_numero(estado,bueno_presion_compresora,regular,malo_presion_compresora);break;
                                case "3":set_estado_por_numero(estado,bueno_presion_maquina,regular,malo_presion_maquina);break;
                                case "4":set_estado_por_numero(estado,bueno_activacion,regular,malo_activacion);break;
                                case "5":set_estado_por_numero(estado,bueno_funcion_contadores,regular,malo_funcion_contadores);break;
                                case "6":set_estado_por_numero(estado,bueno_funcion_silbato,regular,malo_funcion_silbato);break;
                                case "7":set_estado_por_numero(estado,bueno_salida_aguja,regular,malo_salida_aguja);break;
                                case "8":set_estado_por_numero(estado,bueno_calibracion,regular,malo_calibracion);break;
                            }
                            break;

                        case "2":
                            switch(fila.getString(0))
                            {
                                case "1":set_estado_por_numero(estado,bueno_materiales_utilizados,regular,malo_materiales_utilizados);break;
                                case "2":set_estado_por_numero(estado,bueno_cambio_descartables,regular,malo_cambio_descartables);break;
                                case "3":set_estado_por_numero(estado,bueno_limpieza_plato_colocacion,regular,malo_limpieza_plato_colocacion);break;
                                case "4":set_estado_por_numero(estado,bueno_esterilizado_jeringa,regular,malo_esterilizado_jeringa);break;
                                case "5":set_estado_por_numero(estado,bueno_limpieza_mod_inyector,regular,malo_limpieza_mod_inyector);break;
                                case "6":set_estado_por_numero(estado,bueno_limpieza_cerebro,regular,malo_limpieza_cerebro);break;
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
        if(id_servicio != -1 && id_maquina!=-1 && id_compania!=-1 && id_tecnico!=-1)
        {
            String DIRECCION_FIRMA_JEFE="Invetsa/Servicio_mantenimiento/"+id_servicio+"_jefe_"+fecha.getText()+".jpeg";
            String DIRECCION_FIRMA_INVETSA="Invetsa/Servicio_mantenimiento/"+id_servicio+"_invetsa_"+fecha.getText()+".jpeg";
            guardar_en_memoria(bm_firma_1,id_servicio+"_jefe_"+fecha.getText()+".jpeg","Invetsa/Servicio_mantenimiento");
            guardar_en_memoria(bm_firma_2,id_servicio+"_invetsa_"+fecha.getText()+".jpeg","Invetsa/Servicio_mantenimiento");

            String codigo=getString(R.string.codigo_zootec);
            String revision=getString(R.string.revision_zootec);

            guardar_servicio_mantenimiento(id_servicio,fecha.getText().toString(),hora_ingreso.getText().toString(),hora_salida.getText().toString(),codigo,revision,DIRECCION_FIRMA_JEFE,DIRECCION_FIRMA_INVETSA,String.valueOf(id_maquina),String.valueOf(id_tecnico),String.valueOf(id_compania),"ZOOTEC");
            int id_inspeccion=1;
            guardar_inspeccion_visual(id_inspeccion,observacion_inspeccion.getText().toString(),piezas_cambiadas_inspeccion.getText().toString(),id_servicio);

            guardar_detalle_inspeccion_visual(1,id_inspeccion,id_servicio,"ZT50","PLATO DIVISOR DEL MEDIO DEL GABINETE",get_estado(bueno_plato_divisor_gabinete.isChecked(),regular_plato_divisor_gabinete.isChecked(),malo_plato_divisor_gabinete.isChecked()));
            guardar_detalle_inspeccion_visual(2,id_inspeccion,id_servicio,"ZT51M-ZT51D","CUBIERTA TRASERA CON LOGO MERIAL",get_estado(bueno_cubierta_trasera.isChecked(),regular_cubierta_trasera.isChecked(),malo_cubierta_trasera.isChecked()));
            guardar_detalle_inspeccion_visual(3,id_inspeccion,id_servicio,"ZT52","BISAGRA PARA LA CUBIERTA DEL FRENTE",get_estado(bueno_bisagra_cubierta.isChecked(),regular_bisagra_cubierta.isChecked(),malo_bisagra_cubierta.isChecked()));
            guardar_detalle_inspeccion_visual(4,id_inspeccion,id_servicio,"ZT53S-ZT53DNS","CUBIERTA DEL FRENTE EN ACERO INOX",get_estado(bueno_cubierta_del_frente.isChecked(),regular_cubierta_del_frente.isChecked(),malo_cubierta_del_frente.isChecked()));
            guardar_detalle_inspeccion_visual(5,id_inspeccion,id_servicio,"ZT54-ZT54D","TAPA INFERIOR DEL GABINETE",get_estado(bueno_tapa_inferior_gabinete.isChecked(),regular_tapa_inferior_gabinete.isChecked(),malo_tapa_inferior_gabinete.isChecked()));
            guardar_detalle_inspeccion_visual(6,id_inspeccion,id_servicio,"ZT55","PATA DE GOMA",get_estado(bueno_pata_de_goma.isChecked(),regular_pata_de_goma.isChecked(),malo_pata_de_goma.isChecked()));
            guardar_detalle_inspeccion_visual(7,id_inspeccion,id_servicio,"ZT46","TORNILLO P/L CUBIERTA DE PLATO",get_estado(bueno_tornillo_cubierta_plato.isChecked(),regular_tornillo_cubierta_plato.isChecked(),malo_tornillo_cubierta_plato.isChecked()));
            guardar_detalle_inspeccion_visual(8,id_inspeccion,id_servicio,"ZT45","TUERCA MONTAJE DEL PLATO DE COLOCACION",get_estado(bueno_tuerca_montaje_plato.isChecked(),regular_tuerca_montaje_plato.isChecked(),malo_tuerca_montaje_plato.isChecked()));
            guardar_detalle_inspeccion_visual(9,id_inspeccion,id_servicio,"ZT44","BOTON DE TOQUE",get_estado(bueno_boton_toque.isChecked(),regular_boton_toque.isChecked(),malo_boton_toque.isChecked()));
            guardar_detalle_inspeccion_visual(10,id_inspeccion,id_servicio,"ZT43","MICRO VALVULA P.ACTIVAR CILINDRO DE AIRE",get_estado(bueno_micro_valvula_cilidro.isChecked(),regular_micro_valvula_cilidro.isChecked(),malo_micro_valvula_cilidro.isChecked()));
            guardar_detalle_inspeccion_visual(11,id_inspeccion,id_servicio,"ZT42","CUBIERTA DEL PLATO DE COLOCACION",get_estado(bueno_cubierta_plato_colocacion.isChecked(),regular_cubierta_plato_colocacion.isChecked(),malo_cubierta_plato_colocacion.isChecked()));
            guardar_detalle_inspeccion_visual(12,id_inspeccion,id_servicio,"ZT41","PLATO DE COLOCACION",get_estado(bueno_plato_colocacion.isChecked(),regular_plato_colocacion.isChecked(),malo_plato_colocacion.isChecked()));
            guardar_detalle_inspeccion_visual(13,id_inspeccion,id_servicio,"ZT40","PLATO DE COLOCACION EMSAMBLAJE CUBIERTA",get_estado(bueno_plato_colocacion_emsamblaje.isChecked(),regular_plato_colocacion_emsamblaje.isChecked(),malo_plato_colocacion_emsamblaje.isChecked()));
            guardar_detalle_inspeccion_visual(14,id_inspeccion,id_servicio,"ZT410","TORNILLO PARA ZT409NS(x2)",get_estado(bueno_tornillo_para_ZT40x2.isChecked(),regular_tornillo_para_ZT40x2.isChecked(),malo_tornillo_para_ZT40x2.isChecked()));
            guardar_detalle_inspeccion_visual(15,id_inspeccion,id_servicio,"ZT409","PLACA DE MONTAJE PARA TTP",get_estado(bueno_placa_montaje_TTP.isChecked(),regular_placa_montaje_TTP.isChecked(),malo_placa_montaje_TTP.isChecked()));
            guardar_detalle_inspeccion_visual(16,id_inspeccion,id_servicio,"ZT407","CONECTOR Y (4mm)",get_estado(bueno_conector_Y_4mm.isChecked(),regular_conector_Y_4mm.isChecked(),malo_conector_Y_4mm.isChecked()));
            guardar_detalle_inspeccion_visual(17,id_inspeccion,id_servicio,"ZT406","SENSOR DE SANGRADO",get_estado(bueno_sensor_sangrado.isChecked(),regular_sensor_sangrado.isChecked(),malo_sensor_sangrado.isChecked()));
            guardar_detalle_inspeccion_visual(18,id_inspeccion,id_servicio,"ZT405","TORNILLO PARA ZT403NS(x2)",get_estado(bueno_tornillo_para_ZT403NSx2.isChecked(),regular_tornillo_para_ZT403NSx2.isChecked(),malo_tornillo_para_ZT403NSx2.isChecked()));
            guardar_detalle_inspeccion_visual(19,id_inspeccion,id_servicio,"ZT403","BOTON TACTIL",get_estado(bueno_boton_tactil.isChecked(),regular_boton_tactil.isChecked(),malo_boton_tactil.isChecked()));
            guardar_detalle_inspeccion_visual(20,id_inspeccion,id_servicio,"ZT401","TTP CUERPO DE LA PLACA",get_estado(bueno_TTP_cuerpo_placa.isChecked(),regular_TTP_cuerpo_placa.isChecked(),malo_TTP_cuerpo_placa.isChecked()));
            guardar_detalle_inspeccion_visual(21,id_inspeccion,id_servicio,"ZT400","CONJUNTO DE PLACA TACTIL DOBLE TTP",get_estado(bueno_conjunto_placa_tactil.isChecked(),regular_conjunto_placa_tactil.isChecked(),malo_conjunto_placa_tactil.isChecked()));
            guardar_detalle_inspeccion_visual(22,id_inspeccion,id_servicio,"ZT57SM","JERINGA SECUNDARIA DE 0.1ml",get_estado(bueno_jeringa_secundaria01.isChecked(),regular_jeringa_secundaria01.isChecked(),malo_jeringa_secundaria01.isChecked()));
            guardar_detalle_inspeccion_visual(23,id_inspeccion,id_servicio,"ZT58SM","JERINGA SECUNDARIA DE 0.2ml",get_estado(bueno_jeringa_secundaria02.isChecked(),regular_jeringa_secundaria02.isChecked(),malo_jeringa_secundaria02.isChecked()));
            guardar_detalle_inspeccion_visual(24,id_inspeccion,id_servicio,"ZT63M","TUERCA SEGURIDAD PUNTA JERINGA",get_estado(bueno_tuerca_seguridad_punta.isChecked(),regular_tuerca_seguridad_punta.isChecked(),malo_tuerca_seguridad_punta.isChecked()));
            guardar_detalle_inspeccion_visual(25,id_inspeccion,id_servicio,"ZT626","CONECTOR PARA ZT621NS-ZT625NS",get_estado(bueno_conector_ZT621NS.isChecked(),regular_conector_ZT621NS.isChecked(),malo_conector_ZT621NS.isChecked()));
            guardar_detalle_inspeccion_visual(26,id_inspeccion,id_servicio,"ZT623NS","TUBERIA PARA CONECTAR JERINGAS P./S.(x10)NS",get_estado(bueno_tuberia_conectar_jeringasPSx10NS.isChecked(),regular_tuberia_conectar_jeringasPSx10NS.isChecked(),malo_tuberia_conectar_jeringasPSx10NS.isChecked()));
            guardar_detalle_inspeccion_visual(27,id_inspeccion,id_servicio,"ZT623","TUBERIA PARA CONECTAR JERINGAS P./S.(x10)",get_estado(bueno_tuberia_conectar_jeringasPSx10.isChecked(),regular_tuberia_conectar_jeringasPSx10.isChecked(),malo_tuberia_conectar_jeringasPSx10.isChecked()));
            guardar_detalle_inspeccion_visual(28,id_inspeccion,id_servicio,"ZT661M","JUEGO EMPAQUES 1MILLON INY.0.1ML.",get_estado(bueno_juego_empaques_1millon01.isChecked(),regular_juego_empaques_1millon01.isChecked(),malo_juego_empaques_1millon01.isChecked()));
            guardar_detalle_inspeccion_visual(29,id_inspeccion,id_servicio,"ZT681M","JUEGO EMPAQUES 1MILLON INY.0.2ML.",get_estado(bueno_juego_empaques_1millon02.isChecked(),regular_juego_empaques_1millon02.isChecked(),malo_juego_empaques_1millon02.isChecked()));
            guardar_detalle_inspeccion_visual(30,id_inspeccion,id_servicio,"ZT01-ZT01NS","MANOMETRO",get_estado(bueno_manometro.isChecked(),regular_manometro.isChecked(),malo_manometro.isChecked()));
            guardar_detalle_inspeccion_visual(31,id_inspeccion,id_servicio,"ZT02","CONTADOR DE LOTE",get_estado(bueno_contador_de_lote.isChecked(),regular_contador_de_lote.isChecked(),malo_contador_de_lote.isChecked()));
            guardar_detalle_inspeccion_visual(32,id_inspeccion,id_servicio,"ZT03","CONTADOR TOTALIZADOR",get_estado(bueno_contador_totalizador.isChecked(),regular_contador_totalizador.isChecked(),malo_contador_totalizador.isChecked()));
            guardar_detalle_inspeccion_visual(33,id_inspeccion,id_servicio,"ZT04","CUBIERTA DEL CONT. LOTE",get_estado(bueno_cubierta_cont_lote.isChecked(),regular_cubierta_cont_lote.isChecked(),malo_cubierta_cont_lote.isChecked()));
            guardar_detalle_inspeccion_visual(34,id_inspeccion,id_servicio,"ZT05","CUBIERTA CONT. TOTALIZADOR",get_estado(bueno_cubierta_cont_totalizador.isChecked(),regular_cubierta_cont_totalizador.isChecked(),malo_cubierta_cont_totalizador.isChecked()));
            guardar_detalle_inspeccion_visual(35,id_inspeccion,id_servicio,"ZT06","PLACA PARA FIJAR EL CONT. LOTE",get_estado(bueno_placa_fijar_cont_lote.isChecked(),regular_placa_fijar_cont_lote.isChecked(),malo_placa_fijar_cont_lote.isChecked()));
            guardar_detalle_inspeccion_visual(36,id_inspeccion,id_servicio,"ZT07","PLACA PARA FIJAR EL CONT. TOTALIZADOR",get_estado(bueno_placa_fijar_cont_totalizador.isChecked(),regular_placa_fijar_cont_totalizador.isChecked(),malo_placa_fijar_cont_totalizador.isChecked()));
            guardar_detalle_inspeccion_visual(37,id_inspeccion,id_servicio,"ZT17","FILTRO REGULADOR",get_estado(bueno_filtro_regulador.isChecked(),regular_filtro_regulador.isChecked(),malo_filtro_regulador.isChecked()));
            guardar_detalle_inspeccion_visual(38,id_inspeccion,id_servicio,"ZT18","CONECTOR ESPIGADO EN CODO 1/4 P/FILTRO",get_estado(bueno_conector_espigado14.isChecked(),regular_conector_espigado14.isChecked(),malo_conector_espigado14.isChecked()));
            guardar_detalle_inspeccion_visual(39,id_inspeccion,id_servicio,"ZT23","CONECTOR MACHO PARA AIRE",get_estado(bueno_conector_macho_aire.isChecked(),regular_conector_macho_aire.isChecked(),malo_conector_macho_aire.isChecked()));
            guardar_detalle_inspeccion_visual(40,id_inspeccion,id_servicio,"ZT24","CONECTOR HEMBRA PARA AIRE",get_estado(bueno_conector_hembra_aire.isChecked(),regular_conector_hembra_aire.isChecked(),malo_conector_hembra_aire.isChecked()));
            guardar_detalle_inspeccion_visual(41,id_inspeccion,id_servicio,"ZT25","ACOPLE REDUCTOR CONECTOR DEL MANOMETRO",get_estado(bueno_acople_reductor_manometro.isChecked(),regular_acople_reductor_manometro.isChecked(),malo_acople_reductor_manometro.isChecked()));
            guardar_detalle_inspeccion_visual(42,id_inspeccion,id_servicio,"ZT28","TAPON DE GOMA(VAL.AGUJA)",get_estado(bueno_tapon_de_gomaValAguja.isChecked(),regular_tapon_de_gomaValAguja.isChecked(),malo_tapon_de_gomaValAguja.isChecked()));

            guardar_detalle_inspeccion_visual(43,id_inspeccion,id_servicio,"ZT26","EMPAQUE P/CONECTOR MACHO AIRE(x10)",get_estado(bueno_empaque_conector_macho_airex10.isChecked(),regular_empaque_conector_macho_airex10.isChecked(),malo_empaque_conector_macho_airex10.isChecked()));
            guardar_detalle_inspeccion_visual(44,id_inspeccion,id_servicio,"ZT30","PLATO DE MONTAJE C/TORNILLOS-MANOMETRO",get_estado(bueno_plato_montaje_tornillos_manometro.isChecked(),regular_plato_montaje_tornillos_manometro.isChecked(),malo_plato_montaje_tornillos_manometro.isChecked()));
            guardar_detalle_inspeccion_visual(45,id_inspeccion,id_servicio,"ZT14","BOTON DE REINICIO DEL CONTADOR",get_estado(bueno_boton_reinicio_contador.isChecked(),regular_boton_reinicio_contador.isChecked(),malo_boton_reinicio_contador.isChecked()));
            guardar_detalle_inspeccion_visual(46,id_inspeccion,id_servicio,"ZT16","MECANISMO INTERNO BOTON DE INICIADOR",get_estado(bueno_mecanismo_interno_botonReiniciador.isChecked(),regular_mecanismo_interno_botonReiniciador.isChecked(),malo_mecanismo_interno_botonReiniciador.isChecked()));
            guardar_detalle_inspeccion_visual(47,id_inspeccion,id_servicio,"ZT08","INTERRUPTOR ENCENDIDO / APAGADO",get_estado(bueno_interruptor_encendido_apagado.isChecked(),regular_interruptor_encendido_apagado.isChecked(),malo_interruptor_encendido_apagado.isChecked()));
            guardar_detalle_inspeccion_visual(48,id_inspeccion,id_servicio,"ZT10","MEC. INTERNO P/INTERRUPTOR ON/OFF",get_estado(bueno_mec_interruptor_ONOF.isChecked(),regular_mec_interruptor_ONOF.isChecked(),malo_mec_interruptor_ONOF.isChecked()));
            guardar_detalle_inspeccion_visual(49,id_inspeccion,id_servicio,"ZT11","BOTON DE PRUEBA",get_estado(bueno_boton_prueba.isChecked(),regular_boton_prueba.isChecked(),malo_boton_prueba.isChecked()));
            guardar_detalle_inspeccion_visual(50,id_inspeccion,id_servicio,"ZT13","MEC. INTERNO DEL BOTON DE PRUEBA",get_estado(bueno_mec_interno_boton_prueba.isChecked(),regular_mec_interno_boton_prueba.isChecked(),malo_mec_interno_boton_prueba.isChecked()));
            guardar_detalle_inspeccion_visual(51,id_inspeccion,id_servicio,"ZT091","INTERRUPTOR PRE-SELECCION METODO INY.",get_estado(bueno_interruptor_preSeleccion.isChecked(),regular_interruptor_preSeleccion.isChecked(),malo_interruptor_preSeleccion.isChecked()));
            guardar_detalle_inspeccion_visual(52,id_inspeccion,id_servicio,"ZT092","MEC. INTERNO P/INTERRUPTOR PRE-SELECCION",get_estado(bueno_mec_interruptor_preSeleccion.isChecked(),regular_mec_interruptor_preSeleccion.isChecked(),malo_mec_interruptor_preSeleccion.isChecked()));
            guardar_detalle_inspeccion_visual(53,id_inspeccion,id_servicio,"ZT27","VALVULA NEUMATICA DE LA AGUJA",get_estado(bueno_valvula_neumaticaAguja.isChecked(),regular_valvula_neumaticaAguja.isChecked(),malo_valvula_neumaticaAguja.isChecked()));
            guardar_detalle_inspeccion_visual(54,id_inspeccion,id_servicio,"ZT37","CELULA LOGICA AZUL",get_estado(bueno_celula_logica_azul.isChecked(),regular_celula_logica_azul.isChecked(),malo_celula_logica_azul.isChecked()));
            guardar_detalle_inspeccion_visual(55,id_inspeccion,id_servicio,"ZT38","CELULA LOGICA GRIS",get_estado(bueno_celula_logica_gris.isChecked(),regular_celula_logica_gris.isChecked(),malo_celula_logica_gris.isChecked()));
            guardar_detalle_inspeccion_visual(56,id_inspeccion,id_servicio,"ZT39","CELULA LOGICA AMARILLA",get_estado(bueno_celula_logica_amarilla.isChecked(),regular_celula_logica_amarilla.isChecked(),malo_celula_logica_amarilla.isChecked()));
            guardar_detalle_inspeccion_visual(57,id_inspeccion,id_servicio,"ZT355","VALVULA DE ACTIVACION PARA EL ZT35",get_estado(bueno_valvula_activacionZT35.isChecked(),regular_valvula_activacionZT35.isChecked(),malo_valvula_activacionZT35.isChecked()));
            guardar_detalle_inspeccion_visual(58,id_inspeccion,id_servicio,"ZT367","ON-OFF INTERRUPTOR DE PRUEBA",get_estado(bueno_ONOFF_interruptor_prueba.isChecked(),regular_ONOFF_interruptor_prueba.isChecked(),malo_ONOFF_interruptor_prueba.isChecked()));
            guardar_detalle_inspeccion_visual(59,id_inspeccion,id_servicio,"ZT365","ON-OFF MECANISMO DE CAMBIO DE PRUEBA",get_estado(bueno_ONOFF_mec_cambio_prueba.isChecked(),regular_ONOFF_mec_cambio_prueba.isChecked(),malo_ONOFF_mec_cambio_prueba.isChecked()));
            guardar_detalle_inspeccion_visual(60,id_inspeccion,id_servicio,"ZT368","SIMPLE/DOBLE MECANISMO(INTERRUPTOR DE MODO)",get_estado(bueno_simple_doble_mec_interruptorModo.isChecked(),regular_simple_doble_mec_interruptorModo.isChecked(),malo_simple_doble_mec_interruptorModo.isChecked()));
            guardar_detalle_inspeccion_visual(61,id_inspeccion,id_servicio,"ZT366","SIMPLE/DOBLE MECANISMO DEL INTERRUPTOR",get_estado(bueno_simple_doble_mec_interruptor.isChecked(),regular_simple_doble_mec_interruptor.isChecked(),malo_simple_doble_mec_interruptor.isChecked()));
            guardar_detalle_inspeccion_visual(62,id_inspeccion,id_servicio,"ZT363","CONECTOR RECTO 4MM",get_estado(bueno_conector_recto4mm.isChecked(),regular_conector_recto4mm.isChecked(),malo_conector_recto4mm.isChecked()));
            guardar_detalle_inspeccion_visual(63,id_inspeccion,id_servicio,"ZT370","VALVULA RETENCION EN LINEA(VAL. FLUJO LIBRE)",get_estado(bueno_valvula_retencion_linea.isChecked(),regular_valvula_retencion_linea.isChecked(),malo_valvula_retencion_linea.isChecked()));
            guardar_detalle_inspeccion_visual(64,id_inspeccion,id_servicio,"ZT362","VALVULA UNIDIRECCIONAL",get_estado(bueno_valvula_unidireccional.isChecked(),regular_valvula_unidireccional.isChecked(),malo_valvula_unidireccional.isChecked()));
            guardar_detalle_inspeccion_visual(65,id_inspeccion,id_servicio,"ZT357","CONECTOR T (4mm)",get_estado(bueno_conectorT_4mm.isChecked(),regular_conectorT_4mm.isChecked(),malo_conectorT_4mm.isChecked()));
            guardar_detalle_inspeccion_visual(66,id_inspeccion,id_servicio,"ZT356","DISTRIBUIDOR DE AIRE MULTIPLE",get_estado(bueno_distribuidor_aire_multiple.isChecked(),regular_distribuidor_aire_multiple.isChecked(),malo_distribuidor_aire_multiple.isChecked()));
            guardar_detalle_inspeccion_visual(67,id_inspeccion,id_servicio,"ZT358","CONECTOR EN CRUZ (4mm)",get_estado(bueno_conector_cruz4mm.isChecked(),regular_conector_cruz4mm.isChecked(),malo_conector_cruz4mm.isChecked()));
            guardar_detalle_inspeccion_visual(68,id_inspeccion,id_servicio,"ZT361","VALVULA 3 SALIDAS",get_estado(bueno_valvula3salidas.isChecked(),regular_valvula3salidas.isChecked(),malo_valvula3salidas.isChecked()));
            guardar_detalle_inspeccion_visual(69,id_inspeccion,id_servicio,"ZT360","CELULA+BASE(RELE SENSOR AMARILLO)",get_estado(bueno_celula_base.isChecked(),regular_celula_base.isChecked(),malo_celula_base.isChecked()));
            guardar_detalle_inspeccion_visual(70,id_inspeccion,id_servicio,"ZT369","VALVULA DE IMPULSO+CONECTOR",get_estado(bueno_valvula_impulso.isChecked(),regular_valvula_impulso.isChecked(),malo_valvula_impulso.isChecked()));
            guardar_detalle_inspeccion_visual(71,id_inspeccion,id_servicio,"ZT625NS","ESPACIADOR ENTRADA D/L SEGUNDA JERINGA",get_estado(bueno_espaciador_entrada_segundaJeringa.isChecked(),regular_espaciador_entrada_segundaJeringa.isChecked(),malo_espaciador_entrada_segundaJeringa.isChecked()));
            guardar_detalle_inspeccion_visual(72,id_inspeccion,id_servicio,"ZT19","CILINDRO DE AIRE CON TUERCA DE MONTAJE",get_estado(bueno_cilidro_aire_tuercaMontaje.isChecked(),regular_cilidro_aire_tuercaMontaje.isChecked(),malo_cilidro_aire_tuercaMontaje.isChecked()));
            guardar_detalle_inspeccion_visual(73,id_inspeccion,id_servicio,"ZT20","CONECTOR DE 4mm P/CILINDRO DE AIRE",get_estado(bueno_conector4mm_cilindro_aire.isChecked(),regular_conector4mm_cilindro_aire.isChecked(),malo_conector4mm_cilindro_aire.isChecked()));
            guardar_detalle_inspeccion_visual(74,id_inspeccion,id_servicio,"ZT191","DETECTOR MANETICO DE POSICION P/CILINDRO DE AIIRE",get_estado(bueno_detector_manetico.isChecked(),regular_detector_manetico.isChecked(),malo_detector_manetico.isChecked()));
            guardar_detalle_inspeccion_visual(75,id_inspeccion,id_servicio,"ZT21B","CONECTOR PARA ACOPLE DE JERINGA",get_estado(bueno_conector_acople.isChecked(),regular_conector_acople.isChecked(),malo_conector_acople.isChecked()));
            guardar_detalle_inspeccion_visual(76,id_inspeccion,id_servicio,"ZT212","IMPULSADOR ESPACIADOR D/L JERINGA SECUNDARIA",get_estado(bueno_impulsorEspaciador_jeringaSecundaria.isChecked(),regular_impulsorEspaciador_jeringaSecundaria.isChecked(),malo_impulsorEspaciador_jeringaSecundaria.isChecked()));
            guardar_detalle_inspeccion_visual(77,id_inspeccion,id_servicio,"ZT33","RESORTE P/SOSTENEDOR DE LA JERINGA",get_estado(bueno_resorteSostenedor_jeringa.isChecked(),regular_resorteSostenedor_jeringa.isChecked(),malo_resorteSostenedor_jeringa.isChecked()));
            guardar_detalle_inspeccion_visual(78,id_inspeccion,id_servicio,"ZT311","SOSTENEDOR DE LA JERINGA",get_estado(bueno_sostenedor_jeringa.isChecked(),regular_sostenedor_jeringa.isChecked(),malo_sostenedor_jeringa.isChecked()));
            guardar_detalle_inspeccion_visual(79,id_inspeccion,id_servicio,"ZT91","TORNILLOS DE FIJACION 6x20 PARA ZT31(x2)",get_estado(bueno_tornillos_fijacion6x20.isChecked(),regular_tornillos_fijacion6x20.isChecked(),malo_tornillos_fijacion6x20.isChecked()));
            guardar_detalle_inspeccion_visual(80,id_inspeccion,id_servicio,"ZT911","TORNILLOS DE FIJACION 6x50",get_estado(bueno_tornillos_fijacion6x50.isChecked(),regular_tornillos_fijacion6x50.isChecked(),malo_tornillos_fijacion6x50.isChecked()));
            guardar_detalle_inspeccion_visual(81,id_inspeccion,id_servicio,"ZT34","PLACA P/MONT. DEL SOSTENEDOR D/L JERINGA",get_estado(bueno_placa_mont_sostenedorJeringa.isChecked(),regular_placa_mont_sostenedorJeringa.isChecked(),malo_placa_mont_sostenedorJeringa.isChecked()));
            guardar_detalle_inspeccion_visual(82,id_inspeccion,id_servicio,"ZT49","PLATO GUIA P/SOSTENEDOR D/L JERINGA",get_estado(bueno_plato_guiaSostenedor_jeringa.isChecked(),regular_plato_guiaSostenedor_jeringa.isChecked(),malo_plato_guiaSostenedor_jeringa.isChecked()));
            guardar_detalle_inspeccion_visual(83,id_inspeccion,id_servicio,"ZT84","PROBETA CALIBRADOR",get_estado(bueno_probeta_calibrador.isChecked(),regular_probeta_calibrador.isChecked(),malo_probeta_calibrador.isChecked()));
            guardar_detalle_inspeccion_visual(84,id_inspeccion,id_servicio,"ZT83M","PIEZA FINAL DEL PISTON DE LA JERINGA",get_estado(bueno_pieza_finalPiston.isChecked(),regular_pieza_finalPiston.isChecked(),malo_pieza_finalPiston.isChecked()));
            guardar_detalle_inspeccion_visual(85,id_inspeccion,id_servicio,"ZT82M","RESORTES DEL EMBOLO D/L JERINGA",get_estado(bueno_resortes_embolo.isChecked(),regular_resortes_embolo.isChecked(),malo_resortes_embolo.isChecked()));
            guardar_detalle_inspeccion_visual(86,id_inspeccion,id_servicio,"ZT81M","TUERCA TAPA D/EMBOLO D/L JERINGA",get_estado(bueno_tuerca_tapaEmbolo.isChecked(),regular_tuerca_tapaEmbolo.isChecked(),malo_tuerca_tapaEmbolo.isChecked()));
            guardar_detalle_inspeccion_visual(87,id_inspeccion,id_servicio,"ZT90","TORNILLO 4x16 P/L PIEZA FINAL JERINGA ZT83M",get_estado(bueno_tornillo_4x16.isChecked(),regular_tornillo_4x16.isChecked(),malo_tornillo_4x16.isChecked()));
            guardar_detalle_inspeccion_visual(88,id_inspeccion,id_servicio,"ZT101","ACEITE VASELINA EN SPRAY (VEGETAL)",get_estado(bueno_aceite_vaselinaSpray.isChecked(),regular_aceite_vaselinaSpray.isChecked(),malo_aceite_vaselinaSpray.isChecked()));


            ///REGISTRO DE INSPECCION E FUNCIONAMIENTO
            int id_funcionamiento=1;
            guardar_inspeccion_funcionamiento(id_funcionamiento,observacion_funcionamiento.getText().toString(),frecuencia_uso_funcionamiento.getText().toString(),id_servicio);

            //REGISTO DE LOS DETALLES DE INSPECCION EL  FUNCIONAMIENTO,
            guardar_detalle_inspeccion_funcionamiento(1,id_funcionamiento,id_servicio,"UBICACION DE LA MAQUINA",get_estado(bueno_ubicacion_maquina.isChecked(),false,malo_ubicacion_maquina.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(2,id_funcionamiento,id_servicio,"PRESION DE COMPRESORA",get_estado(bueno_presion_compresora.isChecked(),false,malo_presion_compresora.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(3,id_funcionamiento,id_servicio,"PRESION DE MAQUINA",get_estado(bueno_presion_maquina.isChecked(),false,malo_presion_maquina.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(4,id_funcionamiento,id_servicio,"ACTIVACION",get_estado(bueno_activacion.isChecked(),false,malo_activacion.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(5,id_funcionamiento,id_servicio,"FUNCION DE CONTADORES",get_estado(bueno_funcion_contadores.isChecked(),false,malo_funcion_contadores.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(6,id_funcionamiento,id_servicio,"FUNCIONAMIENTO SILVATO",get_estado(bueno_funcion_silbato.isChecked(),false,malo_funcion_silbato.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(7,id_funcionamiento,id_servicio,"SALIDA DE AGUJA",get_estado(bueno_salida_aguja.isChecked(),false,malo_salida_aguja.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(8,id_funcionamiento,id_servicio,"CALIBRACION",get_estado(bueno_calibracion.isChecked(),false,malo_calibracion.isChecked()));

            ///REGISTRO DE LIMPIEZA Y DESINFECCION
            id_funcionamiento=2;
            guardar_inspeccion_funcionamiento(id_funcionamiento,observacion_desinfeccion.getText().toString(),cantidad_aves_desinfeccion.getText().toString(),id_servicio);

            //REGISTO DETALLE DE LIMPIEZA Y DESINFECCION,
            guardar_detalle_inspeccion_funcionamiento(1,id_funcionamiento,id_servicio,"MATERIALES UTILIZADOS",get_estado(bueno_materiales_utilizados.isChecked(),false,malo_materiales_utilizados.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(2,id_funcionamiento,id_servicio,"CAMBIO DE PIEZAS DESCARTABLES",get_estado(bueno_cambio_descartables.isChecked(),false,malo_cambio_descartables.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(3,id_funcionamiento,id_servicio,"LIMPIEZA PLATO COLOCACION",get_estado(bueno_limpieza_plato_colocacion.isChecked(),false,malo_limpieza_plato_colocacion.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(4,id_funcionamiento,id_servicio,"ESTERILIZADO DE JERINGA",get_estado(bueno_esterilizado_jeringa.isChecked(),false,malo_esterilizado_jeringa.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(5,id_funcionamiento,id_servicio,"LIMPIEZA MODULO INYECTOR",get_estado(bueno_limpieza_mod_inyector.isChecked(),false,malo_limpieza_mod_inyector.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(6,id_funcionamiento,id_servicio,"LIMPIEZA DEL CEREBRO",get_estado(bueno_limpieza_cerebro.isChecked(),false,malo_limpieza_cerebro.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(7,id_funcionamiento,id_servicio,"SECADO",get_estado(bueno_secado.isChecked(),false,malo_secado.isChecked()));
            guardar_detalle_inspeccion_funcionamiento(8,id_funcionamiento,id_servicio,"PROTECCION",get_estado(bueno_proteccion.isChecked(),false,malo_proteccion.isChecked()));

/*
 registro.put("id",String.valueOf(id));
        registro.put("observaciones", observaciones);
        registro.put("frecuencia", frecuencia);
        registro.put("id_servicio", String.valueOf(id_servicio));
        bd.insert("inspeccion_funcionamiento", null, registro);
*/
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


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.bt_nuevo:
                startActivity( new Intent(this,Formulario_zootec.class));
                finish();
                break;
            case  R.id.ib_izquierda:
                int aux_sim=0;
                aux_sim=get_id_izquierda(id_zoo);
                if(aux_sim!=-1)
                {   id_zoo=aux_sim;
                    cargar_servicio_mantenimiento(id_zoo);
                }
                else
                {
                    mensaje_ok_error("No existe ningun registro.");
                }
                break;
            case  R.id.ib_derecha:
                int aux_sim1=0;
                aux_sim1=get_id_derecha(id_zoo);
                if(aux_sim1!=-1)
                {   id_zoo=aux_sim1;
                    cargar_servicio_mantenimiento(id_zoo);
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

    private String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

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
        try {
            SQLite admin = new SQLite(this,
                    "invetsa", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();

            registro.put("id", String.valueOf(id));
            registro.put("observaciones", observaciones);
            registro.put("piesas_cambiadas", piesas_cambiadas);
            registro.put("id_servicio", String.valueOf(id_servicio));
            registro.put("imei", imei);
            bd.insert("inspeccion_visual", null, registro);
            bd.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
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
                    jsonParam.put("firma_1",params[2]);
                    jsonParam.put("firma_2",params[3]);

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
                Toast.makeText(getApplicationContext(),suceso.getMensaje(),Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("2"))
            {
                Toast.makeText(getApplicationContext(),suceso.getMensaje(),Toast.LENGTH_SHORT).show();
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
