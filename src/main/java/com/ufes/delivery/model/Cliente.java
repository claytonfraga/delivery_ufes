package com.ufes.delivery.model;

import java.text.DecimalFormat;

/**
 *
 * @author clayton
 */
public class Cliente {

    private String nome;
    private double saldo;

    public Cliente(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public void aumentaSaldo(double valor) {
        this.saldo += valor;
    }

    public void deduzSaldo(double valor) {
        if (valor > this.saldo) {
            throw new RuntimeException("O cliente não pode pagar por esse pedido");
        }
        this.saldo -= valor;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "Cliente: " + nome + ", saldo de R$ " + df.format(saldo);
    }

}
