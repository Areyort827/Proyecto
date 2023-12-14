package com.example.taskmastertester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Registro extends AppCompatActivity implements View.OnClickListener{

    private RequestQueue queue;

    private Button r;

    public TextView id;

    public TextView usuario;

    public TextView password;

    public TextView correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);



        queue = Volley.newRequestQueue(getApplicationContext());
        r = this.findViewById(R.id.registrarse);
        id = this.findViewById(R.id.id_registro);
        usuario=this.findViewById(R.id.usuario_registro);
        password=this.findViewById(R.id.contrasena_registro);
        correo=this.findViewById(R.id.correo_registro);
        r.setOnClickListener(this);

    }

    public void registrarUsuario() {
        String url = "http://192.168.1.138/proyecto/registrar.php?id="+Integer.parseInt(id.getText().toString()) +"&nombre="+usuario.getText().toString()+"&pwd="+password.getText().toString()+"&correo="+correo.getText().toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Registro.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(Registro.this, "Usuario no valido, prueba con otro ID", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registrarse) {
            if(id.getText().toString().isEmpty()){
                id.setError("Introduce un ID");
            }else if(usuario.getText().toString().isEmpty()){
                usuario.setError("Introduce un usuario");
            }else if(password.getText().toString().isEmpty()){
                password.setError("Introduce una contraseña");
            }else if(!id.getText().toString().isEmpty() && !usuario.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                registrarUsuario();
                Intent i = new Intent(this, Inicio.class);
                this.startActivity(i);
            }


        }

    }
}
