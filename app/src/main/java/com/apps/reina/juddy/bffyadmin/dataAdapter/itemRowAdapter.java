package com.apps.reina.juddy.bffyadmin.dataAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.apps.reina.juddy.bffyadmin.R;
import com.apps.reina.juddy.bffyadmin.data.itemRow;

import java.util.List;

/**
 * Created by JUDDY KATHERIN REINA PARDO on 5/12/17.
 * APLICACION ADMINISTRADOR DE BEST FOOD FOR YOY (BFFY)
 */

public class itemRowAdapter extends ArrayAdapter<itemRow> {

    private LayoutInflater layoutInflater;

    public itemRowAdapter(@NonNull Context context, @NonNull List<itemRow> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // holder pattern
        Holder holder = null;
        if (convertView == null)
        {
            holder = new Holder();

            convertView = layoutInflater.inflate(R.layout.adapter_row_item, null);
            holder.setTextViewTitle((TextView) convertView.findViewById(R.id.row_title));
            holder.setCheckBox((CheckBox) convertView.findViewById(R.id.row_checked));
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        itemRow row = getItem(position);
        holder.getTextViewTitle().setText(row.getTitle());
        holder.getCheckBox().setChecked(row.isChecked());

        return convertView;
    }

    static class Holder
    {
        TextView textViewTitle;
        CheckBox checkBox;

        public TextView getTextViewTitle()
        {
            return textViewTitle;
        }

        public void setTextViewTitle(TextView textViewTitle)
        {
            this.textViewTitle = textViewTitle;
        }

        public CheckBox getCheckBox()
        {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox)
        {
            this.checkBox = checkBox;
        }

    }

}
