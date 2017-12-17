package com.grayhatcorp.invetsa.invetsa.Base_de_datos;

/**
 * Created by elisoft on 07-11-16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {

    public SQLite(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table compania(" +
                "id integer primary key not null," +
                "nombre varchar(50)" +
                ")");

        db.execSQL("create table tecnico(" +
                "id integer primary key not null," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("create table maquina(" +
                "id integer primary key not null, " +
                "codigo varchar(15)," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("create table servicio_mantenimiento(" +
                "id integer primary key not null," +
                "fecha text ," +
                "hora_ingreso text," +
                "hora_salidas text," +
                "codigo varchar(20)," +
                "revision varchar(20)," +
                "firma_jefe_planta text," +
                "firma_invetsa text," +
                "id_maquina integer," +
                "id_tecnico integer," +
                "id_compania integer," +
                "imei text," +
                "formulario text," +
                "imagen_jefe text"+
                ")");
        db.execSQL("create table inspeccion_visual(" +
                "id integer not null," +
                "observaciones text," +
                "piesas_cambiadas text," +
                "id_servicio integer," +
                "imei text," +
                "primary key(id,id_servicio)" +
                ")");

        db.execSQL("create table detalle_inspeccion_visual(" +
                "id integer not null," +
                "id_inspeccion integer not null," +
                "codigo varchar(10)," +
                "descripcion text," +
                "estado tinyint," +
                "imei text," +
                "id_servicio integer,"+
                "primary key(id,id_inspeccion,id_servicio)" +
                ")");
        db.execSQL("create table inspeccion_funcionamiento(" +
                "id integer not null," +
                "observaciones text," +
                "frecuencia_de_uso text," +
                "id_servicio integer," +
                "imei text," +
                "primary key(id,id_servicio)" +
                ")");
        db.execSQL("create table detalle_inspeccion_funcionamiento(" +
                "id integer not null," +
                "id_inspeccion integer," +
                "criterio_evaluacion text," +
                "estado tinyint," +
                "imei text," +
                "id_servicio integer," +
                "primary key(id,id_inspeccion,id_servicio)" +
                ")");
        db.execSQL("create table limpieza(" +
                "id integer primary key not null," +
                "observaciones text," +
                "cantidad_aves_vacunadas text," +
                "id_servicio integer" +
                ")");
        db.execSQL("create table detalle_limpieza(" +
                "id integer not null," +
                "id_inspeccion integer," +
                "criterio_evaluacion text," +
                "estado tinyint," +
                "primary key(id,id_inspeccion)" +
                ")");
        db.execSQL("create table empresa(" +
                "id integer primary key not null," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("create table granja (" +
                "id integer not null," +
                "id_empresa integer," +
                "nombre text,"+
                "zona text," +
                "primary key(id,id_empresa)" +
                ")");
        db.execSQL("create table galpon(" +
                "id integer not null," +
                "id_empresa integer," +
                "id_granja integer," +
                "codigo text,"+
                "cantidad_pollo integer," +
                "primary key(id,id_granja,id_empresa)" +
                ")");
        db.execSQL("create table sistema_integral(" +
                "id integer primary key not null," +
                "codigo varchar(20)," +
                "revision varchar(20)," +
                "edad integer," +
                "sexo varchar(1)," +
                "observaciones text," +
                "comentarios text," +
                "imagen1 text," +
                "imagen2 text," +
                "imagen3 text," +
                "imagen4 text," +
                "imagen5 text," +
                "fecha text," +
                "nro_pollos integer," +
                "id_galpon integer," +
                "id_granja integer," +
                "id_empresa integer," +
                "imei text,"+
                "firma_invetsa text," +
                "firma_empresa text," +
                "id_tecnico integer," +
                "imagen_jefe text" +
                ")");
        db.execSQL("create table puntuacion(" +
                "id integer  not null," +
                "nombre varchar(50)," +
                "id_sistema integer," +
                "imei text, " +
                "primary key(id,id_sistema)" +
                ")");
        db.execSQL("create table detalle_puntuacion(" +
                "id integer not null," +
                "nombre varchar(50)," +
                "estado int," +
                "id_puntuacion integer," +
                "id_sistema integer," +
                "imei text," +
                "primary key(id,id_puntuacion,id_sistema)" +
                ")");
        db.execSQL("create table peso(" +
                "id integer not null," +
                "peso_corporal decimal(2,2)," +
                "peso_bursa decimal(2,2)," +
                "peso_brazo decimal(2,2)," +
                "peso_timo decimal(2,2)," +
                "peso_higado decimal(2,2)," +
                "indice_bursal decimal(4,2)," +
                "indice_timico decimal(4,2)," +
                "indice_hepatico decimal(4,2)," +
                "bursometro decimal(4,2)," +
                "id_sistema integer," +
                "imei text, " +
                "primary key(id,id_sistema)" +
                ")");
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
                "id_tecnico integer," +
                "imagen_jefe text," +
                "responsable_invetsa text," +
                "responsable_incubadora text," +
                "total_vc decimal(10,2)," +
                "puntaje_total decimal(10,2)"+
                ")");
        db.execSQL("create table vacunador(" +
                "id integer primary key not null," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("create table accion(" +
                "id integer   not null," +
                "nombre varchar(50)," +
                "id_hoja_verificacion integer," +
                "imei text," +
                "primary key(id,id_hoja_verificacion)"+
                ")");
        db.execSQL("create table detalle_accion(" +
                "id integer  not null," +
                "esperado varchar(50)," +
                "encontrado varchar(50)," +
                "id_accion integer," +
                "id_hoja_verificacion integer,"+
                "imei text,"+
                "primary key(id,id_accion,id_hoja_verificacion)"+
                ")");

        db.execSQL("create table control_indice(" +
                "id integer not null," +
                "nro_pollos_vacunado integer default 0," +
                "puntaje integer default 0," +
                "nro_pollos_controlados integer default 0," +
                "nro_pollos_no_vacunados integer default 0," +
                "nro_heridos integer default 0," +
                "nro_mojados integer default 0," +
                "nro_mala_posicion integer default 0," +
                "nro_pollos_vacunados_correctamente integer default 0," +
                "indice_eficiencia integer default 0," +
                "id_hoja_verificacion integer," +
                "id_vacunador integer," +
                "imei text," +
                "primary key(id,id_vacunador,id_hoja_verificacion)"+
                ")");
        db.execSQL("create table mantenimiento_limpieza(" +
                "id integer not null," +
                "nro_maquina integer," +
                "irregularidades integer," +
                "id_hoja_verificacion integer," +
                "id_vacunador integer," +
                "imei text," +
                "primary key(id,id_vacunador,id_hoja_verificacion)"+
                ")");
        db.execSQL("create table manipulacion_dilucion(" +
                "id integer  not null," +
                "descripcion text," +
                "puntaje decimal(2,2)," +
                "id_hoja_verificacion integer," +
                "imei text" +
                ")");
        db.execSQL("create table viabilidad_celular(" +
                "id integer not null," +
                "antibiotico text," +
                "dosis text," +
                "tiempo text," +
                "vc decimal(10,2)," +
                "id_hoja_verificacion int not null," +
                "imei text," +
                "primary key(id,id_hoja_verificacion)" +
                ")");
        db.execSQL("create table linea_genetica(" +
                "id integer not null," +
                "descripcion text," +
                "cobb text," +
                "ross text," +
                "hybro text," +
                "id_hoja_verificacion integer not null," +
                "imei text," +
                "primary key(id,id_hoja_verificacion)" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists compania");
        db.execSQL("create table compania(" +
                "id integer primary key not null," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("drop table if exists tecnico");
        db.execSQL("create table tecnico(" +
                "id integer primary key not null," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("drop table if exists maquina");
        db.execSQL("create table maquina(" +
                "id integer primary key not null, " +
                "codigo varchar(15)," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("drop table if exists servicio_mantenimiento");
        db.execSQL("create table servicio_mantenimiento(" +
                "id integer primary key not null," +
                "fecha text ," +
                "hora_ingreso text," +
                "hora_salidas text," +
                "codigo varchar(20)," +
                "revision varchar(20)," +
                "firma_jefe_planta text," +
                "firma_invetsa text," +
                "id_maquina integer," +
                "id_tecnico integer," +
                "id_compania integer," +
                "imei text," +
                "formulario text," +
                "imagen_jefe text"+
                ")");
        db.execSQL("drop table if exists inspeccion_visual");
        db.execSQL("create table inspeccion_visual(" +
                "id integer not null," +
                "observaciones text," +
                "piesas_cambiadas text," +
                "id_servicio integer," +
                "imei text," +
                "primary key(id,id_servicio)" +
                ")");
        db.execSQL("drop table if exists inspeccion_visual");
        db.execSQL("create table detalle_inspeccion_visual(" +
                "id integer not null," +
                "id_inspeccion integer not null," +
                "codigo varchar(10)," +
                "descripcion text," +
                "estado tinyint," +
                "imei text," +
                "id_servicio integer,"+
                "primary key(id,id_inspeccion,id_servicio)" +
                ")");
        db.execSQL("drop table if exists inspeccion_funcionamiento");
        db.execSQL("create table inspeccion_funcionamiento(" +
                "id integer not null," +
                "observaciones text," +
                "frecuencia_de_uso text," +
                "id_servicio integer," +
                "imei text," +
                "primary key(id,id_servicio)" +
                ")");
        db.execSQL("drop table if exists detalle_inspeccion_funcionamiento");
        db.execSQL("create table detalle_inspeccion_funcionamiento(" +
                "id integer not null," +
                "id_inspeccion integer," +
                "criterio_evaluacion text," +
                "estado tinyint," +
                "imei text," +
                "id_servicio integer," +
                "primary key(id,id_inspeccion,id_servicio)" +
                ")");
        db.execSQL("drop table if exists limpieza");
        db.execSQL("create table limpieza(" +
                "id integer primary key not null," +
                "observaciones text," +
                "cantidad_aves_vacunadas text," +
                "id_servicio integer" +
                ")");
        db.execSQL("drop table if exists detalle_limpieza");
        db.execSQL("create table detalle_limpieza(" +
                "id integer not null," +
                "id_inspeccion integer," +
                "criterio_evaluacion text," +
                "estado tinyint," +
                "primary key(id,id_inspeccion)" +
                ")");
        db.execSQL("drop table if exists empresa");
        db.execSQL("create table empresa(" +
                "id integer primary key not null," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("drop table if exists granja");
        db.execSQL("create table granja (" +
                "id integer not null," +
                "id_empresa integer," +
                "nombre text,"+
                "zona text," +
                "primary key(id,id_empresa)" +
                ")");
        db.execSQL("drop table if exists galpon");
        db.execSQL("create table galpon(" +
                "id integer not null," +
                "id_empresa integer," +
                "id_granja integer," +
                "codigo text,"+
                "cantidad_pollo integer," +
                "primary key(id,id_granja,id_empresa)" +
                ")");
        db.execSQL("drop table if exists sistema_integral");
        db.execSQL("create table sistema_integral(" +
                "id integer primary key not null," +
                "codigo varchar(20)," +
                "revision varchar(20)," +
                "edad integer," +
                "sexo varchar(1)," +
                "observaciones text," +
                "comentarios text," +
                "imagen1 text," +
                "imagen2 text," +
                "imagen3 text," +
                "imagen4 text," +
                "imagen5 text," +
                "fecha text," +
                "nro_pollos integer," +
                "id_galpon integer," +
                "id_granja integer," +
                "id_empresa integer," +
                "imei text,"+
                "firma_invetsa text," +
                "firma_empresa text," +
                "id_tecnico integer," +
                "imagen_jefe text" +
                ")");
        db.execSQL("drop table if exists puntuacion");
        db.execSQL("create table puntuacion(" +
                "id integer  not null," +
                "nombre varchar(50)," +
                "id_sistema integer," +
                "imei text, " +
                "primary key(id,id_sistema)" +
                ")");
        db.execSQL("drop table if detalle_puntuacion");
        db.execSQL("create table detalle_puntuacion(" +
                "id integer not null," +
                "nombre varchar(50)," +
                "estado int," +
                "id_puntuacion integer," +
                "id_sistema integer," +
                "imei text," +
                "primary key(id,id_puntuacion,id_sistema)" +
                ")");
        db.execSQL("drop table if peso");
        db.execSQL("create table peso(" +
                "id integer not null," +
                "peso_corporal decimal(2,2)," +
                "peso_bursa decimal(2,2)," +
                "peso_brazo decimal(2,2)," +
                "peso_timo decimal(2,2)," +
                "peso_higado decimal(2,2)," +
                "indice_bursal decimal(4,2)," +
                "indice_timico decimal(4,2)," +
                "indice_hepatico decimal(4,2)," +
                "bursometro decimal(4,2)," +
                "id_sistema integer," +
                "imei text, " +
                "primary key(id,id_sistema)" +
                ")");
        db.execSQL("drop table if hoja_verificacion");
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
                "id_tecnico integer," +
                "imagen_jefe text," +
                "responsable_invetsa text," +
                "responsable_incubadora text," +
                "total_vc decimal(10,2)," +
                "puntaje_total decimal(10,2)"+
                ")");
        db.execSQL("drop table if vacunador");
        db.execSQL("create table vacunador(" +
                "id integer primary key not null," +
                "nombre varchar(50)" +
                ")");
        db.execSQL("drop table if accion");
        db.execSQL("create table accion(" +
                "id integer  not null," +
                "nombre varchar(50)," +
                "id_hoja_verificacion integer," +
                "imei text,"+
                "primary key(id,id_hoja_verificacion)"+
                ")");
        db.execSQL("drop table if detalle_accion");
        db.execSQL("create table detalle_accion(" +
                "id integer  not null," +
                "esperado varchar(50)," +
                "encontrado varchar(50)," +
                "id_accion integer," +
                "id_hoja_verificacion integer,"+
                "imei text,"+
                "primary key(id,id_accion,id_hoja_verificacion)"+
                ")");
        db.execSQL("drop table if mantenimiento_limpieza");
        db.execSQL("create table mantenimiento_limpieza(" +
                "id integer not null," +
                "nro_maquina integer," +
                "irregularidades integer," +
                "id_hoja_verificacion integer," +
                "id_vacunador integer," +
                "imei text," +
                "primary key(id,id_vacunador,id_hoja_verificacion)"+
                ")");
        db.execSQL("drop table if control_indice");
        db.execSQL("create table control_indice(" +
                "id integer not null," +
                "nro_pollos_vacunado integer default 0," +
                "puntaje integer default 0," +
                "nro_pollos_controlados integer default 0," +
                "nro_pollos_no_vacunados integer default 0," +
                "nro_heridos integer default 0," +
                "nro_mojados integer default 0," +
                "nro_mala_posicion integer default 0," +
                "nro_pollos_vacunados_correctamente integer default 0," +
                "indice_eficiencia integer default 0," +
                "id_hoja_verificacion integer," +
                "id_vacunador integer," +
                "imei text," +
                "primary key(id,id_vacunador,id_hoja_verificacion)"+
                ")");
        db.execSQL("drop table if manipulacion_dilucion");
        db.execSQL("create table manipulacion_dilucion(" +
                "id integer  not null," +
                "descripcion text," +
                "puntaje decimal(2,2)," +
                "id_hoja_verificacion integer," +
                "imei text" +
                ")");

        db.execSQL("drop table if viabilidad_celular");
        db.execSQL("create table viabilidad_celular(" +
                "id integer not null," +
                "antibiotico text," +
                "dosis text," +
                "tiempo text," +
                "vc decimal(10,2)," +
                "id_hoja_verificacion int not null," +
                "imei text," +
                "primary key(id,id_hoja_verificacion)" +
                ")");
        db.execSQL("drop table if linea_genetica");
        db.execSQL("create table linea_genetica(" +
                "id integer not null," +
                "descripcion text," +
                "cobb text," +
                "ross text," +
                "hybro text," +
                "id_hoja_verificacion integer not null," +
                "imei text," +
                "primary key(id,id_hoja_verificacion)" +
                ")");

    }
}
