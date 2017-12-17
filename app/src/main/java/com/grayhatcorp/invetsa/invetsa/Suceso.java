package com.grayhatcorp.invetsa.invetsa;

/**
 * Created by HOME on 18/01/2017.
 */

public class Suceso {
    private String suceso;
    private String mensaje;
    public  Suceso()
    {
        suceso="0";
        mensaje="Error: al conectar con el servidor.";
    }
    public Suceso (String suceso,String mensaje)
    {
        this.suceso=suceso;
        this.mensaje=mensaje;
    }

    public String getSuceso() {
        return suceso;
    }

    public String getMensaje() {
        return mensaje;
    }
}