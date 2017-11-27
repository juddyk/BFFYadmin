package com.apps.reina.juddy.bffyadmin.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.usuario;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class perfilUsuario extends DialogFragment {

    usuario dataUser;

    public interface perfilUsuarioListener {
        void on_perfil_Positive(DialogFragment dialog, usuario user);
        void on_perfil_Negative(DialogFragment dialog);
    }

    perfilUsuario.perfilUsuarioListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (perfilUsuario.perfilUsuarioListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+ " must implement NoticeDialogListener");
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_perfil, null);

        EditText nombre=view.findViewById(R.id.et_user_name);
        EditText correo=view.findViewById(R.id.et_user_email);
        EditText celular=view.findViewById(R.id.et_user_phone);

        dataUser=new usuario();

        Bundle bndl= getArguments();
        //Establecer los datos en la interfaz
        dataUser.setNombre(bndl.getString("nombre",getResources().getString(R.string.campo_vacio)));
        dataUser.setCorreo(bndl.getString("correo",getResources().getString(R.string.campo_vacio)));
        dataUser.setCeluar(bndl.getString("celular",getResources().getString(R.string.campo_vacio)));

        nombre.setText(dataUser.getNombre());
        correo.setText(dataUser.getCorreo());
        celular.setText(dataUser.getCeluar());

        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                dataUser.setNombre(s.toString());
            }
        });

        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                dataUser.setCorreo(s.toString());
            }
        });

        celular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                dataUser.setCeluar(s.toString());
            }
        });


        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //ACCEPT
                        //VALIDAR QUE NINGUN CAMPO ESTE VACIO
                        if(!dataUser.getNombre().isEmpty() && !dataUser.getCorreo().isEmpty() && !dataUser.getCeluar().isEmpty()){
                            mListener.on_perfil_Positive(perfilUsuario.this,dataUser);
                            Toast.makeText(getContext(),getResources().getString(R.string.perfil_ok), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),getResources().getString(R.string.perfil_no_ok), Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton(R.string.general_no_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //CANCEL
                        mListener.on_perfil_Negative(perfilUsuario.this);
                    }
                });


        return builder.create();

    }

}
