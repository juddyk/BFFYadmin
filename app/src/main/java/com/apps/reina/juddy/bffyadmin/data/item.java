package com.apps.reina.juddy.bffyadmin.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class item {
    private long id;
    private tabla_general informacion;
    private String nombre;
    private List<ingrediente> ingredientes;
    private String fabricante;
    private double gramaje;
    private String unidad_gramaje;
    private String linea_atencion;
    private String nacional;
    private String empaque;
    private String conservantes;
    private String sabor;
    private String apto_para;
    private image_item urlImage;


    public item() {
        this.informacion = new tabla_general();
        this.nombre="none";
        this.ingredientes=new ArrayList<>();
        this.fabricante = "none";
        this.gramaje = 0;
        this.unidad_gramaje = "";
        this.linea_atencion = "none";
        this.nacional = "none";
        this.empaque = "none";
        this.sabor="none";
        this.conservantes="none";
        this.apto_para="none";
        this.urlImage=new image_item();
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public double getGramaje() {
        return gramaje;
    }

    public void setGramaje(double gramaje) {
        this.gramaje = gramaje;
    }

    public String getUnidad_gramaje() {
        return unidad_gramaje;
    }

    public void setUnidad_gramaje(String unidad_gramaje) {
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

    public image_item getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(image_item urlImage) {
        this.urlImage = urlImage;
    }
}
