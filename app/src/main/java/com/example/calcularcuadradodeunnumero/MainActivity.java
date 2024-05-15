package com.example.calcularcuadradodeunnumero;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumber;
    private Button buttonCalculate;
    private TextView textViewResult;
    private ProgressBar progressBar;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        editTextNumber = findViewById(R.id.editTextNumber);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);
        progressBar = findViewById(R.id.progressBar);
        handler = new Handler();

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSquare();
            }
        });
    }
    private void calculateSquare() {
        // Mostrar ProgressBar mientras se realiza el cálculo
        progressBar.setVisibility(View.VISIBLE);

        // Obtener el número ingresado
        String input = editTextNumber.getText().toString().trim();

        // Validar si el campo de entrada está vacío
        if (input.isEmpty()) {
            editTextNumber.setError("Ingrese un número");
            editTextNumber.requestFocus();
            // Ocultar ProgressBar si no hay número ingresado
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        // Convertir el texto a un número entero
        int number = Integer.parseInt(input);

        // Realizar el cálculo en un hilo secundario para evitar bloquear la interfaz de usuario
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Calcular el cuadrado
                final int result = number * number;

                // Actualizar la interfaz de usuario en el hilo principal
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Mostrar el resultado en el TextView
                        textViewResult.setText("Resultado: " + result);
                        // Ocultar ProgressBar después de completar el cálculo
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }
}
