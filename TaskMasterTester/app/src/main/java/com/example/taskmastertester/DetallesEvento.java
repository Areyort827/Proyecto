package com.example.taskmastertester;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetallesEvento extends AppCompatActivity {

    TextView titulo,descripcion,fechainicial,fechafinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_tareas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        titulo = this.findViewById(R.id.dtitulo);
        descripcion = this.findViewById(R.id.ddescripcion);
       // fechafinal = this.findViewById(R.id.dfechafinal);
       // fechainicial = this.findViewById(R.id.dfechainicial);

        Intent intent = this.getIntent();
        if (intent != null){
            String txttitulo = intent.getStringExtra("Titulo");
            String txtdescripcion = intent.getStringExtra("Descripcion");
            String txtfechai = intent.getStringExtra("Fechai");
            String txtfechaf = intent.getStringExtra("Fechaf");

            titulo.setText(txttitulo);
            descripcion.setText(txtdescripcion);
            //fechainicial.setText(txtfechai);
           // fechafinal.setText(txtfechaf);
        }
    }

    private DateTime convertirDateADATETIME(Date fecha) {
        return new DateTime(fecha);
    }
}
