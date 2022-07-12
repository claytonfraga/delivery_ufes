package com.ufes.delivery.model;

/**
 *
 * @author clayton
 */
public class Estabelecimento {

	private String nome;

	public Estabelecimento( String nome ) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	@Override
	public String toString() {
		return "Estabelecimento: " + getNome();
	}

}
