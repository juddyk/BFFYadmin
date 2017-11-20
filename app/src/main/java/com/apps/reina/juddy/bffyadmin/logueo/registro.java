package com.apps.reina.juddy.bffyadmin.logueo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.apps.reina.juddy.bffyadmin.R;

public class registro extends AppCompatActivity {

    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se esconde el Titulo de la app
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Establece la vista
        setContentView(R.layout.activity_registro);

        btnGuardar=findViewById(R.id.btn_guardar_registro);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registro.this,logueo.class));
                finish();
            }
        });


    }
}
