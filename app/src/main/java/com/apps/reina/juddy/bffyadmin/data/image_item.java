package com.apps.reina.juddy.bffyadmin.data;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 1/12/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 */

public class image_item {
    private String producto;
    private String nutricion;
    private long id;

    public image_item(String producto, String nutricion) {
        this.producto = producto;
        this.nutricion = nutricion;
        this.id=0;
    }

    public image_item() {
        this.producto = "";
        this.nutricion = "";
        this.id=0;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getNutricion() {
        return nutricion;
    }

    public void setNutricion(String nutricion) {
        this.nutricion = nutricion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
