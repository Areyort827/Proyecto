package com.example.taskmastertester.ui.slideshow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmastertester.CustomAdapter;
import com.example.taskmastertester.DetallesEvento;
import com.example.taskmastertester.Menu;
import com.example.taskmastertester.OnItemListUpdatedListener;
import com.example.taskmastertester.R;
import com.example.taskmastertester.Usuarios;
import com.example.taskmastertester.databinding.FragmentSlideshowBinding;
import com.example.taskmastertester.model.Evento;

import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

public class SlideshowFragment extends Fragment implements OnItemListUpdatedListener {

    private ArrayAdapter<String> adapter;

    private CustomAdapter customAdapter;
    private SlideshowViewModel slideshowViewModel;


    private OnItemListUpdatedListener onItemListUpdatedListener;
    private FragmentSlideshowBinding binding;
    private ListView listView;
   // private ArrayAdapter<Evento> adapter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Asegurarse de que la actividad contenedora implementa la interfaz
        if (context instanceof OnItemListUpdatedListener) {
            onItemListUpdatedListener = (OnItemListUpdatedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnItemListUpdatedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slideshowViewModel = new ViewModelProvider(requireActivity()).get(SlideshowViewModel.class);


        slideshowViewModel.getItemList().observe(this, new Observer<List<Evento>>() {
            @Override
            public void onChanged(List<Evento> items) {
                adapter.clear();
                for(Evento e: items){
                    adapter.add(e.getTitulo());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        customAdapter = new CustomAdapter(getContext(), Menu.lista);
        listView = view.findViewById(R.id.listViewEventos);
        listView.setAdapter(Menu.customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(),DetallesEvento.class);
                intent.putExtra("Titulo",Menu.customAdapter.getItem(i).getTitulo());
                intent.putExtra("Descripcion",Menu.customAdapter.getItem(i).getDescripcion() );
                intent.putExtra("Fechai", Menu.customAdapter.getItem(i).getFechaInicio().toString() );
                intent.putExtra("Fechaf", Menu.customAdapter.getItem(i).getFechaFinal().toString());
                startActivity(intent);
            }
        });

        slideshowViewModel = new ViewModelProvider(requireActivity()).get(SlideshowViewModel.class);

        slideshowViewModel.getItemList().observe(getViewLifecycleOwner(), newData -> updateListViewData(newData));
        binding = FragmentSlideshowBinding.inflate(getLayoutInflater());

        View root = binding.getRoot();
        final ListView listView1 = binding.listViewEventos;
        return view;
    }


    @Override
    public void updateListViewData(List<Evento> newData) {

        adapter.clear();

        for(Evento e: newData){
            adapter.add(e.getTitulo());
        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}