package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by HOME on 28/01/2017.
 */

public class clsGalpon {
    private int id;
    private int id_empresa;
    private int id_granja;
    private int cantidad_pollo;

    public clsGalpon()
    {
        this.setId(0);
        this.setId_empresa(0);
        this.setId_granja(0);
        this.setCantidad_pollo(0);
    }

    public clsGalpon(int id,int id_empresa,int id_granja,int cantidad_pollo)
    {
        this.setId(id);
        this.setId_empresa(id_empresa);
        this.setId_granja(id_granja);
        this.setCantidad_pollo(cantidad_pollo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_granja() {
        return id_granja;
    }

    public void setId_granja(int id_granja) {
        this.id_granja = id_granja;
    }

    public int getCantidad_pollo() {
        return cantidad_pollo;
    }

    public void setCantidad_pollo(int cantidad_pollo) {
        this.cantidad_pollo = cantidad_pollo;
    }
}