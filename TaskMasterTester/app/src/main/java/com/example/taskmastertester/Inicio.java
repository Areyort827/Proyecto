package com.example.taskmastertester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Inicio extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    public static boolean admin;

    public static String user;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //admin = false;
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
        }else if(v.getId() == R.id.boton_registrarse){   //Pasar a la siguiente pantalla
            Intent i = new Intent(this, Registro.class);
            this.startActivity(i);
        }
    }

    private void iniciarSesion() {
        String url = "http://172.20.10.2/proyecto/iniciar.php?nombre=" + usuario.getText().toString() + "&pwd=" + password.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error.toString());
        Toast.makeText(this, "User not found"+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            JSONArray datosArray = response.getJSONArray("datos");
            JSONObject usuario = datosArray.getJSONObject(0);
            int id = usuario.getInt("id");
            user = usuario.getString("nombre");
            comprobarAdmin(id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Intent i = new Intent(this, Menu.class);
        this.startActivity(i);
    }

    public void comprobarAdmin(int id) {
        String url = "http://172.20.10.2/proyecto/Admin.php?id="+id;
        //172.20.10.2
        //192.168.1.138
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Inicio.this, "Has iniciado en modo Administrador", Toast.LENGTH_SHORT).show();
                admin = true;
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                admin = false;
            }
        });
        queue.add(request);

    }
}
