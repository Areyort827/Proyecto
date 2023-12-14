package com.example.taskmastertester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.taskmastertester.model.Usuario;

import java.util.List;

public class AdapterUsuarios extends ArrayAdapter<Usuario> {


    public AdapterUsuarios(Context context, List<Usuario> list) {
        super(context, 0, list);

    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        Usuario usuario = getItem(i);
        TextView nombre;



        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_usuarios, viewGroup, false);
        }

        nombre = (TextView) convertView.findViewById(R.id.idnombre);


        nombre.setText(usuario.getNombre());
        return convertView;
    }
}
