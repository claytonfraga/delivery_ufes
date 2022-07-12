package com.ufes.delivery.imposto;

import java.text.DecimalFormat;

/**
 *
 * @author clayton
 */
public class Imposto {

	private String nome;
	private double percentual;
	private double valor;

	public Imposto( String nome, double percentual, double valor ) {
		this.nome = nome;
		this.percentual = percentual;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public double getPercentual() {
		return percentual;
	}

	public double getValor() {
		return valor;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat( "0.00" );
		return "Imposto: " + nome + ", (%):" + df.format( percentual * 100 ) + ", valor (R$): " + df.format( valor );
	}

}
