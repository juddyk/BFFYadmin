package com.apps.reina.juddy.bffyadmin;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class splash extends AppCompatActivity {

    private static final long text_DELAY=350;//Tiempo que dura el Splash en ms
    TextView txtNombre;
    int cnt=0;
    final String[] textToShow = { "LA", "LA MEJOR","LA MEJOR COMIDAD", "LA MEJOR COMIDAD PARA", "LA MEJOR COMIDAD PARA TI"};
    Timer tmr;
    TimerTask task;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se esconde el Titulo de la app
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Pantalla Completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Establece la vista
        setContentView(R.layout.activity_splash);

        txtNombre=findViewById(R.id.txt_name_app);
        task=new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(cnt==5){
                            tmr.cancel();
                            tmr = null;
                            startActivity(new Intent(splash.this,MainActivity.class));
                            finish();
                        }else{
                            txtNombre.setText(textToShow[cnt]);
                            cnt++;
                        }
                    }
                });
            }
        };

        //Se define el timer
        tmr=new Timer();
        //Se asigna la tarea y el tiempo de espera.
        tmr.schedule(task,0,text_DELAY);



    }
}
