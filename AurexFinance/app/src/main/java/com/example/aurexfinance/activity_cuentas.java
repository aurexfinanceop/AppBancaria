package com.example.aurexfinance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class activity_cuentas extends AppCompatActivity {

    private LinearLayout layoutEmpty;
    private RecyclerView rvCuentas;
    private FloatingActionButton fabAdd;

    // VARIABLES DEL ADAPTADOR Y LISTA
    private CuentasAdapter adapter;
    private List<Cuenta> listaDeCuentas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        // 1. Inicializar vistas
        layoutEmpty = findViewById(R.id.layoutEmptyState);
        rvCuentas = findViewById(R.id.rvCuentas);
        fabAdd = findViewById(R.id.fabAddCuenta);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // 2. Configurar RecyclerView y su Adaptador
        rvCuentas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CuentasAdapter(listaDeCuentas);
        rvCuentas.setAdapter(adapter);

        // 3. Configurar Menú de Navegación
        bottomNav.setSelectedItemId(R.id.nav_cuentas);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, activity_dashboard.class));
                finish();
                return true;
            }
            return id == R.id.nav_cuentas;
        });

        // 4. Botones para crear cuenta
        findViewById(R.id.btnCrearPrimeraCuenta).setOnClickListener(v -> mostrarModalCrear());
        fabAdd.setOnClickListener(v -> mostrarModalCrear());

        // Verificar estado inicial (si la lista está vacía o no)
        checkCuentas(!listaDeCuentas.isEmpty());
    }

    private void checkCuentas(boolean tieneCuentas) {
        if (tieneCuentas) {
            layoutEmpty.setVisibility(View.GONE);
            rvCuentas.setVisibility(View.VISIBLE);
            fabAdd.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvCuentas.setVisibility(View.GONE);
            fabAdd.setVisibility(View.GONE);
        }
    }

    private void mostrarModalCrear() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.layout_crear_cuenta, null);

        // Configurar el Spinner (Desplegable)
        Spinner spinner = view.findViewById(R.id.spinnerTipo);
        String[] opciones = {"Cuenta de Ahorros", "Cuenta Corriente", "Cuenta de Nómina"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner.setAdapter(spinnerAdapter);

        // Acción del botón Guardar dentro del Modal
        view.findViewById(R.id.btnGuardarCuenta).setOnClickListener(v -> {
            EditText etNombre = view.findViewById(R.id.etNombreCuenta);
            String nombreCuenta = etNombre.getText().toString().trim();
            String tipoSeleccionado = spinner.getSelectedItem().toString();

            if (!nombreCuenta.isEmpty()) {
                // 1. Crear el objeto cuenta con saldo inicial de simulación
                Cuenta nuevaCuenta = new Cuenta(nombreCuenta, tipoSeleccionado, "$0.00");

                // 2. Agregar a la lista global
                listaDeCuentas.add(nuevaCuenta);

                // 3. Notificar al adaptador que hay un item nuevo
                adapter.notifyItemInserted(listaDeCuentas.size() - 1);

                bottomSheet.dismiss();
                checkCuentas(true);
                Toast.makeText(this, "Cuenta \"" + nombreCuenta + "\" creada", Toast.LENGTH_SHORT).show();
            } else {
                etNombre.setError("Escribe un nombre");
            }
        });

        bottomSheet.setContentView(view);
        bottomSheet.show();
    }
}