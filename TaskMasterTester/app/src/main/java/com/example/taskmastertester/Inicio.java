package com.example.taskmastertester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class Inicio extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private Button b; //iniciar sesion
    private Button r; //registrarse

    private RequestQueue queue;
    private JsonRequest jrq;
    public TextView usuario; //Input usuario
    public TextView password; //Input contraseña

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);
        //getSupportActionBar().hide();


        b = this.findViewById(R.id.boton_iniciar_sesion);
        r = this.findViewById(R.id.boton_registrarse);
        usuario=this.findViewById(R.id.input_usuario);
        password=this.findViewById(R.id.input_contrasena);
        b.setOnClickListener(this);
        r.setOnClickListener(this);

        //Hacer consulta
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.boton_iniciar_sesion) {   //Comprobar si existe el usuario y pasar a la siguiente pantalla
            iniciarSesion();
            //Intent i = new Intent(this, Menu.class);
            //this.startActivity(i);
        }else if(v.getId() == R.id.boton_registrarse){   //Pasar a la siguiente pantalla
            Intent i = new Intent(this, Registro.class);
            this.startActivity(i);
            //registrarUsuario();
        }
    }

    private void iniciarSesion() {
        String url = "http://172.20.10.2/proyecto/iniciar.php?nombre=" + usuario.getText().toString() + "&pwd=" + password.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(jrq);
    }
//172.20.10.2
    //192.168.1.136
    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error.toString());
        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Intent i = new Intent(this, Menu.class);
        this.startActivity(i);
    }
}
