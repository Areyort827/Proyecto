package com.example.taskmastertester;



import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmastertester.ui.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NuevaTarea extends AppCompatActivity {
    private int notificacionTiempo;
    public static String idCalendar ="primary";
    EditText txtTitulo,txtDescripcion,txtFechai,txtFechaf,txthorai,txthoraf;
    Switch duracion;

    // Constantes para la solicitud de permisos
    private static final int REQUEST_AUTHORIZATION = 1001;
    Button guardar;
    private RadioGroup radioGroup;
    private GoogleAccountCredential mCredential;

    private final String cero = "0";
    private int yeari,yearf,mesi,mesf,diai,diaf,horai,horaf,minutoi,minutof;
    private String imes,idia,fmes,fdia,ihora,iminuto,fhora,fminuto;
    private static final String[] SCOPES = { CalendarScopes.CALENDAR };

    private String rojo = "11";
    private String verde = "10";
    private String azul = "9";
    private String amarillo = "5";

    private String color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir_tarea);
        //getSupportActionBar().hide();
        guardar = this.findViewById(R.id.guardar);
        txtTitulo= this.findViewById(R.id.txtTitulo);
        txtDescripcion = this.findViewById(R.id.txtDescripcion);
        txtFechai= this.findViewById(R.id.txtFechai);
        txtFechaf= this.findViewById(R.id.txtFechaF);
        txthorai= this.findViewById(R.id.txtHorai);
        txthoraf= this.findViewById(R.id.txtHoraf);
       // duracion = this.findViewById(R.id.switch1);
        yeari = HomeFragment.yeari;
        mesi = HomeFragment.mesi;
        diai = HomeFragment.diai;
        radioGroup = findViewById(R.id.radiogroup);


        if (mesi<10){
            imes = "0" + mesi;

        }else{
            imes = ""+mesi;
        }
        if (diai<10){
            idia = "0"+diai;
        }else {
            idia = "" + diai;
        }

        txtFechai.setText(idia + "/" + imes + "/" + yeari);

        // Configuración de Google Sign-In
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(CalendarScopes.CALENDAR))
                .requestEmail()
                .build();

        // Crear el cliente de inicio de sesión de Google
        final GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, signInOptions);

// Configurar el botón para iniciar sesión
        findViewById(R.id.guardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTitulo.getText().toString().isEmpty()){
                    txtTitulo.setError("Introduce un titulo");
                }
                if(txtDescripcion.getText().toString().isEmpty()){
                    txtDescripcion.setError("Introduce una descripcion");
                }
                if(txtFechai.getText().toString().isEmpty()){
                    txtFechai.setError("Introduce una fecha inicial");
                }
                if(txtFechaf.getText().toString().isEmpty()){
                    txtFechaf.setError("Introduce una fecha final");
                }
                if(txthorai.getText().toString().isEmpty()){
                    txthorai.setError("Introduce una hora inicial");
                }
                if(txthoraf.getText().toString().isEmpty()){
                    txthoraf.setError("Introduce una hora final");
                }
                if(!txtTitulo.getText().toString().isEmpty() && !txtDescripcion.getText().toString().isEmpty() && !txtFechai.getText().toString().isEmpty()
                        && !txtFechaf.getText().toString().isEmpty() && !txthorai.getText().toString().isEmpty() &&
                        !txthoraf.getText().toString().isEmpty() ){
                    new CreateEventTask().execute();

                    Intent i = new Intent(getApplicationContext(),Menu.class);
                    startActivity(i);
                }

            }
        });

        }

    public void onClick(View v){
        if(v.getId() == R.id.btnFechai){
            Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
            DatePickerDialog d = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    yeari = year;
                    mesi = month;
                    diai = day;
                    mesi++;
                    if (mesi<10){
                        imes = "0" + mesi;
                    }else{
                        imes = ""+mesi;
                    }
                    if (diai<10){
                        idia = "0"+diai;
                    }else{
                        idia = ""+diai;
                    }
                    txtFechai.setText(idia + "/" + imes + "/" + yeari);

                    Toast.makeText(NuevaTarea.this, yeari+""+imes+""+idia, Toast.LENGTH_SHORT).show();

                }
            },2024, 0,1);
            d.show();
        }else if(v.getId() == R.id.btnFechaF){
            DatePickerDialog d = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    yearf = year;
                    mesf = month;
                    diaf = day;
                    mesf++;
                    if (mesf<10){

                        fmes = "0" + mesf;
                        //imes = "0"+(mesi+1);
                    }else{
                        fmes = ""+mesf;
                    }
                    if (diaf<10){
                        fdia = "0"+diaf;
                    }else{
                        fdia = ""+diaf;
                    }
                    txtFechaf.setText(fdia + "/" + fmes + "/" + yearf);

                    Toast.makeText(NuevaTarea.this, yearf+""+fmes+""+fdia, Toast.LENGTH_SHORT).show();

                }
            },2024, 0,1);
            d.show();
        }else if(v.getId() == R.id.btnHorai){
            TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                    horai=hora;
                    minutoi=minuto;
                    if(minutoi < 10){
                        iminuto = "0" + minutoi;
                    }else{
                        iminuto = ""+minutoi;
                    }
                        txthorai.setText(horai+":"+iminuto);
                }
            },0,0,true);
            tpd.show();
        }else if(v.getId() == R.id.btnHoraf){
            TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                    horaf=hora;
                    minutof=minuto;

                    if(minutof < 10){
                        fminuto = "0" + minutof;
                    }else{
                        fminuto = ""+minutof;
                    }
                    txthoraf.setText(horaf+":"+fminuto);
                }
            },0,0,true);
            tpd.show();
        } else if (v.getId() == R.id.btnTipoRojo) {
            color = rojo;

        } else if (v.getId() == R.id.btnTipoVerde) {
            color = verde;


        } else if (v.getId() == R.id.btnTipoAmarillo) {
            color = amarillo;


        } else if (v.getId() == R.id.btnTipoAzul) {
            color = azul;


        }
    }

    private class CreateEventTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            createGoogleCalendarEvent();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Este método se ejecuta en el hilo principal después de que doInBackground ha terminado

        }
    }

    private void createGoogleCalendarEvent() {
        /** Authorizes the installed application to access user's protected data. */
        try {


            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            Calendar service = new Calendar.Builder(httpTransport, jsonFactory, Menu.credential)
                    .setApplicationName("TaskMaster")
                    .build();

            Events events = service.events().list(idCalendar).execute();
            List<Event> items = events.getItems();

            // Crear un evento
            Event event = new Event()
                    .setSummary(txtTitulo.getText().toString())
                    .setDescription(txtDescripcion.getText().toString())
                    .setColorId(color)
                    ;

            String inputDateTimeString = yeari+"-"+mesi+"-"+idia+" "+horai+":"+minutoi+":00";
            EventDateTime eventDateTime = convertToEventDateTime(inputDateTimeString);
            event.setStart(eventDateTime);

            String inputDateTimeStringFinal = yearf+"-"+mesf+"-"+fdia+" "+horaf+":"+minutof+":00";
            EventDateTime eventDateTimeFinal = convertToEventDateTime(inputDateTimeStringFinal);

            event.setEnd(eventDateTimeFinal);

            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            if(radioGroup.getCheckedRadioButtonId() == R.id.min30){
                 notificacionTiempo = 30;
            }else if(radioGroup.getCheckedRadioButtonId() == R.id.h1){
                 notificacionTiempo = 60;
            }else if(radioGroup.getCheckedRadioButtonId() == R.id.h2){
                 notificacionTiempo = 120;
            }
            // Añadir notificación al evento

            EventReminder reminder = new EventReminder().setMinutes(notificacionTiempo).setMethod("popup");
            event.setReminders(new Event.Reminders().setUseDefault(false).setOverrides(Collections.singletonList(reminder)));

            // Insertar el evento en el calendario
            String calendarId = idCalendar;  // 'primary' se refiere al calendario del usuario actual

            event = service.events().insert(calendarId, event).execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
    public static EventDateTime convertToEventDateTime(String inputDateTimeString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            // Convertir la cadena a un objeto Date usando SimpleDateFormat
            Date date = simpleDateFormat.parse(inputDateTimeString);

            // Convertir el objeto Date a un objeto DateTime
            DateTime dateTime = new DateTime(date);

            // Crear un objeto EventDateTime y establecer la fecha y hora
            EventDateTime eventDateTime = new EventDateTime();
            eventDateTime.setDateTime(dateTime);

            return eventDateTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Manejar errores de formato de cadena
        }
    }
}




