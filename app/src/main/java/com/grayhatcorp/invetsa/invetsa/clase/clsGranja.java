package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by HOME on 28/01/2017.
 */

public class clsGranja {
    private int id;
    private int id_empresa;
    private String zona;

    public clsGranja()
    {
        this.setId(0);
        this.setId_empresa(0);
        this.setZona("");

    }

    public clsGranja(int id, int id_empresa, String zona)
    {
        this.setId(id);
        this.setId_empresa(id_empresa);
        this.setZona(zona);
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


    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}