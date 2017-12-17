package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by elisoft on 03-04-17.
 */

public class clsHoja_verificacion {

    private int id;
    private String fecha;
    private String hora_ingreso;
    private String hora_salida;
    private int id_galpon;
    private int id_granja;
    private int id_empresa;
    private String nombre_granja;
    private String codigo_galpon;
    private String  nombre_empresa;

    public clsHoja_verificacion()
    {
        setId(0);
        setFecha("");
        setHora_ingreso("");
        setHora_salida("");
        setId_galpon(0);
        setId_granja(0);
        setId_empresa(0);
        setNombre_granja("");
        setCodigo_galpon("");
        setNombre_empresa("");
    }

    public clsHoja_verificacion(int id,String fecha,String hora_ingreso,String hora_salida,int id_galpon,int id_granja,int id_empresa,String nombre_granja,String codigo_galpon,String nombre_empresa)
    {
        this.setId(id);
        this.setFecha(fecha);
        this.setHora_ingreso(hora_ingreso);
        this.setHora_salida(hora_salida);
        this.setId_galpon(id_galpon);
        this.setId_granja(id_granja);
        this.setId_empresa(id_empresa);
        this.setNombre_granja(nombre_granja);
        this.setCodigo_galpon(codigo_galpon);
        this.setNombre_empresa(nombre_empresa);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora_ingreso() {
        return hora_ingreso;
    }

    public void setHora_ingreso(String hora_ingreso) {
        this.hora_ingreso = hora_ingreso;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getId_galpon() {
        return id_galpon;
    }

    public void setId_galpon(int id_galpon) {
        this.id_galpon = id_galpon;
    }

    public int getId_granja() {
        return id_granja;
    }

    public void setId_granja(int id_granja) {
        this.id_granja = id_granja;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNombre_granja() {
        return nombre_granja;
    }

    public void setNombre_granja(String nombre_granja) {
        this.nombre_granja = nombre_granja;
    }

    public String getCodigo_galpon() {
        return codigo_galpon;
    }

    public void setCodigo_galpon(String codigo_galpon) {
        this.codigo_galpon = codigo_galpon;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

       /*
select h.id,h.fecha,h.hora_ingreso,h.hora_salida,h.id_galpon,h.id_granja,h.id_empresa,gr.nombre as 'nombre_granja',ga.codigo as 'codigo_galpon',e.nombre as 'nombre_empresa' from hoja_verificacion h,empresa e,granja gr,galpon ga where h.id_granja=gr.id and h.id_empresa=e.id and h.id_galpon=ga.id

int id_galpon,int id_granja,int id_empresa,String nombre_granja,String codigo_galpon,String nombre_empresa
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

    db.execSQL("create table empresa(" +
            "id integer primary key not null," +
            "nombre varchar(50)" +
            ")");
            */
}
