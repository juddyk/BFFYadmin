package com.apps.reina.juddy.bffyadmin.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.ingrediente;
import com.apps.reina.juddy.bffyadmin.data.itemRow;
import com.apps.reina.juddy.bffyadmin.dataAdapter.itemRowAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 5/12/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 */

public class listItems extends DialogFragment {

    List<String> nombres_producto;
    ListView lv_items;
    List<itemRow> listAdapter;
    String selected;
    int sel=0;

    public interface itemsListener {
        void on_items_Positive(DialogFragment dialog, String nombre, int pos);
        void on_items_Negative(DialogFragment dialog);
    }

    listItems.itemsListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (listItems.itemsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+ " must implement NoticeDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_list_items, null);

        lv_items=view.findViewById(R.id.lstView);
        nombres_producto=new ArrayList<>();
        listAdapter=new ArrayList<>();

        Bundle bndl= getArguments();
        nombres_producto=bndl.getStringArrayList("lista");
        if(nombres_producto.size()>1){
            for(int i=0;i<nombres_producto.size();i++){
                itemRow itm=new itemRow();
                itm.setTitle(nombres_producto.get(i));
                listAdapter.add(itm);
            }
            lv_items.setAdapter(new itemRowAdapter(getContext(), listAdapter));

        }

        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                sel=position;
                selected=listAdapter.get(position).getTitle();
                Toast.makeText(getContext(), selected, Toast.LENGTH_SHORT).show();
                for(int i=0;i<listAdapter.size();i++){
                    listAdapter.get(i).setChecked(false);
                }
                listAdapter.get(position).setChecked(true);
                lv_items.setAdapter(new itemRowAdapter(getContext(), listAdapter));
            }
        });


        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //ACCEPT
                            mListener.on_items_Positive(listItems.this,selected,sel);
                    }
                })
                .setNegativeButton(R.string.general_no_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //CANCEL
                        mListener.on_items_Negative(listItems.this);
                    }
                });


        return builder.create();


    }




}
