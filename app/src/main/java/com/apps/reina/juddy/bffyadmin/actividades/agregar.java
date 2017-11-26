package com.apps.reina.juddy.bffyadmin.actividades;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.ingrediente;
import com.apps.reina.juddy.bffyadmin.dialog.addIngrediente;

import java.util.ArrayList;
import java.util.List;

public class agregar extends AppCompatActivity implements addIngrediente.ingredienteListener{

    Spinner spn_categoria1,spn_categoria2,spn_sub_categoria1,spn_sub_categoria2,spn_sub_categoria3,spn_sub_categoria4;
    Spinner spn_producto,spn_empaque,spn_unidad,spn_conservantes,spn_sabor,spn_apto_para;
    EditText et_nombre,et_nombre_producto,et_calorias,et_azucar,et_sodio,et_fabricante,et_gramaje,et_lineAtencion;
    TextView tv_ingredientes;
    Button btn_guardar;
    LinearLayout rl_fragment;

    List<ingrediente> lista_ingredientes;
    int selCat1=0,selCat2=0,selSubCat1=0,selSubCat2=0,selSubCat3=0,selSubCat4=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add);
        //Establece la vista
        setContentView(R.layout.activity_agregar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spn_categoria1=findViewById(R.id.spnADD_catgeoria_1);
        spn_categoria2=findViewById(R.id.spnADD_catgeoria_2);
        spn_sub_categoria1=findViewById(R.id.spnADD_subcatgeoria_1);
        spn_sub_categoria2=findViewById(R.id.spnADD_subcatgeoria_2);
        spn_sub_categoria3=findViewById(R.id.spnADD_subcatgeoria_3);
        spn_sub_categoria4=findViewById(R.id.spnADD_subcatgeoria_4);

        et_nombre=findViewById(R.id.etADD_nombre);
        btn_guardar=findViewById(R.id.btnADD_guardar);
        rl_fragment=findViewById(R.id.ll_agregar);

        et_calorias=findViewById(R.id.et_calorias);
        et_azucar=findViewById(R.id.et_azucar);
        et_sodio=findViewById(R.id.et_sodio);

        et_nombre_producto=findViewById(R.id.et_item_0);
        tv_ingredientes=findViewById(R.id.tv_item_1);
        et_fabricante=findViewById(R.id.et_item_2);
        et_gramaje=findViewById(R.id.et_item_3);
        et_lineAtencion=findViewById(R.id.et_item_4);
        spn_producto=findViewById(R.id.spn_item_5);
        spn_empaque=findViewById(R.id.spn_item_6);
        spn_unidad=findViewById(R.id.spn_item_3_1);
        spn_conservantes=findViewById(R.id.spn_item_7);
        spn_sabor=findViewById(R.id.spn_item_8);
        spn_apto_para=findViewById(R.id.spn_item_9);

        lista_ingredientes=new ArrayList<>();

        //SELECCION CATEGORIA 1
        spn_categoria1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_nombre.setVisibility(View.GONE);
                et_nombre.setText("");
                rl_fragment.setVisibility(View.GONE);
                btn_guardar.setVisibility(View.GONE);
                limpiar_interfaz();

                spn_sub_categoria1.setVisibility(View.GONE);
                spn_sub_categoria2.setVisibility(View.GONE);
                spn_sub_categoria3.setVisibility(View.GONE);
                spn_sub_categoria4.setVisibility(View.GONE);
                //Establece la seleccion por defecto en todos los spinner
                spn_categoria2.setSelection(0);
                spn_sub_categoria1.setSelection(0);
                spn_sub_categoria2.setSelection(0);
                spn_sub_categoria3.setSelection(0);
                spn_sub_categoria4.setSelection(0);
                //Reinicia la seleccion a 0
                selCat2=0;
                selSubCat1=0;
                selSubCat2=0;
                selSubCat3=0;
                selSubCat4=0;
                if(position==0){//Si se escoge la opcion no valida
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
                rl_fragment.setVisibility(View.GONE);
                btn_guardar.setVisibility(View.GONE);
                limpiar_interfaz();

                //Oculta todos los demas spinner de sub-categoria
                spn_sub_categoria2.setVisibility(View.GONE);
                spn_sub_categoria3.setVisibility(View.GONE);
                spn_sub_categoria4.setVisibility(View.GONE);
                //Establece la seleccion por defecto en demas spinner de sub-categoria
                spn_sub_categoria2.setSelection(0);
                spn_sub_categoria3.setSelection(0);
                spn_sub_categoria4.setSelection(0);
                //Reinicia la seleccion a 0 de todos los spinnes de sub-categoria
                selSubCat1=0;
                selSubCat2=0;
                selSubCat3=0;
                selSubCat4=0;

                if(position==0){//Si se escoge la opcion no valida
                    spn_sub_categoria1.setVisibility(View.GONE);
                }else{
                    selCat2=position;
                    //Se garantiza que se escoja una opcion que tenga sub-categoria
                    if(position==1){//ALIMENTO
                        spn_sub_categoria1.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> adapter;
                        switch (selCat1){//DEPENDE DE LO QUE SE ESCOGIO EN LA CATEGORIA 1 SE AGREGA EL ARRAY AL SPINNER DE SUB-CATEGARIA 1
                            case 1:
                                //Se crea un ArrayAdapter usando el array ALIMENTO_LIQUIDOS y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.alimento_liquidos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria1.setAdapter(adapter);
                                break;
                            case 2:
                                //Se crea un ArrayAdapter usando el array ALIMENTO_SECOS y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.alimento_secos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria1.setAdapter(adapter);
                                break;
                            case 3:
                                //Se crea un ArrayAdapter usando el array ALIMENTO_MIXTOS y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.alimento_mixtos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria1.setAdapter(adapter);
                                break;
                            default:
                                break;

                        }

                    }else if(position==3){//SUPLEMENTO
                        spn_sub_categoria1.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> adapter;
                        //Se crea un ArrayAdapter usando el array SUPLEMENTOS y el spinner por defecto
                        adapter = ArrayAdapter.createFromResource(agregar.this, R.array.suplemento, android.R.layout.simple_spinner_item);
                        //Se especifica el diseño a utilizar en los items del Spinner
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Aplica el adaptador al spinner
                        spn_sub_categoria1.setAdapter(adapter);

                    }else if(position==2){//COMPLEMENTO
                        et_nombre.setVisibility(View.VISIBLE);
                        rl_fragment.setVisibility(View.GONE);
                        btn_guardar.setVisibility(View.GONE);

                        spn_sub_categoria1.setVisibility(View.GONE);
                        spn_sub_categoria2.setVisibility(View.GONE);
                        spn_sub_categoria3.setVisibility(View.GONE);
                        spn_sub_categoria4.setVisibility(View.GONE);
                        //Establece la seleccion por defecto en demas spinner de sub-categoria
                        spn_sub_categoria1.setSelection(0);
                        spn_sub_categoria2.setSelection(0);
                        spn_sub_categoria3.setSelection(0);
                        spn_sub_categoria4.setSelection(0);
                        //Reinicia la seleccion a 0 de todos los spinnes de sub-categoria
                        selSubCat1=0;
                        selSubCat2=0;
                        selSubCat3=0;
                        selSubCat4=0;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //SELECCION SUB-CATEGORIA 1
        spn_sub_categoria1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_nombre.setVisibility(View.GONE);
                et_nombre.setText("");
                rl_fragment.setVisibility(View.GONE);
                btn_guardar.setVisibility(View.GONE);
                limpiar_interfaz();

                //Oculta todos los demas spinner de sub-categoria
                spn_sub_categoria3.setVisibility(View.GONE);
                spn_sub_categoria4.setVisibility(View.GONE);
                //Establece la seleccion por defecto en los demas spinner de sub-categoria
                spn_sub_categoria2.setSelection(0);
                spn_sub_categoria3.setSelection(0);
                spn_sub_categoria4.setSelection(0);
                //Reinicia la seleccion a 0
                selSubCat2=0;
                selSubCat3=0;
                selSubCat4=0;
                if(position==0){//Si se escoge la opcion no valida

                    spn_sub_categoria2.setVisibility(View.GONE);

                }else{
                    selSubCat1=position;
                    if(selCat2==3){//SUPLEMENTOS
                        et_nombre.setVisibility(View.VISIBLE);

                        spn_sub_categoria2.setVisibility(View.GONE);
                        spn_sub_categoria3.setVisibility(View.GONE);
                        spn_sub_categoria4.setVisibility(View.GONE);
                        //Establece la seleccion por defecto en los demas spinner de sub-categoria
                        spn_sub_categoria2.setSelection(0);
                        spn_sub_categoria3.setSelection(0);
                        spn_sub_categoria4.setSelection(0);
                        //Reinicia la seleccion a 0
                        selSubCat2=0;
                        selSubCat3=0;
                        selSubCat4=0;
                    }else if(selCat1==1 && selCat2==1){//LIQUIDOS-ALIMENTO
                        if(position==3 || position==4){//LACTEOS o REFRESCOS
                            spn_sub_categoria2.setVisibility(View.VISIBLE);
                            ArrayAdapter<CharSequence> adapter;
                            //Se crea un ArrayAdapter usando el array LIQUIDOS y el spinner por defecto
                            adapter = ArrayAdapter.createFromResource(agregar.this, R.array.liquidos_sub1, android.R.layout.simple_spinner_item);
                            //Se especifica el diseño a utilizar en los items del Spinner
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Aplica el adaptador al spinner
                            spn_sub_categoria2.setAdapter(adapter);
                        }else if(position==2){
                            spn_sub_categoria2.setVisibility(View.VISIBLE);
                            ArrayAdapter<CharSequence> adapter;
                            //Se crea un ArrayAdapter usando el array JUGOS y el spinner por defecto
                            adapter = ArrayAdapter.createFromResource(agregar.this, R.array.jugos, android.R.layout.simple_spinner_item);
                            //Se especifica el diseño a utilizar en los items del Spinner
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Aplica el adaptador al spinner
                            spn_sub_categoria2.setAdapter(adapter);
                        }else{
                            et_nombre.setVisibility(View.VISIBLE);

                            spn_sub_categoria2.setVisibility(View.GONE);
                            spn_sub_categoria3.setVisibility(View.GONE);
                            spn_sub_categoria4.setVisibility(View.GONE);
                            //Establece la seleccion por defecto en los demas spinner de sub-categoria
                            spn_sub_categoria2.setSelection(0);
                            spn_sub_categoria3.setSelection(0);
                            spn_sub_categoria4.setSelection(0);
                            //Reinicia la seleccion a 0
                            selSubCat2=0;
                            selSubCat3=0;
                            selSubCat4=0;
                        }
                    }else if(selCat1==2 && selCat2==1){//SECOS-ALIMENTO
                        ArrayAdapter<CharSequence> adapter;
                        switch (position){
                            case 1://condimento_especies
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array condimentos y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.condimento, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 2://endulzante
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array endulzante y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.endulzante, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 4://granos
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array granos y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.granos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 9://snack
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array snack y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.snack, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            default:
                                et_nombre.setVisibility(View.VISIBLE);

                                spn_sub_categoria2.setVisibility(View.GONE);
                                spn_sub_categoria3.setVisibility(View.GONE);
                                spn_sub_categoria4.setVisibility(View.GONE);
                                //Establece la seleccion por defecto en los demas spinner de sub-categoria
                                spn_sub_categoria2.setSelection(0);
                                spn_sub_categoria3.setSelection(0);
                                spn_sub_categoria4.setSelection(0);
                                //Reinicia la seleccion a 0
                                selSubCat2=0;
                                selSubCat3=0;
                                selSubCat4=0;
                                break;
                        }
                    }else if(selCat1==3 && selCat2==1){//MIXTOS-ALIMENTO
                        ArrayAdapter<CharSequence> adapter;
                        switch (position){
                            case 1:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array carnes y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.carnes, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 2:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array embutidos y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.embutidos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 3:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array fruta y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.secos_sub1, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 4:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array queso y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.quesos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 6:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array vegetable y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.secos_sub1, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 7:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                //Se crea un ArrayAdapter usando el array OTROS y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.secos_sub2, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            default:
                                et_nombre.setVisibility(View.VISIBLE);

                                spn_sub_categoria2.setVisibility(View.GONE);
                                spn_sub_categoria3.setVisibility(View.GONE);
                                spn_sub_categoria4.setVisibility(View.GONE);
                                //Establece la seleccion por defecto en los demas spinner de sub-categoria
                                spn_sub_categoria2.setSelection(0);
                                spn_sub_categoria3.setSelection(0);
                                spn_sub_categoria4.setSelection(0);
                                //Reinicia la seleccion a 0
                                selSubCat2=0;
                                selSubCat3=0;
                                selSubCat4=0;
                                break;
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //SELECCION SUB-CATEGORIA 2
        spn_sub_categoria2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_nombre.setVisibility(View.GONE);
                et_nombre.setText("");
                rl_fragment.setVisibility(View.GONE);
                btn_guardar.setVisibility(View.GONE);
                limpiar_interfaz();

                //Oculta todos los spinner
                spn_sub_categoria4.setVisibility(View.GONE);
                //Establece la seleccion por defecto en todos los spinner
                spn_sub_categoria3.setSelection(0);
                spn_sub_categoria4.setSelection(0);
                //Reinicia la seleccion a 0
                selSubCat3=0;
                selSubCat4=0;
                if(position==0){//Si se escoge la opcion no valida
                    //Oculta spinner sub-categoria 3
                    spn_sub_categoria3.setVisibility(View.GONE);
                }else{
                    selSubCat2=position;

                    //DEPENDE DE LA SELECCION SE PUEDE O NO ACTIVAR LA SIGUIENTE SUB CATEGORIA
                    if(selCat1==1 && selCat2==1){//LIQUIDOS-ALIMENTO
                        if(selSubCat1==3){//LACTEOS
                            spn_sub_categoria3.setVisibility(View.VISIBLE);
                            ArrayAdapter<CharSequence> adapter;
                            //Se crea un ArrayAdapter usando el array LACTEOS y el spinner por defecto
                            adapter = ArrayAdapter.createFromResource(agregar.this, R.array.lacteos, android.R.layout.simple_spinner_item);
                            //Se especifica el diseño a utilizar en los items del Spinner
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Aplica el adaptador al spinner
                            spn_sub_categoria3.setAdapter(adapter);
                        }else{
                            et_nombre.setVisibility(View.VISIBLE);

                            spn_sub_categoria3.setVisibility(View.GONE);
                            spn_sub_categoria4.setVisibility(View.GONE);
                            //Establece la seleccion por defecto en los demas spinner de sub-categoria
                            spn_sub_categoria3.setSelection(0);
                            spn_sub_categoria4.setSelection(0);
                            //Reinicia la seleccion a 0
                            selSubCat3=0;
                            selSubCat4=0;
                        }
                    }



                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //SELECCION SUB-CATEGORIA 3
        spn_sub_categoria3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_nombre.setVisibility(View.GONE);
                et_nombre.setText("");
                rl_fragment.setVisibility(View.GONE);
                btn_guardar.setVisibility(View.GONE);
                limpiar_interfaz();

                //Establece la seleccion por defecto en todos los spinner
                spn_sub_categoria4.setSelection(0);
                //Reinicia la seleccion a 0
                selSubCat4=0;
                if(position==0){//Si se escoge la opcion no valida
                    //Oculta todos los spinner
                    spn_sub_categoria4.setVisibility(View.GONE);
                }else{
                    selSubCat3=position;
                    //DEPENDE DE LA SELECCION SE PUEDE O NO ACTIVAR LA SIGUIENTE SUB CATEGORIA
                    if(selCat1==1 && selCat2==1){//LIQUIDOS-ALIMENTO
                        if(selSubCat1==3){//LACTEOS
                            if(selSubCat3==2 || selSubCat3==3){//descremada o semidescremada
                                spn_sub_categoria4.setVisibility(View.VISIBLE);
                                ArrayAdapter<CharSequence> adapter;
                                //Se crea un ArrayAdapter usando el array LACTEOS_sub1 y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.lacteos_sub1, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria4.setAdapter(adapter);
                            }
                        }else{
                            et_nombre.setVisibility(View.VISIBLE);
                            spn_sub_categoria4.setVisibility(View.GONE);
                            //Establece la seleccion por defecto en los demas spinner de sub-categoria
                            spn_sub_categoria4.setSelection(0);
                            //Reinicia la seleccion a 0
                            selSubCat4=0;
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //SELECCION SUB-CATEGORIA 4
        spn_sub_categoria4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_nombre.setText("");
                rl_fragment.setVisibility(View.GONE);
                btn_guardar.setVisibility(View.GONE);
                limpiar_interfaz();

                if(position!=0){//Garantiza que se escoja una opcion valida
                    selSubCat4=position;
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
                btn_guardar.setVisibility(View.VISIBLE);
                rl_fragment.setVisibility(View.VISIBLE);

            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_ingredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new addIngrediente();

                ArrayList<String> ingrs=new ArrayList<>();
                ingrs.clear();
                for(int i = 0; i<lista_ingredientes.size(); i++){
                    ingrs.add(lista_ingredientes.get(i).getNombre());
                }
                if(ingrs.isEmpty()){
                    ingrs.add("none");
                }
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("lista",ingrs);
                dialog.setArguments(bundle);

                dialog.show(getSupportFragmentManager(), getResources().getString(R.string.item_1));
            }
        });

    }

    void limpiar_interfaz(){
        et_calorias.setText("");
        et_azucar.setText("");
        et_sodio.setText("");

        et_nombre_producto.setText("");
        tv_ingredientes.setText("");
        et_fabricante.setText("");
        et_gramaje.setText("");
        et_lineAtencion.setText("");
        spn_producto.setSelection(0);
        spn_empaque.setSelection(0);
        spn_unidad.setSelection(0);
        spn_conservantes.setSelection(0);
        spn_sabor.setSelection(0);
        spn_apto_para.setSelection(0);

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

    @Override
    public void on_addIngrediente_Positive(DialogFragment dialog, List<ingrediente> lst) {
        //ACTUALIZAR LISTA DE INGREDIENTES
        lista_ingredientes.clear();
        lista_ingredientes=lst;

        //SE CARGA EL STRING QUE MUESTRA LOS INGREDIENTES
        StringBuilder auxiliarBuilder = new StringBuilder();
        for(int i = 0; i<lista_ingredientes.size()-1; i++){
            auxiliarBuilder.append(lista_ingredientes.get(i).getNombre()).append(", ");
        }
        String auxiliar = auxiliarBuilder.toString();
        auxiliar=auxiliar+lista_ingredientes.get(lst.size()-1).getNombre();

        tv_ingredientes.setText(auxiliar);

        dialog.dismiss();
    }

    @Override
    public void on_addIngrediente_Negative(DialogFragment dialog) {
        dialog.dismiss();
    }
}
