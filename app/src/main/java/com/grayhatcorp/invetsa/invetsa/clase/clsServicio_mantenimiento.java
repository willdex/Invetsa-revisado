package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by HOME on 28/01/2017.
 */

public class clsServicio_mantenimiento {

    private int id;
    private String fecha;
    private String codigo;
    private String revision;
    private int id_maquina;
    private int id_compania;
    private String nombre_maquina;
    private String nombre_compania;



    public clsServicio_mantenimiento()
    {
        this.setId(0);
        this.setFecha("");
        this.setCodigo("");
        this.setRevision("");
        this.setId_maquina(0);
        this.setId_compania(0);
        this.setNombre_maquina("");
        this.setNombre_compania("");
    }

    public clsServicio_mantenimiento(int id,String fecha,String codigo,String revision,int id_maquina,int id_compania,String nombre_maquina,String nombre_compania )
    {
        this.setId(id);
        this.setFecha(fecha);
        this.setCodigo(codigo);
        this.setRevision(revision);
        this.setId_maquina(id_maquina);
        this.setId_compania(id_compania);
        this.setNombre_maquina(nombre_maquina);
        this.setNombre_compania(nombre_compania);
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public int getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(int id_maquina) {
        this.id_maquina = id_maquina;
    }

    public int getId_compania() {
        return id_compania;
    }

    public void setId_compania(int id_compania) {
        this.id_compania = id_compania;
    }

    public String getNombre_maquina() {
        return nombre_maquina;
    }

    public void setNombre_maquina(String nombre_maquina) {
        this.nombre_maquina = nombre_maquina;
    }

    public String getNombre_compania() {
        return nombre_compania;
    }

    public void setNombre_compania(String nombre_compania) {
        this.nombre_compania = nombre_compania;
    }
}