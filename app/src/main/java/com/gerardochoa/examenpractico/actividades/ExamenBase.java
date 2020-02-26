package com.gerardochoa.examenpractico.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gerardochoa.examenpractico.R;
import com.gerardochoa.examenpractico.modelos.Persona;

public class ExamenBase extends AppCompatActivity {

    private final int DEBAJO_PESO_IDEAL = -1;
    private final int PESO_IDEAL = 0;
    private final int SOBREPESO = 1;

    private EditText nombre;
    private Spinner edad, sexo, altura, pesoKilo, pesoGramo;
    private Button limpiarDatos, generarReporte;
    private String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen_base);

        inicializarComponentes();

        limpiarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicializarComponentes();
                nombre.setText("");
                nombre.setHint(getString(R.string.ingresa_nombre));
            }
        });


        titulo = "GGG";
        generarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombrePersona = nombre.getText().toString();
                int edadPersona = Integer.parseInt(edad.getSelectedItem().toString());
                char sexoPersona = sexo.getSelectedItem().toString().charAt(0);
                Double pesoPersona = cadenaPeso();
                Double alturaPersona = Double.parseDouble(altura.getSelectedItem().toString());
                Persona persona = new Persona(nombrePersona, edadPersona, "", sexoPersona, pesoPersona, alturaPersona);
                Log.d("ggg", "nombre: " + persona.getNombre() + " edad: "
                        + persona.getEdad() + " nss: " + persona.getNSS() + " sexo: " + persona.getSexo()
                        + " peso: " + persona.getPeso() + " altura: " + persona.getAltura());
                alertaReporte(titulo, persona);
            }
        });
    }

    private Double cadenaPeso() {
        Double cadenaPeso = 1.0;
        String cadenaPesoString = pesoKilo.getSelectedItem().toString() + pesoGramo.getSelectedItem().toString();
        cadenaPeso = Double.parseDouble(cadenaPesoString);
        Log.d("ggg", "pesoString" + cadenaPesoString);

        return cadenaPeso;
    }

    private void alertaReporte(String titulo, final Persona persona) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (titulo != null) builder.setTitle(getString(R.string.reporte));

        //Asignar el layout a la vista creada
        View view = LayoutInflater.from(this).inflate(R.layout.alerta_reporte, null);
        builder.setView(view);

        //Inicializar componentes de la alerta
        final TextView tvNombre = view.findViewById(R.id.tvNombre);
        final TextView tvEdad = view.findViewById(R.id.tvEdad);
        TextView tvSexo = view.findViewById(R.id.tvSexo);
        TextView tvAltura = view.findViewById(R.id.tvAltura);
        TextView tvPeso = view.findViewById(R.id.tvPeso);
        TextView tvNss = view.findViewById(R.id.tvNss);
        TextView tvEstado = view.findViewById(R.id.tvEstado);

        final int estadoIMC = calcularIMC(persona);

        tvNombre.setText(persona.getNombre());
        tvEdad.setText(String.valueOf(persona.getEdad()));
        tvSexo.setText(String.valueOf(persona.getSexo()));
        tvAltura.setText(String.valueOf(persona.getAltura()));
        tvPeso.setText(String.valueOf(persona.getPeso()));
        tvNss.setText(persona.getNSS());
        switch (estadoIMC){
            case -1:
                tvEstado.setText(getString(R.string.debajo_peso));
                break;
            case 0:
                tvEstado.setText(getString(R.string.peso_ideal));
                break;
            case 1:
                tvEstado.setText(getString(R.string.sobrepero));
                break;
        }

        builder.setPositiveButton(getString(R.string.guardar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                crearNuevoReporte(persona, estadoIMC);
            }
        });



        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void crearNuevoReporte(Persona persona, int estadoIMC) {
    }

    private int calcularIMC(Persona persona) {
        int estadoIC = 0;
        double altura = persona.getAltura();
        double peso = persona.getPeso();

        double alturaMetros = (altura * .01) * (altura * .01);
        double imc = peso / alturaMetros;
        int imcEntero = (int) imc;

        switch (persona.getSexo()) {
            case 'H':
                if (imcEntero < 20) {
                    return DEBAJO_PESO_IDEAL;
                } else if (imcEntero > 25) {
                    return SOBREPESO;
                } else {
                    return PESO_IDEAL;
                }
            case 'M':
                if (imcEntero < 19) {
                    return DEBAJO_PESO_IDEAL;
                } else if (imcEntero > 24) {
                    return SOBREPESO;
                } else {
                    return PESO_IDEAL;
                }
            default:
                return estadoIC;
        }


    }

    private void inicializarComponentes() {
        nombre = findViewById(R.id.etNombre);
        edad = findViewById(R.id.spEdad);
        sexo = findViewById(R.id.spSexo);
        altura = findViewById(R.id.spAltura);
        pesoKilo = findViewById(R.id.spPesoKilo);
        pesoGramo = findViewById(R.id.spPesoGramo);
        limpiarDatos = findViewById(R.id.btLimpiarDatos);
        generarReporte = findViewById(R.id.btGenerar);

        ArrayAdapter<CharSequence> adapterEdad = ArrayAdapter.createFromResource(this,
                R.array.edad, R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterSexo = ArrayAdapter.createFromResource(this,
                R.array.sexo, R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterAltura = ArrayAdapter.createFromResource(this,
                R.array.altura, R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterKilo = ArrayAdapter.createFromResource(this,
                R.array.peso_entero, R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterGramo = ArrayAdapter.createFromResource(this,
                R.array.peso_gramos, R.layout.support_simple_spinner_dropdown_item);

        edad.setAdapter(adapterEdad);
        sexo.setAdapter(adapterSexo);
        altura.setAdapter(adapterAltura);
        pesoKilo.setAdapter(adapterKilo);
        pesoGramo.setAdapter(adapterGramo);
    }
}
