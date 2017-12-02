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

public class modificar extends AppCompatActivity implements addIngrediente.ingredienteListener{
    Spinner spn_categoria1,spn_categoria2, spn_producto,spn_empaque,spn_unidad;
    Spinner spn_conservantes,spn_sabor,spn_apto_para;
    EditText et_nombre,et_calorias,et_azucar,et_sodio,et_fabricante,et_gramaje,et_lineAtencion;
    TextView tv_ingredientes;
    Button btn_guardar,btn_UploadImg_pro,btn_UploadImg_nut;
    LinearLayout rl_fragment;
    ImageView iv_foto_pro,iv_foto_nut;

    int selCat1=0,selCat2=0;
    List<String> arrayCat1,arrayCat2;

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
        setTitle(R.string.edit);
        //Establece la vista
        setContentView(R.layout.activity_modificar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        instanciarFIREBASE();
        instanciarOBJETOS();
        instanciarOBJETOS_interfaz();

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



                }
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

    void instanciarOBJETOS(){
        itemAdd=new item();
        tgAdd=new tabla_general();
        lista_ingredientes=new ArrayList<>();
        images=new image_item();

        arrayCat1= Arrays.asList(getResources().getStringArray(R.array.categoria1));
        arrayCat2=Arrays.asList(getResources().getStringArray(R.array.categoria2));

    }

    void instanciarFIREBASE(){
        //DataBase
        mDataBase= FirebaseDatabase.getInstance();
        mDataBase_Reference=mDataBase.getReference().child(TAG_ALIMENTOS);
        //Storage
        mStorage= FirebaseStorage.getInstance();
        mStorage_Reference=mStorage.getReference().child(TAG_ALIMENTOS);
    }

    void instanciarOBJETOS_interfaz(){
        spn_categoria1=findViewById(R.id.spnEDT_catgeoria_1);
        spn_categoria2=findViewById(R.id.spnEDT_catgeoria_2);

        et_nombre=findViewById(R.id.etEDT_nombre);
        btn_guardar=findViewById(R.id.btnEDT_guardar);
        btn_UploadImg_pro=findViewById(R.id.btn_upload_img_prod);
        btn_UploadImg_nut=findViewById(R.id.btn_upload_img_nutricion);
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
            itemAdd.setGramaje(Long.parseLong(et_gramaje.getText().toString()));
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
                Toast.makeText(modificar.this, getResources().getString(R.string.photo_ok),Toast.LENGTH_SHORT).show();
                Uri selected=data.getData();

                StorageReference ref=mStorage_Reference.child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2)).child("PRO_"+itemAdd.getNombre());

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
                    iv_foto_pro.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(modificar.this, getResources().getString(R.string.photo_error), Toast.LENGTH_LONG).show();
                }

            }else if(requestCode == RC_PHOTO_NUT){
                Toast.makeText(modificar.this, getResources().getString(R.string.photo_ok),Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(modificar.this, getResources().getString(R.string.photo_error), Toast.LENGTH_LONG).show();
                }
            }

        }else if(resultCode == RESULT_CANCELED){
            Toast.makeText(modificar.this, getResources().getString(R.string.photo_no_ok),Toast.LENGTH_SHORT).show();
            if(requestCode == RC_PHOTO_PRO){
                iv_foto_pro.setImageDrawable(getResources().getDrawable(R.drawable.logo_azul_oscuro));
            }else if(requestCode == RC_PHOTO_NUT){
                iv_foto_nut.setImageDrawable(getResources().getDrawable(R.drawable.logo_azul_oscuro));
            }

            finish();
        }
    }

}
