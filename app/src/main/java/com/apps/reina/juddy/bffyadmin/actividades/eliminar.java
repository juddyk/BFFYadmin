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


/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class eliminar extends AppCompatActivity {
    Spinner spn_categoria1,spn_categoria2;
    EditText et_nombre;
    Button btn_eliminar;
    LinearLayout rl_fragmet;

    int selCat1=0,selCat2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.delete);
        //Establece la vista
        setContentView(R.layout.activity_eliminar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spn_categoria1=findViewById(R.id.spnDEL_catgeoria_1);
        spn_categoria2=findViewById(R.id.spnDEL_catgeoria_2);
        et_nombre=findViewById(R.id.etDEL_nombre);
        btn_eliminar=findViewById(R.id.btnDEL_eliminar);
        rl_fragmet=findViewById(R.id.ll_eliminar);


        //SELECCION CATEGORIA 1
        spn_categoria1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_nombre.setVisibility(View.GONE);
                et_nombre.setText("");
                rl_fragmet.setVisibility(View.GONE);
                btn_eliminar.setVisibility(View.GONE);

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
                et_nombre.setVisibility(View.GONE);
                et_nombre.setText("");
                rl_fragmet.setVisibility(View.GONE);
                btn_eliminar.setVisibility(View.GONE);

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
                rl_fragmet.setVisibility(View.VISIBLE);
                btn_eliminar.setVisibility(View.VISIBLE);

            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
