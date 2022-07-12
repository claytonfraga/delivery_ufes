package com.ufes.delivery.model;

import java.text.DecimalFormat;

/**
 *
 * @author clayton
 */
public class Desconto {

	private String nome;
	private Double percentual;
	private Double valor;

	public Desconto( String nome, Double percentual, Double valor ) {
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
		return "Desconto: " + nome + ", (%):" + df.format( percentual * 100 ) + ", valor (R$): " + df.format( valor );
	}

}
