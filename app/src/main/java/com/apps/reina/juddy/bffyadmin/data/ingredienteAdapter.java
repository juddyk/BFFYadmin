package com.apps.reina.juddy.bffyadmin.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juddy on 20/11/17.
 */

public class ingredienteAdapter extends BaseAdapter implements ListAdapter {

    private List<ingrediente> list = new ArrayList<>();
    private Context context;

    public ingredienteAdapter(List<ingrediente> list, Context context) {
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
            /*
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_posgrado, null);
            */
        }

        //Handle TextView and display string from your list
        /*
            TextView itemName = (TextView)view.findViewById(R.id.titulo);

            itemName.setText(list.get(position).getNombre());
        */

        return view;
    }


}
