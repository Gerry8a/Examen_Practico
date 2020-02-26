package com.gerardochoa.examenpractico.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gerardochoa.examenpractico.R;
import com.gerardochoa.examenpractico.adaptadores.AdaptadorGet;
import com.gerardochoa.examenpractico.modelos.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActividadGet extends AppCompatActivity {

    RequestQueue requestQueue;
    private ListView listView;
    private AdaptadorGet adaptadorGet;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_get);

        inicializarComponentes();
        parseJSON(url);


    }

    private void parseJSON(String url) {
        Log.d("ggg", "entra a metodo");
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    llenarLista(jsonArray);
                    Log.d("ggg", "infor" + jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }


    private void llenarLista(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject noticiaJSON = jsonArray.getJSONObject(i);
            agregarNoticia(noticiaJSON);
        }
        adaptadorGet = new AdaptadorGet(getApplicationContext(), clientes, R.layout.adaptador_get);
        listView.setAdapter(adaptadorGet);
    }


    private void agregarNoticia(JSONObject noticiaJSON) throws JSONException {
        String nombre = noticiaJSON.getString("title");
        String apellidos = noticiaJSON.getString("author");
        String nombreUsuario = noticiaJSON.getString("publishedAt");
        String correoElectronioc = noticiaJSON.getString("content");
        String password = noticiaJSON.getString("description");

        Cliente cliente = new Cliente(nombre, apellidos, nombreUsuario, correoElectronioc, password);
        clientes.add(cliente);
    }

    private void inicializarComponentes() {
        listView = findViewById(R.id.getListView);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        url = "http://187.188.122.85:8091/NutriNET/Cliente";
    }


}
