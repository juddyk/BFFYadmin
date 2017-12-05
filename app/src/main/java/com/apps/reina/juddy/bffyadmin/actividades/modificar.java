package com.apps.reina.juddy.bffyadmin.actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class modificar extends AppCompatActivity implements addIngrediente.ingredienteListener{
    Spinner spn_categoria1,spn_categoria2, spn_producto,spn_empaque,spn_unidad;
    Spinner spn_conservantes,spn_sabor,spn_apto_para;
    EditText et_nombre,et_nombre_edt,et_calorias,et_azucar,et_sodio,et_fabricante,et_gramaje,et_lineAtencion;
    TextView tv_ingredientes,tv_espera;
    Button btn_guardar,btn_UploadImg_pro,btn_UploadImg_nut;
    LinearLayout rl_fragment;
    ImageView iv_foto_pro,iv_foto_nut,ib_check;

    int selCat1=0,selCat2=0,cntChild=0;
    boolean flagFind=false;
    List<String> arrayCat1,arrayCat2,referencias,unidades;

    //ACTIVITY RESULT
    public static final int RC_PHOTO_PRO = 1;
    public static final int RC_PHOTO_NUT = 2;

    //DATABASE
    private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBase_Reference;
    private static final String TAG_ALIMENTOS="PRODUCTOS";
    private static final String TAG_PRODUCTOS_nombre="nombre";
    //STORAGE
    private FirebaseStorage mStorage;
    private StorageReference mStorage_Reference;

    item itemAdd;
    tabla_general tgAdd;
    image_item images;
    List<ingrediente> lista_ingredientes;
    String nameProducto="";
    String keyItem="",refItem="";
    String emailUser="";


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

        listener_spns();
        listener_btns();

        et_nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_espera.setVisibility(View.GONE);
                rl_fragment.setVisibility(View.GONE);
                limpiarItem();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ib_check.setVisibility(View.VISIBLE);
            }
        });

        ib_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameProducto=et_nombre.getText().toString();

                mDataBase_Reference=mDataBase.getReference().child(TAG_ALIMENTOS).child(arrayCat1.get(selCat1)).child(arrayCat2.get(selCat2));

                if(selCat2==3){//SUPLEMENTOS
                    referencias=Arrays.asList(getResources().getStringArray(R.array.child_suplementos));
                }else if(selCat1==1 && selCat2==1){//LIQUIDOS-ALIMENTOS
                    referencias=Arrays.asList(getResources().getStringArray(R.array.child_liquidos_alimentos));
                }else if(selCat1==2 && selCat2==1){//SECOS-ALIMENTOS
                    referencias=Arrays.asList(getResources().getStringArray(R.array.child_secos_alimentos));
                }else if(selCat1==3 && selCat2==1){//MIXTOS-ALIMENTOS
                    referencias=Arrays.asList(getResources().getStringArray(R.array.child_mixtos_alimentos));
                }

                tv_espera.setVisibility(View.VISIBLE);

                new modificar.loadItem_task().execute(5);
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

    void listener_spns(){
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
    }

    void listener_btns(){
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selCat1!=0 && selCat2!=0){
                    guardarItem();
                    guardarTablaGeneral();

                    lista_ingredientes.add(new ingrediente("none"));
                    lista_ingredientes.add(new ingrediente("none"));
                    lista_ingredientes.add(new ingrediente("none"));

                    itemAdd.setIngredientes(lista_ingredientes);
                    itemAdd.setInformacion(tgAdd);
                    itemAdd.setUrlImage(images);

                    String ref=refItem.substring(refItem.indexOf("/"+TAG_ALIMENTOS),refItem.lastIndexOf("/"));
                    mDataBase_Reference=mDataBase.getReference().child(ref);

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("/"+keyItem, itemAdd);
                    mDataBase_Reference.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(modificar.this, getResources().getString(R.string.save_item_ok),Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();
                }else{
                    Toast.makeText(modificar.this, getResources().getString(R.string.save_item_no_ok),Toast.LENGTH_SHORT).show();
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
    }

    void limpiar_interfaz(){
        et_nombre.setVisibility(View.GONE);
        et_nombre.setText("");
        rl_fragment.setVisibility(View.GONE);
        btn_guardar.setVisibility(View.GONE);
        ib_check.setVisibility(View.GONE);

    }

    void instanciarOBJETOS(){
        emailUser=getIntent().getExtras().getString("emailUser","");

        itemAdd=new item();
        tgAdd=new tabla_general();
        lista_ingredientes=new ArrayList<>();
        images=new image_item();

        referencias=new ArrayList<>();
        unidades= Arrays.asList(getResources().getStringArray(R.array.gramaje));
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

        et_nombre_edt=findViewById(R.id.et_item_0);
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

        ib_check=findViewById(R.id.check_nombre_edt);
        tv_espera=findViewById(R.id.tv_espera_edt);
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
        if(!et_nombre_edt.getText().toString().isEmpty()){
            itemAdd.setNombre(et_nombre_edt.getText().toString());
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

        itemAdd.setEmailUsr(emailUser);

    }

    class loadItem_task extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            cntChild=0;
            flagFind=false;
            for(int i=0;i<referencias.size();i++){
                mDataBase_Reference.child(referencias.get(i)).orderByChild(TAG_PRODUCTOS_nombre).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        cntChild++;
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            item post = postSnapshot.getValue(item.class);
                            if(post!=null){

                                if(post.getNombre().contentEquals(nameProducto)){
                                    itemAdd=post;
                                    keyItem=postSnapshot.getKey();
                                    refItem=postSnapshot.getRef().toString();
                                    mostrarItem();
                                    flagFind=true;
                                    break;
                                }
                            }
                        }

                        if(!flagFind && (cntChild+1)==referencias.size()){
                            Toast.makeText(modificar.this,getResources().getString(R.string.item_no_found),Toast.LENGTH_SHORT).show();
                            limpiarItem();
                            spn_categoria1.setSelection(0);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        String TAG="DB_BUSCAR_PRODUCTO";
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        Toast.makeText(modificar.this, "Failed to load data.",Toast.LENGTH_SHORT).show();
                    }
                });

            }


            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    void mostrarItem(){
        Toast.makeText(modificar.this, getResources().getString(R.string.item_found),Toast.LENGTH_SHORT).show();
        tv_espera.setVisibility(View.GONE);
        tgAdd=itemAdd.getInformacion();
        images=itemAdd.getUrlImage();

        et_fabricante.setText(itemAdd.getFabricante());
        String gramaje=itemAdd.getUnidad_gramaje();
        for(int i=0;i<unidades.size();i++){
            if(gramaje.equals(unidades.get(i))){
                spn_unidad.setSelection(i);
                break;
            }
        }
        et_gramaje.setText(String.valueOf(itemAdd.getGramaje()));
        et_nombre_edt.setText(itemAdd.getNombre());

        et_calorias.setText(String.valueOf(tgAdd.getCalorias()));
        et_azucar.setText(String.valueOf(tgAdd.getAzucar()));
        et_sodio.setText(String.valueOf(tgAdd.getSodio()));

        if(images.getProducto().isEmpty()){
            iv_foto_pro.setImageDrawable(getResources().getDrawable(R.drawable.logo_verde_oscuro));
        }else{
            Glide.with(getApplicationContext())
                    .load(images.getProducto())
                    .into(iv_foto_pro);
        }
        if(images.getNutricion().isEmpty()){
            iv_foto_nut.setImageDrawable(getResources().getDrawable(R.drawable.logo_verde_oscuro));
        }else {
            Glide.with(getApplicationContext())
                    .load(images.getNutricion())
                    .into(iv_foto_nut);
        }
        rl_fragment.setVisibility(View.VISIBLE);
        btn_guardar.setVisibility(View.VISIBLE);
    }

    void limpiarItem(){
        et_fabricante.setText("");
        et_gramaje.setText("");
        spn_unidad.setSelection(0);
        et_calorias.setText("");
        et_azucar.setText("");
        et_sodio.setText("");

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
