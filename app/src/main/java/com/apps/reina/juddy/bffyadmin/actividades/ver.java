package com.apps.reina.juddy.bffyadmin.actividades;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.image_item;
import com.apps.reina.juddy.bffyadmin.data.ingrediente;
import com.apps.reina.juddy.bffyadmin.data.item;
import com.apps.reina.juddy.bffyadmin.data.tabla_general;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class ver extends AppCompatActivity {
    Spinner spn_categoria1,spn_categoria2;
    EditText et_nombre;
    LinearLayout rl_fragment;

    TextView tv_calorias,tv_azucar,tv_sodio,tv_fabricante,tv_gramaje,tv_lineAtencion;
    TextView tv_producto,tv_empaque,tv_conservantes,tv_sabor,tv_apto_para;
    TextView tv_nombre,tv_ingredientes;
    ImageView iv_foto_pro,iv_foto_nut;
    ImageButton ib_check;
    ProgressBar pb_espera;


    //DATABASE
    private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBase_Reference;

    private static final String TAG_ALIMENTOS="PRODUCTOS";
    private static final String TAG_PRODUCTOS_nombre="nombre";

    item itemShw;
    tabla_general tgShw;
    image_item images;
    List<ingrediente> lista_ingredientes;
    String nameProducto="";
    String keyItem="";

    int selCat1=0,selCat2=0;
    Integer count=0;
    List<String> arrayCat1,arrayCat2,referencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.look);
        //Establece la vista
        setContentView(R.layout.activity_ver);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        instanciarFIREBASE();
        instanciarOBJETOS();
        instanciarOBJETOS_interfaz();


        //SELECCION CATEGORIA 1
        spn_categoria1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                et_nombre.setVisibility(View.GONE);
                et_nombre.setText("");
                ib_check.setVisibility(View.GONE);


                //Establece la seleccion por defecto en todos los spinner
                spn_categoria2.setSelection(0);

                spn_categoria2.setVisibility(View.GONE);
                //Reinicia la seleccion a 0
                selCat2=0;
                if(position!=0){//Garantiza que se escoja una opcion valida
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
                ib_check.setVisibility(View.GONE);

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
                    pb_espera.setVisibility(View.GONE);
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

                pb_espera.setVisibility(View.VISIBLE);
                pb_espera.setProgress(0);
                new loadItem_task().execute(5);



            }
        });


    }

    void instanciarFIREBASE(){
        //DataBase
        mDataBase= FirebaseDatabase.getInstance();

    }

    void instanciarOBJETOS(){

        itemShw=new item();
        tgShw=new tabla_general();
        images=new image_item();
        lista_ingredientes=new ArrayList<>();
        referencias=new ArrayList<>();
        arrayCat1= Arrays.asList(getResources().getStringArray(R.array.categoria1));
        arrayCat2=Arrays.asList(getResources().getStringArray(R.array.categoria2));
    }

    void instanciarOBJETOS_interfaz(){
        spn_categoria1=findViewById(R.id.spnSEE_catgeoria_1);
        spn_categoria2=findViewById(R.id.spnSEE_catgeoria_2);
        et_nombre=findViewById(R.id.etSEE_nombre);
        rl_fragment=findViewById(R.id.ll_ver);

        tv_calorias=findViewById(R.id.tv_calorias);
        tv_azucar=findViewById(R.id.tv_azucar);
        tv_sodio=findViewById(R.id.tv_sodio);

        tv_nombre=findViewById(R.id.tv_item_0);
        tv_ingredientes=findViewById(R.id.tv_item_1);
        tv_fabricante=findViewById(R.id.tv_item_2);
        tv_gramaje=findViewById(R.id.tv_item_3);
        tv_lineAtencion=findViewById(R.id.tv_item_4);
        tv_producto=findViewById(R.id.tv_item_5);
        tv_empaque=findViewById(R.id.tv_item_6);
        tv_conservantes=findViewById(R.id.tv_item_7);
        tv_sabor=findViewById(R.id.tv_item_8);
        tv_apto_para=findViewById(R.id.tv_item_9);

        iv_foto_pro=findViewById(R.id.iv_image_item_pro);
        iv_foto_nut=findViewById(R.id.iv_image_item_nut);

        ib_check=findViewById(R.id.check_nombre);

        pb_espera=findViewById(R.id.pb_espera);
        pb_espera.setMax(10);
    }


    class loadItem_task extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for(int i=0;i<referencias.size();i++){
                count+=35;
                count=count>=100?0:count;//si llega a 100, volver a empezar
                publishProgress(count);

                mDataBase_Reference.child(referencias.get(i)).orderByChild(TAG_PRODUCTOS_nombre).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            item post = postSnapshot.getValue(item.class);
                            if(post!=null){

                                if(post.getNombre().contentEquals(nameProducto)){
                                    itemShw=post;
                                    keyItem=postSnapshot.getKey();
                                    mostrarItem();
                                    Toast.makeText(ver.this, getResources().getString(R.string.item_found),Toast.LENGTH_LONG).show();
                                    pb_espera.setVisibility(View.GONE);
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        String TAG="DB_BUSCAR_PRODUCTO";
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        Toast.makeText(ver.this, "Failed to load data.",Toast.LENGTH_SHORT).show();
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
            pb_espera.setProgress(values[0]);
        }
    }

    void mostrarItem(){
        Toast.makeText(ver.this, keyItem,Toast.LENGTH_SHORT).show();
        pb_espera.setVisibility(View.GONE);
        tgShw=itemShw.getInformacion();
        images=itemShw.getUrlImage();

        tv_fabricante.setText(itemShw.getFabricante());
        String gramaje=String.valueOf(itemShw.getGramaje())+itemShw.getUnidad_gramaje();
        tv_gramaje.setText(gramaje);
        tv_nombre.setText(itemShw.getNombre());

        tv_calorias.setText(String.valueOf(tgShw.getCalorias()));
        tv_azucar.setText(String.valueOf(tgShw.getAzucar()));
        tv_sodio.setText(String.valueOf(tgShw.getSodio()));
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
    }

    void limpiarItem(){
        tv_fabricante.setText("");
        tv_gramaje.setText("");
        tv_nombre.setText("");
        tv_calorias.setText("");
        tv_azucar.setText("");
        tv_sodio.setText("");

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
