package com.apps.reina.juddy.bffyadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.apps.reina.juddy.bffyadmin.actividades.agregar;
import com.apps.reina.juddy.bffyadmin.actividades.eliminar;
import com.apps.reina.juddy.bffyadmin.actividades.modificar;
import com.apps.reina.juddy.bffyadmin.actividades.ver;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //DATABASE
    private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBase_Reference;
    private ChildEventListener mDataBase_ChildListener;
    //AUTH
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuth_StateListener;
    //STORAGE
    private FirebaseStorage mStorage;
    private StorageReference mStorage_Reference;

    private static final String CHILD_PRODUCTOS="PRODUCTOS";
    private static final String CHILD_USUARIOS="ADMINISTRADORES";
    public static final int RC_AUTH = 1;
    private String mUsername;

    //OBEJTOS INTERFAZ
    Button btnAgregar, btnModificar, btnEliminar, btnVer, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se esconde el Titulo de la app
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Establece la vista
        setContentView(R.layout.activity_main);

        instanciaFIREBASE();
        instanciaOBJETOS_interfaz();


        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ver.class));
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, agregar.class));
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, modificar.class));
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, eliminar.class));
             }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(MainActivity.this);
                Toast.makeText(MainActivity.this, getResources().getString(R.string.sign_out),Toast.LENGTH_SHORT).show();

            }
        });


        //AUTENTICACION
        mAuth_StateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user != null){
                    //Usuario logueado
                    onSignedInInitialize(user.getDisplayName());
                }else{
                    //Usuario no logueado
                    onSignedOutCleanup();
                    //Se especifica los proveedores de acceso a usar
                    //  En este caso se usa EMAIL y GOOGLE
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                    );

                    startActivityForResult(AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .setTheme(R.style.AuthTheme)
                                    .setLogo(R.drawable.logo)
                                    .build(),
                            RC_AUTH);
                }
            }
        };



    }

    void instanciaFIREBASE(){
        //DataBase
        mDataBase=FirebaseDatabase.getInstance();
        mDataBase_Reference=mDataBase.getReference().child(CHILD_PRODUCTOS);
        //Auth
        mAuth=FirebaseAuth.getInstance();
        //Storage
        mStorage=FirebaseStorage.getInstance();
        mStorage_Reference=mStorage.getReference().child(CHILD_PRODUCTOS);
    }

    void instanciaOBJETOS_interfaz(){
        btnVer=findViewById(R.id.btn_ver);
        btnAgregar=findViewById(R.id.btn_agregar);
        btnModificar=findViewById(R.id.btn_editar);
        btnEliminar=findViewById(R.id.btn_eliminar);
        btnSalir=findViewById(R.id.btn_salir);
    }

    private void onSignedOutCleanup() {
        mUsername="none";
        detachDataBaseReadListener();
    }

    private void onSignedInInitialize(String username) {
        mUsername=username;
        attachDataBaseReadListener();

    }
    private void attachDataBaseReadListener(){
        /*
        if(mDataBase_ChildListener == null){
            mDataBase_ChildListener= new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //Actulizar informacion de base de datos.
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mDataBase_Reference.addChildEventListener(mDataBase_ChildListener);

        }
        */
    }

    private void detachDataBaseReadListener(){
        /*
        if(mDataBase_ChildListener != null){
            mDataBase_Reference.removeEventListener(mDataBase_ChildListener);
            mDataBase_ChildListener=null;
        }
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_AUTH){
            if(resultCode == RESULT_OK){
                Toast.makeText(MainActivity.this, getResources().getString(R.string.sign_in),Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this, getResources().getString(R.string.sign_out),Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuth_StateListener);
        detachDataBaseReadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuth_StateListener);
    }
}
