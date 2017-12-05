package com.apps.reina.juddy.bffyadmin.data;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 5/12/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 */

public class itemRow {
    private String title;
    private boolean checked;

    public itemRow() {
        this.title="";
        this.checked=false;
    }

    public itemRow(String title, boolean checked) {
        this.title = title;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
