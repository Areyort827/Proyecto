package com.example.taskmastertester;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.taskmastertester.model.Evento;
import com.example.taskmastertester.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Evento> {

    Context context;
    List<Evento> list;

    public CustomAdapter(Context context, List<Evento> dataArrayList) {
        super(context,0,dataArrayList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Evento e = getItem(i);
        TextView titulo;

        if( view == null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.list_items,viewGroup, false);
        }

        titulo = (TextView) view.findViewById(R.id.idtitulo);

        titulo.setText(e.getTitulo());

        return view;
    }
}
