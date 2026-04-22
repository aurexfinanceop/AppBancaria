package com.example.aurexfinance;

public class Cuenta {
    private String nombre;
    private String tipo;
    private String saldo;

    public Cuenta(String nombre, String tipo, String saldo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getSaldo() { return saldo; }
}