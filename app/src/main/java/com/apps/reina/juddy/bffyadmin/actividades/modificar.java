package com.apps.reina.juddy.bffyadmin.actividades;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.apps.reina.juddy.bffyadmin.R;

public class modificar extends AppCompatActivity {
    Spinner spn_categoria1,spn_categoria2, spn_producto,spn_empaque,spn_unidad;
    EditText et_nombre,et_propiedad_1,et_propiedad_2,et_ingredientes,et_fabricante,et_gramaje,et_lineAtencion;
    Button btn_guardar;
    LinearLayout rl_fragment;
    int selCat1=0,selCat2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.edit);
        //Establece la vista
        setContentView(R.layout.activity_modificar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spn_categoria1=findViewById(R.id.spnEDT_catgeoria_1);
        spn_categoria2=findViewById(R.id.spnEDT_catgeoria_2);

        et_nombre=findViewById(R.id.etEDT_nombre);
        btn_guardar=findViewById(R.id.btnEDT_guardar);
        rl_fragment=findViewById(R.id.ll_modificar);

        et_propiedad_1=findViewById(R.id.et_propiedad_1);
        et_propiedad_2=findViewById(R.id.et_propiedad_2);

        et_ingredientes=findViewById(R.id.et_item_1);
        et_fabricante=findViewById(R.id.et_item_2);
        et_gramaje=findViewById(R.id.et_item_3);
        et_lineAtencion=findViewById(R.id.et_item_4);
        spn_producto=findViewById(R.id.spn_item_5);
        spn_empaque=findViewById(R.id.spn_item_6);
        spn_unidad=findViewById(R.id.spn_item_3_1);

        //SELECCION CATEGORIA 1
        spn_categoria1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                limpiar_interfaz();

                //Establece la seleccion por defecto en todos los spinner
                spn_categoria2.setSelection(0);
                //Reinicia la seleccion a 0
                selCat2=0;
                if(position==0){//Si se escoge la opcion no valida
                    //Oculta spinner categoria-2
                    spn_categoria2.setVisibility(View.GONE);
                }else{
                    selCat1=position;
                    spn_categoria2.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //SELECCION CATEGORIA 2
        spn_categoria2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                limpiar_interfaz();

                if(position!=0){//Garantiza que no se escoja la opcion valida
                    selCat2 = position;
                    et_nombre.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        et_nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                rl_fragment.setVisibility(View.VISIBLE);
                btn_guardar.setVisibility(View.VISIBLE);

            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    void limpiar_interfaz(){
        et_nombre.setVisibility(View.GONE);
        et_nombre.setText("");
        rl_fragment.setVisibility(View.GONE);
        btn_guardar.setVisibility(View.GONE);

        /*
        et_propiedad_1.setText("");
        et_propiedad_2.setText("");

        et_ingredientes.setText("");
        et_fabricante.setText("");
        et_gramaje.setText("");
        et_lineAtencion.setText("");
        spn_producto.setSelection(0);
        spn_empaque.setSelection(0);
        spn_unidad.setSelection(0);*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
