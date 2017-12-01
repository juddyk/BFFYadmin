package com.apps.reina.juddy.bffyadmin.data;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class ingrediente {
    private long id;
    private String nombre;

    public ingrediente() {
        this.nombre="none";
        this.id=0;
    }

    public ingrediente(String name) {
        this.nombre=name;
        this.id=0;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
