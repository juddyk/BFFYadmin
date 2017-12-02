package com.apps.reina.juddy.bffyadmin.actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.image_item;
import com.apps.reina.juddy.bffyadmin.data.ingrediente;
import com.apps.reina.juddy.bffyadmin.data.item;
import com.apps.reina.juddy.bffyadmin.data.tabla_general;
import com.apps.reina.juddy.bffyadmin.dialog.addIngrediente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class agregar extends AppCompatActivity implements addIngrediente.ingredienteListener{

    Spinner spn_categoria1,spn_categoria2,spn_sub_categoria1,spn_sub_categoria2,spn_sub_categoria3,spn_sub_categoria4;
    Spinner spn_producto,spn_empaque,spn_unidad,spn_conservantes,spn_sabor,spn_apto_para;
    EditText et_nombre,et_calorias,et_azucar,et_sodio,et_fabricante,et_gramaje,et_lineAtencion;
    TextView tv_ingredientes;
    Button btn_guardar,btn_UploadImg_pro,btn_UploadImg_nut;
    LinearLayout rl_fragment;
    ImageView iv_foto_pro,iv_foto_nut;

    int selCat1=0,selCat2=0,selSubCat1=0,selSubCat2=0,selSubCat3=0,selSubCat4=0;
    List<String> arrayCat1,arrayCat2,arraySubCat1,arraySubCat2,arraySubCat3,arraySubCat4;

    item itemAdd;
    tabla_general tgAdd;
    image_item images;
    List<ingrediente> lista_ingredientes;

    //ACTIVITY RESULT
    public static final int RC_PHOTO_PRO = 1;
    public static final int RC_PHOTO_NUT = 2;


    //DATABASE
    private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBase_Reference;
    private static final String TAG_ALIMENTOS="PRODUCTOS";
    //STORAGE
    private FirebaseStorage mStorage;
    private StorageReference mStorage_Reference;

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

        instanciarFIREBASE();
        instanciarOBJETOS_interfaz();
        instanciarOBJETOS();


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
                arraySubCat1=new ArrayList<>();
                arraySubCat2=new ArrayList<>();
                arraySubCat3=new ArrayList<>();
                arraySubCat4=new ArrayList<>();

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
                arraySubCat1=new ArrayList<>();
                arraySubCat2=new ArrayList<>();
                arraySubCat3=new ArrayList<>();
                arraySubCat4=new ArrayList<>();

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
                        arraySubCat1=new ArrayList<>();
                        arraySubCat2=new ArrayList<>();
                        arraySubCat3=new ArrayList<>();
                        arraySubCat4=new ArrayList<>();
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
                arraySubCat2=new ArrayList<>();
                arraySubCat3=new ArrayList<>();
                arraySubCat4=new ArrayList<>();
                if(position==0){//Si se escoge la opcion no valida
                    spn_sub_categoria2.setVisibility(View.GONE);
                }else{
                    selSubCat1=position;
                    if(selCat2==3){//SUPLEMENTOS
                        arraySubCat1=Arrays.asList(getResources().getStringArray(R.array.tag_suplemento));
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
                        arraySubCat2=new ArrayList<>();
                        arraySubCat3=new ArrayList<>();
                        arraySubCat4=new ArrayList<>();
                    }else if(selCat1==1 && selCat2==1){//LIQUIDOS-ALIMENTO
                        arraySubCat1=Arrays.asList(getResources().getStringArray(R.array.tag_alimento_liquidos));
                        if(position==3 || position==4){//LACTEOS o REFRESCOS
                            spn_sub_categoria2.setVisibility(View.VISIBLE);
                            arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_liquidos_sub1));
                            ArrayAdapter<CharSequence> adapter;
                            //Se crea un ArrayAdapter usando el array LIQUIDOS y el spinner por defecto
                            adapter = ArrayAdapter.createFromResource(agregar.this, R.array.liquidos_sub1, android.R.layout.simple_spinner_item);
                            //Se especifica el diseño a utilizar en los items del Spinner
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Aplica el adaptador al spinner
                            spn_sub_categoria2.setAdapter(adapter);
                        }else if(position==2){
                            spn_sub_categoria2.setVisibility(View.VISIBLE);
                            arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_jugos));
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
                            arraySubCat2=new ArrayList<>();
                            arraySubCat3=new ArrayList<>();
                            arraySubCat4=new ArrayList<>();
                        }
                    }else if(selCat1==2 && selCat2==1){//SECOS-ALIMENTO
                        ArrayAdapter<CharSequence> adapter;
                        arraySubCat1=Arrays.asList(getResources().getStringArray(R.array.tag_alimento_secos));
                        switch (position){
                            case 1://condimento_especies
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_condimento));
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
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_endulzante));
                                //Se crea un ArrayAdapter usando el array endulzante y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.endulzante, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 4://granos
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_granos));
                                //Se crea un ArrayAdapter usando el array granos y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.granos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 9://snack
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_snack));
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
                                arraySubCat2=new ArrayList<>();
                                arraySubCat3=new ArrayList<>();
                                arraySubCat4=new ArrayList<>();
                                break;
                        }
                    }else if(selCat1==3 && selCat2==1){//MIXTOS-ALIMENTO
                        arraySubCat1= Arrays.asList(getResources().getStringArray(R.array.tag_alimento_mixtos));
                        ArrayAdapter<CharSequence> adapter;
                        switch (position){
                            case 1:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_carnes));
                                //Se crea un ArrayAdapter usando el array carnes y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.carnes, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 2:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_embutidos));
                                //Se crea un ArrayAdapter usando el array embutidos y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.embutidos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 3:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_secos_sub1));
                                //Se crea un ArrayAdapter usando el array fruta y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.secos_sub1, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 4:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_quesos));
                                //Se crea un ArrayAdapter usando el array queso y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.quesos, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 6:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_secos_sub1));
                                //Se crea un ArrayAdapter usando el array vegetable y el spinner por defecto
                                adapter = ArrayAdapter.createFromResource(agregar.this, R.array.secos_sub1, android.R.layout.simple_spinner_item);
                                //Se especifica el diseño a utilizar en los items del Spinner
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Aplica el adaptador al spinner
                                spn_sub_categoria2.setAdapter(adapter);
                                break;
                            case 7:
                                spn_sub_categoria2.setVisibility(View.VISIBLE);
                                arraySubCat2=Arrays.asList(getResources().getStringArray(R.array.tag_secos_sub2));
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
                                arraySubCat2=new ArrayList<>();
                                arraySubCat3=new ArrayList<>();
                                arraySubCat4=new ArrayList<>();
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
                arraySubCat3=new ArrayList<>();
                arraySubCat4=new ArrayList<>();
                if(position==0){//Si se escoge la opcion no valida
                    //Oculta spinner sub-categoria 3
                    spn_sub_categoria3.setVisibility(View.GONE);
                }else{
                    selSubCat2=position;

                    //DEPENDE DE LA SELECCION SE PUEDE O NO ACTIVAR LA SIGUIENTE SUB CATEGORIA
                    if(selCat1==1 && selCat2==1){//LIQUIDOS-ALIMENTO
                        if(selSubCat1==3){//LACTEOS
                            spn_sub_categoria3.setVisibility(View.VISIBLE);
                            arraySubCat3=Arrays.asList(getResources().getStringArray(R.array.tag_lacteos));
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
                            arraySubCat3=new ArrayList<>();
                            arraySubCat4=new ArrayList<>();
                        }
                    }else{//MIXTO & SECOS -ALIMENTO
                        et_nombre.setVisibility(View.VISIBLE);

                        spn_sub_categoria3.setVisibility(View.GONE);
                        spn_sub_categoria4.setVisibility(View.GONE);
                        //Establece la seleccion por defecto en los demas spinner de sub-categoria
                        spn_sub_categoria3.setSelection(0);
                        spn_sub_categoria4.setSelection(0);
                        //Reinicia la seleccion a 0
                        selSubCat3=0;
                        selSubCat4=0;
                        arraySubCat3=new ArrayList<>();
                        arraySubCat4=new ArrayList<>();
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
                arraySubCat4=new ArrayList<>();
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
                                arraySubCat4=Arrays.asList(getResources().getStringArray(R.array.tag_lacteos_sub1));
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
                            arraySubCat4=new ArrayList<>();
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
                itemAdd.setNombre(s.toString());

            }
        });

        btn_UploadImg_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.photo_selec)), RC_PHOTO_PRO);
            }
        });

        btn_UploadImg_nut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.photo_selec)), RC_PHOTO_NUT);
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

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selCat1!=0 && selCat2!=0){
                    guardarItem();
                    guardarTablaGeneral();
                    DatabaseReference mRef;

                    lista_ingredientes.add(new ingrediente("none"));
                    lista_ingredientes.add(new ingrediente("none"));
                    lista_ingredientes.add(new ingrediente("none"));

                    itemAdd.setIngredientes(lista_ingredientes);
                    itemAdd.setInformacion(tgAdd);
                    itemAdd.setUrlImage(images);

                    if(selSubCat1!=0){
                        if(selSubCat2!=0){
                            if(selSubCat3!=0){
                                if(selSubCat4!=0){
                                    mRef=mDataBase_Reference.child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2))
                                            .child(arraySubCat1.get(selSubCat1-1))
                                            .child(arraySubCat2.get(selSubCat2-1))
                                            .child(arraySubCat3.get(selSubCat3-1))
                                            .child(arraySubCat4.get(selSubCat4-1))
                                            .push();
                                }else{
                                    mRef=mDataBase_Reference.child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2))
                                            .child(arraySubCat1.get(selSubCat1-1))
                                            .child(arraySubCat2.get(selSubCat2-1))
                                            .child(arraySubCat3.get(selSubCat3-1))
                                            .push();
                                }
                            }else{
                                mRef=mDataBase_Reference.child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2))
                                        .child(arraySubCat1.get(selSubCat1-1))
                                        .child(arraySubCat2.get(selSubCat2-1))
                                        .push();
                            }
                        }else{
                            mRef=mDataBase_Reference.child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2))
                                    .child(arraySubCat1.get(selSubCat1-1))
                                    .push();
                        }
                    }else{
                        mRef=mDataBase_Reference.child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2))
                                .push();
                    }

                    mRef.setValue(itemAdd).addOnSuccessListener(agregar.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(agregar.this, getResources().getString(R.string.save_item_ok),Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();
                }else{
                    Toast.makeText(agregar.this, getResources().getString(R.string.save_item_no_ok),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void limpiar_interfaz(){
        et_calorias.setText("");
        et_azucar.setText("");
        et_sodio.setText("");

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

    void instanciarFIREBASE(){
        //DataBase
        mDataBase=FirebaseDatabase.getInstance();
        mDataBase_Reference=mDataBase.getReference().child(TAG_ALIMENTOS);
        //Storage
        mStorage=FirebaseStorage.getInstance();
        mStorage_Reference=mStorage.getReference().child(TAG_ALIMENTOS);
    }

    void instanciarOBJETOS(){

        arrayCat1=Arrays.asList(getResources().getStringArray(R.array.categoria1));
        arrayCat2=Arrays.asList(getResources().getStringArray(R.array.categoria2));
        arraySubCat1=new ArrayList<>();
        arraySubCat2=new ArrayList<>();
        arraySubCat3=new ArrayList<>();
        arraySubCat4=new ArrayList<>();

        itemAdd=new item();
        tgAdd=new tabla_general();
        lista_ingredientes=new ArrayList<>();
        images=new image_item();

    }

    void instanciarOBJETOS_interfaz(){
        spn_categoria1=findViewById(R.id.spnADD_catgeoria_1);
        spn_categoria2=findViewById(R.id.spnADD_catgeoria_2);
        spn_sub_categoria1=findViewById(R.id.spnADD_subcatgeoria_1);
        spn_sub_categoria2=findViewById(R.id.spnADD_subcatgeoria_2);
        spn_sub_categoria3=findViewById(R.id.spnADD_subcatgeoria_3);
        spn_sub_categoria4=findViewById(R.id.spnADD_subcatgeoria_4);

        et_nombre=findViewById(R.id.etADD_nombre);
        btn_guardar=findViewById(R.id.btnADD_guardar);
        btn_UploadImg_pro=findViewById(R.id.btn_upload_img_prod);
        btn_UploadImg_nut=findViewById(R.id.btn_upload_img_nutricion);
        rl_fragment=findViewById(R.id.ll_agregar);

        et_calorias=findViewById(R.id.et_calorias);
        et_azucar=findViewById(R.id.et_azucar);
        et_sodio=findViewById(R.id.et_sodio);

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

        iv_foto_pro=findViewById(R.id.iv_image_item_pro);
        iv_foto_nut=findViewById(R.id.iv_image_item_nut);

    }

    void guardarTablaGeneral(){
        if(!et_calorias.getText().toString().isEmpty()){
            tgAdd.setCalorias(Long.parseLong(et_calorias.getText().toString()));
        }

        if(!et_azucar.getText().toString().isEmpty()){
            tgAdd.setAzucar(Double.parseDouble(et_azucar.getText().toString()));
        }

        if(!et_sodio.getText().toString().isEmpty()){
            tgAdd.setSodio(Double.parseDouble(et_sodio.getText().toString()));
        }


    }

    void guardarItem(){
        if(!et_nombre.getText().toString().isEmpty()){
            itemAdd.setNombre(et_nombre.getText().toString());
        }

        if(!et_fabricante.getText().toString().isEmpty()){
            itemAdd.setFabricante(et_fabricante.getText().toString());
        }

        if(!et_gramaje.getText().toString().isEmpty()){
            itemAdd.setGramaje(Double.parseDouble(et_gramaje.getText().toString()));
        }

        if(!spn_unidad.getSelectedItem().toString().isEmpty()){
            itemAdd.setUnidad_gramaje(spn_unidad.getSelectedItem().toString());
        }



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == RC_PHOTO_PRO){

                Toast.makeText(agregar.this, getResources().getString(R.string.photo_ok),Toast.LENGTH_SHORT).show();
                Uri selected=data.getData();
                StorageReference ref=mStorage_Reference.child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2)).child("PRO_"+itemAdd.getNombre());

                ref.putFile(selected).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri download=taskSnapshot.getDownloadUrl();
                        if(download!= null){
                            images.setProducto(download.toString());
                        }
                    }
                });

                try {
                    final InputStream imageStream = getContentResolver().openInputStream(selected);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    iv_foto_pro.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(agregar.this, getResources().getString(R.string.photo_error), Toast.LENGTH_LONG).show();
                }

            }else if(requestCode == RC_PHOTO_NUT){
                Toast.makeText(agregar.this, getResources().getString(R.string.photo_ok),Toast.LENGTH_SHORT).show();
                Uri selected=data.getData();
                StorageReference ref=mStorage_Reference.child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2)).child("NUT_"+itemAdd.getNombre());

                ref.putFile(selected).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri download=taskSnapshot.getDownloadUrl();
                        if(download!= null){
                            images.setNutricion(download.toString());
                        }
                    }
                });

                try {
                    final InputStream imageStream = getContentResolver().openInputStream(selected);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    iv_foto_nut.setImageBitmap(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(agregar.this, getResources().getString(R.string.photo_error), Toast.LENGTH_LONG).show();
                }

            }

        }else if(resultCode == RESULT_CANCELED){
            Toast.makeText(agregar.this, getResources().getString(R.string.photo_no_ok),Toast.LENGTH_SHORT).show();
            if(requestCode == RC_PHOTO_PRO){
                iv_foto_pro.setImageDrawable(getResources().getDrawable(R.drawable.logo_azul_oscuro));
            }else if(requestCode == RC_PHOTO_NUT){
                iv_foto_nut.setImageDrawable(getResources().getDrawable(R.drawable.logo_azul_oscuro));
            }

            finish();
        }


    }


}
