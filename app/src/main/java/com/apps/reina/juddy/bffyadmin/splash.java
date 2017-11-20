package com.apps.reina.juddy.bffyadmin;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.apps.reina.juddy.bffyadmin.logueo.logueo;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {

    private static final long SPLASH_DELAY=2500;//Tiempo que dura el Splash en ms
    private static final long text_DELAY=500;//Tiempo que dura el Splash en ms
    TextView txtNombre;
    int cnt=0;
    final String[] textToShow = { "BEST", "BEST FOOD","BEST FOOD FOR", "BEST FOOD FOR YOU"};
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
                        if(cnt==4){
                            tmr.cancel();
                            tmr = null;

                            startActivity(new Intent(splash.this,logueo.class));
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
