package com.ufes.delivery.model;

import java.text.DecimalFormat;

/**
 *
 * @author clayton
 */
public class Produto {

	private int codigo;
	private String nome;
	private double quantidadeEmEstoque;
	private double precoUnitario;

	public Produto( int codigo, String nome, double quantidadeEmEstoque, double precoUnitario ) {
		this.codigo = codigo;
		this.nome = nome;
		this.quantidadeEmEstoque = quantidadeEmEstoque;
		this.precoUnitario = precoUnitario;
	}

	public String getNome() {
		return nome;
	}

	public void incrementaEstoque( double quantidade ) {
		this.quantidadeEmEstoque += quantidade;
	}

	public void decrementaEstoque( double quantidade ) {
		this.quantidadeEmEstoque -= quantidade;
	}

	public double getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public double getPrecoUnitario() {
		return precoUnitario;
	}

	public int getCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat( "0.00" );
		return " Produto: " + getNome() + ", código: " + codigo + ", preço unitário: " + df.format( getPrecoUnitario() ) + ", quantidade em estoque: " + df.format( getQuantidadeEmEstoque() );
	}

}
