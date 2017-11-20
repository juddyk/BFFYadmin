package com.apps.reina.juddy.bffyadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.apps.reina.juddy.bffyadmin.actividades.agregar;
import com.apps.reina.juddy.bffyadmin.actividades.eliminar;
import com.apps.reina.juddy.bffyadmin.actividades.modificar;
import com.apps.reina.juddy.bffyadmin.actividades.ver;
import com.apps.reina.juddy.bffyadmin.logueo.logueo;
import com.apps.reina.juddy.bffyadmin.logueo.registro;

public class MainActivity extends AppCompatActivity {

    Button btnAgregar, btnModificar, btnEliminar, btnVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se esconde el Titulo de la app
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Establece la vista
        setContentView(R.layout.activity_main);

        btnVer=findViewById(R.id.btn_ver);
        btnAgregar=findViewById(R.id.btn_agregar);
        btnModificar=findViewById(R.id.btn_editar);
        btnEliminar=findViewById(R.id.btn_eliminar);

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




    }
}
