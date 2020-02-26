package com.gerardochoa.examenpractico.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gerardochoa.examenpractico.R;
import com.gerardochoa.examenpractico.adaptadores.AdaptadorPersona;
import com.gerardochoa.examenpractico.modelos.Persona;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ExamenBase extends AppCompatActivity implements RealmChangeListener<RealmResults<Persona>> {

    private final int DEBAJO_PESO_IDEAL = -1;
    private final int PESO_IDEAL = 0;
    private final int SOBREPESO = 1;

    private EditText nombre;
    private Spinner edad, sexo, altura, pesoKilo, pesoGramo;
    private Button limpiarDatos, generarReporte;
    private ListView listView;
    private AdaptadorPersona adaptadorPersona;

    private Realm realm;
    private RealmResults<Persona> personas;

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


        generarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String edadd = edad.getSelectedItem().toString();
                String nombrePersona = nombre.getText().toString();
                int edadPersona = Integer.parseInt(edadd);
                String sexoPersona = sexo.getSelectedItem().toString();
                Double pesoPersona = cadenaPeso();
                Double alturaPersona = Double.parseDouble(altura.getSelectedItem().toString());
                alertaReporte(nombrePersona, edadPersona, sexoPersona, pesoPersona, alturaPersona);
            }
        });
    }

    private Double cadenaPeso() {
        Double cadenaPeso = 1.0;
        String cadenaPesoString = pesoKilo.getSelectedItem().toString() + pesoGramo.getSelectedItem().toString();
        cadenaPeso = Double.parseDouble(cadenaPesoString);
        return cadenaPeso;
    }

    private void alertaReporte(final String nombre, final int edad, final String sexo, final Double peso, final Double altura) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.reporte));

        //Asignar el layout a la vista creada
        View view = LayoutInflater.from(this).inflate(R.layout.alerta_reporte, null);
        builder.setView(view);

        //Inicializar componentes de la alerta
        final TextView tvNombre = view.findViewById(R.id.getNombre);
        final TextView tvEdad = view.findViewById(R.id.getApellidos);
        TextView tvSexo = view.findViewById(R.id.getNombreUsuario);
        TextView tvAltura = view.findViewById(R.id.getCorreo);
        TextView tvPeso = view.findViewById(R.id.getPassword);
        TextView tvNss = view.findViewById(R.id.adaptadorNss);
        TextView tvEstado = view.findViewById(R.id.adaptadorEstado);

        final int estadoIMC = calcularIMC(peso, altura, sexo);

        tvNombre.setText(nombre);
        tvEdad.setText(String.valueOf(edad));
        tvSexo.setText(sexo);
        tvAltura.setText(String.valueOf(altura));
        tvPeso.setText(String.valueOf(peso));
        tvNss.setText("");
        switch (estadoIMC) {
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
                crearNuevoReporte(nombre, edad, sexo, altura, peso, estadoIMC);
            }
        });

        builder.setNegativeButton(getString(R.string.descartar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void crearNuevoReporte(String nombre, int edad, String sexo, double altura, double peso, int estadoIMC) {
        realm.beginTransaction();
        String estado = "";
        switch (estadoIMC) {
            case -1:
                estado = getString(R.string.debajo_peso);
                break;
            case 0:
                estado = getString(R.string.peso_ideal);
                break;
            case 1:
                estado = getString(R.string.sobrepero);
                break;
        }
        Persona persona = new Persona(nombre, edad, "", sexo, peso, altura, estado);
        realm.copyToRealm(persona);
        Toast.makeText(this, "se creó", Toast.LENGTH_SHORT).show();
        realm.commitTransaction();
    }

    private int calcularIMC(Double peso, Double altura, String sexo) {
        double alturaMetros = (altura * .01) * (altura * .01);
        double imc = peso / alturaMetros;
        int imcEntero = (int) imc;

        switch (sexo) {
            case "Hombre":
                if (imcEntero < 20) {
                    return DEBAJO_PESO_IDEAL;
                } else if (imcEntero > 25) {
                    return SOBREPESO;
                } else {
                    return PESO_IDEAL;
                }

            case "Mujer":
                if (imcEntero < 19) {
                    return DEBAJO_PESO_IDEAL;
                } else if (imcEntero > 24) {
                    return SOBREPESO;
                } else {
                    return PESO_IDEAL;
                }
            default:
                return imcEntero;
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

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        personas = realm.where(Persona.class).findAll();
        personas.addChangeListener(this);
        adaptadorPersona = new AdaptadorPersona(getApplicationContext(), personas, R.layout.adaptador_personas);
        listView = findViewById(R.id.listViewPersonas);
        listView.setAdapter(adaptadorPersona);
        adaptadorPersona.notifyDataSetChanged();





        /*
        Base de datos
         */


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

    @Override
    public void onChange(RealmResults<Persona> personas) {
        adaptadorPersona.notifyDataSetChanged();
    }
}
