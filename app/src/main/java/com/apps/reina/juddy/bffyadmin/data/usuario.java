package com.apps.reina.juddy.bffyadmin.data;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class usuario {
    private long id;
    private String nombre;
    private String correo;
    private String celuar;
    //private String uid;

    public usuario() {
        this.id =0;
        this.nombre = "";
        this.correo = "";
        this.celuar = "";
        //this.uid = "";
    }

    public usuario(long id, String nombre, String correo, String celuar/*, String uid*/) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.celuar = celuar;
        //this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCeluar() {
        return celuar;
    }

    public void setCeluar(String celuar) {
        this.celuar = celuar;
    }
/*
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }*/
}
