package com.example.taskmastertester;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.taskmastertester.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Usuarios extends AppCompatActivity  {
    private RequestQueue queue;
    private JsonRequest jrq;

    List<Usuario> listaUsuarios = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        ListView listViewUsuarios = findViewById(R.id.listViewUsuarios);
        //Hacer consulta
        queue = Volley.newRequestQueue(getApplicationContext());
        mostrarUsuarios();

    }

    public void mostrarUsuarios() {
        String url = "http://192.168.1.138/proyecto/contarUsuarios.php?";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(Registrar.this, "The user is registered", Toast.LENGTH_SHORT).show();
                // Obtener el array 'datos' del objeto JSON
                try {
                    JSONArray datosArray = response.getJSONArray("datos");

                    for (int i = 0; i < datosArray.length(); i++) {
                        JSONObject usuario = datosArray.getJSONObject(i);

                        // Acceder a las propiedades del usuario (por ejemplo, id, nombre, pwd)
                        int id = usuario.getInt("id");
                        String nombre = usuario.getString("nombre");
                        String pwd = usuario.getString("pwd");

                        // Hacer algo con la información del usuario, por ejemplo, imprimir en la consola
                        Usuario u = new Usuario(id, nombre, pwd);

                        listaUsuarios.add(u);
                        System.out.println("p" + u.getNombre());

                        // Crear un adaptador
                        AdapterUsuarios usuarioAdapter = new AdapterUsuarios(getApplicationContext(), listaUsuarios);
                        // Obtener la referencia al ListView en tu diseño
                        ListView listViewUsuarios = findViewById(R.id.listViewUsuarios);

                        // Asignar el adaptador al ListView
                        listViewUsuarios.setAdapter(usuarioAdapter);


                        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Usuarios.this);
                                builder.setMessage("¿Desea borrar este usuario?")
                                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //Borrar usuario
                                                //System.out.println(listaUsuarios.get(i).getNombre()+listaUsuarios.get(i).getId());
                                                eliminarUsuario(listaUsuarios.get(i).getId());
                                                //listViewUsuarios.getAdapter().getItem(i).toString();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog
                                                Intent i = new Intent(getApplicationContext(), Menu.class);
                                                startActivity(i);
                                            }
                                        });
                                builder.show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                //Toast.makeText(Registrar.this, "User not valid, try with another ID", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);

    }


    public void eliminarUsuario(int id) {
        String url = "http://192.168.1.138/proyecto/eliminarUsuario.php?id="+id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Usuarios.this, "El usuario ha sido eliminado correctamente", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Usuarios.this,Menu.class);
                startActivity(i);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(request);

    }
}

