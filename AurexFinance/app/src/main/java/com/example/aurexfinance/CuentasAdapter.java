package com.example.aurexfinance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Intent;

public class CuentasAdapter extends RecyclerView.Adapter<CuentasAdapter.CuentaViewHolder> {

    private List<Cuenta> listaCuentas;

    public CuentasAdapter(List<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    @NonNull
    @Override
    public CuentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuenta, parent, false);
        return new CuentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuentaViewHolder holder, int position) {
        Cuenta cuenta = listaCuentas.get(position);
        holder.tvNombre.setText(cuenta.getNombre());
        holder.tvTipo.setText(cuenta.getTipo() + " • Activa");
        holder.tvSaldo.setText(cuenta.getSaldo());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), activity_detalle_cuenta.class);
            // Pasamos los datos de la cuenta a la siguiente pantalla
            intent.putExtra("nombre", cuenta.getNombre());
            intent.putExtra("tipo", cuenta.getTipo());
            intent.putExtra("saldo", cuenta.getSaldo());
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listaCuentas.size();
    }

    public static class CuentaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTipo, tvSaldo;

        public CuentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreCuenta);
            tvTipo = itemView.findViewById(R.id.tvTipoEstado);
            tvSaldo = itemView.findViewById(R.id.tvSaldoCuenta);
        }
    }
}