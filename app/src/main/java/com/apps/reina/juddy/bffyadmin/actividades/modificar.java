package com.apps.reina.juddy.bffyadmin.actividades;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.ingrediente;
import com.apps.reina.juddy.bffyadmin.dialog.addIngrediente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class modificar extends AppCompatActivity implements addIngrediente.ingredienteListener{
    Spinner spn_categoria1,spn_categoria2, spn_producto,spn_empaque,spn_unidad;
    Spinner spn_conservantes,spn_sabor,spn_apto_para;
    EditText et_nombre,et_calorias,et_azucar,et_sodio,et_fabricante,et_gramaje,et_lineAtencion;
    TextView tv_ingredientes;
    Button btn_guardar,btn_UploadImg;
    LinearLayout rl_fragment;
    ImageView iv_foto;

    List<ingrediente> lista_ingredientes;
    int selCat1=0,selCat2=0;

    public static final int RC_PHOTO = 1;

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
        btn_UploadImg=findViewById(R.id.btn_upload_img);
        rl_fragment=findViewById(R.id.ll_modificar);

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

        iv_foto=findViewById(R.id.iv_image_item);

        lista_ingredientes=new ArrayList<>();

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

        btn_UploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.photo_selec)), RC_PHOTO);
            }
        });

        tv_ingredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> ingrs=new ArrayList<>();
                ingrs.clear();
                for(int i = 0; i<lista_ingredientes.size(); i++){
                    ingrs.add(lista_ingredientes.get(i).getNombre());
                }
                if(ingrs.isEmpty()){
                    ingrs.add("none");
                }


                DialogFragment dialog = new addIngrediente();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("lista",ingrs);
                dialog.setArguments(bundle);

                dialog.show(getSupportFragmentManager(), getResources().getString(R.string.item_1));
            }
        });

    }

    void limpiar_interfaz(){
        et_nombre.setVisibility(View.GONE);
        et_nombre.setText("");
        rl_fragment.setVisibility(View.GONE);
        btn_guardar.setVisibility(View.GONE);
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

        if(requestCode == RC_PHOTO){
            if(resultCode == RESULT_OK){
                Toast.makeText(modificar.this, getResources().getString(R.string.photo_ok),Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(modificar.this, getResources().getString(R.string.photo_no_ok),Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
