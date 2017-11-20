package com.apps.reina.juddy.bffyadmin.data;

/**
 * Created by juddy on 20/11/17.
 */

public class ingrediente {
    private long id;
    private String nombre;

    public ingrediente() {
        this.nombre="none";
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
