package com.apps.reina.juddy.bffyadmin.dataAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.apps.reina.juddy.bffyadmin.data.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 20/11/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 *
 */

public class itemAdapter extends BaseAdapter implements ListAdapter {

    private List<item> list = new ArrayList<>();
    private Context context;

    public itemAdapter(List<item> list, Context context) {
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
