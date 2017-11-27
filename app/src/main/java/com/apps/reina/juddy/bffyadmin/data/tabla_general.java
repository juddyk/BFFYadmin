package com.apps.reina.juddy.bffyadmin.data;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class tabla_general {

    private long calorias;
    private double azucar;
    private double sodio;


    public tabla_general(long calorias, double azucar, double sodio) {
        this.calorias = calorias;
        this.azucar = azucar;
        this.sodio = sodio;
    }

    public tabla_general() {
        this.calorias = -1;
        this.azucar = -1;
        this.sodio = -1;
    }

    public long getCalorias() {
        return calorias;
    }

    public void setCalorias(long calorias) {
        this.calorias = calorias;
    }

    public double getAzucar() {
        return azucar;
    }

    public void setAzucar(double azucar) {
        this.azucar = azucar;
    }

    public double getSodio() {
        return sodio;
    }

    public void setSodio(double sodio) {
        this.sodio = sodio;
    }
}
