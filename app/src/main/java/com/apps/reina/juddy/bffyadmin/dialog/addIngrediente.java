package com.apps.reina.juddy.bffyadmin.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.ingrediente;
import com.apps.reina.juddy.bffyadmin.dataAdapter.ingredienteAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */
public class addIngrediente extends DialogFragment {

    List<ingrediente> lst_ingredientes;

    ListView lv_ingredientes;
    ImageButton btn_add;
    EditText et_ingrediente;

    public interface ingredienteListener {
        void on_addIngrediente_Positive(DialogFragment dialog, List<ingrediente> lst);
        void on_addIngrediente_Negative(DialogFragment dialog);
    }

    addIngrediente.ingredienteListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (addIngrediente.ingredienteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+ " must implement NoticeDialogListener");
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_ingrediente, null);

        btn_add=view.findViewById(R.id.btn_add_ingrediente);
        lv_ingredientes=view.findViewById(R.id.lv_ingredientes);
        et_ingrediente=view.findViewById(R.id.et_ingrediente);

        lst_ingredientes=new ArrayList<>();
        ArrayList<String> nombres_ingredientes;

        Bundle bndl= getArguments();
        nombres_ingredientes=bndl.getStringArrayList("lista");
        lst_ingredientes.clear();
        if(nombres_ingredientes.size()==1){
            if (!nombres_ingredientes.get(0).contains("none")){
                ingrediente ingr=new ingrediente();
                ingr.setNombre(nombres_ingredientes.get(0));
                lst_ingredientes.add(ingr);
                actualizar_list_view();
            }
        }else{
            for(int i=0;i<nombres_ingredientes.size();i++){
                ingrediente ingr=new ingrediente();
                ingr.setNombre(nombres_ingredientes.get(i));
                lst_ingredientes.add(ingr);
            }
            actualizar_list_view();
        }


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingrediente ingr=new ingrediente();
                ingr.setNombre(et_ingrediente.getText().toString());
                lst_ingredientes.add(ingr);

                actualizar_list_view();

                et_ingrediente.setText("");

            }
        });


        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //ACCEPT
                        if(!lst_ingredientes.isEmpty()){
                            mListener.on_addIngrediente_Positive(addIngrediente.this,lst_ingredientes);
                            Toast.makeText(getContext(),getResources().getString(R.string.toast_ingredientes_ok), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),getResources().getString(R.string.toast_ingredientes_empty), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.general_no_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //CANCEL
                        mListener.on_addIngrediente_Negative(addIngrediente.this);
                    }
                });


        return builder.create();

    }

    void actualizar_list_view(){
        ViewGroup.LayoutParams params = lv_ingredientes.getLayoutParams();
        params.height = (lv_ingredientes.getDividerHeight()+55)* lst_ingredientes.size();
        lv_ingredientes.setLayoutParams(params);
        ingredienteAdapter lstAdapter=new ingredienteAdapter(lst_ingredientes, getContext());
        lv_ingredientes.setAdapter(lstAdapter);
    }



}
