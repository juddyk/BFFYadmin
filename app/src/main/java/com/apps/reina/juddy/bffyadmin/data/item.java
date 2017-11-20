package com.apps.reina.juddy.bffyadmin.data;

/**
 * Created by juddy on 20/11/17.
 */

public class item {
    private long id;
    private tabla_general informacion;
    /*
        ingredientes
     */
    private String fabricante;
    private long gramaje;
    private long unidad_gramaje;
    private String linea_atencion;
    private String nacional;
    private String empaque;
    private String conservantes;
    private String sabor;
    private String apto_para;

    public item() {
        this.informacion = new tabla_general();
        this.fabricante = "none";
        this.gramaje = 0;
        this.unidad_gramaje = 1;
        this.linea_atencion = "none";
        this.nacional = "none";
        this.empaque = "none";
        this.sabor="none";
        this.conservantes="none";
        this.apto_para="none";
        this.id=0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public tabla_general getInformacion() {
        return informacion;
    }

    public void setInformacion(tabla_general informacion) {
        this.informacion = informacion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public long getGramaje() {
        return gramaje;
    }

    public void setGramaje(long gramaje) {
        this.gramaje = gramaje;
    }

    public long getUnidad_gramaje() {
        return unidad_gramaje;
    }

    public void setUnidad_gramaje(long unidad_gramaje) {
        this.unidad_gramaje = unidad_gramaje;
    }

    public String getLinea_atencion() {
        return linea_atencion;
    }

    public void setLinea_atencion(String linea_atencion) {
        this.linea_atencion = linea_atencion;
    }

    public String getNacional() {
        return nacional;
    }

    public void setNacional(String nacional) {
        this.nacional = nacional;
    }

    public String getEmpaque() {
        return empaque;
    }

    public void setEmpaque(String empaque) {
        this.empaque = empaque;
    }

    public String getConservantes() {
        return conservantes;
    }

    public void setConservantes(String conservantes) {
        this.conservantes = conservantes;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getApto_para() {
        return apto_para;
    }

    public void setApto_para(String apto_para) {
        this.apto_para = apto_para;
    }
}
