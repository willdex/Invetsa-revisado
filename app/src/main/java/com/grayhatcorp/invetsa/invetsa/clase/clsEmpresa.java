package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by HOME on 19/01/2017.
 */

public class clsEmpresa {
    private int id;
    private String nombre;

    //construtor
    public clsEmpresa() {
        this.setId(0);
        this.setNombre("");
    }

    public clsEmpresa(int id, String nombre) {
        this.setId(id);
        this.setNombre(nombre);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}