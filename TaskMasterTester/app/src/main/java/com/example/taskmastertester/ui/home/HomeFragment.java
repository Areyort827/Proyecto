package com.example.taskmastertester.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmastertester.Inicio;
import com.example.taskmastertester.Menu;
import com.example.taskmastertester.NuevaTarea;
import com.example.taskmastertester.R;
import com.example.taskmastertester.Registro;
import com.example.taskmastertester.databinding.FragmentHomeBinding;
import com.example.taskmastertester.ui.slideshow.SlideshowViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.CalendarScopes;

import java.util.Calendar;

public class HomeFragment extends Fragment implements View.OnClickListener{
    CalendarView calendarView;
    TextView tv;
    private Button borrarC;

    private boolean calendarVisible = true;
    private FragmentHomeBinding binding;

    public static int yeari, mesi , diai;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        borrarC = view.findViewById(R.id.btnEliminarCalendario);
        calendarView = view.findViewById(R.id.calendarView);

         borrarC = view.findViewById(R.id.btnEliminarCalendario);
         ocultarBoton();
        borrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCalendarVisibility();
            }
        });

        if (calendarView != null) {
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int mes, int day) {
                    yeari = year;
                    mesi = mes+1;
                    diai = day;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("¿Desea crear un nuevo evento?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent intent = new Intent(getContext(),NuevaTarea.class);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });


                    builder.show();


                }
            });
        }else{
            System.out.println("esnulo");
        }

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return view;


    }

    private void ocultarBoton() {
        if (Inicio.admin == true) {
            borrarC.setVisibility(View.INVISIBLE);
            borrarC.setText("Mostrar Calendario");
        }else if(Inicio.admin == true){
            borrarC.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void toggleCalendarVisibility() {
        if (calendarVisible) {
            // Si el CalendarView es visible, hazlo invisible
            calendarView.setVisibility(View.INVISIBLE);
        } else {
            // Si el CalendarView no es visible, hazlo visible
            calendarView.setVisibility(View.VISIBLE);
        }

        // Invierte el estado de visibilidad
        calendarVisible = !calendarVisible;
    }

    public void onClick(View view) {

        }
    }
