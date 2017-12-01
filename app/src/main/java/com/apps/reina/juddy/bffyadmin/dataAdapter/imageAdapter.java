package com.apps.reina.juddy.bffyadmin.dataAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.image_item;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 1/12/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 */

public class imageAdapter extends ArrayAdapter<image_item> {

    public imageAdapter(List<image_item> objects, int resource, Context context) {
        super(context, resource, objects);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.ver_image_item, parent, false);

            ImageView iv_pro=view.findViewById(R.id.iv_image_item_pro);
            ImageView iv_nut=view.findViewById(R.id.iv_image_item_nut);

            if(getItem(position).getProducto().isEmpty()){
                iv_pro.setVisibility(View.VISIBLE);
                Glide.with(iv_pro.getContext())
                        .load(getItem(position).getProducto())
                        .into(iv_pro);
            }

            if(getItem(position).getNutricion().isEmpty()){
                iv_pro.setVisibility(View.VISIBLE);
                Glide.with(iv_pro.getContext())
                        .load(getItem(position).getProducto())
                        .into(iv_pro);
            }else{
                iv_nut.setVisibility(View.GONE);
            }


        }


        return view;
    }


}
