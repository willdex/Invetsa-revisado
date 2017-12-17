package com.grayhatcorp.invetsa.invetsa.clase;

/**
 * Created by HOME on 16/02/2017.
 */

public class Dato {
    private int id;
    private int numero;
    private int valor;
    private int item;

    public Dato()
    {
        this.id=0;
        this.numero=0;
        this.valor=0;
        this.setItem(0);
    }
    public void set_mantenimiento(String dato,int valor)
    {
        this.id=get_id_String(dato);
        this.setItem(get_item_String(dato));
        this.numero=get_numero_String(dato);
        this.valor=valor;
    }
    public void set_control(String dato,int valor)
    {
        this.id=get_id_String(dato);
        this.setItem(0);
        this.numero=get_numero_2_String(dato);
        this.valor=valor;
    }

    private int get_item_String(String dato) {
        int numero=0;
        int fin=dato.length();
        int inicio=0;
        for (int i=0;i<dato.length();i++)
        {
            if(dato.charAt(i)==':')
            {
                inicio=i;
            }
        }
        numero=Integer.parseInt(dato.substring(inicio+1,fin));
        return numero;
    }


    private int get_numero_String(String dato) {
        int numero=0;
        int fin=dato.length();
        int inicio=0;
        for (int i=0;i<dato.length();i++)
        {
            if(dato.charAt(i)==32)
            {
                inicio=i;
            }
            if(dato.charAt(i)==':')
            {
                fin=i;
            }
        }
        numero=Integer.parseInt(dato.substring(inicio+1,fin));
        return numero;
    }

    private int get_numero_2_String(String dato) {
        int numero=0;
        int fin=dato.length();
        int inicio=0;
        for (int i=0;i<dato.length();i++)
        {
            if(dato.charAt(i)==32)
            {
                inicio=i;
            }
        }
        numero=Integer.parseInt(dato.substring(inicio+1,fin));
        return numero;
    }

    public int get_id_String(String dato)
    {
        int id=0;
        int fin=0;
        for (int i=0;i<dato.length();i++)
        {
            if(dato.charAt(i)==32)
            {
                fin=i;
            }
        }
        id=Integer.parseInt(dato.substring(0,fin));
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }
}
