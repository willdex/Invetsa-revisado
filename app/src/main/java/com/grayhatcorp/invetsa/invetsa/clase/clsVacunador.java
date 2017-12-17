package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by HOME on 19/01/2017.
 */

public class clsVacunador {
    private int id;
    private String nombre;
    private boolean estado;

    //construtor
    public clsVacunador() {
        this.setId(0);
        this.setNombre("");
        this.setEstado(false);
    }

    public clsVacunador(int id, String nombre,boolean estado) {
        this.setId(id);
        this.setNombre(nombre);
        this.setEstado(estado);
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}