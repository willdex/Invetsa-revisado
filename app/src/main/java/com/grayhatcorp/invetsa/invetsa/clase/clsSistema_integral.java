package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by HOME on 04/04/2017.
 */

public class clsSistema_integral {

    private int id;
    private String fecha;
    private int id_galpon;
    private int id_granja;
    private int id_empresa;
    private String nombre_granja;
    private String codigo_galpon;
    private String  nombre_empresa;
    public clsSistema_integral()
    {
        setId(0);
        setFecha("");
        setId_galpon(0);
        setId_granja(0);
        setId_empresa(0);
        setNombre_granja("");
        setCodigo_galpon("");
        setNombre_empresa("");
    }
    public clsSistema_integral(int id,String fecha,int id_empresa,int id_granja, int id_galpon, String nombre_granja,String codigo_galpon,String nombre_empresa)
    {
        this.setId(id);
        this.setFecha(fecha);
        this.setId_empresa(id_empresa);
        this.setId_granja(id_granja);
        this.setNombre_empresa(nombre_empresa);
        this.setNombre_granja(nombre_granja);
        this.setCodigo_galpon(codigo_galpon);
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
}
