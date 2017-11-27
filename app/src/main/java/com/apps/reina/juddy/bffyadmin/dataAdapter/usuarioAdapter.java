package com.apps.reina.juddy.bffyadmin.dataAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class usuarioAdapter extends BaseAdapter implements ListAdapter {
    private List<usuario> list = new ArrayList<>();
    private Context context;

    public usuarioAdapter(List<usuario> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_usuario, null);

        }

        //Handle TextView and display string from your list
        EditText nombre=view.findViewById(R.id.et_user_name);
        EditText correo=view.findViewById(R.id.et_user_email);
        EditText celular=view.findViewById(R.id.et_user_phone);

        nombre.setText(list.get(position).getNombre());
        correo.setText(list.get(position).getCorreo());
        celular.setText(list.get(position).getCeluar());


        return view;
    }

}
