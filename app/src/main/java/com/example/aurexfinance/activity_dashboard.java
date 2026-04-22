package com.example.aurexfinance;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class activity_dashboard extends AppCompatActivity {

    private LinearLayout containerTransacciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        containerTransacciones = findViewById(R.id.containerTransacciones);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        FloatingActionButton fab = findViewById(R.id.fabMenu);

        // CONFIGURACIÓN DEL MENÚ INFERIOR
        bottomNav.setSelectedItemId(R.id.nav_home); // Asegura que "Inicio" esté marcado
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true; // Ya estamos aquí
            } else if (id == R.id.nav_cuentas) {
                // Navegación optimizada a Cuentas
                Intent intent = new Intent(this, activity_cuentas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            }
            return false;
        });

        // FAB Menú
        fab.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenu().add("💸 Sacar Plata");
            popup.getMenu().add("📲 Recargar");
            popup.getMenu().add("🛡️ Seguros");
            popup.getMenu().add("🚪 Cerrar Sesión");

            popup.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getTitle().equals("🚪 Cerrar Sesión")) {
                    startActivity(new Intent(this, activity_login.class));
                    finish(); // Al cerrar sesión sí destruimos el Dashboard
                }
                return true;
            });
            popup.show();
        });

        // Botón Transferir
        findViewById(R.id.btnTransferir).setOnClickListener(v -> {
            Toast.makeText(this, "Función Transferir próximamente", Toast.LENGTH_SHORT).show();
        });

        cargarDatosSimulados();
    }

    private void cargarDatosSimulados() {
        String[][] datos = {{"Netflix", "-$15.99", "Hoy"}, {"Nómina", "+$2,500.00", "Ayer"}};
        for (String[] t : datos) agregarFila(t[0], t[1], t[2]);
    }

    private void agregarFila(String titulo, String monto, String fecha) {
        View v = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, null);
        TextView t1 = v.findViewById(android.R.id.text1);
        TextView t2 = v.findViewById(android.R.id.text2);
        t1.setText(titulo);
        t1.setTypeface(null, Typeface.BOLD);
        t1.setTextColor(getResources().getColor(R.color.primary_blue));
        t2.setText(fecha + " | " + monto);
        if (monto.contains("+")) t2.setTextColor(getResources().getColor(R.color.success_green));
        containerTransacciones.addView(v);
    }
}