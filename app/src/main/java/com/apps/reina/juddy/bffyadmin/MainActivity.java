package com.apps.reina.juddy.bffyadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.apps.reina.juddy.bffyadmin.actividades.agregar;
import com.apps.reina.juddy.bffyadmin.actividades.eliminar;
import com.apps.reina.juddy.bffyadmin.actividades.modificar;
import com.apps.reina.juddy.bffyadmin.actividades.ver;
import com.apps.reina.juddy.bffyadmin.data.usuario;
import com.apps.reina.juddy.bffyadmin.dialog.perfilUsuario;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class MainActivity extends AppCompatActivity implements perfilUsuario.perfilUsuarioListener {

    //DATABASE
    private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBase_Reference;
    private  ValueEventListener mDataBase_listener;
    //AUTH
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuth_StateListener;
    private FirebaseUser mAuth_user;

    private static final String TAG_USUARIOS="ADMINISTRADORES";

    public static final int RC_AUTH = 1;
    private usuario mUser;
    private String idUser;
    int count=0;

    //OBJETOS INTERFAZ
    Button btnAgregar, btnModificar, btnEliminar, btnVer, btnSalir, btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se esconde el Titulo de la app
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Establece la vista
        setContentView(R.layout.activity_main);

        instanciarFIREBASE();
        instanciarOBJETOS_interfaz();
        instanciarOBJETOS();


        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                Intent i = new Intent(MainActivity.this, ver.class);
                startActivity(i);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                Intent i = new Intent(MainActivity.this, agregar.class);
                i.putExtra("emailUser",mAuth_user.getEmail());
                startActivity(i);
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                Intent i = new Intent(MainActivity.this, modificar.class);
                i.putExtra("emailUser",mAuth_user.getEmail());
                startActivity(i);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                Intent i = new Intent(MainActivity.this, eliminar.class);
                startActivity(i);
             }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                DialogFragment dialog = new perfilUsuario();

                Bundle bundle = new Bundle();
                bundle.putString("nombre",mUser.getNombre());
                bundle.putString("correo",mUser.getCorreo());
                bundle.putString("celular",mUser.getCeluar());
                dialog.setArguments(bundle);

                dialog.show(getSupportFragmentManager(), getResources().getString(R.string.perfil_data));
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                AuthUI.getInstance().signOut(MainActivity.this);
                Toast.makeText(MainActivity.this, getResources().getString(R.string.sign_out),Toast.LENGTH_SHORT).show();

            }
        });

        //AUTENTICACION
        mAuth_StateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mAuth_user=firebaseAuth.getCurrentUser();
                if(mAuth_user != null){
                    count++;
                    validarExistenciaUsuario();
                    //Usuario logueado
                    onSignedInInitialize(mAuth_user.getDisplayName(), mAuth_user.getEmail());
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
                                    .setLogo(R.drawable.logo_verde_oscuro)
                                    .build(),
                            RC_AUTH);
                }
            }
        };

        
    }

    //Método para validar existencia del usuario registrado
    private  void validarExistenciaUsuario(){
        mDataBase_listener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag=false;
                if(count==1){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        usuario post = postSnapshot.getValue(usuario.class);
                        if(post!=null){
                            if(post.getCorreo().contentEquals(mAuth_user.getEmail())){
                                flag=true;
                                mUser=post;
                                idUser=postSnapshot.getKey();
                                break;
                            }
                        }else{
                            mUser=post;
                            idUser=postSnapshot.getKey();
                        }
                    }
                    if(!flag){
                        //REGISTRA EL USUARIO
                        mUser=new usuario(0,mAuth_user.getDisplayName(),mAuth_user.getEmail(),""/*,mAuth_user.getUid()*/);
                        DatabaseReference mref=mDataBase.getReference().child(TAG_USUARIOS).push();
                        mref.setValue(mUser);
                        idUser=mref.getKey();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String TAG="DB_VALIDAR";
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to load data.",Toast.LENGTH_SHORT).show();
            }
        };
        mDataBase_Reference.addListenerForSingleValueEvent(mDataBase_listener);


    }

    //Metodo para actualizar el usuario registrado
    private  void actualizarUsuario(){
        Map<String, Object> updates = new HashMap<>();
        updates.put("/"+idUser, mUser);
        mDataBase_Reference.updateChildren(updates);

    }


    void instanciarFIREBASE(){
        //DataBase
        mDataBase=FirebaseDatabase.getInstance();
        mDataBase_Reference=mDataBase.getReference().child(TAG_USUARIOS);
        //Auth
        mAuth=FirebaseAuth.getInstance();
    }

    void instanciarOBJETOS_interfaz(){
        btnVer=findViewById(R.id.btn_ver);
        btnAgregar=findViewById(R.id.btn_agregar);
        btnModificar=findViewById(R.id.btn_editar);
        btnEliminar=findViewById(R.id.btn_eliminar);
        btnSalir=findViewById(R.id.btn_salir);
        btnPerfil=findViewById(R.id.btn_perfil);
    }

    void instanciarOBJETOS(){
        mUser=new usuario();
    }

    private void onSignedOutCleanup() {
        mUser.setNombre("none");
        detachDataBaseReadListener();
    }

    private void onSignedInInitialize(String username,String correo) {
        mUser.setNombre(username);
        mUser.setCorreo(correo);
        mUser.setCeluar("none");
        attachDataBaseReadListener();

    }

    private void attachDataBaseReadListener(){

    }

    private void detachDataBaseReadListener(){
        if(mDataBase_listener != null){
            mDataBase_Reference.removeEventListener(mDataBase_listener);
            mDataBase_listener=null;
        }


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

    @Override
    public void on_perfil_Positive(DialogFragment dialog, usuario user) {
        mUser=user;
        actualizarUsuario();
        dialog.dismiss();
    }

    @Override
    public void on_perfil_Negative(DialogFragment dialog) {
        dialog.dismiss();
    }
}
