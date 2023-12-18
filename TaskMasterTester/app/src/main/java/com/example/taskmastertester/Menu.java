package com.example.taskmastertester;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmastertester.model.Evento;
import com.example.taskmastertester.model.Usuario;
import com.example.taskmastertester.ui.slideshow.SlideshowViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmastertester.databinding.ActivityMainBinding;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Menu extends AppCompatActivity implements View.OnClickListener,OnItemListUpdatedListener{

    public static CustomAdapter customAdapter;

    public List<Event> items;
    private SlideshowViewModel slideshowViewModel;
    public static List <Evento> lista = new ArrayList<>();
    private CalendarView calendarView;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static GoogleAccountCredential credential;
    TextView tx;
    public static String correo;
    private static final int REQUEST_AUTHORIZATION = 1001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tx = this.findViewById(R.id.text_home);
        calendarView = this.findViewById(R.id.calendarView);
        if ( items != null){
            items.clear();
        }
        lista = new ArrayList<>();

        // Obtener la instancia del ViewModel
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);

        // Configuración de Google Sign-In
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(CalendarScopes.CALENDAR))
                .requestEmail()
                .build();

        // Crear el cliente de inicio de sesión de Google
        final GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, signInOptions);

        startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_AUTHORIZATION);

         // Implementa según tus necesidades
        System.out.println("ESTO ES UNA PRUEBA");
        for (Evento e : lista) {
            System.out.println("prueba del objeto: " + e.getTitulo());
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar((binding.appBarMain).toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery,R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }

    @Override
    public void updateListViewData(List<Evento> newData) {

    }

    private class LoadEvents extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            //Aquí debes realizar las operaciones asíncronas, como interactuar con la API de Google Calendar

            //String selectedDate = params[0];
            listarEventos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Este método se ejecuta en el hilo principal después de que doInBackground ha terminado
            // Puedes realizar actualizaciones en la interfaz de usuario aquí si es necesario
        }
    }
    private void listarEventos() {
        try {
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName("TaskMaster")
                    .build();

            com.google.api.services.calendar.model.Calendar calendar = service.calendars().get(NuevaTarea.idCalendar).execute();
            Events events = service.events().list(NuevaTarea.idCalendar).execute();
            items = events.getItems();

            for (Event event : items) {
                Evento e = new Evento(event.getSummary(),event.getDescription(),event.getStart(),event.getEnd());
                lista.add(e);
            }

            List<Evento> newData = lista;

            customAdapter = new CustomAdapter(this,lista);
            slideshowViewModel.setItemList(newData);

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_AUTHORIZATION) {
            handleSignInResult(data);
        }
    }

    private void handleSignInResult(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(googleSignInAccount -> {
                    // Obtener las credenciales de la cuenta de Google seleccionada
                    credential = GoogleAccountCredential.usingOAuth2(
                            getApplicationContext(), Arrays.asList(CalendarScopes.CALENDAR));
                    credential.setSelectedAccount(googleSignInAccount.getAccount());
                    correo = googleSignInAccount.getAccount().toString();
                    new LoadEvents().execute();
                    List<Evento> newData = lista;
                    slideshowViewModel.setItemList(newData);


                })
                .addOnFailureListener(e -> {
                    // Manejar el fallo en la autenticación
                    Toast.makeText(this, "Error en la autenticación: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.cerrarsesion){
            Intent i = new Intent(this,Inicio.class);
            this.startActivity(i);
        }else if(view.getId() == R.id.eliminarUsuarios){
            Intent i = new Intent(this, Usuarios.class);
            this.startActivity(i);
        }
    }

}

