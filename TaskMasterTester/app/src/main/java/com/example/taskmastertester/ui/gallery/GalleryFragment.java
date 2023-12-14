package com.example.taskmastertester.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmastertester.Inicio;
import com.example.taskmastertester.Menu;
import com.example.taskmastertester.R;
import com.example.taskmastertester.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {
    private Button borrarU;
    TextView user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        borrarU = view.findViewById(R.id.eliminarUsuarios);
        user = view.findViewById(R.id.ajustesUsuario);
        user.setText("Has iniciado sesion como "+Inicio.user);
        ocultarBotones();

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return view;
    }

    private void ocultarBotones() {
        if (Inicio.admin != true) {
            borrarU.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}