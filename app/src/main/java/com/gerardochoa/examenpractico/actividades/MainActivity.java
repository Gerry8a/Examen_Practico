package com.gerardochoa.examenpractico.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gerardochoa.examenpractico.R;

public class MainActivity extends AppCompatActivity {

    private Button examBase, examRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        examBase = findViewById(R.id.button);
        examRest = findViewById(R.id.button2);

        examBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExamenBase.class);
                startActivity(intent);
            }
        });

        examRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ServiciosRest.class);
                startActivity(intent);
            }
        });
    }
}
