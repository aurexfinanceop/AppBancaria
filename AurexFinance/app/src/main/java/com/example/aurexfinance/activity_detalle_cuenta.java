package com.example.aurexfinance;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_detalle_cuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cuenta);

        // 1. Referencias de la UI
        TextView tvNombre = findViewById(R.id.tvNombreCuentaDetalle);
        TextView tvSaldo = findViewById(R.id.tvSaldoDetalle);
        TextView tvTipo = findViewById(R.id.tvTipoCuentaDetalle);
        ImageButton btnAtras = findViewById(R.id.btnAtrasDetalle);

        // 2. Recibir datos del Intent (lo que mandamos desde el adaptador)
        String nombre = getIntent().getStringExtra("nombre");
        String saldo = getIntent().getStringExtra("saldo");
        String tipo = getIntent().getStringExtra("tipo");

        // 3. Setear datos en la vista
        if (nombre != null) tvNombre.setText(nombre);
        if (saldo != null) tvSaldo.setText(saldo);
        if (tipo != null) tvTipo.setText(tipo.toUpperCase());

        // 4. Configurar botón de regreso
        btnAtras.setOnClickListener(v -> finish());

        // 5. LLAMAR A LA FUNCIÓN DE MOVIMIENTOS
        cargarMovimientosSimulados();
    }

    private void cargarMovimientosSimulados() {
        LinearLayout container = findViewById(R.id.containerMovimientos);

        // Datos de prueba para que la cuenta no se vea vacía
        String[][] movimientos = {
                {"Compra en Starbucks", "-$15.50", "Hoy"},
                {"Transferencia Recibida", "+$100.00", "Ayer"},
                {"Pago de Servicio", "-$40.00", "15 Abr"}
        };

        for (String[] mov : movimientos) {
            // Inflamos un diseño básico de lista de Android (simple_list_item_2)
            View v = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, null);
            TextView t1 = v.findViewById(android.R.id.text1);
            TextView t2 = v.findViewById(android.R.id.text2);

            t1.setText(mov[0]);
            t1.setTextColor(getResources().getColor(R.color.primary_blue));
            t1.setTypeface(null, Typeface.BOLD);

            t2.setText(mov[2] + " | " + mov[1]);

            // Si el monto tiene un "+", lo pintamos de verde (Dinero entrante)
            if (mov[1].contains("+")) {
                t2.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }

            container.addView(v);
        }
    }
}