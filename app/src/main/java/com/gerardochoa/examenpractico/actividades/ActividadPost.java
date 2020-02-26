package com.gerardochoa.examenpractico.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gerardochoa.examenpractico.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActividadPost extends AppCompatActivity {

    private EditText etNombre;
    private EditText etApellidos;
    private EditText etNombreUsuario;
    private EditText etCorreo;
    private EditText etPassword;
    private Button btLimpiar;
    private Button btGuardar;

    private String url;
    private String nombre;
    private String apellidos;
    private String nombreUsusario;
    private String correo;
    private String password;

    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_post);

        inicializarComponentes();
        btLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNombre.setText("");
                etApellidos.setText("");
                etNombreUsuario.setText("");
                etCorreo.setText("");
                etPassword.setText("");
                inicializarComponentes();
            }
        });

        parseJSON(url);
    }

    private void parseJSON(String url) {
        queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(ActividadPost.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(ActividadPost.this, "Error servidor", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("Nombre", nombre);
                params.put("Apellidos", apellidos);
                params.put("Nombre_Usuario", nombreUsusario);
                params.put("Correo_Electronico", correo);
                params.put("Contraseña", password);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void inicializarComponentes() {
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etNombreUsuario = findViewById(R.id.etNombreUsusario);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btGuardar = findViewById(R.id.btAgregarCliente);
        btLimpiar = findViewById(R.id.btlimpiarCliente);

        url = "http://187.188.122.85:8091/NutriNET/Cliente";

        nombre = etNombre.getText().toString();
        apellidos = etApellidos.getText().toString();
        nombreUsusario = etNombreUsuario.getText().toString();
        correo = etCorreo.getText().toString();
        password = etPassword.getText().toString();
    }
}
