package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by HOME on 04/04/2017.
 */

public class clsDetalle_accion {
    private int id;
    private String esperado;
    private String encontrado;
    private int id_accion;
    private int id_hoja_verificacion;

    public clsDetalle_accion(int id,String esperado,String encontrado,int id_accion,int id_hoja_verificacion)
    {
        this.setId(id);
        this.setEsperado(esperado);
        this.setEncontrado(encontrado);
        this.setId_accion(id_accion);
        this.setId_hoja_verificacion(id_hoja_verificacion);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEsperado() {
        return esperado;
    }

    public void setEsperado(String esperado) {
        this.esperado = esperado;
    }

    public String getEncontrado() {
        return encontrado;
    }

    public void setEncontrado(String encontrado) {
        this.encontrado = encontrado;
    }

    public int getId_accion() {
        return id_accion;
    }

    public void setId_accion(int id_accion) {
        this.id_accion = id_accion;
    }

    public int getId_hoja_verificacion() {
        return id_hoja_verificacion;
    }

    public void setId_hoja_verificacion(int id_hoja_verificacion) {
        this.id_hoja_verificacion = id_hoja_verificacion;
    }
}
